package ru.nyrk.gisgmp.database.entity;

import org.joda.time.LocalDateTime;

import javax.persistence.*;

@javax.persistence.Table(name = "tb_log", schema = "", catalog = "gisgmp")
@Entity
public class LogEntity {
    private long logId;
    private Long mesId;
    private String logLevel;
    private LocalDateTime logDt;
    private String logMsg;
    private String logData;

    @javax.persistence.Column(name = "log_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    @javax.persistence.Column(name = "mes_id")
    @Basic
    public Long getMesId() {
        return mesId;
    }

    public void setMesId(Long mesId) {
        this.mesId = mesId;
    }

    @javax.persistence.Column(name = "log_level")
    @Basic
    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    @javax.persistence.Column(name = "log_dt")
    @Basic
    public LocalDateTime getLogDt() {
        return logDt;
    }

    public void setLogDt(LocalDateTime logDt) {
        this.logDt = logDt;
    }

    @javax.persistence.Column(name = "log_msg")
    @Basic
    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    @javax.persistence.Column(name = "log_data")
    @Basic
    public String getLogData() {
        return logData;
    }

    public void setLogData(String logData) {
        this.logData = logData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogEntity logEntity = (LogEntity) o;

        if (logId != logEntity.logId) return false;
        if (logData != null ? !logData.equals(logEntity.logData) : logEntity.logData != null) return false;
        if (logDt != null ? !logDt.equals(logEntity.logDt) : logEntity.logDt != null) return false;
        if (logLevel != null ? !logLevel.equals(logEntity.logLevel) : logEntity.logLevel != null) return false;
        if (logMsg != null ? !logMsg.equals(logEntity.logMsg) : logEntity.logMsg != null) return false;
        if (mesId != null ? !mesId.equals(logEntity.mesId) : logEntity.mesId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (logId ^ (logId >>> 32));
        result = 31 * result + (mesId != null ? mesId.hashCode() : 0);
        result = 31 * result + (logLevel != null ? logLevel.hashCode() : 0);
        result = 31 * result + (logDt != null ? logDt.hashCode() : 0);
        result = 31 * result + (logMsg != null ? logMsg.hashCode() : 0);
        result = 31 * result + (logData != null ? logData.hashCode() : 0);
        return result;
    }
}
