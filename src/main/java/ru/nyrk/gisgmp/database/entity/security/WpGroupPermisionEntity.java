package ru.nyrk.gisgmp.database.entity.security;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: olegnyr
 * Date: 18.12.13
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.IdClass(WpGroupPermisionEntityPK.class)
@javax.persistence.Table(name = "wp_group_permision", schema = "", catalog = "gisgmp")
@Entity
public class WpGroupPermisionEntity {
    private long groupId;

    @javax.persistence.Column(name = "group_id")
    @Id
    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    private long permissionId;

    @javax.persistence.Column(name = "permission_id")
    @Id
    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WpGroupPermisionEntity that = (WpGroupPermisionEntity) o;

        if (groupId != that.groupId) return false;
        if (permissionId != that.permissionId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (groupId ^ (groupId >>> 32));
        result = 31 * result + (int) (permissionId ^ (permissionId >>> 32));
        return result;
    }
}
