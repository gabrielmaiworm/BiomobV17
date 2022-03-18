import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SolicitacaoItem from './solicitacao-item';
import SolicitacaoItemDetail from './solicitacao-item-detail';
import SolicitacaoItemUpdate from './solicitacao-item-update';
import SolicitacaoItemDeleteDialog from './solicitacao-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SolicitacaoItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SolicitacaoItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SolicitacaoItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={SolicitacaoItem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SolicitacaoItemDeleteDialog} />
  </>
);

export default Routes;
