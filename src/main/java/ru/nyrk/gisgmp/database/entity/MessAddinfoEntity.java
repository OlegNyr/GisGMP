package ru.nyrk.gisgmp.database.entity;

import javax.persistence.*;

@javax.persistence.Table(name = "tb_mess_addinfo", schema = "", catalog = "gisgmp")
@Entity
public class MessAddinfoEntity {
    private long messAddinfoId;
    private Long mesId;
    private String fldNm;
    private String fieldVl;

    @javax.persistence.Column(name = "mess_addinfo_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getMessAddinfoId() {
        return messAddinfoId;
    }

    public void setMessAddinfoId(long messAddinfoId) {
        this.messAddinfoId = messAddinfoId;
    }

    @javax.persistence.Column(name = "mes_id")
    @Basic
    public Long getMesId() {
        return mesId;
    }

    public void setMesId(Long mesId) {
        this.mesId = mesId;
    }

    @javax.persistence.Column(name = "fld_nm")
    @Basic
    public String getFldNm() {
        return fldNm;
    }

    public void setFldNm(String fldNm) {
        this.fldNm = fldNm;
    }

    @javax.persistence.Column(name = "field_vl")
    @Basic
    public String getFieldVl() {
        return fieldVl;
    }

    public void setFieldVl(String fieldVl) {
        this.fieldVl = fieldVl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessAddinfoEntity that = (MessAddinfoEntity) o;

        if (messAddinfoId != that.messAddinfoId) return false;
        if (fieldVl != null ? !fieldVl.equals(that.fieldVl) : that.fieldVl != null) return false;
        if (fldNm != null ? !fldNm.equals(that.fldNm) : that.fldNm != null) return false;
        if (mesId != null ? !mesId.equals(that.mesId) : that.mesId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (messAddinfoId ^ (messAddinfoId >>> 32));
        result = 31 * result + (mesId != null ? mesId.hashCode() : 0);
        result = 31 * result + (fldNm != null ? fldNm.hashCode() : 0);
        result = 31 * result + (fieldVl != null ? fieldVl.hashCode() : 0);
        return result;
    }
}
