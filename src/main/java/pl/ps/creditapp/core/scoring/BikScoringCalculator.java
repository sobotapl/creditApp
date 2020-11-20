package pl.ps.creditapp.core.scoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.ps.creditapp.core.bik.BikApi;
import pl.ps.creditapp.core.bik.ScoringRequest;
import pl.ps.creditapp.core.bik.ScoringResponse;
import pl.ps.creditapp.core.model.NaturalPerson;
import pl.ps.creditapp.core.model.Person;
import pl.ps.creditapp.core.model.SelfEmployed;

public class BikScoringCalculator implements ScoringCalculator {
    private static final Logger log = LoggerFactory.getLogger(BikScoringCalculator.class);

    private final BikApi bikApi;

    public BikScoringCalculator(BikApi bikApi) {
        this.bikApi = bikApi;
    }

    @Override
    public int calculate(Person person) {

        ScoringRequest request = new ScoringRequest();

        if(person instanceof SelfEmployed){
            request.setNip(((SelfEmployed)person).getNip());
        }
        if(person instanceof NaturalPerson){
            request.setNip(((NaturalPerson)person).getPesel());
        }
        final ScoringResponse response = bikApi.getScoring(request);
        int scoring = 100 * response.getScoring()/600;
        log.info("Bik Scoring={}/600, scroring={}/100",response.getScoring(), scoring );

        return scoring;
    }
}


