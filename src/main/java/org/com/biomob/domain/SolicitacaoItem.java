package org.com.biomob.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SolicitacaoItem.
 */
@Entity
@Table(name = "solicitacao_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SolicitacaoItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "observacao")
    private String observacao;

    @ManyToOne
    private Item descricao;

    @JsonIgnoreProperties(value = { "doacaoItem", "cadastroUser", "solicitacaoItem" }, allowSetters = true)
    @OneToOne(mappedBy = "solicitacaoItem")
    private Acao acao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SolicitacaoItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return this.quantidade;
    }

    public SolicitacaoItem quantidade(Integer quantidade) {
        this.setQuantidade(quantidade);
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public SolicitacaoItem observacao(String observacao) {
        this.setObservacao(observacao);
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Item getDescricao() {
        return this.descricao;
    }

    public void setDescricao(Item item) {
        this.descricao = item;
    }

    public SolicitacaoItem descricao(Item item) {
        this.setDescricao(item);
        return this;
    }

    public Acao getAcao() {
        return this.acao;
    }

    public void setAcao(Acao acao) {
        if (this.acao != null) {
            this.acao.setSolicitacaoItem(null);
        }
        if (acao != null) {
            acao.setSolicitacaoItem(this);
        }
        this.acao = acao;
    }

    public SolicitacaoItem acao(Acao acao) {
        this.setAcao(acao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SolicitacaoItem)) {
            return false;
        }
        return id != null && id.equals(((SolicitacaoItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SolicitacaoItem{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
