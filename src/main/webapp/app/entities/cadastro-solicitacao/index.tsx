import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CadastroSolicitacao from './cadastro-solicitacao';
import CadastroSolicitacaoDetail from './cadastro-solicitacao-detail';
import CadastroSolicitacaoUpdate from './cadastro-solicitacao-update';
import CadastroSolicitacaoDeleteDialog from './cadastro-solicitacao-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CadastroSolicitacaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CadastroSolicitacaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CadastroSolicitacaoDetail} />
      <ErrorBoundaryRoute path={match.url} component={CadastroSolicitacao} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CadastroSolicitacaoDeleteDialog} />
  </>
);

export default Routes;
