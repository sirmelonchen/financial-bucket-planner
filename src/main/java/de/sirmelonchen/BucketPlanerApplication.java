package de.sirmelonchen;

import de.sirmelonchen.config.EnvLoader;
import de.sirmelonchen.misc.LicenseCheck;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BucketPlanerApplication {

	public static void main(String[] args) {
		EnvLoader envLoader = new EnvLoader();
		envLoader.init();


		LicenseCheck licenseCheck = new LicenseCheck();
		licenseCheck.getKey();
		boolean isValid = licenseCheck.checkKey();
		if (isValid){
			SpringApplication.run(BucketPlanerApplication.class, args);
		}
	}

}
