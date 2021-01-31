package login.login.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.util.Optional;

import javax.validation.constraints.AssertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import login.login.exception.LoanServiceException;
import login.login.exception.UserAlreadyExist;
import login.login.exception.UserNotFound;
import login.login.model.User;
import login.login.repo.UserRepo;

public class UserServiceImplTest {

	@Mock
	UserRepo userRepo;
	
	private User user;
	
	Optional<User> optionaluser;
	Optional<User> optionalNulluser;
	
	@Mock
	UserService service;
	 
	@InjectMocks
	UserServiceImpl serviceImpl;
	
	private AutoCloseable closeable;
	
	
	
	@Before
	public void setUp()  {
		closeable = MockitoAnnotations.openMocks(this);
		user= new User(1, "USet", "123Password", "USER", "xyzs");		
		optionaluser=Optional.of(user);		
		optionalNulluser = Optional.ofNullable(null);
		
	}
	
	@Test(expected = UserAlreadyExist.class)
	public void testRegisterUserSuccess() throws UserAlreadyExist {				
		when(userRepo.save(user)).thenReturn(user);
		when(userRepo.findByUsername(user.getUsername())).thenReturn(user);		
		User u1=serviceImpl.registerUser(user);
		assertNotNull(u1);
		verify(userRepo,times(1)).save(u1);
	}
	@Test(expected = UserAlreadyExist.class)
	public void testRegisterUserFailure() throws UserAlreadyExist {				
		when(userRepo.save(user)).thenReturn(user);
		when(userRepo.findByUsername(user.getUsername())).thenReturn(user);		
		User u1=serviceImpl.registerUser(user);
		assertNull(u1);
		verify(userRepo,times(1)).save(u1);
	}
	
	@Test(expected = LoanServiceException.class)
	public void testRegisterUserException() throws UserNotFound {
		when(userRepo.save(user)).thenThrow(LoanServiceException.class);
		serviceImpl.registerUser(user);
	}

	@Test
	public void testGetUserSuccess() throws UserNotFound {
		when(userRepo.findById(user.getUserid())).thenReturn(optionaluser);
		User u1=serviceImpl.getUser(user.getUserid());
		assertNotNull(u1);
		verify(userRepo, times(1)).findById(u1.getUserid());
	}
	@Test(expected=UserNotFound.class)
	public void testGetUserFailure() throws UserNotFound {
		when(userRepo.findById(user.getUserid())).thenReturn(optionalNulluser);
		User u1=serviceImpl.getUser(user.getUserid());
		assertNull(u1);
		verify(userRepo, times(1)).findById(u1.getUserid());
	}
	
	@Test
	public void testUpdateUserSuccess() throws UserNotFound {
		when(userRepo.findById(user.getUserid())).thenReturn(optionaluser);
		User u1=serviceImpl.updateUser(user);
		assertNull(u1);
		verify(userRepo, times(1)).findById(1);
	}
	
	@Test(expected=UserNotFound.class)
	public void testUpdateUserFailure() throws UserNotFound {
		when(userRepo.findById(user.getUserid())).thenReturn(optionalNulluser);
		User u1=serviceImpl.updateUser(user);
		assertNotNull(u1);
		verify(userRepo, times(1)).findById(u1.getUserid());
	}

	@Test
	public void testDeleteUserSuccess() throws UserNotFound {
		when(userRepo.findById(1)).thenReturn(optionaluser);
		verify(userRepo,times(0)). deleteById(1);
		boolean rslt=serviceImpl.deleteUser(1);
		assertTrue(rslt);
	}
	
	@Test
	public void testDeleteUserFailure() throws UserNotFound {
		when(userRepo.findById(user.getUserid())).thenReturn(optionalNulluser);
		verify(userRepo,times(0)). deleteById(user.getUserid());
		boolean rslt=serviceImpl.deleteUser(user.getUserid());
		assertTrue(rslt);
	}
	

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

}
