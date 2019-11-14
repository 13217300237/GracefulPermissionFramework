package com.zhou.graceful;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.zhou.graceful.consts.PermissionRequestCodeConst;
import com.zhou.zpermission.annotation.PermissionDenied;
import com.zhou.zpermission.annotation.PermissionDeniedForever;
import com.zhou.zpermission.annotation.PermissionNeed;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "PermissionAspectTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_location).setOnClickListener(v -> getLocationPermission());
        findViewById(R.id.btn_contact).setOnClickListener(v -> getContactPermission());

//        test();
    }

    @PermissionNeed(permissions = {}, requestCode = PermissionRequestCodeConst.TEST)
    private void test() {
        Log.d(TAG, "");
    }

    @PermissionNeed(
            permissions = {Manifest.permission.WRITE_CONTACTS},
            requestCode = PermissionRequestCodeConst.REQUEST_CODE_CONTACT)
    private void getContactPermission() {
        Log.d(TAG, "getContactPermission");
    }

    @PermissionNeed(
            permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
            requestCode = PermissionRequestCodeConst.REQUEST_CODE_LOCATION)
    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission");
    }

    @PermissionDenied
    private void permissionDenied(int requestCode) {
        switch (requestCode) {
            case PermissionRequestCodeConst.REQUEST_CODE_CONTACT:
                Log.d(TAG, "联系人权限被拒绝");
                break;
            case PermissionRequestCodeConst.REQUEST_CODE_LOCATION:
                Log.d(TAG, "位置权限被拒绝");
                break;
            default:
                break;
        }
    }

    @PermissionDeniedForever
    private void permissionDeniedForever(int requestCode) {
        switch (requestCode) {
            case PermissionRequestCodeConst.REQUEST_CODE_CONTACT:
                Log.d(TAG, "权限联系人被永久拒绝");
                break;
            case PermissionRequestCodeConst.REQUEST_CODE_LOCATION:
                Log.d(TAG, "位置联系人被永久拒绝");
                break;
            default:
                break;
        }
    }
}
