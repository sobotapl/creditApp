package pl.ps.creditapp.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.ps.creditapp.core.model.Person;
import pl.ps.creditapp.core.model.SelfEmployed;
import pl.ps.creditapp.core.scoring.EducationCalculator;
import pl.ps.creditapp.core.scoring.IncomeCalculator;
import pl.ps.creditapp.core.scoring.MaritalStatusCalculator;
import pl.ps.creditapp.core.scoring.ScoringUtils;

public class SelfEmployedScoringCalculator extends PersonScoringCalculator{
    private static final Logger log = LoggerFactory.getLogger(SelfEmployedScoringCalculator.class);

    public SelfEmployedScoringCalculator(EducationCalculator educationCalculator, MaritalStatusCalculator maritalStatusCalculator, IncomeCalculator incomeCalculator) {
        super(educationCalculator,maritalStatusCalculator,incomeCalculator);
    }

    @Override
    protected int addAdditionalPoints(Person person) {
        if(person instanceof SelfEmployed){
            SelfEmployed selfEmployed = (SelfEmployed) person;
            if(selfEmployed.getYearsSinceFounded() < 2){
                log.info("Years since founded = " + selfEmployed.getYearsSinceFounded() + ScoringUtils.getPointsString(-200));
                return -200;
            }
        }
        return 0;
    }

}