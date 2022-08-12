package io.levelops.plugins.commons.utils;

import hudson.model.User;

public class MimickedUser {
    public static final String UNKNOWN_USER_NAME = "unknown";
    public static final String UNKNOWN_USER_ID = "unknown";

    private String id;
    private String fullName;

    public MimickedUser(User user) {
        this(
                (user != null) ? user.getId() : UNKNOWN_USER_NAME,
                (user != null) ? user.getFullName() : UNKNOWN_USER_ID);
    }

    public MimickedUser(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    /**
     *
     * @return {@link User#getId()}
     */
    public String getId() { return id; }

    /**
     *
     * @return a string matching {@link User#getFullName()}
     */
    public String getFullName() { return fullName; }

    public User getUser(boolean create) {
        return User.getById(getId(), create);
    }
}
