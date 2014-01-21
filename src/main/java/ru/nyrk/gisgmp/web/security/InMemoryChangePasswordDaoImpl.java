package ru.nyrk.gisgmp.web.security;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collection;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: oanyrkov
 * Date: 07.03.13
 * Time: 11:09
 */
public class InMemoryChangePasswordDaoImpl extends InMemoryUserDetailsManager implements IChangePassword{
    public InMemoryChangePasswordDaoImpl(Collection<UserDetails> users) {
        super(users);
    }

    public InMemoryChangePasswordDaoImpl(Properties users) {
        super(users);
    }

    @Override
    public void changeUserPassword(String userName, String password) {

        // get the UserDetails
        UserDetails userDetails = loadUserByUsername(userName);

        // create a new UserDetails with the new password
        User newUserDetails = new User(userDetails.getUsername(), password,
                userDetails.isEnabled(), userDetails.isAccountNonExpired(),
                userDetails.isCredentialsNonExpired(), userDetails
                .isAccountNonLocked(), userDetails.getAuthorities());

        // add to the map
        updateUser(newUserDetails);
    }
}
