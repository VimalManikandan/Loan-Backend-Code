package login.login.service;

import login.login.model.User;

public interface UserService {
	
	public User registerUser(User user);
	public User getUser(int id) ;
	public User updateUser(User user);
	public boolean deleteUser(int id) ;
}
