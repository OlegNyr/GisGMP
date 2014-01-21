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
public class WpUsersGroupEntityPK implements Serializable {
    private long userId;
    private long groupId;

@Id@Column(name = "user_id")
public long getUserId() {
    return userId;
}

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Id@Column(name = "group_id")
    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WpUsersGroupEntityPK that = (WpUsersGroupEntityPK) o;

        if (groupId != that.groupId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (groupId ^ (groupId >>> 32));
        return result;
}}
