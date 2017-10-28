package com.rama.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rama.admin.domain.MovieContentDetails;

import com.rama.admin.repository.MovieContentDetailsRepository;
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
 * REST controller for managing MovieContentDetails.
 */
@RestController
@RequestMapping("/api")
public class MovieContentDetailsResource {

    private final Logger log = LoggerFactory.getLogger(MovieContentDetailsResource.class);

    private static final String ENTITY_NAME = "movieContentDetails";

    private final MovieContentDetailsRepository movieContentDetailsRepository;

    public MovieContentDetailsResource(MovieContentDetailsRepository movieContentDetailsRepository) {
        this.movieContentDetailsRepository = movieContentDetailsRepository;
    }

    /**
     * POST  /movie-content-details : Create a new movieContentDetails.
     *
     * @param movieContentDetails the movieContentDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movieContentDetails, or with status 400 (Bad Request) if the movieContentDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movie-content-details")
    @Timed
    public ResponseEntity<MovieContentDetails> createMovieContentDetails(@RequestBody MovieContentDetails movieContentDetails) throws URISyntaxException {
        log.debug("REST request to save MovieContentDetails : {}", movieContentDetails);
        if (movieContentDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new movieContentDetails cannot already have an ID")).body(null);
        }
        MovieContentDetails result = movieContentDetailsRepository.save(movieContentDetails);
        return ResponseEntity.created(new URI("/api/movie-content-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movie-content-details : Updates an existing movieContentDetails.
     *
     * @param movieContentDetails the movieContentDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movieContentDetails,
     * or with status 400 (Bad Request) if the movieContentDetails is not valid,
     * or with status 500 (Internal Server Error) if the movieContentDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movie-content-details")
    @Timed
    public ResponseEntity<MovieContentDetails> updateMovieContentDetails(@RequestBody MovieContentDetails movieContentDetails) throws URISyntaxException {
        log.debug("REST request to update MovieContentDetails : {}", movieContentDetails);
        if (movieContentDetails.getId() == null) {
            return createMovieContentDetails(movieContentDetails);
        }
        MovieContentDetails result = movieContentDetailsRepository.save(movieContentDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movieContentDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movie-content-details : get all the movieContentDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of movieContentDetails in body
     */
    @GetMapping("/movie-content-details")
    @Timed
    public List<MovieContentDetails> getAllMovieContentDetails() {
        log.debug("REST request to get all MovieContentDetails");
        return movieContentDetailsRepository.findAll();
        }

    /**
     * GET  /movie-content-details/:id : get the "id" movieContentDetails.
     *
     * @param id the id of the movieContentDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movieContentDetails, or with status 404 (Not Found)
     */
    @GetMapping("/movie-content-details/{id}")
    @Timed
    public ResponseEntity<MovieContentDetails> getMovieContentDetails(@PathVariable Long id) {
        log.debug("REST request to get MovieContentDetails : {}", id);
        MovieContentDetails movieContentDetails = movieContentDetailsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movieContentDetails));
    }

    /**
     * DELETE  /movie-content-details/:id : delete the "id" movieContentDetails.
     *
     * @param id the id of the movieContentDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movie-content-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteMovieContentDetails(@PathVariable Long id) {
        log.debug("REST request to delete MovieContentDetails : {}", id);
        movieContentDetailsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
