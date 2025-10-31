import { DataQuadro } from '../../../../../shared/models';

/**
 * Interface con i parametri per le informazioni per la gestione della chiamata per il salvataggio dati per i json data dei quadri delle opere.
 */
export interface IParamsDataQuadro {
  dataQuadro: DataQuadro;
  isPut: boolean;
}

/**
 * Interface con i parametri per le informazioni per la gestione della chiamata per il salvataggio dati per i json data dei quadri delle opere.
 */
export interface IParamsDataRiepilogo {
  dataQuadro: DataQuadro;
}

/**
 * Interface con le informazioni ritornate dal servizio di salvataggio dati per il json data quadro e riepilogo.
 */
export interface IOpereSaveJsonDataRes {
  quadro: DataQuadro;
  riepilogo: DataQuadro;
}
