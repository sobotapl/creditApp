package pl.ps.creditapp.client;

import pl.ps.creditapp.util.ObjectMapperService;
import pl.ps.creditapp.core.model.CreditApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileCreditApplicationReader implements CreditApplicationReader {
    private final Path path;

    public FileCreditApplicationReader(Path path) {
        this.path = path;
    }

    @Override
    public CreditApplication read() {
        try {
            String content = Files.readString(path);
            return ObjectMapperService.OBJECT_MAPPER.readValue(content, CreditApplication.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
}
