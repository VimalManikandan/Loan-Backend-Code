package login.login.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import login.login.exception.LoanServiceException;
import login.login.exception.UserNotFound;
import login.login.model.User;
import login.login.repo.UserRepo;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



public class UserAuthServiceImplTest {

	@Mock
	UserRepo userRepo;
	
	private User user;
	
	@Mock
	UserService service;
	
	@InjectMocks
	UserAuthServiceImpl serviceImpl;
	 
	private AutoCloseable closeable;
	
	
	@Before
	public void setUp() throws Exception {
		closeable = MockitoAnnotations.openMocks(this);
		user= new User(1, "USet", "123Password", "USER", false);	
		
	}
	
	@Test
	public void testLoginUserSuccess() throws UserNotFound {
		when(userRepo.findByUsernameAndUserpwd(user.getUsername(),user.getUserpwd())).thenReturn(user);
		boolean result=	serviceImpl.loginUser(user);
		assertTrue(result);		
		verify(userRepo,times(1)).findByUsernameAndUserpwd(user.getUsername(), user.getUserpwd());
		verify(service,times(1)).updateUser(user);
	}

	@Test(expected=UserNotFound.class)
	public void testLoginUserFailure() throws UserNotFound {
		when(userRepo.findByUsernameAndUserpwd(user.getUsername(),user.getUserpwd())).thenReturn(null);
		boolean result=	serviceImpl.loginUser(user);
		assertFalse(result);
		assertNull(userRepo.findByUsernameAndUserpwd(user.getUsername(),user.getUserpwd()));	
	}

	
	@Test
	public void testLogoutUserSuccess() throws UserNotFound {
		when(userRepo.findByUsernameAndUserpwd(user.getUsername(),user.getUserpwd())).thenReturn(user);
		boolean result=serviceImpl.logoutUser(user);
		assertTrue(result);	
		verify(userRepo,times(1)).findByUsernameAndUserpwd(user.getUsername(), user.getUserpwd());
		verify(service,times(1)).updateUser(user);
	}
	
	@Test(expected = LoanServiceException.class)
	public void testLoginUserException() throws UserNotFound {
		when(userRepo.findByUsernameAndUserpwd(user.getUsername(),user.getUserpwd())).thenThrow(LoanServiceException.class);
		boolean result=	serviceImpl.loginUser(user);
	}
	
	@Test(expected=UserNotFound.class)
	public void testLogoutUserFailure() throws UserNotFound {
		when(userRepo.findByUsernameAndUserpwd(user.getUsername(),user.getUserpwd())).thenReturn(null);
		boolean result=serviceImpl.logoutUser(user);
		assertFalse(result);
		assertNull(userRepo.findByUsernameAndUserpwd(user.getUsername(),user.getUserpwd()));		
	}
	
	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

}
