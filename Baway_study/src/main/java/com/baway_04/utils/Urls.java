package com.baway_04.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Urls {

    public static final String  url1="https://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1";
    public static final List<HashMap<String,String>> classList = new ArrayList<>();

    static {
        HashMap<String,String>  className = new HashMap<>();
        className.put("BdMapbdActivity","实现定位和marker标记");
        classList.add(className);
        className = new HashMap<>();
        className.put("GaodeMapActivity","实现定位和marker标记");
        classList.add(className);

        className = new HashMap<>();
        className.put("IntentServiceActivity","测试intentService");
        classList.add(className);

        className = new HashMap<>();
        className.put("OkhttpDemoActivity","测试okhttp");
        classList.add(className);


        className = new HashMap<>();
        className.put("ExpandActivity","扩展的双级list");
        classList.add(className);

        className = new HashMap<>();
        className.put("MVVMActivity","MVVM demo");
        classList.add(className);
    }
}
