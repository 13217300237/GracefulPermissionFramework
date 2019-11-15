package com.zhou.graceful;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zhou.graceful.tools.ToastUtil;
import com.zhou.zpermission.annotation.PermissionDenied;
import com.zhou.zpermission.annotation.PermissionDeniedForever;
import com.zhou.zpermission.annotation.PermissionNeed;

public class MyFragment extends Fragment {

    public static MyFragment getInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment, container, false);
        inflate.findViewById(R.id.btn_request_permission).setOnClickListener(v -> getLocation());
        return inflate;
    }

    @PermissionNeed(
            permissions = {Manifest.permission.READ_CALENDAR},
            requestCode = 2)
    private void getLocation() {
        ToastUtil.showToast(getActivity(), "Fragment:权限已申请成功");
    }

    /**
     * 这里写的要特别注意，denied方法，必须是带有一个int参数的方法，下面的也一样
     *
     * @param requestCode
     */
    @PermissionDenied
    private void denied(int requestCode) {
        ToastUtil.showToast(getActivity(), "Fragment:权限被拒绝");
    }

    @PermissionDeniedForever
    private void deniedForever(int requestCode) {
        ToastUtil.showToast(getActivity(), "Fragment:权限被永久拒绝");
    }
}
