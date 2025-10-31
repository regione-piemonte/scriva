/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import {
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { Observable, of } from 'rxjs';
import { switchMap, tap } from 'rxjs/operators';
import { Allegato, TipologiaAllegato } from 'src/app/features/ambito/models';
import { AllegatiService } from 'src/app/features/ambito/services';
import { ScrivaServerError } from '../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../core/enums/scriva-codes-messages.enums';
import { IServiziErrorConfig } from '../../interfaces/scriva-utilities.interfaces';
import { Adempimento, Istanza } from '../../models';
import {
  AuthStoreService,
  IstanzaService,
  MessageService,
} from '../../services';
import { HeaderQuadroConsts } from './utilities/header-quadro.consts';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-header-quadro',
  templateUrl: './header-quadro.component.html',
  styleUrls: ['./header-quadro.component.scss'],
})
export class HeaderQuadroComponent implements OnInit, OnChanges {
  /** HeaderQuadroConsts con le informazioni costanti e di utility del componente. */
  HQ_C = new HeaderQuadroConsts();

  @Input() adempimentoInput: Adempimento;
  @Input() istanza: Istanza;

  desAdempimento = '';

  constructor(
    private _authStore: AuthStoreService,
    private istanzaService: IstanzaService,
    private allegatiService: AllegatiService,
    private _message: MessageService,
    private spinner: NgxSpinnerService
  ) {}

  ngOnChanges(changes: SimpleChanges) {
    if (this.istanza) {
      this.desAdempimento = this.istanza.adempimento.des_adempimento;
    } else {
      this.desAdempimento = this.adempimentoInput?.des_adempimento;
    }
  }

  ngOnInit() {}

  /**
   * #########################
   * UTILITY PER DOWNLOAD FILE
   * #########################
   */

  /**
   * Funzione di comodo che ricerca all'interno di una lista di allegato, un allegato per codice tipo allegato.
   * @param allegati Allegato[] con la lista di allegati per la ricerca.
   * @param codTipoAllegato string con il codice per la ricerca.
   * @returns Allegato con l'oggetto trovato. Altrimenti undefined.
   */
  private cercaAllegatoByTipologiaAllegato(
    allegati: Allegato[],
    codTipoAllegato: string
  ): Allegato {
    // Verifico l'input
    allegati = allegati ?? [];

    // Cerco e ritorno l'allegato
    return allegati.find((a: Allegato) => {
      // Recupero il tipo allegato
      const ta: TipologiaAllegato = a?.tipologia_allegato;
      // Verifico e ritorno la condizione
      return ta?.cod_tipologia_allegato === codTipoAllegato;
    });
  }

  /**
   * Funzione di comodo che ricerca all'interno di una lista di allegato, un allegato firmato per l'istanza.
   * Il codice di riferimento è: "MOD_IST".
   * @param allegati Allegato[] con la lista di allegati per la ricerca.
   * @returns Allegato con l'oggetto trovato. Altrimenti undefined.
   */
  private cercaAllegato_MOD_IST(allegati: Allegato[]): Allegato {
    // Definisco il codice di ricerca
    const ricerca: string = 'MOD_IST';
    // Richiamo e ritorno il risultato della ricerca interna
    return this.cercaAllegatoByTipologiaAllegato(allegati, ricerca);
  }

  /**
   * Funzione di comodo che ricerca all'interno di una lista di allegato, un allegato firmato esternamente per l'istanza.
   * Il codice di riferimento è: "MOD_IST_ESTERNO".
   * @param allegati Allegato[] con la lista di allegati per la ricerca.
   * @returns Allegato con l'oggetto trovato. Altrimenti undefined.
   */
  private cercaAllegato_MOD_IST_ESTERNO(allegati: Allegato[]): Allegato {
    // Definisco il codice di ricerca
    const ricerca: string = 'MOD_IST_ESTERNO';
    // Richiamo e ritorno il risultato della ricerca interna
    return this.cercaAllegatoByTipologiaAllegato(allegati, ricerca);
  }

  /**
   * #############
   * DOWNLOAD FILE
   * #############
   */

  /**
   * Funzione che scarica la bozza dell'istanza.
   * A seconda dello stato dell'istanza, vengono gestite logiche di download differenti da INDEX oppure generato dai dati SCRIVA.
   * @reference SCRIVA-1359 aggiunta gestione casistiche specifiche richieste.
   */
  onDownload() {
    // Faccio partire lo spinner
    this.spinner.show();

    // Richiamo la funzione per recuperare tutti gli allegati dell'istanza
    this.allegatiService
      .getAllAllegatiIstanza(this.istanza.id_istanza)
      .pipe(
        switchMap((allegati: Allegato[]) => {
          // Richiamo la funzione che definisce le logiche per la gestione del download dati
          return this.downloadFileIstanza(allegati);
          // #
        })
      )
      .subscribe(
        (response: any) => {
          // In questo caso la response è variabile dalla chiamate della switchmap, serve solo per chiudere lo spinner
          this.spinner.hide();
          // #
        },
        (error: ScrivaServerError) => {
          // Gestisco l'errore
          this.onServiziError(error);
          // Chiudo lo spinner
          this.spinner.hide();
        }
      );
  }

  /**
   * Funzione specifica che gestisce le logiche di business che definiscono da quale fonte dati deve essere scaricato il file.
   * La funzione gestisce anche tutte le casistiche puntuali richieste.
   * @param allegati Allegato[] con la lista dati scaricati dalla funzione in riferimento all'istanza attuale.
   * @returns Observable<any> con il risultato delle funzioni. Le logiche specifiche di download sono definite dalle sotto funzioni richiamate poiché differenti tra loro.
   */
  private downloadFileIstanza(allegati: Allegato[]): Observable<any> {
    // Recupero le informazioni per gestire il flusso
    const moduloFirmato: Allegato = this.cercaAllegato_MOD_IST(allegati);
    const moduloFirmatoEsterno: Allegato =
      this.cercaAllegato_MOD_IST_ESTERNO(allegati);
    // Verifico se siamo FO o BO
    const isBO: boolean = this._authStore.isBackOffice;
    // Converto in booleano l'esistenza del modulo firmato
    const existModFirmato: boolean = !!moduloFirmato;
    const existModFirmatoExt: boolean = !!moduloFirmatoEsterno;

    // Definisco le condizioni per lo scarico file (!! serve per parsare la condizione di oggetto esistente in boolean)
    // CASO 1: moduloFirmato o moduloFirmatoEsterno in contesto BO
    const case1: boolean = existModFirmato || (existModFirmatoExt && isBO);
    // CASO 2: modulo non firmato, ne interno ne esterno e in contesto BO
    const case2: boolean = isBO && !existModFirmato && !existModFirmatoExt;
    // CASO 3: modulo interno non firmato e in contesto FO
    const case3: boolean = !isBO && !existModFirmato;

    // Verifico le condizioni e gestisco il flusso
    if (case1) {
      // Vado a recuperare l'oggetto del modulo
      const modulo: Allegato = moduloFirmato ?? moduloFirmatoEsterno;
      // Il modulo risulta firmato interno oppure esterno e siamo in BO, scarico il file
      return this.downloadModuloFirmato(modulo);
      // #
    } else if (case2) {
      // Siamo in contesto BO, ma non esiste un modulo firmato ne interno ne esterno, effetto la segnalazione all'utente
      return this.bloccaDownloadAllegatoSenzaFirmaBO();
      // #
    } else if (case3) {
      // Contesto FO con modulo interno non firmato, il file deve essere generato dall'istanza
      return this.generaModuloIstanza();
      // #
    } else {
      // Caso di default di sicurezza
      return of({});
    }
  }

  /**
   * ##############################
   * GESTIONE DOWNLOAD MODULO INDEX
   * ##############################
   */

  /**
   * Funzione che effettua il download di un modulo firmato, andando a recuperarlo da INDEX.
   * @param modulo Allegato con le informazioni del modulo da scaricare.
   * @returns Observable<any> con il risultato delle funzioni.
   */
  private downloadModuloFirmato(modulo: Allegato) {
    // Recupero dal modulo lo uuid_index di INDEX
    const uuidIndex: string = modulo?.uuid_index;

    // Effettuo la chiamata e ritorno le informazioni riguardante l'allegato
    return this.allegatiService.getAllegatoByUuid(uuidIndex).pipe(
      tap((res: any) => {
        // Scarico localmente il file passando i parametri
        this.generaFileINDEX(res, modulo.nome_allegato);
        // #
      })
    );
  }

  /**
   * Funzione che gestisce le informazioni dei dati scaricati dalla chiamata come allegato da index, andando ad effettuare il salvataggio in locale.
   * @param res any con le informazioni di ritorno dallo scarico file di index.
   * @param fileName string con il nome del file da generare.
   */
  private generaFileINDEX(res: any, fileName: string) {
    // Genero un blob dati
    const blob = new Blob([res], { type: 'application/pdf' });
    // Creo l'url per lo scarico con i dati del blob
    const url = window.URL.createObjectURL(blob);
    // Definisco il file name
    fileName = fileName ?? `ALLEGATO_${new Date().toLocaleDateString()}`;

    // Creo un elemento link d'attivare programmativamente per lo scarico del file
    const a = document.createElement('a');
    a.href = url;
    a.download = fileName;
    a.click();
    window.URL.revokeObjectURL(url);

    // Rimuovo il link nascosto di download
    a.remove();
  }

  /**
   * ###############################
   * GESTIONE DOWNLOAD MODULO SCRIVA
   * ###############################
   */

  /**
   * Funzione che effettua il download di un modulo generato dai dati di SCRIVA.
   * @returns Observable<any> con il risultato delle funzioni.
   */
  generaModuloIstanza(): Observable<any> {
    // Recupero dall'istanza l'id per lo scarico dati
    const idIstanza: number = this.istanza.id_istanza;

    // Richiamo la funzione di download dei dati
    return this.istanzaService.downloadMuduloIstanza(idIstanza).pipe(
      tap((res: HttpResponse<Blob>) => {
        // Scarico localmente il file passando i parametri
        this.generaFileSCRIVA(res);
        // #
      })
    );
  }

  /**
   * Funzione che gestisce le informazioni dei dati scaricati dalla chiamata come istanza SCRIVA.
   * @param res HttpResponse<Blob> con le informazioni di ritorno dallo scarico dati.
   */
  private generaFileSCRIVA(res: HttpResponse<Blob>) {
    // Recupero il body dati per il blob
    const blobBody: any = res.body;

    // Genero un blob dati
    const blob = new Blob([blobBody], { type: 'application/pdf' });
    // Creo l'url per lo scarico con i dati del blob
    const url = window.URL.createObjectURL(blob);

    // Recupero il file name dall'header della risposta
    const contentDispositionHeader = res.headers.get('Content-Disposition');
    let fileName = contentDispositionHeader.split('filename="')[1];
    fileName = fileName.slice(0, -1);

    // Creo un elemento link d'attivare programmativamente per lo scarico del file
    const a = document.createElement('a');
    a.href = url;
    a.download = fileName;
    a.click();
    window.URL.revokeObjectURL(url);

    // Rimuovo il link nascosto di download
    a.remove();
  }

  /**
   * ############################
   * GESTIONI DOWNLOAD SPECIFICHE
   * ############################
   */

  /**
   * Funzione di comodo che gestisce una delle casistiche per il download del file.
   * La funzione visualizza un messaggio e non compie altre azioni, come da richiesta.
   * @reference SCRIVA-1359 aggiunta gestione casistiche specifiche richieste.
   */
  bloccaDownloadAllegatoSenzaFirmaBO(): Observable<any> {
    // Definisco delle variabili di comodo
    const div: string = this.messageDivDefault;
    const code: ScrivaCodesMesseges = ScrivaCodesMesseges.I029;

    // Caso specifico con gestione richiesta mediante informazione specifica
    let message: IServiziErrorConfig;
    message = this._message.createMessageConfig(div, null, code, true);
    // Visualizzo il messaggio informativo
    this._message.showMessageConfigs(message);

    // Ritorno un observable con l'oggetto di messaggistica generato
    return of(message);
  }

  /**
   * ###################
   * FUNZIONI DI UTILITY
   * ###################
   */

  /**
   * Funzione di comodo che gestisce la segnalazione dei messaggi d'errore dai servizi.
   * @param error ScrivaServerError con l'errore generato dal server.
   */
  onServiziError(error?: ScrivaServerError) {
    // Definisco delle variabili di comodo
    const div: string = this.messageDivDefault;
    // Caso specifico con gestione richiesta mediante informazione specifica
    let message: IServiziErrorConfig;
    message = this._message.createMessageConfig(div, error);
    // Visualizzo il messaggio informativo
    this._message.showMessageConfigs(message);
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter per l'id del div di default per il componente per la messaggistica utente.
   * @return string con l'id recuperato.
   */
  get messageDivDefault(): string {
    // Ritorno l'id dalle costanti
    return this.HQ_C.DIV_MSG_DEFAULT;
  }
}
