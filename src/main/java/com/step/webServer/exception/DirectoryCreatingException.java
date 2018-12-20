package com.step.webServer.exception;

public class DirectoryCreatingException extends RuntimeException{
    public DirectoryCreatingException() {
        super();
    }

    public DirectoryCreatingException(String message) {
        super(message);
    }

}
