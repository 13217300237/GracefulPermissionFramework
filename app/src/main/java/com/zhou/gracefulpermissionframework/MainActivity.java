package com.zhou.gracefulpermissionframework;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.logging.Logger;

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
        requestPermission(permissions, PermissionRequestCodeConst.REQUEST_CODE_CONTACT);
    }

    //Ok，就是这么一个普通的Activity，如果我需要 读取内部存储的权限。
    private void getLocationPermission() {
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        requestPermission(permissions, PermissionRequestCodeConst.REQUEST_CODE_LOCATION);
    }

    @Override
    public void granted(int requestCode) {
        switch (requestCode) {
            case PermissionRequestCodeConst.REQUEST_CODE_LOCATION:
                Log.e(TAG, "定位权限已经授予");
                break;
            case PermissionRequestCodeConst.REQUEST_CODE_CONTACT:
                Log.d(TAG, "联系人权限已经授予");
                break;
            default:
                break;
        }
    }

    @Override
    public void denied(int requestCode) {
        switch (requestCode) {
            case PermissionRequestCodeConst.REQUEST_CODE_LOCATION:
                Log.e(TAG, "定位权限已经被拒");
                break;
            case PermissionRequestCodeConst.REQUEST_CODE_CONTACT:
                Log.d(TAG, "联系人权限已经被拒");
                break;
            default:
                break;
        }
    }

    @Override
    public void deniedForever(int requestCode) {
        switch (requestCode) {
            case PermissionRequestCodeConst.REQUEST_CODE_LOCATION:
                Log.e(TAG, "定位权限已经永久被拒");
                break;
            case PermissionRequestCodeConst.REQUEST_CODE_CONTACT:
                Log.d(TAG, "联系人权限已经永久被拒");
                break;
            default:
                break;
        }
    }
}
