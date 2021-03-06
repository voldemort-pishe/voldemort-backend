package hr.pishe.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.pishe.domain.enumeration.JobStatus;
import hr.pishe.domain.enumeration.JobType;
import hr.pishe.domain.enumeration.LanguageType;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class JobDTO extends AbstractAuditingDTO implements Serializable {

    private String uniqueId;
    private String nameFa;
    private String descriptionFa;
    private String nameEn;
    private String descriptionEn;
    private LanguageType language;
    private JobType type;
    private String location;
    private String department;
    private JobStatus status;
    @JsonIgnore
    private Set<JobHireTeamDTO> jobHireTeam = new HashSet<>();
    @JsonIgnore
    private Set<CandidateDTO> candidate = new HashSet<>();

    private Long companyId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getNameFa() {
        return nameFa;
    }

    public void setNameFa(String nameFa) {
        this.nameFa = nameFa;
    }

    public String getDescriptionFa() {
        return descriptionFa;
    }

    public void setDescriptionFa(String descriptionFa) {
        this.descriptionFa = descriptionFa;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public LanguageType getLanguage() {
        return language;
    }

    public void setLanguage(LanguageType language) {
        this.language = language;
    }

    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public Set<JobHireTeamDTO> getJobHireTeam() {
        return jobHireTeam;
    }

    public void setJobHireTeam(Set<JobHireTeamDTO> jobHireTeam) {
        this.jobHireTeam = jobHireTeam;
    }

    public Set<CandidateDTO> getCandidate() {
        return candidate;
    }

    public void setCandidate(Set<CandidateDTO> candidate) {
        this.candidate = candidate;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "JobDTO{" +
            "uniqueId='" + uniqueId + '\'' +
            ", nameFa='" + nameFa + '\'' +
            ", descriptionFa='" + descriptionFa + '\'' +
            ", nameEn='" + nameEn + '\'' +
            ", descriptionEn='" + descriptionEn + '\'' +
            ", language=" + language +
            ", type=" + type +
            ", location='" + location + '\'' +
            ", department='" + department + '\'' +
            ", status=" + status +
            ", companyId=" + companyId +
            '}';
    }
}
