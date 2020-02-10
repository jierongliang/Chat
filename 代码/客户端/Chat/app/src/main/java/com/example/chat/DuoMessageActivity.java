package com.example.chat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//群发消息活动
public class DuoMessageActivity extends AppCompatActivity {


    @BindView(R.id.iv_duomessagetext)
    EditText ivDuomessagetext;
    @BindView(R.id.iv_duoButton)
    Button ivDuoButton;
    @BindView(R.id.iv_duomessageusername)
    EditText ivDuomessageusername;
    private static final String TAG = "DuoMessageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duo_message);
        ButterKnife.bind(this);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
    }
    //群发消息按钮
    @OnClick(R.id.iv_duoButton)
    public void onViewClicked() {
        String usernameSource = ivDuomessageusername.getText().toString();
        Log.d(TAG, "onViewClicked: " + usernameSource);
        //分割姓名
        List<String> usernames = Arrays.asList(usernameSource.split("\n"));
        for (String temp : usernames) {
            Log.d(TAG, "onViewClicked: " + temp);
        }
        Log.d(TAG, "onViewClicked: " + usernames.toArray());
        String duoMessage = ivDuomessagetext.getText().toString();
        String addressInsert = "http://192.168.0.4:8080/Bye/erke";
        final StringBuilder successname = new StringBuilder();
        final StringBuilder failname = new StringBuilder();
        //遍历列表 群发消息
        for (final String username : usernames) {
            long time = System.currentTimeMillis();
            Msg msg = new Msg(ChatActivity.myUsername, username, duoMessage, time);
            Log.d(TAG, "onViewClicked: "+username);
            HttpUtil.insertMsg(addressInsert, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    boolean flag = Boolean.valueOf(response);
                    if (flag) {
                        Log.d(TAG, "onViewClicked: "+username);
                        Log.d(TAG, "onFinish: success insert");
                        successname.append(username);

                    } else {
                        Log.d(TAG, "onViewClicked: "+username);
                        Log.d(TAG, "onFinish:fail insert ");
                        failname.append(username);
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.d(TAG, "onError: " + e.getMessage());
                }
            }, msg);
        }
        //发消息耗时 等待6秒
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onViewClicked: "+"成功名单:"+successname.toString()+"\n失败名单:"+failname.toString());
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(DuoMessageActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("成功名单:"+successname.toString()+"\n失败名单:"+failname.toString());
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                normalDialog.show();
            }
        });

    }
}
