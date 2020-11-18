package pl.ps.creditapp.core.validation;

import pl.ps.creditapp.core.exception.ValidationException;
import pl.ps.creditapp.core.model.CreditApplication;
import pl.ps.creditapp.di.Inject;

public class CreditApplicationValidator implements Validator {
    @Inject
    private  ObjectValidator objectValidator;

    public CreditApplicationValidator(ObjectValidator objectValidator) {
        this.objectValidator = objectValidator;
    }

    public CreditApplicationValidator() {

    }

    @Override

    public void validate(CreditApplication creditApplication) throws ValidationException {

        try {
            objectValidator.validate(creditApplication);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
