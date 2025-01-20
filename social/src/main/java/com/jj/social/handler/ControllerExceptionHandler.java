package com.jj.social.handler;

import com.jj.social.dto.CMRespDto;
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

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<CMRespDto<Map<String, String>>> validationApiException(CustomValidationApiException e) {
        return new ResponseEntity<>(
                new CMRespDto<>(-1, e.getMessage(), e.getErrorMap())
                , HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {
        return new Script().back(e.getErrorMap().toString());
    }
}
