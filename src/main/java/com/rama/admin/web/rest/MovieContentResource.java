package com.rama.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rama.admin.domain.MovieContent;

import com.rama.admin.repository.MovieContentRepository;
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
 * REST controller for managing MovieContent.
 */
@RestController
@RequestMapping("/api")
public class MovieContentResource {

    private final Logger log = LoggerFactory.getLogger(MovieContentResource.class);

    private static final String ENTITY_NAME = "movieContent";

    private final MovieContentRepository movieContentRepository;

    public MovieContentResource(MovieContentRepository movieContentRepository) {
        this.movieContentRepository = movieContentRepository;
    }

    /**
     * POST  /movie-contents : Create a new movieContent.
     *
     * @param movieContent the movieContent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movieContent, or with status 400 (Bad Request) if the movieContent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movie-contents")
    @Timed
    public ResponseEntity<MovieContent> createMovieContent(@RequestBody MovieContent movieContent) throws URISyntaxException {
        log.debug("REST request to save MovieContent : {}", movieContent);
        if (movieContent.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new movieContent cannot already have an ID")).body(null);
        }
        MovieContent result = movieContentRepository.save(movieContent);
        return ResponseEntity.created(new URI("/api/movie-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movie-contents : Updates an existing movieContent.
     *
     * @param movieContent the movieContent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movieContent,
     * or with status 400 (Bad Request) if the movieContent is not valid,
     * or with status 500 (Internal Server Error) if the movieContent couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movie-contents")
    @Timed
    public ResponseEntity<MovieContent> updateMovieContent(@RequestBody MovieContent movieContent) throws URISyntaxException {
        log.debug("REST request to update MovieContent : {}", movieContent);
        if (movieContent.getId() == null) {
            return createMovieContent(movieContent);
        }
        MovieContent result = movieContentRepository.save(movieContent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movieContent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movie-contents : get all the movieContents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of movieContents in body
     */
    @GetMapping("/movie-contents")
    @Timed
    public List<MovieContent> getAllMovieContents() {
        log.debug("REST request to get all MovieContents");
        return movieContentRepository.findAll();
        }

    /**
     * GET  /movie-contents/:id : get the "id" movieContent.
     *
     * @param id the id of the movieContent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movieContent, or with status 404 (Not Found)
     */
    @GetMapping("/movie-contents/{id}")
    @Timed
    public ResponseEntity<MovieContent> getMovieContent(@PathVariable Long id) {
        log.debug("REST request to get MovieContent : {}", id);
        MovieContent movieContent = movieContentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movieContent));
    }

    /**
     * DELETE  /movie-contents/:id : delete the "id" movieContent.
     *
     * @param id the id of the movieContent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movie-contents/{id}")
    @Timed
    public ResponseEntity<Void> deleteMovieContent(@PathVariable Long id) {
        log.debug("REST request to delete MovieContent : {}", id);
        movieContentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
