package com.zhou.zpermission.aspect;

import android.content.Context;
import android.util.Log;

import com.zhou.zpermission.annotation.PermissionDenied;
import com.zhou.zpermission.annotation.PermissionDeniedForever;
import com.zhou.zpermission.annotation.PermissionNeed;
import com.zhou.zpermission.interfaces.IPermissionCallback;
import com.zhou.zpermission.utils.ApplicationUtil;
import com.zhou.zpermission.utils.PermissionUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 定义一个AspectJ切面类
 */
@Aspect
public class PermissionAspect {

    private static final String TAG = "PermissionAspectTag";

    private final String pointcutExpression2
            = "execution(@com.zhou.zpermission.annotation.PermissionNeed * *(..)) && @annotation(permissionNeed)";

    @Pointcut(value = pointcutExpression2, argNames = "permissionNeed")
    public void requestPermission(PermissionNeed permissionNeed) {
        Log.d(TAG, "pointCut 定义切入点");
    }

    @Around("requestPermission(permissionNeed)")
    public void doPermission(final ProceedingJoinPoint joinPoint, PermissionNeed permissionNeed) {
        PermissionAspectActivity.startActivity(getContext(joinPoint), permissionNeed.permissions(), permissionNeed.requestCode(), new IPermissionCallback() {
            @Override
            public void granted(int requestCode) {
                // 如果授予，那么执行joinPoint原方法体
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void denied(int requestCode) {//这里的getThis也是要给梗
                PermissionUtil.invokeAnnotation(joinPoint.getThis(), PermissionDenied.class, requestCode);
            }

            @Override
            public void deniedForever(int requestCode) {
                PermissionUtil.invokeAnnotation(joinPoint.getThis(), PermissionDeniedForever.class, requestCode);
            }
        });
    }

    private Context getContext(final ProceedingJoinPoint joinPoint) {
        final Object obj = joinPoint.getThis();
        if (obj instanceof Context) {// 如果切入点是一个类？那么这个类的对象是不是context？
            return (Context) obj;
        } else {// 如果切入点不是Context的子类呢？ //jointPoint.getThis，其实是得到切入点所在类的对象
            Object[] args = joinPoint.getArgs();
            if (args.length > 0) {//
                if (args[0] instanceof Context) {//看看第一个参数是不是context
                    return (Context) args[0];
                } else {
                    return ApplicationUtil.getApplication();//如果不是，那么就只好hook反射了
                }
            } else {
                return ApplicationUtil.getApplication();//如果不是，那么就只好hook反射了
            }
        }
    }
}
