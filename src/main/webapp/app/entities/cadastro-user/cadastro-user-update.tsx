import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IAcao } from 'app/shared/model/acao.model';
import { getEntities as getAcaos } from 'app/entities/acao/acao.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cadastro-user.reducer';
import { ICadastroUser } from 'app/shared/model/cadastro-user.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CadastroUserUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const acaos = useAppSelector(state => state.acao.entities);
  const cadastroUserEntity = useAppSelector(state => state.cadastroUser.entity);
  const loading = useAppSelector(state => state.cadastroUser.loading);
  const updating = useAppSelector(state => state.cadastroUser.updating);
  const updateSuccess = useAppSelector(state => state.cadastroUser.updateSuccess);
  const handleClose = () => {
    props.history.push('/cadastro-user');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getAcaos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...cadastroUserEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          ...cadastroUserEntity,
          user: cadastroUserEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="bioMobApp.cadastroUser.home.createOrEditLabel" data-cy="CadastroUserCreateUpdateHeading">
            Create or edit a CadastroUser
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
                <ValidatedField name="id" required readOnly id="cadastro-user-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Nome" id="cadastro-user-nome" name="nome" data-cy="nome" type="text" />
              <ValidatedField label="Telefone" id="cadastro-user-telefone" name="telefone" data-cy="telefone" type="text" />
              <ValidatedField
                label="Documento Numero"
                id="cadastro-user-documentoNumero"
                name="documentoNumero"
                data-cy="documentoNumero"
                type="text"
              />
              <ValidatedField label="Pais" id="cadastro-user-pais" name="pais" data-cy="pais" type="text" />
              <ValidatedField label="Estado" id="cadastro-user-estado" name="estado" data-cy="estado" type="text" />
              <ValidatedField label="Cidade" id="cadastro-user-cidade" name="cidade" data-cy="cidade" type="text" />
              <ValidatedField label="Bairro" id="cadastro-user-bairro" name="bairro" data-cy="bairro" type="text" />
              <ValidatedField label="Cep" id="cadastro-user-cep" name="cep" data-cy="cep" type="text" />
              <ValidatedField label="Logradouro" id="cadastro-user-logradouro" name="logradouro" data-cy="logradouro" type="text" />
              <ValidatedField label="Numero" id="cadastro-user-numero" name="numero" data-cy="numero" type="text" />
              <ValidatedField label="Complemento" id="cadastro-user-complemento" name="complemento" data-cy="complemento" type="text" />
              <ValidatedField id="cadastro-user-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cadastro-user" replace color="info">
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

export default CadastroUserUpdate;
