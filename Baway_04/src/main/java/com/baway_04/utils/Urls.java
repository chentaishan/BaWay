package com.baway_04.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Urls {

    public static final String  url1="http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1";


    public static final HashMap<String,String>  className = new HashMap<>();
    public static final List<HashMap<String,String>> classList = new ArrayList<>();

    static {
        className.put("BdMapActivity","实现定位和marker标记");
        classList.add(className);
        className.put("GaodeMapActivity","实现定位和marker标记");
        classList.add(className);
    }
}
