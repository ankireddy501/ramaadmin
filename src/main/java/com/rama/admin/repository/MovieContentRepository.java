package com.rama.admin.repository;

import com.rama.admin.domain.MovieContent;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MovieContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieContentRepository extends JpaRepository<MovieContent, Long> {

}
