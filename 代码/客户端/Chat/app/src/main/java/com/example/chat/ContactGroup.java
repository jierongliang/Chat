package com.example.chat;

import java.util.ArrayList;
// 父项
// 包含 组名 和 联系人列表
public class ContactGroup {
    private String groupname;
    private ArrayList<User> contacts;

    public ContactGroup(String groupname, ArrayList<User> contacts) {
        this.groupname = groupname;
        this.contacts = contacts;
    }

    public ContactGroup() {
        contacts = new ArrayList<User>();
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public ArrayList<User> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<User> contacts) {
        this.contacts = contacts;
    }
    public void addGroupMember(User user){
        contacts.add(user);
    }
    public void removeGroupMember(User user){
        contacts.remove(user);
    }
}
