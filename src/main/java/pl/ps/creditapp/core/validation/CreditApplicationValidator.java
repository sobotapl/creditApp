package pl.ps.creditapp.core.validation;

import pl.ps.creditapp.core.exception.ValidationException;
import pl.ps.creditapp.core.model.CreditApplication;

public class CreditApplicationValidator implements Validator {
    private final PersonValidator personValidator;
    private final PurposeOfLoanValidator purposeOfLoanValidator;
    private final GuarantorValidator guarantorValidator;

    public CreditApplicationValidator(PersonValidator personValidator, PurposeOfLoanValidator purposeOfLoanValidator, GuarantorValidator guarantorValidator) {
        this.personValidator = personValidator;
        this.purposeOfLoanValidator = purposeOfLoanValidator;
        this.guarantorValidator = guarantorValidator;
    }

    @Override
    public void validate(CreditApplication creditApplication) throws ValidationException {

        ValidationUtils.validateNotNull("person", creditApplication.getPerson());
        personValidator.validate(creditApplication);

        ValidationUtils.validateNotNull("purposeOfLoan", creditApplication.getPurposeOfLoan());
        purposeOfLoanValidator.validate(creditApplication);

        ValidationUtils.validateNotNull("guarantors", creditApplication.getGuarantors());
        guarantorValidator.validate(creditApplication);
    }
}
