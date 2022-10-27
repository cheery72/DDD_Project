package com.project.ddd.common.exception;

public class NoSuchMemberException extends RuntimeException{
    public NoSuchMemberException(String message) {
        super(message);
    }
}
