package com.springboot.config;

import com.alibaba.fastjson.JSONObject;
import com.springboot.common.HttpContextUtils;
import com.springboot.entity.SysLogEntity;
import com.springboot.service.SysLogService;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;


/***
 * 操作日志记录
 * @ClassName: WebLogAspect
 * @Description:
 * @Auther: cxy
 * @Date: 2019/5/19:18:08
 * @version : V1.0
 */
/*@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})})*/
@Aspect
@Order(5)
@Component
/*@Configuration*/
public class WebLogAspect /*implements Interceptor*/ {
    private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    @Autowired
    private SysLogService sysLogService;
    ThreadLocal<Long> startTime = new ThreadLocal<>();


    //自定义拦截
  /*  @Override
    public Object intercept(Invocation invocation) {

        // 开始时间
        try {
            Object target = invocation.getTarget();
            System.out.println("-->"+ JSONObject.toJSONString(target));
            Field delegate = target.getClass().getDeclaredField("delegate");
            delegate.setAccessible(true);
            Object delegateObj = delegate.get(target);
            System.out.println("fff");
            Field[] declaredFields = delegateObj.getClass().getSuperclass().getDeclaredFields();
            Field parameterHandler = delegateObj.getClass().getSuperclass().getDeclaredField("boundSql");
            parameterHandler.setAccessible(true);
            Object parameterHandlerObj = parameterHandler.get(delegateObj);

            Field parameterObject = parameterHandlerObj.getClass().getDeclaredField("sql");
            parameterObject.setAccessible(true);
            Object o = parameterObject.get(parameterHandlerObj);
            System.out.println("拦截到执行的sql:--->"+o);

        }catch (Exception e){
            e.printStackTrace();
        }
        long start = System.currentTimeMillis();
        invocation.getArgs();
        try {
            return invocation.proceed();
        } catch (Exception e) {
            logger.error("执行失败！", e);
            return null;
        } finally {
            long end = System.currentTimeMillis();
            long time = end - start;
            logger.info("cost time {}ms", time);
        }
    }

    @Override
    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, new WebLogAspect());
    }

    @Override
    public void setProperties(Properties arg0) {
    }*/

    /**
     第一个*表示返回任何类型,com.cxy.shibernate.controller下任何类,任何方法,任何参数
     也可以加入参数限定例如com.cxy.shibernate.controller.*.*(..)&&args(name,..)

     下面那中表示方法也是对的,表示com.cxy.shibernate.下面任何子包下任何方法,任何参数
     **/
    @Pointcut("execution(public * com.springboot.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
/*
        // 接收到请求，记录请求内容

        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String ipAddress = HttpContextUtils.getIpAddress();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        logger.info("ip:"+ipAddress);*/

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
        // 接收到请求，记录请求内容
        Object target = joinPoint.getTarget();
        System.out.println("-->"+ JSONObject.toJSONString(target));
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String ipAddress = HttpContextUtils.getIpAddress();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        logger.info("ip:"+ipAddress);
        // 处理完请求，返回内
        logger.info("RESPONSE : " + ret);
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));

        logger.info("++++"+joinPoint.getSignature().toString());
        long requestTime = System.currentTimeMillis() - startTime.get();
        SysLogEntity sysLogEntity = new SysLogEntity();
        sysLogEntity.setRequestUrl(request.getRequestURL().toString());
        sysLogEntity.setRequestMode(request.getMethod());
        sysLogEntity.setRequestIp(request.getRemoteAddr());
        sysLogEntity.setRequestClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        sysLogEntity.setRequestParams(Arrays.toString(joinPoint.getArgs()));
        sysLogEntity.setRequestResponse(ret.toString());
        sysLogEntity.setRequestTime(String.valueOf(requestTime));
        sysLogEntity.setIfError("0");
        sysLogService.save(sysLogEntity);
    }
    @AfterThrowing(pointcut = "webLog()", throwing = "e")
     public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {

        // 接收到请求，记录请求内容 ,方式一
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String ipAddress = HttpContextUtils.getIpAddress();
        long requestTime = System.currentTimeMillis() - startTime.get();
        SysLogEntity sysLogEntity = new SysLogEntity();
        sysLogEntity.setRequestUrl(request.getRequestURL().toString());
        sysLogEntity.setRequestMode(request.getMethod());
        sysLogEntity.setRequestIp(request.getRemoteAddr());
        sysLogEntity.setRequestClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        sysLogEntity.setRequestParams(Arrays.toString(joinPoint.getArgs()));
        sysLogEntity.setRequestResponse("fail");
        sysLogEntity.setRequestTime(String.valueOf(requestTime));
        sysLogEntity.setErrorMessage(e.getMessage());
        sysLogEntity.setIfError("1");
        sysLogService.save(sysLogEntity);
        //方式二
            /*    // 获取RequestAttributes
                 RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
                 // 从获取RequestAttributes中获取HttpServletRequest的信息
                 HttpServletRequest request = (HttpServletRequest) requestAttributes
                         .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        SysLogEntity sysLogEntity = new SysLogEntity();
                     sysLogEntity.setRequestParams(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
                     sysLogService.save(sysLogEntity);*/


             }

    /**
     193      * 转换异常信息为字符串
     194      *
     195      * @param exceptionName    异常名称
     196      * @param exceptionMessage 异常信息
     197      * @param elements         堆栈信息
     198      */
     public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
                 StringBuffer strbuff = new StringBuffer();
                 for (StackTraceElement stet : elements) {
                         strbuff.append(stet + "\n");
                     }
                 String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
                 return message;
             }

}
