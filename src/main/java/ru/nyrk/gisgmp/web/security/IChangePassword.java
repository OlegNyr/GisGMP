package ru.nyrk.gisgmp.web.security;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created with IntelliJ IDEA.
 * User: oanyrkov
 * Date: 07.03.13
 * Time: 10:55
 */
public interface IChangePassword extends UserDetailsService {
    void changeUserPassword(String userName, String password);

}
