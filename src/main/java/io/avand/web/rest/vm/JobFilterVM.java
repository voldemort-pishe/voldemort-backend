package io.avand.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.avand.domain.enumeration.JobStatus;

import java.io.Serializable;

public class JobFilterVM implements Serializable {

    private JobStatus status;
    private Long manager;
    @JsonIgnore
    private Long company;

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public Long getManager() {
        return manager;
    }

    public void setManager(Long manager) {
        this.manager = manager;
    }

    public Long getCompany() {
        return company;
    }

    public void setCompany(Long company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "JobFilterVM{" +
            "status=" + status +
            ", manager=" + manager +
            ", company=" + company +
            '}';
    }
}
