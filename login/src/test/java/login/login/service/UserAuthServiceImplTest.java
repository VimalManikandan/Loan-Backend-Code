package login.login.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
	 
	private AutoCloseable closeable;
	
	
	@Before
	public void setUp() throws Exception {
		closeable = MockitoAnnotations.openMocks(this);
		user= new User(1, "USet", "123Password", "USER", false);	
		
	}
	
	@Test
	public void testLoginUserSuccess() throws UserNotFound {
		when(userRepo.findByUsernameAndUserpwd(user.getUsername(),user.getUserpwd())).thenReturn(user);
		assertNotNull(user);		
		verify(userRepo,times(0)).findByUsernameAndUserpwd(user.getUsername(), user.getUserpwd());
		verify(service,times(0)).updateUser(user);
	}

	@Test
	public void testLoginUserFailure() {
		when(userRepo.findByUsernameAndUserpwd(user.getUsername(),user.getUserpwd())).thenReturn(null);
		assertNotNull(user);
		assertNull(userRepo.findByUsernameAndUserpwd(user.getUsername(),user.getUserpwd()));	
	}

	
	@Test
	public void testLogoutUserSuccess() throws UserNotFound {
		when(userRepo.findByUsernameAndUserpwd(user.getUsername(),user.getUserpwd())).thenReturn(user);
		assertNotNull(user);		
		verify(userRepo,times(0)).findByUsernameAndUserpwd(user.getUsername(), user.getUserpwd());
		verify(service,times(0)).updateUser(user);
	}
	
	@Test
	public void testLogoutUserFailure() throws UserNotFound {
		when(userRepo.findByUsernameAndUserpwd(user.getUsername(),user.getUserpwd())).thenReturn(null);
		assertNotNull(user);
		assertNull(userRepo.findByUsernameAndUserpwd(user.getUsername(),user.getUserpwd()));
		
	}
	
	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

}
