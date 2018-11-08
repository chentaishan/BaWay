package com.baway_04.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway_04.R;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecylerAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<String> foodList = new ArrayList<>();

    public RecylerAdapter(Context context,List<String> foodList){
        this.context = context;
        this.foodList = foodList;
    }
    @NonNull
    @Override
    public RecyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        RecyViewHolder recyViewHolder = (RecyViewHolder) viewHolder;
        x.image().bind(recyViewHolder.img,foodList.get(i));

        recyViewHolder.text.setText(foodList.get(i));
    }


    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class RecyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_img)
        ImageView img;
        @BindView(R.id.item_text)
        TextView text;

        public RecyViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);

        }

    }
}
