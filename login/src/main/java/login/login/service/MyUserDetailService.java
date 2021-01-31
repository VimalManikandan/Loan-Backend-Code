package login.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import login.login.model.MyUserDetails;
import login.login.repo.UserRepo;





@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		
		login.login.model.User user= userRepo.findByUsername(s);
		return new MyUserDetails(user);
	}

}
