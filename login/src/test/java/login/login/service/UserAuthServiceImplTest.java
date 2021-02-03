package login.login.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import login.login.exception.LoanServiceException;
import login.login.exception.UserNotFound;
import login.login.model.MyUserDetails;
import login.login.model.User;
import login.login.repo.UserRepo;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
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
	
	private AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
	
	
	@Before
	public void setUp() throws Exception {
		closeable = MockitoAnnotations.openMocks(this);
		user= new User(3, "User2", "123Password", "USER", "xyz");		
	}
	
	@Test(expected=LoanServiceException.class)
	public void testLoginUserSuccess() throws UserNotFound {
		when(userRepo.findByUsernameAndUserpwd(user.getUsername(),user.getUserpwd())).thenReturn(user);
		  Authentication authentication = mock(Authentication.class);
		  SecurityContext securityContext = mock(SecurityContext.class);
		  
		  MyUserDetails details=mock(MyUserDetails.class);
		  MyUserDetailService userDetailService=mock(MyUserDetailService.class);
		  when(userDetailService.loadUserByUsername(user.getUsername())).thenReturn(details);
	      when(securityContext.getAuthentication()).thenReturn(authentication);		
		when(serviceImpl.loginUser(user)).thenReturn(user);
		assertNotNull(serviceImpl.loginUser(user));		
		verify(userRepo,times(1)).findByUsernameAndUserpwd(user.getUsername(), user.getUserpwd());
		verify(service,times(1)).updateUser(user);
	}

	@Test(expected=LoanServiceException.class)
	public void testLoginUserFailure() throws UserNotFound {
		when(userRepo.findByUsernameAndUserpwd(user.getUsername(),user.getUserpwd())).thenReturn(null);
			
		Mockito.when(authenticationManager.authenticate(Mockito.<Authentication> any())).thenReturn(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getUserpwd()));
		
		  Authentication authentication = mock(Authentication.class);
		  SecurityContext securityContext = mock(SecurityContext.class);
	      when(securityContext.getAuthentication()).thenReturn(authentication);
	      SecurityContextHolder.setContext(securityContext);
	      when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(authentication);
	      when(serviceImpl.loginUser(user)).thenReturn(null);
		User u1=	serviceImpl.loginUser(user);
		assertNull(u1);
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
		User u1=	serviceImpl.loginUser(user);
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
