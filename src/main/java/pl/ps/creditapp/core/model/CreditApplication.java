package pl.ps.creditapp.core.model;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class CreditApplication {
    private final UUID id;
    private final Person person;
    private final PurposeOfLoan purposeOfLoan;
    private final Set<Guarantor> guarantors;

    public CreditApplication(Person person, PurposeOfLoan purposeOfLoan) {
        this.person = person;
        this.purposeOfLoan = purposeOfLoan;
        this.id = UUID.randomUUID();
        this.guarantors = new TreeSet<>();
    }

    public CreditApplication(Person person, PurposeOfLoan purposeOfLoan, Set<Guarantor> guarantors) {
        this.person = person;
        this.purposeOfLoan = purposeOfLoan;
        this.id = UUID.randomUUID();
        this.guarantors =  new TreeSet<>(guarantors);
    }

    public Set<Guarantor> getGuarantors() {
        return guarantors;
    }

    public UUID getId() {
        return id;
    }

    public PurposeOfLoan getPurposeOfLoan() {
        return purposeOfLoan;
    }

    public Person getPerson() {
        return person;
    }
}
