package loan.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
//@EnableHystrix
public class LoanAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanAppApplication.class, args);
	}

}
