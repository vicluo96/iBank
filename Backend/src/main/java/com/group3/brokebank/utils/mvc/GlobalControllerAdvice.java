package com.group3.brokebank.utils.mvc;

import com.group3.brokebank.utils.exception.CustomException;
import com.group3.brokebank.utils.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Response handle(RuntimeException exception){
        exception.printStackTrace();
        return Response.fail(exception.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Response handle(CustomException exception){
        exception.printStackTrace();
        return Response.fail(exception.getMessage());
    }
}
