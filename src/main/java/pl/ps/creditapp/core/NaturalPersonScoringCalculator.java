package pl.ps.creditapp.core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.ps.creditapp.core.model.Person;
import pl.ps.creditapp.core.scoring.EducationCalculator;
import pl.ps.creditapp.core.scoring.IncomeCalculator;
import pl.ps.creditapp.core.scoring.MaritalStatusCalculator;

public class NaturalPersonScoringCalculator extends PersonScoringCalculator{
    private static final Logger log = LoggerFactory.getLogger(NaturalPersonScoringCalculator.class);

    public NaturalPersonScoringCalculator(EducationCalculator educationCalculator, MaritalStatusCalculator maritalStatusCalculator, IncomeCalculator incomeCalculator) {
        super(educationCalculator,maritalStatusCalculator,incomeCalculator);
    }

    @Override
    protected int addAdditionalPoints(Person person) {
        return 0;
    }

}
