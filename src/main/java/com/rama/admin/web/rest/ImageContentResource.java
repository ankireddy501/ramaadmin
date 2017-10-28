package com.rama.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rama.admin.domain.ImageContent;

import com.rama.admin.repository.ImageContentRepository;
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

/**
 * REST controller for managing ImageContent.
 */
@RestController
@RequestMapping("/api")
public class ImageContentResource {

    private final Logger log = LoggerFactory.getLogger(ImageContentResource.class);

    private static final String ENTITY_NAME = "imageContent";

    private final ImageContentRepository imageContentRepository;

    public ImageContentResource(ImageContentRepository imageContentRepository) {
        this.imageContentRepository = imageContentRepository;
    }

    /**
     * POST  /image-contents : Create a new imageContent.
     *
     * @param imageContent the imageContent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new imageContent, or with status 400 (Bad Request) if the imageContent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/image-contents")
    @Timed
    public ResponseEntity<ImageContent> createImageContent(@RequestBody ImageContent imageContent) throws URISyntaxException {
        log.debug("REST request to save ImageContent : {}", imageContent);
        if (imageContent.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new imageContent cannot already have an ID")).body(null);
        }
        ImageContent result = imageContentRepository.save(imageContent);
        return ResponseEntity.created(new URI("/api/image-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /image-contents : Updates an existing imageContent.
     *
     * @param imageContent the imageContent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated imageContent,
     * or with status 400 (Bad Request) if the imageContent is not valid,
     * or with status 500 (Internal Server Error) if the imageContent couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/image-contents")
    @Timed
    public ResponseEntity<ImageContent> updateImageContent(@RequestBody ImageContent imageContent) throws URISyntaxException {
        log.debug("REST request to update ImageContent : {}", imageContent);
        if (imageContent.getId() == null) {
            return createImageContent(imageContent);
        }
        ImageContent result = imageContentRepository.save(imageContent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, imageContent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /image-contents : get all the imageContents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of imageContents in body
     */
    @GetMapping("/image-contents")
    @Timed
    public List<ImageContent> getAllImageContents() {
        log.debug("REST request to get all ImageContents");
        return imageContentRepository.findAll();
        }

    /**
     * GET  /image-contents/:id : get the "id" imageContent.
     *
     * @param id the id of the imageContent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imageContent, or with status 404 (Not Found)
     */
    @GetMapping("/image-contents/{id}")
    @Timed
    public ResponseEntity<ImageContent> getImageContent(@PathVariable Long id) {
        log.debug("REST request to get ImageContent : {}", id);
        ImageContent imageContent = imageContentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(imageContent));
    }

    /**
     * DELETE  /image-contents/:id : delete the "id" imageContent.
     *
     * @param id the id of the imageContent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/image-contents/{id}")
    @Timed
    public ResponseEntity<Void> deleteImageContent(@PathVariable Long id) {
        log.debug("REST request to delete ImageContent : {}", id);
        imageContentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
