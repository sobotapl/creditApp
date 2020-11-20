package pl.ps.creditapp.core.model;

import pl.ps.creditapp.core.annotation.ExactlyOneNotNull;

import java.util.ArrayList;
import java.util.List;
@ExactlyOneNotNull({"nip", "regon"})
public class SelfEmployed extends Person {
    private final String nip;
    private final String regon;
    private final int yearsSinceFounded;

    public String getNip() {
        return nip;
    }

    public int getYearsSinceFounded() {
        return yearsSinceFounded;
    }

    private SelfEmployed(int yearsSinceFounded, String nip, String regon, PersonalData personalData, ContactData contactData, FinanceData financeData, List<FamilyMember> familyMemberList) {
        super(personalData, contactData, financeData, familyMemberList);
        this.nip = nip;
        this.regon = regon;
        this.yearsSinceFounded = yearsSinceFounded;
    }

    public static class Builder {
        private PersonalData personalData;
        private ContactData contactData;
        private FinanceData financeData;
        private String nip;
        private String regon;
        private int yearsSinceFounded;
        private List<FamilyMember> familyMemberList = new ArrayList<>();


        private Builder() {
        }

        public Builder withFamilyMembers(List<FamilyMember> familyMemberList) {
            this.familyMemberList = familyMemberList;
            return this;
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder withYearsSinceFounded(int yearsSinceFounded) {
            this.yearsSinceFounded = yearsSinceFounded;
            return this;
        }

        public Builder withPersonalData(PersonalData personalData) {
            this.personalData = personalData;
            return this;
        }

        public Builder withContactData(ContactData contactData) {
            this.contactData = contactData;
            return this;
        }

        public Builder withFinanceData(FinanceData financeData) {
            this.financeData = financeData;
            return this;
        }

        public Builder withNip(String nip) {
            this.nip = nip;
            return this;
        }

        public Builder withRegon(String regon) {
            this.regon = regon;
            return this;
        }

        public SelfEmployed build() {
            return new SelfEmployed(yearsSinceFounded, nip, regon, personalData, contactData, financeData, familyMemberList);
        }
    }
}
