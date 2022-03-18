import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICadastroUser } from 'app/shared/model/cadastro-user.model';
import { getEntities as getCadastroUsers } from 'app/entities/cadastro-user/cadastro-user.reducer';
import { ISolicitacaoItem } from 'app/shared/model/solicitacao-item.model';
import { getEntities as getSolicitacaoItems } from 'app/entities/solicitacao-item/solicitacao-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cadastro-solicitacao.reducer';
import { ICadastroSolicitacao } from 'app/shared/model/cadastro-solicitacao.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { StatusSolicitacao } from 'app/shared/model/enumerations/status-solicitacao.model';

export const CadastroSolicitacaoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const cadastroUsers = useAppSelector(state => state.cadastroUser.entities);
  const solicitacaoItems = useAppSelector(state => state.solicitacaoItem.entities);
  const cadastroSolicitacaoEntity = useAppSelector(state => state.cadastroSolicitacao.entity);
  const loading = useAppSelector(state => state.cadastroSolicitacao.loading);
  const updating = useAppSelector(state => state.cadastroSolicitacao.updating);
  const updateSuccess = useAppSelector(state => state.cadastroSolicitacao.updateSuccess);
  const statusSolicitacaoValues = Object.keys(StatusSolicitacao);
  const handleClose = () => {
    props.history.push('/cadastro-solicitacao');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCadastroUsers({}));
    dispatch(getSolicitacaoItems({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...cadastroSolicitacaoEntity,
      ...values,
      nome: cadastroUsers.find(it => it.id.toString() === values.nome.toString()),
      descricao: solicitacaoItems.find(it => it.id.toString() === values.descricao.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          statusSolicitacao: 'ATENDIDO',
          ...cadastroSolicitacaoEntity,
          nome: cadastroSolicitacaoEntity?.nome?.id,
          descricao: cadastroSolicitacaoEntity?.descricao?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="bioMobApp.cadastroSolicitacao.home.createOrEditLabel" data-cy="CadastroSolicitacaoCreateUpdateHeading">
            Create or edit a CadastroSolicitacao
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="cadastro-solicitacao-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Nome Solicitante"
                id="cadastro-solicitacao-nomeSolicitante"
                name="nomeSolicitante"
                data-cy="nomeSolicitante"
                type="text"
              />
              <ValidatedField
                label="Cpf Solicitante"
                id="cadastro-solicitacao-cpfSolicitante"
                name="cpfSolicitante"
                data-cy="cpfSolicitante"
                type="text"
              />
              <ValidatedField
                label="Data Solicitacao"
                id="cadastro-solicitacao-dataSolicitacao"
                name="dataSolicitacao"
                data-cy="dataSolicitacao"
                type="date"
              />
              <ValidatedField label="Endereco" id="cadastro-solicitacao-endereco" name="endereco" data-cy="endereco" type="text" />
              <ValidatedField
                label="Ponto Referencia"
                id="cadastro-solicitacao-pontoReferencia"
                name="pontoReferencia"
                data-cy="pontoReferencia"
                type="text"
              />
              <ValidatedField
                label="Situacao Solicitante"
                id="cadastro-solicitacao-situacaoSolicitante"
                name="situacaoSolicitante"
                data-cy="situacaoSolicitante"
                type="text"
              />
              <ValidatedField
                label="Quantidade Pessoas"
                id="cadastro-solicitacao-quantidadePessoas"
                name="quantidadePessoas"
                data-cy="quantidadePessoas"
                type="text"
              />
              <ValidatedField
                label="Retira Doacao"
                id="cadastro-solicitacao-retiraDoacao"
                name="retiraDoacao"
                data-cy="retiraDoacao"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Perdeu Moradia"
                id="cadastro-solicitacao-perdeuMoradia"
                name="perdeuMoradia"
                data-cy="perdeuMoradia"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Perdeu Emprego"
                id="cadastro-solicitacao-perdeuEmprego"
                name="perdeuEmprego"
                data-cy="perdeuEmprego"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Idoso Deficiente"
                id="cadastro-solicitacao-idosoDeficiente"
                name="idosoDeficiente"
                data-cy="idosoDeficiente"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Filhos Pequenos"
                id="cadastro-solicitacao-filhosPequenos"
                name="filhosPequenos"
                data-cy="filhosPequenos"
                check
                type="checkbox"
              />
              <ValidatedField label="Observacao" id="cadastro-solicitacao-observacao" name="observacao" data-cy="observacao" type="text" />
              <ValidatedField
                label="Data Aprovacao"
                id="cadastro-solicitacao-dataAprovacao"
                name="dataAprovacao"
                data-cy="dataAprovacao"
                type="date"
              />
              <ValidatedField label="Ativa" id="cadastro-solicitacao-ativa" name="ativa" data-cy="ativa" check type="checkbox" />
              <ValidatedField
                label="Fator Prioridade"
                id="cadastro-solicitacao-fatorPrioridade"
                name="fatorPrioridade"
                data-cy="fatorPrioridade"
                type="text"
              />
              <ValidatedField
                label="Status Solicitacao"
                id="cadastro-solicitacao-statusSolicitacao"
                name="statusSolicitacao"
                data-cy="statusSolicitacao"
                type="select"
              >
                {statusSolicitacaoValues.map(statusSolicitacao => (
                  <option value={statusSolicitacao} key={statusSolicitacao}>
                    {statusSolicitacao}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="cadastro-solicitacao-nome" name="nome" data-cy="nome" label="Nome" type="select">
                <option value="" key="0" />
                {cadastroUsers
                  ? cadastroUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="cadastro-solicitacao-descricao" name="descricao" data-cy="descricao" label="Descricao" type="select">
                <option value="" key="0" />
                {solicitacaoItems
                  ? solicitacaoItems.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cadastro-solicitacao" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CadastroSolicitacaoUpdate;
