package io.levelops.plugins.commons.utils;

import java.io.PrintStream;
import java.util.Objects;

public class Logger {

    private static final String LOG_FORMAT = "[Propelo Publisher] %s - %s"; // <LEVEL> <Message>

    private static void log(PrintStream out, String level, String message) {
        out.println(String.format(LOG_FORMAT, level, Objects.toString(message)));
    }

    public static void info(PrintStream out, String message) {
        log(out, "INFO", message);
    }

    public static void error(PrintStream out, String message) {
        log(out, "ERROR", message);
    }

    public static void debug(PrintStream out, String message) {
        log(out, "DEBUG", message);
    }

    public static void error(PrintStream out, String message, Exception e) {
        error(out, message + " " + e.toString());
        e.printStackTrace(out);
    }
}
