/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, Observable, of, throwError } from 'rxjs';
import { catchError, switchMap, tap } from 'rxjs/operators';
import { AmbitoService } from 'src/app/features/ambito/services';
import { EsitoProcedimento, Istanza } from 'src/app/shared/models';
import { AdempimentoService, AuthStoreService, HelpService, IstanzaService, MessageService } from 'src/app/shared/services';
import { ScrivaNoteService } from 'src/app/shared/services/scriva-note/scriva-note.service';
import { ScrivaServerError } from '../../../../core/classes/scriva.classes';
import { AdvancedActionsChiavi, AdvancedActionsMaschere } from '../../enums/advanced-actions.enums';
import { AbstractAdvancedActionInterface } from '../../interfaces/abstract-advanced-action.interface';
import { AdvancedActionService } from '../../services/advanced-action.service';
import { AbstractAdvancedAction } from '../abstract-advanced-action';

@Component({
  selector: 'app-conclusione-procedimento',
  templateUrl: './conclusione-procedimento.component.html',
  styleUrls: ['./conclusione-procedimento.component.scss'],
})
export class ConclusioneProcedimentoComponent
  extends AbstractAdvancedAction
  implements OnInit, AbstractAdvancedActionInterface
{
  chiave: AdvancedActionsChiavi = AdvancedActionsChiavi.CONCLUDI_PROC;
  codMaschera = AdvancedActionsMaschere.CONCLUDI_PROC;

  esiti: void;

  dettaglioForm: FormGroup;
  listEsitoProcedura = []; // todo associa modal

  constructor(
    router: Router,
    fb: FormBuilder,
    istanza: IstanzaService,
    adempimento: AdempimentoService,
    ambito: AmbitoService,
    authStore: AuthStoreService,
    help: HelpService,
    message: MessageService,
    route: ActivatedRoute,
    spinner: NgxSpinnerService,
    scrivaNote: ScrivaNoteService,
    advancedAction: AdvancedActionService
  ) {
    super(
      router,
      fb,
      istanza,
      adempimento,
      ambito,
      authStore,
      help,
      message,
      route,
      spinner,
      scrivaNote,
      advancedAction
    );

    if (!this.idIstanza) {
      this.goToSearchPage();
      return;
    }

    this.scrivaBreadCrumbItems = [
      {
        emitAction: 'goToSearchPage',
        label: 'Ricerca',
        ariaLabel: 'ricerca',
      },
      {
        label: 'Concludi Procedimento',
      },
    ];
  }

  ngOnInit() {
    super.ngOnInit();
    this.buildForm();
    this.loadData();
  }

  buildForm() {
    this.dettaglioForm = this.fb.group({
      esitoProcedura: [null, Validators.required],
    });
  }

  /**
   * metodo che recupera i dati
   */
  loadData() {
    this._spinner.show();
    // le chiamate che devo prendere come base sono quelle del parent per utte le azioni avanzate
    let calls = [...this.loadDataObservables];
    forkJoin(calls).subscribe(
      (res) => {
        this.loadDataSuccess(res);
        this._spinner.hide();
      },
      (err) => {
        this.loadDataError(err);
      }
    );
  }

  /**
   * in caso di successo chiamo la super.loadDataSucces
   * e dopo compio le azioni spercifiche della mia azione avanzata
   * @param res
   */
  loadDataSuccess(res: any[]) {
    super.loadDataSuccess(res);
    this.esiti = this.getEsitiProcedimenti();
  }

  getEsitiProcedimenti() {
    this.adempimentoService
      .getEsitiProcedimentiById(this.istanza?.adempimento.id_adempimento)
      .subscribe((listEsiti) => {
        listEsiti.forEach((el) => {
          if (el.ind_esito === '1' || el.ind_esito === '3') {
            this.listEsitoProcedura.push(el.esito_procedimento);
          }
        });
        return listEsiti;
      });
  }

  /**
   * Funzione che gestisce l'aggiornamento delle informazioni dell'istanza a seguito delle interazioni dell'utente per la conclusione.
   * @author Ismaele Bottelli
   * @date 08/01/2025
   * @jira SCRIVA-1583
   * @notes Con la gestione dei documenti, bisogna aggiornare la data conclusione procedimento dell'istanza a seguito delle operazioni fatte sugli allegati.
   *        A seguito delle correzioni fatte sulla SCRIVA-1503, la data conclusione procedimento è una data che se non viene passata, il servizio la cancella da DB.
   *        Essendo che in questo caso specifico la data conclusione procedimento viene gestita dai servizi sulla base delle informazioni che l'utente inserisce per i documenti,
   *        vado a scaricare le nuove informazioni dell'istanza per recuperare e aggiornare localmente, prima del salvataggio, la data_conclusione_procedimento.
   *        Questa logica viene fatta prima del salvataggio dell'istanza e non dopo l'interazione con i documenti, perché altri utenti nello stesso momento potrebbero aver fatto interventi sui documenti.
   *        In questo modo sappiamo quasi per certo che la data scaricata appena prima dell'aggiornamento dell'istanza sarà consistente.
   *        Ho segnalato i miei dubbi per un discorso di concorrenzialità di modifica dei dati, ma la collega #Simona_Rotolo ha confermato personalmente il flusso in data 08/01/2025 - 12:15.
   */
  saveIstanza() {
    // Vado ad aggiornare le informazioni in sessione dell'istanza con l'esito scelto dall'utente
    this.istanza.esito_procedimento =
      this.dettaglioForm.get('esitoProcedura').value;

    // Attivo lo spinner di caricamento
    this._spinner.show();
    // Prima di aggiornare i dati, vado a sincronizzare alcune informazioni dell'istanza
    this.aggiornaDataConclusioneProcedimentoIstanza$()
      .pipe(
        // In caso ci siano errori con la GET dell'istanza, blocco il flusso e gestisco la segnalazione di errore
        catchError((e: ScrivaServerError) => {
          // Segnalo l'errore
          this.onServiziError(e);
          // Propago l'errore
          return throwError(e);
          // #
        }),
        switchMap((istanza: Istanza) => {
          // Richiamo il salvataggio delle note istanza dopo che l'istanza in sessione è stata aggiornata con le ultime informazioni
          return this.salvaNoteIstanza().pipe(
            switchMap((data: any) => {
              // L'istanza è stata aggiornata anche in questo passaggio in sessione, lancio il prossimo step. La gestione specifica degli errori è interna
              return super.saveIstanza$(this.tipoEventoEnum.CONCL_BO, 'P016');
              // #
            }),
            catchError((e: ScrivaServerError) => {
              // Lancio comunque il salvataggio dell'istanza utilizzando l'oggetto in sessione anche in errore
              return super.saveIstanza$(this.tipoEventoEnum.CONCL_BO, 'P016');
              // #
            })
          );
          // #
        })
      )
      .subscribe({
        next: (res: any) => {
          // Chiudo lo spinner di caricamento
          this._spinner.hide();
          // #
        },
        error: (e: ScrivaServerError) => {
          // Chiudo lo spinner di caricamento
          this._spinner.hide();
          // #
        },
      });
  }

  /**
   * Funzione che gestisce il flusso di gestione per il salvataggio delle note istanza.
   * @returns Observable<any> con il risultato dell'operazione.
   */
  private salvaNoteIstanza(): Observable<any> {
    // Richiamo e ritorno il salvataggio per le note istanza
    return super.saveNoteIstanza().pipe(catchError((err) => of([])));
  }

  onAnnulla() {
    this.dettaglioForm.reset();
  }

  onConferma() {
    if (!this.dettaglioForm.valid) {
      this._message.showMessage('E001', 'searchFormCard', true);
      return;
    }
    this.saveIstanza();
  }

  compareEsito(e1: EsitoProcedimento, e2: EsitoProcedimento) {
    return e1 && e2 && e1.id_esito_procedimento === e2.id_esito_procedimento;
  }

  /**
   * ######################################
   * GESTIONE DATA CONCLUSIONE PROCEDIMENTO
   * ######################################
   */

  // #region "GESTIONE DATA CONCLUSIONE PROCEDIMENTO"

  /**
   * Funzione che gestisce l'aggiornamento delle informazioni dell'istanza per la data conclusione procedimento a seguito della gestione dei documenti.
   * La funzione è pensata per il solo scarico e aggiornamento dati.
   * @returns Observable<Istanza> con l'istanza scaricata.
   * @author Ismaele Bottelli
   * @date 08/01/2025
   * @jira SCRIVA-1583
   * @notes Con la gestione dei file, bisogna aggiornare la data conclusione procedimento dell'istanza a seguito delle operazioni fatte sugli allegati.
   */
  private aggiornaDataConclusioneProcedimentoIstanza$(): Observable<Istanza> {
    // Recupero l'id dell'istanza in sessione
    const idIstanza: number = this.istanza?.id_istanza;

    // Richiamo la funzione di scarico dell'istanza
    return this._istanza.getIstanzaById(idIstanza).pipe(
      tap((istanza: Istanza) => {
        // Dall'istanza scaricata recupero la data_conclusione_procedimento
        const dataConclusioneProcedimento: string =
          istanza?.data_conclusione_procedimento;
        // Aggiorno localmente la data conclusione procedimento sull'oggetto dell'istanza in sessione
        this.istanza.data_conclusione_procedimento =
          dataConclusioneProcedimento;
        // #
      })
    );
  }

  // #endregion "GESTIONE DATA CONCLUSIONE PROCEDIMENTO"
}
