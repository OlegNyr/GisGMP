package ru.nyrk.gisgmp.database.entity;

import com.google.common.base.Objects;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

@javax.persistence.Table(name = "tb_ticket")
@Entity
public class TicketEntity {
    private long ticketId;
    private DataEntity dataEntity;
    private MessagesEntity messagesEntity;
    private LocalDateTime ticketDt;
    private String errorKd;
    private String errorMsg;


    @javax.persistence.Column(name = "ticket_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "data_id", referencedColumnName = "data_id")
    public DataEntity getDataEntity() {
        return dataEntity;
    }

    public void setDataEntity(DataEntity dataEntity) {
        this.dataEntity = dataEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mes_id", referencedColumnName = "mes_id")
    public MessagesEntity getMessagesEntity() {
        return messagesEntity;
    }

    public void setMessagesEntity(MessagesEntity messagesEntity) {
        this.messagesEntity = messagesEntity;
    }


    @javax.persistence.Column(name = "ticket_dt")
    @Basic
    public LocalDateTime getTicketDt() {
        return ticketDt;
    }

    public void setTicketDt(LocalDateTime ticketDt) {
        this.ticketDt = ticketDt;
    }

    @javax.persistence.Column(name = "error_kd")
    @Basic
    public String getErrorKd() {
        return errorKd;
    }

    public void setErrorKd(String errorKd) {
        this.errorKd = errorKd;
    }

    @javax.persistence.Column(name = "error_msg")
    @Basic
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketEntity that = (TicketEntity) o;

        if (ticketId != that.ticketId) return false;
        if (dataEntity != null ? !dataEntity.equals(that.dataEntity) : that.dataEntity != null) return false;
        if (errorKd != null ? !errorKd.equals(that.errorKd) : that.errorKd != null) return false;
        if (errorMsg != null ? !errorMsg.equals(that.errorMsg) : that.errorMsg != null) return false;
        if (messagesEntity != null ? !messagesEntity.equals(that.messagesEntity) : that.messagesEntity != null)
            return false;
        if (ticketDt != null ? !ticketDt.equals(that.ticketDt) : that.ticketDt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (ticketId ^ (ticketId >>> 32));
        result = 31 * result + (dataEntity != null ? dataEntity.hashCode() : 0);
        result = 31 * result + (messagesEntity != null ? messagesEntity.hashCode() : 0);
        result = 31 * result + (ticketDt != null ? ticketDt.hashCode() : 0);
        result = 31 * result + (errorKd != null ? errorKd.hashCode() : 0);
        result = 31 * result + (errorMsg != null ? errorMsg.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("ticketId", ticketId)
                .add("dataEntity", dataEntity)
                .add("messagesEntity", messagesEntity)
                .add("ticketDt", ticketDt)
                .add("errorKd", errorKd)
                .add("errorMsg", errorMsg)
                .toString();
    }
}
