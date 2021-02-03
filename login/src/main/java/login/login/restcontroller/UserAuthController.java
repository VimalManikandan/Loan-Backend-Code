package login.login.restcontroller;

import org.springframework.http.HttpStatus;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import login.login.model.GenericResponce;
import login.login.model.User;
import login.login.service.UserAuthService;

@RestController
@RequestMapping("/loginApi")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class UserAuthController {

	@Autowired
	UserAuthService authService;


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<User> loginUser(@Valid  @RequestBody User user) {

			User result=authService.loginUser(user);

		return new ResponseEntity<User>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
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
