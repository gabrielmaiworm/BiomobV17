package org.com.biomob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.com.biomob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CadastroSolicitacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CadastroSolicitacao.class);
        CadastroSolicitacao cadastroSolicitacao1 = new CadastroSolicitacao();
        cadastroSolicitacao1.setId(1L);
        CadastroSolicitacao cadastroSolicitacao2 = new CadastroSolicitacao();
        cadastroSolicitacao2.setId(cadastroSolicitacao1.getId());
        assertThat(cadastroSolicitacao1).isEqualTo(cadastroSolicitacao2);
        cadastroSolicitacao2.setId(2L);
        assertThat(cadastroSolicitacao1).isNotEqualTo(cadastroSolicitacao2);
        cadastroSolicitacao1.setId(null);
        assertThat(cadastroSolicitacao1).isNotEqualTo(cadastroSolicitacao2);
    }
}
