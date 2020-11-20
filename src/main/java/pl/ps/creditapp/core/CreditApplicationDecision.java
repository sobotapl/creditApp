package pl.ps.creditapp.core;

import pl.ps.creditapp.core.exception.RequirementNotMetCause;
import pl.ps.creditapp.core.model.PersonalData;

import java.math.BigDecimal;
import java.util.Optional;


public class CreditApplicationDecision {
    private final DecisionType type;
    private final Optional<RequirementNotMetCause> requirementNotMetCause;
    private final PersonalData personalData;
    private final Double creditRate;
    private final Integer scoring;

    public CreditApplicationDecision(DecisionType type, PersonalData personalData, Double creditRate, Integer scoring) {
        this.type = type;
        this.personalData = personalData;
        this.creditRate = creditRate;
        this.scoring = scoring;
        this.requirementNotMetCause = Optional.empty();
    }

    public CreditApplicationDecision(DecisionType type, PersonalData personalData, Double creditRate, Integer scoring, RequirementNotMetCause cause) {
        this.type = type;
        this.personalData = personalData;
        this.creditRate = creditRate;
        this.scoring = scoring;
        this.requirementNotMetCause = Optional.of(cause);
    }


    public Optional<RequirementNotMetCause> getRequirementNotMetCause() {
        return requirementNotMetCause;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public int getScoring() {
        return scoring;
    }

    public Double getCreditRate() {
        return creditRate;
    }

    public DecisionType getType() {
        return type;
    }
}
