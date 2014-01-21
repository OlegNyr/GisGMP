package ru.nyrk.gisgmp.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MyUserDetails extends User {
    private Family family;
    private String email;
    private String phone;
    private String salt;
    private Integer mainAgentId;
    private Integer trmId;


    public MyUserDetails(String username,
                         String password,
                         Collection<? extends GrantedAuthority> authorities,
                         Family family,
                         String email,
                         String phone,
                         String salt
    ) {
        super(username, password, authorities);
        this.family = family;
        this.email = email;
        this.phone = phone;
        this.salt = salt;
    }

    public MyUserDetails(String username,
                         String password,
                         boolean enabled,
                         boolean accountNonExpired,
                         boolean credentialsNonExpired,
                         boolean accountNonLocked,
                         Collection<? extends GrantedAuthority> authorities,
                         Family family,
                         String email,
                         String phone,
                         String salt
    ) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.family = family;
        this.email = email;
        this.phone = phone;
        this.salt = salt;
        this.mainAgentId = mainAgentId;
        this.trmId = trmId;
    }

    public MyUserDetails(MyUserDetails myUserDetails, Collection<? extends GrantedAuthority> authorities) {
        super(myUserDetails.getUsername(), myUserDetails.getPassword(), myUserDetails.isEnabled(), myUserDetails.isAccountNonExpired(), myUserDetails.isCredentialsNonExpired(), myUserDetails.isAccountNonLocked(), authorities);
        this.family = myUserDetails.getFamily();
        this.email = myUserDetails.getEmail();
        this.phone = myUserDetails.getPhone();
        this.salt = myUserDetails.getSalt();
        this.mainAgentId = myUserDetails.getMainAgentId();
        this.trmId = myUserDetails.getTrmId();
    }

    static public MyUserDetails getMyUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof MyUserDetails) {
            return ((MyUserDetails) principal);
        } else {
            return null;
        }
    }

    /**
     * @return Имя отчество фамилиия
     */
    public Family getFamily() {
        return family;
    }

    /**
     * @return Почта
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return Телефон
     */
    public String getPhone() {
        return phone;
    }

    public String getSalt() {
        return salt;
    }

    public Integer getMainAgentId() {
        return mainAgentId;
    }

    public void setMainAgentId(Integer mainAgentId) {
        this.mainAgentId = mainAgentId;
    }

    public Integer getTrmId() {
        return trmId;
    }

    public void setTrmId(Integer trmId) {
        this.trmId = trmId;
    }
}
