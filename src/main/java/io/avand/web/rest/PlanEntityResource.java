package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.PlanEntity;

import io.avand.repository.PlanEntityRepository;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.util.HeaderUtil;
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
 * REST controller for managing PlanEntity.
 */
@RestController
@RequestMapping("/api")
public class PlanEntityResource {

    private final Logger log = LoggerFactory.getLogger(PlanEntityResource.class);

    private static final String ENTITY_NAME = "planEntity";

    private final PlanEntityRepository planEntityRepository;

    public PlanEntityResource(PlanEntityRepository planEntityRepository) {
        this.planEntityRepository = planEntityRepository;
    }

    /**
     * POST  /plan-entities : Create a new planEntity.
     *
     * @param planEntity the planEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planEntity, or with status 400 (Bad Request) if the planEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plan-entities")
    @Timed
    public ResponseEntity<PlanEntity> createPlanEntity(@RequestBody PlanEntity planEntity) throws URISyntaxException {
        log.debug("REST request to save PlanEntity : {}", planEntity);
        if (planEntity.getId() != null) {
            throw new BadRequestAlertException("A new planEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanEntity result = planEntityRepository.save(planEntity);
        return ResponseEntity.created(new URI("/api/plan-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plan-entities : Updates an existing planEntity.
     *
     * @param planEntity the planEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planEntity,
     * or with status 400 (Bad Request) if the planEntity is not valid,
     * or with status 500 (Internal Server Error) if the planEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plan-entities")
    @Timed
    public ResponseEntity<PlanEntity> updatePlanEntity(@RequestBody PlanEntity planEntity) throws URISyntaxException {
        log.debug("REST request to update PlanEntity : {}", planEntity);
        if (planEntity.getId() == null) {
            return createPlanEntity(planEntity);
        }
        PlanEntity result = planEntityRepository.save(planEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, planEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plan-entities : get all the planEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of planEntities in body
     */
    @GetMapping("/plan-entities")
    @Timed
    public List<PlanEntity> getAllPlanEntities() {
        log.debug("REST request to get all PlanEntities");
        return planEntityRepository.findAll();
        }

    /**
     * GET  /plan-entities/:id : get the "id" planEntity.
     *
     * @param id the id of the planEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planEntity, or with status 404 (Not Found)
     */
    @GetMapping("/plan-entities/{id}")
    @Timed
    public ResponseEntity<PlanEntity> getPlanEntity(@PathVariable Long id) {
        log.debug("REST request to get PlanEntity : {}", id);
        PlanEntity planEntity = planEntityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(planEntity));
    }

    /**
     * DELETE  /plan-entities/:id : delete the "id" planEntity.
     *
     * @param id the id of the planEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plan-entities/{id}")
    @Timed
    public ResponseEntity<Void> deletePlanEntity(@PathVariable Long id) {
        log.debug("REST request to delete PlanEntity : {}", id);
        planEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
