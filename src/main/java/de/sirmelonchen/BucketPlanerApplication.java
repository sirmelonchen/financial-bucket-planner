package de.sirmelonchen;

import de.sirmelonchen.config.EnvLoader;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BucketPlanerApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

		System.setProperty("db.host", dotenv.get("DB_HOST"));
		System.setProperty("db.port", dotenv.get("DB_PORT"));
		System.setProperty("db.user", dotenv.get("DB_USER"));
		System.setProperty("db.pass", dotenv.get("DB_PASS"));


		SpringApplication.run(BucketPlanerApplication.class, args);
	}

}
