import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './cadastro-solicitacao.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CadastroSolicitacaoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const cadastroSolicitacaoEntity = useAppSelector(state => state.cadastroSolicitacao.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cadastroSolicitacaoDetailsHeading">CadastroSolicitacao</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{cadastroSolicitacaoEntity.id}</dd>
          <dt>
            <span id="nomeSolicitante">Nome Solicitante</span>
          </dt>
          <dd>{cadastroSolicitacaoEntity.nomeSolicitante}</dd>
          <dt>
            <span id="cpfSolicitante">Cpf Solicitante</span>
          </dt>
          <dd>{cadastroSolicitacaoEntity.cpfSolicitante}</dd>
          <dt>
            <span id="dataSolicitacao">Data Solicitacao</span>
          </dt>
          <dd>
            {cadastroSolicitacaoEntity.dataSolicitacao ? (
              <TextFormat value={cadastroSolicitacaoEntity.dataSolicitacao} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endereco">Endereco</span>
          </dt>
          <dd>{cadastroSolicitacaoEntity.endereco}</dd>
          <dt>
            <span id="pontoReferencia">Ponto Referencia</span>
          </dt>
          <dd>{cadastroSolicitacaoEntity.pontoReferencia}</dd>
          <dt>
            <span id="situacaoSolicitante">Situacao Solicitante</span>
          </dt>
          <dd>{cadastroSolicitacaoEntity.situacaoSolicitante}</dd>
          <dt>
            <span id="quantidadePessoas">Quantidade Pessoas</span>
          </dt>
          <dd>{cadastroSolicitacaoEntity.quantidadePessoas}</dd>
          <dt>
            <span id="retiraDoacao">Retira Doacao</span>
          </dt>
          <dd>{cadastroSolicitacaoEntity.retiraDoacao ? 'true' : 'false'}</dd>
          <dt>
            <span id="perdeuMoradia">Perdeu Moradia</span>
          </dt>
          <dd>{cadastroSolicitacaoEntity.perdeuMoradia ? 'true' : 'false'}</dd>
          <dt>
            <span id="perdeuEmprego">Perdeu Emprego</span>
          </dt>
          <dd>{cadastroSolicitacaoEntity.perdeuEmprego ? 'true' : 'false'}</dd>
          <dt>
            <span id="idosoDeficiente">Idoso Deficiente</span>
          </dt>
          <dd>{cadastroSolicitacaoEntity.idosoDeficiente ? 'true' : 'false'}</dd>
          <dt>
            <span id="filhosPequenos">Filhos Pequenos</span>
          </dt>
          <dd>{cadastroSolicitacaoEntity.filhosPequenos ? 'true' : 'false'}</dd>
          <dt>
            <span id="observacao">Observacao</span>
          </dt>
          <dd>{cadastroSolicitacaoEntity.observacao}</dd>
          <dt>
            <span id="dataAprovacao">Data Aprovacao</span>
          </dt>
          <dd>
            {cadastroSolicitacaoEntity.dataAprovacao ? (
              <TextFormat value={cadastroSolicitacaoEntity.dataAprovacao} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="ativa">Ativa</span>
          </dt>
          <dd>{cadastroSolicitacaoEntity.ativa ? 'true' : 'false'}</dd>
          <dt>
            <span id="fatorPrioridade">Fator Prioridade</span>
          </dt>
          <dd>{cadastroSolicitacaoEntity.fatorPrioridade}</dd>
          <dt>
            <span id="statusSolicitacao">Status Solicitacao</span>
          </dt>
          <dd>{cadastroSolicitacaoEntity.statusSolicitacao}</dd>
          <dt>Nome</dt>
          <dd>{cadastroSolicitacaoEntity.nome ? cadastroSolicitacaoEntity.nome.id : ''}</dd>
          <dt>Descricao</dt>
          <dd>{cadastroSolicitacaoEntity.descricao ? cadastroSolicitacaoEntity.descricao.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cadastro-solicitacao" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cadastro-solicitacao/${cadastroSolicitacaoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CadastroSolicitacaoDetail;
