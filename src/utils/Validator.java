package utils;

public class Validator {

    // Никой не може да направи "new Validator()"
    private Validator() {
    }

    public static String validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("ГРЕШКА: " + fieldName + " не може да бъде празно!");
        }
        return value;
    }

    public static long validatePositive(long value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException("ГРЕШКА: " + fieldName + " не може да бъде отрицателно!");
        }
        return value;
    }

    public static <T> T validateNotNull(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException("ГРЕШКА: " + fieldName + " не може да бъде null!");
        }
        return value;
    }
}
