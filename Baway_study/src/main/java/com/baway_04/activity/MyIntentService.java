package com.baway_04.activity;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.baway_04.inter.IUpdateListener;
import com.baway_04.utils.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyIntentService extends IntentService {
    private static IUpdateListener iUpdate;
    private static final String TAG = "MyIntentService";

    public MyIntentService( ) {
        super(TAG);

    }

    public static void setUpdateListener(IUpdateListener iUpdateListener){

        iUpdate = iUpdateListener;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


//        String value  = intent.getExtras().getString("key");

        try {
            String result = getNetData();

            JSONObject jsonObject = new JSONObject(result);

            JSONArray jsonArr =jsonObject.optJSONArray("data");
            List<String>  foodList = new ArrayList<>();
             for (int x=0;x<jsonArr.length();x++){
                 JSONObject json = jsonArr.getJSONObject(x);
                 foodList.add(json.optString("pic"));
             }

             iUpdate.updateUI(  foodList);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getNetData() throws IOException {

        URL url  = new URL(Urls.url1);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        //设置连接时间，10秒
        urlConnection.setConnectTimeout(10 * 1000);
        urlConnection.setReadTimeout(10 * 1000);

        //数据编码格式，这里utf-8
        urlConnection.setRequestProperty("Charset", "utf-8");

        //设置返回结果的类型，这里是json
        urlConnection.setRequestProperty("accept", "application/json");

        int statusCode = urlConnection.getResponseCode();
        if (statusCode==200){
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //httpUrlConnection返回传输字节的长度，创建一个byte 数组。
            byte[] b = new byte[1024*4];
            int length;
            while ((length = bufferedInputStream.read(b)) > 0) {
                byteArrayOutputStream.write(b, 0, length);
            }
           return byteArrayOutputStream.toString("utf-8");
        }
        return "";

    }
}
