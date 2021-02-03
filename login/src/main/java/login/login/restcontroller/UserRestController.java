package login.login.restcontroller;



import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import login.login.model.GenericResponce;
import login.login.model.User;
import login.login.service.UserService;


@RestController
@Validated
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/userApi")
public class UserRestController {
	
	public static Logger logger=LoggerFactory.getLogger(UserRestController.class);
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ResponseEntity<User> createUser(@Valid  @RequestBody User userObj)  {
		User obj=null;
		obj= userService.registerUser(userObj);
		return new ResponseEntity<User>(obj,HttpStatus.OK);
	}
	
	@RequestMapping(value="/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<User>  getUser( @PathVariable("id") int id)  {
		User userobj=null;
		userobj=userService.getUser(id);
		return new ResponseEntity<User>(userobj,HttpStatus.OK);
				
	}
	
	@RequestMapping(value="/update", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@Valid  @RequestBody User user) {
		User userobj=null;
		userobj=userService.updateUser(user);
		return new ResponseEntity<User>(userobj,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<GenericResponce> deleteUser( @PathVariable("id") int id) {
		boolean rslt=false;
		GenericResponce responce=new GenericResponce();
			if(userService.deleteUser(id)){
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
