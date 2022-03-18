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
import org.com.biomob.domain.DoacaoItem;
import org.com.biomob.repository.DoacaoItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link DoacaoItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DoacaoItemResourceIT {

    private static final byte[] DEFAULT_IMAGEM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEM_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/doacao-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DoacaoItemRepository doacaoItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoacaoItemMockMvc;

    private DoacaoItem doacaoItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoacaoItem createEntity(EntityManager em) {
        DoacaoItem doacaoItem = new DoacaoItem()
            .imagem(DEFAULT_IMAGEM)
            .imagemContentType(DEFAULT_IMAGEM_CONTENT_TYPE)
            .quantidade(DEFAULT_QUANTIDADE)
            .observacao(DEFAULT_OBSERVACAO);
        return doacaoItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoacaoItem createUpdatedEntity(EntityManager em) {
        DoacaoItem doacaoItem = new DoacaoItem()
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .quantidade(UPDATED_QUANTIDADE)
            .observacao(UPDATED_OBSERVACAO);
        return doacaoItem;
    }

    @BeforeEach
    public void initTest() {
        doacaoItem = createEntity(em);
    }

    @Test
    @Transactional
    void createDoacaoItem() throws Exception {
        int databaseSizeBeforeCreate = doacaoItemRepository.findAll().size();
        // Create the DoacaoItem
        restDoacaoItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doacaoItem)))
            .andExpect(status().isCreated());

        // Validate the DoacaoItem in the database
        List<DoacaoItem> doacaoItemList = doacaoItemRepository.findAll();
        assertThat(doacaoItemList).hasSize(databaseSizeBeforeCreate + 1);
        DoacaoItem testDoacaoItem = doacaoItemList.get(doacaoItemList.size() - 1);
        assertThat(testDoacaoItem.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testDoacaoItem.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testDoacaoItem.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testDoacaoItem.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
    }

    @Test
    @Transactional
    void createDoacaoItemWithExistingId() throws Exception {
        // Create the DoacaoItem with an existing ID
        doacaoItem.setId(1L);

        int databaseSizeBeforeCreate = doacaoItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoacaoItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doacaoItem)))
            .andExpect(status().isBadRequest());

        // Validate the DoacaoItem in the database
        List<DoacaoItem> doacaoItemList = doacaoItemRepository.findAll();
        assertThat(doacaoItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDoacaoItems() throws Exception {
        // Initialize the database
        doacaoItemRepository.saveAndFlush(doacaoItem);

        // Get all the doacaoItemList
        restDoacaoItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doacaoItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }

    @Test
    @Transactional
    void getDoacaoItem() throws Exception {
        // Initialize the database
        doacaoItemRepository.saveAndFlush(doacaoItem);

        // Get the doacaoItem
        restDoacaoItemMockMvc
            .perform(get(ENTITY_API_URL_ID, doacaoItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doacaoItem.getId().intValue()))
            .andExpect(jsonPath("$.imagemContentType").value(DEFAULT_IMAGEM_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagem").value(Base64Utils.encodeToString(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO));
    }

    @Test
    @Transactional
    void getNonExistingDoacaoItem() throws Exception {
        // Get the doacaoItem
        restDoacaoItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDoacaoItem() throws Exception {
        // Initialize the database
        doacaoItemRepository.saveAndFlush(doacaoItem);

        int databaseSizeBeforeUpdate = doacaoItemRepository.findAll().size();

        // Update the doacaoItem
        DoacaoItem updatedDoacaoItem = doacaoItemRepository.findById(doacaoItem.getId()).get();
        // Disconnect from session so that the updates on updatedDoacaoItem are not directly saved in db
        em.detach(updatedDoacaoItem);
        updatedDoacaoItem
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .quantidade(UPDATED_QUANTIDADE)
            .observacao(UPDATED_OBSERVACAO);

        restDoacaoItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDoacaoItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDoacaoItem))
            )
            .andExpect(status().isOk());

        // Validate the DoacaoItem in the database
        List<DoacaoItem> doacaoItemList = doacaoItemRepository.findAll();
        assertThat(doacaoItemList).hasSize(databaseSizeBeforeUpdate);
        DoacaoItem testDoacaoItem = doacaoItemList.get(doacaoItemList.size() - 1);
        assertThat(testDoacaoItem.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testDoacaoItem.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testDoacaoItem.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testDoacaoItem.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    void putNonExistingDoacaoItem() throws Exception {
        int databaseSizeBeforeUpdate = doacaoItemRepository.findAll().size();
        doacaoItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoacaoItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doacaoItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doacaoItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoacaoItem in the database
        List<DoacaoItem> doacaoItemList = doacaoItemRepository.findAll();
        assertThat(doacaoItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDoacaoItem() throws Exception {
        int databaseSizeBeforeUpdate = doacaoItemRepository.findAll().size();
        doacaoItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoacaoItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doacaoItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoacaoItem in the database
        List<DoacaoItem> doacaoItemList = doacaoItemRepository.findAll();
        assertThat(doacaoItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDoacaoItem() throws Exception {
        int databaseSizeBeforeUpdate = doacaoItemRepository.findAll().size();
        doacaoItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoacaoItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doacaoItem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DoacaoItem in the database
        List<DoacaoItem> doacaoItemList = doacaoItemRepository.findAll();
        assertThat(doacaoItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDoacaoItemWithPatch() throws Exception {
        // Initialize the database
        doacaoItemRepository.saveAndFlush(doacaoItem);

        int databaseSizeBeforeUpdate = doacaoItemRepository.findAll().size();

        // Update the doacaoItem using partial update
        DoacaoItem partialUpdatedDoacaoItem = new DoacaoItem();
        partialUpdatedDoacaoItem.setId(doacaoItem.getId());

        partialUpdatedDoacaoItem.quantidade(UPDATED_QUANTIDADE).observacao(UPDATED_OBSERVACAO);

        restDoacaoItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoacaoItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDoacaoItem))
            )
            .andExpect(status().isOk());

        // Validate the DoacaoItem in the database
        List<DoacaoItem> doacaoItemList = doacaoItemRepository.findAll();
        assertThat(doacaoItemList).hasSize(databaseSizeBeforeUpdate);
        DoacaoItem testDoacaoItem = doacaoItemList.get(doacaoItemList.size() - 1);
        assertThat(testDoacaoItem.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testDoacaoItem.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testDoacaoItem.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testDoacaoItem.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    void fullUpdateDoacaoItemWithPatch() throws Exception {
        // Initialize the database
        doacaoItemRepository.saveAndFlush(doacaoItem);

        int databaseSizeBeforeUpdate = doacaoItemRepository.findAll().size();

        // Update the doacaoItem using partial update
        DoacaoItem partialUpdatedDoacaoItem = new DoacaoItem();
        partialUpdatedDoacaoItem.setId(doacaoItem.getId());

        partialUpdatedDoacaoItem
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .quantidade(UPDATED_QUANTIDADE)
            .observacao(UPDATED_OBSERVACAO);

        restDoacaoItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoacaoItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDoacaoItem))
            )
            .andExpect(status().isOk());

        // Validate the DoacaoItem in the database
        List<DoacaoItem> doacaoItemList = doacaoItemRepository.findAll();
        assertThat(doacaoItemList).hasSize(databaseSizeBeforeUpdate);
        DoacaoItem testDoacaoItem = doacaoItemList.get(doacaoItemList.size() - 1);
        assertThat(testDoacaoItem.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testDoacaoItem.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testDoacaoItem.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testDoacaoItem.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    void patchNonExistingDoacaoItem() throws Exception {
        int databaseSizeBeforeUpdate = doacaoItemRepository.findAll().size();
        doacaoItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoacaoItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, doacaoItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doacaoItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoacaoItem in the database
        List<DoacaoItem> doacaoItemList = doacaoItemRepository.findAll();
        assertThat(doacaoItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDoacaoItem() throws Exception {
        int databaseSizeBeforeUpdate = doacaoItemRepository.findAll().size();
        doacaoItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoacaoItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doacaoItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoacaoItem in the database
        List<DoacaoItem> doacaoItemList = doacaoItemRepository.findAll();
        assertThat(doacaoItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDoacaoItem() throws Exception {
        int databaseSizeBeforeUpdate = doacaoItemRepository.findAll().size();
        doacaoItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoacaoItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(doacaoItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DoacaoItem in the database
        List<DoacaoItem> doacaoItemList = doacaoItemRepository.findAll();
        assertThat(doacaoItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDoacaoItem() throws Exception {
        // Initialize the database
        doacaoItemRepository.saveAndFlush(doacaoItem);

        int databaseSizeBeforeDelete = doacaoItemRepository.findAll().size();

        // Delete the doacaoItem
        restDoacaoItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, doacaoItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DoacaoItem> doacaoItemList = doacaoItemRepository.findAll();
        assertThat(doacaoItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
