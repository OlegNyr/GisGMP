package ru.nyrk.gisgmp.database.entity.security;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: olegnyr
 * Date: 18.12.13
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
public class WpGroupPermisionEntityPK implements Serializable {
    private long groupId;
    private long permissionId;

@Id
@Column(name = "group_id")
public long getGroupId() {
    return groupId;
}

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Id@Column(name = "permission_id")
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

        WpGroupPermisionEntityPK that = (WpGroupPermisionEntityPK) o;

        if (groupId != that.groupId) return false;
        if (permissionId != that.permissionId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (groupId ^ (groupId >>> 32));
        result = 31 * result + (int) (permissionId ^ (permissionId >>> 32));
        return result;
}}
