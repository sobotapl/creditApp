package pl.ps.creditapp.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import pl.ps.creditapp.core.bik.BikApi;
import pl.ps.creditapp.core.bik.ScoringRequest;
import pl.ps.creditapp.core.bik.ScoringResponse;
import pl.ps.creditapp.core.exception.RequirementNotMetCause;
import pl.ps.creditapp.core.model.*;
import pl.ps.creditapp.core.scoring.*;
import pl.ps.creditapp.core.validation.*;
import pl.ps.creditapp.core.validation.reflection.*;
import pl.ps.creditapp.util.AgeUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static pl.ps.creditapp.util.AgeUtils.generateBirthDate;

class CreditApplicationServiceBddTest {
    private EducationCalculator educationCalculator = new EducationCalculator();
    private MaritalStatusCalculator maritalStatusCalculator = new MaritalStatusCalculator();
    private IncomeCalculator incomeCalculator = new IncomeCalculator();
    private SelfEmployedScoringCalculator selfEmployedScoringCalculator = new SelfEmployedScoringCalculator();
    private GuarantorsCalculator guarantorsCalculator = new GuarantorsCalculator();
    private BikApi bankApiMock = Mockito.mock(BikApi.class);
    private BikScoringCalculator bikScoringCalculator = new BikScoringCalculator(bankApiMock);
    private PersonScoringCalculatorFactory personScoringCalculatorFactory = new PersonScoringCalculatorFactory(selfEmployedScoringCalculator, educationCalculator, maritalStatusCalculator, incomeCalculator,
            guarantorsCalculator, bikScoringCalculator);
    private List<FieldAnnotationProcessor> fieldProcessors = List.of(new NotNullAnnotationProcessor(), new RegexAnnotationProcessor());
    private List<ClassAnnotationProcessor> classProcessors = List.of(new ExactlyOneNotNullAnnotationProcessor());
    final ObjectValidator objectValidator = new ObjectValidator(fieldProcessors, classProcessors);
    private CreditApplicationValidator creditApplicationValidator = new CreditApplicationValidator(objectValidator);
    private CompoundPostValidator compoundPostValidator = new CompoundPostValidator(new PurposeOfLoanPostValidator(), new ExpansesPostValidator());
    private CreditApplicationService cut = new CreditApplicationService(personScoringCalculatorFactory, new CreditRatingCalculator(), creditApplicationValidator, compoundPostValidator);

    @BeforeEach
    public void init() {
        ScoringResponse res = new ScoringResponse();
        res.setScoring(0);
        BDDMockito.given(bankApiMock.getScoring(any(ScoringRequest.class))).willReturn(res);
    }

    @Test
    @DisplayName("should return Decision is NEGATIVE_REQUIREMENTS_NOT_MET, min loan amount  requirement is not met")
    public void test1() {
        //given
        List<FamilyMember> familyMemberList = Arrays.asList(new FamilyMember("John", generateBirthDate(18)));
        NaturalPerson person = NaturalPerson.Builder
                .create()
                .withPesel("12341234123")
                .withFamilyMembers(familyMemberList)
                .withPersonalData(PersonalData.Builder.create()
                        .withName("Test")
                        .withLastName("Test")
                        .withMothersMaidenName("Test")
                        .withEducation(Education.MIDDLE)
                        .withMaritalStatus(MaritalStatus.MARRIED)
                        .build())
                .withFinanceData(new FinanceData(new SourceOfIncome(IncomeType.SELF_EMPLOYMENT, 10000.00)))
                .withContactData(ContactData.Builder.create()
                        .withEmail("test@test")
                        .withPhoneNumber("456456456")
                        .withHomeAddress(new Address("test", "test", "test", "test", "test"))
                        .withCorrespondenceAddress(new Address("test", "test", "test", "test", "test"))
                        .build())
                .build();
        PurposeOfLoan purposeOfLoan = new PurposeOfLoan(PurposeOfLoanType.MORTGAGE, 50000.00, 30);
        CreditApplication creditApplication = CreditApplicationTestFactory.create(person, purposeOfLoan);

        //when
        CreditApplicationDecision decision = cut.getDecision(creditApplication);

        //then
        assertEquals(DecisionType.NEGATIVE_REQUIREMENTS_NOT_MET, decision.getType());
        assertEquals(600, decision.getScoring());
        assertEquals(360000.00, decision.getCreditRate());

    }

    @Test
    @DisplayName("should return Decision is negative, when years since founded <2")
    public void test2() {
        //given
        List<FamilyMember> familyMemberList = Arrays.asList(new FamilyMember("John", generateBirthDate(18)));
        SelfEmployed person = SelfEmployed.Builder
                .create()
                .withNip("3245234")
                .withFamilyMembers(familyMemberList)
                .withPersonalData(PersonalData.Builder.create()
                        .withName("Test")
                        .withLastName("Test")
                        .withMothersMaidenName("Test")
                        .withEducation(Education.MIDDLE)
                        .withMaritalStatus(MaritalStatus.MARRIED)
                        .build())
                .withFinanceData(new FinanceData(new SourceOfIncome(IncomeType.SELF_EMPLOYMENT, 7000.00)))
                .withYearsSinceFounded(1)
                .withContactData(ContactData.Builder.create()
                        .withEmail("test@test")
                        .withPhoneNumber("456456456")
                        .withHomeAddress(new Address("test", "test", "test", "test", "test"))
                        .withCorrespondenceAddress(new Address("test", "test", "test", "test", "test"))
                        .build())
                .build();
        PurposeOfLoan purposeOfLoan = new PurposeOfLoan(PurposeOfLoanType.MORTGAGE, 500000.00, 30);
        CreditApplication creditApplication = CreditApplicationTestFactory.create(person, purposeOfLoan);

        //when
        CreditApplicationDecision decision = cut.getDecision(creditApplication);

        //then
        assertEquals(DecisionType.NEGATIVE_SCORING, decision.getType());
        assertEquals(200, decision.getScoring());
    }

    @Test
    @DisplayName("should return Decision is contact required, when years since founded >=2")
    public void test3() {
        //given
        List<FamilyMember> familyMemberList = Arrays.asList(new FamilyMember("John", generateBirthDate(18)));
        SelfEmployed person = SelfEmployed.Builder
                .create()
                .withNip("3245234")
                .withFamilyMembers(familyMemberList)
                .withPersonalData(PersonalData.Builder.create()
                        .withName("Test")
                        .withLastName("Test")
                        .withMothersMaidenName("Test")
                        .withEducation(Education.MIDDLE)
                        .withMaritalStatus(MaritalStatus.MARRIED)
                        .build())
                .withContactData(ContactData.Builder.create()
                        .withEmail("test@test")
                        .withPhoneNumber("456456456")
                        .withHomeAddress(new Address("test", "test", "test", "test", "test"))
                        .withCorrespondenceAddress(new Address("test", "test", "test", "test", "test"))
                        .build())
                .withFinanceData(new FinanceData(new SourceOfIncome(IncomeType.SELF_EMPLOYMENT, 7000.00)))
                .withYearsSinceFounded(3)
                .build();
        PurposeOfLoan purposeOfLoan = new PurposeOfLoan(PurposeOfLoanType.MORTGAGE, 500000.00, 30);
        CreditApplication creditApplication = CreditApplicationTestFactory.create(person, purposeOfLoan);

        //when
        CreditApplicationDecision decision = cut.getDecision(creditApplication);

        //then
        assertEquals(DecisionType.CONTACT_REQUIRED, decision.getType());
        assertEquals(400, decision.getScoring());
    }

    @Test
    @DisplayName("should return Decision is negative requirements not met, cause too high personal expenses")
    public void test4() {
        //given
        Set<Expense> expenseSet = Set.of(new Expense("1", ExpenseType.PERSONAL, 500),
                new Expense("2", ExpenseType.PERSONAL, 750));

        final FinanceData financeData = new FinanceData(expenseSet, new SourceOfIncome(IncomeType.SELF_EMPLOYMENT, 2000.00));
        SelfEmployed person = SelfEmployed.Builder
                .create()
                .withNip("3245234")
                .withPersonalData(PersonalData.Builder.create()
                        .withName("Test")
                        .withLastName("Test")
                        .withMothersMaidenName("Test")
                        .withEducation(Education.MIDDLE)
                        .withMaritalStatus(MaritalStatus.MARRIED)
                        .build())
                .withContactData(ContactData.Builder.create()
                        .withEmail("test@test")
                        .withPhoneNumber("456456456")
                        .withHomeAddress(new Address("test", "test", "test", "test", "test"))
                        .withCorrespondenceAddress(new Address("test", "test", "test", "test", "test"))
                        .build())
                .withFinanceData(financeData)
                .withYearsSinceFounded(3)
                .build();
        PurposeOfLoan purposeOfLoan = new PurposeOfLoan(PurposeOfLoanType.MORTGAGE, 500000.00, 30);
        CreditApplication creditApplication = CreditApplicationTestFactory.create(person, purposeOfLoan);

        //when
        CreditApplicationDecision decision = cut.getDecision(creditApplication);

        //then
        assertEquals(DecisionType.NEGATIVE_REQUIREMENTS_NOT_MET, decision.getType());
        assertTrue(decision.getRequirementNotMetCause().isPresent());
        assertEquals(RequirementNotMetCause.TOO_HIGH_PERSONAL_EXPENSES, decision.getRequirementNotMetCause().get());
    }


}