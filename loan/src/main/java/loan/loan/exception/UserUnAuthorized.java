package loan.loan.exception;



public class UserUnAuthorized extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserUnAuthorized(String message) {
		super(message);
	}


}
