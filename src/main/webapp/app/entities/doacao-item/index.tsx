import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DoacaoItem from './doacao-item';
import DoacaoItemDetail from './doacao-item-detail';
import DoacaoItemUpdate from './doacao-item-update';
import DoacaoItemDeleteDialog from './doacao-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DoacaoItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DoacaoItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DoacaoItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={DoacaoItem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DoacaoItemDeleteDialog} />
  </>
);

export default Routes;
