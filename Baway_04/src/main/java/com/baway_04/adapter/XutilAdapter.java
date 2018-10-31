package com.baway_04.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway_04.R;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class XutilAdapter extends BaseAdapter {


    private Context context;

    public XutilAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view==null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.listview_item,null,false);
            viewHolder.img = view.findViewById(R.id.item_img);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String text = list.get(i);

//        viewHolder.img
        x.image().bind(viewHolder.img,text);


        return view;
    }
    class ViewHolder{
        ImageView img;
        TextView text;
    }

    public void setList(List<String> itemList) {
        if (!list.isEmpty()){
            list.clear();
        }

        list.addAll(itemList);
    }

    List<String >  list  = new ArrayList<>();
}
