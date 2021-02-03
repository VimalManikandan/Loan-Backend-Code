package loan.loan.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loan.loan.datamodel.UserD;
import loan.loan.exception.LoanNotFound;
import loan.loan.exception.LoanServiceException;
import loan.loan.exception.UserNotFound;
import loan.loan.exception.UserUnAuthorized;
import loan.loan.model.Loan;
import loan.loan.model.MyUserDetails;
import loan.loan.model.User;
import loan.loan.repo.LoanRepo;
import loan.loan.restclient.LoginClient;
import loan.loan.security.JwtUtil;


@Service
public class LoanServiceImpl implements LoanService {
	
	public static Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);
	
	@Autowired
	LoanRepo loanRepo;
	
	@Autowired
	LoginClient loginClient;
	

	@Autowired
	JwtUtil jwtUtil;
	
	@Override
	public Loan addLoan(Loan loan) {

		Loan loanObj = null;
		
		try{
		UserD user = loginClient.getLogin(loan.getUser().getUserid());
		if (user == null) {
			throw new UserNotFound("User Not Found");
		}
		User userObj = new User(user.getUid(), user.getUsername(), user.getUserpwd(), user.getUsertype(),user.getJwtToken());
		MyUserDetails userDetails = new MyUserDetails(userObj);
		if (user.getUsertype().equals("ADMIN") && jwtUtil.validateToken(user.getJwtToken(), userDetails)) {
			loanObj= loanRepo.save(loan);
		}
		 else {
			throw new UserUnAuthorized("User Un Authorized");
		}
		}
		catch(UserUnAuthorized e){
			logger.error("Exception occured: User Un Authorized" );
			throw e;
		}
		catch(UserNotFound e){
			logger.error("Exception occured: User Not Found" );
			throw e;
		}
		catch (Exception e) {
			logger.error("Exception occured: LoanServiceException" );
			throw new LoanServiceException("Something went wrong..!");
		}
		return loanObj;
	}
	@Override
	public Loan getLoan(int id, int userId)  {
		Loan loanObj = null;
		try {
		UserD user = loginClient.getLogin(userId);
		if (user == null) {
			throw new UserNotFound("User Not Found");
		}
		User userObj = new User(user.getUid(), user.getUsername(), user.getUserpwd(), user.getUsertype(),
				user.getJwtToken());
		MyUserDetails userDetails = new MyUserDetails(userObj);
		if (jwtUtil.validateToken(user.getJwtToken(), userDetails)) {
		
			Optional<Loan> optional = loanRepo.findById(id)	;
			if (optional.isPresent()) {
				loanObj=optional.get();
			}
			else{
				
				throw new LoanNotFound("Loan Not found");
			}
		}
		else {
			throw new UserUnAuthorized("User Un Authorized");
		}
		}
		catch(UserUnAuthorized e){
			logger.error("Exception occured: User Un Authorized" );
			throw e;
		}
		catch(UserNotFound e){
			logger.error("Exception occured: User Not found" );
		}
		catch(LoanNotFound e){
			logger.error("Exception occured: Loan Not found" );
			throw e;
		}
		catch (Exception e) {
			logger.error("Exception occured: LoanServiceException" );
			throw new LoanServiceException("Something went wrong..!");
		}
		return loanObj;
	}

	@Override
	public Loan updateLoan(Loan loan) {
		Loan loanObj = null;
		try {
		UserD user = loginClient.getLogin(loan.getUser().getUserid());
		if (user == null) {
			throw new UserNotFound("User Not Found");

		}
		User userObj = new User(user.getUid(), user.getUsername(), user.getUserpwd(), user.getUsertype(),
				user.getJwtToken());
		MyUserDetails userDetails = new MyUserDetails(userObj);
		if (user.getUsertype().equals("ADMIN") && jwtUtil.validateToken(user.getJwtToken(), userDetails)) {
		Optional<Loan> optional = loanRepo.findById(loan.getLoanno())	;
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
		}
		else {
			throw new UserUnAuthorized("User Un Authorized");
		}
		}
		catch(UserUnAuthorized e){
			logger.error("Exception occured: User Un Authorized" );
			throw e;
		}
		catch(UserNotFound e){
			logger.error("Exception occured: User Not found" );
		}
		catch(LoanNotFound e){
				logger.error("Exception occured: Loan Not found" );
				throw e;
			}
		catch (Exception e) {
			logger.error("Exception occured: LoanServiceException" );
			throw new LoanServiceException("Something went wrong..!");
		}
		return loanObj;
	}

	@Override
	public boolean deleteLoan(int id,int userId) {
		boolean result =false;
		
		try {
		UserD user = loginClient.getLogin(userId);
		if (user == null) {
			throw new UserNotFound("User Not Found");

		}
		User userObj = new User(user.getUid(), user.getUsername(), user.getUserpwd(), user.getUsertype(),
				user.getJwtToken());
		MyUserDetails userDetails = new MyUserDetails(userObj);
		if (user.getUsertype().equals("ADMIN") && jwtUtil.validateToken(user.getJwtToken(), userDetails)) {
		
	
			loanRepo.deleteById(id);
			return true;
		}else {
			throw new UserUnAuthorized("User Un Authorized");
			
		}
		}
		catch(UserUnAuthorized e){
			logger.error("Exception occured: User Un Authorized" );
			throw e;
		}
		catch(UserNotFound e){
			logger.error("Exception occured: User Not found" );
		}
		
		catch (Exception e) {
			logger.error("Exception occured: Loan Not found" );
			throw new LoanNotFound("Loan Not found");
		}
		return result;
		
	}
	@Override
	public List<Loan> getAllLoans() {
		List<Loan> loans=null;
		try{
			loans=loanRepo.findAll();
			if(loans.isEmpty()){
				throw new LoanNotFound("Loan Not found");
			}
		}
		catch(LoanNotFound e){
			logger.error("Exception occured: Loan Not found" );
			throw e;
		}
		catch(Exception e){
			logger.error("Exception occured: LoanServiceException" );
			throw new LoanServiceException("Something went wrong..!");
		}
		return loans;
	}

}
