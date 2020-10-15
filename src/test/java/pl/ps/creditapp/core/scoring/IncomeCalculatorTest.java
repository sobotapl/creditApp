package pl.ps.creditapp.core.scoring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.ps.creditapp.core.model.IncomeType;
import pl.ps.creditapp.core.model.Person;
import pl.ps.creditapp.core.model.PersonTestFactory;
import pl.ps.creditapp.core.model.SourcesOfIncome;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IncomeCalculatorTest {

    private IncomeCalculator cut = new IncomeCalculator();

    @Test
    @DisplayName("should return 100 points for each 1000 zł for family member.")
    public void test1() {
        //given
        SourcesOfIncome s1 = new SourcesOfIncome(IncomeType.SELF_EMPLOYMENT, 5000.00);
        Person person = PersonTestFactory.create(2, s1);
        //when
        int scoring = cut.calculate(person);
        //then
        assertEquals(200,scoring);
    }

    @Test
    @DisplayName("should return plus 200 points where there is more than 1 source of income.")
    public void test2() {
        //given
        SourcesOfIncome s1 = new SourcesOfIncome(IncomeType.SELF_EMPLOYMENT, 4000.00);
        SourcesOfIncome s2 = new SourcesOfIncome(IncomeType.RETIREMENT, 1000.00);
        Person person = PersonTestFactory.create(2, s1, s2); //varargs
        //when
        int scoring = cut.calculate(person);
        //then
        assertEquals(300,scoring);
    }


}