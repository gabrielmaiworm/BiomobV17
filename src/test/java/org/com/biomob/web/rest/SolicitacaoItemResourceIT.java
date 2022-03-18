package org.com.biomob.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.com.biomob.IntegrationTest;
import org.com.biomob.domain.SolicitacaoItem;
import org.com.biomob.repository.SolicitacaoItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SolicitacaoItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SolicitacaoItemResourceIT {

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/solicitacao-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SolicitacaoItemRepository solicitacaoItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSolicitacaoItemMockMvc;

    private SolicitacaoItem solicitacaoItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SolicitacaoItem createEntity(EntityManager em) {
        SolicitacaoItem solicitacaoItem = new SolicitacaoItem().quantidade(DEFAULT_QUANTIDADE).observacao(DEFAULT_OBSERVACAO);
        return solicitacaoItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SolicitacaoItem createUpdatedEntity(EntityManager em) {
        SolicitacaoItem solicitacaoItem = new SolicitacaoItem().quantidade(UPDATED_QUANTIDADE).observacao(UPDATED_OBSERVACAO);
        return solicitacaoItem;
    }

    @BeforeEach
    public void initTest() {
        solicitacaoItem = createEntity(em);
    }

    @Test
    @Transactional
    void createSolicitacaoItem() throws Exception {
        int databaseSizeBeforeCreate = solicitacaoItemRepository.findAll().size();
        // Create the SolicitacaoItem
        restSolicitacaoItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(solicitacaoItem))
            )
            .andExpect(status().isCreated());

        // Validate the SolicitacaoItem in the database
        List<SolicitacaoItem> solicitacaoItemList = solicitacaoItemRepository.findAll();
        assertThat(solicitacaoItemList).hasSize(databaseSizeBeforeCreate + 1);
        SolicitacaoItem testSolicitacaoItem = solicitacaoItemList.get(solicitacaoItemList.size() - 1);
        assertThat(testSolicitacaoItem.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testSolicitacaoItem.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
    }

    @Test
    @Transactional
    void createSolicitacaoItemWithExistingId() throws Exception {
        // Create the SolicitacaoItem with an existing ID
        solicitacaoItem.setId(1L);

        int databaseSizeBeforeCreate = solicitacaoItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolicitacaoItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(solicitacaoItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the SolicitacaoItem in the database
        List<SolicitacaoItem> solicitacaoItemList = solicitacaoItemRepository.findAll();
        assertThat(solicitacaoItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSolicitacaoItems() throws Exception {
        // Initialize the database
        solicitacaoItemRepository.saveAndFlush(solicitacaoItem);

        // Get all the solicitacaoItemList
        restSolicitacaoItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solicitacaoItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }

    @Test
    @Transactional
    void getSolicitacaoItem() throws Exception {
        // Initialize the database
        solicitacaoItemRepository.saveAndFlush(solicitacaoItem);

        // Get the solicitacaoItem
        restSolicitacaoItemMockMvc
            .perform(get(ENTITY_API_URL_ID, solicitacaoItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(solicitacaoItem.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO));
    }

    @Test
    @Transactional
    void getNonExistingSolicitacaoItem() throws Exception {
        // Get the solicitacaoItem
        restSolicitacaoItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSolicitacaoItem() throws Exception {
        // Initialize the database
        solicitacaoItemRepository.saveAndFlush(solicitacaoItem);

        int databaseSizeBeforeUpdate = solicitacaoItemRepository.findAll().size();

        // Update the solicitacaoItem
        SolicitacaoItem updatedSolicitacaoItem = solicitacaoItemRepository.findById(solicitacaoItem.getId()).get();
        // Disconnect from session so that the updates on updatedSolicitacaoItem are not directly saved in db
        em.detach(updatedSolicitacaoItem);
        updatedSolicitacaoItem.quantidade(UPDATED_QUANTIDADE).observacao(UPDATED_OBSERVACAO);

        restSolicitacaoItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSolicitacaoItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSolicitacaoItem))
            )
            .andExpect(status().isOk());

        // Validate the SolicitacaoItem in the database
        List<SolicitacaoItem> solicitacaoItemList = solicitacaoItemRepository.findAll();
        assertThat(solicitacaoItemList).hasSize(databaseSizeBeforeUpdate);
        SolicitacaoItem testSolicitacaoItem = solicitacaoItemList.get(solicitacaoItemList.size() - 1);
        assertThat(testSolicitacaoItem.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testSolicitacaoItem.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    void putNonExistingSolicitacaoItem() throws Exception {
        int databaseSizeBeforeUpdate = solicitacaoItemRepository.findAll().size();
        solicitacaoItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolicitacaoItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, solicitacaoItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(solicitacaoItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the SolicitacaoItem in the database
        List<SolicitacaoItem> solicitacaoItemList = solicitacaoItemRepository.findAll();
        assertThat(solicitacaoItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSolicitacaoItem() throws Exception {
        int databaseSizeBeforeUpdate = solicitacaoItemRepository.findAll().size();
        solicitacaoItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolicitacaoItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(solicitacaoItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the SolicitacaoItem in the database
        List<SolicitacaoItem> solicitacaoItemList = solicitacaoItemRepository.findAll();
        assertThat(solicitacaoItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSolicitacaoItem() throws Exception {
        int databaseSizeBeforeUpdate = solicitacaoItemRepository.findAll().size();
        solicitacaoItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolicitacaoItemMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(solicitacaoItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SolicitacaoItem in the database
        List<SolicitacaoItem> solicitacaoItemList = solicitacaoItemRepository.findAll();
        assertThat(solicitacaoItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSolicitacaoItemWithPatch() throws Exception {
        // Initialize the database
        solicitacaoItemRepository.saveAndFlush(solicitacaoItem);

        int databaseSizeBeforeUpdate = solicitacaoItemRepository.findAll().size();

        // Update the solicitacaoItem using partial update
        SolicitacaoItem partialUpdatedSolicitacaoItem = new SolicitacaoItem();
        partialUpdatedSolicitacaoItem.setId(solicitacaoItem.getId());

        partialUpdatedSolicitacaoItem.quantidade(UPDATED_QUANTIDADE);

        restSolicitacaoItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSolicitacaoItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSolicitacaoItem))
            )
            .andExpect(status().isOk());

        // Validate the SolicitacaoItem in the database
        List<SolicitacaoItem> solicitacaoItemList = solicitacaoItemRepository.findAll();
        assertThat(solicitacaoItemList).hasSize(databaseSizeBeforeUpdate);
        SolicitacaoItem testSolicitacaoItem = solicitacaoItemList.get(solicitacaoItemList.size() - 1);
        assertThat(testSolicitacaoItem.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testSolicitacaoItem.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
    }

    @Test
    @Transactional
    void fullUpdateSolicitacaoItemWithPatch() throws Exception {
        // Initialize the database
        solicitacaoItemRepository.saveAndFlush(solicitacaoItem);

        int databaseSizeBeforeUpdate = solicitacaoItemRepository.findAll().size();

        // Update the solicitacaoItem using partial update
        SolicitacaoItem partialUpdatedSolicitacaoItem = new SolicitacaoItem();
        partialUpdatedSolicitacaoItem.setId(solicitacaoItem.getId());

        partialUpdatedSolicitacaoItem.quantidade(UPDATED_QUANTIDADE).observacao(UPDATED_OBSERVACAO);

        restSolicitacaoItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSolicitacaoItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSolicitacaoItem))
            )
            .andExpect(status().isOk());

        // Validate the SolicitacaoItem in the database
        List<SolicitacaoItem> solicitacaoItemList = solicitacaoItemRepository.findAll();
        assertThat(solicitacaoItemList).hasSize(databaseSizeBeforeUpdate);
        SolicitacaoItem testSolicitacaoItem = solicitacaoItemList.get(solicitacaoItemList.size() - 1);
        assertThat(testSolicitacaoItem.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testSolicitacaoItem.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    void patchNonExistingSolicitacaoItem() throws Exception {
        int databaseSizeBeforeUpdate = solicitacaoItemRepository.findAll().size();
        solicitacaoItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolicitacaoItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, solicitacaoItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(solicitacaoItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the SolicitacaoItem in the database
        List<SolicitacaoItem> solicitacaoItemList = solicitacaoItemRepository.findAll();
        assertThat(solicitacaoItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSolicitacaoItem() throws Exception {
        int databaseSizeBeforeUpdate = solicitacaoItemRepository.findAll().size();
        solicitacaoItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolicitacaoItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(solicitacaoItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the SolicitacaoItem in the database
        List<SolicitacaoItem> solicitacaoItemList = solicitacaoItemRepository.findAll();
        assertThat(solicitacaoItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSolicitacaoItem() throws Exception {
        int databaseSizeBeforeUpdate = solicitacaoItemRepository.findAll().size();
        solicitacaoItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolicitacaoItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(solicitacaoItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SolicitacaoItem in the database
        List<SolicitacaoItem> solicitacaoItemList = solicitacaoItemRepository.findAll();
        assertThat(solicitacaoItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSolicitacaoItem() throws Exception {
        // Initialize the database
        solicitacaoItemRepository.saveAndFlush(solicitacaoItem);

        int databaseSizeBeforeDelete = solicitacaoItemRepository.findAll().size();

        // Delete the solicitacaoItem
        restSolicitacaoItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, solicitacaoItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SolicitacaoItem> solicitacaoItemList = solicitacaoItemRepository.findAll();
        assertThat(solicitacaoItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
