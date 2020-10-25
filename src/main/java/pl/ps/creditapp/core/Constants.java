package pl.ps.creditapp.core;

public class Constants {
    public static final double MORTGAGE_LOAN_RATE;
    public static final double PERSONAL_LOAN_LOAN_RATE;
    public static final String DOUBLE_REGEX;
    public static final String INTEGER_REGEX;
    public static final String NAME_REGEX;
    public static final String LAST_NAME_REGEX;
    public static final String EMAIL_REGEX;
    public static final String PHONE_REGEX;
    public static final double MIN_LOAN_AMOUNT_MORTGAGE;

    static{
        MORTGAGE_LOAN_RATE = 0.2;
        PERSONAL_LOAN_LOAN_RATE = 0.1;
        DOUBLE_REGEX = "(\\d+)(\\.\\d+)?";
        INTEGER_REGEX = "[0-9]+";
        NAME_REGEX = "[A-ZĄ-Ź][a-zą-ź]{2,9}";
        LAST_NAME_REGEX = "([A-ZĄ-Ź][a-zą-ź]+)([\\s-]([A-ZĄ-Ź][a-zą-ź]+))?";
        EMAIL_REGEX = ".+@.+";
        PHONE_REGEX = "(\\+\\d{2})?\\d{9}";
        MIN_LOAN_AMOUNT_MORTGAGE = 100000.00;
    }
}