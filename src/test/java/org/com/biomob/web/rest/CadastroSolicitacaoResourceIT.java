package org.com.biomob.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.com.biomob.IntegrationTest;
import org.com.biomob.domain.CadastroSolicitacao;
import org.com.biomob.domain.enumeration.StatusSolicitacao;
import org.com.biomob.repository.CadastroSolicitacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CadastroSolicitacaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CadastroSolicitacaoResourceIT {

    private static final String DEFAULT_NOME_SOLICITANTE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_SOLICITANTE = "BBBBBBBBBB";

    private static final String DEFAULT_CPF_SOLICITANTE = "AAAAAAAAAA";
    private static final String UPDATED_CPF_SOLICITANTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_SOLICITACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_SOLICITACAO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_PONTO_REFERENCIA = "AAAAAAAAAA";
    private static final String UPDATED_PONTO_REFERENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_SITUACAO_SOLICITANTE = "AAAAAAAAAA";
    private static final String UPDATED_SITUACAO_SOLICITANTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTIDADE_PESSOAS = 1;
    private static final Integer UPDATED_QUANTIDADE_PESSOAS = 2;

    private static final Boolean DEFAULT_RETIRA_DOACAO = false;
    private static final Boolean UPDATED_RETIRA_DOACAO = true;

    private static final Boolean DEFAULT_PERDEU_MORADIA = false;
    private static final Boolean UPDATED_PERDEU_MORADIA = true;

    private static final Boolean DEFAULT_PERDEU_EMPREGO = false;
    private static final Boolean UPDATED_PERDEU_EMPREGO = true;

    private static final Boolean DEFAULT_IDOSO_DEFICIENTE = false;
    private static final Boolean UPDATED_IDOSO_DEFICIENTE = true;

    private static final Boolean DEFAULT_FILHOS_PEQUENOS = false;
    private static final Boolean UPDATED_FILHOS_PEQUENOS = true;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_APROVACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_APROVACAO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ATIVA = false;
    private static final Boolean UPDATED_ATIVA = true;

    private static final Float DEFAULT_FATOR_PRIORIDADE = 1F;
    private static final Float UPDATED_FATOR_PRIORIDADE = 2F;

    private static final StatusSolicitacao DEFAULT_STATUS_SOLICITACAO = StatusSolicitacao.ATENDIDO;
    private static final StatusSolicitacao UPDATED_STATUS_SOLICITACAO = StatusSolicitacao.ENCAMINHADO;

    private static final String ENTITY_API_URL = "/api/cadastro-solicitacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CadastroSolicitacaoRepository cadastroSolicitacaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCadastroSolicitacaoMockMvc;

    private CadastroSolicitacao cadastroSolicitacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CadastroSolicitacao createEntity(EntityManager em) {
        CadastroSolicitacao cadastroSolicitacao = new CadastroSolicitacao()
            .nomeSolicitante(DEFAULT_NOME_SOLICITANTE)
            .cpfSolicitante(DEFAULT_CPF_SOLICITANTE)
            .dataSolicitacao(DEFAULT_DATA_SOLICITACAO)
            .endereco(DEFAULT_ENDERECO)
            .pontoReferencia(DEFAULT_PONTO_REFERENCIA)
            .situacaoSolicitante(DEFAULT_SITUACAO_SOLICITANTE)
            .quantidadePessoas(DEFAULT_QUANTIDADE_PESSOAS)
            .retiraDoacao(DEFAULT_RETIRA_DOACAO)
            .perdeuMoradia(DEFAULT_PERDEU_MORADIA)
            .perdeuEmprego(DEFAULT_PERDEU_EMPREGO)
            .idosoDeficiente(DEFAULT_IDOSO_DEFICIENTE)
            .filhosPequenos(DEFAULT_FILHOS_PEQUENOS)
            .observacao(DEFAULT_OBSERVACAO)
            .dataAprovacao(DEFAULT_DATA_APROVACAO)
            .ativa(DEFAULT_ATIVA)
            .fatorPrioridade(DEFAULT_FATOR_PRIORIDADE)
            .statusSolicitacao(DEFAULT_STATUS_SOLICITACAO);
        return cadastroSolicitacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CadastroSolicitacao createUpdatedEntity(EntityManager em) {
        CadastroSolicitacao cadastroSolicitacao = new CadastroSolicitacao()
            .nomeSolicitante(UPDATED_NOME_SOLICITANTE)
            .cpfSolicitante(UPDATED_CPF_SOLICITANTE)
            .dataSolicitacao(UPDATED_DATA_SOLICITACAO)
            .endereco(UPDATED_ENDERECO)
            .pontoReferencia(UPDATED_PONTO_REFERENCIA)
            .situacaoSolicitante(UPDATED_SITUACAO_SOLICITANTE)
            .quantidadePessoas(UPDATED_QUANTIDADE_PESSOAS)
            .retiraDoacao(UPDATED_RETIRA_DOACAO)
            .perdeuMoradia(UPDATED_PERDEU_MORADIA)
            .perdeuEmprego(UPDATED_PERDEU_EMPREGO)
            .idosoDeficiente(UPDATED_IDOSO_DEFICIENTE)
            .filhosPequenos(UPDATED_FILHOS_PEQUENOS)
            .observacao(UPDATED_OBSERVACAO)
            .dataAprovacao(UPDATED_DATA_APROVACAO)
            .ativa(UPDATED_ATIVA)
            .fatorPrioridade(UPDATED_FATOR_PRIORIDADE)
            .statusSolicitacao(UPDATED_STATUS_SOLICITACAO);
        return cadastroSolicitacao;
    }

    @BeforeEach
    public void initTest() {
        cadastroSolicitacao = createEntity(em);
    }

    @Test
    @Transactional
    void createCadastroSolicitacao() throws Exception {
        int databaseSizeBeforeCreate = cadastroSolicitacaoRepository.findAll().size();
        // Create the CadastroSolicitacao
        restCadastroSolicitacaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cadastroSolicitacao))
            )
            .andExpect(status().isCreated());

        // Validate the CadastroSolicitacao in the database
        List<CadastroSolicitacao> cadastroSolicitacaoList = cadastroSolicitacaoRepository.findAll();
        assertThat(cadastroSolicitacaoList).hasSize(databaseSizeBeforeCreate + 1);
        CadastroSolicitacao testCadastroSolicitacao = cadastroSolicitacaoList.get(cadastroSolicitacaoList.size() - 1);
        assertThat(testCadastroSolicitacao.getNomeSolicitante()).isEqualTo(DEFAULT_NOME_SOLICITANTE);
        assertThat(testCadastroSolicitacao.getCpfSolicitante()).isEqualTo(DEFAULT_CPF_SOLICITANTE);
        assertThat(testCadastroSolicitacao.getDataSolicitacao()).isEqualTo(DEFAULT_DATA_SOLICITACAO);
        assertThat(testCadastroSolicitacao.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testCadastroSolicitacao.getPontoReferencia()).isEqualTo(DEFAULT_PONTO_REFERENCIA);
        assertThat(testCadastroSolicitacao.getSituacaoSolicitante()).isEqualTo(DEFAULT_SITUACAO_SOLICITANTE);
        assertThat(testCadastroSolicitacao.getQuantidadePessoas()).isEqualTo(DEFAULT_QUANTIDADE_PESSOAS);
        assertThat(testCadastroSolicitacao.getRetiraDoacao()).isEqualTo(DEFAULT_RETIRA_DOACAO);
        assertThat(testCadastroSolicitacao.getPerdeuMoradia()).isEqualTo(DEFAULT_PERDEU_MORADIA);
        assertThat(testCadastroSolicitacao.getPerdeuEmprego()).isEqualTo(DEFAULT_PERDEU_EMPREGO);
        assertThat(testCadastroSolicitacao.getIdosoDeficiente()).isEqualTo(DEFAULT_IDOSO_DEFICIENTE);
        assertThat(testCadastroSolicitacao.getFilhosPequenos()).isEqualTo(DEFAULT_FILHOS_PEQUENOS);
        assertThat(testCadastroSolicitacao.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testCadastroSolicitacao.getDataAprovacao()).isEqualTo(DEFAULT_DATA_APROVACAO);
        assertThat(testCadastroSolicitacao.getAtiva()).isEqualTo(DEFAULT_ATIVA);
        assertThat(testCadastroSolicitacao.getFatorPrioridade()).isEqualTo(DEFAULT_FATOR_PRIORIDADE);
        assertThat(testCadastroSolicitacao.getStatusSolicitacao()).isEqualTo(DEFAULT_STATUS_SOLICITACAO);
    }

    @Test
    @Transactional
    void createCadastroSolicitacaoWithExistingId() throws Exception {
        // Create the CadastroSolicitacao with an existing ID
        cadastroSolicitacao.setId(1L);

        int databaseSizeBeforeCreate = cadastroSolicitacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCadastroSolicitacaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cadastroSolicitacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the CadastroSolicitacao in the database
        List<CadastroSolicitacao> cadastroSolicitacaoList = cadastroSolicitacaoRepository.findAll();
        assertThat(cadastroSolicitacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCadastroSolicitacaos() throws Exception {
        // Initialize the database
        cadastroSolicitacaoRepository.saveAndFlush(cadastroSolicitacao);

        // Get all the cadastroSolicitacaoList
        restCadastroSolicitacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cadastroSolicitacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeSolicitante").value(hasItem(DEFAULT_NOME_SOLICITANTE)))
            .andExpect(jsonPath("$.[*].cpfSolicitante").value(hasItem(DEFAULT_CPF_SOLICITANTE)))
            .andExpect(jsonPath("$.[*].dataSolicitacao").value(hasItem(DEFAULT_DATA_SOLICITACAO.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
            .andExpect(jsonPath("$.[*].pontoReferencia").value(hasItem(DEFAULT_PONTO_REFERENCIA)))
            .andExpect(jsonPath("$.[*].situacaoSolicitante").value(hasItem(DEFAULT_SITUACAO_SOLICITANTE)))
            .andExpect(jsonPath("$.[*].quantidadePessoas").value(hasItem(DEFAULT_QUANTIDADE_PESSOAS)))
            .andExpect(jsonPath("$.[*].retiraDoacao").value(hasItem(DEFAULT_RETIRA_DOACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].perdeuMoradia").value(hasItem(DEFAULT_PERDEU_MORADIA.booleanValue())))
            .andExpect(jsonPath("$.[*].perdeuEmprego").value(hasItem(DEFAULT_PERDEU_EMPREGO.booleanValue())))
            .andExpect(jsonPath("$.[*].idosoDeficiente").value(hasItem(DEFAULT_IDOSO_DEFICIENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].filhosPequenos").value(hasItem(DEFAULT_FILHOS_PEQUENOS.booleanValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].dataAprovacao").value(hasItem(DEFAULT_DATA_APROVACAO.toString())))
            .andExpect(jsonPath("$.[*].ativa").value(hasItem(DEFAULT_ATIVA.booleanValue())))
            .andExpect(jsonPath("$.[*].fatorPrioridade").value(hasItem(DEFAULT_FATOR_PRIORIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].statusSolicitacao").value(hasItem(DEFAULT_STATUS_SOLICITACAO.toString())));
    }

    @Test
    @Transactional
    void getCadastroSolicitacao() throws Exception {
        // Initialize the database
        cadastroSolicitacaoRepository.saveAndFlush(cadastroSolicitacao);

        // Get the cadastroSolicitacao
        restCadastroSolicitacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, cadastroSolicitacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cadastroSolicitacao.getId().intValue()))
            .andExpect(jsonPath("$.nomeSolicitante").value(DEFAULT_NOME_SOLICITANTE))
            .andExpect(jsonPath("$.cpfSolicitante").value(DEFAULT_CPF_SOLICITANTE))
            .andExpect(jsonPath("$.dataSolicitacao").value(DEFAULT_DATA_SOLICITACAO.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO))
            .andExpect(jsonPath("$.pontoReferencia").value(DEFAULT_PONTO_REFERENCIA))
            .andExpect(jsonPath("$.situacaoSolicitante").value(DEFAULT_SITUACAO_SOLICITANTE))
            .andExpect(jsonPath("$.quantidadePessoas").value(DEFAULT_QUANTIDADE_PESSOAS))
            .andExpect(jsonPath("$.retiraDoacao").value(DEFAULT_RETIRA_DOACAO.booleanValue()))
            .andExpect(jsonPath("$.perdeuMoradia").value(DEFAULT_PERDEU_MORADIA.booleanValue()))
            .andExpect(jsonPath("$.perdeuEmprego").value(DEFAULT_PERDEU_EMPREGO.booleanValue()))
            .andExpect(jsonPath("$.idosoDeficiente").value(DEFAULT_IDOSO_DEFICIENTE.booleanValue()))
            .andExpect(jsonPath("$.filhosPequenos").value(DEFAULT_FILHOS_PEQUENOS.booleanValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO))
            .andExpect(jsonPath("$.dataAprovacao").value(DEFAULT_DATA_APROVACAO.toString()))
            .andExpect(jsonPath("$.ativa").value(DEFAULT_ATIVA.booleanValue()))
            .andExpect(jsonPath("$.fatorPrioridade").value(DEFAULT_FATOR_PRIORIDADE.doubleValue()))
            .andExpect(jsonPath("$.statusSolicitacao").value(DEFAULT_STATUS_SOLICITACAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCadastroSolicitacao() throws Exception {
        // Get the cadastroSolicitacao
        restCadastroSolicitacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCadastroSolicitacao() throws Exception {
        // Initialize the database
        cadastroSolicitacaoRepository.saveAndFlush(cadastroSolicitacao);

        int databaseSizeBeforeUpdate = cadastroSolicitacaoRepository.findAll().size();

        // Update the cadastroSolicitacao
        CadastroSolicitacao updatedCadastroSolicitacao = cadastroSolicitacaoRepository.findById(cadastroSolicitacao.getId()).get();
        // Disconnect from session so that the updates on updatedCadastroSolicitacao are not directly saved in db
        em.detach(updatedCadastroSolicitacao);
        updatedCadastroSolicitacao
            .nomeSolicitante(UPDATED_NOME_SOLICITANTE)
            .cpfSolicitante(UPDATED_CPF_SOLICITANTE)
            .dataSolicitacao(UPDATED_DATA_SOLICITACAO)
            .endereco(UPDATED_ENDERECO)
            .pontoReferencia(UPDATED_PONTO_REFERENCIA)
            .situacaoSolicitante(UPDATED_SITUACAO_SOLICITANTE)
            .quantidadePessoas(UPDATED_QUANTIDADE_PESSOAS)
            .retiraDoacao(UPDATED_RETIRA_DOACAO)
            .perdeuMoradia(UPDATED_PERDEU_MORADIA)
            .perdeuEmprego(UPDATED_PERDEU_EMPREGO)
            .idosoDeficiente(UPDATED_IDOSO_DEFICIENTE)
            .filhosPequenos(UPDATED_FILHOS_PEQUENOS)
            .observacao(UPDATED_OBSERVACAO)
            .dataAprovacao(UPDATED_DATA_APROVACAO)
            .ativa(UPDATED_ATIVA)
            .fatorPrioridade(UPDATED_FATOR_PRIORIDADE)
            .statusSolicitacao(UPDATED_STATUS_SOLICITACAO);

        restCadastroSolicitacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCadastroSolicitacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCadastroSolicitacao))
            )
            .andExpect(status().isOk());

        // Validate the CadastroSolicitacao in the database
        List<CadastroSolicitacao> cadastroSolicitacaoList = cadastroSolicitacaoRepository.findAll();
        assertThat(cadastroSolicitacaoList).hasSize(databaseSizeBeforeUpdate);
        CadastroSolicitacao testCadastroSolicitacao = cadastroSolicitacaoList.get(cadastroSolicitacaoList.size() - 1);
        assertThat(testCadastroSolicitacao.getNomeSolicitante()).isEqualTo(UPDATED_NOME_SOLICITANTE);
        assertThat(testCadastroSolicitacao.getCpfSolicitante()).isEqualTo(UPDATED_CPF_SOLICITANTE);
        assertThat(testCadastroSolicitacao.getDataSolicitacao()).isEqualTo(UPDATED_DATA_SOLICITACAO);
        assertThat(testCadastroSolicitacao.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testCadastroSolicitacao.getPontoReferencia()).isEqualTo(UPDATED_PONTO_REFERENCIA);
        assertThat(testCadastroSolicitacao.getSituacaoSolicitante()).isEqualTo(UPDATED_SITUACAO_SOLICITANTE);
        assertThat(testCadastroSolicitacao.getQuantidadePessoas()).isEqualTo(UPDATED_QUANTIDADE_PESSOAS);
        assertThat(testCadastroSolicitacao.getRetiraDoacao()).isEqualTo(UPDATED_RETIRA_DOACAO);
        assertThat(testCadastroSolicitacao.getPerdeuMoradia()).isEqualTo(UPDATED_PERDEU_MORADIA);
        assertThat(testCadastroSolicitacao.getPerdeuEmprego()).isEqualTo(UPDATED_PERDEU_EMPREGO);
        assertThat(testCadastroSolicitacao.getIdosoDeficiente()).isEqualTo(UPDATED_IDOSO_DEFICIENTE);
        assertThat(testCadastroSolicitacao.getFilhosPequenos()).isEqualTo(UPDATED_FILHOS_PEQUENOS);
        assertThat(testCadastroSolicitacao.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testCadastroSolicitacao.getDataAprovacao()).isEqualTo(UPDATED_DATA_APROVACAO);
        assertThat(testCadastroSolicitacao.getAtiva()).isEqualTo(UPDATED_ATIVA);
        assertThat(testCadastroSolicitacao.getFatorPrioridade()).isEqualTo(UPDATED_FATOR_PRIORIDADE);
        assertThat(testCadastroSolicitacao.getStatusSolicitacao()).isEqualTo(UPDATED_STATUS_SOLICITACAO);
    }

    @Test
    @Transactional
    void putNonExistingCadastroSolicitacao() throws Exception {
        int databaseSizeBeforeUpdate = cadastroSolicitacaoRepository.findAll().size();
        cadastroSolicitacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCadastroSolicitacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cadastroSolicitacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cadastroSolicitacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the CadastroSolicitacao in the database
        List<CadastroSolicitacao> cadastroSolicitacaoList = cadastroSolicitacaoRepository.findAll();
        assertThat(cadastroSolicitacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCadastroSolicitacao() throws Exception {
        int databaseSizeBeforeUpdate = cadastroSolicitacaoRepository.findAll().size();
        cadastroSolicitacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCadastroSolicitacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cadastroSolicitacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the CadastroSolicitacao in the database
        List<CadastroSolicitacao> cadastroSolicitacaoList = cadastroSolicitacaoRepository.findAll();
        assertThat(cadastroSolicitacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCadastroSolicitacao() throws Exception {
        int databaseSizeBeforeUpdate = cadastroSolicitacaoRepository.findAll().size();
        cadastroSolicitacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCadastroSolicitacaoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cadastroSolicitacao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CadastroSolicitacao in the database
        List<CadastroSolicitacao> cadastroSolicitacaoList = cadastroSolicitacaoRepository.findAll();
        assertThat(cadastroSolicitacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCadastroSolicitacaoWithPatch() throws Exception {
        // Initialize the database
        cadastroSolicitacaoRepository.saveAndFlush(cadastroSolicitacao);

        int databaseSizeBeforeUpdate = cadastroSolicitacaoRepository.findAll().size();

        // Update the cadastroSolicitacao using partial update
        CadastroSolicitacao partialUpdatedCadastroSolicitacao = new CadastroSolicitacao();
        partialUpdatedCadastroSolicitacao.setId(cadastroSolicitacao.getId());

        partialUpdatedCadastroSolicitacao
            .dataSolicitacao(UPDATED_DATA_SOLICITACAO)
            .endereco(UPDATED_ENDERECO)
            .pontoReferencia(UPDATED_PONTO_REFERENCIA)
            .quantidadePessoas(UPDATED_QUANTIDADE_PESSOAS)
            .perdeuEmprego(UPDATED_PERDEU_EMPREGO)
            .filhosPequenos(UPDATED_FILHOS_PEQUENOS)
            .observacao(UPDATED_OBSERVACAO)
            .dataAprovacao(UPDATED_DATA_APROVACAO)
            .ativa(UPDATED_ATIVA)
            .statusSolicitacao(UPDATED_STATUS_SOLICITACAO);

        restCadastroSolicitacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCadastroSolicitacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCadastroSolicitacao))
            )
            .andExpect(status().isOk());

        // Validate the CadastroSolicitacao in the database
        List<CadastroSolicitacao> cadastroSolicitacaoList = cadastroSolicitacaoRepository.findAll();
        assertThat(cadastroSolicitacaoList).hasSize(databaseSizeBeforeUpdate);
        CadastroSolicitacao testCadastroSolicitacao = cadastroSolicitacaoList.get(cadastroSolicitacaoList.size() - 1);
        assertThat(testCadastroSolicitacao.getNomeSolicitante()).isEqualTo(DEFAULT_NOME_SOLICITANTE);
        assertThat(testCadastroSolicitacao.getCpfSolicitante()).isEqualTo(DEFAULT_CPF_SOLICITANTE);
        assertThat(testCadastroSolicitacao.getDataSolicitacao()).isEqualTo(UPDATED_DATA_SOLICITACAO);
        assertThat(testCadastroSolicitacao.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testCadastroSolicitacao.getPontoReferencia()).isEqualTo(UPDATED_PONTO_REFERENCIA);
        assertThat(testCadastroSolicitacao.getSituacaoSolicitante()).isEqualTo(DEFAULT_SITUACAO_SOLICITANTE);
        assertThat(testCadastroSolicitacao.getQuantidadePessoas()).isEqualTo(UPDATED_QUANTIDADE_PESSOAS);
        assertThat(testCadastroSolicitacao.getRetiraDoacao()).isEqualTo(DEFAULT_RETIRA_DOACAO);
        assertThat(testCadastroSolicitacao.getPerdeuMoradia()).isEqualTo(DEFAULT_PERDEU_MORADIA);
        assertThat(testCadastroSolicitacao.getPerdeuEmprego()).isEqualTo(UPDATED_PERDEU_EMPREGO);
        assertThat(testCadastroSolicitacao.getIdosoDeficiente()).isEqualTo(DEFAULT_IDOSO_DEFICIENTE);
        assertThat(testCadastroSolicitacao.getFilhosPequenos()).isEqualTo(UPDATED_FILHOS_PEQUENOS);
        assertThat(testCadastroSolicitacao.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testCadastroSolicitacao.getDataAprovacao()).isEqualTo(UPDATED_DATA_APROVACAO);
        assertThat(testCadastroSolicitacao.getAtiva()).isEqualTo(UPDATED_ATIVA);
        assertThat(testCadastroSolicitacao.getFatorPrioridade()).isEqualTo(DEFAULT_FATOR_PRIORIDADE);
        assertThat(testCadastroSolicitacao.getStatusSolicitacao()).isEqualTo(UPDATED_STATUS_SOLICITACAO);
    }

    @Test
    @Transactional
    void fullUpdateCadastroSolicitacaoWithPatch() throws Exception {
        // Initialize the database
        cadastroSolicitacaoRepository.saveAndFlush(cadastroSolicitacao);

        int databaseSizeBeforeUpdate = cadastroSolicitacaoRepository.findAll().size();

        // Update the cadastroSolicitacao using partial update
        CadastroSolicitacao partialUpdatedCadastroSolicitacao = new CadastroSolicitacao();
        partialUpdatedCadastroSolicitacao.setId(cadastroSolicitacao.getId());

        partialUpdatedCadastroSolicitacao
            .nomeSolicitante(UPDATED_NOME_SOLICITANTE)
            .cpfSolicitante(UPDATED_CPF_SOLICITANTE)
            .dataSolicitacao(UPDATED_DATA_SOLICITACAO)
            .endereco(UPDATED_ENDERECO)
            .pontoReferencia(UPDATED_PONTO_REFERENCIA)
            .situacaoSolicitante(UPDATED_SITUACAO_SOLICITANTE)
            .quantidadePessoas(UPDATED_QUANTIDADE_PESSOAS)
            .retiraDoacao(UPDATED_RETIRA_DOACAO)
            .perdeuMoradia(UPDATED_PERDEU_MORADIA)
            .perdeuEmprego(UPDATED_PERDEU_EMPREGO)
            .idosoDeficiente(UPDATED_IDOSO_DEFICIENTE)
            .filhosPequenos(UPDATED_FILHOS_PEQUENOS)
            .observacao(UPDATED_OBSERVACAO)
            .dataAprovacao(UPDATED_DATA_APROVACAO)
            .ativa(UPDATED_ATIVA)
            .fatorPrioridade(UPDATED_FATOR_PRIORIDADE)
            .statusSolicitacao(UPDATED_STATUS_SOLICITACAO);

        restCadastroSolicitacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCadastroSolicitacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCadastroSolicitacao))
            )
            .andExpect(status().isOk());

        // Validate the CadastroSolicitacao in the database
        List<CadastroSolicitacao> cadastroSolicitacaoList = cadastroSolicitacaoRepository.findAll();
        assertThat(cadastroSolicitacaoList).hasSize(databaseSizeBeforeUpdate);
        CadastroSolicitacao testCadastroSolicitacao = cadastroSolicitacaoList.get(cadastroSolicitacaoList.size() - 1);
        assertThat(testCadastroSolicitacao.getNomeSolicitante()).isEqualTo(UPDATED_NOME_SOLICITANTE);
        assertThat(testCadastroSolicitacao.getCpfSolicitante()).isEqualTo(UPDATED_CPF_SOLICITANTE);
        assertThat(testCadastroSolicitacao.getDataSolicitacao()).isEqualTo(UPDATED_DATA_SOLICITACAO);
        assertThat(testCadastroSolicitacao.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testCadastroSolicitacao.getPontoReferencia()).isEqualTo(UPDATED_PONTO_REFERENCIA);
        assertThat(testCadastroSolicitacao.getSituacaoSolicitante()).isEqualTo(UPDATED_SITUACAO_SOLICITANTE);
        assertThat(testCadastroSolicitacao.getQuantidadePessoas()).isEqualTo(UPDATED_QUANTIDADE_PESSOAS);
        assertThat(testCadastroSolicitacao.getRetiraDoacao()).isEqualTo(UPDATED_RETIRA_DOACAO);
        assertThat(testCadastroSolicitacao.getPerdeuMoradia()).isEqualTo(UPDATED_PERDEU_MORADIA);
        assertThat(testCadastroSolicitacao.getPerdeuEmprego()).isEqualTo(UPDATED_PERDEU_EMPREGO);
        assertThat(testCadastroSolicitacao.getIdosoDeficiente()).isEqualTo(UPDATED_IDOSO_DEFICIENTE);
        assertThat(testCadastroSolicitacao.getFilhosPequenos()).isEqualTo(UPDATED_FILHOS_PEQUENOS);
        assertThat(testCadastroSolicitacao.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testCadastroSolicitacao.getDataAprovacao()).isEqualTo(UPDATED_DATA_APROVACAO);
        assertThat(testCadastroSolicitacao.getAtiva()).isEqualTo(UPDATED_ATIVA);
        assertThat(testCadastroSolicitacao.getFatorPrioridade()).isEqualTo(UPDATED_FATOR_PRIORIDADE);
        assertThat(testCadastroSolicitacao.getStatusSolicitacao()).isEqualTo(UPDATED_STATUS_SOLICITACAO);
    }

    @Test
    @Transactional
    void patchNonExistingCadastroSolicitacao() throws Exception {
        int databaseSizeBeforeUpdate = cadastroSolicitacaoRepository.findAll().size();
        cadastroSolicitacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCadastroSolicitacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cadastroSolicitacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cadastroSolicitacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the CadastroSolicitacao in the database
        List<CadastroSolicitacao> cadastroSolicitacaoList = cadastroSolicitacaoRepository.findAll();
        assertThat(cadastroSolicitacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCadastroSolicitacao() throws Exception {
        int databaseSizeBeforeUpdate = cadastroSolicitacaoRepository.findAll().size();
        cadastroSolicitacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCadastroSolicitacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cadastroSolicitacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the CadastroSolicitacao in the database
        List<CadastroSolicitacao> cadastroSolicitacaoList = cadastroSolicitacaoRepository.findAll();
        assertThat(cadastroSolicitacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCadastroSolicitacao() throws Exception {
        int databaseSizeBeforeUpdate = cadastroSolicitacaoRepository.findAll().size();
        cadastroSolicitacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCadastroSolicitacaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cadastroSolicitacao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CadastroSolicitacao in the database
        List<CadastroSolicitacao> cadastroSolicitacaoList = cadastroSolicitacaoRepository.findAll();
        assertThat(cadastroSolicitacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCadastroSolicitacao() throws Exception {
        // Initialize the database
        cadastroSolicitacaoRepository.saveAndFlush(cadastroSolicitacao);

        int databaseSizeBeforeDelete = cadastroSolicitacaoRepository.findAll().size();

        // Delete the cadastroSolicitacao
        restCadastroSolicitacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, cadastroSolicitacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CadastroSolicitacao> cadastroSolicitacaoList = cadastroSolicitacaoRepository.findAll();
        assertThat(cadastroSolicitacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
