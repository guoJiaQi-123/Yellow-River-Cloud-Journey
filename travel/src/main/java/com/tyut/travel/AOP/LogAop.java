package com.tyut.travel.AOP;

import com.tyut.travel.bean.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Aspect //切面配置类
@SuppressWarnings("all")
public class LogAop {
    @Autowired
    private HttpServletRequest request;
    private final static Logger logger = LoggerFactory.getLogger(LogAop.class);

    //切点： controller包下的所有类中的所有方法
    @Pointcut("execution(* com.tyut.travel.controller.*.*(..))")
    public void pointcut() {
    }

    //前置通知
    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        //记录访问时间
        Date date = new Date();
        request.setAttribute("visitTime", date);
    }

    //后置通知
    @After("pointcut()")
    public void doAfter() {
        Log log = new Log();
        Date visitTime = (Date) request.getAttribute("visitTime"); // 访问时间
        //记录方法执行后时间
        Date now = new Date();
        //访问时长
        int executionTime = (int) (now.getTime() - visitTime.getTime()); // 访问时长

        //获取IP
        String IP = request.getRemoteAddr();

        //获取访问路径
        String url = request.getRequestURI();

        // 拿到security中的User对象
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user instanceof User) {
            String username = ((User) user).getUsername();
            log.setUsername(username);
        }
        log.setExecutionTime(executionTime);
        log.setUrl(url);
        log.setIp(IP);
        log.setVisitTime(visitTime);
        logger.info(log.toString());
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "ex")
    public void doThrowing(Throwable ex) {
        Log log = new Log();

        Date visitTime = (Date) request.getAttribute("visitTime"); // 访问时间
        Date now = new Date();
        int executionTime = (int) (now.getTime() - visitTime.getTime()); // 访问时长
        String ip = request.getRemoteAddr(); // 访问ip
        String url = request.getRequestURI(); // 访问路径
        // 拿到Security中的User对象
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof User) {
            String username = ((User) user).getUsername();
            log.setUsername(username);
        }
        log.setExecutionTime(executionTime);
        log.setUrl(url);
        log.setIp(ip);
        log.setVisitTime(visitTime);

        // 异常信息
        String exMessage = ex.getMessage();
        log.setExceptionMessage(exMessage);

        logger.info(log.toString());
    }
}
