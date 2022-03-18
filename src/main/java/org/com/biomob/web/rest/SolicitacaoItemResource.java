package org.com.biomob.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.com.biomob.domain.SolicitacaoItem;
import org.com.biomob.repository.SolicitacaoItemRepository;
import org.com.biomob.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.com.biomob.domain.SolicitacaoItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SolicitacaoItemResource {

    private final Logger log = LoggerFactory.getLogger(SolicitacaoItemResource.class);

    private static final String ENTITY_NAME = "solicitacaoItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SolicitacaoItemRepository solicitacaoItemRepository;

    public SolicitacaoItemResource(SolicitacaoItemRepository solicitacaoItemRepository) {
        this.solicitacaoItemRepository = solicitacaoItemRepository;
    }

    /**
     * {@code POST  /solicitacao-items} : Create a new solicitacaoItem.
     *
     * @param solicitacaoItem the solicitacaoItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new solicitacaoItem, or with status {@code 400 (Bad Request)} if the solicitacaoItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solicitacao-items")
    public ResponseEntity<SolicitacaoItem> createSolicitacaoItem(@RequestBody SolicitacaoItem solicitacaoItem) throws URISyntaxException {
        log.debug("REST request to save SolicitacaoItem : {}", solicitacaoItem);
        if (solicitacaoItem.getId() != null) {
            throw new BadRequestAlertException("A new solicitacaoItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolicitacaoItem result = solicitacaoItemRepository.save(solicitacaoItem);
        return ResponseEntity
            .created(new URI("/api/solicitacao-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /solicitacao-items/:id} : Updates an existing solicitacaoItem.
     *
     * @param id the id of the solicitacaoItem to save.
     * @param solicitacaoItem the solicitacaoItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solicitacaoItem,
     * or with status {@code 400 (Bad Request)} if the solicitacaoItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the solicitacaoItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solicitacao-items/{id}")
    public ResponseEntity<SolicitacaoItem> updateSolicitacaoItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SolicitacaoItem solicitacaoItem
    ) throws URISyntaxException {
        log.debug("REST request to update SolicitacaoItem : {}, {}", id, solicitacaoItem);
        if (solicitacaoItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, solicitacaoItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!solicitacaoItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SolicitacaoItem result = solicitacaoItemRepository.save(solicitacaoItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, solicitacaoItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /solicitacao-items/:id} : Partial updates given fields of an existing solicitacaoItem, field will ignore if it is null
     *
     * @param id the id of the solicitacaoItem to save.
     * @param solicitacaoItem the solicitacaoItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solicitacaoItem,
     * or with status {@code 400 (Bad Request)} if the solicitacaoItem is not valid,
     * or with status {@code 404 (Not Found)} if the solicitacaoItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the solicitacaoItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/solicitacao-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SolicitacaoItem> partialUpdateSolicitacaoItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SolicitacaoItem solicitacaoItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update SolicitacaoItem partially : {}, {}", id, solicitacaoItem);
        if (solicitacaoItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, solicitacaoItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!solicitacaoItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SolicitacaoItem> result = solicitacaoItemRepository
            .findById(solicitacaoItem.getId())
            .map(existingSolicitacaoItem -> {
                if (solicitacaoItem.getQuantidade() != null) {
                    existingSolicitacaoItem.setQuantidade(solicitacaoItem.getQuantidade());
                }
                if (solicitacaoItem.getObservacao() != null) {
                    existingSolicitacaoItem.setObservacao(solicitacaoItem.getObservacao());
                }

                return existingSolicitacaoItem;
            })
            .map(solicitacaoItemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, solicitacaoItem.getId().toString())
        );
    }

    /**
     * {@code GET  /solicitacao-items} : get all the solicitacaoItems.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of solicitacaoItems in body.
     */
    @GetMapping("/solicitacao-items")
    public List<SolicitacaoItem> getAllSolicitacaoItems(@RequestParam(required = false) String filter) {
        if ("acao-is-null".equals(filter)) {
            log.debug("REST request to get all SolicitacaoItems where acao is null");
            return StreamSupport
                .stream(solicitacaoItemRepository.findAll().spliterator(), false)
                .filter(solicitacaoItem -> solicitacaoItem.getAcao() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all SolicitacaoItems");
        return solicitacaoItemRepository.findAll();
    }

    /**
     * {@code GET  /solicitacao-items/:id} : get the "id" solicitacaoItem.
     *
     * @param id the id of the solicitacaoItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the solicitacaoItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solicitacao-items/{id}")
    public ResponseEntity<SolicitacaoItem> getSolicitacaoItem(@PathVariable Long id) {
        log.debug("REST request to get SolicitacaoItem : {}", id);
        Optional<SolicitacaoItem> solicitacaoItem = solicitacaoItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(solicitacaoItem);
    }

    /**
     * {@code DELETE  /solicitacao-items/:id} : delete the "id" solicitacaoItem.
     *
     * @param id the id of the solicitacaoItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solicitacao-items/{id}")
    public ResponseEntity<Void> deleteSolicitacaoItem(@PathVariable Long id) {
        log.debug("REST request to delete SolicitacaoItem : {}", id);
        solicitacaoItemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
