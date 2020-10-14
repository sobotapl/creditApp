package pl.ps.creditapp.core.scoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.ps.creditapp.core.model.Education;
import pl.ps.creditapp.core.model.Person;

public class EducationCalculator {
    private static final Logger log = LoggerFactory.getLogger(EducationCalculator.class);

    public int calculate(Person person) {
        Education education = person.getPersonalData().getEducation();
        int pointsForEducation = education.getScoringPoints();

        log.info("Education = " + education + ScoringUtils.getPointsString(pointsForEducation));
        return pointsForEducation;
    }
}
