package pl.ps.creditapp.core.model;
import pl.ps.creditapp.core.annotation.NotNull;
import pl.ps.creditapp.core.annotation.ValidateCollection;
import pl.ps.creditapp.core.annotation.ValidateObject;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class CreditApplication {
    @NotNull
    private final UUID id;

    private final ZoneId clientTimeZone;

    private final Locale clientLocale;

    private final ZonedDateTime creationDateClientZone;
    @NotNull
    @ValidateObject
    private final Person person;
    @NotNull
    @ValidateObject
    private final PurposeOfLoan purposeOfLoan;
    @NotNull
    @ValidateCollection
    private final Set<Guarantor> guarantors;

    public CreditApplication(Locale clientLocale, ZoneId clientTimeZone, Person person, PurposeOfLoan purposeOfLoan) {
        this.person = person;
        this.purposeOfLoan = purposeOfLoan;
        this.id = UUID.randomUUID();
        this.clientTimeZone = clientTimeZone;
        this.creationDateClientZone = ZonedDateTime.now(clientTimeZone);
        this.guarantors = new TreeSet<>();
        this.clientLocale = clientLocale;

    }

    public CreditApplication(Locale clientLocale, ZoneId clientTimeZone, Person person, PurposeOfLoan purposeOfLoan, Set<Guarantor> guarantors) {
        this.person = person;
        this.purposeOfLoan = purposeOfLoan;
        this.id = UUID.randomUUID();
        this.clientTimeZone = clientTimeZone;
        this.creationDateClientZone = ZonedDateTime.now(clientTimeZone);
        this.guarantors =  new TreeSet<>(guarantors);
        this.clientLocale = clientLocale;
    }

    public Locale getClientLocale() {
        return clientLocale;
    }

    public ZoneId getClientTimeZone() {
        return clientTimeZone;
    }

    public ZonedDateTime getCreationDateClientZone() {
        return creationDateClientZone;
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
