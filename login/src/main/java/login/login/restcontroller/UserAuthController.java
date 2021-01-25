package login.login.restcontroller;

import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import login.login.model.GenericResponce;
import login.login.model.User;
import login.login.service.UserAuthService;

@RestController
@RequestMapping("/loginApi")
public class UserAuthController {

	@Autowired
	UserAuthService authService;

	public static Logger logger = LoggerFactory.getLogger(UserAuthController.class);

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<GenericResponce> loginUser(@RequestBody User user) {
		GenericResponce responce=new GenericResponce();
		
		try {
			 if(authService.loginUser(user)){
				 responce.setStatus(HttpStatus.OK.value());
				 responce.setMessage("SUCCESS");
			 }
			 else{
				 responce.setStatus(HttpStatus.NOT_FOUND.value());
				 responce.setMessage("FAILED");
			 }
				 
		} catch (Exception e) {
			logger.error("Exception occcured: " + e);
		}
		return new ResponseEntity<GenericResponce>(responce, HttpStatus.OK);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<GenericResponce> logoutUser(@RequestBody User user) {
		GenericResponce responce=new GenericResponce();
		try {
			if(authService.logoutUser(user)){
				 responce.setStatus(HttpStatus.OK.value());
				 responce.setMessage("SUCCESS");
			 }
			 else{
				 responce.setStatus(HttpStatus.NOT_FOUND.value());
				 responce.setMessage("FAILED");
			 }
			
		} catch (Exception e) {
			logger.error("Exception occcured: " + e);
			
		}
		return new ResponseEntity<GenericResponce>(responce, HttpStatus.OK);		
	}
}
