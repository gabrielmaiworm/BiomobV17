import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './doacao-item.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DoacaoItemDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const doacaoItemEntity = useAppSelector(state => state.doacaoItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="doacaoItemDetailsHeading">DoacaoItem</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{doacaoItemEntity.id}</dd>
          <dt>
            <span id="imagem">Imagem</span>
          </dt>
          <dd>
            {doacaoItemEntity.imagem ? (
              <div>
                {doacaoItemEntity.imagemContentType ? (
                  <a onClick={openFile(doacaoItemEntity.imagemContentType, doacaoItemEntity.imagem)}>
                    <img
                      src={`data:${doacaoItemEntity.imagemContentType};base64,${doacaoItemEntity.imagem}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {doacaoItemEntity.imagemContentType}, {byteSize(doacaoItemEntity.imagem)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="quantidade">Quantidade</span>
          </dt>
          <dd>{doacaoItemEntity.quantidade}</dd>
          <dt>
            <span id="observacao">Observacao</span>
          </dt>
          <dd>{doacaoItemEntity.observacao}</dd>
          <dt>Descricao</dt>
          <dd>{doacaoItemEntity.descricao ? doacaoItemEntity.descricao.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/doacao-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/doacao-item/${doacaoItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DoacaoItemDetail;
