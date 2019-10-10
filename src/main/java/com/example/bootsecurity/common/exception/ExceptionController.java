package com.example.bootsecurity.common.exception;

import com.example.bootsecurity.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public Result toException(HttpServletRequest request, ServiceException ex){
              Result result=Result.fail(ex.getMessage());
              return result;
    }

}
