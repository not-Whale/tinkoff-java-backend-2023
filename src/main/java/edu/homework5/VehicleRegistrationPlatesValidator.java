package edu.homework5;

public class VehicleRegistrationPlatesValidator {
    private VehicleRegistrationPlatesValidator() { }

    public static boolean validate(String platesNumber) {
        String regexCyrillic = "^[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}[1-9]?\\d{2}$";
        return platesNumber.matches(regexCyrillic);
    }
}
