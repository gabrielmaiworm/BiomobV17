import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { IAcao } from 'app/shared/model/acao.model';
import { getEntities as getAcaos } from 'app/entities/acao/acao.reducer';
import { getEntity, updateEntity, createEntity, reset } from './solicitacao-item.reducer';
import { ISolicitacaoItem } from 'app/shared/model/solicitacao-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SolicitacaoItemUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const items = useAppSelector(state => state.item.entities);
  const acaos = useAppSelector(state => state.acao.entities);
  const solicitacaoItemEntity = useAppSelector(state => state.solicitacaoItem.entity);
  const loading = useAppSelector(state => state.solicitacaoItem.loading);
  const updating = useAppSelector(state => state.solicitacaoItem.updating);
  const updateSuccess = useAppSelector(state => state.solicitacaoItem.updateSuccess);
  const handleClose = () => {
    props.history.push('/solicitacao-item');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getItems({}));
    dispatch(getAcaos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...solicitacaoItemEntity,
      ...values,
      descricao: items.find(it => it.id.toString() === values.descricao.toString()),
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
          ...solicitacaoItemEntity,
          descricao: solicitacaoItemEntity?.descricao?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="bioMobApp.solicitacaoItem.home.createOrEditLabel" data-cy="SolicitacaoItemCreateUpdateHeading">
            Create or edit a SolicitacaoItem
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
                <ValidatedField name="id" required readOnly id="solicitacao-item-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Quantidade" id="solicitacao-item-quantidade" name="quantidade" data-cy="quantidade" type="text" />
              <ValidatedField label="Observacao" id="solicitacao-item-observacao" name="observacao" data-cy="observacao" type="text" />
              <ValidatedField id="solicitacao-item-descricao" name="descricao" data-cy="descricao" label="Descricao" type="select">
                <option value="" key="0" />
                {items
                  ? items.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/solicitacao-item" replace color="info">
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

export default SolicitacaoItemUpdate;
