package com.example.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    // 定义一个Context
    private Context context;
    // 定义一个LayoutInflater
    private LayoutInflater inflater;
    // 定义一个List来保存列表数据
    private ArrayList<ContactGroup> grouplist = new ArrayList<ContactGroup>();

    public ExpandableListViewAdapter(Context context, ArrayList<ContactGroup> grouplist) {
        this.context = context;
        this.grouplist = grouplist;
        this.inflater = LayoutInflater.from(context);
    }

    // 刷新数据
    public void flashData(ArrayList<ContactGroup> datas) {
        this.grouplist = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return grouplist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return grouplist.get(groupPosition).getContacts().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return grouplist.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return grouplist.get(groupPosition).getContacts().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    class ChildViewHolder {
        TextView groupusername;
    }
    class FatherViewHolder{
        TextView grouplistname;
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
       FatherViewHolder fatherViewHolder ;
       View view;
       ContactGroup contactGroup = (ContactGroup) getGroup(groupPosition);
       if(convertView == null){
           view = LayoutInflater.from(context).inflate(R.layout.father_contact_item,parent,false);//
           fatherViewHolder = new FatherViewHolder();

           fatherViewHolder.grouplistname = (TextView) view.findViewById(R.id.father_group_list_name);
           view.setTag(fatherViewHolder);
       }
       else{
           view = convertView;
           fatherViewHolder = (FatherViewHolder)view.getTag();
       }
       fatherViewHolder.grouplistname.setText(contactGroup.getGroupname());

       return view;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        View view;
        User user = (User)getChild(groupPosition,childPosition);
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.child_contact_item,parent,false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.groupusername = (TextView)view.findViewById(R.id.child_contact_name);
            view.setTag(childViewHolder);
        }
        else{
             view = convertView;
             childViewHolder = (ChildViewHolder)view.getTag();
        }
        childViewHolder.groupusername.setText(user.getUsername());
        return view;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
