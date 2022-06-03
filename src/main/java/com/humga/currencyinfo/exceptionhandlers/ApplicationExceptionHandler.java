package com.humga.currencyinfo.exceptionhandlers;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.util.Locale;

@ControllerAdvice
class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;
    private final Locale locale = Locale.getDefault();
    @Value("${app.config.giphy.appkey}")
    private String appKey;

    @Value("${app.config.openexchange.appid}")
    private String appId;

    ApplicationExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    //Standard Spring MVC Exceptions
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        //ensure no sensitive data exposed
        String bodyOfResponse = status + ". " +
                ex.getMessage().replace(appKey,"***app_key***").replace(appId,"***appId***") + ".";

        return new ResponseEntity<>(bodyOfResponse, headers, status);
    }

    //Feign clients exceptions
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignException() {

        String bodyOfResponse = HttpStatus.INTERNAL_SERVER_ERROR +
                messageSource.getMessage("external-service-error", null, locale);
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //If something other inside application went wrong
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleRuntimeException() {

        String bodyOfResponse = HttpStatus.INTERNAL_SERVER_ERROR +
                messageSource.getMessage("internal-server-error", null, locale);
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
