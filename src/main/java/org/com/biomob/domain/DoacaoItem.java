package org.com.biomob.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DoacaoItem.
 */
@Entity
@Table(name = "doacao_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DoacaoItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @Column(name = "imagem_content_type")
    private String imagemContentType;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "observacao")
    private String observacao;

    @ManyToOne
    private Item descricao;

    @JsonIgnoreProperties(value = { "doacaoItem", "cadastroUser", "solicitacaoItem" }, allowSetters = true)
    @OneToOne(mappedBy = "doacaoItem")
    private Acao acao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DoacaoItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagem() {
        return this.imagem;
    }

    public DoacaoItem imagem(byte[] imagem) {
        this.setImagem(imagem);
        return this;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return this.imagemContentType;
    }

    public DoacaoItem imagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
        return this;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public Integer getQuantidade() {
        return this.quantidade;
    }

    public DoacaoItem quantidade(Integer quantidade) {
        this.setQuantidade(quantidade);
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public DoacaoItem observacao(String observacao) {
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

    public DoacaoItem descricao(Item item) {
        this.setDescricao(item);
        return this;
    }

    public Acao getAcao() {
        return this.acao;
    }

    public void setAcao(Acao acao) {
        if (this.acao != null) {
            this.acao.setDoacaoItem(null);
        }
        if (acao != null) {
            acao.setDoacaoItem(this);
        }
        this.acao = acao;
    }

    public DoacaoItem acao(Acao acao) {
        this.setAcao(acao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoacaoItem)) {
            return false;
        }
        return id != null && id.equals(((DoacaoItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoacaoItem{" +
            "id=" + getId() +
            ", imagem='" + getImagem() + "'" +
            ", imagemContentType='" + getImagemContentType() + "'" +
            ", quantidade=" + getQuantidade() +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
