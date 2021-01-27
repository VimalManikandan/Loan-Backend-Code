package loan.loan.restclient;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import loan.loan.datamodel.UserD;


@FeignClient("Zuul-api-gateway")
public interface LoginClient {
	
	@GetMapping("/login-server/userApi/get/{id}")
	public UserD getLogin (@PathVariable("id") int id);
	

}
