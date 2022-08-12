package io.levelops.plugins.commons.utils;

public class EnvironmentVariableNotDefinedException extends IllegalArgumentException{
    public EnvironmentVariableNotDefinedException() {
        super();
    }

    public EnvironmentVariableNotDefinedException(final String message) {
        super(message);
    }
}
