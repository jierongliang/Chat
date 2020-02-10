package com.example.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
//消息适配器
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg> mMsgList;
    private String myUsername;

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg, leftTime;
        TextView rightMsg, rightTime;

        public ViewHolder(View view) {
            super(view);
            leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            leftMsg = (TextView) view.findViewById(R.id.left_msg);
            rightMsg = (TextView) view.findViewById(R.id.right_msg);
            leftTime = (TextView) view.findViewById(R.id.left_msg_time);
            rightTime = (TextView) view.findViewById(R.id.right_msg_time);
        }
    }

    public MsgAdapter(List<Msg> mMsgList, String myUsername) {
        this.mMsgList = mMsgList;
        this.myUsername = myUsername;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);
        // 如果是发出的消息，则显示右边的消息布局，将左边的消息布局隐藏
        if (msg.getFromUser().equals(myUsername)) {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
            holder.leftTime.setText(formatTime(msg.getTime()));
        }
        // 如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
        else {
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
            holder.rightTime.setText(formatTime(msg.getTime()));
        }
    }
    //格式化时间
    public String formatTime(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒");
        Date date = new Date();
        date.setTime(time);
        String result = simpleDateFormat.format(date);
        return result;
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
