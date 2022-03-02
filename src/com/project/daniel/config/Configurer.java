package com.project.daniel.config;

public abstract class Configurer {
    protected static final String CONFIG_FILE = "resources/config.txt";

    protected static String extractPropertyValue(final String line) {
        int index = 0;
        char ch = line.charAt(index);
        while (ch != '=') {
            index++;
            ch = line.charAt(index);
        }
        index++;
        StringBuilder value = new StringBuilder();
        while (index < line.length()) {
            ch = line.charAt(index);
            value.append(ch);
            index++;
        }
        return value.toString();
    }
}