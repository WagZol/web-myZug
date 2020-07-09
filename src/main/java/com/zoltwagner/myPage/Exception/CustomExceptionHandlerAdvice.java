package com.zoltwagner.myPage.Exception;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class CustomExceptionHandlerAdvice {

	@ExceptionHandler(NullParameterException.class)
    public ResponseEntity handleException(NullParameterException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }        
	
	@ExceptionHandler(ParseException.class)
    public ResponseEntity handleException(ParseException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occured during parsing");
    }
	
	
	@ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity handleException(HttpClientErrorException.NotFound e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
	
	
}

