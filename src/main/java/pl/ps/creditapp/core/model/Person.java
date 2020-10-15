package pl.ps.creditapp.core.model;

public class Person {
    private final PersonalData personalData;
    private final ContactData contactData;
    private final FinanceData financeData;

    public Person(PersonalData personalData, ContactData contactData, FinanceData financeData) {
        this.personalData = personalData;
        this.contactData = contactData;
        this.financeData = financeData;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public ContactData getContactData() {
        return contactData;
    }

    public FinanceData getFinanceData() {
        return financeData;
    }

    public double getIncomePerFamilyMember(){
        double totalMonthlyIncome = 0;
        for(SourcesOfIncome sourcesOfIncome : financeData.getSourcesOfIncome()){
            totalMonthlyIncome += sourcesOfIncome.getNetMonthlyIncome();
        }
        return totalMonthlyIncome / this.getPersonalData().getNumOfDependants();
    }
}
