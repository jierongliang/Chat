package com.example.chat;

import org.litepal.crud.LitePalSupport;
//用户实体类
public class User extends LitePalSupport {
    private String uid;
    private String username;
    private String groupname;
    public User() {
    }

    public User(String uid, String username,String groupname) {
        this.uid = uid;
        this.username = username;
        this.groupname = groupname;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
