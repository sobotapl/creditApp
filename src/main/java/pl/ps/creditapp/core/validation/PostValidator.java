package pl.ps.creditapp.core.validation;

import pl.ps.creditapp.core.exception.RequirementNotMetException;
import pl.ps.creditapp.core.model.CreditApplication;

public interface PostValidator {
    void validate(CreditApplication creditApplication, int scoring, double rating) throws RequirementNotMetException;
}
