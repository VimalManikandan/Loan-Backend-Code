package loan.loan.exception;



public class LoanNotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LoanNotFound(String message) {
		super(message);
	}


}
