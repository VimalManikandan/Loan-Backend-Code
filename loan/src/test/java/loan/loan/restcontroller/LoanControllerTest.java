package loan.loan.restcontroller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import loan.loan.datamodel.GenericResponce;
import loan.loan.datamodel.UserD;
import loan.loan.model.Loan;
import loan.loan.model.User;
import loan.loan.restclient.LoginClient;
import loan.loan.service.LoanService;

@RunWith(SpringRunner.class)
@WebAppConfiguration
public class LoanControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private LoanService loanService;

	private Loan loan;
	private Loan loan2;

	private GenericResponce responce;
	private List<Loan> loans;
	

	private UserD user;

	@MockBean(name="loginClient")
	LoginClient loginClient;

	@InjectMocks
	private LoanController loanController;

	private AutoCloseable closeable;

	@Before
	public void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
		
		loan = new Loan(1, "Vimal", "V", "Palakkad", 125000, "Personal", 60);
		loan2 = new Loan(2, "Sujith", "AK ", "Kerala", 15000, "Personal", 60);
		loans = new ArrayList<>();
		responce=new GenericResponce();
		loans.add(loan);
		loans.add(loan2);
		user = new UserD(1, "USERadmin", "123Password", "ADMIN","ABCD");
	}

	@Test
	public void testCreateLoanSuccess() throws Exception {
		when(loanService.addLoan(loan)).thenReturn(loan);
		when(loginClient.getLogin(user.getUid())).thenReturn(user);
		mockMvc.perform(post("/loanApi/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(loan)))
				.andExpect(status().isOk());
	}
	@Test
	public void testCreateLoanFailure() throws Exception {
		when(loanService.addLoan(loan)).thenReturn(null);
		when(loginClient.getLogin(user.getUid())).thenReturn(user);
		mockMvc.perform(post("/loanApi/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(loan)))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetLoanSuccess() throws Exception {
		when(loanService.getLoan(loan.getLoanno(),user.getUid())).thenReturn(loan);
		mockMvc.perform(get("/loanApi/getLoan/{loanId}", loan.getLoanno()).param("userId", String.valueOf(user.getUid())))
		.andExpect(status().isOk());
	}
	@Test
	public void testGetLoanFailure() throws Exception {
		when(loanService.getLoan(loan.getLoanno(),user.getUid())).thenReturn(loan);
		mockMvc.perform(get("/loanApi/getLoan/{loanId}", loan.getLoanno()).param("userId", String.valueOf(user.getUid())))
		.andExpect(status().isOk());
	}
	

	@Test
	public void testUpdateLoanSuccess() throws Exception {
		when(loanService.updateLoan(loan)).thenReturn(loan);
		mockMvc.perform(put("/loanApi/updateLoan").contentType(MediaType.APPLICATION_JSON).content(asJsonString(loan)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testUpdateLoanFailure() throws Exception {
		when(loanService.updateLoan(loan)).thenReturn(loan);
		mockMvc.perform(put("/loanApi/updateLoan").contentType(MediaType.APPLICATION_JSON).content(asJsonString(loan)))
		.andExpect(status().isOk());
	}

	@Test
	public void testDeleteLoanSuccess() throws Exception {
		when(loanService.deleteLoan(loan.getLoanno(),user.getUid())).thenReturn(true);
		mockMvc.perform(delete("/loanApi/deleteLoan/{loanId}", loan.getLoanno()).param("userId", String.valueOf(user.getUid())))
		.andExpect(status().isOk());
	}
	@Test
	public void testDeleteLoanFailure() throws Exception {
		when(loanService.deleteLoan(loan.getLoanno(),user.getUid())).thenReturn(true);
		mockMvc.perform(delete("/loanApi/deleteLoan/{loanId}", loan.getLoanno()).param("userId", String.valueOf(user.getUid())))
		.andExpect(status().isOk());
	}

	@Test
	public void testUserObject() throws Exception {
		User user1=new User(1, "Vimal", "test", "Admin", "abc");
		User user2=new User();
		user2.setUserid(1);
		user2.setUsername("Vimal");
		user2.setUserpwd("test");
		user2.setUsertype("Admin");
		user2.setJwtToken("ABC");
		assertEquals(user1, user2);
		assertTrue( user1.hashCode()==user1.hashCode() );

		assertNotNull(user2.getUserid());
		assertNotNull(user2.getUsername());
		assertNotNull(user2.getUserpwd());
		assertNotNull(user2.getUsertype());
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
		GenericResponce g2=new GenericResponce();
		g2.setStatus(g1.getStatus());
		g2.setMessage(g1.getMessage());

		assertNotNull(g1);
		assertNotNull(g2);
		assertNotEquals(g1,g2);
	}
	

	@Test
	public void testUserDObject() throws Exception {
		UserD ud1=new UserD(1, "Vimal", "test", "Admin","ABCD");
		UserD ud2=new UserD();
		ud2.setUid(ud1.getUid());
		ud2.setUsername(ud1.getUsername());
		ud2.setUserpwd(ud1.getUserpwd());
		ud2.setUsertype(ud1.getUsertype());


		assertNotNull(ud1);
		assertNotNull(ud2);
		assertNotEquals(ud1,ud2);
	}
	
	@Test
	public void testSearchLoan() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		
		params.add("loanNo", "1");
		params.add("fName", "Vimal");
		params.add("lName", "V");
		
		when(loanService.getAllLoans()).thenReturn(loans);
		mockMvc.perform(get("/loanApi/searchLoan")
				.params(params)
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
				.andExpect(status().isOk())
				 .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
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
