package com.zhou.gracefulpermissionframework;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocationPermission();
            }
        });

        findViewById(R.id.btn_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContactPermission();
            }
        });

    }

    //Ok，就是这么一个普通的Activity，如果我需要 读取内部存储的权限。
    private void getContactPermission() {
        String[] permissions = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};
        requestPermission(permissions, PermissionConst.REQUEST_CODE_CONTACT);
    }

    //Ok，就是这么一个普通的Activity，如果我需要 读取内部存储的权限。
    private void getLocationPermission() {
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        requestPermission(permissions, PermissionConst.REQUEST_CODE_LOCATION);
    }

    @Override
    public void granted(int requestCode) {
        if (PermissionConst.REQUEST_CODE_LOCATION == requestCode) {
            Log.e(TAG, "定位权限已经授予");
        } else if (PermissionConst.REQUEST_CODE_CONTACT == requestCode) {
            Log.e(TAG, "联系人权限已经授予");
        }
    }

    @Override
    public void denied(int requestCode) {
        if (PermissionConst.REQUEST_CODE_LOCATION == requestCode) {
            Log.e(TAG, "定位权限被拒绝了");
        } else if (PermissionConst.REQUEST_CODE_CONTACT == requestCode) {
            Log.e(TAG, "联系人权限被拒绝了");
        }
    }

    @Override
    public void deniedForever(int requestCode) {
        if (PermissionConst.REQUEST_CODE_LOCATION == requestCode) {
            Log.e(TAG, "定位权限被永久拒绝了");
        } else if (PermissionConst.REQUEST_CODE_CONTACT == requestCode) {
            Log.e(TAG, "联系人权限被永久拒绝了");
        }
    }

    //所有的技术手段，无论是编程语言还是，代码框架，都是为了需求服务的，脱离需求谈技术，都是纸老虎！
    //那么，现在来总结一下，我们app开发的过程中，用到权限申请的时候，会有哪些实际需求呢?
    // 举个实际例子，我们需要向系统申请 定位权限。
}
