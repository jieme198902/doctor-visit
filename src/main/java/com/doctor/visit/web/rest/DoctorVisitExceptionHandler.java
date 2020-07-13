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
        ComResponse comResponse = ComResponse.fail();
        if (ex instanceof org.springframework.dao.DuplicateKeyException) {
            if (ex.getMessage().contains("Duplicate entry")) {
                String message = ex.getMessage();
                message = message.substring(message.indexOf("Duplicate entry '")+"Duplicate entry '".length(), message.indexOf("' for key '"));
                comResponse.setMessage("【"+message + "】已录入");
                response.getOutputStream().write(new Gson().toJson(comResponse).getBytes());
            }
        }
    }
}
