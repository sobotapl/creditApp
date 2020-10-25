package pl.ps.creditapp.core.model;

import java.util.Optional;

public class CreditApplication {
    private final Optional<NaturalPerson> naturalPerson;
    private final Optional<Object> selfEmployed;
    private final PurposeOfLoan purposeOfLoan;

    public CreditApplication(NaturalPerson person, PurposeOfLoan purposeOfLoan) {
        this.naturalPerson = Optional.of(person);
        this.selfEmployed = Optional.empty();
        this.purposeOfLoan = purposeOfLoan;
    }

    public CreditApplication(SelfEmployed person, PurposeOfLoan purposeOfLoan) {
        this.naturalPerson = Optional.empty();
        this.selfEmployed = Optional.of(person);
        this.purposeOfLoan = purposeOfLoan;
    }

    public Optional<NaturalPerson> getNaturalPerson() {
        return naturalPerson;
    }

    public Optional<Object> getSelfEmployed() {
        return selfEmployed;
    }

    public PurposeOfLoan getPurposeOfLoan() {
        return purposeOfLoan;
    }

    public Person getPerson() {
        if (naturalPerson.isPresent()) {
            return naturalPerson.get();
        }
        return (Person) selfEmployed.get();
    }

}