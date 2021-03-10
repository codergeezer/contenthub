package com.codergeezer.contenthub.entity;

import com.codergeezer.core.base.data.BaseEntity;

import javax.persistence.*;

/**
 * @author haidv
 * @version 1.0
 */
@Entity
@Table(name = "tbl_event_failed")
public class EventFailed extends BaseEntity {

    private Long eventFailedId;

    private String eventId;

    private String eventName;

    private String functionHandle;

    private String classHandle;

    private Boolean isSync;

    private Short totalRetry;

    private String eventData;

    @Id
    @Column(name = "event_failed_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getEventFailedId() {
        return eventFailedId;
    }

    public void setEventFailedId(Long eventFailedId) {
        this.eventFailedId = eventFailedId;
    }

    @Basic
    @Column(name = "event_id")
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Basic
    @Column(name = "event_name")
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Basic
    @Column(name = "function_handle")
    public String getFunctionHandle() {
        return functionHandle;
    }

    public void setFunctionHandle(String functionHandle) {
        this.functionHandle = functionHandle;
    }

    @Basic
    @Column(name = "class_handle")
    public String getClassHandle() {
        return classHandle;
    }

    public void setClassHandle(String classHandle) {
        this.classHandle = classHandle;
    }

    @Basic
    @Column(name = "is_sync")
    public Boolean getSync() {
        return isSync;
    }

    public void setSync(Boolean sync) {
        isSync = sync;
    }

    @Basic
    @Column(name = "total_retry")
    public Short getTotalRetry() {
        return totalRetry;
    }

    public void setTotalRetry(Short totalRetry) {
        this.totalRetry = totalRetry;
    }

    @Basic
    @Column(name = "event_data")
    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }
}
