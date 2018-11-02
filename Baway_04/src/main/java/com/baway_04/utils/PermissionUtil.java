package com.baway_04.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import pub.devrel.easypermissions.EasyPermissions;

public class PermissionUtil {

    public static String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    public static boolean checkPermission(Activity context) {
        return EasyPermissions.hasPermissions(context, needPermissions);
    }

    public static void requestPermission(Activity context,String tip,int requestCode) {
        EasyPermissions.requestPermissions(context, tip,requestCode,needPermissions);
    }
 }
