package pl.ps.creditapp.core.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.ps.creditapp.core.annotation.ExactlyOneNotNull;
import pl.ps.creditapp.core.annotation.NotNull;
import pl.ps.creditapp.core.annotation.ValidateCollection;
import pl.ps.creditapp.core.annotation.ValidateObject;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@ExactlyOneNotNull({"naturalPerson", "selfEmployed"})
public class CreditApplication implements Serializable {
    public static final long serialVersionUID = 1l;
    @NotNull
    @JsonIgnore
    private UUID id;
    @JsonProperty
    private ZoneId clientTimeZone;
    @JsonProperty
    private Locale clientLocale;
    @JsonIgnore
    private ZonedDateTime creationDateClientZone;
    @ValidateObject
    @JsonProperty
    private NaturalPerson naturalPerson;
    @ValidateObject
    @JsonProperty
    private SelfEmployed selfEmployed;
    @NotNull
    @ValidateObject
    @JsonProperty
    private PurposeOfLoan purposeOfLoan;
    @NotNull
    @ValidateCollection
    @JsonProperty
    private Set<Guarantor> guarantors;

    public CreditApplication() {
    }

    public CreditApplication(Locale clientLocale, ZoneId clientTimeZone, NaturalPerson person, PurposeOfLoan purposeOfLoan) {
        this.naturalPerson = person;
        this.purposeOfLoan = purposeOfLoan;
        this.id = UUID.randomUUID();
        this.clientTimeZone = clientTimeZone;
        this.creationDateClientZone = ZonedDateTime.now(clientTimeZone);
        this.guarantors = new TreeSet<>();
        this.clientLocale = clientLocale;
    }

    public CreditApplication(Locale clientLocale, ZoneId clientTimeZone, SelfEmployed person, PurposeOfLoan purposeOfLoan) {
        this.selfEmployed = person;
        this.purposeOfLoan = purposeOfLoan;
        this.id = UUID.randomUUID();
        this.clientTimeZone = clientTimeZone;
        this.creationDateClientZone = ZonedDateTime.now(clientTimeZone);
        this.guarantors = new TreeSet<>();
        this.clientLocale = clientLocale;
    }


    @JsonIgnore
    public boolean isNaturalPerson() {
        return naturalPerson != null;
    }

    public ZoneId getClientTimeZone() {
        return clientTimeZone;
    }

    public ZonedDateTime getCreationDateClientZone() {
        return creationDateClientZone;
    }

    public CreditApplication(Locale clientLocale, ZoneId clientTimeZone, NaturalPerson person, PurposeOfLoan purposeOfLoan, Set<Guarantor> guarantors) {
        this.naturalPerson = person;
        this.purposeOfLoan = purposeOfLoan;
        this.id = UUID.randomUUID();
        this.clientTimeZone = clientTimeZone;
        this.guarantors = new TreeSet<>(guarantors);
        this.creationDateClientZone = ZonedDateTime.now(clientTimeZone);
        this.clientLocale = clientLocale;
    }

    public CreditApplication(Locale clientLocale, ZoneId clientTimeZone, SelfEmployed person, PurposeOfLoan purposeOfLoan, Set<Guarantor> guarantors) {
        this.selfEmployed = person;
        this.purposeOfLoan = purposeOfLoan;
        this.id = UUID.randomUUID();
        this.clientTimeZone = clientTimeZone;
        this.guarantors = new TreeSet<>(guarantors);
        this.creationDateClientZone = ZonedDateTime.now(clientTimeZone);
        this.clientLocale = clientLocale;
    }

    public Locale getClientLocale() {
        return clientLocale;
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


    @JsonIgnore
    public Person getPerson() {
        return naturalPerson != null ? naturalPerson : selfEmployed;
    }

    public void init() {
        this.id = UUID.randomUUID();
        this.creationDateClientZone = ZonedDateTime.now(clientTimeZone);
    }
}
