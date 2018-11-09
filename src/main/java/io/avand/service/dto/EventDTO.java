package io.avand.service.dto;

import io.avand.domain.enumeration.EventStatus;
import io.avand.domain.enumeration.EventType;

import java.io.Serializable;

public class EventDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;
    private String title;
    private String description;
    private EventType type;
    private String extra;
    private EventStatus status;
    private Long ownerId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "EventDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", type=" + type +
            ", extra='" + extra + '\'' +
            ", status=" + status +
            ", ownerId=" + ownerId +
            '}';
    }
}
