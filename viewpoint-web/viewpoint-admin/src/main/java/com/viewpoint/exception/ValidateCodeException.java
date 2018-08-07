package com.viewpoint.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * 验证异常类 继承 Security 的 AuthenticationException
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
