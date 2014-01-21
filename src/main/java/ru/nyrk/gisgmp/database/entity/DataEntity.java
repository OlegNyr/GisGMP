package ru.nyrk.gisgmp.database.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Arrays;

@javax.persistence.Table(name = "tb_data")
@Entity
public class DataEntity {
    private long dataId;
    private byte[] mesData;
    private String fileNm;

    public DataEntity() {
    }

    public DataEntity(byte[] mesData) {
        this.mesData = mesData;
    }

    public DataEntity(byte[] mesData, String fileNm) {
        this.mesData = mesData;
        this.fileNm = fileNm;
    }

    @javax.persistence.Column(name = "data_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getDataId() {
        return dataId;
    }

    public void setDataId(long dataId) {
        this.dataId = dataId;
    }

    @javax.persistence.Column(name = "mes_data")
    @Lob
    public byte[] getMesData() {
        return mesData;
    }

    public void setMesData(byte[] mesData) {
        this.mesData = mesData;
    }

    @javax.persistence.Column(name = "file_nm")
    @Basic
    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataEntity that = (DataEntity) o;

        if (dataId != that.dataId) return false;
        if (fileNm != null ? !fileNm.equals(that.fileNm) : that.fileNm != null) return false;
        if (!Arrays.equals(mesData, that.mesData)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (dataId ^ (dataId >>> 32));
        result = 31 * result + (mesData != null ? Arrays.hashCode(mesData) : 0);
        result = 31 * result + (fileNm != null ? fileNm.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("dataId", dataId)
                .add("mesData", mesData)
                .add("fileNm", fileNm)
                .toString();
    }
}
