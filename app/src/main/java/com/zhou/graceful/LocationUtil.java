package com.zhou.graceful;

import android.Manifest;
import android.util.Log;

import com.zhou.graceful.tools.ToastUtil;
import com.zhou.zpermission.annotation.PermissionDenied;
import com.zhou.zpermission.annotation.PermissionDeniedForever;
import com.zhou.zpermission.annotation.PermissionNeed;

/**
 * 普通类
 */
public class LocationUtil {

    @PermissionNeed(
            permissions = {Manifest.permission.BODY_SENSORS},
            requestCode = 0)
    public void getLocation() {
        Log.d("LocationUtilTag","普通Java类：权限已获得");
        ToastUtil.showToast("普通Java类：权限已获得");
    }

    /**
     * 这里写的要特别注意，denied方法，必须是带有一个int参数的方法，下面的也一样
     *
     * @param requestCode
     */
    @PermissionDenied
    private void denied(int requestCode) {
        Log.d("LocationUtilTag","普通Java类：权限被拒绝");
        ToastUtil.showToast("普通Java类：权限被拒绝");
    }

    @PermissionDeniedForever
    private void deniedForever(int requestCode) {
        Log.d("LocationUtilTag","普通Java类：权限被永久拒绝");
        ToastUtil.showToast("普通Java类：权限被永久拒绝");
    }
}
