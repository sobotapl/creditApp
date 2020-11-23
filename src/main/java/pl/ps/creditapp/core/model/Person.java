package pl.ps.creditapp.core.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.ps.creditapp.core.annotation.NotNull;
import pl.ps.creditapp.core.annotation.ValidateCollection;
import pl.ps.creditapp.core.annotation.ValidateObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class Person implements Serializable {
    public static final long serialVersionUID = 1l;
    @NotNull
    @ValidateObject
    @JsonProperty
    private PersonalData personalData;
    @NotNull
    @ValidateObject
    @JsonProperty
    private ContactData contactData;
    @NotNull
    @ValidateObject
    @JsonProperty
    private FinanceData financeData;
    @NotNull
    @ValidateCollection
    @JsonProperty
    private List<FamilyMember> familyMembers;

    protected Person(){}

    protected Person(PersonalData personalData, ContactData contactData, FinanceData financeData, List<FamilyMember> familyMembers) {
        this.personalData = personalData;
        this.contactData = contactData;
        this.financeData = financeData;
        this.familyMembers = familyMembers;
        Collections.sort(this.familyMembers);
    }

    public List<FamilyMember> getFamilyMembersSortedByName() {
        List<FamilyMember> copy = new ArrayList<>(this.familyMembers);
        Collections.sort(copy, new FamilyMemberNameComparator());
        return copy;
    }

    public List<FamilyMember> getFamilyMembers() {

        return familyMembers;
    }

    public double getBalance() {
        double totalMonthlyIncome = 0;
        for (SourceOfIncome sourceOfIncome : financeData.getSourcesOfIncome()) {
            totalMonthlyIncome += sourceOfIncome.getNetMonthlyIncome();
        }

        double totalExpenses = 0;
        for (Expense expense : financeData.getExpenses()) {
            totalExpenses += expense.getAmount();
        }

        return totalMonthlyIncome - totalExpenses;
    }

    public int getNumOfDependants() {
        return 1 + this.familyMembers.size();
    }

    public FinanceData getFinanceData() {
        return financeData;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public ContactData getContactData() {
        return contactData;
    }

    public double getIncomePerFamilyMember() {
        return getBalance() / this.getNumOfDependants();
    }
}
