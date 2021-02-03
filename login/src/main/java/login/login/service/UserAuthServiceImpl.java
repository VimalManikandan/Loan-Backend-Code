package login.login.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import login.login.exception.LoanServiceException;
import login.login.exception.UserNotFound;
import login.login.model.User;
import login.login.repo.UserRepo;
import login.login.restcontroller.UserAuthController;
import login.login.security.JwtUtil;

@Service
public class UserAuthServiceImpl implements UserAuthService {

	public static Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

	@Autowired
	private AuthenticationManager authenticationManger;

	@Autowired
	private MyUserDetailService userDetailService;

	@Autowired
	JwtUtil jwtTokenUtil;

	@Autowired
	UserRepo userRepo;

	@Autowired
	UserService userService;

	@Override
	public User loginUser(User user) {
		try {
			logger.info("Inside LoginUser");
			authenticationManger
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getUserpwd()));

			final UserDetails userDetails = userDetailService.loadUserByUsername(user.getUsername());
			final String jwt = jwtTokenUtil.generateToken(userDetails);
			User u11 = userRepo.findByUsernameAndUserpwd(user.getUsername(), user.getUserpwd());	
			u11.setJwtToken(jwt);
			userService.updateUser(u11);
			return u11;
			
		} catch (UserNotFound e) {
			logger.error("Exceprtion occured: User Not Found");
			throw e;
		} catch (BadCredentialsException exception) {
			logger.error("Exceprtion occured: " + exception);
			throw new UserNotFound("User Not Found");

		} catch (Exception e) {
			logger.error("Exceprtion occured: LoanServiceException");
			throw new LoanServiceException("Wrong Creddentials..!");
		}
	}

	@Override
	public boolean logoutUser(User user) {

		try {
			logger.info("Inside logoutUser");
			User u1 = userRepo.findByUsernameAndUserpwd(user.getUsername(), user.getUserpwd());
			if (u1 == null) {
				throw new UserNotFound("User Not Found");
			} else {
				u1.setJwtToken("");
				userService.updateUser(u1);
				return true;
			}

		} catch (UserNotFound e) {
			logger.error("Exceprtion occured: User Not Found");
			throw e;
		} catch (Exception e) {
			logger.error("Exceprtion occured: LoanServiceException");
			throw new LoanServiceException("Something went wrong..!");
		}

	}

}
