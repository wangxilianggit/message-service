package com.panshi.hujin2.message.domain.exception;

/**
 * create by shenjiankang on 25/07/2018 14:02
 */
public class EmailException extends RuntimeException {
    private static final long serialVersionUID = 8804604646545603022L;

    public EmailException() {
    }

    public EmailException(String message) {
        super(message);
    }
}
