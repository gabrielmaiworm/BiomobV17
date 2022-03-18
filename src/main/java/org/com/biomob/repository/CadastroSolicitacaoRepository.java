package org.com.biomob.repository;

import org.com.biomob.domain.CadastroSolicitacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CadastroSolicitacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CadastroSolicitacaoRepository extends JpaRepository<CadastroSolicitacao, Long> {}
