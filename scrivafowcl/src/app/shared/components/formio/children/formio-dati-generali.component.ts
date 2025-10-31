/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, Inject } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { Observable, throwError } from 'rxjs';
import { catchError, switchMap, tap } from 'rxjs/operators';
import {
  OggettoIstanza,
  Opera,
  TipologiaOggetto,
} from 'src/app/features/ambito/models';
import {
  AllegatiService,
  AmbitoService,
} from 'src/app/features/ambito/services';
import { CONFIG } from 'src/app/shared/config.injectiontoken';
import { StepConfig } from 'src/app/shared/models';
import {
  AppConfigService,
  AuthStoreService,
  HelpService,
  IstanzaService,
  MessageService,
  StepManagerService,
} from 'src/app/shared/services';
import { FormioImporterService } from 'src/app/shared/services/formio/formio-importer.service';
import { QuadriService } from 'src/app/shared/services/quadri/quadri.service';
import { ScrivaServerError } from '../../../../core/classes/scriva.classes';
import { DerivazioniBozzaService } from '../../../../features/ambito/services/ambito/derivazioni/derivazioni-bozza/derivazioni-bozza.service';
import { FormioModalsService } from '../../../services/formio/formio-modals.service';
import { FormioService } from '../../../services/formio/formio.service';
import { FormioI18NService } from '../../../services/formio/i18n/formio-i18n.service';
import { ScrivaUtilitiesService } from '../../../services/scriva-utilities/scriva-utilities.service';
import { FormioComponent } from '../formio.component';
import { PresentazioneIstanzaService } from 'src/app/features/ambito/services/presentazione-istanza/presentazione-istanza.service';

/**
 * Componente specifica per l'adempimento DERIVAZIONI.
 * Questo componente gestisce in maniera specifica la logica dei dati generali con configurazione FormIo.
 * Il componente ha delle logiche aggiuntive al salvataggio dei dati del quadro.
 */
@Component({
  selector: 'app-formio-dati-generali',
  templateUrl: '../formio.component.html',
  styleUrls: ['../formio.component.scss'],
  providers: [DerivazioniBozzaService],
})
export class FormioDatiGeneraliComponent extends FormioComponent {
  /**
   * Nota dello sviluppatore: queste configurazioni sotto fanno parte della definizione generica per l'istanza.
   * I valori sono specifici per la gestione specifica dell'inserimento di una nuova CON.
   */
  // Lavora solo con codAdempimento CON
  codAdempimento: string = 'CON';
  // tipologie di oggetto
  tipologieOggetto: TipologiaOggetto[] = [];
  /** dati per inserimento opeera, non sono dinamici, sono sempre i medesimi */
  idMasterdataOrigine: number = 1;
  idMasterdata: number = 1;
  oggetto: string = 'Concessione di derivazione idrica';
  // gestUID temporaneo per eventuale cancellazione
  tmpGestUID: string = null;

  constructor(
    @Inject(CONFIG) public injConfig: StepConfig,
    ambitoService: AmbitoService,
    configService: AppConfigService,
    messageService: MessageService,
    stepManagerService: StepManagerService,
    spinner: NgxSpinnerService,
    istanzaService: IstanzaService,
    authStoreService: AuthStoreService,
    helpService: HelpService,
    formio: FormioService,
    formioImporter: FormioImporterService,
    formioModals: FormioModalsService,
    formioI18N: FormioI18NService,
    quadri: QuadriService,
    allegati: AllegatiService,
    scrivaUtilities: ScrivaUtilitiesService,
    presentazioneIstanzaService: PresentazioneIstanzaService,
    private _bozzaDER: DerivazioniBozzaService
  ) {
    // chiamo il costruttore della classe padre
    super(
      ambitoService,
      configService,
      formio,
      formioImporter,
      formioModals,
      formioI18N,
      quadri,
      allegati,
      scrivaUtilities,
      presentazioneIstanzaService,
      injConfig,
      messageService,
      helpService,
      istanzaService,
      authStoreService,
      stepManagerService,
      spinner
    );
  }

  /**
   * ngOnInit.
   */
  ngOnInit() {
    // Richiamo l'init della classe padre
    super.ngOnInit();
    /* Questa componente viene utilizzata solo per le derivazioni con codAdempimenteo CON */
    /* Recupero le tipologie oggetto */
    this.ambitoService
      .getTipologieOggettoByAdempimento(this.codAdempimento)
      .subscribe({
        next: (tipologieOggetto: TipologiaOggetto[]) => {
          // aggiorno le tipologie oggetto
          this.tipologieOggetto = tipologieOggetto;
          // #
        },
        error: (e: ScrivaServerError) => {
          // Gestisco l'errore
          this.onServiziError(e);
          // #
        },
      });
  }

  /**
   * Funzione che gestisce le logiche di preparazione dei dati per proseguire allo step successivo.
   * 
   */
  public salvaDatiQuadro() {
    // Verifico se esiste all'interno dell'oggetto jsonData "id_oggetto_istanza" per definire "inserimento" o "modifica"
    let isInserimento: boolean = true;
    // La condizione di "inserimento" si basa sul fatto che non esiste la proprietà "id_oggetto", che viene definito da logiche, appunto, per l'inserimento
    isInserimento = !this.jsonDataQuadro?.id_oggetto;

    // Verifico la gestione del flusso
    if (isInserimento) {
      // Sono in inserimento
      this.inserimentoDatiGenerali();
      // #
    } else {
      // Sono in modifica
      this.modificaDatiGenerali();
      // #
    }
  }

  /**
   * Funzione che gestisce il flusso d'inserimento dei dati generali per DER.
   * La funzione d'inserimento di basa sull'assenza della proprietà "id_oggetto_istanza" dentro il jsonData del quadro.
   * L'inserimento prevede la creazione due oggetti: Oggetto e OggettoIstanza; per poter proseguire correttamente il flusso di gestione per le opere di captazione.
   */
  private inserimentoDatiGenerali() {
    // Recupero la COD_TIPOLOGIA_OGGETTO se presente
    const codTipologia = JSON.parse(this.istanza.json_data).QDR_CONFIG
      .COD_TIPOLOGIA_OGGETTO;

    // Utilizzo tipologia_oggetto in base a config o la passo null demendando la gestione al BE
    let tipologia_oggetto: TipologiaOggetto = null;
    // Verifico se esiste il codice tipologia
    if (codTipologia) {
      // Esiste il codice tipologia cerco di estrarre il dato dalla lista del componente
      tipologia_oggetto = this.tipologieOggetto.find(
        (tipologia: TipologiaOggetto) => {
          // Effettuo un match tra codici tipologia oggetto
          return tipologia.cod_tipologia_oggetto === codTipologia;
        }
      );
    }

    // Creo la nuova opera da salvare
    const nuovaOpera: Opera = {
      den_oggetto: this.oggetto,
      des_oggetto: this.oggetto,
      tipologia_oggetto: tipologia_oggetto,
      id_masterdata_origine: this.idMasterdataOrigine,
      id_masterdata: this.idMasterdata,
      id_istanza_aggiornamento: this.idIstanza,
      stato_oggetto: null,
      cod_oggetto_fonte: null,
      coordinata_x: null,
      coordinata_y: null,
    };

    // Creo la nuova OggettoIstanza da salvare
    let operaEdit: OggettoIstanza = {
      id_istanza: this.idIstanza,
      den_oggetto: this.oggetto,
      des_oggetto: this.oggetto,
      id_masterdata_origine: this.idMasterdataOrigine,
      id_masterdata: this.idMasterdata,
      siti_natura_2000: [],
      aree_protette: [],
      cod_oggetto_fonte: null,
      coordinata_x: null,
      coordinata_y: null,
      tipologia_oggetto: null,
      cod_utenza: null,
      note_atto_precedente: null,
    };

    // Lancio lo spinner
    this.spinner.show();

    this.ambitoService
      .salvaOpera(nuovaOpera, this.istanza.adempimento.cod_adempimento)
      .pipe(
        catchError((e: ScrivaServerError) => {
          // Gestisco l'errore segnalandolo
          this.onServiziError(e);
          // Ritorno comunque eccezione per la gestione del flusso
          return throwError(e);
          // #
        }),
        tap((opera: Opera) => {
          // setto id_oggetto della risposta per il salvataggio dell'opera
          operaEdit.id_oggetto = opera.id_oggetto;
          // salvo su this.tmpGestUID opera per eventuale eliminazione
          // che avviene su errore della salvaOggettoIstanza
          this.tmpGestUID = opera.gestUID;
          // il livello in questo caso e' fisso a 1
          operaEdit.ind_livello = 1;
          // #
        }),
        switchMap(() => {
          // Vado a salvare l'oggetto istanza rispetto all'opera che si sta gestendo nella funzione
          return this.ambitoService.salvaOggettoIstanza(operaEdit).pipe(
            catchError((e: ScrivaServerError) => {
              // Gestisco l'errore segnalandolo
              this.onServiziError(e);
              // Lancio l'eliminazione dell'opera (MA STA ROBA QUA? COSI, GESTITA AD CATZUM)
              this.ambitoService.eliminaOpera(this.tmpGestUID).subscribe();
              // Ritorno l'errore
              return throwError(e);
            })
          );
        })
      )
      .subscribe({
        next: (oggettoIstanza: OggettoIstanza) => {
          // Chiudo lo spinner
          this.spinner.hide();

          // Recupero gli id per l'oggetto opera e l'oggetto-istanza
          const idOpera: number = oggettoIstanza.id_oggetto;
          const idOggIst: number = oggettoIstanza.id_oggetto_istanza;
          // Dopo i salvataggi oggetto e oggetto istanza salvo la parte json
          this.saveJsonDataInserimento(idOpera, idOggIst);
          // #
        },
        error: (e: ScrivaServerError) => {
          // Chiudo lo spinner
          this.spinner.hide();
          // Gestisco l'errore segnalandolo
          this.onServiziError(e);
          // #
        },
      });
  }

  /**
   * Funzione che gestisce il flusso di modifica dei dati generali per DER.
   */
  private modificaDatiGenerali(){
    // Salvo le informazioni del json data
    this.saveJsonDataModifica();
  }

  /**
   * Funzione che gestisce la logica di flusso per il salvataggio del json data a seguito dell'inserimento dei dati del quadro.
   * In caso sia definito l'idOggettoIstanza, questo verrà aggiunto all'insieme dei dati del jsonData del quadro.
   * @param idOpera number con l'id opera di riferimento d'aggiungere ai dati del json data.
   * @param idOggettoIstanza number con l'id oggetto istanza di riferimento d'aggiungere ai dati del json data.
   */
  private saveJsonDataInserimento(
    idOpera: number = null,
    idOggettoIstanza: number = null
  ) {
    // Creo una copia dell'oggetto generato dal formio per poterci lavorare con la gestione dell'id opera
    let jsonDataQuadro = this.getJsonDataQuadroWithIdOperaEOggettoIstanza(
      idOpera,
      idOggettoIstanza
    );

    // Recupero le informazioni per il json_data
    const jsonData: any = this.jsonDataAll;
    // Lancio la funzione di gestione per le informazioni della bozza
    jsonDataQuadro = this._bozzaDER.completaDatiGeneraliBozza(
      jsonDataQuadro,
      jsonData
    );

    // Lancio la funzione per salvare e andare al prossimo step
    this.saveJsonDataAndGoNext(jsonDataQuadro);
  }

  /**
   * Funzione che gestisce la logica di flusso per il salvataggio del json data a seguito della modifica dei dati del quadro.
   * In caso sia definito l'idOggettoIstanza, questo verrà aggiunto all'insieme dei dati del jsonData del quadro.
   * @param idOpera number con l'id oggetto istanza di riferimento d'aggiungere ai dati del json data.
   * @param idOggettoIstanza number con l'id oggetto istanza di riferimento d'aggiungere ai dati del json data.
   */
  private saveJsonDataModifica(
    idOpera: number = null,
    idOggettoIstanza: number = null
  ) {
    // Creo una copia dell'oggetto generato dal formio per poterci lavorare con la gestione dell'id opera
    let jsonDataQuadro = this.getJsonDataQuadroWithIdOperaEOggettoIstanza(
      idOpera,
      idOggettoIstanza
    );
    // Lancio la funzione per salvare e andare al prossimo step
    this.saveJsonDataAndGoNext(jsonDataQuadro);
  }

  /**
   * Funzione che gestisce la logica di aggiornamento per l'id_opera di un jsonData sulla base dell'input.
   * @param idOpera number con l'id opera di riferimento d'aggiungere ai dati del json data.
   * @param idOggettoIstanza number con l'id oggetto istanza di riferimento d'aggiungere ai dati del json data.
   * @author Ismaele Bottelli
   * @date 07/03/2025
   * @jira SCRIVA-1578
   * @notes Le proprietà da salvare sono tutte e due, id opera e id oggetto-istanza.
   */
  private getJsonDataQuadroWithIdOperaEOggettoIstanza(
    idOpera: number = null,
    idOggettoIstanza: number = null
  ) {
    // Creo una copia dell'oggetto generato dal formio per poterci lavorare
    let jsonDataQuadro = super.getJsonDataQuadroforDB();
    // Solo nel caso di salvataggio opera e oggetto Istanza
    if (idOpera) {
      // Associo id_oggetto con quello passato in input alla funzione
      jsonDataQuadro.id_oggetto = idOpera;
      // #
    }
    // Verifico anche per l'id oggetto istanza
    if (idOggettoIstanza) {
      // Associo id_oggetto_istanza con quello passato in input alla funzione
      jsonDataQuadro.id_oggetto_istanza = idOggettoIstanza;
      // #
    }

    // Ritorno l'oggetto del json data quadro
    return jsonDataQuadro;
    // #
  }

  /**
   * Funzione che gestisce la logica di flusso per il salvataggio del json data a seguito dell'inserimento o della modifica dei dati del quadro.
   * @param idOpera number con l'id oggetto istanza di riferimento d'aggiungere ai dati del json data.
   */
  private saveJsonDataAndGoNext(jsonDataQuadro: any) {
    // salvo il json e vado avanti nello stepper tramite un metodo della classe padre
     super.salvaDatiQuadro(jsonDataQuadro);
  }
}
