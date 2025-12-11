package org.example.tictactoe.utils;

public class CharacterConvertorUtils {

    public static String toSubscript(int num) {
        String normal = String.valueOf(num);
        StringBuilder sb = new StringBuilder();

        for (char c : normal.toCharArray()) {
            switch (c) {
                case '0': sb.append("₀"); break;
                case '1': sb.append("₁"); break;
                case '2': sb.append("₂"); break;
                case '3': sb.append("₃"); break;
                case '4': sb.append("₄"); break;
                case '5': sb.append("₅"); break;
                case '6': sb.append("₆"); break;
                case '7': sb.append("₇"); break;
                case '8': sb.append("₈"); break;
                case '9': sb.append("₉"); break;
            }
        }
        return sb.toString();
    }

}
