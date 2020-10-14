package pl.ps.creditapp.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.ps.creditapp.core.model.Person;
import pl.ps.creditapp.core.scoring.EducationCalculator;
import pl.ps.creditapp.core.scoring.IncomeCalculator;
import pl.ps.creditapp.core.scoring.MaritalStatusCalculator;

public class PersonScoringCalculator {
    private static final Logger log = LoggerFactory.getLogger(PersonScoringCalculator.class);
    private final EducationCalculator educationCalculator;
    private final MaritalStatusCalculator maritalStatusCalculator;
    private final IncomeCalculator incomeCalculator;

    public PersonScoringCalculator(EducationCalculator educationCalculator, MaritalStatusCalculator maritalStatusCalculator, IncomeCalculator incomeCalculator) {
        this.educationCalculator = educationCalculator;
        this.maritalStatusCalculator = maritalStatusCalculator;
        this.incomeCalculator = incomeCalculator;
    }

    public int calculate(Person person) {
        int scoring = educationCalculator.calculate(person) + maritalStatusCalculator.calculate(person) + incomeCalculator.calculate(person);
        log.info("Calculated scoring = " + scoring + " points");
        return scoring;
    }
}
