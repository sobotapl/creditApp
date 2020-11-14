package pl.ps.creditapp.core.validation;

import pl.ps.creditapp.core.Constants;
import pl.ps.creditapp.core.exception.ValidationException;
import pl.ps.creditapp.core.model.CreditApplication;
import pl.ps.creditapp.core.model.Guarantor;

import java.util.Set;

public class GuarantorValidator implements Validator {

    @Override
    public void validate(CreditApplication creditApplication) throws ValidationException {
        final Set<Guarantor> guarantorSet = creditApplication.getGuarantors();
        for (Guarantor g : guarantorSet) {
            ValidationUtils.validateNotNull("guarantorPesel", g.getPesel());
            ValidationUtils.validateRegex("guarantorPesel", g.getPesel(), Constants.PESEL_REGEX);
            ValidationUtils.validateMinValue("guarantorAge", 0, g.getAge());
        }

    }
}
