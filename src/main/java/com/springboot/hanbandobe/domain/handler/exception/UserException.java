package com.springboot.hanbandobe.domain.handler.exception;

import com.springboot.hanbandobe.domain.handler.message.ExceptionMessage;
import lombok.Getter;

import java.io.Serial;

@Getter
public class UserException extends BaseException {

    @Serial
    private static final long serialVersionUID = -8539352692637830207L;

    public UserException(ExceptionMessage message) {
        super(message);
    }
}
