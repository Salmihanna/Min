package hotelbookingmanagementproject;

import java.util.*;

public class ExceptionsHandeling {

    public <T> String PadRight(T t) {
        String string = (String) t;
        int totalStringLength = 15;
        int charsToPadd = totalStringLength - string.length();

        // incase the string is the same length or longer than our maximum lenght
        if (string.length() >= totalStringLength) {
            return string;
        }

        StringBuilder stringBuilder = new StringBuilder(string);
        for (int i = 0; i < charsToPadd; i++) {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}
