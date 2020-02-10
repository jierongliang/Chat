package com.example.chat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import org.litepal.LitePal;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
public class ChatActivity extends AppCompatActivity {
    @BindView(R.id.iv_recyclerview)
    RecyclerView ivRecyclerview;
    @BindView(R.id.messagelogo)
    CircleImageView messagelogo;
    @BindView(R.id.userlogo)
    CircleImageView userlogo;
    @BindView(R.id.iv_duomessage)
    ImageView ivDuomessage;
    @BindView(R.id.exit)
    ImageView exit;
    private String uid;
    private String username;
    private static final String TAG = "ChatActivity";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static UserAdapter adapter;
    private static ArrayList<User> users;
    public static String myUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate:初始化 ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        sharedPreferences = getSharedPreferences("Chat", MODE_PRIVATE);
        uid = sharedPreferences.getString("uid", "");
        username = sharedPreferences.getString("username", "");
        myUsername = username;

        initialUser();
        initial();
    }
    //初始化recyclerview adapter
    public void initial(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ivRecyclerview.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider));
        ivRecyclerview.addItemDecoration(divider);
        adapter = new UserAdapter(users, uid, username);
        ivRecyclerview.setAdapter(adapter);
    }
    //初始化user联系人列表
    public void initialUser() {
        if (users == null) {
            users = new ArrayList<User>();
            LitePal.getDatabase();
            List<User> users1 = LitePal.findAll(User.class);
            for (User user : users1) {
                users.add(user);
                Log.d(TAG, "initialUser: " + user);
            }
        } else {
            //判断联系人列表有无变化
            List<User> users1 = LitePal.findAll(User.class);
            if (users1.size() != users.size()) {
                users = new ArrayList<User>();
                for (User user : users1) {
                    users.add(user);
                    Log.d(TAG, "initialUser: " + user);
                }
            }
        }
    }
    public static void flashData() {
        adapter.notifyDataSetChanged();
    }
    //群发消息按钮
    @OnClick(R.id.iv_duomessage)
    public void onIvDuomessageClicked() {
        Intent intent = new Intent(ChatActivity.this, DuoMessageActivity.class);
        startActivity(intent);
    }
    //联系人列表按钮
    @OnClick(R.id.userlogo)
    public void onUserlogoClicked() {
        Intent intent = new Intent(ChatActivity.this, ContactActivity.class);
        startActivity(intent);
    }
    //退出登录按钮
    @OnClick(R.id.exit)
    public void onExitClicked() {
        Log.d(TAG, "onExitClicked: 789");
        sharedPreferences = getSharedPreferences("Chat", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        boolean isRemember = sharedPreferences.getBoolean("remember_password",false);
        //如果记住密码 则不用清空数据
        if(isRemember){
            editor.putBoolean("isLogin",false);
        }
        //否则清空数据
        else{
            editor.putString("username","");
            editor.putString("password","");
            editor.putBoolean("isLogin",false);
        }
        editor.apply();
        Intent intent = new Intent(ChatActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
