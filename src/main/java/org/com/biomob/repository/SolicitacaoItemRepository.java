package org.com.biomob.repository;

import org.com.biomob.domain.SolicitacaoItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SolicitacaoItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolicitacaoItemRepository extends JpaRepository<SolicitacaoItem, Long> {}
