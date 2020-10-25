package pl.ps.creditapp.core.scoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.ps.creditapp.core.model.MaritalStatus;
import pl.ps.creditapp.core.model.Person;

public class MaritalStatusCalculator implements PersonalCalculator {

    private static final Logger log = LoggerFactory.getLogger(MaritalStatusCalculator.class);

    @Override
    public int calculate(Person person) {
        MaritalStatus maritalStatus = person.getPersonalData().getMaritalStatus();
        int pointsForEducation = maritalStatus.getScoringPoints();

        log.info("Marital status = " + maritalStatus + ScoringUtils.getPointsString(pointsForEducation));
        return pointsForEducation;
    }
}
