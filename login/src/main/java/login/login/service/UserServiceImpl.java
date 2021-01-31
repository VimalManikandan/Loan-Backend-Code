package login.login.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import login.login.exception.LoanServiceException;
import login.login.exception.UserAlreadyExist;
import login.login.exception.UserNotFound;
import login.login.model.User;
import login.login.repo.UserRepo;
import login.login.restcontroller.UserAuthController;

@Service
public class UserServiceImpl implements UserService {

	public static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserRepo userRepo;

	@Override
	public User registerUser(User user) {
		try {
			logger.info("Inside registerUser");
			User userr= userRepo.findByUsername(user.getUsername());
			if(userr==null){
				return userRepo.save(user);
			}
			else{				
				throw new UserAlreadyExist("User already exists");
			}
		}
		catch(UserAlreadyExist e){
			logger.error("Exceprtion occured: User already exists" );
			throw e;
		}
		catch (Exception e) {
			logger.error("Exceprtion occured: LoanServiceException" );
			throw new LoanServiceException("Something went wrong..!");
		}

	}

	@Override
	public User getUser(int id){
		User userobj = null;
		logger.info("Inside getUser");
		try {
			Optional<User> optional = userRepo.findById(id)	;
			if (optional.isPresent()) {
				userobj=optional.get();
			}
			else{
			
				throw new UserNotFound("User Not Found");
			}
		}
		catch(UserNotFound e){
			logger.error("Exceprtion occured: User Not Found" );
			throw e;
		}
		catch (Exception e) {
			logger.error("Exceprtion occured: LoanServiceException" );
			throw new LoanServiceException("Something went wrong..!");
		}
		return userobj;
	}

	@Override
	public User updateUser(User user) {
		logger.info("Inside updateUser");
		Optional<User> optional = userRepo.findById(user.getUserid())	;
		User userobj = null;
		try {
			if (optional.isPresent()) {
				userobj = optional.get();
				userobj.setJwtToken(user.getJwtToken());
				userobj.setUserid(user.getUserid());
				userobj.setUsername(user.getUsername());
				userobj.setUserpwd(user.getUserpwd());
				userobj.setUsertype(user.getUsertype());
				userobj = userRepo.save(userobj);
			}
			else{
				throw new UserNotFound("User Not Found");
			}
		}
		catch(UserNotFound e){
			logger.error("Exceprtion occured: User Not Found" );
			throw e;
		}
		catch (Exception e) {
			logger.error("Exceprtion occured: LoanServiceException" );
			throw new LoanServiceException("Something went wrong..!");
		}
		return userobj;

	}

	@Override
	public boolean deleteUser(int id)  {
		logger.info("Inside deleteUser");
		try {
			userRepo.deleteById(id);
			return true;
		} catch (Exception e) {
			logger.error("Exceprtion occured: User Not Found" );
			throw new UserNotFound("User Not Found");
		}
	}

}
