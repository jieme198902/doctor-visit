package com.doctor.visit.aop.logging;

import com.doctor.visit.domain.BusLog;
import com.doctor.visit.repository.BusLogMapper;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import io.github.jhipster.config.JHipsterConstants;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Aspect for logging execution of service and repository Spring components.
 * <p>
 * By default, it only runs with the "dev" profile.
 */
@Aspect
public class LoggingAspect {

    private final Environment env;
    @Autowired
    private BusLogMapper busLogMapper;
    private Gson gson = new Gson();

    public LoggingAspect(Environment env) {
        this.env = env;
    }

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
        " || within(@org.springframework.stereotype.Service *)" +
        " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.doctor.visit.repository..*)" +
        " || within(com.doctor.visit.service..*)" +
        " || within(com.doctor.visit.web.rest..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Retrieves the {@link Logger} associated to the given {@link JoinPoint}.
     *
     * @param joinPoint join point we want the logger for.
     * @return {@link Logger} associated to the given {@link JoinPoint}.
     */
    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice.
     * @param e         exception.
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (env.acceptsProfiles(Profiles.of(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT))) {
            logger(joinPoint)
                .error(
                    "Exception in {}() with cause = \'{}\' and exception = \'{}\'",
                    joinPoint.getSignature().getName(),
                    e.getCause() != null ? e.getCause() : "NULL",
                    e.getMessage(),
                    e
                );
        } else {
            logger(joinPoint)
                .error(
                    "Exception in {}() with cause = {}",
                    joinPoint.getSignature().getName(),
                    e.getCause() != null ? e.getCause() : "NULL"
                );
        }
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice.
     * @return result.
     * @throws Throwable throws {@link IllegalArgumentException}.
     */
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        BusLog busLog = new BusLog();
        busLog.setId(IDKeyUtil.generateId());
        busLog.setCreateBy(3L);
        busLog.setCreateName("Administrator");
        busLog.setCreateTime(new Date());
        busLog.setModel("统一日志");
        busLog.setOperation("记录");
        busLog.setSys("0");
        Logger log = logger(joinPoint);
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        Object[] args = joinPoint.getArgs();
        Map<String, Object> requestMap = Maps.newHashMap();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                //忽略这个参数
            } else {
                //
                requestMap.put(Utils.generateOf8(), arg);
            }
        }
        busLog.setRequest(gson.toJson(requestMap));
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}() with result = {}", joinPoint.getSignature().getName(), result);
            }
            busLog.setResponse(gson.toJson(result));
            busLogMapper.insertSelective(busLog);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getName());
            Map<String, Object> resultMap = Maps.newHashMap();
            requestMap.put("异常", joinPoint.getSignature());
            busLog.setResponse(gson.toJson(resultMap));
            busLogMapper.insertSelective(busLog);
            throw e;
        }

    }
}
