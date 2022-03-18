package org.com.biomob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.com.biomob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DoacaoItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoacaoItem.class);
        DoacaoItem doacaoItem1 = new DoacaoItem();
        doacaoItem1.setId(1L);
        DoacaoItem doacaoItem2 = new DoacaoItem();
        doacaoItem2.setId(doacaoItem1.getId());
        assertThat(doacaoItem1).isEqualTo(doacaoItem2);
        doacaoItem2.setId(2L);
        assertThat(doacaoItem1).isNotEqualTo(doacaoItem2);
        doacaoItem1.setId(null);
        assertThat(doacaoItem1).isNotEqualTo(doacaoItem2);
    }
}
