package ru.nyrk.gisgmp.web.security;

import org.springframework.data.domain.AuditorAware;

/**
 * Created with IntelliJ IDEA.
 * User: oanyrkov
 * Date: 28.05.13
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
public class AuditorAwareBean implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        return MyUserDetails.getMyUserDetails().getUsername();
    }
}
