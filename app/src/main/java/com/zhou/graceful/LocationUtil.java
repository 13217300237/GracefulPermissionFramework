package com.zhou.graceful;

import android.Manifest;
import android.util.Log;

import com.zhou.graceful.consts.PermissionRequestCodeConst;
import com.zhou.zpermission.annotation.PermissionDenied;
import com.zhou.zpermission.annotation.PermissionDeniedForever;
import com.zhou.zpermission.annotation.PermissionNeed;

public class LocationUtil {

    private String TAG = "LocationUtil";

    @PermissionNeed(
            permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
            requestCode = PermissionRequestCodeConst.REQUEST_CODE_LOCATION)
    public void getLocation() {
        Log.e(TAG, "申请位置权限之后，我要获取经纬度");
    }

    @PermissionDenied
    public void denied(int requestCode) {
        Log.e(TAG, "用户不给啊");
    }

    @PermissionDeniedForever
    public void deniedForever(int requestCode) {
        Log.e(TAG, "用户永久拒绝");
    }
}
