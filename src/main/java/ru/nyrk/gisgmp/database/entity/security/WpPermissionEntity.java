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
@javax.persistence.Table(name = "wp_permission", schema = "", catalog = "gisgmp")
@Entity
public class WpPermissionEntity {
    private long permissionId;

    @javax.persistence.Column(name = "permission_id")
    @Id
    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }

    private String permissionNm;

    @javax.persistence.Column(name = "permission_nm")
    @Basic
    public String getPermissionNm() {
        return permissionNm;
    }

    public void setPermissionNm(String permissionNm) {
        this.permissionNm = permissionNm;
    }

    private String permissionDsc;

    @javax.persistence.Column(name = "permission_dsc")
    @Basic
    public String getPermissionDsc() {
        return permissionDsc;
    }

    public void setPermissionDsc(String permissionDsc) {
        this.permissionDsc = permissionDsc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WpPermissionEntity that = (WpPermissionEntity) o;

        if (permissionId != that.permissionId) return false;
        if (permissionDsc != null ? !permissionDsc.equals(that.permissionDsc) : that.permissionDsc != null)
            return false;
        if (permissionNm != null ? !permissionNm.equals(that.permissionNm) : that.permissionNm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (permissionId ^ (permissionId >>> 32));
        result = 31 * result + (permissionNm != null ? permissionNm.hashCode() : 0);
        result = 31 * result + (permissionDsc != null ? permissionDsc.hashCode() : 0);
        return result;
    }
}
