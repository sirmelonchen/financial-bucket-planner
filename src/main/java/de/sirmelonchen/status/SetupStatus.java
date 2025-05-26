package de.sirmelonchen.status;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class SetupStatus {
    private static final String ENV_FILE = ".env";  // Pfad zur vorhandenen .env-Datei
    private static final String SETUP_KEY = "SETUP";

    private boolean setupCompleted = false;

    public SetupStatus() {
        Path envPath = Paths.get(ENV_FILE);
        if (Files.exists(envPath)) {
            try {
                List<String> lines = Files.readAllLines(envPath);
                for (String line : lines) {
                    if (line.startsWith(SETUP_KEY + "=")) {
                        String value = line.substring((SETUP_KEY + "=").length()).trim();
                        setupCompleted = Boolean.parseBoolean(value);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                setupCompleted = false;
            }
        }
    }

    public boolean isSetupCompleted() {
        return setupCompleted;
    }

    public void setSetupCompleted(boolean setupCompleted) {
        this.setupCompleted = setupCompleted;
        Path envPath = Paths.get(ENV_FILE);

        try {
            List<String> lines = new ArrayList<>();
            if (Files.exists(envPath)) {
                lines = Files.readAllLines(envPath);
                boolean found = false;

                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i);

                    // Zeile mit dem Schlüssel finden und ersetzen
                    if (line.startsWith(SETUP_KEY + "=")) {
                        lines.set(i, SETUP_KEY + "=" + setupCompleted);
                        found = true;
                        break; // nur erste passende Zeile ändern
                    }
                }

                // Wenn keine Zeile mit dem Schlüssel gefunden wurde, anhängen
                if (!found) {
                    // Optional: füge vorher eine Leerzeile ein, falls letzte Zeile nicht leer ist
                    if (!lines.isEmpty() && !lines.get(lines.size() - 1).isEmpty()) {
                        lines.add("");
                    }
                    lines.add(SETUP_KEY + "=" + setupCompleted);
                }
            }

            // Schreibe die komplette Datei mit der modifizierten Zeile zurück
            Files.write(envPath, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
