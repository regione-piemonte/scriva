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
  Inject,
  Input,
  LOCALE_ID,
  OnInit,
  TemplateRef,
  ViewChild
} from '@angular/core';
import { BaseComponent, Status, UtilityService } from '@app/core';
import { HelpModalComponent } from '@app/core/components/help-modal/help-modal.component';
import {
  Allegato,
  TreeElement
} from '@app/pages/pratiche/model/allegato.model';
import { PraticheService } from '@app/pages/pratiche/services/pratiche.service';
import { INgxDataTableRowSelected } from '@app/shared/interfaces/utilities.interfaces';
import { ModalService } from '@app/shared/modal/modal.service';
import { NotificationIcons } from '@app/shared/notification/components/notification/utilities/notification.enum';
import { NotificationModel } from '@app/shared/notification/model/notification.model';
import { FileDimensionPipe } from '@app/shared/pipes';
import { LoadingService } from '@app/theme/layouts/loading.services';
import { I18nService } from '@eng-ds/translate';
import { ColumnMode, SelectionType } from '@swimlane/ngx-datatable';
import * as moment from 'moment';
import { takeUntil } from 'rxjs/operators';
import { CaptchaModalComponent } from '../captcha-modal/captcha-modal.component';

@Component({
  selector: 'app-allegati-pratica',
  templateUrl: './allegati-pratica.component.html',
  styleUrls: ['./allegati-pratica.component.scss']
})
export class AllegatiPraticaComponent extends BaseComponent implements OnInit {
  @Input() id_istanza: number;
  @Input() cod_pratica: string;
  @ViewChild('helpModal') helpModal: TemplateRef<any>;
  ColumnMode = ColumnMode;
  rows: TreeElement[];

  columns: any[] = [
    {
      prop: 'nome',
      name: this.i18n.translate('PRACTICE.DETAIL.DOCUMENTI.FIELDS.NOME_FILE')
    },
    {
      prop: 'nome_correlato',
      name: this.i18n.translate(
        'PRACTICE.DETAIL.DOCUMENTI.FIELDS.NOME_FILE_CORRELATO'
      )
    },
    {
      prop: 'categoria',
      name: this.i18n.translate(
        'PRACTICE.DETAIL.DOCUMENTI.FIELDS.CATEGORIA_ALLEGATO'
      )
    },
    {
      prop: 'tipologia',
      name: this.i18n.translate(
        'PRACTICE.DETAIL.DOCUMENTI.FIELDS.TIPOLOGIA_ALLEGATO'
      )
    },
    /**
     * @author Ismaele Bottelli
     * @jira SCRIVA-1562: rimossa colonna.
     */
    // {
    //   prop: 'codice',
    //   name: this.i18n.translate(
    //     'PRACTICE.DETAIL.DOCUMENTI.FIELDS.COD_ELABORATO'
    //   )
    // },
    /**
     * @author Ismaele Bottelli
     * @jira SCRIVA-1562: rimossa colonna.
     */
    // {
    //   prop: 'dataUpload',
    //   name: this.i18n.translate('PRACTICE.DETAIL.DOCUMENTI.FIELDS.DATA_PUB')
    // },
    {
      prop: 'dimensione',
      name: this.i18n.translate('PRACTICE.DETAIL.DOCUMENTI.FIELDS.DIMENSIONE'),
      pipe: new FileDimensionPipe()
    }
  ];

  columnsCsv: any[] = [
    {
      prop: 'classe_allegato.des_classe_allegato',
      name: this.i18n.translate(
        'PRACTICE.DETAIL.DOCUMENTI.FIELDS.CLASSIFICAZIONE_DOC'
      ),
      getter: {
        transform: (value: Allegato) =>
          value.classe_allegato?.des_classe_allegato
            ? value.classe_allegato.des_classe_allegato
            : ''
      }
    },
    {
      prop: 'nome_doc',
      name: this.i18n.translate('PRACTICE.DETAIL.DOCUMENTI.FIELDS.NOME_FILE'),
      getter: {
        transform: (value: Allegato) =>
          !value?.id_allegato_istanza_padre
            ? value.nome_allegato
            : this.getNomeAllegato(value.id_allegato_istanza_padre)
      }
    },
    {
      prop: 'nome_allegato',
      name: this.i18n.translate(
        'PRACTICE.DETAIL.DOCUMENTI.FIELDS.NOME_FILE_CORRELATO'
      ),
      getter: {
        transform: (value: Allegato) =>
          !value?.id_allegato_istanza_padre ? '' : value.nome_allegato
      }
    },
    {
      prop: 'tipologia_allegato.categoria_allegato.des_categoria_allegato',
      name: this.i18n.translate(
        'PRACTICE.DETAIL.DOCUMENTI.FIELDS.CATEGORIA_ALLEGATO'
      ),
      getter: {
        transform: (value: Allegato) =>
          value?.tipologia_allegato?.categoria_allegato?.des_categoria_allegato
            ? value.tipologia_allegato.categoria_allegato.des_categoria_allegato
            : ''
      }
    },
    {
      prop: 'tipologia_allegato.des_tipologia_allegato',
      name: this.i18n.translate(
        'PRACTICE.DETAIL.DOCUMENTI.FIELDS.TIPOLOGIA_ALLEGATO'
      ),
      getter: {
        transform: (value: Allegato) =>
          value.tipologia_allegato?.des_tipologia_allegato
            ? value.tipologia_allegato.des_tipologia_allegato
            : ''
      }
    },
    // {
    //   prop: 'cod_allegato',
    //   name: this.i18n.translate(
    //     'PRACTICE.DETAIL.DOCUMENTI.FIELDS.COD_ELABORATO'
    //   )
    // },
    // {
    //   prop: 'data_upload',
    //   name: this.i18n.translate('PRACTICE.DETAIL.DOCUMENTI.FIELDS.DATA_UPLOAD'),
    //   pipe: {
    //     transform: (value: Allegato) =>
    //       moment(value.data_upload).format('DD/MM/YYYY - HH:mm:ss')
    //   }
    // },
    // {
    //   prop: 'data_pubblicazione',
    //   name: this.i18n.translate('PRACTICE.DETAIL.DOCUMENTI.FIELDS.DATA_PUB'),
    //   pipe: {
    //     transform: (value: Allegato) =>
    //       moment(value.data_pubblicazione).format('DD/MM/YYYY')
    //   }
    // },
    {
      prop: 'dimensione_upload',
      name: this.i18n.translate(
        'PRACTICE.DETAIL.DOCUMENTI.FIELDS.DIMENSIONE_MB'
      ),
      pipe: {
        transform: (value: Allegato) => this.pipeMb(value.dimensione_upload)
      }
    }
  ];

  canDownloadPdf: boolean = false;
  canExportCsv: boolean = false;
  downloadModal;
  messages = {
    emptyMessage: 'Nessuna corrispondenza trovata'
  };

  selectedRows: TreeElement[] = [];

  SelectionType = SelectionType;

  notificationErScaricaSelConfig: NotificationModel;
  notificationErCaptcha: NotificationModel;
  notificationErDownload: NotificationModel;
  notificationErDownloadZip: NotificationModel;

  /** (row: any) => INgxDataTableRowSelected; che definisce le logiche per la gestione della colorazione di una riga di tabella. */
  handleRowStyles: (row: any) => INgxDataTableRowSelected;

  constructor(
    private praticheService: PraticheService,
    private i18n: I18nService,
    private loadingService: LoadingService,
    private modalService: ModalService,
    private utilityService: UtilityService,
    @Inject(LOCALE_ID) public locale: string
  ) {
    super();
    this.loadingService.hide();

    // Lancio il set per la funzionalità della tabella
    this.setupFunzioniTabella();
  }

  /**
   * Funzione di setup che definisce le logiche per la gestione della tabella allegati.
   */
  private setupFunzioniTabella() {
    // Definisco la funzione per il coloramento della riga di tabella
    this.handleRowStyles = (row: any) => {
      // Richiamo la logica di verifica
      return this.checkRowAllegatiSelected(row, this.selectedRows);
      // #
    };
  }

  ngOnInit(): void {
    this.praticheService
      .getAllegatiList(this.id_istanza)
      .subscribe((response: TreeElement[]) => {
        this.rows = response;
      });
  }

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
      // toFixed() viene utilizzato per arrotondare il risultato a tre decimali
      return mb.toFixed(2);
    }
    return '';
  }

  exportGenericFile(type: string) {
    if (type === 'csv') {
      this._downloadCSV();
    } else {
      this.modalService
        .openDialog(CaptchaModalComponent, {
          header: this.i18n.translate('PRACTICE.DETAIL.HELP.HEADER'),
          sizeModal: 'xs',
          showCloseButton: true
        })
        .closed.subscribe((value) => {
          if (
            value.captchaValidate.toLowerCase() === 'success' &&
            type === 'zip'
          ) {
            this.notificationErCaptcha = null;
            this.loadingService.show();
            this._downloadZIP();
            //this.loadingService.hide();
          } else {
            this.notificationErCaptcha = {
              title: this.i18n.translate('PRACTICE.DETAIL.CAPTCHA.ERROR.TITLE'),
              text: this.i18n.translate('PRACTICE.DETAIL.CAPTCHA.ERROR.TEXT'),
              status: Status.error,
              icon: NotificationIcons.ERROR,
              dismissable: false
            };
          }
        });
    }
  }

  _downloadCSV(): void {
    this.loadingService.show();
    let items: any[] = this.rows
      .filter((i) => i.columns.isFileNode)
      .map((item) => item.allegato);

    let columns = [...this.columnsCsv];
    // console.log('rows: ', items);
    // console.log('columns: ', columns);

    this.utilityService.createCSV(
      items,
      columns,
      `Documenti_istanza_${this.cod_pratica}_${new Date().toLocaleDateString()}`
    );
    this.loadingService.hide();
  }

  _downloadZIP(): void {
    this.loadingService.show();
    this.praticheService
      .downloadAllegatiZip(this.id_istanza)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        error: () => {
          this.loadingService.hide();
          this.notificationErDownloadZip = {
            title: this.i18n.translate(
              'PRACTICE.DETAIL.DOCUMENTI.EXPORT.CSV.ERROR.TITLE'
            ),
            text: this.i18n.translate(
              'PRACTICE.DETAIL.DOCUMENTI.EXPORT.CSV.ERROR.TEXT'
            ),
            status: Status.error,
            icon: NotificationIcons.ERROR,
            dismissable: false
          };

          /*           this.notificationService.error({
            title: this.i18n.translate(
              'PRACTICE.DETAIL.DOCUMENTI.EXPORT.CSV.ERROR.TITLE'
            ),
            text: this.i18n.translate(
              'PRACTICE.DETAIL.DOCUMENTI.EXPORT.CSV.ERROR.TEXT'
            )}
          ); */
        }
      });
  }

  _downloadSelectedZIP(): void {}

  onDownloadSelected() {
    this.notificationErCaptcha = null;
    if (this.selectedRows.length === 0) {
      this.notificationErScaricaSelConfig = {
        title: this.i18n.translate(
          'PRACTICE.DETAIL.DOCUMENTI.EXPORT.CSV.NO_SELECTION.TITLE'
        ),
        text: this.i18n.translate(
          'PRACTICE.DETAIL.DOCUMENTI.EXPORT.CSV.NO_SELECTION.TEXT'
        ),
        status: Status.warning,
        icon: NotificationIcons.WARNING,
        dismissable: true,
        time: 7000
      };
      return;
    }
    this.modalService
      .openDialog(CaptchaModalComponent, {
        header: this.i18n.translate('PRACTICE.DETAIL.HELP.HEADER'),
        sizeModal: 'xs',
        showCloseButton: true
      })
      .closed.subscribe((value) => {
        if (value.captchaValidate.toLowerCase() === 'success') {
          this.loadingService.show();
          this.notificationErCaptcha = null;
          this.notificationErDownload = null;
          const list = [];

          this.selectedRows.forEach((row: TreeElement) => {
            if (row.columns.isFileNode) {
              list.push(row.columns.codice);
            }
          });
          this.praticheService
            .downloadAllegatiSelezionati(this.id_istanza, list)
            .pipe(takeUntil(this.destroy$))
            .subscribe({
              next: () => {
                this.loadingService.hide();
              },
              error: () => {
                // TODO - da fixare con messaggio corretto con replace PH..jira 1041, test su 2024-36/VIA-VAL
                // const desAlert = this.conswebMessages.getDesMessaggio(
                //   ConswebCodesMesseges.E083
                // );
                this.loadingService.hide();
                this.notificationErDownload = {
                  title: this.i18n.translate(
                    'PRACTICE.DETAIL.DOCUMENTI.EXPORT.CSV.NO_SELECTION.TITLE'
                  ),
                  text: 'Attenzione: il download dei file selezionati è fallito, contatta l’assistenza.', //desAlert,
                  status: Status.error,
                  icon: NotificationIcons.ERROR,
                  dismissable: false
                };

                /*                 this.notificationService.error({
                  title: this.i18n.translate(
                    'PRACTICE.DETAIL.DOCUMENTI.EXPORT.CSV.ERROR.TITLE'
                  ),
                  text: this.i18n.translate(
                    'PRACTICE.DETAIL.DOCUMENTI.EXPORT.CSV.ERROR.TEXT'
                  )
                }); */
              }
            });
        } else {
          this.loadingService.hide();
          this.notificationErCaptcha = {
            title: this.i18n.translate('PRACTICE.DETAIL.CAPTCHA.ERROR.TITLE'),
            text: this.i18n.translate('PRACTICE.DETAIL.CAPTCHA.ERROR.TEXT'),
            status: Status.error,
            icon: NotificationIcons.ERROR,
            dismissable: false
          };
        }
      });
  }

  onHelp(): void {
    this.utilityService
      .getHelp('PUBWEB.MSCR004P.btnDownloadZip')
      .subscribe((help) => {
        this.modalService.openDialog(HelpModalComponent, {
          header: this.i18n.translate('PRACTICE.DETAIL.HELP.HEADER'),
          showCloseButton: true,
          context: { helpOBJ: help }
        });
      });
  }

  onDownload(row: TreeElement) {
    this.modalService
      .openDialog(CaptchaModalComponent, {
        header: this.i18n.translate('PRACTICE.DETAIL.HELP.HEADER'),
        sizeModal: 'xs',
        showCloseButton: true
      })
      .closed.subscribe((value) => {
        if (value.captchaValidate.toLowerCase() === 'success') {
          this.notificationErCaptcha = null;
          this.notificationErDownload = null;
          this.exportFile(row.columns.codice, row.columns.nome).subscribe({
            error: () => {
              // const desAlert = this.conswebMessages.getDesMessaggio(
              //   ConswebCodesMesseges.E083
              // );
              this.notificationErDownload = {
                title: this.i18n.translate(
                  'PRACTICE.DETAIL.DOCUMENTI.EXPORT.FILE.ERROR.TITLE'
                ),
                text: this.i18n.translate(
                  'PRACTICE.DETAIL.DOCUMENTI.EXPORT.FILE.ERROR.TEXT'
                ),
                status: Status.error,
                icon: NotificationIcons.ERROR,
                dismissable: false
              };

              /*               this.notificationService.error({
                title: this.i18n.translate(
                  'PRACTICE.DETAIL.DOCUMENTI.EXPORT.FILE.ERROR.TITLE'
                ),
                text: this.i18n.translate(
                  'PRACTICE.DETAIL.DOCUMENTI.EXPORT.FILE.ERROR.TEXT'
                )
              }); */
            }
          });
        } else {
          this.notificationErCaptcha = {
            title: this.i18n.translate('PRACTICE.DETAIL.CAPTCHA.ERROR.TITLE'),
            text: this.i18n.translate('PRACTICE.DETAIL.CAPTCHA.ERROR.TEXT'),
            status: Status.error,
            icon: NotificationIcons.ERROR,
            dismissable: false
          };
        }
      });
  }

  exportFile(cod_allegato: string, filename: string) {
    return this.praticheService
      .downloadAllegato(this.id_istanza, cod_allegato, filename)
      .pipe(takeUntil(this.destroy$));
  }

  displayCheck(row: any) {
    return row.columns.isFileNode;
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
    this.selectedRows = rowsSelected;
  }

  getNumberOfElements(): number {
    let totaleAllegati = [];
    this.rows?.forEach((al) => {
      if (al?.allegato?.id_allegato_istanza) {
        totaleAllegati.push(al);
      }
    });
    return totaleAllegati.length || 0;
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
}
