package com.baway_04.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.baway_04.R;
import com.baway_04.activity.MainActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ExPandAdapter extends BaseExpandableListAdapter {
    //        获取分组的个数
//    public String[] groupStrings = {"西游记", "水浒传", "三国演义", "红楼梦"};
//    public String[][] childStrings = {
//            {"唐三藏", "孙悟空", "猪八戒", "沙和尚"},
//            {"宋江", "林冲", "李逵", "鲁智深"},
//            {"曹操", "刘备", "孙权", "诸葛亮", "周瑜"},
//            {"贾宝玉", "林黛玉", "薛宝钗", "王熙凤"}
//    };

    HashMap<Integer, List<String>> dataList = new HashMap<Integer, List<String>>();
    List<String>  groupList = new ArrayList();
    private Context context;

    // TODO 维护group 的状态

    List<Boolean>  groupStatus = new ArrayList();
    // TODO 维护child 的状态
    HashMap<Integer, List<Boolean>> childStatus = new HashMap<Integer, List<Boolean>>();


    public ExPandAdapter(Context context) {
        this.context = context;


        groupList.add("西游记");
        groupList.add("水浒传");
        groupStatus.add(false);
        groupStatus.add(false);

        for (int x = 0; x < groupList.size(); x++) {
            if (x == 0) {
                List<String> list1 = new ArrayList<>();
                List<Boolean> listStatus1 = new ArrayList<>();
                list1.add("唐三藏");
                list1.add("孙悟空");
                list1.add("猪八戒");
                listStatus1.add(false);
                listStatus1.add(false);
                listStatus1.add(false);

                dataList.put(x, list1);
               childStatus.put(x,listStatus1);
            } else {
                List<String> list2 = new ArrayList<>();
                List<Boolean> listStatus2 = new ArrayList<>();
                list2.add("宋江");
                list2.add("林冲");
                list2.add("李逵");

                listStatus2.add(false);
                listStatus2.add(false);
                listStatus2.add(false);

                dataList.put(x, list2);
                childStatus.put(x,listStatus2);
            }

        }


    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    //        获取指定分组中的子选项的个数
    @Override
    public int getChildrenCount(int groupPosition) {


        //  pos  = 0
        return dataList.get(groupPosition).size();
    }

    //        获取指定的分组数据
    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    //        获取指定分组中的指定子选项数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataList.get(groupPosition).get(childPosition);
    }

    //        获取指定分组的ID, 这个ID必须是唯一的
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //        获取子选项的ID, 这个ID必须是唯一的
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //        分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们。
    @Override
    public boolean hasStableIds() {
        return true;
    }

    //        获取显示指定分组的视图
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_expand_group, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
            groupViewHolder.groupCheck = (CheckBox) convertView.findViewById(R.id.group_checkbox);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        String groupTitle  = groupList.get(groupPosition);
        groupViewHolder.tvTitle.setText(groupTitle);

        if (groupStatus.get(groupPosition)){
            groupViewHolder.groupCheck.setChecked(true);
        }else{
            groupViewHolder.groupCheck.setChecked(false);
        }


        groupViewHolder.groupCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupStatus.get(groupPosition)){
                    groupStatus.set(groupPosition,false);
                    groupViewHolder.groupCheck.setChecked(false);

                    updateChildStaus(groupPosition,false);
                }else{
                    groupStatus.set(groupPosition,true);
                    groupViewHolder.groupCheck.setChecked(true);

                    updateChildStaus(groupPosition,true);
                }
            }
        });

        return convertView;
    }

    /**
     *
     * @param groupPos
     * @param status  要执行的动作
     */
    private void updateChildStaus(int groupPos,boolean status){
        List<Boolean> childList = childStatus.get(groupPos);
        for (int x=0;x<childList.size();x++){
            childList.set(x,status);
        }
        childStatus.put(groupPos,childList);

        notifyDataSetChanged();
    }
    //        获取显示指定分组中的指定子选项的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_expand_child, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_child);
            childViewHolder.childCheck = (CheckBox) convertView.findViewById(R.id.child_checkbox);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        if (childStatus.get(groupPosition).get(childPosition)){
            // 当 子选项 取消选中，改变UI的选中状态，同时改变状态集合里的数据

            childViewHolder.childCheck.setChecked(true);
            childStatus.get(groupPosition).set(childPosition,true);

        }else{
            childViewHolder.childCheck.setChecked(false);
            childStatus.get(groupPosition).set(childPosition,false);

        }





        childViewHolder.tvTitle.setText(dataList.get( groupPosition).get( childPosition));
        return convertView;
    }

    //        指定位置上的子元素是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public int getDataCount() {
        return  groupList.size();
    }

    static class GroupViewHolder {
        TextView tvTitle;
        CheckBox groupCheck;
    }

    static class ChildViewHolder {
        TextView tvTitle;
        CheckBox childCheck;
    }

}
