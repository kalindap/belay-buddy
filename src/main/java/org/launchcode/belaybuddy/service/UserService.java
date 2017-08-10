package org.launchcode.belaybuddy.service;

import org.launchcode.belaybuddy.models.User;

/**
 * Created by kalindapiper on 8/2/17.
 */
public interface UserService {
    public User findUserByEmail(String email);
    public void saveUser(User user);
}
