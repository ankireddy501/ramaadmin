package com.rama.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.rama.admin.domain.enumeration.ContentType;

import com.rama.admin.domain.enumeration.SubscriptionType;

/**
 * A Content.
 */
@Entity
@Table(name = "content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private ContentType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_type")
    private SubscriptionType subscriptionType;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(unique = true)
    private MovieContent video;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(unique = true)
    private ContentRules rules;

    @OneToMany(mappedBy = "content", cascade=CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ImageContent> galleries = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContentType getType() {
        return type;
    }

    public Content type(ContentType type) {
        this.type = type;
        return this;
    }

    public void setType(ContentType type) {
        this.type = type;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public Content subscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
        return this;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public MovieContent getVideo() {
        return video;
    }

    public Content video(MovieContent movieContent) {
        this.video = movieContent;
        return this;
    }

    public void setVideo(MovieContent movieContent) {
        this.video = movieContent;
    }

    public ContentRules getRules() {
        return rules;
    }

    public Content rules(ContentRules contentRules) {
        this.rules = contentRules;
        return this;
    }

    public void setRules(ContentRules contentRules) {
        this.rules = contentRules;
    }

    public Set<ImageContent> getGalleries() {
        return galleries;
    }

    public Content galleries(Set<ImageContent> imageContents) {
        this.galleries = imageContents;
        return this;
    }

    public Content addGallery(ImageContent imageContent) {
        this.galleries.add(imageContent);
        imageContent.setContent(this);
        return this;
    }

    public Content removeGallery(ImageContent imageContent) {
        this.galleries.remove(imageContent);
        imageContent.setContent(null);
        return this;
    }

    public void setGalleries(Set<ImageContent> imageContents) {
        this.galleries = imageContents;
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
        Content content = (Content) o;
        if (content.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), content.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Content{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", subscriptionType='" + getSubscriptionType() + "'" +
            "}";
    }
}
