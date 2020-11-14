package pl.ps.creditapp;

import pl.ps.creditapp.client.CreditApplicationReader;
import pl.ps.creditapp.client.DummyCreditApplicationReader;
import pl.ps.creditapp.core.*;
import pl.ps.creditapp.core.scoring.EducationCalculator;
import pl.ps.creditapp.core.scoring.GuarantorsCalculator;
import pl.ps.creditapp.core.scoring.IncomeCalculator;
import pl.ps.creditapp.core.scoring.MaritalStatusCalculator;
import pl.ps.creditapp.core.validation.*;




public class Main {

    public static void main(String[] args) {
        CreditApplicationReader reader = new DummyCreditApplicationReader();
        EducationCalculator educationCalculator = new EducationCalculator();
        MaritalStatusCalculator maritalStatusCalculator = new MaritalStatusCalculator();
        IncomeCalculator incomeCalculator = new IncomeCalculator();
        SelfEmployedScoringCalculator selfEmployedScoringCalculator = new SelfEmployedScoringCalculator();
        GuarantorsCalculator guarantorsCalculator = new GuarantorsCalculator();
        PersonScoringCalculatorFactory personScoringCalculatorFactory = new PersonScoringCalculatorFactory(selfEmployedScoringCalculator, educationCalculator, maritalStatusCalculator, incomeCalculator, guarantorsCalculator);
        GuarantorValidator guarantorValidator = new GuarantorValidator();
        CreditApplicationValidator creditApplicationValidator = new CreditApplicationValidator(new PersonValidator(new PersonalDataValidator()), new PurposeOfLoanValidator(), guarantorValidator);
        CompoundPostValidator compoundPostValidator = new CompoundPostValidator(new PurposeOfLoanPostValidator(), new ExpansesPostValidator());
        CreditApplicationService service = new CreditApplicationService(personScoringCalculatorFactory, new CreditRatingCalculator(), creditApplicationValidator, compoundPostValidator);
        CreditApplicationManager manager = new CreditApplicationManager(service);

        manager.add(reader.read());

        manager.startProcessing();
    }
}
