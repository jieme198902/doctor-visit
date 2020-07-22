package com.doctor.visit.web.rest;

import com.doctor.visit.web.rest.util.ComResponse;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class DoctorVisitExceptionHandler {

    /**
     * 异常处理
     */
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        ex.printStackTrace();
        ComResponse comResponse = ComResponse.fail();
        String message = ex.getMessage();
        if (ex instanceof org.springframework.dao.DuplicateKeyException) {
            if (message.contains("Duplicate entry")) {
                message = message.substring(message.indexOf("Duplicate entry '")+"Duplicate entry '".length(), message.indexOf("' for key '"));
                comResponse.setMessage("【"+message + "】已录入");
            }
        }
        if(ex instanceof org.springframework.dao.DataIntegrityViolationException){
            if(message.contains("doesn't have a default value")){
                message = message.substring(message.indexOf(": Field '")+": Field '".length(), message.indexOf("' doesn't have a default value"));
                comResponse.setMessage("请填写【"+message + "】的值");
            }
        }
        //jwt 过期
        if(ex instanceof org.springframework.security.authentication.InsufficientAuthenticationException){
            comResponse = ComResponse.failUnauthorized();
        }
        response.getOutputStream().write(new Gson().toJson(comResponse).getBytes());
    }
}
