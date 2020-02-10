package com.example.chat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
//用户适配器
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private ArrayList<User> users;
    private String myuid;
    private String myusername;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.user_name);
        }
    }

    public UserAdapter(ArrayList<User> users, String myuid, String myusername) {
        this.users = users;
        this.myuid = myuid;
        this.myusername = myusername;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final Context context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item,viewGroup,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();

                User user = users.get(position);
//                Intent intent = new Intent(context,sfafsaaf.class);
//                context.startActivity(intent);
                Intent intent = new Intent(context,MsgActivity.class);
                intent.putExtra("toUsername",user.getUsername());
                intent.putExtra("fromUsername",ChatActivity.myUsername);
                context.startActivity(intent);
               // Toast.makeText(v.getContext(),user.getUsername()+user.getUid(),Toast.LENGTH_LONG).show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        User user = users.get(i);
        viewHolder.username.setText(user.getUsername());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
