package com.project.ddd.common.exception;

public class NoSuchCommentException extends RuntimeException{
    public NoSuchCommentException(String message) {
        super(message);
    }
}
