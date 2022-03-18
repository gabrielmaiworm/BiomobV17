import dayjs from 'dayjs';
import { ICadastroUser } from 'app/shared/model/cadastro-user.model';
import { ISolicitacaoItem } from 'app/shared/model/solicitacao-item.model';
import { StatusSolicitacao } from 'app/shared/model/enumerations/status-solicitacao.model';

export interface ICadastroSolicitacao {
  id?: number;
  nomeSolicitante?: string | null;
  cpfSolicitante?: string | null;
  dataSolicitacao?: string | null;
  endereco?: string | null;
  pontoReferencia?: string | null;
  situacaoSolicitante?: string | null;
  quantidadePessoas?: number | null;
  retiraDoacao?: boolean | null;
  perdeuMoradia?: boolean | null;
  perdeuEmprego?: boolean | null;
  idosoDeficiente?: boolean | null;
  filhosPequenos?: boolean | null;
  observacao?: string | null;
  dataAprovacao?: string | null;
  ativa?: boolean | null;
  fatorPrioridade?: number | null;
  statusSolicitacao?: StatusSolicitacao | null;
  nome?: ICadastroUser | null;
  descricao?: ISolicitacaoItem | null;
}

export const defaultValue: Readonly<ICadastroSolicitacao> = {
  retiraDoacao: false,
  perdeuMoradia: false,
  perdeuEmprego: false,
  idosoDeficiente: false,
  filhosPequenos: false,
  ativa: false,
};
