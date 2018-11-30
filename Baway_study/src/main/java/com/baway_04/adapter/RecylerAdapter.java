package com.baway_04.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.BoringLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway_04.R;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecylerAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<String> foodList = new ArrayList<>();
    private List<Boolean> foodListStatus = new ArrayList<>();
    HashMap<Integer,Boolean> statusMap = new HashMap<>();

    private static final String TAG = "RecylerAdapter";
    public RecylerAdapter(Context context,List<String> foodList){
        this.context = context;
        this.foodList = foodList;

        for (int x=0;x<foodList.size();x++){
            foodListStatus.add(false);
        }

    }

    public void refreshData(List<String> newFoodList ){
        //清除原来的数据集合
        if (foodList!=null){
            foodList.clear();
            this.foodList = newFoodList;
        }
        //清除原来的数据状态集合
        if (foodListStatus!=null){
            foodListStatus.clear();
        }
        //重新给状态集合赋值
        for (int x=0;x<foodList.size();x++){
            foodListStatus.add(false);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        RecyViewHolder recyViewHolder = (RecyViewHolder) viewHolder;
        x.image().bind(recyViewHolder.img, foodList.get(i), new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
                Log.d(TAG, "onSuccess: ");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Log.d(TAG, "onError: ");
            }

            @Override
            public void onCancelled(CancelledException cex) {

                Log.d(TAG, "onCancelled: ");
            }

            @Override
            public void onFinished() {

                Log.d(TAG, "onFinished: ");
            }
        });
//        x.image().bind();

        if (foodListStatus.get(i)){
            recyViewHolder.checkBox.setChecked(true);
        }else{
            recyViewHolder.checkBox.setChecked(false);
        }
        Log.d(TAG, "onBindViewHolder: status="+foodListStatus.get(i)+"--index="+i);
        recyViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foodListStatus.get(i)){
                    foodListStatus.set(i,false);
                }else{
                    foodListStatus.set(i,true);
                }
                Log.d(TAG, "onCheckedChanged: --index="+i);
            }
        });
//        recyViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    foodListStatus.set(i,isChecked);
//                Log.d(TAG, "onCheckedChanged: status="+isChecked+"--index="+i);
//            }
//        });
        recyViewHolder.text.setText(foodList.get(i));
    }


    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public void setSelectAll(boolean isAllSelected) {
        for (int y =0;y<foodListStatus.size();y++){
            if (isAllSelected){
                foodListStatus.set(y,false);
            }else{
                foodListStatus.set(y,true);
            }
        }
        notifyDataSetChanged();
    }

    public static class RecyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_img)
        ImageView img;
        @BindView(R.id.item_text)
        TextView text;
        @BindView(R.id.checkBox)
        CheckBox checkBox;

        public RecyViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);

        }

    }
}
