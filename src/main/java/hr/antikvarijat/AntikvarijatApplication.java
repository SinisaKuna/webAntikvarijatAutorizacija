package hr.antikvarijat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class AntikvarijatApplication {

	public static void main(String[] args) {

		System.setProperty("file.encoding", "UTF-8");

		SpringApplication.run(AntikvarijatApplication.class, args);
	}

}
