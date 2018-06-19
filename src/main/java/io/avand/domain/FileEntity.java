package io.avand.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A FileEntity.
 */
@Entity
@Table(name = "file_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FileEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "filetype")
    private String filetype;

    @OneToOne(mappedBy = "file")
    @JsonIgnore
    private CandidateEntity candidate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public FileEntity filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public FileEntity filetype(String filetype) {
        this.filetype = filetype;
        return this;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public CandidateEntity getCandidate() {
        return candidate;
    }

    public FileEntity candidate(CandidateEntity candidateEntity) {
        this.candidate = candidateEntity;
        return this;
    }

    public void setCandidate(CandidateEntity candidateEntity) {
        this.candidate = candidateEntity;
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
        FileEntity fileEntity = (FileEntity) o;
        if (fileEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fileEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FileEntity{" +
            "id=" + getId() +
            ", filename='" + getFilename() + "'" +
            ", filetype='" + getFiletype() + "'" +
            "}";
    }
}
