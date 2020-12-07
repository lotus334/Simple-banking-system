package org.hyperskill.banking;

import java.util.ArrayList;
import java.util.List;

public class LuhnAlgorithm {
    public static String createNumber(String number) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < number.length() - 1; i++) {
            list.add(Integer.parseInt(String.valueOf(number.charAt(i))));
        }
        for (int i = 0; i < number.length() - 1; i += 2) {
            list.set(i, list.get(i) * 2);
        }
        for (int i = 0; i < number.length() - 1; i++) {
            if (list.get(i) > 9) {
                list.set(i, list.get(i) - 9);
            }
        }
        int sum = list.stream().reduce(Integer::sum).orElse(0);
        int lastInteger = 10 - sum % 10;
        if (lastInteger == 10) {
            lastInteger = 0;
        }
        return number.substring(0, number.length() - 1) + lastInteger;
    }
}
