package loan.loan.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import loan.loan.model.Loan;


public interface LoanRepo extends JpaRepository<Loan, Integer>{
	

}
