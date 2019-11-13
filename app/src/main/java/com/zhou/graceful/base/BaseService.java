package com.zhou.graceful.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.zhou.zpermission.interfaces.IPermissionCallback;

public abstract class BaseService extends Service implements IPermissionCallback {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    //本来我还想试试Service能不能申请权限，
    // 然而，MMP，Service连权限申请的权力都没有，ActivityCompat.requestPermissions 第一个参数，必须是Activity

//    protected static final String TAG = "BaseService";
//
//    /**
//     * 申请权限
//     */
//    protected void requestPermission(String[] permissions, int requestCode) {
//        // 是否已经有了这些权限
//        if (PermissionUtil.hasSelfPermissions(getBaseContext(), permissions)) {
//            Log.e(TAG, "Activity，requestPermission: 所有权限都已经有了,无需申请");
//        } else {
//            // 开始请求权限
//            ActivityCompat.requestPermissions(getActivity(), permissions, requestCode);
//        }
//    }
//
//    /**
//     * 请求回馈
//     *
//     * @param requestCode
//     * @param permissions
//     * @param grantResults
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (PermissionUtil.verifyPermissions(grantResults)) {//检查是否都赋予了权限
//            granted(requestCode);
//        } else {
//            // 显示提示
//            if (PermissionUtil.shouldShowRequestPermissionRationale(getBaseContext(), permissions)) {
//                //shouldShowRequestPermissionRationale 这个方法就是检查，是不是权限被永久拒绝了。。。
//                如果用就拒绝，这里就返回false，只是第一次拒绝，那就返回true
//                // 取消权限
//                denied(requestCode);
//            } else {
//                // 权限被拒绝
//                deniedForever(requestCode);
//            }
//        }
//    }
}
