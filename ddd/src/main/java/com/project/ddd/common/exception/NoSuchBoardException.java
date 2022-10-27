package com.project.ddd.common.exception;

public class NoSuchBoardException extends RuntimeException{

    public NoSuchBoardException(String message){
        super(message);
    }
}
