package com.doctor.visit.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "jhi_persistent_audit_event")
public class JhiPersistentAuditEvent implements Serializable {
    @Id
    @Column(name = "event_id")
    private Long eventId;

    private String principal;

    @Column(name = "event_date")
    private Date eventDate;

    @Column(name = "event_type")
    private String eventType;

    private static final long serialVersionUID = 1L;

    /**
     * @return event_id
     */
    public Long getEventId() {
        return eventId;
    }

    /**
     * @param eventId
     */
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    /**
     * @return principal
     */
    public String getPrincipal() {
        return principal;
    }

    /**
     * @param principal
     */
    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    /**
     * @return event_date
     */
    public Date getEventDate() {
        return eventDate;
    }

    /**
     * @param eventDate
     */
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * @return event_type
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * @param eventType
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}