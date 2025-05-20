package de.sirmelonchen.config;

import jakarta.annotation.PostConstruct;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvLoader {

    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.configure()
                .filename(".env") // Optional, default ist ".env"
                .ignoreIfMissing()
                .load();

        System.setProperty("db.host", dotenv.get("DB_HOST"));
        System.setProperty("db.port", dotenv.get("DB_PORT"));
        System.setProperty("db.user", dotenv.get("DB_USER"));
        System.setProperty("db.pass", dotenv.get("DB_PASS"));
    }
}

