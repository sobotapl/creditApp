package pl.ps.creditapp.core.scoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.ps.creditapp.core.model.Person;

public class CompoundScoringCalculator implements PersonalCalculator {

    private static final Logger log = LoggerFactory.getLogger(CompoundScoringCalculator.class);
    private final PersonalCalculator[] calculators;

    public CompoundScoringCalculator(PersonalCalculator... calculators) {
        this.calculators = calculators;
    }

    @Override
    public int calculate(Person person) {
        int scoring =0;

        for(PersonalCalculator calculator : calculators){
            scoring += calculator.calculate(person);

        }
        log.info("Calculated scoring = " + scoring + " points");
        return scoring;
    }
}
