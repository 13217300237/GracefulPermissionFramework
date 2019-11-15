package com.zhou.graceful;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zhou.graceful.tools.ToastUtil;
import com.zhou.zpermission.annotation.PermissionDenied;
import com.zhou.zpermission.annotation.PermissionDeniedForever;
import com.zhou.zpermission.annotation.PermissionNeed;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFragment();

        findViewById(R.id.btn_location).setOnClickListener(v -> getLocationPermission());
        findViewById(R.id.btn_java_clz).setOnClickListener(v -> new LocationUtil().getLocation());
        findViewById(R.id.btn_service).setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, MyService.class);
            startService(i);
        });

    }

    @PermissionNeed(
            permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
            requestCode = 1)
    private void getLocationPermission() {
        ToastUtil.showToast(this, "Activity:成功获得权限");
    }

    @PermissionDenied
    private void permissionDenied(int requestCode) {
        switch (requestCode) {
            case 1:
                ToastUtil.showToast(this, "Activity:权限被拒绝");
                break;
            default:
                break;
        }
    }

    @PermissionDeniedForever
    private void permissionDeniedForever(int requestCode) {
        switch (requestCode) {
            case 1:
                ToastUtil.showToast(this, "Activity:权限被永久拒绝");
                break;
            default:
                break;
        }
    }

    /**
     * 显示这个测试用的Fragment
     */
    private void showFragment() {

        MyFragment fragment = MyFragment.getInstance();

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contentFrame, fragment, "myFragment");
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

}
