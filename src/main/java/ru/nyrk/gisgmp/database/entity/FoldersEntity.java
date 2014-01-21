package ru.nyrk.gisgmp.database.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@javax.persistence.Table(name = "tb_folders")
@Entity
public class FoldersEntity {
    private String folderKd;
    private String folderNm;

    @javax.persistence.Column(name = "folder_kd")
    @Id
    public String getFolderKd() {
        return folderKd;
    }

    public void setFolderKd(String folderKd) {
        this.folderKd = folderKd;
    }

    @javax.persistence.Column(name = "folder_nm")
    @Basic
    public String getFolderNm() {
        return folderNm;
    }

    public void setFolderNm(String folderNm) {
        this.folderNm = folderNm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FoldersEntity that = (FoldersEntity) o;

        if (folderKd != null ? !folderKd.equals(that.folderKd) : that.folderKd != null) return false;
        if (folderNm != null ? !folderNm.equals(that.folderNm) : that.folderNm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = folderKd != null ? folderKd.hashCode() : 0;
        result = 31 * result + (folderNm != null ? folderNm.hashCode() : 0);
        return result;
    }
}
