package pl.ps.creditapp.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.ps.creditapp.core.model.Person;
import pl.ps.creditapp.core.model.PersonTestFactory;
import pl.ps.creditapp.core.scoring.EducationCalculator;
import pl.ps.creditapp.core.scoring.IncomeCalculator;
import pl.ps.creditapp.core.scoring.MaritalStatusCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class PersonScoringCalculatorTest {

    @InjectMocks
    private NaturalPersonScoringCalculator cut1;

    @InjectMocks
    private SelfEmployedScoringCalculator cut2;

    @Mock
    private EducationCalculator educationCalculatorMock;
    @Mock
    private MaritalStatusCalculator maritalStatusCalculatorMock;
    @Mock
    private IncomeCalculator incomeCalculatorMock;

    @Test
    @DisplayName("should return sum of calculations")
    public void test1() {
        //given
        Person person = PersonTestFactory.create();
        BDDMockito.given(educationCalculatorMock.calculate(eq(person)))
                .willReturn(100);
        BDDMockito.given(maritalStatusCalculatorMock.calculate(eq(person)))
                .willReturn(200);
        BDDMockito.given(incomeCalculatorMock.calculate(eq(person)))
                .willReturn(50);
        //when
        int scoring = cut1.calculate(person);
        //then
        assertEquals(350, scoring);

        //when
        scoring = cut2.calculate(person);
        //then
        assertEquals(350, scoring);

    }
}