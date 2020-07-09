package com.zoltwagner.myPage.Exception;

public class NullParameterException extends RuntimeException{

	public NullParameterException() {
        super();
    }
    public NullParameterException(String message, Throwable cause) {
        super(message, cause);
    }
    public NullParameterException(String message) {
        super(message);
    }
    public NullParameterException(Throwable cause) {
        super(cause);
    }
}
