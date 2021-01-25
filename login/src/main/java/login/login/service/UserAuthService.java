package login.login.service;

import login.login.exception.UserNotFound;
import login.login.model.User;

public interface UserAuthService {
	
	public boolean loginUser(User user) throws UserNotFound;
	public boolean logoutUser(User user) throws UserNotFound;

}
