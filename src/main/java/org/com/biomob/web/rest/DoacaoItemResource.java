package org.com.biomob.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.com.biomob.domain.DoacaoItem;
import org.com.biomob.repository.DoacaoItemRepository;
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
 * REST controller for managing {@link org.com.biomob.domain.DoacaoItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DoacaoItemResource {

    private final Logger log = LoggerFactory.getLogger(DoacaoItemResource.class);

    private static final String ENTITY_NAME = "doacaoItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoacaoItemRepository doacaoItemRepository;

    public DoacaoItemResource(DoacaoItemRepository doacaoItemRepository) {
        this.doacaoItemRepository = doacaoItemRepository;
    }

    /**
     * {@code POST  /doacao-items} : Create a new doacaoItem.
     *
     * @param doacaoItem the doacaoItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doacaoItem, or with status {@code 400 (Bad Request)} if the doacaoItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doacao-items")
    public ResponseEntity<DoacaoItem> createDoacaoItem(@RequestBody DoacaoItem doacaoItem) throws URISyntaxException {
        log.debug("REST request to save DoacaoItem : {}", doacaoItem);
        if (doacaoItem.getId() != null) {
            throw new BadRequestAlertException("A new doacaoItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoacaoItem result = doacaoItemRepository.save(doacaoItem);
        return ResponseEntity
            .created(new URI("/api/doacao-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doacao-items/:id} : Updates an existing doacaoItem.
     *
     * @param id the id of the doacaoItem to save.
     * @param doacaoItem the doacaoItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doacaoItem,
     * or with status {@code 400 (Bad Request)} if the doacaoItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doacaoItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doacao-items/{id}")
    public ResponseEntity<DoacaoItem> updateDoacaoItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DoacaoItem doacaoItem
    ) throws URISyntaxException {
        log.debug("REST request to update DoacaoItem : {}, {}", id, doacaoItem);
        if (doacaoItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doacaoItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doacaoItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DoacaoItem result = doacaoItemRepository.save(doacaoItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doacaoItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /doacao-items/:id} : Partial updates given fields of an existing doacaoItem, field will ignore if it is null
     *
     * @param id the id of the doacaoItem to save.
     * @param doacaoItem the doacaoItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doacaoItem,
     * or with status {@code 400 (Bad Request)} if the doacaoItem is not valid,
     * or with status {@code 404 (Not Found)} if the doacaoItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the doacaoItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/doacao-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DoacaoItem> partialUpdateDoacaoItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DoacaoItem doacaoItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update DoacaoItem partially : {}, {}", id, doacaoItem);
        if (doacaoItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doacaoItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doacaoItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DoacaoItem> result = doacaoItemRepository
            .findById(doacaoItem.getId())
            .map(existingDoacaoItem -> {
                if (doacaoItem.getImagem() != null) {
                    existingDoacaoItem.setImagem(doacaoItem.getImagem());
                }
                if (doacaoItem.getImagemContentType() != null) {
                    existingDoacaoItem.setImagemContentType(doacaoItem.getImagemContentType());
                }
                if (doacaoItem.getQuantidade() != null) {
                    existingDoacaoItem.setQuantidade(doacaoItem.getQuantidade());
                }
                if (doacaoItem.getObservacao() != null) {
                    existingDoacaoItem.setObservacao(doacaoItem.getObservacao());
                }

                return existingDoacaoItem;
            })
            .map(doacaoItemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doacaoItem.getId().toString())
        );
    }

    /**
     * {@code GET  /doacao-items} : get all the doacaoItems.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doacaoItems in body.
     */
    @GetMapping("/doacao-items")
    public List<DoacaoItem> getAllDoacaoItems(@RequestParam(required = false) String filter) {
        if ("acao-is-null".equals(filter)) {
            log.debug("REST request to get all DoacaoItems where acao is null");
            return StreamSupport
                .stream(doacaoItemRepository.findAll().spliterator(), false)
                .filter(doacaoItem -> doacaoItem.getAcao() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all DoacaoItems");
        return doacaoItemRepository.findAll();
    }

    /**
     * {@code GET  /doacao-items/:id} : get the "id" doacaoItem.
     *
     * @param id the id of the doacaoItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doacaoItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doacao-items/{id}")
    public ResponseEntity<DoacaoItem> getDoacaoItem(@PathVariable Long id) {
        log.debug("REST request to get DoacaoItem : {}", id);
        Optional<DoacaoItem> doacaoItem = doacaoItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(doacaoItem);
    }

    /**
     * {@code DELETE  /doacao-items/:id} : delete the "id" doacaoItem.
     *
     * @param id the id of the doacaoItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doacao-items/{id}")
    public ResponseEntity<Void> deleteDoacaoItem(@PathVariable Long id) {
        log.debug("REST request to delete DoacaoItem : {}", id);
        doacaoItemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
