package com.panshi.hujin2.message.domain.exception;

/**
 * create by shenjiankang on 2018/6/20 9:13
 */
public class MessageException extends RuntimeException {
    private static final long serialVersionUID = 4287991039806335607L;

    public MessageException() {
    }

    public MessageException(String message) {
        super(message);
    }
}
