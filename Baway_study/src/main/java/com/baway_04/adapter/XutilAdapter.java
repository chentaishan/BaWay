package com.baway_04.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway_04.R;
import com.baway_04.utils.Urls;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XutilAdapter extends BaseAdapter {


    private Context context;

    public XutilAdapter(Context context) {
        this.context = context;

    }
    @Override
    public int getCount() {
        return Urls.classList.size();
    }

    @Override
    public Object getItem(int i) {
        return Urls.classList.get(i);
    }

    public String getKey(int i){

       HashMap<String ,String> map = (HashMap<String, String>) getItem(i);

       return map.keySet().iterator().next();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.listview_item,null,false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        HashMap<String,String> itemMap  = Urls.classList.get(i);

//        viewHolder.img
//        x.image().bind(viewHolder.img,text);
        String key = itemMap.keySet().iterator().next();
        viewHolder.title.setText(key);
        viewHolder.text.setText(itemMap.get(key));


        return view;
    }
    static class ViewHolder{
        @BindView(R.id.item_title)
        TextView title;
        @BindView(R.id.item_text)
        TextView text;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }


}
