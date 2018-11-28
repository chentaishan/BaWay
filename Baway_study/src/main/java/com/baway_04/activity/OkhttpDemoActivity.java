package com.baway_04.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TimeUtils;

import com.baway_04.R;
import com.baway_04.adapter.RecylerAdapter;
import com.baway_04.utils.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpDemoActivity extends Activity {

    private static final String TAG = "OkhttpDemoActivity";
    @BindView(R.id.recyle_view)
    RecyclerView recyclerView;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            String result = (String) msg.obj;

            try {

                JSONObject jsonObject = new JSONObject(result);

                JSONArray jsonArr =jsonObject.optJSONArray("data");
                List<String>  foodList = new ArrayList<>();
                for (int x=0;x<jsonArr.length();x++){
                    JSONObject json = jsonArr.getJSONObject(x);
                    foodList.add(json.optString("pic"));
                }

                setData2View(foodList);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okhttp_activity);

        ButterKnife.bind(this);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().get().url(Urls.url1).build();
        Call call =  okHttpClient.newCall(request);
        try {
            Response response = call.execute();


        } catch (IOException e) {
            e.printStackTrace();
        }
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: call="+(Looper.myLooper()==getMainLooper()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: call="+(Looper.myLooper()==getMainLooper()));
                Message msg  =handler.obtainMessage();
                msg.obj = response.body().string();
                handler.sendMessage(msg);
            }
        });

    }

    public   void setData2View(List<String> foodList){
        RecylerAdapter adapter = new RecylerAdapter(this,foodList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
//            recyclerView.setLayoutManager(new LinearLayoutManager(IntentServiceActivity.this,LinearLayoutManager.HORIZONTAL,false));//这里用线性显示 类似于listview
        recyclerView.setAdapter(adapter);
    }

}
