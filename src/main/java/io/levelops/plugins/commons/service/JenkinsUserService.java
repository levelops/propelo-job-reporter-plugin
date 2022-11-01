package io.levelops.plugins.commons.service;

import io.levelops.plugins.commons.models.jenkins.output.User;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JenkinsUserService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public static final String USERS_LIST_DIR = "users";
    public static final String CONFIG_XML = "config.xml";

    public List<User> readUsers(){
        List<User> users = new ArrayList<>();
        try {
            for(hudson.model.User u:hudson.model.User.getAll()){
                users.add(new User(u.getId(), u.getFullName(), u.getProperty(hudson.tasks.Mailer.UserProperty.class).getAddress()));
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception while getting all the users!!", e);
        }
        return users;
    }
}
