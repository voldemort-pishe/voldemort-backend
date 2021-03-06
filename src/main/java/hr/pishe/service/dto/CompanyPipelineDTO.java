package hr.pishe.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CompanyPipelineDTO implements Serializable {

    private Long id;

    private String title;

    private Integer weight;

    @NotNull
    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "CompanyPipelineDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", weight=" + weight +
            '}';
    }
}
