package ma.agilisys.devis.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {
    private final Path rootLocation = Paths.get("uploads");

    public String storePdf(byte[] pdfContent, String filename) {
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }

            Path destinationFile = rootLocation.resolve(filename)
                    .normalize()
                    .toAbsolutePath();

            Files.write(destinationFile, pdfContent);
            return "/uploads/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Échec du stockage du fichier", e);
        }
    }
}
