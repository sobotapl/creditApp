package pl.ps.creditapp.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import pl.ps.creditapp.core.model.CreditApplication;

import java.util.ArrayDeque;
import java.util.Deque;

public class CreditApplicationManager {
    private static final Logger log = LoggerFactory.getLogger(CreditApplicationManager.class);
    private final CreditApplicationService creditApplicationService;
    private final Deque<CreditApplication> queue = new ArrayDeque<>();

    public CreditApplicationManager(CreditApplicationService creditApplicationService) {
        this.creditApplicationService = creditApplicationService;
    }

    public void add(CreditApplication creditApplication){
        log.info(String.format("Application %s is added to queue",creditApplication.getId()));
        queue.addFirst(creditApplication);
    }

    public void startProcessing(){
        while (!queue.isEmpty()){
            CreditApplication creditApplication = queue.pollLast();
            log.info(String.format("Starting processing application with id %s",creditApplication.getId()));
            CreditApplicationDecision decision = creditApplicationService.getDecision(creditApplication);
            log.info(decision.getDecisionString());
            MDC.remove("id");
        }
    }
}
