package loan.loan.service;

import java.util.List;

import loan.loan.exception.LoanNotFound;
import loan.loan.model.Loan;

public interface LoanService {
	
	public Loan addLoan(Loan loan);
	public Loan getLoan(int id) ;
	public List<Loan> getAllLoans() ;
	public Loan updateLoan(Loan loan);
	public boolean deleteLoan(int id);

}
