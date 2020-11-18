package pl.ps.creditapp.core.scoring;

import org.junit.jupiter.api.Test;
import pl.ps.creditapp.core.model.*;
import pl.ps.creditapp.util.AgeUtils;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GuarantorsCalculatorTest {
    private GuarantorsCalculator cut = new GuarantorsCalculator();

    @Test
    public void test1() {
        //given
        NaturalPerson person = createNaturalPerson();
        PurposeOfLoan purposeOfLoan = new PurposeOfLoan(PurposeOfLoanType.MORTGAGE, 50000.00, 30);
        Set<Guarantor> guarantorSet = Set.of(new Guarantor("45645645645", AgeUtils.generateBirthDate(18)));
        CreditApplication creditApplication = CreditApplicationTestFactory.create(person, purposeOfLoan, guarantorSet);

        //when
        int scoring = cut.calculate(creditApplication);

        //then
        assertEquals(50, scoring);
    }

    @Test
    public void test2() {
        //given
        NaturalPerson person = createNaturalPerson();
        PurposeOfLoan purposeOfLoan = new PurposeOfLoan(PurposeOfLoanType.MORTGAGE, 50000.00, 30);
        Set<Guarantor> guarantorSet = Set.of(new Guarantor("45645645645", AgeUtils.generateBirthDate(18)),
                new Guarantor("45645645646", AgeUtils.generateBirthDate(41)));
        CreditApplication creditApplication = CreditApplicationTestFactory.create(person, purposeOfLoan, guarantorSet);

        //when
        int scoring = cut.calculate(creditApplication);

        //then
        assertEquals(75, scoring);
    }

    private NaturalPerson createNaturalPerson() {
        return NaturalPerson.Builder
                .create()
                .withFamilyMembers(new ArrayList<>())
                .withPersonalData(PersonalData.Builder.create()
                        .withName("Test")
                        .withLastName("Test")
                        .withMothersMaidenName("Test")
                        .withEducation(Education.MIDDLE)
                        .withMaritalStatus(MaritalStatus.MARRIED)
                        .build())
                .withFinanceData(new FinanceData(new SourceOfIncome(IncomeType.SELF_EMPLOYMENT, 10000.00)))
                .build();
    }

}