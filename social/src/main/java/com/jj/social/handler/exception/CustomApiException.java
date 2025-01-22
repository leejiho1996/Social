package com.jj.social.handler.exception;

import java.util.Map;

public class CustomApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Map<String, String> erorMap;

    public CustomApiException(String message) {
        super(message);
    }

    public CustomApiException(String message, Map<String, String> erorMap) {
        super(message);
        this.erorMap = erorMap;
    }

    public Map<String, String> getErrorMap() {
        return erorMap;
    }

}
