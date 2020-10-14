package pl.ps.creditapp;

import pl.ps.creditapp.client.ConsoleReader;
import pl.ps.creditapp.core.CreditApplicationDecision;
import pl.ps.creditapp.core.CreditApplicationService;
import pl.ps.creditapp.core.CreditRatingCalculator;
import pl.ps.creditapp.core.PersonScoringCalculator;
import pl.ps.creditapp.core.model.CreditApplication;
import pl.ps.creditapp.core.scoring.EducationCalculator;
import pl.ps.creditapp.core.scoring.IncomeCalculator;
import pl.ps.creditapp.core.scoring.MaritalStatusCalculator;

public class Main {

    public static void main(String[] args) {
        PersonScoringCalculator calculator = new PersonScoringCalculator(new EducationCalculator(), new MaritalStatusCalculator(), new IncomeCalculator());
        CreditApplicationService service = new CreditApplicationService(calculator, new CreditRatingCalculator());
        CreditApplication creditApplication = new ConsoleReader().readInputParameters();

        CreditApplicationDecision decision = service.getDecision(creditApplication);

        System.out.println(decision.getDecisionString());
    }
}
