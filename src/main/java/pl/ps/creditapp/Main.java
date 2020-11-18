package pl.ps.creditapp;

import pl.ps.creditapp.client.CreditApplicationReader;
import pl.ps.creditapp.client.DummyCreditApplicationReader;
import pl.ps.creditapp.core.*;
import pl.ps.creditapp.core.scoring.EducationCalculator;
import pl.ps.creditapp.core.scoring.GuarantorsCalculator;
import pl.ps.creditapp.core.scoring.IncomeCalculator;
import pl.ps.creditapp.core.scoring.MaritalStatusCalculator;
import pl.ps.creditapp.core.validation.*;
import pl.ps.creditapp.core.validation.reflection.*;
import pl.ps.creditapp.di.ClassInitializer;

import java.sql.SQLOutput;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;


public class Main {

    static {
        TimeZone.setDefault(TimeZone.getTimeZone(Constants.DEFAULT_SYSTEM_ZONE_ID));
    }

    public static void main(String[] args) throws Exception {


        CreditApplicationReader reader = new DummyCreditApplicationReader();

        List<FieldAnnotationProcessor> fieldProcessors = List.of(new NotNullAnnotationProcessor(), new RegexAnnotationProcessor());
        List<ClassAnnotationProcessor> classProcessors = List.of(new ExactlyOneNotNullAnnotationProcessor());
        final ObjectValidator objectValidator = new ObjectValidator(fieldProcessors, classProcessors);

        CompoundPostValidator compoundPostValidator = new CompoundPostValidator(new PurposeOfLoanPostValidator(), new ExpansesPostValidator());
        ClassInitializer classInitializer = new ClassInitializer();
        classInitializer.registerInstance(compoundPostValidator);
        classInitializer.registerInstance(objectValidator);

        CreditApplicationManager manager = (CreditApplicationManager) classInitializer.createInstance(CreditApplicationManager.class);
        manager.add(reader.read());

        manager.startProcessing();
    }
}
