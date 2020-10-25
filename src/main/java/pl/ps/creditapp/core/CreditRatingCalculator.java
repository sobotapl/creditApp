package pl.ps.creditapp.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.ps.creditapp.core.model.CreditApplication;
import pl.ps.creditapp.core.model.Person;

import java.math.BigDecimal;

import static pl.ps.creditapp.core.Constants.MORTGAGE_LOAN_RATE;
import static pl.ps.creditapp.core.Constants.PERSONAL_LOAN_LOAN_RATE;

public class CreditRatingCalculator {
    private static final Logger log = LoggerFactory.getLogger(CreditRatingCalculator.class);

    public double calculate(CreditApplication creditApplication) {
        Person person = creditApplication.getPerson();
        double creditRate = person.getIncomePerFamilyMember() * 12 * creditApplication.getPurposeOfLoan().getPeriod();
        switch (creditApplication.getPurposeOfLoan().getPurposeOfLoanType()) {
            case PERSONAL_LOAN:
                creditRate *= PERSONAL_LOAN_LOAN_RATE;
                break;
            case MORTGAGE:
                creditRate *= MORTGAGE_LOAN_RATE;
                break;
        }
        log.info("Calculated rating = " + new BigDecimal(creditRate).setScale(2));
        return creditRate;
    }
}
