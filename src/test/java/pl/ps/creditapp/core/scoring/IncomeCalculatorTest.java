package pl.ps.creditapp.core.scoring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.ps.creditapp.core.model.Person;
import pl.ps.creditapp.core.model.PersonTestFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IncomeCalculatorTest {

    private IncomeCalculator cut = new IncomeCalculator();

    @Test
    @DisplayName("should return 100 points for each 1000 zł for family member.")
    public void test1() {
        //given
        Person person = PersonTestFactory.create(5000.00,2);
        //when
        int scoring = cut.calculate(person);
        //then
        assertEquals(200,scoring);
    }

}