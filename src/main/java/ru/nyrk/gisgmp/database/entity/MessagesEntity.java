package ru.nyrk.gisgmp.database.entity;

import com.google.common.base.Objects;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

@javax.persistence.Table(name = "tb_messages")
@Entity
public class MessagesEntity {
    private long mesId;
    private DataEntity dataEntity;
    private Long mainMessId;
    private DataEntity dataSourceEntity;
    private Byte versionNn;
    private LocalDateTime createDt;
    private String postBlockId;
    private LocalDateTime postBlockDt;
    private String supplierBillId;
    private String systemIdentifier;
    private String payerIdentifier;
    private String bisRefNm;
    private FoldersEntity foldersEntity;
    private int changeStatus;
    private TypeMessEntity typeMessEntity;

    @javax.persistence.Column(name = "mes_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getMesId() {
        return mesId;
    }

    public void setMesId(long mesId) {
        this.mesId = mesId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_kd", referencedColumnName = "folder_kd")
    public FoldersEntity getFoldersEntity() {
        return foldersEntity;
    }

    public void setFoldersEntity(FoldersEntity foldersEntity) {
        this.foldersEntity = foldersEntity;
    }


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "data_id", referencedColumnName = "data_id")
    public DataEntity getDataEntity() {
        return dataEntity;
    }

    public void setDataEntity(DataEntity dataEntity) {
        this.dataEntity = dataEntity;
    }

    @javax.persistence.Column(name = "main_mess_id")
    @Basic
    public Long getMainMessId() {
        return mainMessId;
    }

    public void setMainMessId(Long mainMessId) {
        this.mainMessId = mainMessId;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "source_data_id", referencedColumnName = "data_id")
    public DataEntity getDataSourceEntity() {
        return dataSourceEntity;
    }

    public void setDataSourceEntity(DataEntity dataEntity) {
        this.dataSourceEntity = dataEntity;
    }

    @javax.persistence.Column(name = "version_nn")
    @Version
    public Byte getVersionNn() {
        return versionNn;
    }

    public void setVersionNn(Byte versionNn) {
        this.versionNn = versionNn;
    }

    @javax.persistence.Column(name = "create_dt")
    @Basic
//    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    public LocalDateTime getCreateDt() {
        return createDt;
    }

    public void setCreateDt(LocalDateTime createDt) {
        this.createDt = createDt;
    }

    @javax.persistence.Column(name = "post_block_id")
    @Basic
    public String getPostBlockId() {
        return postBlockId;
    }

    public void setPostBlockId(String postBlockId) {
        this.postBlockId = postBlockId;
    }

    @javax.persistence.Column(name = "post_block_dt")
    @Basic
//    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    public LocalDateTime getPostBlockDt() {
        return postBlockDt;
    }

    public void setPostBlockDt(LocalDateTime postBlockDt) {
        this.postBlockDt = postBlockDt;
    }

    @javax.persistence.Column(name = "SupplierBillID")
    @Basic
    public String getSupplierBillId() {
        return supplierBillId;
    }

    public void setSupplierBillId(String supplierBillId) {
        this.supplierBillId = supplierBillId;
    }

    @javax.persistence.Column(name = "SystemIdentifier")
    @Basic
    public String getSystemIdentifier() {
        return systemIdentifier;
    }

    public void setSystemIdentifier(String systemIdentifier) {
        this.systemIdentifier = systemIdentifier;
    }

    @javax.persistence.Column(name = "PayerIdentifier")
    @Basic
    public String getPayerIdentifier() {
        return payerIdentifier;
    }

    public void setPayerIdentifier(String payerIdentifier) {
        this.payerIdentifier = payerIdentifier;
    }

    @javax.persistence.Column(name = "Bis_ref_nm")
    @Basic
    public String getBisRefNm() {
        return bisRefNm;
    }

    public void setBisRefNm(String bisRefNm) {
        this.bisRefNm = bisRefNm;
    }

    @javax.persistence.Column(name = "ChangeStatus")
    @Basic
    public int getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(int changeStatus) {
        this.changeStatus = changeStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_mess_kd", referencedColumnName = "type_mess_kd")
    public TypeMessEntity getTypeMessEntity() {
        return typeMessEntity;
    }

    public void setTypeMessEntity(TypeMessEntity typeMessEntity) {
        this.typeMessEntity = typeMessEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessagesEntity that = (MessagesEntity) o;

        if (changeStatus != that.changeStatus) return false;
        if (mesId != that.mesId) return false;
        if (bisRefNm != null ? !bisRefNm.equals(that.bisRefNm) : that.bisRefNm != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (dataEntity != null ? !dataEntity.equals(that.dataEntity) : that.dataEntity != null) return false;
        if (dataSourceEntity != null ? !dataSourceEntity.equals(that.dataSourceEntity) : that.dataSourceEntity != null)
            return false;
        if (foldersEntity != null ? !foldersEntity.equals(that.foldersEntity) : that.foldersEntity != null)
            return false;
        if (mainMessId != null ? !mainMessId.equals(that.mainMessId) : that.mainMessId != null) return false;
        if (payerIdentifier != null ? !payerIdentifier.equals(that.payerIdentifier) : that.payerIdentifier != null)
            return false;
        if (postBlockDt != null ? !postBlockDt.equals(that.postBlockDt) : that.postBlockDt != null) return false;
        if (postBlockId != null ? !postBlockId.equals(that.postBlockId) : that.postBlockId != null) return false;
        if (supplierBillId != null ? !supplierBillId.equals(that.supplierBillId) : that.supplierBillId != null)
            return false;
        if (systemIdentifier != null ? !systemIdentifier.equals(that.systemIdentifier) : that.systemIdentifier != null)
            return false;
        if (typeMessEntity != null ? !typeMessEntity.equals(that.typeMessEntity) : that.typeMessEntity != null)
            return false;
        if (versionNn != null ? !versionNn.equals(that.versionNn) : that.versionNn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (mesId ^ (mesId >>> 32));
        result = 31 * result + (dataEntity != null ? dataEntity.hashCode() : 0);
        result = 31 * result + (mainMessId != null ? mainMessId.hashCode() : 0);
        result = 31 * result + (dataSourceEntity != null ? dataSourceEntity.hashCode() : 0);
        result = 31 * result + (versionNn != null ? versionNn.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (postBlockId != null ? postBlockId.hashCode() : 0);
        result = 31 * result + (postBlockDt != null ? postBlockDt.hashCode() : 0);
        result = 31 * result + (supplierBillId != null ? supplierBillId.hashCode() : 0);
        result = 31 * result + (systemIdentifier != null ? systemIdentifier.hashCode() : 0);
        result = 31 * result + (payerIdentifier != null ? payerIdentifier.hashCode() : 0);
        result = 31 * result + (bisRefNm != null ? bisRefNm.hashCode() : 0);
        result = 31 * result + (foldersEntity != null ? foldersEntity.hashCode() : 0);
        result = 31 * result + changeStatus;
        result = 31 * result + (typeMessEntity != null ? typeMessEntity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("mesId", mesId)
                .add("dataEntity", dataEntity)
                .add("mainMessId", mainMessId)
                .add("dataSourceEntity", dataSourceEntity)
                .add("versionNn", versionNn)
                .add("createDt", createDt)
                .add("postBlockId", postBlockId)
                .add("postBlockDt", postBlockDt)
                .add("supplierBillId", supplierBillId)
                .add("systemIdentifier", systemIdentifier)
                .add("payerIdentifier", payerIdentifier)
                .add("bisRefNm", bisRefNm)
                .add("foldersEntity", foldersEntity)
                .add("changeStatus", changeStatus)
                .add("typeMessEntity", typeMessEntity)
                .toString();
    }
}
