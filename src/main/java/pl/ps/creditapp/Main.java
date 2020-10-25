package pl.ps.creditapp;

import pl.ps.creditapp.client.ConsoleReader;
import pl.ps.creditapp.core.*;
import pl.ps.creditapp.core.model.CreditApplication;
import pl.ps.creditapp.core.model.SelfEmployed;
import pl.ps.creditapp.core.scoring.EducationCalculator;
import pl.ps.creditapp.core.scoring.IncomeCalculator;
import pl.ps.creditapp.core.scoring.MaritalStatusCalculator;

public class Main {

    public static void main(String[] args) {
        NaturalPersonScoringCalculator naturalPersonScoringCalcuator= new NaturalPersonScoringCalculator(new EducationCalculator(), new MaritalStatusCalculator(), new IncomeCalculator());
        SelfEmployedScoringCalculator selfEmployedScoringCalculator = new SelfEmployedScoringCalculator(new EducationCalculator(), new MaritalStatusCalculator(), new IncomeCalculator());
        PersonScoringCalculatorFactory personScoringCalculatorFactory = new PersonScoringCalculatorFactory(naturalPersonScoringCalcuator, selfEmployedScoringCalculator);

        CreditApplicationService service = new CreditApplicationService(personScoringCalculatorFactory, new CreditRatingCalculator());
        CreditApplication creditApplication = new ConsoleReader().readInputParameters();

        CreditApplicationDecision decision = service.getDecision(creditApplication);

        System.out.println(decision.getDecisionString());
    }
}
