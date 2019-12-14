package com.springboot.config;

import com.springboot.common.HttpContextUtils;
import com.springboot.entity.SysLogEntity;
import com.springboot.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/***
 * 操作日志记录
 * @ClassName: WebLogAspect
 * @Description:
 * @Auther: cxy
 * @Date: 2019/5/19:18:08
 * @version : V1.0
 */
@Aspect
@Order(5)
@Component
public class WebLogAspect {
    private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    @Autowired
    private SysLogService sysLogService;
    ThreadLocal<Long> startTime = new ThreadLocal<>();
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

        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String ipAddress = HttpContextUtils.getIpAddress();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        logger.info("ip:"+ipAddress);
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
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
