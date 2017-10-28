package com.rama.admin.repository;

import com.rama.admin.domain.MovieContentDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MovieContentDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieContentDetailsRepository extends JpaRepository<MovieContentDetails, Long> {

}
