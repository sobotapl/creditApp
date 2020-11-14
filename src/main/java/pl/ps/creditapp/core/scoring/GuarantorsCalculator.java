package pl.ps.creditapp.core.scoring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.ps.creditapp.core.model.CreditApplication;
import pl.ps.creditapp.core.model.Guarantor;

public class GuarantorsCalculator implements ScoringCalculator {
    private static final Logger log = LoggerFactory.getLogger(GuarantorsCalculator.class);

    @Override
    public int calculate(CreditApplication creditApplication) {
        int scoringAgeUnder40 = 0;
        int scoringOthers = 0;
        for (Guarantor g : creditApplication.getGuarantors()) {
            if (g.getAge() < 40) {
                scoringAgeUnder40 += 50;
            } else {
                scoringOthers += 25;
            }
        }
        if (scoringAgeUnder40 > 0) {
            log.info("Point for guarantors under age 40 = " + scoringAgeUnder40 + ". " + ScoringUtils.getPointsString(scoringAgeUnder40));
        }
        if (scoringOthers > 0) {
            log.info("Point for other guarantors = " + scoringOthers + ". " + ScoringUtils.getPointsString(scoringOthers));
        }

        return scoringAgeUnder40 + scoringOthers;
    }
}