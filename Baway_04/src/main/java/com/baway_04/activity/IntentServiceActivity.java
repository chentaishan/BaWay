package com.baway_04.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.baway_04.R;
import com.baway_04.adapter.RecylerAdapter;
import com.baway_04.inter.IUpdateListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntentServiceActivity extends AppCompatActivity implements IUpdateListener {
    @BindView(R.id.recyle_view)
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intentservice_activity);

        ButterKnife.bind(this);

        Intent intent = new Intent(this,MyIntentService.class);
//        intent.putExtra("key","value");
        MyIntentService.setUpdateListener(this);
        startService(intent);


    }

    @Override
    public void updateUI(List<String> foodList) {

        Message msg = handler.obtainMessage();
        msg.obj = foodList;
        handler.sendMessage(msg);

    }

    @SuppressLint("HandlerLeak")
     Handler handler = new Handler(){
        public void handleMessage(Message msg ){
            List<String> foodList  = (List<String>) msg.obj;

            RecylerAdapter adapter = new RecylerAdapter(IntentServiceActivity.this,foodList);
            recyclerView.setLayoutManager(new LinearLayoutManager(IntentServiceActivity.this));//这里用线性显示 类似于listview
            recyclerView.setAdapter(adapter);
        }
    };

}
