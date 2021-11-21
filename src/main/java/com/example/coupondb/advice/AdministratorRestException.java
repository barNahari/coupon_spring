package com.example.coupondb.advice;

import com.example.coupondb.exceptions.AdministratorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class AdministratorRestException {
    @ExceptionHandler(value = {AdministratorException.class})
    //what to return in response
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleException(Exception e){
        return new ErrorDetail("asdsa", e.getMessage());
    }

}
