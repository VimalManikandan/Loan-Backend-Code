package login.login.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import login.login.exception.LoanServiceException;
import login.login.exception.UserNotFound;
import login.login.model.User;
import login.login.repo.UserRepo;
import login.login.restcontroller.UserAuthController;

@Service
public class UserAuthServiceImpl implements UserAuthService {

	public static Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);
	@Autowired
	UserRepo userRepo;

	@Autowired
	UserService userService;

	@Override
	public boolean loginUser(User user) {
		try {			
			logger.info("Inside LoginUser");
			User u11 = userRepo.findByUsernameAndUserpwd(user.getUsername(), user.getUserpwd());
			if (u11 == null){				
				throw new UserNotFound("User Not Found");
			}
			else {			
				u11.setLoggedin(true);
				userService.updateUser(u11);
				return true;
			}
		} 
		catch(UserNotFound e ){
			logger.error("Exceprtion occured: User Not Found" );
			throw e;
		}
		catch (Exception e) {
			logger.error("Exceprtion occured: LoanServiceException" );
			throw new LoanServiceException("Something went wrong..!");
		}
	}

	@Override
	public boolean logoutUser(User user) {

		try {
			logger.info("Inside logoutUser");
			User u1 = userRepo.findByUsernameAndUserpwd(user.getUsername(), user.getUserpwd());
			if (u1 == null){				
				throw new UserNotFound("User Not Found");
			}
			else {
				u1.setLoggedin(false);
				userService.updateUser(u1);
				return true;
			}

		}
		catch(UserNotFound e ){
			logger.error("Exceprtion occured: User Not Found" );
			throw e;
		}
		catch (Exception e ) {
			logger.error("Exceprtion occured: LoanServiceException" );
			throw new LoanServiceException("Something went wrong..!");
		}

	}

}
