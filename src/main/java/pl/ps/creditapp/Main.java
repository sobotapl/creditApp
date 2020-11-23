package pl.ps.creditapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.ps.creditapp.client.CreditApplicationReader;
import pl.ps.creditapp.client.DummyCreditApplicationReader;
import pl.ps.creditapp.client.FileCreditApplicationReader;
import pl.ps.creditapp.core.*;
import pl.ps.creditapp.core.model.CreditApplication;
import pl.ps.creditapp.core.scoring.*;
import pl.ps.creditapp.core.validation.*;
import pl.ps.creditapp.core.validation.reflection.*;
import pl.ps.creditapp.di.ClassInitializer;
import pl.ps.creditapp.integration.BikApiAdapter;

import java.nio.file.*;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    static {
        TimeZone.setDefault(TimeZone.getTimeZone(Constants.DEFAULT_SYSTEM_ZONE_ID));
        Locale.setDefault(Constants.DEFAULT_LOCALE);
    }

    public static void main(String[] args) throws Exception {

        CreditApplicationReader reader = new DummyCreditApplicationReader();
        List<FieldAnnotationProcessor> fieldProcessors = List.of(new NotNullAnnotationProcessor(), new RegexAnnotationProcessor());
        List<ClassAnnotationProcessor> classProcessors = List.of(new ExactlyOneNotNullAnnotationProcessor());
        final ObjectValidator objectValidator = new ObjectValidator(fieldProcessors, classProcessors);
        CompoundPostValidator compoundPostValidator = new CompoundPostValidator(new PurposeOfLoanPostValidator(), new ExpansesPostValidator());
        ClassInitializer classInitializer = new ClassInitializer();
        classInitializer.registerInstance(compoundPostValidator);
        classInitializer.registerInstance(objectValidator);
        classInitializer.registerInstance(new BikScoringCalculator(new BikApiAdapter()));
        CreditApplicationManager manager = (CreditApplicationManager) classInitializer.createInstance(CreditApplicationManager.class);
        manager.init();
        if (args != null && args.length == 2 && args[1].matches("[NS]-\\d*")) {
            String appId = args[0];
            String personId = args[1];
            manager.loadApplication(appId, personId);
        } else {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path homeDir = Paths.get(Constants.OUTPUT_PATH);
            homeDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            WatchKey watchKey;
            while ((watchKey = watchService.take()) != null) {
                log.info("New event fired");
                for (WatchEvent event : watchKey.pollEvents()) {
                    log.info("New file detected {}", event.context());
                    if (event.context().toString().endsWith(".json")) {
                        Path pathToFile = homeDir.resolve(event.context().toString());
                        final CreditApplication creditApplication = new FileCreditApplicationReader(pathToFile).read();
                        creditApplication.init();
                        manager.add(creditApplication);
                        Files.deleteIfExists(pathToFile);

                    } else {
                        log.info("File processing {} skipped", event.context());
                    }
                }
                manager.startProcessing();
                watchKey.reset();
            }
        }
    }
}
