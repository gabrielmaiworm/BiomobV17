import { IUser } from 'app/shared/model/user.model';
import { IAcao } from 'app/shared/model/acao.model';

export interface ICadastroUser {
  id?: number;
  nome?: string | null;
  telefone?: string | null;
  documentoNumero?: string | null;
  pais?: string | null;
  estado?: string | null;
  cidade?: string | null;
  bairro?: string | null;
  cep?: number | null;
  logradouro?: string | null;
  numero?: number | null;
  complemento?: string | null;
  user?: IUser | null;
  acao?: IAcao | null;
}

export const defaultValue: Readonly<ICadastroUser> = {};
