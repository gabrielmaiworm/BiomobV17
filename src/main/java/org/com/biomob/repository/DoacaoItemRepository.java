package org.com.biomob.repository;

import org.com.biomob.domain.DoacaoItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DoacaoItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoacaoItemRepository extends JpaRepository<DoacaoItem, Long> {}
