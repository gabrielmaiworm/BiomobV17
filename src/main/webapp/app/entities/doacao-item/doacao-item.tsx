import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './doacao-item.reducer';
import { IDoacaoItem } from 'app/shared/model/doacao-item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DoacaoItem = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const doacaoItemList = useAppSelector(state => state.doacaoItem.entities);
  const loading = useAppSelector(state => state.doacaoItem.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="doacao-item-heading" data-cy="DoacaoItemHeading">
        Doacao Items
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Doacao Item
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {doacaoItemList && doacaoItemList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Imagem</th>
                <th>Quantidade</th>
                <th>Observacao</th>
                <th>Descricao</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {doacaoItemList.map((doacaoItem, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${doacaoItem.id}`} color="link" size="sm">
                      {doacaoItem.id}
                    </Button>
                  </td>
                  <td>
                    {doacaoItem.imagem ? (
                      <div>
                        {doacaoItem.imagemContentType ? (
                          <a onClick={openFile(doacaoItem.imagemContentType, doacaoItem.imagem)}>
                            <img src={`data:${doacaoItem.imagemContentType};base64,${doacaoItem.imagem}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {doacaoItem.imagemContentType}, {byteSize(doacaoItem.imagem)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{doacaoItem.quantidade}</td>
                  <td>{doacaoItem.observacao}</td>
                  <td>{doacaoItem.descricao ? <Link to={`item/${doacaoItem.descricao.id}`}>{doacaoItem.descricao.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${doacaoItem.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${doacaoItem.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${doacaoItem.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Doacao Items found</div>
        )}
      </div>
    </div>
  );
};

export default DoacaoItem;
