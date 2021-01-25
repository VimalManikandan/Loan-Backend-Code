package loan.loan.service;

import java.util.List;

import loan.loan.exception.LoanNotFound;
import loan.loan.model.Loan;

public interface LoanService {
	
	public Loan addLoan(Loan loan);
	public Loan getLoan(int id) throws LoanNotFound;
	public List<Loan> getAllLoans() throws LoanNotFound;
	public Loan updateLoan(Loan loan) throws LoanNotFound;
	public boolean deleteLoan(int id) throws LoanNotFound;

}
