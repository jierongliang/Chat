package com.example.chat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//消息活动
public class MsgActivity extends AppCompatActivity  {
    @BindView(R.id.msg_recyclerview)
    RecyclerView msgRecyclerview;
    @BindView(R.id.input_text)
    EditText inputText;
    @BindView(R.id.send)
    Button send;
    public String addressQuery = "http://192.168.0.4:8080/Bye/lizhien";
    public String addressInsert = "http://192.168.0.4:8080/Bye/erke";
    @BindView(R.id.contact_user)
    TextView contactUser;
    @BindView(R.id.backUserActivity)
    ImageButton backUserActivity;
    private MsgAdapter adapter;
    private List<Msg> msgList;
    private String myUsername;
    private String toUsername;
    private static final String TAG = "MsgActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        toUsername = intent.getStringExtra("toUsername");
        myUsername = ChatActivity.myUsername;
        contactUser.setText(toUsername);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initialMsg();
        initialRecview();
        flashData();
//        MsgFlashService msgFlashService = new MsgFlashService(this);
//
//        Intent intent1 = new Intent(MsgActivity.this,msgFlashService.getClass());
//        startService(intent1);
    }

    public void initialRecview() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerview.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList, myUsername);
        msgRecyclerview.setAdapter(adapter);
    }
//    private MsgFlashService.MyBinder myBinder;
//    private ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            myBinder = (MsgFlashService.MyBinder)service;
//            myBinder.refresh(MsgActivity.this);
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//    }
//    public void myCallbackMethod() {
//
//        flashData();
//    }
//    public void flashData(){
//        if (msgList == null) {
//            msgList = new ArrayList<Msg>();
//        }
//        Log.d(TAG, "flashData: ");
//        HttpUtil.queryMsg(addressQuery, new HttpCallbackListener() {
//            @Override
//            public void onFinish(String response) {
//                Log.d(TAG, "onFinish:falshdata " + response);
//                ArrayList<Msg> msgs = sortList(parseJSONWithJSONObject(response));
//                //如果消息个数增多 说明有新消息 更新数据
//
//                if (msgList.size() < msgs.size()) {
//                    for (int i = msgList.size(); i < msgs.size(); i++) {
//                        final Msg msg = msgs.get(i);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                msgList.add(msg);
//                                adapter.notifyItemInserted(msgList.size() - 1); // 当有新消息时，刷新ListView中的显示
//                                msgRecyclerview.scrollToPosition(msgList.size() - 1); // 将ListView定位到最后一行
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Log.d(TAG, "onError: " + e.getMessage());
//            }
//        }, myUsername, toUsername);
//    }
    public Notification getNotification(String title,String message,String toUsername){
        Intent intent = new Intent(MsgActivity.this,MsgActivity.class);
        intent.putExtra("toUsername",toUsername);
        PendingIntent pendingIntent = PendingIntent.getActivity(MsgActivity.this,0,intent,0);
        Notification notification = new NotificationCompat.Builder(MsgActivity.this)
                                     .setContentIntent(pendingIntent)
                                     .setSmallIcon(R.mipmap.ic_launcher)
                                     .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                                     .setContentTitle(title)
                                     .setAutoCancel(true)
                                     .setContentText(message).build();

              return notification;
    }
    public NotificationManager getManager(){
        return (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }
    public void flashData() {

        //用timertask来更新数据 也可以用服务
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                HttpUtil.queryMsg(addressQuery, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        Log.d(TAG, "onFinish: " + response);
                        ArrayList<Msg> msgs = sortList(parseJSONWithJSONObject(response));
                        //如果消息个数增多 说明有新消息 更新数据
                        if (msgList.size() < msgs.size()) {
                            for (int i = msgList.size(); i < msgs.size(); i++) {
                                final Msg msg = msgs.get(i);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        msgList.add(msg);
                                        adapter.notifyItemInserted(msgList.size() - 1); // 当有新消息时，刷新ListView中的显示
                                        msgRecyclerview.scrollToPosition(msgList.size() - 1); // 将ListView定位到最后一行
                                        Notification notification = getNotification("来自"+msg.getFromUser()+"新消息",msg.getContent(),msg.getFromUser());
                                        getManager().notify(1,notification);
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                }, myUsername, toUsername);
            }
        };
        Timer timer = new Timer();
        //延迟一秒后，每3秒一次运行
        timer.schedule(timerTask, 1000, 3000);
    }

    //初始化消息
    public void initialMsg() {
        if (msgList == null) {
            msgList = new ArrayList<Msg>();
        }
        HttpUtil.queryMsg(addressQuery, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d(TAG, "onFinish: " + response);
                ArrayList<Msg> msgs = sortList(parseJSONWithJSONObject(response));
                if(msgs.size() >0){
                    for (Msg msg : msgs) {
                        msgList.add(msg);
                        Log.d(TAG, "onFinish: " + msg);
                    }
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }
        }, myUsername, toUsername);
        Log.d(TAG, "initialMsg: " + msgList.size());
    }

    //用JSON格式化数据
    private ArrayList<Msg> parseJSONWithJSONObject(String jsonData) {
        String content;
        long time;
        String fromUser, toUser;
        Msg msg;
        ArrayList<Msg> msgs = new ArrayList<Msg>();
        try {
            if (jsonData != null && jsonData.length() != 0) {
                JSONArray jsonArray = new JSONArray(jsonData);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    content = jsonObject.getString("content");
                    time = jsonObject.getLong("time");
                    fromUser = jsonObject.getString("fromUser");
                    toUser = jsonObject.getString("toUser");
                    msg = new Msg(fromUser, toUser, content, time);
                    msgs.add(msg);
                }
            }
        } catch (Exception e) {
            Log.d("LJR", "parseJSONWithJSONObject: " + e.getMessage());
        }
        return msgs;
    }

    //根据消息的时间进行排序
    public ArrayList<Msg> sortList(ArrayList<Msg> source) {
        Collections.sort(source, new Comparator<Msg>() {
            @Override
            public int compare(Msg o1, Msg o2) {
                int res = (int) (o1.getTime() - o2.getTime());
                return res;
            }
        });
        return source;
    }

    //退出按钮
    @OnClick(R.id.backUserActivity)
    public void onBackUserActivityClicked() {
        Intent intent = new Intent(MsgActivity.this, ChatActivity.class);
        startActivity(intent);
        finish();
    }

    //发送按钮
    @OnClick(R.id.send)
    public void onSendClicked() {
        String content = inputText.getText().toString();
        if (TextUtils.isEmpty(content))
            return;
        long time = System.currentTimeMillis();
        final Msg msg = new Msg(myUsername, toUsername, content, time);
        HttpUtil.insertMsg(addressInsert, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean flag = Boolean.valueOf(response);
                //判断插入是否成功
                if (flag) {
                    Log.d(TAG, "onFinish: success insert");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            msgList.add(msg);
                            // 当有新消息时，刷新ListView中的显示
                            adapter.notifyItemInserted(msgList.size() - 1);
                            // 将recyclerview定位到最后一行
                            msgRecyclerview.scrollToPosition(msgList.size() - 1);
                            // 清空输入框中的内容
                            inputText.setText("");
                        }
                    });
                } else {
                    Log.d(TAG, "onFinish:fail insert ");
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }
        }, msg);
    }


}
