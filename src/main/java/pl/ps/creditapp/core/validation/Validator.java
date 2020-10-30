package pl.ps.creditapp.core.validation;

import pl.ps.creditapp.core.exception.ValidationException;
import pl.ps.creditapp.core.model.CreditApplication;

public interface Validator {

    void validate(CreditApplication creditApplication) throws ValidationException;
}

