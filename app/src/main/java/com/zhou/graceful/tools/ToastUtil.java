package com.zhou.graceful.tools;

import android.widget.Toast;

import com.zhou.zpermission.utils.ApplicationUtil;

public class ToastUtil {
    public static void showToast(String msg) {
        Toast.makeText(ApplicationUtil.getApplication(), msg, Toast.LENGTH_SHORT).show();
    }
}
