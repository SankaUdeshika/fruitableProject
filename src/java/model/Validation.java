package model;

public class Validation {

    public static boolean isEmailValid(String email) {
        return email.matches("^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
    }

    public static boolean ispasswordValid(String password) {
        return password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$");
    }

    public static boolean isDouble(String text) {
        return text.matches("^\\d+(\\.\\d{2})?$");
    }

    public static boolean isInteger(String text) {
        return text.matches("^\\d+$");
    }
    
    public static boolean IsMobileValid(String mobile){
        return mobile.matches("^(\\+94|0)?7[0-9]{8}$");
    }
}
