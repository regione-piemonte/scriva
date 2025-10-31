import { Injectable } from '@angular/core';
import { IScrivaAnnoSelect } from '../utilities/test-input-form.interfaces';

import { Observable, throwError, of } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { ScrivaServerError } from 'src/app/core/classes/scriva.classes';
import { IScrivaServerError } from 'src/app/core/interfaces/scriva.interfaces';
import { ScrivaCodesMesseges } from 'src/app/core/enums/scriva-codes-messages.enums';
import { TestInputConfigService } from './test-input-config.service';

@Injectable({
  providedIn: 'root'
})
export class TestInputFormService {

  /**
   * Costruttore.
   */
  constructor(private _configurazioni: TestInputConfigService) {}

  /**
   * ###############################
   * FUNZIONI CHE RICHIAMO I SERVIZI
   * ###############################
   */

   /**
   * Funzione di GET che recupera la lista degli anni disponibili per i tipi usi regole, dato un id ambito di riferimento.
   * La funzione modifica e manipola l'output della get base e la gestisce per la logica FE.
   * @returns Observable<number[]> con le informazioni scaricate.
   */
   getAnniList(): Observable<IScrivaAnnoSelect[]> {
    // Richiamo la funzione del servizio
    return this._configurazioni.getListaAnni().pipe(
      switchMap((anni: number[]) => {
        // Verifico se l'array restituito è vuoto
        if (!anni || anni.length == 0) {
          // La lista vuota definisco le informazioni per la gestione dell'errore
          const code = ScrivaCodesMesseges.I040;
          const title = `[SCRIVA-FE] ${this.PATH_ANNI} returned an empty response`;
          const error: IScrivaServerError = { code, title };
          // Genero e ritorno un errore
          const e = new ScrivaServerError(error);
          return throwError(e);
          // #
        } else {
          // Lista con valori, la rigiro
          return of(anni);
          // #
        }
      }),
      map((anni: number[]) => {
        // Converto la lista in oggetti IScrivaAnnoSelect
        return anni.map((anno: number, i: number) => {
          // Definisco un oggetto IScrivaAnnoSelect
          let ras: IScrivaAnnoSelect;
          ras = { anno };

          // Il servizio, da documentazione, riporta che il primo elemento della lista deve essere quello selezionato di default
          if (i === 0) {
            // "sovrascrivo" l'oggetto definendo la proprietà __selected
            ras = { ...ras, ...{ __selected: true } };
            // #
          }

          // Ritorno l'oggetto
          return ras;
        });
      })
    );
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter di comodo, che restituisce il path dell'API dal servizio di dipendenza.
   * @returns string con l'API.
   */
  get PATH_ANNI(): string {
    // Recupero il path dal servizio
    return this._configurazioni.PATH_ANNI;
  }

}
