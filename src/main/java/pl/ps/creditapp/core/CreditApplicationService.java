package pl.ps.creditapp.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import pl.ps.creditapp.core.model.CreditApplication;
import pl.ps.creditapp.core.model.Person;

import java.util.UUID;

public class CreditApplicationService {
    private static final Logger log = LoggerFactory.getLogger(CreditApplicationService.class);
    private final PersonScoringCalculator personScoringCalculator;
    private final CreditRatingCalculator creditRatingCalculator;

    public CreditApplicationService(PersonScoringCalculator calculator, CreditRatingCalculator creditRatingCalculator) {
        this.personScoringCalculator = calculator;
        this.creditRatingCalculator = creditRatingCalculator;
    }

    public CreditApplicationDecision getDecision(CreditApplication creditApplication) {
        String id = UUID.randomUUID().toString();
        log.info("Application ID is " + id);
        MDC.put("id", id);


        Person person = creditApplication.getPerson();
        int scoring = personScoringCalculator.calculate(person);
        CreditApplicationDecision decision;
        if (scoring < 300) {
            decision = new CreditApplicationDecision(DecisionType.NEGATIVE_SCORING, person.getPersonalData(), null);
        } else if (scoring <= 400) {
            decision = new CreditApplicationDecision(DecisionType.CONTACT_REQUIRED, person.getPersonalData(), null);
        } else {
            double creditRate = creditRatingCalculator.calculate(creditApplication);
            if (creditRate >= creditApplication.getPurposeOfLoan().getAmount()) {
                decision = new CreditApplicationDecision(DecisionType.POSITIVE, person.getPersonalData(), creditRate);
            } else {
                decision = new CreditApplicationDecision(DecisionType.NEGATIVE_RATING, person.getPersonalData(), creditRate);
            }
        }
        log.info("Decision = " + decision.getType());
        return decision;
    }


}
