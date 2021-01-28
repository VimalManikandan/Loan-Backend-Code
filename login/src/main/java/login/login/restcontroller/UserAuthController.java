package login.login.restcontroller;

import org.springframework.http.HttpStatus;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import login.login.model.GenericResponce;
import login.login.model.User;
import login.login.service.UserAuthService;

@RestController
@RequestMapping("/loginApi")
@Validated
public class UserAuthController {

	@Autowired
	UserAuthService authService;


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<GenericResponce> loginUser(@Valid  @RequestBody User user) {
		GenericResponce responce=new GenericResponce();		
		
			 if(authService.loginUser(user)){
				 responce.setStatus(HttpStatus.OK.value());
				 responce.setMessage("SUCCESS");
			 }
			 else{
				 responce.setStatus(HttpStatus.NOT_FOUND.value());
				 responce.setMessage("FAILED");
			 }
		return new ResponseEntity<GenericResponce>(responce, HttpStatus.OK);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<GenericResponce> logoutUser(@Valid @RequestBody User user){
		GenericResponce responce=new GenericResponce();
			if(authService.logoutUser(user)){
				 responce.setStatus(HttpStatus.OK.value());
				 responce.setMessage("SUCCESS");
			 }
			 else{
				 responce.setStatus(HttpStatus.NOT_FOUND.value());
				 responce.setMessage("FAILED");
			 }

		return new ResponseEntity<GenericResponce>(responce, HttpStatus.OK);		
	}
}
