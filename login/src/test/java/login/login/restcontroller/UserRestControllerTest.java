package login.login.restcontroller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import login.login.model.GenericResponce;
import login.login.model.Loan;
import login.login.model.User;
import login.login.service.UserService;

@RunWith(SpringRunner.class)
@WebAppConfiguration
public class UserRestControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@InjectMocks
	private UserRestController userRestController;

	private AutoCloseable closeable;
	
	
	private User user;

	@Before
	public void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
		user = new User(1, "USet", "123Password", "USER", "xyz");
	}

	@Test
	public void testCreateUser() throws Exception {
		when(userService.registerUser(user)).thenReturn(user);
		mockMvc.perform(post("/userApi/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetUser() throws Exception {
		when(userService.getUser(user.getUserid())).thenReturn(user);
		mockMvc.perform(get("/userApi/get/{id}",user.getUserid()))
		.andExpect(status().isOk());
	}

	@Test
	public void testUpdateUser() throws Exception {
		when(userService.updateUser(user)).thenReturn(user);
		mockMvc.perform(put("/userApi/update").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
		.andExpect(status().isOk());
	}

	@Test
	public void testDeleteUser() throws Exception {
		when(userService.deleteUser(user.getUserid())).thenReturn(true);
		mockMvc.perform(delete("/userApi/delete/{id}",user.getUserid()))
		.andExpect(status().isOk());
	}

	@Test
	public void testUserObject() throws Exception {
		User user1=new User(1, "Vimal", "test", "Admin", "abc");
		User user2=new User(1, "Vimal", "test", "Admin", "xyz");
		assertEquals(user1, user2);
		assertTrue( user1.hashCode()==user1.hashCode() );
	}
	
	@Test
	public void testLoanObject() throws Exception {
		Loan l1= new Loan(1, "Vimal", "V", "Palakkad", 125000, "Personal", 60);
		Loan l2= new Loan();
		l2.setLoanno(1);
		l2.setFname("Vimal");
		l2.setFname("V");
		l2.setPaddress("Palakkad");
		l2.setLoanAmount(125000);
		l2.setLoantype("Personal");
		l2.setLoanterm(60);

		assertEquals(l1, l2);
		assertNotNull(l1.getLoanno());
		assertNotNull(l1.getFname());
		assertNotNull(l1.getLname());
		assertNotNull(l1.getPaddress());
		assertNotNull(l1.getLoanAmount());
		assertNotNull(l1.getLoantype());
		assertNotNull(l1.getLoanterm());

		assertTrue( l1.hashCode()==l2.hashCode() );
	}

	@Test
	public void testGenericObject() throws Exception {
		GenericResponce g1=new GenericResponce(HttpStatus.OK.value(),"Success");
		assertNotNull(g1);
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
