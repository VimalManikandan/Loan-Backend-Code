package login.login;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import login.login.repo.UserRepo;
import login.login.service.UserAuthService;
import login.login.service.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest
class LoginApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAuthService authService;
	
	@MockBean
	private UserRepo repo;
}
