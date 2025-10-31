/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { DatePipe } from '@angular/common';
import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ColumnMode, SelectionType } from '@swimlane/ngx-datatable';
import * as moment from 'moment';
import { NgxSpinnerService } from 'ngx-spinner';
import { from, Observable, of } from 'rxjs';
import { catchError, concatMap, map, reduce } from 'rxjs/operators';
import { AutoUnsubscribe } from 'src/app/core/components';
import { Allegato, TreeElement } from 'src/app/features/ambito/models';
import { AllegatiService } from 'src/app/features/ambito/services';
import { AllegatiIntegrazioniService } from 'src/app/features/ambito/services/allegati-integrazioni.service';
import { AllegatiTreeService } from 'src/app/features/ambito/services/allegati-tree.service';
import { Help, Istanza } from 'src/app/shared/models';
import {
  AuthStoreService,
  HelpService,
  IstanzaService,
  MessageService,
} from 'src/app/shared/services';
import { ScrivaServerError } from '../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../core/enums/scriva-codes-messages.enums';
import { ScrivaInfoLevels } from '../../enums/scriva-utilities/scriva-utilities.enums';
import {
  IHandleBlobEvents,
  IHandleBlobRead,
  INgxDataTableRowSelected,
} from '../../interfaces/scriva-utilities.interfaces';
import { IntegrazioneIstanza } from '../../models/istanza/integrazione-istanza.model';
import { ScrivaAlertService } from '../../services/scriva-alert/scriva-alert.service';
import { IAlertConfigsUpdate } from '../../services/scriva-alert/utilities/scriva-alert.interfaces';
import { ScrivaUtilitiesService } from '../../services/scriva-utilities/scriva-utilities.service';
import { AttoreGestioneIstanzaEnum } from '../../utils';
import { ScrivaAlertConfigs } from '../scriva-alert/utilities/scriva-alert.classes';
import { TScrivaAlertType } from '../scriva-alert/utilities/scriva-alert.types';
import {
  CardRiepilogoAllegatiConsts,
  UUID_INDEX_BIN_PREFIX,
} from './utilities/card-riepilogo-allegati.consts';
import { CardRiepilogoAllegatiActsion } from './utilities/card-riepilogo-allegati.enums';
import {
  ICRAPubblicazioneAllegati,
  ICRATableColumn,
} from './utilities/card-riepilogo-allegati.interfaces';

@Component({
  selector: 'app-card-riepilogo-allegati',
  templateUrl: './card-riepilogo-allegati.component.html',
  styleUrls: ['./card-riepilogo-allegati.component.scss'],
})
export class CardRiepilogoAllegatiComponent
  extends AutoUnsubscribe
  implements OnInit, OnChanges
{
  /** CardRiepilogoAllegatiConsts con le costanti del componente. */
  CRA_C = new CardRiepilogoAllegatiConsts();

  @Input() istanza: Istanza;
  @Input() allegati: Allegato[];
  @Input() oggAppBtnPubblica: any = null;
  @Input() oggAppBtnProtocolla: any = null;
  @Input() codQuadro: string;
  @Input() isFrontOffice: boolean = false;
  @Input() isAzioneAvanzata: boolean = false;
  @Input() readonlyAllegati: boolean = false;

  /** AttoreGestioneIstanzaEnum con l'indicazione di gestione per l'attore gestione istanza. */
  @Input() attoreGestioneIstanza = AttoreGestioneIstanzaEnum.WRITE as string;

  /** Output che definisce l'evento passato verso alto */
  @Output() emit = new EventEmitter<{ action; args? }>();
  /** Output Allegato con le informazioni allegato cancellato dall'utente. */
  @Output() onAllegatoDeleted$ = new EventEmitter<Allegato>();
  /** Output ICRAPubblicazioneAllegati con le informazioni per allegati pubblicati dall'utente. */
  @Output() onAllegatiPubblicati$ =
    new EventEmitter<ICRAPubblicazioneAllegati>();

  // per ritornare alla vecchia visualizzaione senza
  // alberatura settare a false
  public isTreeActive: boolean = true;
  rows: TreeElement[];
  gestioneEnum = AttoreGestioneIstanzaEnum;

  /**
   * string con l'indicazione per la gestione dei dati del componente.
   * @author Ismaele Bottelli
   * @date 27/11/2024 15:30
   * @jira SCRIVA-1564
   * @notes Potrei sbagliarmi, ma qua viene sempre definito l'attore gestione istanza in WRITE, ma questo collide con l'idea di abilitare le funzioni utente in base alla gestione.
   *        E' necessario testare questa parte per assicurarsi la corretta gestione delle abilitazioni.
   *        Idealmente questa variabile, come in altre parti, dovrebbe prevedere il set tramite [Input] oppure il recupero dalla sessione dati (vedi: allegati.component.ts).
   */
  // attoreGestioneIstanza = AttoreGestioneIstanzaEnum.WRITE as string;

  // rif: SCRIVA-1380
  private _hasAlgPerfezionamento: boolean = false;
  private _hasAlgIntegrazione: boolean = false;

  selected: TreeElement[] = [];
  columns = [];

  // logMyColumn = (o: any) => { debugger; return '' };

  /** (row: any) => INgxDataTableRowSelected; che definisce le logiche per la gestione della colorazione di una riga di tabella. */
  handleRowStyles: (row: any) => INgxDataTableRowSelected;

  /** ICRATableColumn[] con le configurazioni per le colonne per la generazione del file csv degli allegati. */
  columnsCsv: ICRATableColumn[] = [];

  ColumnMode = ColumnMode;

  showScrollbar: 'true' | 'false' = null;
  selectionType = null;

  // SCRIVA-1398
  helpList: Help[];
  helpRiepilogo: Help;

  @ViewChild('checkboxHeaderTemplate') checkboxHeaderTemplate: TemplateRef<any>;
  @ViewChild('checkboxColTemplate') checkboxColTemplate: TemplateRef<any>;

  @ViewChild('nomeDocumentoTemplate') nomeDocumentoTemplate: TemplateRef<any>;
  @ViewChild('categoriaTemplate') categoriaTemplate: TemplateRef<any>;
  @ViewChild('accessoTemplate') accessoTemplate: TemplateRef<any>;
  @ViewChild('codiceTemplate') codiceTemplate: TemplateRef<any>;
  @ViewChild('dataUploadTemplate') dataUploadTemplate: TemplateRef<any>;
  @ViewChild('dataPubblicazioneTemplate')
  dataPubblicazioneTemplate: TemplateRef<any>;
  @ViewChild('dimensioneTemplate') dimensioneTemplate: TemplateRef<any>;
  @ViewChild('baseActionsTemplate') baseActionsTemplate: TemplateRef<any>;

  /** ScrivaAlertConfigs contenente le configurazioni per la gestione dell'alert utente. */
  alertConfigs = new ScrivaAlertConfigs({ allowAlertClose: true });

  constructor(
    public activeModal: NgbActiveModal,
    private allegatiService: AllegatiService,
    private allegatiIntegrazioniService: AllegatiIntegrazioniService,
    private allegatiTreeService: AllegatiTreeService,
    private authStoreService: AuthStoreService,
    private datePipe: DatePipe,
    private helpService: HelpService,
    private istanzaService: IstanzaService,
    private messageService: MessageService,
    private spinner: NgxSpinnerService,
    private _scrivaAlert: ScrivaAlertService,
    private _scrivaUtilities: ScrivaUtilitiesService
  ) {
    super();

    // Lancio la funzione di setup
    this.setupComponente();
  }

  ngOnInit() {
    // Lancio la funzione di init del componente
    this.initComponente();
  }

  /**
   * NgOnChanges.
   * @param changes
   */
  ngOnChanges(changes: SimpleChanges): void {
    // Recupero il possibile oggetto di changes: preferenzaNotifica
    const pnChanges = changes.allegati;
    const pnChangesA = changes.readonlyAllegati;
    // Verifico se è stato aggiornato: allegati
    if (pnChanges && !pnChanges.firstChange) {
      // Lancio l'aggiornamento del tree
      if (this.isTreeActive) {
        this.rows = this.allegatiTreeService.makeTree(this.allegati);
        this.makeActions();
      }
    }
    if (pnChangesA && !pnChangesA.firstChange) {
      // allineo le azioni della tabellina
      this.makeActions();
    }
  }

  /** start prese da allegati  */
  ngAfterViewInit(): void {
    this.columns = [
      {
        name: this.CRA_C.LABEL_DOCUMENTO,
        cellTemplate: this.nomeDocumentoTemplate,
        prop: 'nome_allegato',
        sortable: false,
      },
      {
        name: this.CRA_C.LABEL_CATEGORIATIPOLOGIA,
        cellTemplate: this.categoriaTemplate,
        minWidth: 80,
        prop: 'des_categoria_allegato',
        sortable: false,
      },
      {
        name: this.CRA_C.LABEL_ACCESSO,
        cellTemplate: this.accessoTemplate,
        prop: 'flg_riservato',
        sortable: false,
      },
      {
        name: this.CRA_C.LABEL_CODICE_ELABORATO,
        cellTemplate: this.codiceTemplate,
        prop: 'cod_allegato',
        sortable: false,
      },
      {
        name: this.CRA_C.LABEL_DATA_CARICAMENTO,
        cellTemplate: this.dataUploadTemplate,
        prop: 'data_upload',
        sortable: false,
      },
      {
        name: this.CRA_C.LABEL_DATA_PUBBLICAZIONE,
        cellTemplate: this.dataPubblicazioneTemplate,
        prop: 'data_pubblicazione',
      },
      {
        name: this.CRA_C.LABEL_DIMENSIONE,
        cellTemplate: this.dimensioneTemplate,
        prop: 'dimensione_upload',
        sortable: false,
      },
      {
        name: this.CRA_C.LABEL_AZIONI,
        cellTemplate: this.baseActionsTemplate,
        sortable: false,
      },
    ];

    if (this.oggAppBtnPubblica) {
      // if (this.oggAppPulsanti) {
      // if (true) {
      const cbxColumn = {
        name: '',
        sortable: false,
        canAutoResize: false,
        draggable: false,
        resizable: false,
        width: 140,
        headerTemplate: this.checkboxHeaderTemplate,
        cellTemplate: this.checkboxColTemplate,
        cellClass: 'checkbox-cell',
      };

      this.columns.splice(0, 0, cbxColumn);
    }
  }

  /**
   * #################
   * FUNZIONI DI SETUP
   * #################
   */

  /**
   * Funzione di setup per le logiche del componente.
   */
  private setupComponente() {
    // Richiamo la funzione di setup per l'inizializzazione delle logiche per la tabella allegati
    this.setupFunzioniTabella();
    // Richiamo la funzione di setup per la gestione delle configurazioni della tabella allegati csv
    this.setupTabellaAllegatiCsv();
  }

  /**
   * Funzione di setup che definisce le logiche per la gestione della tabella allegati.
   */
  private setupFunzioniTabella() {
    // Definisco la funzione per il coloramento della riga di tabella
    this.handleRowStyles = (row: any) => {
      // Richiamo la logica di verifica
      return this.checkRowAllegatiSelected(row, this.selected);
      // #
    };
  }

  /**
   * Funzione di setup che gestisce le configurazioni per la tabella allegati con csv.
   */
  private setupTabellaAllegatiCsv() {
    // Vado a gestire le configurazioni con le informazioni presenti nel componente
    this.CRA_C.CSV_DOCUMENTO.getter = {
      transform: (value: Allegato) => {
        return !value?.id_allegato_istanza_padre
          ? value.nome_allegato
          : this.getNomeAllegato(value.id_allegato_istanza_padre);
      },
    };
  }

  /**
   * ################
   * FUNZIONI DI INIT
   * ################
   */

  /**
   * Funzione di init per le logiche del componente.
   */
  private initComponente() {
    // Richiamo la funzione di init miscellanea (vecchio codice, da rifattorizzare)
    this.initMiscData();
    // Richiamo la funzione di init per la gestione delle configurazioni della tabella allegati csv
    this.initTabellaAllegatiCsv();
  }

  /**
   * Funzione di init con le configurazioni miscellanee del componente (da rifattorizzare).
   */
  private initMiscData() {
    const componente = this.authStoreService.getComponente();

    this.selectionType = SelectionType.checkbox;

    // rif: SCRIVA-1380
    this.hasAlgPerfezionamento = !!this.istanzaService.getIstanzaOggettoApp(
      this.istanza,
      'alg_perfezionamento'
    );
    this.hasAlgIntegrazione = !!this.istanzaService.getIstanzaOggettoApp(
      this.istanza,
      'alg_integrazione'
    );

    if (this.oggAppBtnPubblica) {
      this.showScrollbar = 'true';
    }
    if (this.isTreeActive) {
      this.rows = this.allegatiTreeService.makeTree(this.allegati);
      // rif: SCRIVA-1379, SCRIVA-1380
      this.makeActions();
    }

    // SCRIVA-1398
    const getHelpList = this.helpService.getHelpByChiave(
      `${componente}.${this.istanza.adempimento.tipo_adempimento.cod_tipo_adempimento}.${this.istanza.adempimento.cod_adempimento}.QDR_ALLEGATO`
    );

    getHelpList.subscribe((helpList: Help[]) => {
      this.helpList = helpList;
      this.helpRiepilogo = this.helpList?.find(
        (help) =>
          help.tipo_help.cod_tipo_help === 'BNR' &&
          help.chiave_help.includes('riepilogo_allegati')
      );
    });

    // Aggiorno la configurazione per la gestione del FO
    this.CRA_C.updateIsFOConfigs(this.isFrontOffice);
  }

  /**
   * Funzione di init che gestisce le configurazioni per la tabella allegati con csv.
   */
  private initTabellaAllegatiCsv() {
    // Verifico se mi trovo in modalità FO o BO
    if (this.isFrontOffice) {
      // Definisco la configurazione specifica per il frontoffice
      this.columnsCsv = this.CRA_C.allegatiCsvColumnFO;
      // #
    } else {
      // Definisco la configurazione specifica per il backoffice
      this.columnsCsv = this.CRA_C.allegatiCsvColumnBO;
      // #
    }
  }

  /**
   * ##############
   * ALTRE FUNZIONI
   * ##############
   */

  getNomeAllegato(idAllegato: number) {
    let nome = '';
    this.rows?.forEach((treeElement) => {
      if (
        treeElement.allegato &&
        treeElement.allegato.id_allegato_istanza === idAllegato
      ) {
        nome = treeElement.allegato.nome_allegato;
      }
    });
    return nome;
  }

  /**
   *
   * @param byte dimensione file in byte
   * @returns Questa funzione converte il numero di byte in megabyte dividendo per 1024^2 (il numero di byte in un megabyte).
   */
  pipeMb(byte: number) {
    if (byte) {
      let mb = byte / 1048576;
      // toFixed(3) viene utilizzato per arrotondare il risultato a tre decimali
      return mb.toFixed(2);
    }
    return '';
  }

  /**
   * rendo visbibili e abilito disabilito edit delete e deletelogic
   */
  makeActions() {
    this.rows?.forEach((treeElement) => {
      if (treeElement.allegato) {
        treeElement.actions = {
          editVisible: this.isEditVisible(treeElement.allegato),
          deleteVisible: this.isDeleteVisible(treeElement.allegato),
          deletelogicVisible: this.isDeleteLogicVisible(treeElement.allegato),
        };
        if (treeElement.actions.editVisible) {
          treeElement.actions.edit = this.isEditAllowed(treeElement.allegato);
        }
        if (treeElement.actions.deleteVisible) {
          treeElement.actions.delete = this.isDeleteAllowed(
            treeElement.allegato
          );
        }
        if (treeElement.actions.deletelogicVisible) {
          treeElement.actions.deletelogic = this.isDeleteLogicAllowed(
            treeElement.allegato
          );
        }
      }
    });
  }

  /**
   * Azione edit è visibile
   * @param row Allegato
   * @returns boolean
   */
  isEditVisible(row: Allegato): boolean {
    // SCRIVA-1379 flg_sistema
    if (row.tipologia_allegato?.flg_sistema) {
      return false;
    }

    return true;
  }

  /**
   * Azione delete è visibile
   * @param row Allegato
   * @returns boolean
   */
  isDeleteVisible(row: Allegato): boolean {
    // SCRIVA-1379 flg_sistema
    if (row.tipologia_allegato?.flg_sistema) {
      return false;
    }

    return true;
  }

  /**
   * Azione delete logic è visibile
   * @param allegato Allegato
   * @returns boolean
   */
  isDeleteLogicVisible(allegato: Allegato): boolean {
    // SCRIVA-1379 flg_sistema
    if (allegato.tipologia_allegato?.flg_sistema) {
      return false;
    }

    // Caso FO con id_funzionario non può cancellare
    if (allegato.id_funzionario && this.isFrontOffice) {
      return false;
    }

    // la cancellazione logica si visualizza solo nei casi di perferzionamento integrazione
    // rif: SCRIVA-1380
    if (
      this.isAzioneAvanzata ||
      (!this.hasAlgPerfezionamento && !this.hasAlgIntegrazione)
    ) {
      return false;
    }

    //  se ha oggetto applicativo algoritimo controllo nel servizio
    if (
      !this.isAzioneAvanzata &&
      (this.hasAlgPerfezionamento || this.hasAlgIntegrazione) &&
      this.allegatiIntegrazioniService.disableAllegato(
        this.istanza.id_istanza,
        allegato,
        'deletelogic'
      )
    ) {
      return false;
    }

    return true;
  }

  /**
   * Azione edit è consentita
   * @param row Allegato
   * @returns boolean
   */
  isEditAllowed(row: Allegato): boolean {
    // non consento la cancellazione allegati di sistema
    // per via di flg_sistema o cod_categoria_allegato SYS
    // oppure se gli allegati sono in readonly
    if (row.flg_cancellato) {
      return false;
    }

    // devo poter protocollare
    if (this.oggAppBtnProtocolla) {
      return true;
    }

    if (this.readonlyAllegati) {
      return false;
    }

    /* Caso BO con id_istanza_attore non può cancellare
    if (row.id_istanza_attore && !this.isFrontOffice) {
      return false;
    }*/

    // Caso FO con id_funzionario non può editare
    if (row.id_funzionario && this.isFrontOffice) {
      return false;
    }

    //  se ha oggetto applicativo algoritimo controllo nel servizio
    // rif: SCRIVA-1380
    if (
      !this.isAzioneAvanzata &&
      (this.hasAlgPerfezionamento || this.hasAlgIntegrazione) &&
      this.allegatiIntegrazioniService.disableAllegato(
        this.istanza.id_istanza,
        row,
        'edit'
      )
    ) {
      return false;
    }

    // consento la cancellazione allegati nel caso attoreGestioneIstanza write
    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      return true;
    }

    if (this.attoreGestioneIstanza === this.codQuadro) {
      return true;
    }

    return false;
  }

  /**
   * Azione delete è consentita
   * @param row Allegato
   * @returns boolean
   */
  isDeleteAllowed(row: Allegato): boolean {
    // non consento la cancellazione allegati di sistema
    // per via di flg_sistema o cod_categoria_allegato SYS
    // oppure se gli allegati sono in readonly
    if (this.readonlyAllegati || row.flg_cancellato) {
      return false;
    }

    // Caso BO con id_istanza_attore non può cancellare
    if (row.id_istanza_attore && !this.isFrontOffice) {
      return false;
    }

    // Caso FO con id_funzionario non può cancellare
    if (row.id_funzionario && this.isFrontOffice) {
      return false;
    }

    //  se ha oggetto applicativo algoritimo controllo nel servizio
    // rif: SCRIVA-1380
    if (
      !this.isAzioneAvanzata &&
      (this.hasAlgPerfezionamento || this.hasAlgIntegrazione) &&
      this.allegatiIntegrazioniService.disableAllegato(
        this.istanza.id_istanza,
        row,
        'delete'
      )
    ) {
      return false;
    }

    // consento la cancellazione allegati nel caso attoreGestioneIstanza write
    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      return true;
    }

    if (this.attoreGestioneIstanza === this.codQuadro) {
      return true;
    }

    return false;
  }

  /**
   * Azione deletelogi è consentita
   * @param row Allegato
   * @returns boolean
   */
  isDeleteLogicAllowed(row: Allegato): boolean {
    // non consento la cancellazione allegati di sistema
    if (this.readonlyAllegati || row.flg_cancellato) {
      return false;
    }

    // consento la cancellazione allegati nel caso attoreGestioneIstanza write
    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      return true;
    }

    if (this.attoreGestioneIstanza === this.codQuadro) {
      return true;
    }

    return false;
  }

  editRow(row: Allegato) {
    this.emit.next({
      action: CardRiepilogoAllegatiActsion.modificaAllegato,
      args: row,
    });
  }

  /**
   * Funzione invocata al click sull'azione di tabella allegat: dettaglio.
   * @param allegato Allegato con le informazioni dell'allegato della riga.
   */
  detailRow(allegato: Allegato) {
    // Definisco l'action specifica
    const action = CardRiepilogoAllegatiActsion.visualizzaAllegato;
    // Definisco le informazioni da passare come payload
    const args = allegato;

    // Richiamo l'eventemitter di output
    this.emit.next({ action, args });
    // #
  }

  exportCSV() {
    // Recupero la lista di tutte le righe della tabella
    const rows: TreeElement[] = this.rows;
    // Vado a recuperare gli allegati forzando la struttura ad albero come parametro
    const allegati: Allegato[] = this.getAllegatiFromRows(rows, true);

    // recupero la lista delle colonne dalla configurazione csv
    const columns = [...this.columnsCsv];
    // Definisco il nome del file da salvare
    const codIstanza: string = this.istanza.cod_istanza;
    const today: string = new Date().toLocaleDateString();
    const fileName: string = `Elenco_Allegati_${codIstanza}_${today}`;

    // Lancio la creazione del csv dati
    this.allegatiService.createCSV(allegati, columns, fileName);
  }

  /**
   * Funzione di esportazione dati per gli allegati.
   * La funzione andrà a creare uno zip e scaricherà gli allegati presenti come righe di tabella.
   */
  exportZIP() {
    // Avvio lo spinner di caricamento
    this.spinner.show();

    // Recupero la lista degli allegati selezionati tramite funzione
    let allegati: Allegato[] = this.getRowSelected();
    // Definisco le informazioni per la chiamata
    const idIstanza: number = this.istanza.id_istanza;
    let codiciAllegato: string[];
    codiciAllegato = allegati.map((a: Allegato) => a.cod_allegato);

    // Richiamo la funzione di download degli allegati
    this.allegatiService
      .downloadAllegatiSelezionati(idIstanza, codiciAllegato)
      .subscribe({
        next: () => {
          // Resetto la selezione degli elementi della tabella
          this.selected = [];
          // Chiudo lo spinner
          this.spinner.hide();
          // #
        },
        error: (err) => {
          // SCRIVA 1398 messaggio di errore
          if (err.error) {
            // Gestisco l'errore come blob
            this.onServiziErrorBlob(err.error);
            // #
          } else {
            // Gestisco un errore generico
            this.onServiziError(null);
          }
          // Chiudo lo spinner
          this.spinner.hide();
        },
      });
  }

  downloadRow(row: Allegato) {
    this.spinner.show();

    this.allegatiService.getAllegatoByUuid(row.uuid_index).subscribe(
      (response) => {
        const nameArr = row.nome_allegato.split('.');
        const blob = new Blob([response], {
          type: 'application/' + nameArr[nameArr.length - 1],
        });
        const url = window.URL.createObjectURL(blob);
        const fileName = row.nome_allegato;

        const a = document.createElement('a');
        a.href = url;
        a.download = fileName;
        a.click();
        window.URL.revokeObjectURL(url);
        a.remove();
        this.spinner.hide();
      },
      (err) => {
        if (err.error?.code) {
          this.messageService.showMessage(
            err.error.code,
            'containerUploadDocumenti',
            false
          );
        } else {
          this.messageService.showMessage(
            'E100',
            'containerUploadDocumenti',
            true
          );
        }
      }
    );
  }

  onDelete(row: Allegato) {
    this.messageService.showConfirmation({
      title: 'Attenzione',
      codMess: 'A030',
      buttons: [
        {
          label: 'ANNULLA',
          type: 'btn-link',
          callback: () => {},
        },
        {
          label: 'CONFERMA',
          type: 'btn-primary',
          callback: () => {
            this.deleteRow(row);
          },
        },
      ],
    });
  }

  /**
   * Funzione di comodo che gestisce il flusso a seguito della cancellazione di una riga.
   * @param allegatoDel Allegato con l'oggetto cancellato.
   */
  private onDeleteRow(allegatoDel: Allegato) {
    // Emetto l'evento di ricarica degli allegati perché la riga è stata cancellata
    this.emit.next({ action: 'reloadAllegati' });
    // Emetto un evento specifico per informare che la riga è stata cancellata.
    this.onAllegatoDeleted$.emit(allegatoDel);
    // #
  }

  deleteRow(allegatoRow: Allegato) {
    this.spinner.show();
    this.allegatiService
      .deleteAllegatoByUuid(allegatoRow.uuid_index)
      .subscribe({
        next: (res: any) => {
          // Variabili di comodo
          const isAzAvanzata = this.isAzioneAvanzata;
          const isAlgoPerfOrInte =
            this.hasAlgPerfezionamento || this.hasAlgIntegrazione;

          // Verifico le condizioni per gestire il flusso di gestione dell'allegato cancellato
          if (!isAzAvanzata && isAlgoPerfOrInte) {
            this.allegatiIntegrazioniService
              .callbackDeleteAllegati(allegatoRow, this.istanza.id_istanza)
              .subscribe({
                next: (integrazioneIstanza: IntegrazioneIstanza) => {
                  // Gestisco il flusso
                  this.onDeleteRow(allegatoRow);
                  // #
                },
              });
          } else {
            // Gestisco il flusso
            this.onDeleteRow(allegatoRow);
            // #
          }

          // #
        },
        error: (err) => {
          if (err.error?.code) {
            this.messageService.showMessage(
              err.error.code,
              'containerUploadDocumenti',
              false
            );
          } else {
            this.messageService.showMessage(
              'E100',
              'containerUploadDocumenti',
              true
            );
          }
        },
      });
  }

  onLogicDelete(row: Allegato) {
    this.messageService.showConfirmation({
      title: 'Attenzione',
      codMess: 'A010',
      buttons: [
        {
          label: 'ANNULLA',
          type: 'btn-link',
          callback: () => {},
        },
        {
          label: 'CONFERMA',
          type: 'btn-primary',
          callback: () => {
            this.logicDeleteRow(row);
          },
        },
      ],
    });
  }

  logicDeleteRow(row: Allegato) {
    const request: Partial<Allegato> = {
      ...row,
      flg_cancellato: true,
      data_cancellazione: this._convertDateForm4BE(
        new Date().toISOString().slice(0, 10)
      ),
    };

    this.allegatiService.updateAllegati(request).subscribe(
      (res) => {
        this.emit.next({ action: 'reloadAllegati' });
      },
      (err) => {
        if (err.error?.code) {
          this.messageService.showMessage(
            err.error.code,
            'containerUploadDocumenti',
            false
          );
        } else {
          this.messageService.showMessage(
            'E100',
            'containerUploadDocumenti',
            true
          );
        }
      }
    );
  }

  /**
   * Funzione collegata all'evento di selezione della tabella.
   * Questo evento viene attivato per qualunque tipo di operazione di selezione, che sia per tutte le righe o che sia per la singola riga.
   * Andando ad aggiornare la variabile di componente "selected", automaticamente la libreria della tabella andrà a selezionare e gestire le righe dentro l'array.
   * @param event { selected: TreeElement[] } con le informazioni emesse dall'evento.
   */
  onSelect(event: { selected: TreeElement[] }) {
    // Verifico l'input
    if (!event) {
      // Non ci sono informazioni
      return;
    }

    // Recupero la lista di righe selezionate della tabella
    const rowsSelected: TreeElement[] = [...event.selected];
    // Updating this.selected is handled automatically by ngx-datatable
    this.selected = rowsSelected;
  }

  get isSelectedRows(): boolean {
    return this.selected?.length > 0;
  }

  // rif: SCRIVA-1380
  set hasAlgPerfezionamento(v: boolean) {
    this._hasAlgPerfezionamento = v;
  }

  set hasAlgIntegrazione(v: boolean) {
    this._hasAlgIntegrazione = v;
  }

  get hasAlgPerfezionamento(): boolean {
    return this._hasAlgPerfezionamento;
  }

  get hasAlgIntegrazione(): boolean {
    return this._hasAlgIntegrazione;
  }

  annullaPubblicaDocumenti() {
    this._pubblicaDocumenti(false);
  }

  pubblicaDocumenti() {
    this._pubblicaDocumenti(true);
  }

  /**
   * In caso di Tree attivo esapndo e collaso alberatura
   * @param event
   * @returns
   */
  onTreeAction(event: any) {
    const row = event.row;
    if (event.treeStatus === 'disabled') {
      return;
    }

    if (!row.treeStatus || row.treeStatus === 'collapsed') {
      row.treeStatus = 'expanded';
    } else {
      row.treeStatus = 'collapsed';
    }
    this.rows = [...this.rows];
  }

  /**
   * In caso di Tree attivo verifico se devo visualizzare il chek
   * @param row TreeElement
   * @returns
   */
  displayCheck(row: TreeElement) {
    // SCRIVA-1398 flg_cancellato uuid_index
    return (
      row.columns.isFileNode &&
      !row.allegato.flg_cancellato &&
      row.allegato.uuid_index &&
      // Questa funzione è stata agganciata direttamente alla tabella e non si può usare la funzione "allegatoBinValid"
      row.allegato.uuid_index?.indexOf(UUID_INDEX_BIN_PREFIX) === -1
    );
  }

  /**
   * Funzione di verifica che ritorna la validità di un allegato data la validità del file binario associato.
   * @param allegato Allegato con l'oggetto da verificare.
   * @returns boolean con il risultato del controllo.
   */
  private allegatoBinValid(allegato: Allegato): boolean {
    // Verifico l'input
    if (!allegato) {
      // Manca l'input torno undefined
      return undefined;
    }

    // Recupero lo uuid_index
    const uuidIndex: string = allegato.uuid_index;
    // Verifico che esista e che non contenga il prefisso che indica l'errore di associazione di index
    return uuidIndex?.indexOf(UUID_INDEX_BIN_PREFIX) === -1;
  }

  onHelpClicked() {
    const componente = this.authStoreService.getComponente();
    const chiaveHelp = `${componente}.${this.istanza.adempimento.tipo_adempimento.cod_tipo_adempimento}.${this.istanza.adempimento.cod_adempimento}.QDR_ALLEGATO.in_pubblicazione`;

    const modalContent =
      this.helpList.find((help) => help.chiave_help === chiaveHelp)
        ?.des_testo_help || 'Help non trovato...';

    this.messageService.showConfirmation({
      title: 'HELP',
      codMess: null,
      content: modalContent,
      buttons: [],
    });
  }

  /**
   * Recupero gli allegati dato un array che rappresenta le informazioni della tabella.
   * La funzione è stata pensata per gestire le righe selezionate.
   * @returns Allegato[] selezionati nella tabella.
   */
  private getRowSelected(): Allegato[] {
    // Recupero la lista delle righe selezionate
    let selected: TreeElement[] = [...this.selected];
    // Recupero gli allegati per le righe selezionate
    return this.getAllegatiFromRows(selected, this.isTreeActive, true);
  }

  /**
   * SCRIVA-1398
   * Funzione che estrae, da una lista in input di righe di tabella, le informazioni degli allegati.
   * @param row TreeElement[] con la lista di elemente da verificare per l'estrazione degli allegati.
   * @param checkTreeActive boolean che verifica se la gestione ad albero è attiva. Se lo è, le righe verranno filtrate per una proprietà specifica. Per default è: false.
   * @param checkFileCancOrBin boolean che verifica se il file è stato cancellato o se il file binario associato non è valido.
   * @returns Allegato[] con la lista degli allegati estratti.
   */
  private getAllegatiFromRows(
    row: TreeElement[],
    checkTreeActive: boolean = false,
    checkFileCancOrBin: boolean = false
  ): Allegato[] {
    // Recupero la lista degli elementi da gestire
    let elements: TreeElement[] = [...row];

    // Verifico se la gestione ad albero è attiva
    if (checkTreeActive) {
      // Filtro le righe per la quale solo quelle con isFileNode sono per gli allegati
      elements = elements.filter((e: TreeElement) => e.columns.isFileNode);
      // #
    }

    // Recupero la lista degli allegati
    let allegati: Allegato[];
    allegati = elements.map((item: TreeElement) => item.allegato);

    // Verifico se bisogna controllare lo stato dei file, se cancellati o il file binario associato non è valido
    if (checkFileCancOrBin) {
      // Filtro le righe per tutti quei file che risultano validi per flg cancellato e uuid index valido
      allegati = allegati.filter((a: Allegato) => {
        // Definisco i check di controllo
        const isDeleted: boolean = a.flg_cancellato;
        const isBinOK: boolean = this.allegatoBinValid(a);
        // Il file non deve essere cancellato e non deve avere problemi con il file binario
        return !isDeleted && isBinOK;
      });
    }

    // Ritorno la lista degli allegati
    return allegati;
  }

  private _shoMessagePublishReserved() {
    this.messageService.showMessageStatic(
      'containerDocumenti',
      false,
      'Attenzione, tra gli allegati di cui hai chiesto la pubblicazione ve ne sono di riservati e su di essi non si può procedere',
      'A'
    );
  }

  /**
   * Funzione che attiva il procedimento di pubblicazione/annullamento degli allegati.
   * @param flg_da_pubblicare boolean con l'indicazione per pubbblicare o annullare gli allegati.
   */
  private _pubblicaDocumenti(flg_da_pubblicare: boolean = false) {
    let items: Allegato[] = this.getRowSelected();
    const serviceCallArray = [];

    // Gestisco le informazioni dei dati per l'allegato
    let datiPubblicazioneAllegato: Partial<Allegato>;
    datiPubblicazioneAllegato = this.datiAllegatoPubblicazione(
      flg_da_pubblicare,
      this.istanza
    );

    let shoMessagePublishReserved: boolean = false;

    // Definisco un array che conterrà tutte le informazioni degli allegati da aggiornare
    const allegatiToUpd: Partial<Allegato>[] = [];

    items.forEach((allegato: Allegato) => {
      // Creo un oggetto Allegato parziale con i dati per l'aggiornamento
      const allegatoToUpd: Partial<Allegato> = {
        /**
         * TODO: @Ismaele @Roberto_Orrù => SCRIVA-1412/SCRIVA-1503
         * Partendo dalla correzione per SCRIVA-1503, aprendo in dettaglio un Allegato possono essere aggiornate alcune informazioni.
         * Passando dalla publlicazione/annullamento allegati, tutti gli oggetti Allegato vengono "filtrati" sul totale delle informazioni.
         * Succede che, le proprietà:
         * - data_protocollo_allegato;
         * - data_atto;
         * - num_atto;
         * - num_protocollo_allegato;
         * Devono essere sempre passate, poiché il null viene considerato come valore d'aggiornare sul DB.
         * Capire se un giorno debba servire passare anche tutti gli altri dati dell'allegato.
         */
        // ...allegato,
        data_protocollo_allegato: allegato.data_protocollo_allegato ?? null,
        data_atto: allegato.data_atto ?? null,
        num_atto: allegato.num_atto ?? null,
        num_protocollo_allegato: allegato.num_protocollo_allegato ?? null,
        // Logica pre-controlli dati Allegato
        id_allegato_istanza: allegato.id_allegato_istanza,
        id_istanza: allegato.id_istanza,
        uuid_index: allegato.uuid_index,
        tipologia_allegato: allegato.tipologia_allegato,
        note: allegato.note,
        flg_riservato: allegato.flg_riservato,
        // Sovrascrivo le informazioni per i dati di pubblicazione
        ...datiPubblicazioneAllegato,
      };

      // Verifico se l'allegato ha il flag riservato e si sta cercado di pubblicarlo
      if (allegato.flg_riservato && flg_da_pubblicare) {
        // E' un file riservato e non va pubblicato
        shoMessagePublishReserved = true;
        // Blocco il flusso
        return;
      }

      // Aggiungo l'allegato e la chiamata di aggiornamento alla lista
      serviceCallArray.push(this.allegatiService.updateAllegati(allegatoToUpd));
      // Aggiungo l'oggetto alla lista di allegati
      allegatiToUpd.push(allegatoToUpd);
      // #
    });

    // SCRIVA-1318, SCRIVA-1372
    // In caso di richiesta di pubblicazione di allegati su di essi
    // non si procede e si mostra un messagggio a schermo
    if (shoMessagePublishReserved) {
      // Visualizzo il messaggio che per uno o più allegati riservati non è possibile effettuare la pubblicazione
      this._shoMessagePublishReserved();
      // #
    }

    // Verifico che ci siano informazioni da salvare
    if (serviceCallArray.length === 0) {
      // Non ci sono allegati da pubblicare
      return;
    }

    // Avvio uno spinner
    this.spinner.show();

    // forkJoin(serviceCallArray) // Chiamata originale
    // SCRIVA-1488 => DEV NOTES => A quanto pare le chiamate verso INDEX (documentale) se vengono fatte in parallelo (forkJoin)
    //                             Vengono bloccate completamente. La soluzione è stata sequenziare le chiamate all'API, e non sembra diano errore.
    this.pubblicaDocumentiSeq(allegatiToUpd).subscribe({
      next: (response: (Partial<Allegato> | ScrivaServerError)[]) => {
        // Richiamo la funzione di success
        this.onPubblicaDocumentiSuccess(
          allegatiToUpd,
          response,
          flg_da_pubblicare
        );
        // #
      },
      error: (e: ScrivaServerError) => {
        // Gestisco la segnalazione d'errore
        this.onServiziError(e);
        // #
      },
    });
  }

  /**
   * Funzione che gestisce i dati per la pubblicazione/annullamento di un Allegato.
   * @param allegatoDaPubblicare boolean che indica se l'allegato è da pubblicare [true] o da annullare [false].
   * @param istanza Istanza con le informazioni dell'istanza gestita dall'utente.
   * @returns Partial<Allegato> con le informazioni specifiche per la gestione della pubblicazione/annullamento allegato.
   */
  private datiAllegatoPubblicazione(
    allegatoDaPubblicare: boolean,
    istanza: Istanza
  ): Partial<Allegato> {
    // Gestisco le informazioni dei dati per l'allegato
    let datiPubblicazioneAllegato: Partial<Allegato>;

    // Verifico se l'allegato è da pubblicare
    if (allegatoDaPubblicare) {
      // Definisco una variabile per la data di pubblicazione
      let dataPubblicazione: string;
      // Verifico se esiste la data pubblicazione dell'istanza
      if (istanza?.data_pubblicazione) {
        // Per qualche motivo, l'ora da impostare sono le 2 del mattino, riporto com'era prima il codice
        const dueDelMattino = { hour: 2, minute: 0, second: 0 };
        // Imposto la data di pubblicazione con la formattazione in stringa
        dataPubblicazione = moment()
          .set(dueDelMattino)
          .format('YYYY-MM-DD hh:mm:ss');
        // #
      } else {
        // Non esiste la data pubblicazione istanza
        dataPubblicazione = null;
        // #
      }

      // Definisco i dati per la pubblicazione
      datiPubblicazioneAllegato = {
        flg_da_pubblicare: true,
        data_pubblicazione: dataPubblicazione,
      };
      // #
    } else {
      // Definisco i dati per l'annullamento
      datiPubblicazioneAllegato = {
        flg_da_pubblicare: false,
        data_pubblicazione: null,
      };
    }

    // Ritorno la configurazione generata
    return datiPubblicazioneAllegato;
  }

  /**
   * Funzione che effettua un salvataggio sequenziale degli allegati per la pubblicazione.
   * @param allegatiToUpd Partial<Allegato>[] con la lista di allegati d'aggiornare.
   * @returns Observable<any> con le informazioni salvate.
   */
  private pubblicaDocumentiSeq(
    allegatiToUpd: Partial<Allegato>[]
  ): Observable<any> {
    // Definisco una variabile che conterrà le informazioni dell'allegato che il flusso sta cercando di salvare
    let allegatoCycled: Partial<Allegato> = undefined;

    // Sequenzio il salvataggio e ritorno il risultato al chiamante
    return from(allegatiToUpd).pipe(
      // Sequenzio il salvataggio degli allegati
      concatMap((allegatoToUpd: Partial<Allegato>, i: number) => {
        // Mi salvo internamente alla funzione, l'allegato che sto cercando di salvare
        allegatoCycled = { ...allegatoToUpd };

        // Effettuo la chiamata tentando di salvare l'allegato
        return this.allegatiService.updateAllegati(allegatoToUpd).pipe(
          map((response: Allegato | Allegato[]) => {
            // Verifico se la response è un oggetto singolo o un array
            return (Array.isArray(response) ? response[0] : response) as any;
            // #
          }),
          catchError((error: ScrivaServerError) => {
            // Verifico se esiste la struttura dell'errore singolo
            if (!error.error) {
              // Genero la struttura per error
              error.error = {};
            }
            // Verifico se esiste la struttura per il dettaglio dell'errore singolo
            if (!error.error.detail) {
              // Genero la struttura per detail
              error.error.detail = {};
            }

            // Mergio le proprietà di detail
            error.error.detail = {
              ...error.error.detail,
              // Aggiungo ai dati di dettaglio l'allegato che è andato in errore
              ...{ allegatoToUpd: allegatoCycled },
            };
            // Ritorno l'oggetto di errore, come se fosse valido
            return of(error);
            // #
          })
        );
      }),
      // Tramite operatore reduce, accumulo le risposte ottenute dalle chiamate all'API. Il secondo parametro è il "seed" ossia, l'inizializzazione per "resAccumulator"
      reduce((resAccumulator: any[], resCurrent) => {
        // Aggiungo la risposta corrente all'accumulatore (che sia l'allegato x success o uno scriva error x error)
        resAccumulator.push(resCurrent);
        // Ritorno l'accumulatore
        return resAccumulator;
        // #
      }, [])
    );
  }

  /**
   * Funzione di comodo che gestisce il flusso dati a seguito del successo per la chiamata alla pubblicazione documenti.
   * @param allegati Partial<Allegato>[] con la lista di allegati per la pubblicazione/annullamento.
   * @param response (Partial<Allegato> | ScrivaServerError)[] con la lista degli oggetti correttamente salvati o la lista degli errori generati dal salvataggio.
   * @param flg_da_pubblicare boolean che indica se il flag è per la pubblicazione.
   */
  private onPubblicaDocumentiSuccess(
    allegati: Partial<Allegato>[],
    response: (Partial<Allegato> | ScrivaServerError)[],
    flg_da_pubblicare: boolean
  ) {
    // Cerco di estrarre tutti i possibili errori generati dalle risposte
    const errors: ScrivaServerError[] = response.filter(
      (r: ScrivaServerError) => {
        // Forzo la tipizzazione dell'oggetto e verifico se esiste la proprietà principale "error". Se è una response in success, l'oggetto "Allegato" non avrà mai la proprietà "error".
        return r.error !== undefined;
        // #
      }
    );

    // Ci sono tre condizioni possibili: tutte le chiamate OK, tutte le chiamate KO, parte delle chiamate OK e parte KO
    const allOK: boolean = errors.length === 0;
    const allKO: boolean = errors.length === response.length;
    const partialOK: boolean = !allOK && !allKO;

    // Definisco delle variabili di comodo
    const codMess: string = flg_da_pubblicare ? 'P022' : 'P023';
    const boxMess: string = 'containerDocumenti';
    const eventAction: string = 'annullaInserisciAllegatoReload';

    // Creo un oggetto per emettere l'evento di allegati pubblicati/annullati
    const pubblicazioneAllegati: ICRAPubblicazioneAllegati = {
      allegati,
      risultati: response,
    };

    // Verifico se è andato tutto bene
    if (allOK) {
      // Flusso normale di gestione e segnalazione
      this.messageService.showMessage(codMess, boxMess, false);
      this.selected = [];
      this.emit.next({ action: eventAction });
      this.onAllegatiPubblicati$.emit(pubblicazioneAllegati);
      // #
    } else if (allKO) {
      // Tutte le chiamate sono andate in errore, segnalo all'utente
      this.messageService.showMessage('E100', boxMess, true);
      // #
    } else if (partialOK) {
      // Parte sono OK, parte sono KO, gestisco la casistica
      this.messageService.showMessage('A063', boxMess, false);
      this.selected = [];
      this.emit.next({ action: eventAction });
      this.onAllegatiPubblicati$.emit(pubblicazioneAllegati);
      // #
    }
  }

  /**
   * Funzione di comodo che gestisce il flusso dati a seguito del fallimento per la chiamata alla pubblicazione documenti.
   * @param err ScrivaServerError con il dettaglio per l'errore ritornato dal servizio.
   */
  private onPubblicaDocumentiError(err: ScrivaServerError) {
    // Verifico se esiste un codice per l'errore
    if (err.error?.code) {
      this.messageService.showMessage(
        err.error.code,
        'containerDocumenti',
        false
      );
    } else {
      this.messageService.showMessage('E100', 'containerDocumenti', true);
    }
  }

  /**
   * utility che converte la data del form in data per il BE
   * @param date stringa
   * @returns stringa in formato yyyy-MM-dd HH:mm:ss per salvataggio a BE
   */
  private _convertDateForm4BE(
    date: string,
    format: string = 'yyyy-MM-dd HH:mm:ss'
  ): string {
    return this.datePipe.transform(date, format);
  }

  /**
   * #####################################
   * FUNZIONI DI GESTIONE TABELLA ALLEGATI
   * #####################################
   */

  /**
   * Funzione di supporto che definisce le logiche di comparazione tra una riga della tabella allegati e la lista di allegati selezionati.
   * @param row any come oggetto dinamico che può contenere le informazioni di una riga di tabella.
   * @param rowsSelected any[] come lista di oggetti dinamici che può contenere le informazioni delle righe selezionate tramite check della tabella.
   * @returns INgxDataTableRowSelected come oggetto di configurazione per la classe di stile d'applicare alla riga della tabella se questa viene checkata.
   */
  private checkRowAllegatiSelected(
    row: any,
    rowsSelected: any[]
  ): INgxDataTableRowSelected {
    // Definisco il flag che gestirà il colore delle riga per la tabella
    let isRowSelected: boolean = false;
    // Definisco una micro funzione di comodo per la gestione delle logiche per la classe di stile
    const rowClass = (rowSelected: boolean) => {
      // Ritorno la configurazione
      return { 'riga-selezionata': rowSelected };
    };

    // Viste le logiche e strutture dinamiche/non strettamente tipizzate gestisco tutto in un try/catch
    try {
      // Recupero dalla riga le informazioni dell'allegato
      const allegato: Allegato = row?.allegato;
      // Recupero la lista di righe selezionate
      let righeSelezionate: Allegato[];
      righeSelezionate = rowsSelected?.map((s: any) => s?.allegato);

      // Verifico di essere riuscito ad estrarre correttamente le informazioni
      const existA: boolean = allegato !== undefined;
      const existRS: boolean =
        righeSelezionate !== undefined && righeSelezionate.length > 0;
      if (!existA || !existRS) {
        // Non ho i dati per verificare il check sulla riga
        return rowClass(isRowSelected);
      }

      // I dati esistono verifico se all'interno delle righe selezionate, è presente l'allegato in input
      isRowSelected = righeSelezionate.some((allegatoRiga: Allegato) => {
        // Recupero gli id allegati
        const idAR: number = allegatoRiga?.id_allegato_istanza;
        const idA: number = allegato.id_allegato_istanza;
        // Se entrambi gli id sono undefined, allora non considero uguale i dati
        if (idAR === undefined && idA === undefined) {
          // Gli allegati non sono uguali
          return false;
        }
        // Verifico gli id degli allegati
        return idAR === idA;
        // #
      });

      // #
    } catch (e) {}

    // Ritorno un oggetto che definisce classe css e flag booleano che attiva effettivamente la classe (se true)
    return rowClass(isRowSelected);
    // #
  }

  /**
   * #######################
   * FUNZIONI GESTIONE ALERT
   * #######################
   */

  /**
   * Funzione di comodo che gestisce il messaggio d'errore tramite errore server.
   * @param error ScrivaServerError che definisce il corpo dell'errore.
   * @param messageCode string che definisce il messaggio d'errore da visualizzare in testata. Default è 'E100'.
   * @param otherMessages Array di string contenente un array con altri messaggi di dettaglio per l'errore. Default è [].
   */
  protected onServiziError(
    error: ScrivaServerError,
    messageCode = ScrivaCodesMesseges.E100,
    otherMessages: string[] = []
  ) {
    // Definisco un array di messaggi d'errore
    let erroriValidazione = this._scrivaAlert.messagesFromServerError(
      error,
      messageCode,
      otherMessages
    );

    // Aggiorno la lista di errori
    this.aggiornaAlertConfigs(
      this.alertConfigs,
      erroriValidazione,
      ScrivaInfoLevels.danger
    );
  }

  /**
   * Funzione di comodo che gestisce il blob d'errore tramite errore server.
   * @param blobError Blob che definisce il blob d'errore da gestire.
   */
  protected onServiziErrorBlob(blobError: Blob) {
    // Definisco un array di eventi/funzioni da eseguire alla lettura del blob
    const eventsBehavior: IHandleBlobEvents[] = [
      {
        event: 'loadend',
        behavior: (e: ProgressEvent<FileReader>) => {
          // Converto il risultato della lettura del blob
          const r: string = e.target.result as string;
          // Effettuo il parse del risultato e verifico se esiste una proprietà "title"
          const title: string = JSON.parse(r)?.title;
          // Verifico se esiste title
          if (title) {
            // Esiste, aggiorno l'alert locale con le informazioni come errore
            const a = this.alertConfigs;
            const m = [title];
            const t = ScrivaInfoLevels.danger;

            // Aggiorno la lista di errori
            this.aggiornaAlertConfigs(a, m, t);
            this.alertConfigs.allowAlertClose = true;
            // #
          }
        },
      },
    ];
    // Definisco l'oggetto per la gestione della lettura del blob
    const blobConfigs: IHandleBlobRead = { blob: blobError, eventsBehavior };

    // Lancio la funzione di utilities per la lettura e gestione dati
    this._scrivaUtilities.readBlob(blobConfigs);
  }

  /**
   * Funzione che definisce un comportamento standard quando viene emesso l'evento: onAlertHidden; da parte del componente: scriva-alert.
   * @param hidden boolean che definisce lo stato di nascosto dell'alert.
   * @param alertConfigs ScrivaAlertConfigs da resettare.
   */
  onAlertHidden(hidden: boolean, alertConfigs: ScrivaAlertConfigs) {
    // Verifico il risultato
    if (hidden) {
      // Resetto la configurazione dell'alert
      this.resetAlertConfigs(alertConfigs);
    }
  }

  /**
   * Funzione che gestisce il prompt dei messaggi.
   * Se i parametri non sono definiti, verrà effettuato un reset.
   * @param c ScrivaAlertConfigs d'aggiornare con le nuove informazioni.
   * @param messaggi Array di string contenente i messaggi da visualizzare.
   * @param tipo TRiscaAlertType che definisce la tipologia di alert da visualizzare.
   */
  protected aggiornaAlertConfigs(
    c?: ScrivaAlertConfigs,
    messaggi?: string[],
    tipo?: TScrivaAlertType
  ) {
    // Verifico se esiste l'oggetto per l'alert, altrimenti imposto quello locale
    c = c ?? this.alertConfigs;
    // Definisco la struttura dell'oggetto da passare alla chiamata
    const configs: IAlertConfigsUpdate = { alert: c, messaggi, tipo };
    // Aggiorno la configurazione
    c = this._scrivaAlert.aggiornaAlertConfigsMsg(configs);
    // #
  }

  /**
   * Funzione che gestisce il reset del prompt dei messaggi.
   * Se i parametri non sono definiti, verrà effettuato un reset.
   * @param c ScrivaAlertConfigs da resettare.
   */
  protected resetAlertConfigs(c?: ScrivaAlertConfigs) {
    // Verifico se esiste l'oggetto per l'alert, altrimenti imposto quello locale
    c = c ?? this.alertConfigs;
    // Definisco la struttura dell'oggetto da passare alla chiamata
    const configs: IAlertConfigsUpdate = { alert: c };
    // Resetto la configurazione
    c = this._scrivaAlert.aggiornaAlertConfigsMsg(configs);
    // #
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter che verifica se l'oggetto alertConfigs esiste e ha messaggi.
   */
  get alertConfigsCheck() {
    // Verifico e ritorno la condizione
    return this._scrivaAlert.alertConfigsCheck(this.alertConfigs);
  }
}
