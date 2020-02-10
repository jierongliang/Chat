package com.example.chat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//删除联系人活动
public class MinusUserActivity extends AppCompatActivity {

    @BindView(R.id.iv_minusText)
    EditText ivMinusText;
    @BindView(R.id.iv_minusButton)
    Button ivMinusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minus_user);
        ButterKnife.bind(this);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
    }


    @OnClick(R.id.backUserActivity)
    public void onBackUserActivityClicked() {
        finish();
    }

    @OnClick(R.id.iv_minusButton)
    public void onIvMinusButtonClicked() {
        String username = ivMinusText.getText().toString();
        LitePal.getDatabase();
        List<User> users = LitePal.where("username = ?",username).find(User.class);
        if(users.size() == 1){
            LitePal.deleteAll(User.class,"username = ?",username);
            final AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(MinusUserActivity.this);
            normalDialog.setTitle("提示");
            normalDialog.setMessage("删除成功");
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ChatActivity.flashData();
                            ContactActivity.flashData();
                        }
                    });
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    normalDialog.show();
                }
            });
        }
        else {
            final AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(MinusUserActivity.this);
            normalDialog.setTitle("提示");
            normalDialog.setMessage("删除失败，用户不存在");
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
}
