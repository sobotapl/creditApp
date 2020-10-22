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
            decision = new CreditApplicationDecision(DecisionType.NEGATIVE_SCORING, person.getPersonalData(), null, scoring);
        } else if (scoring <= 400) {
            decision = new CreditApplicationDecision(DecisionType.CONTACT_REQUIRED, person.getPersonalData(), null, scoring);
        } else {
            double creditRate = creditRatingCalculator.calculate(creditApplication);
            if (creditRate >= creditApplication.getPurposeOfLoan().getAmount()) {
                if(creditApplication.getPurposeOfLoan().getAmount() < Constants.MIN_LOAN_AMOUNT_MORTGAGE){
                    decision = new CreditApplicationDecision(DecisionType.NEGATIVE_REQUIREMENTS_NOT_MET, person.getPersonalData(), creditRate, scoring);
                }else {
                    decision = new CreditApplicationDecision(DecisionType.POSITIVE, person.getPersonalData(), creditRate, scoring);
                }
            } else {
                decision = new CreditApplicationDecision(DecisionType.NEGATIVE_RATING, person.getPersonalData(), creditRate, scoring);
            }
        }
        log.info("Decision = " + decision.getType());
        return decision;
    }


}