package com.tyss.job.portal.handler;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tyss.job.portal.exception.JobsNotFoundException;
import com.tyss.job.portal.exception.SomeThingWentWrongException;
import com.tyss.job.portal.exception.UserNotFoundException;
import com.tyss.job.portal.exception.UserPresentException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> userNotFound(UserNotFoundException exception) {
		return ResponseEntity.badRequest().body(exception.getMessage());

	}

	@ExceptionHandler(UserPresentException.class)
	public ResponseEntity<?> userFound(UserPresentException exception) {
		return ResponseEntity.badRequest().body(exception.getMessage());

	}

	@ExceptionHandler(JobsNotFoundException.class)
	public ResponseEntity<?> userNotFound(JobsNotFoundException exception) {
		return ResponseEntity.badRequest().body(exception.getMessage());

	}

	@ExceptionHandler(SomeThingWentWrongException.class)
	public ResponseEntity<?> userNotFound(SomeThingWentWrongException exception) {
		return ResponseEntity.badRequest().body(exception.getMessage());

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleArgumnetsNotvalid(MethodArgumentNotValidException ex) {
		return ResponseEntity.badRequest().body(ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ":" + error.getDefaultMessage()).collect(Collectors.toList()));

	}

//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
//        Map<String, String> errorMap = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(error -> {
//            errorMap.put(error.getField(), error.getDefaultMessage());
//        });
//        return errorMap;
//    }

}
