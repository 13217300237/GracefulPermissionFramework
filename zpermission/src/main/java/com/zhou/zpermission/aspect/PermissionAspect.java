package com.zhou.zpermission.aspect;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 定义一个AspectJ切面类
 */
@Aspect
public class PermissionAspect {

    @Pointcut()
    public void requestPermission() {

    }

    @Around("")
    public void doPermission() {

    }
}
