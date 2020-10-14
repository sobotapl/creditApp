package pl.ps.creditapp.core.model;

public class PersonTestFactory {

    public static Person create(double income, int numOfDependants) {
        PersonalData personalData = new PersonalData("test", "test", "test", income, MaritalStatus.SINGLE, Education.MIDDLE, numOfDependants);
        return new Person(personalData, null);
    }

    public static Person create(MaritalStatus maritalStatus) {
        PersonalData personalData = new PersonalData("test", "test", "test", 500.00, maritalStatus, Education.MIDDLE, 2);
        return new Person(personalData, null);
    }

    public static Person create(Education education) {
        PersonalData personalData = new PersonalData("test", "test", "test", 500.00, MaritalStatus.SINGLE, education, 2);
        return new Person(personalData, null);
    }

    public static Person create() {
        PersonalData personalData = new PersonalData("test", "test", "test", 500.00, MaritalStatus.SINGLE, Education.MIDDLE, 2);
        return new Person(personalData, null);
    }

    public static Person create(double totalMonthlyIncomeInPln, int numOfDependants, Education education, MaritalStatus maritalStatus) {
        PersonalData personalData = new PersonalData("test", "test", "test", totalMonthlyIncomeInPln, maritalStatus, education, numOfDependants);
        return new Person(personalData, null);
    }
}
