package loan.loan.restcontroller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import loan.loan.datamodel.UserD;
import loan.loan.model.Loan;
import loan.loan.restclient.LoginClient;
import loan.loan.service.LoanService;


@RunWith(SpringRunner.class)
@WebAppConfiguration
public class LoanControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private LoanService loanService;

	private Loan loan;
	
	private UserD user;
	
	@MockBean
	LoginClient loginClient;

	@InjectMocks
	private LoanController loanController;

	private AutoCloseable closeable;

	@Before
	public void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
		loan = new Loan(1, "Vimal", "V", "Palakkad", 125000, "Personal", 60);
		user=new UserD(1, "USERadmin", "123Password", "ADMIN");
	}

	@Test
	public void testCreateLoan() throws Exception {
		when(loanService.addLoan(loan)).thenReturn(loan);
		when(loginClient.getLogin(user.getUid())).thenReturn(user);
		mockMvc.perform(post("/loanApi/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(loan)))
		.andExpect(status().isOk());		
	}
	

	@Test
	public void testGetLoan() throws Exception {
		when(loanService.getLoan(loan.getLoanno())).thenReturn(loan);
		mockMvc.perform(get("/getLoan/{loanId}",loan.getLoanno()))
		.andExpect(status().isOk());
	}

	@Test
	public void testUpdateLoan() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteLoan() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchLoan() {
		fail("Not yet implemented");
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
