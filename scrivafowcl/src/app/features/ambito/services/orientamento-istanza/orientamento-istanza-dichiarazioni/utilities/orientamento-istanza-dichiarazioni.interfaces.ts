import { Observable } from 'rxjs';
import {
  CompetenzaTerritorio,
  ConfigAdempimento,
  TipoAdempimento,
} from '../../../../../../shared/models';
import { TipoInfoAdempimento } from '../../../../../../shared/services/configurazioni/utilities/configurazioni.enums';
import { IFormIoOrientamentoDichiarazioni } from '../../../../pages/orientamento-istanza/utilities/orientamento-istanza.interfaces';

/**
 * Interfaccia che definisce tutte le informazioni per la gestione dell'autorità competente.
 */
export interface IGestisciACDichiarazioni {
  codiceProcedimento: string;
  tipoInformazione: TipoInfoAdempimento;
  autoritaCompetente: CompetenzaTerritorio;
  formioStructure: IFormIoOrientamentoDichiarazioni;
  tipoAdempimento: TipoAdempimento;
}

/**
 * Interfaccia che definisce le richieste per lo scarico delle configurazioni per l'autorità competente.
 */
export interface IACFormioReq {
  regionePiemonte: Observable<ConfigAdempimento>;
  altraAutoritaCompetente?: Observable<ConfigAdempimento>;
}

/**
 * Interfaccia che definisce le risposte allo scarico delle configurazioni per l'autorità competente.
 */
export interface IACFormioRes {
  regionePiemonte: ConfigAdempimento;
  altraAutoritaCompetente?: ConfigAdempimento;
}
