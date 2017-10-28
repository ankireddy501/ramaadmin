package com.rama.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.rama.admin.domain.enumeration.LifeTime;

import com.rama.admin.domain.enumeration.Validity;

/**
 * A ContentRules.
 */
@Entity
@Table(name = "content_rules")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContentRules implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "life_time")
    private LifeTime lifeTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "validity")
    private Validity validity;

    @Column(name = "jhi_cost")
    private Integer cost;

    @OneToOne(mappedBy = "rules",cascade=CascadeType.ALL)
    @JsonIgnore
    private Content content;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LifeTime getLifeTime() {
        return lifeTime;
    }

    public ContentRules lifeTime(LifeTime lifeTime) {
        this.lifeTime = lifeTime;
        return this;
    }

    public void setLifeTime(LifeTime lifeTime) {
        this.lifeTime = lifeTime;
    }

    public Validity getValidity() {
        return validity;
    }

    public ContentRules validity(Validity validity) {
        this.validity = validity;
        return this;
    }

    public void setValidity(Validity validity) {
        this.validity = validity;
    }

    public Integer getCost() {
        return cost;
    }

    public ContentRules cost(Integer cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Content getContent() {
        return content;
    }

    public ContentRules content(Content content) {
        this.content = content;
        return this;
    }

    public void setContent(Content content) {
        this.content = content;
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
        ContentRules contentRules = (ContentRules) o;
        if (contentRules.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contentRules.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContentRules{" +
            "id=" + getId() +
            ", lifeTime='" + getLifeTime() + "'" +
            ", validity='" + getValidity() + "'" +
            ", cost='" + getCost() + "'" +
            "}";
    }
}
