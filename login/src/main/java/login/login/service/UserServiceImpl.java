package login.login.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import login.login.exception.UserAlreadyExist;
import login.login.exception.UserNotFound;
import login.login.model.User;
import login.login.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;

	@Override
	public User registerUser(User user) throws UserAlreadyExist  {
		try {
			User userr= userRepo.findByUsername(user.getUsername());
			if(userr==null){
				return userRepo.save(user);
			}
			else{
				throw new UserAlreadyExist("User already exists");
			}
		}
	
		catch(UserAlreadyExist e){
			throw e;
		}
		catch (Exception e) {
			throw e;
		}

	}

	@Override
	public User getUser(int id) throws UserNotFound {
		User userobj = null;
		try {
			Optional<User> optional = userRepo.findById(id)	;
			if (optional.isPresent()) {
				userobj=optional.get();
			}
			else{
				throw new UserNotFound("User Not Found");
			}
		}catch(UserNotFound e){
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		return userobj;
	}

	@Override
	public User updateUser(User user) throws UserNotFound {
		Optional<User> optional = userRepo.findById(user.getUserid())	;
		User userobj = null;
		try {
			if (optional.isPresent()) {
				userobj = optional.get();
				userobj.setLoggedin(user.isLoggedin());
				userobj.setUserid(user.getUserid());
				userobj.setUsername(user.getUsername());
				userobj.setUserpwd(user.getUserpwd());
				userobj.setUsertype(user.getUsertype());
				userobj = userRepo.save(userobj);
			}
			else{
				throw new UserNotFound("User Not Found");
			}
		}catch(UserNotFound e){
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		return userobj;

	}

	@Override
	public boolean deleteUser(int id) throws UserNotFound {
		try {
			userRepo.deleteById(id);
			return true;
		} catch (Exception e) {
			throw new UserNotFound("User Not Found");
		}
	}

}
