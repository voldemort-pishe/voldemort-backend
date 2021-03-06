package hr.pishe.domain.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.pishe.domain.enumeration.JobStatus;
import hr.pishe.domain.enumeration.JobType;
import hr.pishe.domain.enumeration.LanguageType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A JobEntity.
 */
@Entity
@Table(name = "job")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class JobEntity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unique_id")
    private String uniqueId;

    @Column(name = "name_fa")
    private String nameFa;

    @Lob
    @Column(name = "description_fa")
    private String descriptionFa;

    @Column(name = "name_en")
    private String nameEn;

    @Lob
    @Column(name = "description_en")
    private String descriptionEn;

    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    private LanguageType language;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private JobType type;

    @Column(name = "location")
    private String location;

    @Column(name = "department")
    private String department;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<JobHireTeamEntity> jobHireTeam = new HashSet<>();

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<CandidateEntity> candidate = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private CompanyEntity company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Set<JobHireTeamEntity> getJobHireTeam() {
        return jobHireTeam;
    }

    public void setJobHireTeam(Set<JobHireTeamEntity> jobHireTeam) {
        this.jobHireTeam = jobHireTeam;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<CandidateEntity> getCandidate() {
        return candidate;
    }

    public void setCandidate(Set<CandidateEntity> candidate) {
        this.candidate = candidate;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public JobEntity company(CompanyEntity companyEntity) {
        this.company = companyEntity;
        return this;
    }

    public void setCompany(CompanyEntity companyEntity) {
        this.company = companyEntity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobEntity jobEntity = (JobEntity) o;
        if (jobEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobEntity{" +
            "id=" + id +
            ", uniqueId='" + uniqueId + '\'' +
            ", nameFa='" + nameFa + '\'' +
            ", descriptionFa='" + descriptionFa + '\'' +
            ", nameEn='" + nameEn + '\'' +
            ", descriptionEn='" + descriptionEn + '\'' +
            ", language=" + language +
            ", type=" + type +
            ", location='" + location + '\'' +
            ", department='" + department + '\'' +
            ", status=" + status +
            '}';
    }
}
