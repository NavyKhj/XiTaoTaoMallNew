package com.shangcheng.common_module.aspectj;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.shangcheng.common_module.base.BaseActivity;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.utils.ConstantPool;
import com.shangcheng.common_module.utils.T;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 切面编程解析类
 */
@SuppressWarnings("ALL")
@Aspect
public class AspectJCheckLogin {
    private static final String TAG = "tag00";

    @Pointcut("execution(@com.shangcheng.common_module.aspectj.CheckLogin  * *(..))")
    public void executionCheckLogin() {

    }

    @Around("executionCheckLogin()")
    public Object aroundAspectJ(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        CheckLogin checkLogin = methodSignature.getMethod().getAnnotation(CheckLogin.class);
        Object o = null;
        if (checkLogin != null) {
            Object object = joinPoint.getThis();
            Context context;
            if (object instanceof Fragment) {
                context = ((Fragment) object).getActivity();
            } else if (object instanceof Context) {
                context = (Context) object;
            } else {
                context = app.getApplication();
            }
            if (app.init().getUserInfo() == null || !app.init().getUserInfo().isLogin()) {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(ConstantPool.Action.ACTION_LOGIN);
                context.startActivity(intent);
            } else {
                o = joinPoint.proceed();
            }
        }
        return o;
    }
}
