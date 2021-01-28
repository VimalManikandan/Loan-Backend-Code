package loan.loan.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loan.loan.exception.LoanNotFound;
import loan.loan.exception.LoanServiceException;
import loan.loan.model.Loan;
import loan.loan.repo.LoanRepo;


@Service
public class LoanServiceImpl implements LoanService {
	
	public static Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);
	
	@Autowired
	LoanRepo loanRepo;
	
	@Override
	public Loan addLoan(Loan loan) {
		try {
			return loanRepo.save(loan);
		} catch (LoanServiceException e) {
			logger.error("Exceprtion occured: LoanServiceException" );
			throw new LoanServiceException("Something went wrong..!");
		}
	}
	@Override
	public Loan getLoan(int id)  {
		Loan loanObj=null;
		try {
			Optional<Loan> optional = loanRepo.findById(id)	;
			if (optional.isPresent()) {
				loanObj=optional.get();
			}
			else{
				logger.error("Exceprtion occured: Loan Not found" );
				throw new LoanNotFound("Loan Not found");
			}
		}
		catch (LoanServiceException e) {
			logger.error("Exceprtion occured: LoanServiceException" );
			throw new LoanServiceException("Something went wrong..!");
		}
		return loanObj;
	}

	@Override
	public Loan updateLoan(Loan loan) {
		Optional<Loan> optional = loanRepo.findById(loan.getLoanno())	;
		Loan loanObj = null;
		try {
			if (optional.isPresent()) {
				loanObj = optional.get();
				loanObj.setLoanno(loan.getLoanno());
				loanObj.setFname(loan.getFname());
				loanObj.setLname(loan.getLname());
				loanObj.setLoanAmount(loan.getLoanAmount());
				loanObj.setLoanterm(loan.getLoanterm());
				loanObj.setLoantype(loan.getLoantype());
				loanObj.setPaddress(loan.getPaddress());
				loanObj.setUser(loan.getUser());
				loanObj = loanRepo.save(loanObj);
			}
			else{
				logger.error("Exceprtion occured: Loan Not found" );
				throw new LoanNotFound("Loan Not found");
			}
		}catch (LoanServiceException e) {
			logger.error("Exceprtion occured: LoanServiceException" );
			throw new LoanServiceException("Something went wrong..!");
		}
		return loanObj;
	}

	@Override
	public boolean deleteLoan(int id) {
		boolean result =false;
		try {
			loanRepo.deleteById(id);
			return true;
		} catch (Exception e) {
			logger.error("Exceprtion occured: Loan Not found" );
			throw new LoanNotFound("Loan Not found");
		}
		
	}
	@Override
	public List<Loan> getAllLoans() {
		List<Loan> loans=null;
		try{
			loans=loanRepo.findAll();
			if(loans.isEmpty()){
				logger.error("Exceprtion occured: Loan Not found" );
				throw new LoanNotFound("Loan Not found");
			}
		}
		catch(LoanServiceException e){
			logger.error("Exceprtion occured: LoanServiceException" );
			throw new LoanServiceException("Something went wrong..!");
		}
		return loans;
	}
}
