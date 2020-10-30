package pl.ps.creditapp.client;
import pl.ps.creditapp.core.Constants;
import pl.ps.creditapp.core.model.*;
import java.util.Scanner;

public class ConsoleReader implements CreditApplicationReader{

    @Override
    public CreditApplication read() {
        Scanner in = new Scanner(System.in);

        String name = getName(in);
        String lastName = getLastName(in);
        String mothersMaidenName = getMothersMaidenName(in);
        MaritalStatus maritalStatus = getMaritalStatus(in);
        Education education = getEducation(in);
        String email = getEmail(in);
        String phoneNumber = getPhoneNumber(in);
        SourceOfIncome[] sourcesOfIncome = getSourceOfIncomes(in);
        int numOfDependant = getNumOfDependants(in);
        PurposeOfLoanType purposeOfLoanType = getPurposeOfLoanType(in);
        double purposeOfLoanAmount = getPurposeOfLoanAmount(in);
        int period = getPeriod(in);

        ContactData contactData = ContactData.Builder
                .create()
                .withEmail(email)
                .withPhoneNumber(phoneNumber)
                .build();
        PurposeOfLoan purposeOfLoan = new PurposeOfLoan(purposeOfLoanType, purposeOfLoanAmount, period);
        FinanceData financeData = new FinanceData(sourcesOfIncome);

        return new CreditApplication(NaturalPerson.Builder
                .create()
                .withContactData(contactData)
                .withFinanceData(financeData)
                .withPersonalData(PersonalData
                        .Builder
                        .create()
                        .withName(name)
                        .withLastName(lastName)
                        .withMothersMaidenName(mothersMaidenName)
                        .withEducation(education)
                        .withNumOfDependants(numOfDependant)
                        .withMaritalStatus(maritalStatus)
                        .build())
                .build(),
                purposeOfLoan);
    }

    private int getPeriod(Scanner in) {
        String input;
        do {
            System.out.println("Enter loan period (in years)");
            input = in.next();
        } while (!NumberValidator.validateInteger(input, 5, 10, 15, 20, 25, 40));
        return Integer.valueOf(input);
    }

    private SourceOfIncome[] getSourceOfIncomes(Scanner in) {
        int numOfSourcesOfIncome = getNumOfSourcesOfIncome(in);
        SourceOfIncome[] sourcesOfIncome = new SourceOfIncome[numOfSourcesOfIncome];
        for (int i = 1; i <= numOfSourcesOfIncome; i++) {
            IncomeType incomeType = getIncomeType(in, i);
            double netMonthlyIncome = getNetMonthlyIncome(in, i);
            SourceOfIncome sourceOfIncome = new SourceOfIncome(incomeType, netMonthlyIncome);
            sourcesOfIncome[i - 1] = sourceOfIncome;
        }
        return sourcesOfIncome;
    }

    private double getNetMonthlyIncome(Scanner in, int i) {
        String input;
        do {
            System.out.println("Enter net monthly income of source of income " + i);
            input = in.next();
        } while (!NumberValidator.validateDouble(input, 0.0, Double.MAX_VALUE));
        return in.nextDouble();
    }

    private double getPurposeOfLoanAmount(Scanner in) {
        String input;
        do {
            System.out.println("Enter loan amount");
            input = in.next();
        } while (!NumberValidator.validateDouble(input, 0.0, Double.MAX_VALUE));
        return Double.valueOf(input);
    }

    private int getNumOfSourcesOfIncome(Scanner in) {
        String input;
        do {
            System.out.println("How many sources of income do you have?");
            input = in.next();
        } while (!NumberValidator.validateInteger(input, 0, Integer.MAX_VALUE));
        return Integer.valueOf(input);
    }

    private int getNumOfDependants(Scanner in) {
        String input;
        do {
            System.out.println("Enter number of family dependants (including applicant):");
            input = in.next();
        } while (!NumberValidator.validateInteger(input, 1, Integer.MAX_VALUE));
        return Integer.valueOf(input);
    }

    private String getName(Scanner in) {
        String input;
        do {
            System.out.println("Enter your name");
            input = in.next();
        } while (!StringValidator.validateString(input, Constants.NAME_REGEX));
        return input;
    }

    private String getLastName(Scanner in) {
        String input;
        do {
            System.out.println("Enter your last name");
            input = in.next();
        } while (!StringValidator.validateString(input, Constants.LAST_NAME_REGEX));
        return input;
    }

    private String getEmail(Scanner in) {
        String input;
        do {
            System.out.println("Enter your email");
            input = in.next();
        } while (!StringValidator.validateString(input, Constants.EMAIL_REGEX));
        return input;
    }

    private String getPhoneNumber(Scanner in) {
        String input;
        do {
            System.out.println("Enter your phone number ");
            input = in.next();
        } while (!PhoneValidator.validate(input));
        return input;
    }

    private String getMothersMaidenName(Scanner in) {
        String input;
        do {
            System.out.println("Enter your mothers maiden name");
            input = in.next();
        } while (!StringValidator.validateString(input, Constants.LAST_NAME_REGEX));
        return input;
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
            System.out.println("What is your education level? " + generateEducationElements());
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
