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
@javax.persistence.Table(name = "wp_group", schema = "", catalog = "gisgmp")
@Entity
public class WpGroupEntity {
    private long groupId;

    @javax.persistence.Column(name = "group_id")
    @Id
    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    private String groupNm;

    @javax.persistence.Column(name = "group_nm")
    @Basic
    public String getGroupNm() {
        return groupNm;
    }

    public void setGroupNm(String groupNm) {
        this.groupNm = groupNm;
    }

    private String groupDsc;

    @javax.persistence.Column(name = "group_dsc")
    @Basic
    public String getGroupDsc() {
        return groupDsc;
    }

    public void setGroupDsc(String groupDsc) {
        this.groupDsc = groupDsc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WpGroupEntity that = (WpGroupEntity) o;

        if (groupId != that.groupId) return false;
        if (groupDsc != null ? !groupDsc.equals(that.groupDsc) : that.groupDsc != null) return false;
        if (groupNm != null ? !groupNm.equals(that.groupNm) : that.groupNm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (groupId ^ (groupId >>> 32));
        result = 31 * result + (groupNm != null ? groupNm.hashCode() : 0);
        result = 31 * result + (groupDsc != null ? groupDsc.hashCode() : 0);
        return result;
    }
}
