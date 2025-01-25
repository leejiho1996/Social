package com.jj.social.handler;

import com.jj.social.dto.CMRespDto;
import com.jj.social.handler.exception.CustomApiException;
import com.jj.social.handler.exception.CustomException;
import com.jj.social.handler.exception.CustomValidationApiException;
import com.jj.social.handler.exception.CustomValidationException;
import com.jj.social.util.Script;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {
        if (e.getErrorMap() == null) {
            return Script.back(e.getMessage());
        } else {
            return Script.back(e.getErrorMap().toString());
        }
    }

    // profile 페이지에서 사용될 핸들러
    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e) {
        return Script.back(e.getMessage());
    }

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<CMRespDto<Map<String, String>>> validationApiException(CustomValidationApiException e) {
        return new ResponseEntity<>(
                new CMRespDto<>(-1, e.getMessage(), e.getErrorMap())
                , HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<CMRespDto<Map<String, String>>> apiException(CustomApiException e) {
        return new ResponseEntity<> (
                new CMRespDto<>(-1, e.getMessage(), e.getErrorMap())
                ,HttpStatus.BAD_REQUEST);
    }
}
