package pl.ps.creditapp.client;

public class StringValidator {

    public static boolean validateString(String input, String regex){
        return input.matches(regex);
    }
}
