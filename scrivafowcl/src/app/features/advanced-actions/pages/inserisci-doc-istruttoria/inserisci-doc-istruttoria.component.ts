/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ColumnMode } from '@swimlane/ngx-datatable';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AbstractAdvancedAction } from 'src/app/features/advanced-actions/pages/abstract-advanced-action';
import { AmbitoService } from 'src/app/features/ambito/services';
import {
  IFunzionarioAutorizzato,
  Help,
  Istanza,
  NotaIstanza,
} from 'src/app/shared/models';
import {
  AdempimentoService,
  AuthStoreService,
  HelpService,
  IstanzaService,
  MessageService,
} from 'src/app/shared/services';
import { ScrivaNoteService } from 'src/app/shared/services/scriva-note/scriva-note.service';
import {
  IACAAllegatiPubblicati,
  IACAAllegatoCancellato,
  IACAAllegatoModificato,
  IACAFileAggiunto,
} from '../../components/advanced-action-allegati/utilities/advanced-action-allegati.interfaces';
import { AdvancedActionService } from '../../services/advanced-action.service';
import {
  AdvancedActionsChiavi,
  AdvancedActionsMaschere,
} from './../../../advanced-actions/enums/advanced-actions.enums';
import { IDIRegistroAzione } from './utilities/inserisci-doc-istruttoria.classes';
import { IDIModificheUtente } from './utilities/inserisci-doc-istruttoria.enums';
import {
  IAllegatoAzioneRegistro,
  IIDIRegistroModifica,
  INotaAzioneRegistro,
} from './utilities/inserisci-doc-istruttoria.interfaces';
import { ScrivaUtilitiesService } from '../../../../shared/services/scriva-utilities/scriva-utilities.service';

@Component({
  selector: 'app-inserisci-doc-istruttoria',
  templateUrl: './inserisci-doc-istruttoria.component.html',
  styleUrls: ['./inserisci-doc-istruttoria.component.scss'],
})
export class InserisciDocIstruttoriaComponent
  extends AbstractAdvancedAction
  implements OnInit
{
  @ViewChild('dataTemplate') dataTemplate: TemplateRef<any>;
  @ViewChild('flgRiservataTemplate') flgRiservataTemplate: TemplateRef<any>;
  @ViewChild('azioniTemplate') azioniTemplate: TemplateRef<any>;

  chiave: AdvancedActionsChiavi = AdvancedActionsChiavi.INS_DOC_ISTR;
  codMaschera: AdvancedActionsMaschere = AdvancedActionsMaschere.INS_DOC_ISTR;
  componente: string;
  funzionario: IFunzionarioAutorizzato;

  idIstanza: number;
  istanza: Istanza;
  denTitolareIstanza: string;

  helpList: Help[];

  infoForm: FormGroup;
  noteIstanza: NotaIstanza[] = [];
  ColumnMode = ColumnMode;
  noteTableColumns = [];
  flgGestoreConfigurato: any;

  enableBtnProtocolla = false;

  /** IDIRegistroModifica[] con la lista delle azioni compiute dall'utente. Se ci sono azioni specifiche, viene abilitata la conferma per l'azione avanzata. */
  private _registroModifiche: IDIRegistroAzione[] = [];
  /** IDIRegistroModifica[] con la lista delle azioni compiute dall'utente sulle note. La struttura differisce dalle altre azioni, poiché una nota aggiunta può anche essere rimossa. */
  private _registroModificheNote: IDIRegistroAzione[] = [];

  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public fb: FormBuilder,
    public _istanza: IstanzaService,
    public ambitoService: AmbitoService,
    public adempimentoService: AdempimentoService,
    public authStoreService: AuthStoreService,
    public helpService: HelpService,
    public _message: MessageService,
    public _spinner: NgxSpinnerService,
    public modalService: NgbModal,
    public scrivaNoteService: ScrivaNoteService,
    public advancedActionService: AdvancedActionService,
    private _scrivaUtilities: ScrivaUtilitiesService,
  ) {
    super(
      router,
      fb,
      _istanza,
      adempimentoService,
      ambitoService,
      authStoreService,
      helpService,
      _message,
      route,
      _spinner,
      scrivaNoteService,
      advancedActionService
    );

    this.componente = this.authStoreService.getComponente(); // should always be BO
    this.funzionario = this.authStoreService.getFunzionarioAutorizzato();

    this.scrivaBreadCrumbItems = [
      {
        emitAction: 'goToSearchPage',
        label: 'Ricerca',
        ariaLabel: 'ricerca',
      },
      {
        label: 'Inserisci documenti istruttoria',
      },
    ];
  }

  ngOnInit() {
    super.ngOnInit();
    this.buildForm();
    this.loadData();
  }

  buildForm() {
    this.infoForm = this.fb.group({
      notaIstanza: [null, Validators.maxLength(500)],
    });
  }

  loadData() {
    this._spinner.show();

    let calls = [...this.loadDataObservables];
    // const getAC = this.adempimentoService
    //   .getAutoritaCompetenteByIstanza(this.idIstanza)
    //   .pipe(
    //     map((list) =>
    //       list.filter((istComp) => istComp.flg_autorita_principale)
    //     ),
    //     tap((list) => (this.istanzaCompetenzaList = list)),
    //     map((list) => list.map((elem) => elem.competenza_territorio))
    //   );

    // calls.push(getAC);

    forkJoin(calls).subscribe(
      (res: any[]) => {
        this.loadDataSuccess(res);
        this._spinner.hide();
      },
      (err) => {
        this.loadDataError(err);
      }
    );
  }

  loadDataSuccess(res: any[]) {
    super.loadDataSuccess(res);
  }

  onConferma() {
    if (!this.infoForm.valid) {
      this._message.showMessage('E001', 'dettaglioContainer', true);
      return;
    }

    if (this.istanza.data_protocollo_istanza) {
      this.istanza.data_protocollo_istanza += ' 02:00:00';
    }

    const codEvento = this.tipoEventoEnum.INS_DOC_ISTR;
    const codMess = 'P025';

    super
      .saveNoteIstanza()
      .pipe(catchError((err) => of([])))
      .subscribe({
        next: (res: any) => {
          this._spinner.hide();
          if (this.flgGestoreConfigurato) {
            super.generaEvento(codEvento, codMess);
          } else {
            super.saveIstanza(codEvento, codMess);
          }
        },
        error: (err: any) => {
          // Chiudo lo spinner di caricamento
          this._spinner.hide();
          if (err.error?.code) {
            this._message.showMessage(
              err.error.code,
              'dettaglioContainer',
              false
            );
          } else {
            this._message.showMessage('E100', 'dettaglioContainer', true);
          }
        },
      });
  }

  /**
   * ##################################
   * FUNZIONI DI GESTIONE AZIONI UTENTE
   * ##################################
   */

  /**
   * Funzione che gestisce il registro delle azioni utente.
   * @param azione IDIRegistroAzione con l'azione da registrare.
   * @param maxActions number con il numero massimo d'azioni che bisogna tenere in memoria per la gestione del registro. Per default è: 5.
   * @jira SCRIVA-1506
   */
  private registraAzione(azione: IDIRegistroAzione, maxActions: number = 5) {
    // Verifico l'input
    if (!azione) {
      // Manca la configurazione
      return;
    }

    // Aggiungo l'azione alla lista di azioni fatte dall'utente
    this._registroModifiche.push(azione);
    // Verifico se le azioni registrate eccedono la quantità da mantenere in memoria
    if (this._registroModifiche.length > maxActions) {
      // Calcolo la differenza per gestire la quantità di azioni
      const removeActions = this._registroModifiche.length - maxActions;
      // C'è un eccesso di azioni registrate, riduco l'array
      this._registroModifiche = this._registroModifiche.slice(removeActions);
      // #
    }
  }
  
  /**
   * Funzione che gestisce il registro delle azioni utente specifica per le note.
   * Le note sono gestite in maniera specifica.
   * Essendo che i dati sono gestiti solo lato FE, aggiungere e cancellare una nota, se non ci sono altre azioni sulle note, non deve visualizzare il pulsante "CONFERMA".
   * Si usa quindi una struttura specifica in maniera tale da mantenere stabile la logica di verifica dell'utente che ha effettivamente fatto azioni rilevanti.
   * @param azione IDIRegistroAzione con l'azione da registrare.
   * @jira SCRIVA-1506
   */
  private registraAzioneNota(azione: IDIRegistroAzione) {
    // Verifico l'input
    if (!azione) {
      // Manca la configurazione
      return;
    }

    // Aggiungo l'azione alla lista di azioni fatte dall'utente
    this._registroModificheNote.push(azione);
  }

  // #region "FILES"

  /**
   * Funzione che intercetta l'evento di singolo file caricato.
   * @param datiFile IACAFileAggiunto con le informazioni generate per il file.
   * @jira SCRIVA-1506
   */
  onSingleFileUploaded(datiFile: IACAFileAggiunto) {
    // Definisco l'evento di gestione
    const evento = IDIModificheUtente.documentoSingoloAggiunto;
    // Creo l'oggetto con le informazioni per l'azione
    const datiAzione: IIDIRegistroModifica = {
      azione: evento,
      datiFile: datiFile,
    };
    // Creo un oggetto azione per tracciare l'utente
    const azione = new IDIRegistroAzione(datiAzione);
    // Aggiungo l'azione alla lista di azioni fatte dall'utente
    this.registraAzione(azione);
  }

  /**
   * Funzione che intercetta l'evento di file multiplo caricato.
   * @param datiFile IACAFileAggiunto con le informazioni generate per il file.
   * @jira SCRIVA-1506
   */
  onMultiFileUploaded(datiFile: IACAFileAggiunto) {
    // Imposto un flag che mi indichi se devo aggiornare dati esistenti
    let aggiornaFile: boolean = false;

    // Ciclo le azioni utente per verificare se è già stato inserito un blocco file
    for (let i = 0; i < this._registroModifiche?.length; i++) {
      // Recupero l'azione dal registro
      const azioneRegistro = this._registroModifiche[i];

      // Verifico se l'azione contiene le informazioni dei file multipli
      const sameoBloccoFile: boolean =
        azioneRegistro.isFileInBloccoFiles(datiFile);

      // Controllo il risultato
      if (sameoBloccoFile) {
        // Il blocco di file multipli è il medesimo, aggiungo il dato alla lista esistente
        this._registroModifiche[i].aggiungiFileMultiplo(datiFile);
        // Non devo creare una nuova azione
        aggiornaFile = true;
        // Interrompo il ciclo
        break;
        // #
      }
    }

    // Verifico se è avvenuto un aggiornamento di un'azione esistente
    if (!aggiornaFile) {
      // Definisco l'evento di gestione
      const evento = IDIModificheUtente.documentoMultiploAggiunto;
      // Creo l'oggetto con le informazioni per l'azione
      const datiAzione: IIDIRegistroModifica = {
        azione: evento,
        datiFiles: [datiFile],
      };
      // Creo un oggetto azione per tracciare l'utente
      const azione = new IDIRegistroAzione(datiAzione);
      // Aggiungo l'azione alla lista di azioni fatte dall'utente
      this.registraAzione(azione);
      // #
    }
  }

  // #endregion "FILES"

  // #region "ALLEGATI"

  /**
   * Funzione che gestisce la registrazione delle azioni utente per i dati degli allegati.
   * @param datiAllegato IAllegatoAzioneRegistro con i dati dell'allegato.
   * @param evento IDIModificheUtente con l'evento da registrare.
   */
  private registraAzioneAllegato(
    datiAllegato: IAllegatoAzioneRegistro,
    evento: IDIModificheUtente
  ) {
    // Creo l'oggetto con le informazioni per l'azione
    const datiAzione: IIDIRegistroModifica = {
      azione: evento,
      datiAllegato: datiAllegato,
    };
    // Creo un oggetto azione per tracciare l'utente
    const azione = new IDIRegistroAzione(datiAzione);
    // Aggiungo l'azione alla lista di azioni fatte dall'utente
    this.registraAzione(azione);
  }

  /**
   * Funzione che intercetta l'evento di allegato aggiornato.
   * @param datiAllegato IACAAllegatoModificato con le informazioni dell'allegato modificato.
   */
  onAllegatoUpdated(datiAllegato: IACAAllegatoModificato) {
    // Definisco l'evento di gestione
    const evento = IDIModificheUtente.allegatoModificato;
    // Aggiungo l'azione alla lista di azioni fatte dall'utente
    this.registraAzioneAllegato(datiAllegato, evento);
  }

  /**
   * Funzione che intercetta l'evento di allegato cancellato.
   * @param datiAllegato IACAAllegatoCancellato con le informazioni dell'allegato cancellato.
   */
  onAllegatoDeleted(datiAllegato: IACAAllegatoCancellato) {
    // Definisco l'evento di gestione
    const evento = IDIModificheUtente.allegatoCancellato;
    // Aggiungo l'azione alla lista di azioni fatte dall'utente
    this.registraAzioneAllegato(datiAllegato, evento);
  }

  // #endregion "ALLEGATI"

  // #region "PUBBLICAZIONE/ANNULLAMENTO ALLEGATI"

  /**
   * Funzione che intercetta l'evento di allegati pubblicati/annullati.
   * @param pubblicazione IACAAllegatiPubblicati con le informazioni generate dalla procedura.
   */
  onAllegatiPubblicati(pubblicazione: IACAAllegatiPubblicati) {
    // Definisco l'evento di gestione
    const evento = IDIModificheUtente.pubblicazioneAllegati;
    // Creo l'oggetto con le informazioni per l'azione
    const datiAzione: IIDIRegistroModifica = {
      azione: evento,
      datiPubblicazioneAllegati: pubblicazione,
    };
    // Creo un oggetto azione per tracciare l'utente
    const azione = new IDIRegistroAzione(datiAzione);
    // Aggiungo l'azione alla lista di azioni fatte dall'utente
    this.registraAzione(azione);
  }

  // #endregion "PUBBLICAZIONE/ANNULLAMENTO ALLEGATI"

  // #region "AGGIUNTA NOTA"

  /**
   * Funzione che intercetta l'evento di nota inserita.
   * @param nota NotaIstanza con le informazioni della nota.
   */
  onNotaInserted(nota: NotaIstanza) {
    // Definisco l'evento di gestione
    const evento = IDIModificheUtente.notaAggiunta;
    // Creo l'oggetto con le informazioni per gestire l'azione
    const datiNota: INotaAzioneRegistro = {
      id: this._scrivaUtilities.generateRandomId(),
      notaIstanza: nota,
    }
    // Creo l'oggetto con le informazioni per l'azione
    const datiAzione: IIDIRegistroModifica = {
      azione: evento,
      datiNota: datiNota,
    };
    // Creo un oggetto azione per tracciare l'utente
    const azione = new IDIRegistroAzione(datiAzione);
    // Aggiungo l'azione alla lista di azioni fatte dall'utente
    this.registraAzioneNota(azione);
  }

  /**
   * Funzione che intercetta l'evento di nota inserita.
   * Per la cancellazione delle note non si traccia l'azione, ma si va a rimuovere l'azione di aggiunta nota.
   * @param nota NotaIstanza con le informazioni della nota.
   */
  onNotaDeleted(nota: NotaIstanza) {
    // Ciclo le azioni utente per trovare la nota cancellata
    for (let i = 0; i < this._registroModificheNote?.length; i++) {
      // Recupero l'azione dal registro
      const azioneRegistro = this._registroModificheNote[i];

      // Verifico se l'azione contiene le informazioni della nota
      const isNotaInserita: boolean = azioneRegistro.isNotaInserita(nota);

      // Controllo il risultato
      if (isNotaInserita) {
        // La nota è da cancellare
        this._registroModificheNote.splice(i, 1);
        // Interrompo il ciclo
        break;
        // #
      }
    }
  }

  // #endregion "AGGIUNTA NOTA"

  /**
   * Getter che verifica se l'utente ha effettuato modifiche signifigicative in pagina.
   * @returns boolean con il risultato del check.
   */
  get registriModificheAttivi(): boolean {
    // Verifico se i registri delle azioni hanno almeno un azione registrata
    const registroMod = this._registroModifiche.length > 0;
    const registroModNote = this._registroModificheNote.length > 0;
    // Verifico se uno dei registri ha azioni
    return registroMod || registroModNote;
    // #
  }
}
