package hr.pishe.service.dto;

import hr.pishe.domain.enumeration.PlanConfigType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PlanConfigDTO implements Serializable {
    private Long id;
    private PlanConfigType type;
    private Long value;
    @NotNull
    private Long planId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlanConfigType getType() {
        return type;
    }

    public void setType(PlanConfigType type) {
        this.type = type;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    @Override
    public String toString() {
        return "PlanConfigDTO{" +
            "id=" + id +
            ", type=" + type +
            ", value=" + value +
            ", planId=" + planId +
            '}';
    }
}
