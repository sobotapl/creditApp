package pl.ps.creditapp.core.scoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.ps.creditapp.core.model.CreditApplication;
import pl.ps.creditapp.core.model.Person;

public class CompoundScoringCalculator implements ScoringCalculator {

    private static final Logger log = LoggerFactory.getLogger(CompoundScoringCalculator.class);
    private final ScoringCalculator[] calculators;


    public CompoundScoringCalculator(ScoringCalculator... calculators) {
        this.calculators = calculators;
    }

    @Override
    public int calculate(CreditApplication creditApplication) {
        int scoring =0;

        for(ScoringCalculator calculator : calculators){
            scoring += calculator.calculate(creditApplication);

        }
        log.info("Calculated scoring = " + scoring + " points");
        return scoring;
    }
}
