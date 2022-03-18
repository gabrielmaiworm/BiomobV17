package org.com.biomob.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.com.biomob.domain.CadastroSolicitacao;
import org.com.biomob.repository.CadastroSolicitacaoRepository;
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
 * REST controller for managing {@link org.com.biomob.domain.CadastroSolicitacao}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CadastroSolicitacaoResource {

    private final Logger log = LoggerFactory.getLogger(CadastroSolicitacaoResource.class);

    private static final String ENTITY_NAME = "cadastroSolicitacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CadastroSolicitacaoRepository cadastroSolicitacaoRepository;

    public CadastroSolicitacaoResource(CadastroSolicitacaoRepository cadastroSolicitacaoRepository) {
        this.cadastroSolicitacaoRepository = cadastroSolicitacaoRepository;
    }

    /**
     * {@code POST  /cadastro-solicitacaos} : Create a new cadastroSolicitacao.
     *
     * @param cadastroSolicitacao the cadastroSolicitacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cadastroSolicitacao, or with status {@code 400 (Bad Request)} if the cadastroSolicitacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cadastro-solicitacaos")
    public ResponseEntity<CadastroSolicitacao> createCadastroSolicitacao(@RequestBody CadastroSolicitacao cadastroSolicitacao)
        throws URISyntaxException {
        log.debug("REST request to save CadastroSolicitacao : {}", cadastroSolicitacao);
        if (cadastroSolicitacao.getId() != null) {
            throw new BadRequestAlertException("A new cadastroSolicitacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CadastroSolicitacao result = cadastroSolicitacaoRepository.save(cadastroSolicitacao);
        return ResponseEntity
            .created(new URI("/api/cadastro-solicitacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cadastro-solicitacaos/:id} : Updates an existing cadastroSolicitacao.
     *
     * @param id the id of the cadastroSolicitacao to save.
     * @param cadastroSolicitacao the cadastroSolicitacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cadastroSolicitacao,
     * or with status {@code 400 (Bad Request)} if the cadastroSolicitacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cadastroSolicitacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cadastro-solicitacaos/{id}")
    public ResponseEntity<CadastroSolicitacao> updateCadastroSolicitacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CadastroSolicitacao cadastroSolicitacao
    ) throws URISyntaxException {
        log.debug("REST request to update CadastroSolicitacao : {}, {}", id, cadastroSolicitacao);
        if (cadastroSolicitacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cadastroSolicitacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cadastroSolicitacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CadastroSolicitacao result = cadastroSolicitacaoRepository.save(cadastroSolicitacao);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cadastroSolicitacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cadastro-solicitacaos/:id} : Partial updates given fields of an existing cadastroSolicitacao, field will ignore if it is null
     *
     * @param id the id of the cadastroSolicitacao to save.
     * @param cadastroSolicitacao the cadastroSolicitacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cadastroSolicitacao,
     * or with status {@code 400 (Bad Request)} if the cadastroSolicitacao is not valid,
     * or with status {@code 404 (Not Found)} if the cadastroSolicitacao is not found,
     * or with status {@code 500 (Internal Server Error)} if the cadastroSolicitacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cadastro-solicitacaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CadastroSolicitacao> partialUpdateCadastroSolicitacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CadastroSolicitacao cadastroSolicitacao
    ) throws URISyntaxException {
        log.debug("REST request to partial update CadastroSolicitacao partially : {}, {}", id, cadastroSolicitacao);
        if (cadastroSolicitacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cadastroSolicitacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cadastroSolicitacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CadastroSolicitacao> result = cadastroSolicitacaoRepository
            .findById(cadastroSolicitacao.getId())
            .map(existingCadastroSolicitacao -> {
                if (cadastroSolicitacao.getNomeSolicitante() != null) {
                    existingCadastroSolicitacao.setNomeSolicitante(cadastroSolicitacao.getNomeSolicitante());
                }
                if (cadastroSolicitacao.getCpfSolicitante() != null) {
                    existingCadastroSolicitacao.setCpfSolicitante(cadastroSolicitacao.getCpfSolicitante());
                }
                if (cadastroSolicitacao.getDataSolicitacao() != null) {
                    existingCadastroSolicitacao.setDataSolicitacao(cadastroSolicitacao.getDataSolicitacao());
                }
                if (cadastroSolicitacao.getEndereco() != null) {
                    existingCadastroSolicitacao.setEndereco(cadastroSolicitacao.getEndereco());
                }
                if (cadastroSolicitacao.getPontoReferencia() != null) {
                    existingCadastroSolicitacao.setPontoReferencia(cadastroSolicitacao.getPontoReferencia());
                }
                if (cadastroSolicitacao.getSituacaoSolicitante() != null) {
                    existingCadastroSolicitacao.setSituacaoSolicitante(cadastroSolicitacao.getSituacaoSolicitante());
                }
                if (cadastroSolicitacao.getQuantidadePessoas() != null) {
                    existingCadastroSolicitacao.setQuantidadePessoas(cadastroSolicitacao.getQuantidadePessoas());
                }
                if (cadastroSolicitacao.getRetiraDoacao() != null) {
                    existingCadastroSolicitacao.setRetiraDoacao(cadastroSolicitacao.getRetiraDoacao());
                }
                if (cadastroSolicitacao.getPerdeuMoradia() != null) {
                    existingCadastroSolicitacao.setPerdeuMoradia(cadastroSolicitacao.getPerdeuMoradia());
                }
                if (cadastroSolicitacao.getPerdeuEmprego() != null) {
                    existingCadastroSolicitacao.setPerdeuEmprego(cadastroSolicitacao.getPerdeuEmprego());
                }
                if (cadastroSolicitacao.getIdosoDeficiente() != null) {
                    existingCadastroSolicitacao.setIdosoDeficiente(cadastroSolicitacao.getIdosoDeficiente());
                }
                if (cadastroSolicitacao.getFilhosPequenos() != null) {
                    existingCadastroSolicitacao.setFilhosPequenos(cadastroSolicitacao.getFilhosPequenos());
                }
                if (cadastroSolicitacao.getObservacao() != null) {
                    existingCadastroSolicitacao.setObservacao(cadastroSolicitacao.getObservacao());
                }
                if (cadastroSolicitacao.getDataAprovacao() != null) {
                    existingCadastroSolicitacao.setDataAprovacao(cadastroSolicitacao.getDataAprovacao());
                }
                if (cadastroSolicitacao.getAtiva() != null) {
                    existingCadastroSolicitacao.setAtiva(cadastroSolicitacao.getAtiva());
                }
                if (cadastroSolicitacao.getFatorPrioridade() != null) {
                    existingCadastroSolicitacao.setFatorPrioridade(cadastroSolicitacao.getFatorPrioridade());
                }
                if (cadastroSolicitacao.getStatusSolicitacao() != null) {
                    existingCadastroSolicitacao.setStatusSolicitacao(cadastroSolicitacao.getStatusSolicitacao());
                }

                return existingCadastroSolicitacao;
            })
            .map(cadastroSolicitacaoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cadastroSolicitacao.getId().toString())
        );
    }

    /**
     * {@code GET  /cadastro-solicitacaos} : get all the cadastroSolicitacaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cadastroSolicitacaos in body.
     */
    @GetMapping("/cadastro-solicitacaos")
    public List<CadastroSolicitacao> getAllCadastroSolicitacaos() {
        log.debug("REST request to get all CadastroSolicitacaos");
        return cadastroSolicitacaoRepository.findAll();
    }

    /**
     * {@code GET  /cadastro-solicitacaos/:id} : get the "id" cadastroSolicitacao.
     *
     * @param id the id of the cadastroSolicitacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cadastroSolicitacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cadastro-solicitacaos/{id}")
    public ResponseEntity<CadastroSolicitacao> getCadastroSolicitacao(@PathVariable Long id) {
        log.debug("REST request to get CadastroSolicitacao : {}", id);
        Optional<CadastroSolicitacao> cadastroSolicitacao = cadastroSolicitacaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cadastroSolicitacao);
    }

    /**
     * {@code DELETE  /cadastro-solicitacaos/:id} : delete the "id" cadastroSolicitacao.
     *
     * @param id the id of the cadastroSolicitacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cadastro-solicitacaos/{id}")
    public ResponseEntity<Void> deleteCadastroSolicitacao(@PathVariable Long id) {
        log.debug("REST request to delete CadastroSolicitacao : {}", id);
        cadastroSolicitacaoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
