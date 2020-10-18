package pl.ps.creditapp.client;

import pl.ps.creditapp.core.model.*;

import java.util.Scanner;

public class ConsoleReader {

    public CreditApplication readInputParameters() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter your name");
        String name = in.next();

        System.out.println("Enter your last name");
        String lastName = in.next();

        System.out.println("Enter your mothers maiden name");
        String mothersMaidenName = in.next();

        MaritalStatus maritalStatus = getMaritalStatus(in);

        Education education = getEducation(in);


        System.out.println("Enter your email address:");
        String email = in.next();

        System.out.println("Enter your phone number:");
        String phoneNumber = in.next();

        System.out.println("How many sources of income do you have?");
        int numOfSourcesOfIncome = in.nextInt();

        SourcesOfIncome[] sourcesOfIncome = new SourcesOfIncome[numOfSourcesOfIncome];
        for (int i = 1; i <= numOfSourcesOfIncome; i++) {
            IncomeType incomeType = getIncomeType(in, i);
            System.out.println("Enter net monthly income of source of income " + i);
            double netMonthlyIncome = in.nextDouble();

            SourcesOfIncome sourceOfIncome = new SourcesOfIncome(incomeType, netMonthlyIncome);
            sourcesOfIncome[i - 1] = sourceOfIncome;
        }

        System.out.println("Enter number of family dependants (including applicant):");
        int numOfDependant = in.nextInt();

        PurposeOfLoanType purposeOfLoanType = getPurposeOfLoanType(in);

        System.out.println("Enter loan amount");
        double purposeOfLoanAmount = in.nextDouble();

        System.out.println("Enter loan period (in years)");
        int period = in.nextInt();

        PersonalData personalData = new PersonalData(name, lastName, mothersMaidenName, maritalStatus, education, numOfDependant);
        ContactData contactData = new ContactData(email, phoneNumber);
        PurposeOfLoan purposeOfLoan = new PurposeOfLoan(purposeOfLoanType, purposeOfLoanAmount, period);
        FinanceData financeData = new FinanceData(sourcesOfIncome);

        return new CreditApplication(new Person(personalData, contactData, financeData), purposeOfLoan);
    }

    private PurposeOfLoanType getPurposeOfLoanType(Scanner in) {
        String purposeOfLoanInput;
        do {
            System.out.println("What is purpose of loan? " + generatePurposeOfLoanTypeElements());
            purposeOfLoanInput = in.next();
        } while (!EnumValidator.validatePurposeOfLoanType(purposeOfLoanInput));
        return PurposeOfLoanType.valueOf(purposeOfLoanInput);
    }

    private Education getEducation(Scanner in) {
        String educationInput;
        do {
            System.out.println("What is your education level? (NONE, PRIMARY, MIDDLE, SECONDARY, POST_SECONDARY, TERTIARY)");
            educationInput = in.next();
        } while (!EnumValidator.validateEducation(educationInput));
        return Education.valueOf(educationInput);
    }

    private IncomeType getIncomeType(Scanner in, int i) {
        String incomeTypeInput;
        do {
            System.out.println("Enter type of source of income " + i + " " + generateIncomeTypeElements());
            incomeTypeInput = in.next();
        } while (!EnumValidator.validateIncomeType(incomeTypeInput));
        return IncomeType.valueOf(incomeTypeInput);
    }

    private MaritalStatus getMaritalStatus(Scanner in) {
        String maritalStatusInput;
        do {
            System.out.println("What is your marital status? " + generateMaritalStatusElements());
            maritalStatusInput = in.next();
        } while (!EnumValidator.validateMaritalStatus(maritalStatusInput));
        return MaritalStatus.valueOf(maritalStatusInput);
    }

    private String generateMaritalStatusElements() {
        String elements = "(";
        for (int i = 0; i < MaritalStatus.values().length; i++) {
            elements += MaritalStatus.values()[i].name();
            if (i < MaritalStatus.values().length - 1) {
                elements += ", ";
            }
        }
        elements += ")";
        return elements;
    }

    private String generateEducationElements() {
        String elements = "(";
        for (int i = 0; i < Education.values().length; i++) {
            elements += Education.values()[i].name();
            if (i < Education.values().length - 1) {
                elements += ", ";
            }
        }
        elements += ")";
        return elements;
    }

    private String generateIncomeTypeElements() {
        String elements = "(";
        for (int i = 0; i < IncomeType.values().length; i++) {
            elements += IncomeType.values()[i].name();
            if (i < IncomeType.values().length - 1) {
                elements += ", ";
            }
        }
        elements += ")";
        return elements;
    }

    private String generatePurposeOfLoanTypeElements() {
        String elements = "(";
        for (int i = 0; i < PurposeOfLoanType.values().length; i++) {
            elements += PurposeOfLoanType.values()[i].name();
            if (i < PurposeOfLoanType.values().length - 1) {
                elements += ", ";
            }
        }
        elements += ")";
        return elements;
    }
}