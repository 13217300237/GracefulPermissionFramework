package com.zhou.zpermission.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被此注解修饰的方法，会在方法执行之前去申请相应的权限，只有用户授予权限，被修饰的方法体才会执行
 */
@Target(ElementType.METHOD)//此注解用于修饰方法
@Retention(RetentionPolicy.RUNTIME)//注解保留到运行时，因为可能会需要反射执行方法（上面说了修饰的是方法！）
public @interface PermissionNeed {

    String[] permissions();//需要申请的权限,支持多个，需要传入String数组

    int requestCode() default 0;//此次申请权限之后的返回码
}
