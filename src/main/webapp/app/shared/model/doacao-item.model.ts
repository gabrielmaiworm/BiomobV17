import { IItem } from 'app/shared/model/item.model';
import { IAcao } from 'app/shared/model/acao.model';

export interface IDoacaoItem {
  id?: number;
  imagemContentType?: string | null;
  imagem?: string | null;
  quantidade?: number | null;
  observacao?: string | null;
  descricao?: IItem | null;
  acao?: IAcao | null;
}

export const defaultValue: Readonly<IDoacaoItem> = {};
