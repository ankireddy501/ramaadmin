package com.rama.admin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MovieContent.
 */
@Entity
@Table(name = "movie_content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MovieContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "content_path")
    private String contentPath;

    @Column(name = "creation_time")
    private LocalDate creationTime;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(unique = true)
    private MovieContentDetails details;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MovieContent name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public MovieContent description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContentPath() {
        return contentPath;
    }

    public MovieContent contentPath(String contentPath) {
        this.contentPath = contentPath;
        return this;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public LocalDate getCreationTime() {
        return creationTime;
    }

    public MovieContent creationTime(LocalDate creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public void setCreationTime(LocalDate creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public MovieContent updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public MovieContentDetails getDetails() {
        return details;
    }

    public MovieContent details(MovieContentDetails movieContentDetails) {
        this.details = movieContentDetails;
        return this;
    }

    public void setDetails(MovieContentDetails movieContentDetails) {
        this.details = movieContentDetails;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MovieContent movieContent = (MovieContent) o;
        if (movieContent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movieContent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovieContent{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", contentPath='" + getContentPath() + "'" +
            ", creationTime='" + getCreationTime() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
