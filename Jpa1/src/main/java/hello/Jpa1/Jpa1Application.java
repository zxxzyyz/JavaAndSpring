package hello.Jpa1;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Jpa1Application {

	public static void main(String[] args) {
		SpringApplication.run(Jpa1Application.class, args);
	}

	// For : OrderSimpleApiController.orderV1()
	@Bean
	Hibernate5Module hibernate5Module() {
		return new Hibernate5Module();
	}

}
