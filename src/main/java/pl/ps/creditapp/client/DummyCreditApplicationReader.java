package pl.ps.creditapp.client;

import pl.ps.creditapp.core.model.CreditApplication;
import pl.ps.creditapp.core.model.NaturalPerson;
import pl.ps.creditapp.core.model.PurposeOfLoan;
import pl.ps.creditapp.core.model.PurposeOfLoanType;

public class DummyCreditApplicationReader implements CreditApplicationReader {
    @Override
    public CreditApplication read() {
        return new CreditApplication(NaturalPerson.Builder.create().build(),
                new PurposeOfLoan(PurposeOfLoanType.MORTGAGE, 10000, 30));
    }
}
