package pl.ps.creditapp.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import pl.ps.creditapp.core.exception.ValidationException;
import pl.ps.creditapp.core.model.CreditApplication;
import pl.ps.creditapp.core.model.NaturalPerson;
import pl.ps.creditapp.core.model.Person;
import pl.ps.creditapp.core.validation.CreditApplicationValidator;

import java.util.UUID;

import static pl.ps.creditapp.core.Constants.MIN_LOAN_AMOUNT_MORTGAGE;
import static pl.ps.creditapp.core.DecisionType.*;


public class CreditApplicationService {
    private static final Logger log = LoggerFactory.getLogger(CreditApplicationService.class);
    private final PersonScoringCalculatorFactory personScoringCalculatorFactory;
    private final CreditRatingCalculator creditRatingCalculator;
    private final CreditApplicationValidator creditApplicationValidator;

    public CreditApplicationService(PersonScoringCalculatorFactory personScoringCalculatorFactory, CreditRatingCalculator creditRatingCalculator, CreditApplicationValidator creditApplicationValidator) {
        this.personScoringCalculatorFactory = personScoringCalculatorFactory;
        this.creditRatingCalculator = creditRatingCalculator;
        this.creditApplicationValidator = creditApplicationValidator;
    }

    public CreditApplicationDecision getDecision(CreditApplication creditApplication) {
        String id = UUID.randomUUID().toString();
        log.info("Application ID is " + id);
        MDC.put("id", id);

        try {
            creditApplicationValidator.validate(creditApplication);

            Person person = creditApplication.getPerson();
            int scoring = personScoringCalculatorFactory.getCalculator(person).calculate(person);
            CreditApplicationDecision decision;
            if (scoring < 300) {
                decision = new CreditApplicationDecision(NEGATIVE_SCORING, person.getPersonalData(), null, scoring);
            } else if (scoring <= 400) {
                decision = new CreditApplicationDecision(CONTACT_REQUIRED, person.getPersonalData(), null, scoring);
            } else {
                double creditRate = creditRatingCalculator.calculate(creditApplication);
                if (creditRate >= creditApplication.getPurposeOfLoan().getAmount()) {
                    if (creditApplication.getPurposeOfLoan().getAmount() < MIN_LOAN_AMOUNT_MORTGAGE) {
                        decision = new CreditApplicationDecision(NEGATIVE_REQUIREMENTS_NOT_MET, person.getPersonalData(), creditRate, scoring);
                    } else {
                        decision = new CreditApplicationDecision(POSITIVE, person.getPersonalData(), creditRate, scoring);
                    }
                } else {
                    decision = new CreditApplicationDecision(NEGATIVE_RATING, person.getPersonalData(), creditRate, scoring);
                }
            }
            log.info("Decision = " + decision.getType());
            return decision;
        } catch (ValidationException validationException) {
            log.error(validationException.getMessage());
            throw new IllegalStateException();
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new IllegalStateException();
        }
        finally {
            log.info("Application processing is finished");
        }
    }


}