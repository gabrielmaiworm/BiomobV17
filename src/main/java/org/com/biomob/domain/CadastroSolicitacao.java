package org.com.biomob.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.com.biomob.domain.enumeration.StatusSolicitacao;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CadastroSolicitacao.
 */
@Entity
@Table(name = "cadastro_solicitacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CadastroSolicitacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome_solicitante")
    private String nomeSolicitante;

    @Column(name = "cpf_solicitante")
    private String cpfSolicitante;

    @Column(name = "data_solicitacao")
    private LocalDate dataSolicitacao;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "ponto_referencia")
    private String pontoReferencia;

    @Column(name = "situacao_solicitante")
    private String situacaoSolicitante;

    @Column(name = "quantidade_pessoas")
    private Integer quantidadePessoas;

    @Column(name = "retira_doacao")
    private Boolean retiraDoacao;

    @Column(name = "perdeu_moradia")
    private Boolean perdeuMoradia;

    @Column(name = "perdeu_emprego")
    private Boolean perdeuEmprego;

    @Column(name = "idoso_deficiente")
    private Boolean idosoDeficiente;

    @Column(name = "filhos_pequenos")
    private Boolean filhosPequenos;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "data_aprovacao")
    private LocalDate dataAprovacao;

    @Column(name = "ativa")
    private Boolean ativa;

    @Column(name = "fator_prioridade")
    private Float fatorPrioridade;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_solicitacao")
    private StatusSolicitacao statusSolicitacao;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "acao" }, allowSetters = true)
    private CadastroUser nome;

    @ManyToOne
    @JsonIgnoreProperties(value = { "descricao", "acao" }, allowSetters = true)
    private SolicitacaoItem descricao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CadastroSolicitacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeSolicitante() {
        return this.nomeSolicitante;
    }

    public CadastroSolicitacao nomeSolicitante(String nomeSolicitante) {
        this.setNomeSolicitante(nomeSolicitante);
        return this;
    }

    public void setNomeSolicitante(String nomeSolicitante) {
        this.nomeSolicitante = nomeSolicitante;
    }

    public String getCpfSolicitante() {
        return this.cpfSolicitante;
    }

    public CadastroSolicitacao cpfSolicitante(String cpfSolicitante) {
        this.setCpfSolicitante(cpfSolicitante);
        return this;
    }

    public void setCpfSolicitante(String cpfSolicitante) {
        this.cpfSolicitante = cpfSolicitante;
    }

    public LocalDate getDataSolicitacao() {
        return this.dataSolicitacao;
    }

    public CadastroSolicitacao dataSolicitacao(LocalDate dataSolicitacao) {
        this.setDataSolicitacao(dataSolicitacao);
        return this;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public CadastroSolicitacao endereco(String endereco) {
        this.setEndereco(endereco);
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getPontoReferencia() {
        return this.pontoReferencia;
    }

    public CadastroSolicitacao pontoReferencia(String pontoReferencia) {
        this.setPontoReferencia(pontoReferencia);
        return this;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public String getSituacaoSolicitante() {
        return this.situacaoSolicitante;
    }

    public CadastroSolicitacao situacaoSolicitante(String situacaoSolicitante) {
        this.setSituacaoSolicitante(situacaoSolicitante);
        return this;
    }

    public void setSituacaoSolicitante(String situacaoSolicitante) {
        this.situacaoSolicitante = situacaoSolicitante;
    }

    public Integer getQuantidadePessoas() {
        return this.quantidadePessoas;
    }

    public CadastroSolicitacao quantidadePessoas(Integer quantidadePessoas) {
        this.setQuantidadePessoas(quantidadePessoas);
        return this;
    }

    public void setQuantidadePessoas(Integer quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }

    public Boolean getRetiraDoacao() {
        return this.retiraDoacao;
    }

    public CadastroSolicitacao retiraDoacao(Boolean retiraDoacao) {
        this.setRetiraDoacao(retiraDoacao);
        return this;
    }

    public void setRetiraDoacao(Boolean retiraDoacao) {
        this.retiraDoacao = retiraDoacao;
    }

    public Boolean getPerdeuMoradia() {
        return this.perdeuMoradia;
    }

    public CadastroSolicitacao perdeuMoradia(Boolean perdeuMoradia) {
        this.setPerdeuMoradia(perdeuMoradia);
        return this;
    }

    public void setPerdeuMoradia(Boolean perdeuMoradia) {
        this.perdeuMoradia = perdeuMoradia;
    }

    public Boolean getPerdeuEmprego() {
        return this.perdeuEmprego;
    }

    public CadastroSolicitacao perdeuEmprego(Boolean perdeuEmprego) {
        this.setPerdeuEmprego(perdeuEmprego);
        return this;
    }

    public void setPerdeuEmprego(Boolean perdeuEmprego) {
        this.perdeuEmprego = perdeuEmprego;
    }

    public Boolean getIdosoDeficiente() {
        return this.idosoDeficiente;
    }

    public CadastroSolicitacao idosoDeficiente(Boolean idosoDeficiente) {
        this.setIdosoDeficiente(idosoDeficiente);
        return this;
    }

    public void setIdosoDeficiente(Boolean idosoDeficiente) {
        this.idosoDeficiente = idosoDeficiente;
    }

    public Boolean getFilhosPequenos() {
        return this.filhosPequenos;
    }

    public CadastroSolicitacao filhosPequenos(Boolean filhosPequenos) {
        this.setFilhosPequenos(filhosPequenos);
        return this;
    }

    public void setFilhosPequenos(Boolean filhosPequenos) {
        this.filhosPequenos = filhosPequenos;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public CadastroSolicitacao observacao(String observacao) {
        this.setObservacao(observacao);
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public LocalDate getDataAprovacao() {
        return this.dataAprovacao;
    }

    public CadastroSolicitacao dataAprovacao(LocalDate dataAprovacao) {
        this.setDataAprovacao(dataAprovacao);
        return this;
    }

    public void setDataAprovacao(LocalDate dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    public Boolean getAtiva() {
        return this.ativa;
    }

    public CadastroSolicitacao ativa(Boolean ativa) {
        this.setAtiva(ativa);
        return this;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public Float getFatorPrioridade() {
        return this.fatorPrioridade;
    }

    public CadastroSolicitacao fatorPrioridade(Float fatorPrioridade) {
        this.setFatorPrioridade(fatorPrioridade);
        return this;
    }

    public void setFatorPrioridade(Float fatorPrioridade) {
        this.fatorPrioridade = fatorPrioridade;
    }

    public StatusSolicitacao getStatusSolicitacao() {
        return this.statusSolicitacao;
    }

    public CadastroSolicitacao statusSolicitacao(StatusSolicitacao statusSolicitacao) {
        this.setStatusSolicitacao(statusSolicitacao);
        return this;
    }

    public void setStatusSolicitacao(StatusSolicitacao statusSolicitacao) {
        this.statusSolicitacao = statusSolicitacao;
    }

    public CadastroUser getNome() {
        return this.nome;
    }

    public void setNome(CadastroUser cadastroUser) {
        this.nome = cadastroUser;
    }

    public CadastroSolicitacao nome(CadastroUser cadastroUser) {
        this.setNome(cadastroUser);
        return this;
    }

    public SolicitacaoItem getDescricao() {
        return this.descricao;
    }

    public void setDescricao(SolicitacaoItem solicitacaoItem) {
        this.descricao = solicitacaoItem;
    }

    public CadastroSolicitacao descricao(SolicitacaoItem solicitacaoItem) {
        this.setDescricao(solicitacaoItem);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CadastroSolicitacao)) {
            return false;
        }
        return id != null && id.equals(((CadastroSolicitacao) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CadastroSolicitacao{" +
            "id=" + getId() +
            ", nomeSolicitante='" + getNomeSolicitante() + "'" +
            ", cpfSolicitante='" + getCpfSolicitante() + "'" +
            ", dataSolicitacao='" + getDataSolicitacao() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", pontoReferencia='" + getPontoReferencia() + "'" +
            ", situacaoSolicitante='" + getSituacaoSolicitante() + "'" +
            ", quantidadePessoas=" + getQuantidadePessoas() +
            ", retiraDoacao='" + getRetiraDoacao() + "'" +
            ", perdeuMoradia='" + getPerdeuMoradia() + "'" +
            ", perdeuEmprego='" + getPerdeuEmprego() + "'" +
            ", idosoDeficiente='" + getIdosoDeficiente() + "'" +
            ", filhosPequenos='" + getFilhosPequenos() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", dataAprovacao='" + getDataAprovacao() + "'" +
            ", ativa='" + getAtiva() + "'" +
            ", fatorPrioridade=" + getFatorPrioridade() +
            ", statusSolicitacao='" + getStatusSolicitacao() + "'" +
            "}";
    }
}
