package com.faos.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
//make it global exception handler
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
	    
	    for (FieldError e : fieldErrors) {
	        errors.put(e.getField(), e.getDefaultMessage());
	    }
	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	
   // This method will catch validation errors (like empty fields or invalid email formats).
    
    
    @ExceptionHandler(InvalidEntityException.class)
  //  This method catches custom exceptions like InvalidEntityException.
  //  It sends a 404 Not Found status with a custom message.
    public ResponseEntity<Map<String, String>> handleEmployeeNotFoundException(InvalidEntityException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
