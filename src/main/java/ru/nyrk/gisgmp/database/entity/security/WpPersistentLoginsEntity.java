package ru.nyrk.gisgmp.database.entity.security;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: olegnyr
 * Date: 18.12.13
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "wp_persistent_logins", schema = "", catalog = "gisgmp")
@Entity
public class WpPersistentLoginsEntity {
    private String seriesStr;

    @javax.persistence.Column(name = "series_str")
    @Id
    public String getSeriesStr() {
        return seriesStr;
    }

    public void setSeriesStr(String seriesStr) {
        this.seriesStr = seriesStr;
    }

    private String tokenStr;

    @javax.persistence.Column(name = "token_str")
    @Basic
    public String getTokenStr() {
        return tokenStr;
    }

    public void setTokenStr(String tokenStr) {
        this.tokenStr = tokenStr;
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

    private Timestamp lastUsed;

    @javax.persistence.Column(name = "last_used")
    @Basic
    public Timestamp getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Timestamp lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WpPersistentLoginsEntity that = (WpPersistentLoginsEntity) o;

        if (lastUsed != null ? !lastUsed.equals(that.lastUsed) : that.lastUsed != null) return false;
        if (seriesStr != null ? !seriesStr.equals(that.seriesStr) : that.seriesStr != null) return false;
        if (tokenStr != null ? !tokenStr.equals(that.tokenStr) : that.tokenStr != null) return false;
        if (userUpperNm != null ? !userUpperNm.equals(that.userUpperNm) : that.userUpperNm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = seriesStr != null ? seriesStr.hashCode() : 0;
        result = 31 * result + (tokenStr != null ? tokenStr.hashCode() : 0);
        result = 31 * result + (userUpperNm != null ? userUpperNm.hashCode() : 0);
        result = 31 * result + (lastUsed != null ? lastUsed.hashCode() : 0);
        return result;
    }
}
