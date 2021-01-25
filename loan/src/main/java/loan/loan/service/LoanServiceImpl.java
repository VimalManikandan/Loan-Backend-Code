package loan.loan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loan.loan.exception.LoanNotFound;
import loan.loan.model.Loan;
import loan.loan.repo.LoanRepo;

@Service
public class LoanServiceImpl implements LoanService {
	
	@Autowired
	LoanRepo loanRepo;
	

	@Override
	public Loan addLoan(Loan loan) {
		try {
			return loanRepo.save(loan);
		} catch (Exception e) {
			throw e;
		}
	}

	
	@Override
	public Loan getLoan(int id) throws LoanNotFound {
		Loan loanObj=null;
		try {
			Optional<Loan> optional = loanRepo.findById(id)	;
			if (optional.isPresent()) {
				loanObj=optional.get();
			}
			else{
				throw new LoanNotFound("Loan Not found");
			}
		}catch(LoanNotFound e){
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		return loanObj;
	}

	@Override
	public Loan updateLoan(Loan loan) throws LoanNotFound {
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
				throw new LoanNotFound("Loan Not found");
			}
		} catch(LoanNotFound e){
			throw e;
		}catch (Exception e) {
			throw e;
		}
		return loanObj;
	}

	@Override
	public boolean deleteLoan(int id) throws LoanNotFound {
		boolean result =false;
		try {
			loanRepo.deleteById(id);
			return true;
		} catch (Exception e) {
			throw new LoanNotFound("Loan Not found");
		}
	}


	@Override
	public List<Loan> getAllLoans() throws LoanNotFound {
		List<Loan> loans=null;
		try{
			loans=loanRepo.findAll();
			if(loans.isEmpty()){
				throw new LoanNotFound("Loan Not found");
			}
		}
		catch(LoanNotFound e){
			throw e;
		}
		catch(Exception e){
			throw e;
		}
		return loans;
	}




}
