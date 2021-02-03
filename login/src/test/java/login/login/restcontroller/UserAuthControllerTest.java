package login.login.restcontroller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import login.login.model.User;
import login.login.service.UserAuthService;



@RunWith(SpringRunner.class)
@WebAppConfiguration
public class UserAuthControllerTest {	
	
	public static Logger logger = LoggerFactory.getLogger(UserAuthControllerTest.class);
	 
	private MockMvc mockMvc;

	@MockBean
	private UserAuthService userAuthService;
	

	private User user;
	
	@InjectMocks
	UserAuthController userAuthControleller;
	
	private AutoCloseable closeable;
	
	@Before
	public void setUp(){
		closeable = MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userAuthControleller).build();
		user= new User(1, "USet", "123Password", "USER", "xyz");		
	}

	
	@Test
	public void testLoginUserSuccess() throws Exception {
		when(userAuthService.loginUser(user)).thenReturn(user);
		mockMvc.perform(post("/loginApi/login").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
				.andExpect(status().isOk())
		 		.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
		 		.andDo(MockMvcResultHandlers.print());
		
		
	}
	
	@Test
	public void testLoginUserFailure() throws Exception {
		when(userAuthService.loginUser(user)).thenReturn(user);
		mockMvc.perform(post("/loginApi/login").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
				.andExpect(status().isOk())
				 .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}


	@Test
	public void testLogoutUserSuccess() throws Exception {
		when(userAuthService.logoutUser(user)).thenReturn(true);
		mockMvc.perform(post("/loginApi/logout").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
		 		.andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void testLogoutUserFailure() throws Exception {
		when(userAuthService.logoutUser(user)).thenReturn(true);
		mockMvc.perform(post("/loginApi/logout").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
		 		.andDo(MockMvcResultHandlers.print());
	}
	
	
	public static String asJsonString(final Object obj) {
		try {

			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }
}
