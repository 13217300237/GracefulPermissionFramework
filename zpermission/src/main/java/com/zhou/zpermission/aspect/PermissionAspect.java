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

    /**
     * 接入点，表示当前这个切面，应该切入到 class字节码的哪些位置
     * <p>
     * 有两个参数，value为 切入点表达式 语法为：
     * <p>
     * XX
     * <p>
     * AspectJ的底层是？ ASM，改变了编译之后的字节码，强行插入了一些函数
     */

    private static final String TAG = "PermissionAspectTag";

    /**
     *
     */
    private final String pointcutExpression
            = "execution(@com.zhou.zpermission.annotation.PermissionNeed private void com.zhou.graceful.MainActivity.test(..)) && @annotation(permissionNeed)";

    //但是事实上我不需要这么精确，我只需要找到所有使用了@PermissionNeed这个注解的地方作为切入点,其他的我一概不关心
    private final String pointcutExpression2
            = "execution(@com.zhou.zpermission.annotation.PermissionNeed * *(..)) && @annotation(permissionNeed)";
    //TODO OK，对于这个pointcut表达式的写法，我觉得这个有必要深究一下，因为我发现我实验结果和leo老师所说所写的有出入

    /**
     * AOP编程，切面的类型分为3类：
     * 1. 函数（方法） 的被调用，  执行时，包括构造函数的被调用和执行时
     * 2. 成员变量的get和set
     * 3. 静态代码块执行时
     * 4. 当这个类报出异常时
     *
     * 确定了切入点，那么再决定切入的策略，分为：
     * 1. 切入点之前 @Before
     * 2. 切入点之后 @After
     * 3. 覆盖切入点 @Around
     * 4. 切入点方法return之后 @AfterReturning
     * 5. 切入点抛出异常之后 @AfterThrowing
     *
     * 所以一个切面类，有两个概念，切入点和切入策略，我们常用的切入点和切入策略是如下:
     * 切入点呢，使用一个方法执行时 executing(xxxxxxxxxx)
     * 切入策略，则采用Around() 保留执行原逻辑的可能性，又可以插入新的逻辑
     */


    /**
     * @argNames When compiling without debug info, or when interpreting pointcuts at runtime,
     * the names of any arguments used in the pointcut are not available.
     * Under these circumstances only, it is necessary to provide the arg names in
     * the annotation - these MUST duplicate the names used in the annotated method.
     * Format is a simple comma-separated list.
     * <p>
     * 当不包含debug信息编译，或者 在运行时解释切入点的时候， 使用到的切入点的名字是无效的。
     * 在这种情况下，有必要提供在注解中提供参数名，这些参数名必须 与注解方法中的名字一样，格式就是简单的逗号分隔列表
     * 没看懂是干啥的。
     */
    @Pointcut(value = pointcutExpression2, argNames = "permissionNeed")
    // 这是一种完整写法，详细规定了，切入点是在哪一个注解（全限定名），哪一个 方法（全限定名），并且如果切入点的方法使用到了注解的值，还要&&跟上切入点方法的形参名字
    public void requestPermission(PermissionNeed permissionNeed) {
        //Ok,切入点就是这么一个方法
        Log.d(TAG, "pointCut 定义切入点");
    }

    /**
     * 切入策略 Around 围绕切入点，替代原来的代码，但是依然保留执行源代码的可能性
     * <p>
     * ？？？这个语法一点都不紧凑，全手工码代码?
     */
    @Around("requestPermission(permissionNeed)")
    public void doPermission(final ProceedingJoinPoint joinPoint, PermissionNeed permissionNeed) {
        //策略，当切入点函数被执行的时候，启动一个透明Activity（取消Activity切换动画）。让这个Activity替代我们进行权限申请，并且处理回调结果
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
        //这里有4个参数，需要一个一个来获得

    }


    // 由于启动Activity需要context，这里给要给getContext的方法
    private Context getContext(final ProceedingJoinPoint joinPoint) {
        final Object obj = joinPoint.getThis();
        if (obj instanceof Context) {// 如果切入点是一个类？那么这个类的对象是不是context？
            return (Context) obj;
        } else {// 如果切入点不是Context的子类呢？ //TODO 这里也是需要深究的地方，这个joinPoint到底是个神马玩意
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
