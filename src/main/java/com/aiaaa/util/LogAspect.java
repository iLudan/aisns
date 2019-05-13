package com.aiaaa.util;

import com.aiaaa.annotation.LuxLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Enumeration;

@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut(value = "execution(* com.aiaaa.web.controller..*.*(..))")
    public void cut(){}

    @Before(value = "LogAspect.cut()")
    public void doBefore(JoinPoint joinPoint){

        //Object proceed = joinpoint.proceed();
        if (logger.isInfoEnabled()){
            logger.info("before " + joinPoint);
        }

        //获取HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录请求参数
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            logger.info("name:{},value:{}", name, request.getParameter(name));
        }

    }
    //@Around("LogAspect.cut()")
    public void around(ProceedingJoinPoint jp) throws Throwable
    {
        System.out.println("obj->"+jp.getTarget());
        System.out.println("do around->"+jp.getSignature().getName());
        jp.proceed();
        System.out.println("end around->"+jp.getSignature().getName());
    }

    public void doAround(ProceedingJoinPoint joinPoint){
        System.out.println("==========开始执行controller环绕通知===============");
        long start = System.currentTimeMillis();
        try {
            joinPoint.proceed();
            long end = System.currentTimeMillis();
            if(logger.isInfoEnabled()){
                logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
            }
            System.out.println("==========结束执行controller环绕通知===============");
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            if(logger.isInfoEnabled()){
                logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
            }
        }
    }

    @After("LogAspect.cut()")
    public void doAfter(JoinPoint joinPoint){

        try {
            String targetName = joinPoint.getTarget().getClass().getName();
            String curMethodName = joinPoint.getSignature().getName();
            Object[] arguments  = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            String val = null;
            for (Method method : methods){
                if (method.getName().equals(curMethodName))
                {
                    Parameter[] pars = method.getParameters();
                    //区分重载方法
                    if (pars.length == arguments.length){
                        //判断是否有标识Log注解
                        if (method.getAnnotation(LuxLog.class) != null) {
                            val = method.getAnnotation(LuxLog.class).value();
                        }
                        break;
                    }
                }
            }
            if (!StringUtils.isEmpty(val)) {
                logger.info("=====日志记录开始=====");
                logger.info("请求方法:" + (targetName + "." + curMethodName + "()"));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @AfterThrowing(pointcut = "LogAspect.cut()",throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint,Throwable e)
    {
        System.out.println("=====异常通知开始=====");
        System.out.println("异常代码:" + e.getClass().getName());
        System.out.println("异常信息:" + e.getMessage());
        System.out.println("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));

    }


}
