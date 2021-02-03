package loan.loan.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doThrow;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import loan.loan.datamodel.UserD;
import loan.loan.exception.LoanNotFound;
import loan.loan.exception.LoanServiceException;
import loan.loan.model.Loan;
import loan.loan.model.User;
import loan.loan.repo.LoanRepo;
import loan.loan.restclient.LoginClient;


public class LoanServiceImplTest {

	@Mock
	LoanRepo loanRepo;

	private Loan loan;
	private Loan loan2;
	
	private User user;
	
	private UserD userDObject;
	
	@Mock
	LoanService loanService;

	@InjectMocks
	LoanServiceImpl loanServiceImpl;

	private List<Loan> loans;

	private Optional<Loan> optionalloan;
	private Optional<Loan> optionalNullloan;

	private AutoCloseable closeable;

	@Before
	public void setUp() throws Exception {
		closeable = MockitoAnnotations.openMocks(this);
		loan = new Loan(1, "Vimal", "V", "Kerala", 12000, "Personal", 60);
		loan2 = new Loan(2, "Sujith", "AK ", "Kerala", 15000, "Personal", 60);
		user=new User(4, "Vima", "pwd123", "ADMIN", "ABC");
		userDObject=new UserD(4, "Vima", "pwd123", "ADMIN", "ABC");
		loans = new ArrayList<>();

		loans.add(loan);
		loans.add(loan2);

		optionalloan = Optional.of(loan);
		optionalNullloan = Optional.ofNullable(null);

	}

	@Test(expected=LoanServiceException.class)
	public void testAddLoan() {
		when(loanRepo.save(loan)).thenReturn(loan);
		loanServiceImpl.addLoan(loan);
	}

	@Test(expected = Exception.class)
	public void testAddLoanFailure() {
		doThrow(Exception.class).when(loanRepo.save(loan));
		loanServiceImpl.addLoan(loan);
		verify(loanServiceImpl, times(1)).addLoan(loan);
	}
	
	@Test(expected = LoanServiceException.class)
	public void testAddLoanException()  {
		when(loanRepo.save(loan)).thenThrow(LoanServiceException.class);
		Loan l1 = loanServiceImpl.addLoan(loan);
	}

	@Test(expected=LoanServiceException.class)
	public void testGetLoan() throws LoanNotFound {
		when(loanRepo.findById(loan.getLoanno())).thenReturn(optionalloan);
		Loan l1 = loanServiceImpl.getLoan(loan.getLoanno(),2);
		assertNotNull(l1);

	}
	
	@Test(expected = LoanServiceException.class)
	public void testLoginUserException()  {
		when(loanRepo.findById(loan.getLoanno())).thenThrow(LoanServiceException.class);
		Loan l1 = loanServiceImpl.getLoan(loan.getLoanno(),1);
	}

	@Test(expected = LoanServiceException.class)
	public void testGetLoanFailure() throws LoanNotFound {
		when(loanRepo.findById(loan.getLoanno())).thenReturn(optionalNullloan);
		Loan l1 = loanServiceImpl.getLoan(loan.getLoanno(),1);
		assertNull(l1);
	}

	@Test(expected=LoanServiceException.class)
	public void testUpdateLoan() throws LoanNotFound {
		when(loanRepo.findById(loan.getLoanno())).thenReturn(optionalloan);
		LoginClient loginClient=mock(LoginClient.class);
		Mockito.when(loginClient.getLogin(user.getUserid())).thenReturn(userDObject);
		Loan l1 = loanServiceImpl.updateLoan(loan);
		assertNull(l1);
	}
	
	@Test(expected = LoanServiceException.class)
	public void testUpdateLoanFailure() throws LoanNotFound {
		when(loanRepo.findById(loan.getLoanno())).thenReturn(optionalNullloan);
		Loan l1 = loanServiceImpl.updateLoan(loan);
	}

	@Test(expected=LoanNotFound.class)
	public void testDeleteLoan() throws LoanNotFound {
		when(loanRepo.findById(1)).thenReturn(optionalloan);
		verify(loanService, times(0)).deleteLoan(2,3);
		boolean rslt = loanServiceImpl.deleteLoan(2,3);
		assertTrue(rslt);
	}

	@Test(expected=LoanNotFound.class)
	public void testDeleteLoanFailure() throws LoanNotFound {

		when(loanRepo.findById(1)).thenReturn(optionalNullloan);
		verify(loanService, times(0)).deleteLoan(2,2);
		boolean rslt = loanServiceImpl.deleteLoan(2,2);
	}

	@Test
	public void testGetAllLoans() throws LoanNotFound {
		when(loanRepo.findAll()).thenReturn(loans);
		verify(loanService, times(0)).getAllLoans();
		List<Loan> list = loanServiceImpl.getAllLoans();
		assertTrue(list.size() > 0);
	}

	@Test(expected = LoanNotFound.class)
	public void testGetAllLoansFailure() throws LoanNotFound {
		List<Loan> rlist = new ArrayList<>();
		when(loanRepo.findAll()).thenReturn(rlist);
		verify(loanService, times(0)).getAllLoans();
		List<Loan> list = loanServiceImpl.getAllLoans();
		assertTrue(list.size() > 0);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}
}
