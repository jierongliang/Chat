package com.example.chat;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
//联系人活动
public class ContactActivity extends AppCompatActivity {
    @BindView(R.id.iv_adduser)
    ImageView ivAdduser;
    @BindView(R.id.iv_minususer)
    ImageView ivMinususer;
    @BindView(R.id.expandablelistview)
    ExpandableListView expandablelistview;
    @BindView(R.id.messagelogo)
    CircleImageView messagelogo;
    private static ExpandableListViewAdapter adapter;
    private ArrayList<ContactGroup> contactGroups;
    private static final String TAG = "ContactActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        initial();
        initialListener();
        setData();
        setAdapter();
        adapter = new ExpandableListViewAdapter(this, contactGroups);
        expandablelistview.setAdapter(adapter);
    }

    //初始化组别和联系人
    private void initial() {
        if (contactGroups == null) {
            contactGroups = new ArrayList<>();
        }
        String[] groups = {"朋友", "家人", "亲戚", "爱人"};
        for (int i = 0; i < 4; i++) {
            ContactGroup contactGroup = new ContactGroup();
            contactGroup.setGroupname(groups[i]);
            // 二级列表中的数据
            List<User> source = LitePal.where("groupname = ? ", groups[i]).find(User.class);
            ArrayList<User> users = new ArrayList<User>();
            for (User user : source) {
                users.add(user);
            }
            contactGroup.setContacts(users);
            contactGroups.add(contactGroup);
        }
    }

    //刷新数据
    public static void flashData() {
        adapter.notifyDataSetChanged();
    }

    //绑定监听器
    private void initialListener() {
        //子项监听器
        expandablelistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //打开相应的消息页面
                User user = (User) adapter.getChild(groupPosition, childPosition);
                Intent intent = new Intent(ContactActivity.this, MsgActivity.class);
                intent.putExtra("toUsername", user.getUsername());
                intent.putExtra("fromUsername", ChatActivity.myUsername);
                ContactActivity.this.startActivity(intent);
                // Toast.makeText(ContactActivity.this, "Click " + groupPosition + childPosition, Toast.LENGTH_LONG).show();
                return true;
            }
        });
        //父项监听器
        expandablelistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //进行折叠与展开
                if (!expandablelistview.isGroupExpanded(groupPosition)) {
                    expandablelistview.expandGroup(groupPosition);
                } else {
                    expandablelistview.collapseGroup(groupPosition);
                }
                //Toast.makeText(ContactActivity.this, "Click " + groupPosition, Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    //初始化adapter
    private void setAdapter() {
        if (adapter == null) {
            adapter = new ExpandableListViewAdapter(this, contactGroups);
            expandablelistview.setAdapter(adapter);
        } else {
            adapter.flashData(contactGroups);
        }
    }

    private void setData() {
        if (contactGroups == null) {
            contactGroups = new ArrayList<>();
        }
    }

    //增加联系人按钮
    @OnClick(R.id.iv_adduser)
    public void onIvAdduserClicked() {
        Intent intent = new Intent(ContactActivity.this, AddUserActivity.class);
        startActivityForResult(intent, 1);
    }

    //删除联系人按钮
    @OnClick(R.id.iv_minususer)
    public void onIvMinususerClicked() {
        Intent intent = new Intent(ContactActivity.this, MinusUserActivity.class);
        startActivity(intent);
    }

    //切换页面
    @OnClick(R.id.messagelogo)
    public void onMessagelogoClicked() {
        Intent intent = new Intent(ContactActivity.this, ChatActivity.class);
        startActivity(intent);
    }

    //得到相应的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 1:
                //添加成功 更新相应的 group 和 user 并刷新数据 更新显示
                if (resultCode == RESULT_OK) {
                    String username = data.getStringExtra("username");
                    String groupname = data.getStringExtra("groupname");
                    ArrayList<User> contacts = new ArrayList<User>();
                    for (ContactGroup contactGroup : contactGroups) {
                        String groupnameA = contactGroup.getGroupname();
                        //找到相应的组别
                        if (groupname.equals(groupnameA)) {
                            contacts = contactGroup.getContacts();
                            break;
                        }
                    }
                    User user = new User();
                    user.setUsername(username);
                    user.setGroupname(groupname);
                    //在相应的组别增加该联系人
                    contacts.add(user);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                    user.save();
                }
        }
    }
}
