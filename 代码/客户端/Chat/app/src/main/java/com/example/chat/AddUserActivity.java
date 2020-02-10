package com.example.chat;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import org.litepal.LitePal;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//增加联系人活动
public class AddUserActivity extends AppCompatActivity {
    @BindView(R.id.iv_addText)
    EditText ivAddText;
    @BindView(R.id.iv_addButton)
    Button ivAddButton;
    @BindView(R.id.iv_addGroup)
    EditText ivAddGroup;
    @BindView(R.id.backUserActivity)
    ImageButton backUserActivity;
    private String addressUser = "http://192.168.0.4:8080/Bye/iu";
    private static final String TAG = "AddUserActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ButterKnife.bind(this);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
    }
    //退出按钮
    @OnClick(R.id.backUserActivity)
    public void onBackUserActivityClicked() {
        finish();
    }
    //增加联系人按钮
    @OnClick(R.id.iv_addButton)
    public void onIvAddButtonClicked() {
        {
            final String username = ivAddText.getText().toString().trim();
            final String groupname = ivAddGroup.getText().toString();
            Log.d(TAG, "onViewClicked:" + username);
            //先根据用户名查找用户是否存在
            HttpUtil.queryUserExists(addressUser, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    //如果用户存在
                    if (response.equals("exist")) {
                        //判断是否已经添加了该联系人
                        List<User> temp = LitePal.where("username = ? ", username).find(User.class);
                        if (temp.size() == 1) {
                            final AlertDialog.Builder normalDialog =
                                    new AlertDialog.Builder(AddUserActivity.this);
                            normalDialog.setTitle("提示");
                            //已经添加了 询问是修改组别还是维持不变
                            normalDialog.setMessage("已添加该用户,修改其组别还是不变");
                            normalDialog.setPositiveButton("修改",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put("groupname", groupname);
                                            LitePal.updateAll(User.class, contentValues, "username = ?", username);
                                        }
                                    });
                            normalDialog.setNegativeButton("不变", new DialogInterface.OnClickListener() {
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
                        //未添加 添加新用户
                        else {
                            User user = new User();
                            user.setGroupname(groupname);
                            user.setUsername(username);
                            user.save();
                            Log.d(TAG, "onFinish: exist");
                            final AlertDialog.Builder normalDialog =
                                    new AlertDialog.Builder(AddUserActivity.this);
                            normalDialog.setTitle("提示");
                            normalDialog.setMessage("添加成功");
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
                    }
                    //不存在该用户 提示
                    else if (response.equals("no")) {
                        Log.d(TAG, "onFinish: no");
                        final AlertDialog.Builder normalDialog =
                                new AlertDialog.Builder(AddUserActivity.this);
                        normalDialog.setTitle("提示");
                        normalDialog.setMessage("该用户不存在，添加失败");
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
                @Override
                public void onError(Exception e) {
                    Log.d(TAG, "onError: " + e.getMessage());
                }
            }, username);
        }
    }
}
