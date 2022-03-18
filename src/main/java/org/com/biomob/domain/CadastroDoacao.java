package org.com.biomob.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CadastroDoacao.
 */
@Entity
@Table(name = "cadastro_doacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CadastroDoacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "doacao_anonima")
    private Boolean doacaoAnonima;

    @Column(name = "realiza_entrega")
    private Boolean realizaEntrega;

    @Column(name = "data_doacao")
    private LocalDate dataDoacao;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "cep")
    private String cep;

    @Column(name = "estado")
    private String estado;

    @Column(name = "pais")
    private String pais;

    @Column(name = "complemento")
    private String complemento;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "acao" }, allowSetters = true)
    private CadastroUser nome;

    @ManyToOne
    @JsonIgnoreProperties(value = { "descricao", "acao" }, allowSetters = true)
    private DoacaoItem descricao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CadastroDoacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDoacaoAnonima() {
        return this.doacaoAnonima;
    }

    public CadastroDoacao doacaoAnonima(Boolean doacaoAnonima) {
        this.setDoacaoAnonima(doacaoAnonima);
        return this;
    }

    public void setDoacaoAnonima(Boolean doacaoAnonima) {
        this.doacaoAnonima = doacaoAnonima;
    }

    public Boolean getRealizaEntrega() {
        return this.realizaEntrega;
    }

    public CadastroDoacao realizaEntrega(Boolean realizaEntrega) {
        this.setRealizaEntrega(realizaEntrega);
        return this;
    }

    public void setRealizaEntrega(Boolean realizaEntrega) {
        this.realizaEntrega = realizaEntrega;
    }

    public LocalDate getDataDoacao() {
        return this.dataDoacao;
    }

    public CadastroDoacao dataDoacao(LocalDate dataDoacao) {
        this.setDataDoacao(dataDoacao);
        return this;
    }

    public void setDataDoacao(LocalDate dataDoacao) {
        this.dataDoacao = dataDoacao;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public CadastroDoacao logradouro(String logradouro) {
        this.setLogradouro(logradouro);
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public CadastroDoacao numero(Integer numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return this.bairro;
    }

    public CadastroDoacao bairro(String bairro) {
        this.setBairro(bairro);
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return this.cidade;
    }

    public CadastroDoacao cidade(String cidade) {
        this.setCidade(cidade);
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return this.cep;
    }

    public CadastroDoacao cep(String cep) {
        this.setCep(cep);
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return this.estado;
    }

    public CadastroDoacao estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return this.pais;
    }

    public CadastroDoacao pais(String pais) {
        this.setPais(pais);
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public CadastroDoacao complemento(String complemento) {
        this.setComplemento(complemento);
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public CadastroUser getNome() {
        return this.nome;
    }

    public void setNome(CadastroUser cadastroUser) {
        this.nome = cadastroUser;
    }

    public CadastroDoacao nome(CadastroUser cadastroUser) {
        this.setNome(cadastroUser);
        return this;
    }

    public DoacaoItem getDescricao() {
        return this.descricao;
    }

    public void setDescricao(DoacaoItem doacaoItem) {
        this.descricao = doacaoItem;
    }

    public CadastroDoacao descricao(DoacaoItem doacaoItem) {
        this.setDescricao(doacaoItem);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CadastroDoacao)) {
            return false;
        }
        return id != null && id.equals(((CadastroDoacao) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CadastroDoacao{" +
            "id=" + getId() +
            ", doacaoAnonima='" + getDoacaoAnonima() + "'" +
            ", realizaEntrega='" + getRealizaEntrega() + "'" +
            ", dataDoacao='" + getDataDoacao() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", numero=" + getNumero() +
            ", bairro='" + getBairro() + "'" +
            ", cidade='" + getCidade() + "'" +
            ", cep='" + getCep() + "'" +
            ", estado='" + getEstado() + "'" +
            ", pais='" + getPais() + "'" +
            ", complemento='" + getComplemento() + "'" +
            "}";
    }
}
