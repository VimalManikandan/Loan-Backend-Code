package login.login.service;

import login.login.model.User;

public interface UserAuthService {
	
	public boolean loginUser(User user) ;
	public boolean logoutUser(User user) ;

}
