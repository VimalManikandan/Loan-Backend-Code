package loan.loan.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import loan.loan.model.MyUserDetails;
import loan.loan.model.User;
import loan.loan.restclient.LoginClient;
import loan.loan.security.JwtUtil;
import loan.loan.service.LoanService;
import loan.loan.datamodel.GenericResponce;

@RestController
@RequestMapping("/loanApi")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class LoanController {

	public static Logger logger = LoggerFactory.getLogger(LoanController.class);

	@Autowired
	LoginClient loginClient;

	@Autowired
	LoanService loanService;

	@Autowired
	JwtUtil jwtUtil;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Loan> createLoan(@Valid @RequestBody Loan loan) {
		Loan	loanObj = loanService.addLoan(loan);
		return new ResponseEntity<Loan>(loanObj, HttpStatus.OK);

	}

	@RequestMapping(value = "/getLoan/{loanId}", method = RequestMethod.GET)
	public ResponseEntity<Loan> getLoan(@PathVariable int loanId, @RequestParam int userId) {
		Loan loanObj = loanService.getLoan(loanId,userId);
		return new ResponseEntity<Loan>(loanObj, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateLoan", method = RequestMethod.PUT)
	public ResponseEntity<Loan> updateLoan(@Valid @RequestBody Loan loan) {
		Loan loanObj = loanService.updateLoan(loan);
		return new ResponseEntity<Loan>(loanObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteLoan/{loanId}", method = RequestMethod.DELETE)
	public ResponseEntity<GenericResponce> deleteLoan(@PathVariable int loanId, @RequestParam @NotNull int userId) {
		GenericResponce responce = new GenericResponce();
			System.out.println("Loan No:"+loanId+"User Id:"+userId);
		
			if (loanService.deleteLoan(loanId,userId)) {
				responce.setStatus(HttpStatus.OK.value());
				responce.setMessage("SUCCESS");
			} else {
				responce.setStatus(HttpStatus.NOT_FOUND.value());
				responce.setMessage("FAILED");
			}

		return new ResponseEntity<GenericResponce>(responce, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchLoan", method = RequestMethod.GET)
	public ResponseEntity<List<Loan>> searchLoan(@RequestParam(value = "loanNo", required = false) String loanNo,
			@RequestParam(value = "fName", required = false) String fName,
			@RequestParam(value = "lName", required = false) String lName) {
		List<Loan> loanList = new ArrayList<>();
		List<Loan> resultList = new ArrayList<>();
		
		System.out.println("lno->"+loanNo+"fanme->"+fName+"->"+lName);
		
		System.out.println("Loan NO:"+loanNo);
		int lnNo=(loanNo==null || loanNo.equals("null"))?0:(loanNo.isEmpty()?0:Integer.parseInt(loanNo));
		String fNnamee=(fName==null || fName.equals("null"))?"":fName;
		String lNnamee=(lName==null || lName.equals("null"))?"":lName;
		System.out.println("After Loan NO:"+lnNo);
		
		loanList = loanService.getAllLoans();
		
		System.out.println("Loan No:"+lnNo+"FName:"+fNnamee+"LName:"+lNnamee);
		
		if (loanList == null || loanList.isEmpty()) {
			throw new LoanNotFound("Loan Not Found");
		} else {
			resultList = loanList.stream()
					.filter(loan -> ((lnNo != 0 ? loan.getLoanno() == lnNo : true)
							&& (fNnamee.isEmpty() ? true : loan.getFname().equalsIgnoreCase(fNnamee))
							&& (lNnamee.isEmpty() ? true : loan.getLname().equalsIgnoreCase(lNnamee))))
					.collect(Collectors.toList());
		}
		
		
		System.out.println("ResultList:"+resultList);
		
		return new ResponseEntity<List<Loan>>(resultList, HttpStatus.OK);
	}

}
