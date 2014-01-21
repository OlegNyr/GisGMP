package ru.nyrk.gisgmp.database.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@javax.persistence.Table(name = "tb_type_mess")
@Entity
public class TypeMessEntity {
    private String typeMessKd;
    private String typeMessNm;

    @javax.persistence.Column(name = "type_mess_kd")
    @Id
    public String getTypeMessKd() {
        return typeMessKd;
    }

    public void setTypeMessKd(String typeMessKd) {
        this.typeMessKd = typeMessKd;
    }

    @javax.persistence.Column(name = "type_mess_nm")
    @Basic
    public String getTypeMessNm() {
        return typeMessNm;
    }

    public void setTypeMessNm(String typeMessNm) {
        this.typeMessNm = typeMessNm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeMessEntity that = (TypeMessEntity) o;

        if (typeMessKd != null ? !typeMessKd.equals(that.typeMessKd) : that.typeMessKd != null) return false;
        if (typeMessNm != null ? !typeMessNm.equals(that.typeMessNm) : that.typeMessNm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = typeMessKd != null ? typeMessKd.hashCode() : 0;
        result = 31 * result + (typeMessNm != null ? typeMessNm.hashCode() : 0);
        return result;
    }
}
