import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './solicitacao-item.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SolicitacaoItemDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const solicitacaoItemEntity = useAppSelector(state => state.solicitacaoItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="solicitacaoItemDetailsHeading">SolicitacaoItem</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{solicitacaoItemEntity.id}</dd>
          <dt>
            <span id="quantidade">Quantidade</span>
          </dt>
          <dd>{solicitacaoItemEntity.quantidade}</dd>
          <dt>
            <span id="observacao">Observacao</span>
          </dt>
          <dd>{solicitacaoItemEntity.observacao}</dd>
          <dt>Descricao</dt>
          <dd>{solicitacaoItemEntity.descricao ? solicitacaoItemEntity.descricao.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/solicitacao-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/solicitacao-item/${solicitacaoItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SolicitacaoItemDetail;
