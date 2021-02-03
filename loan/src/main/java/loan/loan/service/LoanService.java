package loan.loan.service;

import java.util.List;

import loan.loan.model.Loan;

public interface LoanService {
	
	public Loan addLoan(Loan loan);
	public List<Loan> getAllLoans() ;
	public Loan updateLoan(Loan loan);
	public boolean deleteLoan(int id, int userId);
	public Loan getLoan(int loanId, int userId);

}
