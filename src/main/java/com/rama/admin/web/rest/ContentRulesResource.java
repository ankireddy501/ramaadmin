package com.rama.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rama.admin.domain.ContentRules;

import com.rama.admin.repository.ContentRulesRepository;
import com.rama.admin.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing ContentRules.
 */
@RestController
@RequestMapping("/api")
public class ContentRulesResource {

    private final Logger log = LoggerFactory.getLogger(ContentRulesResource.class);

    private static final String ENTITY_NAME = "contentRules";

    private final ContentRulesRepository contentRulesRepository;

    public ContentRulesResource(ContentRulesRepository contentRulesRepository) {
        this.contentRulesRepository = contentRulesRepository;
    }

    /**
     * POST  /content-rules : Create a new contentRules.
     *
     * @param contentRules the contentRules to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contentRules, or with status 400 (Bad Request) if the contentRules has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/content-rules")
    @Timed
    public ResponseEntity<ContentRules> createContentRules(@RequestBody ContentRules contentRules) throws URISyntaxException {
        log.debug("REST request to save ContentRules : {}", contentRules);
        if (contentRules.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contentRules cannot already have an ID")).body(null);
        }
        ContentRules result = contentRulesRepository.save(contentRules);
        return ResponseEntity.created(new URI("/api/content-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /content-rules : Updates an existing contentRules.
     *
     * @param contentRules the contentRules to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contentRules,
     * or with status 400 (Bad Request) if the contentRules is not valid,
     * or with status 500 (Internal Server Error) if the contentRules couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/content-rules")
    @Timed
    public ResponseEntity<ContentRules> updateContentRules(@RequestBody ContentRules contentRules) throws URISyntaxException {
        log.debug("REST request to update ContentRules : {}", contentRules);
        if (contentRules.getId() == null) {
            return createContentRules(contentRules);
        }
        ContentRules result = contentRulesRepository.save(contentRules);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contentRules.getId().toString()))
            .body(result);
    }

    /**
     * GET  /content-rules : get all the contentRules.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of contentRules in body
     */
    @GetMapping("/content-rules")
    @Timed
    public List<ContentRules> getAllContentRules(@RequestParam(required = false) String filter) {
        if ("content-is-null".equals(filter)) {
            log.debug("REST request to get all ContentRuless where content is null");
            return StreamSupport
                .stream(contentRulesRepository.findAll().spliterator(), false)
                .filter(contentRules -> contentRules.getContent() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all ContentRules");
        return contentRulesRepository.findAll();
        }

    /**
     * GET  /content-rules/:id : get the "id" contentRules.
     *
     * @param id the id of the contentRules to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contentRules, or with status 404 (Not Found)
     */
    @GetMapping("/content-rules/{id}")
    @Timed
    public ResponseEntity<ContentRules> getContentRules(@PathVariable Long id) {
        log.debug("REST request to get ContentRules : {}", id);
        ContentRules contentRules = contentRulesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contentRules));
    }

    /**
     * DELETE  /content-rules/:id : delete the "id" contentRules.
     *
     * @param id the id of the contentRules to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/content-rules/{id}")
    @Timed
    public ResponseEntity<Void> deleteContentRules(@PathVariable Long id) {
        log.debug("REST request to delete ContentRules : {}", id);
        contentRulesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
