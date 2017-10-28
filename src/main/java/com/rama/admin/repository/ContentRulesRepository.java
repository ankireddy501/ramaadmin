package com.rama.admin.repository;

import com.rama.admin.domain.ContentRules;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContentRules entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentRulesRepository extends JpaRepository<ContentRules, Long> {

}
