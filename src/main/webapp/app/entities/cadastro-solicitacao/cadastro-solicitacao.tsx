import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './cadastro-solicitacao.reducer';
import { ICadastroSolicitacao } from 'app/shared/model/cadastro-solicitacao.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CadastroSolicitacao = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const cadastroSolicitacaoList = useAppSelector(state => state.cadastroSolicitacao.entities);
  const loading = useAppSelector(state => state.cadastroSolicitacao.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="cadastro-solicitacao-heading" data-cy="CadastroSolicitacaoHeading">
        Cadastro Solicitacaos
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Cadastro Solicitacao
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {cadastroSolicitacaoList && cadastroSolicitacaoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nome Solicitante</th>
                <th>Cpf Solicitante</th>
                <th>Data Solicitacao</th>
                <th>Endereco</th>
                <th>Ponto Referencia</th>
                <th>Situacao Solicitante</th>
                <th>Quantidade Pessoas</th>
                <th>Retira Doacao</th>
                <th>Perdeu Moradia</th>
                <th>Perdeu Emprego</th>
                <th>Idoso Deficiente</th>
                <th>Filhos Pequenos</th>
                <th>Observacao</th>
                <th>Data Aprovacao</th>
                <th>Ativa</th>
                <th>Fator Prioridade</th>
                <th>Status Solicitacao</th>
                <th>Nome</th>
                <th>Descricao</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {cadastroSolicitacaoList.map((cadastroSolicitacao, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${cadastroSolicitacao.id}`} color="link" size="sm">
                      {cadastroSolicitacao.id}
                    </Button>
                  </td>
                  <td>{cadastroSolicitacao.nomeSolicitante}</td>
                  <td>{cadastroSolicitacao.cpfSolicitante}</td>
                  <td>
                    {cadastroSolicitacao.dataSolicitacao ? (
                      <TextFormat type="date" value={cadastroSolicitacao.dataSolicitacao} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{cadastroSolicitacao.endereco}</td>
                  <td>{cadastroSolicitacao.pontoReferencia}</td>
                  <td>{cadastroSolicitacao.situacaoSolicitante}</td>
                  <td>{cadastroSolicitacao.quantidadePessoas}</td>
                  <td>{cadastroSolicitacao.retiraDoacao ? 'true' : 'false'}</td>
                  <td>{cadastroSolicitacao.perdeuMoradia ? 'true' : 'false'}</td>
                  <td>{cadastroSolicitacao.perdeuEmprego ? 'true' : 'false'}</td>
                  <td>{cadastroSolicitacao.idosoDeficiente ? 'true' : 'false'}</td>
                  <td>{cadastroSolicitacao.filhosPequenos ? 'true' : 'false'}</td>
                  <td>{cadastroSolicitacao.observacao}</td>
                  <td>
                    {cadastroSolicitacao.dataAprovacao ? (
                      <TextFormat type="date" value={cadastroSolicitacao.dataAprovacao} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{cadastroSolicitacao.ativa ? 'true' : 'false'}</td>
                  <td>{cadastroSolicitacao.fatorPrioridade}</td>
                  <td>{cadastroSolicitacao.statusSolicitacao}</td>
                  <td>
                    {cadastroSolicitacao.nome ? (
                      <Link to={`cadastro-user/${cadastroSolicitacao.nome.id}`}>{cadastroSolicitacao.nome.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {cadastroSolicitacao.descricao ? (
                      <Link to={`solicitacao-item/${cadastroSolicitacao.descricao.id}`}>{cadastroSolicitacao.descricao.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${cadastroSolicitacao.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${cadastroSolicitacao.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${cadastroSolicitacao.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Cadastro Solicitacaos found</div>
        )}
      </div>
    </div>
  );
};

export default CadastroSolicitacao;
