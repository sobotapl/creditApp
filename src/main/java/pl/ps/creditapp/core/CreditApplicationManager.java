package pl.ps.creditapp.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import pl.ps.creditapp.core.model.CreditApplication;
import pl.ps.creditapp.core.model.ProcessedCreditApplication;
import pl.ps.creditapp.di.Inject;

import java.util.ArrayDeque;
import java.util.Deque;

public class CreditApplicationManager {
    private static final Logger log = LoggerFactory.getLogger(CreditApplicationManager.class);

    @Inject
    private CreditApplicationService creditApplicationService;

    @Inject
    private CreditApplicationDecisionFactory creditApplicationDecisionFactory;

    @Inject
    private FileManager fileManager;

    private Deque<CreditApplication> queue = new ArrayDeque<>();

    public CreditApplicationManager() {
    }

    public void add(CreditApplication creditApplication) {
        log.info(String.format("Application %s is added to queue", creditApplication.getId()));
        queue.addFirst(creditApplication);
    }

    public void startProcessing() {
        while (!queue.isEmpty()) {
            CreditApplication creditApplication = queue.pollLast();
            log.info(String.format("Starting processing application with id %s", creditApplication.getId()));
            CreditApplicationDecision decision = creditApplicationService.getDecision(creditApplication);
            log.info(creditApplicationDecisionFactory.getDecisionString(creditApplication, decision));
            fileManager.write(new ProcessedCreditApplication(creditApplication, decision));
            MDC.remove("id");
        }
    }

    public void loadApplication(String id) {
        final ProcessedCreditApplication read = fileManager.read(id);

        try {
            log.info(ObjectMapperService.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(read));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
