package hr.antikvarijat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class AntikvarijatApplication {

	public static void main(String[] args) {
		SpringApplication.run(AntikvarijatApplication.class, args);
	}

}
