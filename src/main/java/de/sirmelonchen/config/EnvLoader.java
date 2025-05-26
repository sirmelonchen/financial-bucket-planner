package de.sirmelonchen.config;

import de.sirmelonchen.status.SetupStatus;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

/**
 * The type Env loader.
 */
@Configuration
public class EnvLoader {

    /**
     * Init.
     */
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
        System.setProperty("MAIL_HOST", dotenv.get("MAIL_HOST"));
        System.setProperty("MAIL_PORT", dotenv.get("MAIL_PORT"));
        System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
        System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
        System.setProperty("MAIL_AUTH", dotenv.get("MAIL_AUTH"));
        System.setProperty("MAIL_STARTTLS", dotenv.get("MAIL_STARTTLS"));
        System.setProperty("MAIL_FROM", dotenv.get("MAIL_FROM"));
        System.setProperty("SETUP", dotenv.get("SETUP"));
        SetupStatus setupStatus = new SetupStatus();
        setupStatus.setSetupCompleted(Boolean.parseBoolean(dotenv.get("SETUP")));
    }
}

