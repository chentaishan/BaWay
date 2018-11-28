package com.baway_04;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.squareup.leakcanary.LeakCanary;

import org.xutils.x;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
        x.Ext.setDebug(true);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

//        setApiKey(String apiKey);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        LeakCanary.install(this);
    }

    public int compareVerNum(double a,double b){
        int blength = (b+"").length();
        int alength = (a+"").length();
        int size = blength-alength;
        if (size>0){
            String as=null;
            int [] time = new int[size];
            for (int t : time){
                as = a+"0";
            }
            a = Double.valueOf(as);
        }else if (size<0){
            String bs=null;
            int [] time = new int[size];
            for (int t : time){
                bs = a+"0";
            }
            b= Double.valueOf(bs);
        }

        if (a>b) {
            return -1;
        }else if(a==b){
            return 0;
        }else if(a<b){
            return -1;
        }

        return -3;
    }
}
