package de.sirmelonchen;

import de.sirmelonchen.config.EnvLoader;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BucketPlanerApplication {

	public static void main(String[] args) {
		EnvLoader envLoader = new EnvLoader();
		envLoader.init();


		SpringApplication.run(BucketPlanerApplication.class, args);
	}

}
