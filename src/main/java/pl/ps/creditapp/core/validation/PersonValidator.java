package pl.ps.creditapp.core.validation;

import pl.ps.creditapp.core.exception.ValidationException;
import pl.ps.creditapp.core.model.CreditApplication;

public class PersonValidator implements Validator {
    private final PersonalDataValidator personalDataValidator;

    public PersonValidator(PersonalDataValidator personalDataValidator) {
        this.personalDataValidator = personalDataValidator;
    }

    @Override
    public void validate(CreditApplication creditApplication) throws ValidationException {
        ValidationUtils.validateNotNull("personalData",creditApplication.getPerson().getPersonalData());
        personalDataValidator.validate(creditApplication);

        //validate contactData
        //validate financeData
    }
}
