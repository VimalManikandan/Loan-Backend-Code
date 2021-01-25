package loan.loan.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import loan.loan.datamodel.UserD;
import loan.loan.exception.LoanNotFound;
import loan.loan.exception.UserNotFound;
import loan.loan.exception.UserUnAuthorized;
import loan.loan.model.Loan;
import loan.loan.model.User;
import loan.loan.restclient.LoginClient;
import loan.loan.service.LoanService;
import loan.loan.datamodel.GenericResponce;

@RestController
@RequestMapping("/loanApi")
public class LoanController {

	public static Logger logger = LoggerFactory.getLogger(LoanController.class);

	@Autowired
	LoginClient loginClient;

	@Autowired
	LoanService loanService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) throws UserUnAuthorized, UserNotFound {
		Loan loanObj = null;
		UserD user = loginClient.getLogin(loan.getUser().getUserid());
		try {
			if (user == null) {
				throw new UserNotFound("User Not Found");
			} else if (user.getUsertype().equals("ADMIN") && user.isLoggedin()) {
				loanObj = loanService.addLoan(loan);
			} else {
				throw new UserUnAuthorized("User Un Authorized");
			}
		} catch (UserNotFound e) {
			logger.error("Exception occured:" + e);
			throw e;
		} catch (UserUnAuthorized e) {
			logger.error("Exception occured:" + e);
			throw e;
		} catch (Exception e) {
			logger.error("Exception occured:" + e);
			throw e;
		}
		return new ResponseEntity<Loan>(loanObj,HttpStatus.OK);

	}

	@RequestMapping(value = "/getLoan/{loanId}", method = RequestMethod.GET)
	public ResponseEntity<Loan> getLoan(@PathVariable int loanId, @RequestParam int userId) throws Exception {
		Loan loanObj = null;
		UserD user = loginClient.getLogin(userId);
		try {
			if (user == null) {
				throw new UserNotFound("User Not Found");
			} else if (user.isLoggedin()) {
				loanObj = loanService.getLoan(loanId);
			} else {
				throw new UserUnAuthorized("User Un Authorized");
			}
		} catch (UserNotFound e) {
			logger.error("Exception occured:" + e);
		} catch (Exception e) {
			logger.error("Exception occured:" + e);
		}
		return new ResponseEntity<Loan>(loanObj,HttpStatus.OK);

	}

	@RequestMapping(value = "/updateLoan", method = RequestMethod.PUT)
	public ResponseEntity<Loan> updateLoan(@RequestBody Loan loan) {
		Loan loanObj = null;
		UserD user = loginClient.getLogin(loan.getUser().getUserid());
		try {
			if (user == null) {
				throw new UserNotFound("User Not Found");

			} else if (user.getUsertype().equals("ADMIN") && user.isLoggedin()) {
				loanObj = loanService.updateLoan(loan);
			} else {
				throw new UserUnAuthorized("User Un Authorized");
			}
		} catch (UserNotFound e) {
			logger.error("Exception occured:" + e);
		} catch (UserUnAuthorized e) {
			logger.error("Exception occured:" + e);
		} catch (Exception e) {
			logger.error("Exception occured:" + e);
		}
		return  new ResponseEntity<Loan>(loanObj,HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteLoan/{loanId}", method = RequestMethod.DELETE)
	public  ResponseEntity<GenericResponce> deleteLoan(@PathVariable int loanId, @RequestParam int userId) {
		GenericResponce responce=new GenericResponce();
		UserD user = loginClient.getLogin(userId);
		try {
			if (user == null) {
				throw new UserNotFound("User Not Found");

			} else if (user.getUsertype().equals("ADMIN") && user.isLoggedin()) {
				if( loanService.deleteLoan(loanId)){
					responce.setStatus(HttpStatus.OK.value());
					responce.setMessage("SUCCESS");
				}
				else{
					responce.setStatus(HttpStatus.NOT_FOUND.value());
					responce.setMessage("FAILED");
				}
				
			} else {
				throw new UserUnAuthorized("User Un Authorized");
			}
		} catch (UserNotFound e) {
			logger.error("Exception occured:" + e);
		} catch (UserUnAuthorized e) {
			logger.error("Exception occured:" + e);
		} catch (Exception e) {
			logger.error("Exception occured:" + e);
		}
		
		return new ResponseEntity<GenericResponce>(responce, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchLoan", method = RequestMethod.GET)
	public ResponseEntity<List<Loan>>searchLoan(@RequestParam(value = "loanNo", required = false) String loanNo,
			@RequestParam(value = "fName", required = false) String fName,
			@RequestParam(value = "lName", required = false) String lName) {
		List<Loan> loanList = new ArrayList<>();
		List<Loan> resultList = new ArrayList<>();		
		try {
			int lnNo=loanNo.isEmpty()?0:Integer.parseInt(loanNo);
			loanList = loanService.getAllLoans();
			if (loanList == null || loanList.isEmpty()) {
				throw new LoanNotFound("Loan Not Found");
			} else {
				resultList=	loanList.stream().filter(loan ->((lnNo != 0 ? loan.getLoanno() == lnNo : true)
						  &&(fName.isEmpty() ? true : loan.getFname().equalsIgnoreCase(fName))
						  &&(lName.isEmpty() ? true : loan.getLname().equalsIgnoreCase(lName)))) .collect(Collectors.toList());
			}
		} catch (Exception e) {
			logger.error("Exception occured:" + e);
		}
		
		return new ResponseEntity<List<Loan>>(resultList,HttpStatus.OK);
	}

}
