package pl.ps.creditapp.core.validation;


import pl.ps.creditapp.core.exception.RequirementNotMetCause;
import pl.ps.creditapp.core.exception.RequirementNotMetException;
import pl.ps.creditapp.core.model.CreditApplication;

import static pl.ps.creditapp.core.Constants.MIN_LOAN_AMOUNT_MORTGAGE;

public class PurposeOfLoanPostValidator implements PostValidator {

    @Override
    public void validate(CreditApplication creditApplication, int scoring, double rating) throws RequirementNotMetException {
        if (creditApplication.getPurposeOfLoan().getAmount() < MIN_LOAN_AMOUNT_MORTGAGE) {
            throw new RequirementNotMetException(RequirementNotMetCause.TOO_LOW_LOAN_AMOUNT);
        }
    }
}
