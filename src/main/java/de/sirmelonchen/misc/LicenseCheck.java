// This file is part of the SirMelonchen Modpack.

package de.sirmelonchen.misc;

import dev.respark.licensegate.LicenseGate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * The type License check.
 */
public class LicenseCheck {
    private String licenseKey;

    /**
     * Instantiates a new License check.
     */
    public LicenseCheck() {
    }

    /**
     * Check key boolean.
     *
     * @return the boolean
     */
    public boolean checkKey(){
        return new LicenseGate("a1fce")
                .verify(this.licenseKey)
                .isValid();
    }

    /**
     * Get key.
     */
    public void getKey(){
        String keyFilePath = "license.key";
        Path path = Paths.get(keyFilePath);
        try {
            // Read all lines into a List
            List<String> lines = Files.readAllLines(path);

            // Print each line
            for (String line : lines) {
                this.licenseKey = line;
            }

        } catch (IOException e) {
            System.out.println("Keine KeyFile gefunden");
            System.exit(1);
        }
    }
}
