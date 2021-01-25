package login.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import login.login.exception.UserNotFound;
import login.login.model.User;
import login.login.repo.UserRepo;

@Service
public class UserAuthServiceImpl implements UserAuthService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	UserService userService;

	@Override
	public boolean loginUser(User user) throws UserNotFound {
		try {
			User u11 = userRepo.findByUsernameAndUserpwd(user.getUsername(), user.getUserpwd());
			if (u11 == null)
				throw new UserNotFound("User Not Found");
			else {
				u11.setLoggedin(true);
				userService.updateUser(u11);
				return true;
			}
		} 
		catch(UserNotFound e){
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public boolean logoutUser(User user) throws UserNotFound {

		try {
			User u1 = userRepo.findByUsernameAndUserpwd(user.getUsername(), user.getUserpwd());
			if (u1 == null)
				throw new UserNotFound("User Not Found");
			else {
				u1.setLoggedin(false);
				userService.updateUser(u1);
				return true;
			}

		}catch(UserNotFound e){
			throw e;
		} 
		catch (Exception e) {
			throw e;
		}

	}

}
