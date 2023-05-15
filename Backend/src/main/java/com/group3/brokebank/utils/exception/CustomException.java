package com.group3.brokebank.utils.exception;

public class CustomException extends RuntimeException {

    public CustomException(){

    }
    public CustomException(String message){
        super(message);
    }
}

