package com.effortstone.backend.global.error.exception;


import com.effortstone.backend.global.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
//@RestControllerAdvice(annotations = {RestController.class}, basePackageClasses = {DiaryController.class, UserController.class, TodoController.
//        , TodoController.class})
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
