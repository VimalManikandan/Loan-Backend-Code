package loan.loan.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;

import loan.loan.datamodel.GenericResponce;

@ControllerAdvice
public class LoanExceptionHandler {
	
	
	@ExceptionHandler(value = LoanServiceException.class)
	public ResponseEntity<GenericResponce> handleException(Exception e) {

		GenericResponce responce = new GenericResponce();
		responce.setMessage(e.getMessage());
		responce.setStatus(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<GenericResponce>(responce, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(value = LoanNotFound.class)
	public ResponseEntity<GenericResponce> handleException(LoanNotFound e) {

		GenericResponce responce = new GenericResponce();
		responce.setMessage(e.getMessage());
		responce.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<GenericResponce>(responce, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(value = UserNotFound.class)
	public ResponseEntity<GenericResponce> handleException(UserNotFound e) {

		GenericResponce responce = new GenericResponce();
		responce.setMessage(e.getMessage());
		responce.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<GenericResponce>(responce, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(value = UserUnAuthorized.class)
	public ResponseEntity<GenericResponce> handleException(UserUnAuthorized e) {

		GenericResponce responce = new GenericResponce();
		responce.setMessage(e.getMessage());
		responce.setStatus(HttpStatus.UNAUTHORIZED.value());
		return new ResponseEntity<GenericResponce>(responce, HttpStatus.UNAUTHORIZED);

	}
}
