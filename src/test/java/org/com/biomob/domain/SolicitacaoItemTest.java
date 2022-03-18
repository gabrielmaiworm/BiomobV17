package org.com.biomob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.com.biomob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SolicitacaoItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitacaoItem.class);
        SolicitacaoItem solicitacaoItem1 = new SolicitacaoItem();
        solicitacaoItem1.setId(1L);
        SolicitacaoItem solicitacaoItem2 = new SolicitacaoItem();
        solicitacaoItem2.setId(solicitacaoItem1.getId());
        assertThat(solicitacaoItem1).isEqualTo(solicitacaoItem2);
        solicitacaoItem2.setId(2L);
        assertThat(solicitacaoItem1).isNotEqualTo(solicitacaoItem2);
        solicitacaoItem1.setId(null);
        assertThat(solicitacaoItem1).isNotEqualTo(solicitacaoItem2);
    }
}
