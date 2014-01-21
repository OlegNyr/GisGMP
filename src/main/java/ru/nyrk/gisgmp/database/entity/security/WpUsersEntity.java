package ru.nyrk.gisgmp.database.entity.security;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: olegnyr
 * Date: 18.12.13
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "wp_users", schema = "", catalog = "gisgmp")
@Entity
public class WpUsersEntity {
    private long userId;

    @javax.persistence.Column(name = "user_id")
    @Id
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    private String userNm;

    @javax.persistence.Column(name = "user_nm")
    @Basic
    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    private String userFirstNm;

    @javax.persistence.Column(name = "user_first_nm")
    @Basic
    public String getUserFirstNm() {
        return userFirstNm;
    }

    public void setUserFirstNm(String userFirstNm) {
        this.userFirstNm = userFirstNm;
    }

    private String userNextNm;

    @javax.persistence.Column(name = "user_next_nm")
    @Basic
    public String getUserNextNm() {
        return userNextNm;
    }

    public void setUserNextNm(String userNextNm) {
        this.userNextNm = userNextNm;
    }

    private String userFamilyNm;

    @javax.persistence.Column(name = "user_family_nm")
    @Basic
    public String getUserFamilyNm() {
        return userFamilyNm;
    }

    public void setUserFamilyNm(String userFamilyNm) {
        this.userFamilyNm = userFamilyNm;
    }

    private String emailNm;

    @javax.persistence.Column(name = "email_nm")
    @Basic
    public String getEmailNm() {
        return emailNm;
    }

    public void setEmailNm(String emailNm) {
        this.emailNm = emailNm;
    }

    private String password;

    @javax.persistence.Column(name = "password")
    @Basic
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String phoneNm;

    @javax.persistence.Column(name = "phone_nm")
    @Basic
    public String getPhoneNm() {
        return phoneNm;
    }

    public void setPhoneNm(String phoneNm) {
        this.phoneNm = phoneNm;
    }

    private Boolean userEnabledPr;

    @javax.persistence.Column(name = "user_enabled_pr")
    @Basic
    public Boolean getUserEnabledPr() {
        return userEnabledPr;
    }

    public void setUserEnabledPr(Boolean userEnabledPr) {
        this.userEnabledPr = userEnabledPr;
    }

    private String userUpperNm;

    @javax.persistence.Column(name = "user_upper_nm")
    @Basic
    public String getUserUpperNm() {
        return userUpperNm;
    }

    public void setUserUpperNm(String userUpperNm) {
        this.userUpperNm = userUpperNm;
    }

    private String salt;

    @javax.persistence.Column(name = "salt")
    @Basic
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WpUsersEntity that = (WpUsersEntity) o;

        if (userId != that.userId) return false;
        if (emailNm != null ? !emailNm.equals(that.emailNm) : that.emailNm != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (phoneNm != null ? !phoneNm.equals(that.phoneNm) : that.phoneNm != null) return false;
        if (salt != null ? !salt.equals(that.salt) : that.salt != null) return false;
        if (userEnabledPr != null ? !userEnabledPr.equals(that.userEnabledPr) : that.userEnabledPr != null)
            return false;
        if (userFamilyNm != null ? !userFamilyNm.equals(that.userFamilyNm) : that.userFamilyNm != null) return false;
        if (userFirstNm != null ? !userFirstNm.equals(that.userFirstNm) : that.userFirstNm != null) return false;
        if (userNextNm != null ? !userNextNm.equals(that.userNextNm) : that.userNextNm != null) return false;
        if (userNm != null ? !userNm.equals(that.userNm) : that.userNm != null) return false;
        if (userUpperNm != null ? !userUpperNm.equals(that.userUpperNm) : that.userUpperNm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (userNm != null ? userNm.hashCode() : 0);
        result = 31 * result + (userFirstNm != null ? userFirstNm.hashCode() : 0);
        result = 31 * result + (userNextNm != null ? userNextNm.hashCode() : 0);
        result = 31 * result + (userFamilyNm != null ? userFamilyNm.hashCode() : 0);
        result = 31 * result + (emailNm != null ? emailNm.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (phoneNm != null ? phoneNm.hashCode() : 0);
        result = 31 * result + (userEnabledPr != null ? userEnabledPr.hashCode() : 0);
        result = 31 * result + (userUpperNm != null ? userUpperNm.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        return result;
    }
}
