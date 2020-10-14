package pl.ps.creditapp.core.scoring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pl.ps.creditapp.core.model.MaritalStatus;
import pl.ps.creditapp.core.model.Person;
import pl.ps.creditapp.core.model.PersonTestFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaritalStatusCalculatorTest {
    private MaritalStatusCalculator cut = new MaritalStatusCalculator();

    @ParameterizedTest
    @DisplayName("should return point attached to enum element")
    @EnumSource(MaritalStatus.class)
    public void test(MaritalStatus maritalStatus){
        //given
        Person person = PersonTestFactory.create(maritalStatus);
        //when
        int scoring = cut.calculate(person);
        //then
        assertEquals(maritalStatus.getScoringPoints(),scoring);
    }
}