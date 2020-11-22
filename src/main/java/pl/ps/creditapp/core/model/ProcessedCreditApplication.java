package pl.ps.creditapp.core.model;

import pl.ps.creditapp.core.CreditApplicationDecision;

import javax.script.ScriptEngine;
import java.io.Serializable;

public class ProcessedCreditApplication implements Serializable {
    public static final long serialVersionUID =1l;

    private final CreditApplication application;
    private final CreditApplicationDecision decision;

    public ProcessedCreditApplication(CreditApplication application, CreditApplicationDecision decision) {
        this.application = application;
        this.decision = decision;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public CreditApplication getApplication() {
        return application;
    }

    public CreditApplicationDecision getDecision() {
        return decision;
    }
}
