package login.login.service;

import login.login.exception.UserAlreadyExist;
import login.login.exception.UserNotFound;
import login.login.model.User;

public interface UserService {
	
	public User registerUser(User user) throws UserAlreadyExist;
	public User getUser(int id) throws UserNotFound;
	public User updateUser(User user) throws UserNotFound;
	public boolean deleteUser(int id) throws UserNotFound;
}
