package login.login.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import login.login.model.GenericResponce;

import org.springframework.http.HttpStatus;



@ControllerAdvice
public class LoanLoginExceptionHandler {
	
	
/*	
	@ExceptionHandler(value = BadCredentialsException.class)
	public ResponseEntity<GenericResponce> handleException(BadCredentialsException e) {

		GenericResponce responce = new GenericResponce();
		responce.setMessage(e.getMessage());
		responce.setStatus(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<GenericResponce>(responce, HttpStatus.BAD_REQUEST);

	}*/
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<GenericResponce> handleException(Exception e) {

		GenericResponce responce = new GenericResponce();
		responce.setMessage(e.getMessage());
		responce.setStatus(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<GenericResponce>(responce, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(value = UserNotFound.class)
	public ResponseEntity<GenericResponce> handleException(UserNotFound e) {
		GenericResponce responce = new GenericResponce();
		responce.setMessage(e.getMessage());
		responce.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<GenericResponce>(responce, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(value = UserAlreadyExist.class)
	public ResponseEntity<GenericResponce> handleException(UserAlreadyExist e) {
		GenericResponce responce = new GenericResponce();
		responce.setMessage(e.getMessage());
		responce.setStatus(HttpStatus.CONFLICT.value());
		return new ResponseEntity<GenericResponce>(responce, HttpStatus.CONFLICT);

	}
}
