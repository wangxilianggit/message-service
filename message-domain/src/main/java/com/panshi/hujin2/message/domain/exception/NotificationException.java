package com.panshi.hujin2.message.domain.exception;

/**
 * create by shenjiankang on 2018/6/25 15:16
 *
 * 消息通知 自定義異常
 */
public class NotificationException extends RuntimeException {

    private static final long serialVersionUID = 2245261197711721255L;

    public NotificationException() {
    }

    public NotificationException(String message) {
        super(message);
    }
}
