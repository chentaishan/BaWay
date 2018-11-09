package com.baway_04.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baway_04.R;
import com.baway_04.utils.Urls;
import com.baway_04.adapter.XutilAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.LOCATION_HARDWARE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.SEND_SMS;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    @ViewInject(R.id.listview)
    private ListView listView;

    private static final String TAG = "MainActivity";
    private List<String> itemList = new ArrayList<>();
    private XutilAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x.view().inject(this);
        methodRequiresTwoPermission();



//        initXutilsQuest();
    }
    @AfterPermissionGranted(1010)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_HARDWARE,READ_PHONE_STATE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
            initView();
            LogUtil.d("Already have permission, do the thing");
        } else {
            // Do not have permissions, request them now
            LogUtil.d("Do not have permissions, request them now");
            EasyPermissions.requestPermissions(this, "camera_and_send_sms", 1010, perms);
        }
    }

    public void initView(){
        adapter = new XutilAdapter(this);
//        adapter.setList(itemList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String className =   adapter.getKey(i);
                Intent intent = new Intent();
                String packageName = "com.baway_04.activity.";
                intent.setClassName(MainActivity.this,packageName+className);
                startActivity(intent);

            }
        });
    }

    public void initXutilsQuest(){
        RequestParams requestParams = new RequestParams(Urls.url1);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                LogUtil.e(result+"-is-mainThread="+(Looper.getMainLooper()==Looper.myLooper()));

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray objArr = jsonObject.optJSONArray("data");

                    for (int x=0;x<objArr.length();x++){
                        JSONObject object = objArr.getJSONObject(x);

                        itemList.add(object.optString("pic"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e(cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished");
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private boolean isFrist = true;
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        // ...
        if (isFrist){
            initView();
            isFrist = false;
        }
        LogUtil.d("Some permissions have been granted=" + requestCode);
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
        LogUtil.d("Some permissions have been denied=" + requestCode);
    }
}
