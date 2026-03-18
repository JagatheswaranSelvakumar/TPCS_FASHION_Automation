package com.in.saragroup.tpcsambur.utilities;

import java.util.Random;

public class RandomUtils {

    public static String generateRandomColor() {

//        String[] colors = {"Red", "Blue", "Green", "Yellow", "Black", "White", "Orange", "Purple", "Pink", "Brown",
//                "Gray", "Cyan", "Magenta", "Maroon", "Olive", "Lime", "Teal", "Navy", "Gold", "Silver", "Coral",
//                "Indigo", "Ivory", "Khaki", "Lavender", "Mint", "Peach", "Plum", "Salmon", "Turquoise", "Violet",
//                "Amber", "Beige", "Crimson", "Fuchsia", "Jade", "Lilac", "Mustard", "Ruby", "Sapphire"};

        String[] colors = {"Red1", "Blue1", "Green1", "Yellow1", "Black1", "White1", "Orange1", "Purple1", "Pink1", "Brown1"};

        Random random = new Random();

        return colors[random.nextInt(colors.length)];
    }

    public static String generateRandomString(int length) {

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }

        return result.toString();
    }

}
