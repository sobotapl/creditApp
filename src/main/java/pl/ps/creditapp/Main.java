package pl.ps.creditapp;

import pl.ps.creditapp.client.ConsoleReader;
import pl.ps.creditapp.client.CreditApplicationReader;
import pl.ps.creditapp.client.DummyCreditApplicationReader;
import pl.ps.creditapp.core.*;
import pl.ps.creditapp.core.model.CreditApplication;
import pl.ps.creditapp.core.model.SelfEmployed;
import pl.ps.creditapp.core.scoring.EducationCalculator;
import pl.ps.creditapp.core.scoring.IncomeCalculator;
import pl.ps.creditapp.core.scoring.MaritalStatusCalculator;
import pl.ps.creditapp.core.validation.CreditApplicationValidator;
import pl.ps.creditapp.core.validation.PersonValidator;
import pl.ps.creditapp.core.validation.PersonalDataValidator;
import pl.ps.creditapp.core.validation.PurposeOfLoanValidator;

public class Main {

    public static void main(String[] args) {
        CreditApplicationReader reader = new DummyCreditApplicationReader();
        EducationCalculator educationCalculator = new EducationCalculator();
        MaritalStatusCalculator maritalStatusCalculator = new MaritalStatusCalculator();
        IncomeCalculator incomeCalculator = new IncomeCalculator();
        SelfEmployedScoringCalculator selfEmployedScoringCalculator = new SelfEmployedScoringCalculator();
        PersonScoringCalculatorFactory personScoringCalculatorFactory = new PersonScoringCalculatorFactory(selfEmployedScoringCalculator, educationCalculator, maritalStatusCalculator, incomeCalculator);
        CreditApplicationValidator creditApplicationValidator = new CreditApplicationValidator(new PersonValidator(new PersonalDataValidator()),new PurposeOfLoanValidator());
        CreditApplicationService service = new CreditApplicationService(personScoringCalculatorFactory, new CreditRatingCalculator(), creditApplicationValidator);
        CreditApplication creditApplication = reader.read();

        CreditApplicationDecision decision = service.getDecision(creditApplication);

        System.out.println(decision.getDecisionString());
    }
}
