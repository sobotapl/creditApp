package pl.ps.creditapp.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.ps.creditapp.core.model.SelfEmployed;
import pl.ps.creditapp.core.scoring.*;

public class SelfEmployedScoringCalculator implements ScoringCalculator {
    private static final Logger log = LoggerFactory.getLogger(SelfEmployedScoringCalculator.class);

    @Override
    public int calculate(SelfEmployed selfEmployed) {
        if (selfEmployed.getYearsSinceFounded() < 2) {
            log.info("Years since founded = " + selfEmployed.getYearsSinceFounded() + ScoringUtils.getPointsString(-200));
            return -200;
        }
        return 0;
    }

}
