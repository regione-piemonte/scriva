/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { compact, uniqWith } from 'lodash';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin } from 'rxjs';
import { AutoUnsubscribe } from 'src/app/core/components';
import {
  Allegato,
  CategoriaAllegato,
  TipoAllegato,
} from 'src/app/features/ambito/models';
import { AllegatiService } from 'src/app/features/ambito/services';
import { InfoProtocolloAttoModalComponent } from 'src/app/shared/components';
import { EditAllegatoModalComponent } from 'src/app/shared/components/edit-allegato-modal/edit-allegato-modal.component';
import {
  Adempimento,
  ConfigAdempimento,
  Help,
  Istanza,
  TipoAdempimentoOggApp,
} from 'src/app/shared/models';
import {
  AuthStoreService,
  ConfigurazioniScrivaService,
  HelpService,
  IstanzaService,
  MessageService,
  StepManagerService,
} from 'src/app/shared/services';
import { TipoInfoAdempimento } from 'src/app/shared/services/configurazioni/utilities/configurazioni.enums';
import {
  AttoreGestioneIstanzaEnum,
  TipoEventoEnum,
} from 'src/app/shared/utils';
import { CardRiepilogoAllegatiActsion } from '../../../../shared/components/card-riepilogo-allegati/utilities/card-riepilogo-allegati.enums';
import { ICRAPubblicazioneAllegati } from '../../../../shared/components/card-riepilogo-allegati/utilities/card-riepilogo-allegati.interfaces';
import { ScrivaUtilitiesService } from '../../../../shared/services/scriva-utilities/scriva-utilities.service';
import { AdvancedActionsChiavi } from '../../enums/advanced-actions.enums';
import {
  IACAAllegatiPubblicati,
  IACAAllegatoCancellato,
  IACAAllegatoModificato,
  IACAFileAggiunto,
} from './utilities/advanced-action-allegati.interfaces';

@Component({
  selector: 'advanced-action-allegati',
  templateUrl: './advanced-action-allegati.component.html',
  styleUrls: ['./advanced-action-allegati.component.scss'],
})
export class AdvancedActionAllegatiComponent
  extends AutoUnsubscribe
  implements OnInit
{
  /** Input Istanza con istanza adempimento corrente */
  @Input() istanza: Istanza;

  /** chiave che identifica il tipo di ademmpimento */
  @Input() chiave: AdvancedActionsChiavi;

  /** Output che definisce l'evento passato verso alto */
  @Output() emit = new EventEmitter<string>();

  @Output() dataError = new EventEmitter<string>();

  /** Output any con le informazioni del file che l'utente ha caricato. */
  @Output() onSingleFileUploaded$ = new EventEmitter<IACAFileAggiunto>();
  /** Output any con le informazioni dei file che l'utente ha caricato. */
  @Output() onMultiFileUploaded$ = new EventEmitter<IACAFileAggiunto>();
  /** Output any con le informazioni allegato modificato dall'utente. */
  @Output() onAllegatoUpdated$ = new EventEmitter<IACAAllegatoModificato>();
  /** Output any con le informazioni allegato cancellato dall'utente. */
  @Output() onAllegatoDeleted$ = new EventEmitter<IACAAllegatoCancellato>();
  /** Output IACAAllegatiPubblicati con le informazioni per allegati pubblicati dall'utente. */
  @Output() onAllegatiPubblicati$ = new EventEmitter<IACAAllegatiPubblicati>();

  info: TipoInfoAdempimento = TipoInfoAdempimento.azTipoAllegato;

  /** se abbiamo almeno una tipologia mostro la parte degli allegati */
  isActive: boolean = false;

  /** parte allegati collapsed */
  isCollapsed: boolean = true;

  /** ConfigAdempimento[] con la lista delle azioni avanzate. Questa è la struttura principale che pilota le informazioni in pagina. */
  configsAdempimento: ConfigAdempimento[] = [];

  /* prese da allegati */
  componente: string;
  isFrontOffice: boolean;
  codMaschera = '.MSCR013F';

  helpList: Help[];
  tipoEventoEnum = TipoEventoEnum;

  oggAppBtnPubblica: TipoAdempimentoOggApp;
  oggAppBtnProtocolla: TipoAdempimentoOggApp;
  oggAppBtnPerfezionaAllegati: TipoAdempimentoOggApp;
  oggAppBtnIntegraAllegati: TipoAdempimentoOggApp;
  oggAppBtnIntegraAllegatiSucc: TipoAdempimentoOggApp;

  formAllegati: FormGroup;
  tipologiaSelezionata: TipoAllegato = null;

  adempimento: Adempimento;

  /** TipoAllegato[] con la lista completa dei tipi allegato, generata dalle configurazioni adempimento. */
  fullListTipiAllegato: TipoAllegato[];
  /** CategoriaAllegato[] con la lista di categorie filtrate sulla base dei tipi allegato scaricati (ref.: fullListTipiAllegato). */
  categorie: CategoriaAllegato[];
  /** TipoAllegato[] con la lista dei tipi allegato, filtrata sulla base del valore della categoria scelta in pagina. */
  tipiAllegato: TipoAllegato[];

  allegatiObbligatoriPerVincoli: TipoAllegato[];

  idIstanza: number;
  idTemplateQuadro: number;
  stepIndex: number;
  codTipoQuadro: string;
  codQuadro: string;
  qdr_riepilogo;
  saveWithPut = false;

  acceptedFileTypes: string;
  fileExtensionsArray = [];
  maxFileSize: number;
  maxNote = 2000;
  maxLengths: {
    key: string;
    max: number;
  }[] = [
    { key: 'numProtocollo', max: 20 }, // num_protocollo_allegato
    { key: 'numAtto', max: 20 }, // num_atto
    { key: 'titolo', max: 1000 }, // titolo allegato
    { key: 'autore', max: 300 }, // autore allegato
  ];
  // numero di protocollo lunghezza massima campo
  numProtocolloMaxLength: number;

  tipoIntegrazione;

  rows: Allegato[] = [];

  gestioneEnum = AttoreGestioneIstanzaEnum;
  attoreGestioneIstanza = AttoreGestioneIstanzaEnum.WRITE as string;

  additionalInfo: {
    protocollo?: { data: string; numero: string };
    atto?: { data: string; numero: string; titolo: string; autore: string };
  } = null;

  alertText: string;
  showAlertBanner = false;
  disableBtnRiferimentiAtto = false;

  // isStepValid = false;
  showButtonNext = true;
  showButtonPrevious = true;
  showSuccessMessageIntegrazione = false;
  showBtnConferma = false;
  flgIntegrazioneAllegati = false;
  flgContinue = false;

  setAsterisk = false;
  totalMandatory = 0;
  today = new Date();

  /* end prese da allegati */

  constructor(
    protected router: Router,
    protected allegatiService: AllegatiService,
    protected istanzaService: IstanzaService,
    protected authStoreService: AuthStoreService,
    protected helpService: HelpService,
    protected configurazioniService: ConfigurazioniScrivaService,
    protected fb: FormBuilder,
    protected messageService: MessageService,
    protected spinner: NgxSpinnerService,
    protected stepManagerService: StepManagerService,
    protected modalService: NgbModal,
    protected _scrivaUtilities: ScrivaUtilitiesService
  ) {
    super();
    this.checkOggettiApplicativi();
  }

  checkOggettiApplicativi() {
    const oggAppPulsanti = this.istanzaService.getOggAppPulsanti()
      ? this.istanzaService.getOggAppPulsanti()
      : this.istanzaService.getOggApp();
    oggAppPulsanti?.forEach((element) => {
      switch (element.cod_oggetto_app) {
        case 'btn_pubblica_doc':
          this.oggAppBtnPubblica = element;
          break;
        case 'btn_protocolla':
          this.oggAppBtnProtocolla = element;
          break;
        case 'btn_perfeziona_allegati':
          this.oggAppBtnPerfezionaAllegati = element;
          break;
        case 'btn_integra_allegati':
          this.oggAppBtnIntegraAllegati = element;
          break;
        case 'btn_integra_allegati_succ':
          this.oggAppBtnIntegraAllegatiSucc = element;
          break;
        default:
          break;
      }
    });
  }

  /**
   * NgOnInit.
   */
  ngOnInit() {
    // forzo il codTipoQuadro che dovrebbe essere sempre lo stesso
    this.codTipoQuadro = 'QDR_ALLEGATO';
    this.componente = this.authStoreService.getComponente(); // should always be BO
    this.buildFormAllegati();
    this.idIstanza = this.istanzaService.getIdIstanza();
    this.adempimento = this.istanza.adempimento;
    this.idIstanza = this.istanza.id_istanza;
    this.helpService.setCodMaschera(this.codMaschera);
    this.getHelpList();
    this.startConfigAllegati();
  }

  /**
   * Propago evento verso alto verso componente che contiene il breadcrumb
   * @param ev stringa che dice quale evento deve essere emesso
   */
  clickEmit(ev: string) {
    this.emit.next(ev);
  }

  startConfigAllegati() {
    // prima controlllo se ci sono degli allegati presenti per azione avanzata corrente
    this.configurazioniService
      .getConfigurazioniByInfoAndChiave(
        this.adempimento.cod_adempimento,
        this.info,
        this.chiave
      )
      .subscribe({
        next: (response: ConfigAdempimento[]) => {
          // Assegno localmente le configurazioni per l'azione avanzata
          this.configsAdempimento = response;
          this.checkOggettiApplicativi();
          this.getConfigAllegati();
        },
        error: (err) => {
          console.log(err);
          // TODO non credo che dovremo mostrare errori in questo caso
        },
      });
  }

  getConfigAllegati() {
    forkJoin([
      this.allegatiService.getAllTipiAllegatoByCodAdempimento(
        this.adempimento.cod_adempimento
      ),
      this.allegatiService.getCategorieAllegatoByCodAdempimento(
        this.adempimento.cod_adempimento
      ),
      this.allegatiService.getAcceptedFileTypesByCodAdempimento(
        this.adempimento.cod_adempimento
      ),
      this.configurazioniService.getConfigurazione('SCRIVA_INDEX_MAX_MB_FILE'),
    ]).subscribe({
      next: (response) => {
        // Recupero la lista delle configurazioni adempimento principali (azioni avanzate)
        let configsAdempimento: ConfigAdempimento[];
        configsAdempimento = this.configsAdempimento;
        // Lancio la funzione di gestione dei risultati
        this.getConfigAllegatiCallback(response, configsAdempimento);
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

  /**
   * Funzione che filtra le informazioni dei tipi allegato, sulla base delle configurazioni dell'adempimento.
   * La logica si basa sull'ordinamento della lista oggetti configsAdempimento, dalla quale si estrae poi le informazioni dei tipi allegati.
   * Il filtro avrà le seguenti logiche:
   * 1) Ciclo la lista delle configurazioni adempimento e recupero la proprietà "valore";
   * 2) Ciclo la lista dei tipi allegato e recupero tutti gli oggetti che matchano la condizione: valore === tipologia_allegato.cod_tipologia_allegato;
   * In questo modo le logiche tengono ordinate posizionalmente le informazioni negli array.
   * @param tipiAllegato TipoAllegato[] con le informazioni da filtrare.
   * @param configsAdempimento ConfigAdempimento[] con la lista di riferimento per la gestione del filtro.
   * @returns TipoAllegato[] con la lista filtrata di elementi.
   */
  private filterTipiAllegatoAzioneAvanzata(
    tipiAllegato: TipoAllegato[],
    configsAdempimento: ConfigAdempimento[]
  ): TipoAllegato[] {
    // Gestisco l'input
    tipiAllegato = tipiAllegato ?? [];
    configsAdempimento = configsAdempimento ?? [];

    // Creo un array di supporto che conterrà i dati filtrati
    const tipiAllegatoByConfigAdempimento: TipoAllegato[] = [];

    // Ciclo la lista delle configurazioni adempimento
    configsAdempimento.forEach((configAdempimento: ConfigAdempimento) => {
      // Estraggo dall'oggetto la proprietà "valore"
      const valore: string = configAdempimento.valore ?? '';

      // Ciclo la lista dei tipi allegato
      tipiAllegato.forEach((tipoAllegato: TipoAllegato) => {
        // Estraggo dall'oggetto la proprietà per la verifica
        const codTA: string =
          tipoAllegato.tipologia_allegato?.cod_tipologia_allegato ?? '';

        // Verifico che entrambi i codici esistano
        const bothCodEmpty: boolean = valore === '' && codTA === '';
        const sameCode: boolean = valore === codTA;
        // Verifico che entrambe le informazioni esistano e se sono le stesse
        if (!bothCodEmpty && sameCode) {
          // La configurazione combacia, l'aggiungo all'array dati
          tipiAllegatoByConfigAdempimento.push(tipoAllegato);
          // #
        }
        // #
      });
      // #
    });

    // Ritorno la lista filtrata
    return tipiAllegatoByConfigAdempimento;
  }

  /**
   * Funzione che filtra le informazioni delle categorie allegato, sulla base delle configurazioni dei tipi allegato.
   * La logica si basa sull'ordinamento della lista oggetti tipiAllegato, dalla quale si estrae poi le informazioni delle categorie allegato.
   * Il filtro avrà le seguenti logiche:
   * 1) Ciclo la lista deli tipi allegato ed estraggo la proprietà "categoria_allegato";
   * 2) Dalla lista ottenuta, vado ad effettuare un filtro per rendere univoci gli oggetti;
   * In questo modo le logiche tengono ordinate posizionalmente le informazioni negli array.
   * @param tipiAllegato TipoAllegato[] con le informazioni per l'estrazione e filtraggio dati.
   * @returns CategoriaAllegato[] con la lista generata di elementi.
   */
  private filterCategorieAzioneAvanzata(
    tipiAllegato: TipoAllegato[]
  ): CategoriaAllegato[] {
    // Verifico l'input
    tipiAllegato = tipiAllegato ?? [];

    // Estraggo dalla lista dei tipi allegato tutti gli oggetti per le categorie
    let categorieAllegatoRaw: CategoriaAllegato[] = tipiAllegato.map(
      (tipoAllegato: TipoAllegato) => {
        // Estraggo l'oggetto della categoria allegato
        return tipoAllegato?.tipologia_allegato?.categoria_allegato;
        // #
      }
    );
    // Dalla lista di oggetti completa, vado a rimuovere tutte le configurazioni non definite
    categorieAllegatoRaw = compact(categorieAllegatoRaw);
    // Genero infine la lista finale, senza oggetti duplicati
    const categorieAllegatoClean: CategoriaAllegato[] = uniqWith(
      categorieAllegatoRaw,
      (catA: CategoriaAllegato, catB: CategoriaAllegato) => {
        // Verifico se hanno lo stesso codice categoria
        return catA?.cod_categoria_allegato === catB?.cod_categoria_allegato;
      }
    );

    // Ritorno la listra filtrata
    return categorieAllegatoClean;
  }

  _filterAllegatiAzioneAvanzata(allegati: Allegato[]): Allegato[] {
    allegati = allegati.filter(
      (c) =>
        this.fullListTipiAllegato.findIndex(
          (x) =>
            x.tipologia_allegato.cod_tipologia_allegato ===
            c.tipologia_allegato.cod_tipologia_allegato
        ) > -1
    );
    return allegati;
  }

  /**
   * Funzione che gestisce le configurazioni scaricate per l'adempimento.
   * Partendo dalle configurazioni dell'adempimento, verranno filtrati i dati restituiti dai servizi, nello specifico:
   * 1) Le configurazioni adempimento filtreranno le tipologie allegato scaricate;
   * 2) Dalla lista filtrata per le tipologie, verranno estratte le categorie allegato. Questa lista NON avrà valori duplicati.
   * @param response any[] con la lista delle risposte ottenute dal servizio di scarico dati.
   * @param configsAdempimento ConfigAdempimento[] con la lista di oggetti principali per le configurazioni dell'adempimento.
   */
  getConfigAllegatiCallback(
    response: any[],
    configsAdempimento: ConfigAdempimento[]
  ) {
    // ### 1) Filtri per la gestione delle tipologie allegato
    // Vado a recuperare le tipologie allegato dalla chiamata effettuate
    let tipiAllegatoRes: TipoAllegato[];
    tipiAllegatoRes = response[0];
    // Vado a filtrare i tipi allegato sulla base delle configurazioni dell'adempimento
    let tipiAllegato: TipoAllegato[];
    tipiAllegato = this.filterTipiAllegatoAzioneAvanzata(
      tipiAllegatoRes,
      configsAdempimento
    );
    // Verifico che esistano effettivamente dei tipi allegato
    if (tipiAllegato.length === 0) {
      return;
    }
    // Assegno localmente le informazioni filtrate per i tipi allegato
    this.fullListTipiAllegato = tipiAllegato;
    // Definisco come attiva la sezione degli allegati
    this.isActive = true;

    // ### 2) Filtri per la gestione delle categorie allegato
    // // Vado a recuperare le categorie allegato dalla response
    // const categorieAllegato: CategoriaAllegato[] = response[1];

    // Genero la lista delle categorie basandomi sulla struttura dati dei tipi allegato
    this.categorie = this.filterCategorieAzioneAvanzata(tipiAllegato);

    // Altra logica - da rifattorizzare?
    this.acceptedFileTypes = '';
    this.fileExtensionsArray = [];
    response[2].map((ext) => {
      this.fileExtensionsArray.push(ext.estensione_allegato.toLowerCase());
      if (this.acceptedFileTypes.length > 0) {
        this.acceptedFileTypes += ', ';
      }
      this.acceptedFileTypes += '.' + ext.estensione_allegato.toLowerCase();
    });
    this.maxFileSize = parseFloat(response[3][0]['valore']);
    this.setPage();
  }

  /** start prese da allegati  */

  /* modificato  */
  protected buildFormAllegati() {
    this.numProtocolloMaxLength = this.maxLengths.find(
      (i) => i.key === 'numProtocollo'
    ).max;

    this.formAllegati = this.fb.group({
      categoria: [null, { validators: [Validators.required] }],
      tipologia: [null, { validators: [Validators.required] }],
      riservato: [false, { validators: [Validators.required] }],
      nota: ['', { validators: [] }],
      dataProtocolloAllegato: [null, [this.maxDateValidator]],
      numProtocolloAllegato: [
        '',
        { validators: [Validators.maxLength(this.numProtocolloMaxLength)] },
      ],
    });

    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      this.formAllegati.get('categoria').enable();
      this.formAllegati.get('tipologia').enable();
      this.formAllegati.get('riservato').enable();
      this.formAllegati.get('dataProtocolloAllegato').enable();
      this.formAllegati.get('numProtocolloAllegato').enable();
      this.formAllegati.get('nota').enable();
    } else {
      this.formAllegati.get('categoria').disable();
      this.formAllegati.get('tipologia').disable();
      this.formAllegati.get('riservato').disable();
      this.formAllegati.get('dataProtocolloAllegato').disable();
      this.formAllegati.get('numProtocolloAllegato').disable();
      this.formAllegati.get('nota').disable();
    }
  }

  maxDateValidator(control: AbstractControl): ValidationErrors | null {
    // Verifico che la data sia definita
    if (!control.value) {
      // Non c'è da verificare la data
      return null;
    }

    const selectedDate = new Date(control.value);
    const maxDate = new Date();
    return selectedDate <= maxDate ? null : { maxDateExceededToday: true };
  }

  /**
   * Funzione agganciata all'evento di categoria modificata.
   * La funzione andrà a definire le tipologie allegato data la categoria recuperata dal form.
   */
  onChangeCategoria() {
    // Recupero la categoria selezionata
    let catSelezionata: CategoriaAllegato;
    catSelezionata = this.formAllegati.get('categoria').value;

    // Filtro, partendo dalla lista di tutti i tipi allegato, ed estraggo solo i tipi allegato per stessa categoria allegato
    this.tipiAllegato = this.fullListTipiAllegato.filter(
      (tipoAllegato: TipoAllegato) => {
        // Recupero i dati per il confronto delle categorie
        const codCatTA: string =
          tipoAllegato?.tipologia_allegato?.categoria_allegato
            ?.cod_categoria_allegato;
        const codCatSel: string = catSelezionata?.cod_categoria_allegato;
        // Verifico se i codici categoria sono gli stessi
        return codCatTA === codCatSel;
        // #
      }
    );

    // Quando cambio categoria il pulsante riferimento atto va disabilitato
    this.disableBtnRiferimentiAtto = false;
    // e cancellare le additionalInfo riferimenti atto
    this.onDeleteRiferimentiAtto();

    this.formAllegati.get('tipologia').reset();
  }

  onChangeTipologia() {
    this.formAllegati.get('riservato').enable();
    this.tipologiaSelezionata = this.formAllegati.get('tipologia')?.value;
    this.formAllegati
      .get('riservato')
      .setValue(this.tipologiaSelezionata?.flg_riservato);
    if (this.tipologiaSelezionata?.flg_riservato && this.isFrontOffice) {
      this.formAllegati.get('riservato').disable();
    }

    this.formAllegati.get('nota').clearValidators();
    if (this.tipologiaSelezionata?.flg_nota) {
      this.formAllegati
        .get('nota')
        .setValidators([
          Validators.required,
          Validators.maxLength(this.maxNote),
        ]);
    } else {
      this.formAllegati
        .get('nota')
        .setValidators([Validators.maxLength(this.maxNote)]);
    }
    this.formAllegati.get('nota').updateValueAndValidity();

    this.disableBtnRiferimentiAtto =
      !this.isFrontOffice &&
      this.tipologiaSelezionata?.tipologia_allegato.flg_atto;
    if (!this.disableBtnRiferimentiAtto) {
      this.onDeleteRiferimentiAtto();
    }
    // MOCK
    // this.disableBtnRiferimentiAtto = true;
  }

  onDeleteRiferimentiAtto() {
    if (this.additionalInfo?.atto) {
      this.additionalInfo.atto = null;
    }
  }

  setPage() {
    this.spinner.show();
    this.allegatiService.getAllAllegatiIstanza(this.idIstanza).subscribe(
      (rows) => {
        this.rows = [...this._filterAllegatiAzioneAvanzata(rows)];
        if (this.rows.length > 0) {
          this.isCollapsed = false;
        }
        // this.filterPubblicati(); scriva-916
        let element;
        if (this.rows?.length > 0) {
          element = document.getElementById('containerDocumenti');
        } else {
          element = document.getElementById('containerUploadDocumenti');
        }
        if (element) {
          element.scrollIntoView();
        }

        if (this.showSuccessMessageIntegrazione) {
          this.messageService.showMessage('P008', 'divBtnConferma', false);
          this.showSuccessMessageIntegrazione = false;
          setTimeout(() => {
            this.router.navigate(['/ricerca']);
          }, 1500);
        } else if (this.flgIntegrazioneAllegati) {
          this.checkBtnConferma();
        }

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

  checkBtnConferma() {
    this.allegatiService.getAllAllegatiIstanza(this.idIstanza).subscribe(
      (res) => {
        const filteredList = res.filter(
          (elem) =>
            !!elem.tipo_integra_allegato?.id_tipo_integra_allegato &&
            !elem.data_integrazione
        );
        this.showBtnConferma = filteredList.length > 0;
      },
      (err) => {
        if (err.status !== 404) {
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
      }
    );
  }

  compareCategoria(c1: CategoriaAllegato, c2: CategoriaAllegato) {
    return c1 && c2 && c1.cod_categoria_allegato === c2.cod_categoria_allegato;
  }

  compareTipologia(t1: TipoAllegato, t2: TipoAllegato) {
    return (
      t1 &&
      t2 &&
      t1.tipologia_allegato?.cod_tipologia_allegato ===
        t2.tipologia_allegato?.cod_tipologia_allegato
    );
  }

  getHelpList() {
    this.helpService
      .getHelpByChiave(this.componente + this.codMaschera)
      .subscribe(
        (res) => {
          this.helpList = res;
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
          console.log(
            'Error retrieving help array ',
            this.componente + this.codMaschera
          );
        }
      );
  }

  onHelpClicked(chiave: string) {
    const modalContent =
      this.helpList.find(
        (help) =>
          help.chiave_help === this.componente + this.codMaschera + '.' + chiave
      )?.des_testo_help || 'Help non trovato...';

    this.messageService.showConfirmation({
      title: 'HELP',
      codMess: null,
      content: modalContent,
      buttons: [],
    });
  }

  getHelpBannerText(chiave: string): string {
    const helpBanner = this.helpList?.find(
      (help) =>
        help.tipo_help.cod_tipo_help === 'BNR' &&
        help.chiave_help.includes(chiave)
    );
    return (
      helpBanner?.des_testo_help ||
      "Errore: il testo di questo paragrafo non è stato trovato. Contattare l'assistenza."
    );
  }

  onInserisciRiferimentiAtto() {
    const modalRef = this.modalService.open(InfoProtocolloAttoModalComponent, {
      centered: true,
      scrollable: true,
      backdrop: 'static',
      size: 'md',
    });

    modalRef.componentInstance.type = 'A';
    modalRef.componentInstance.tempValues = this.additionalInfo?.atto;
    modalRef.componentInstance.maxLengths = this.maxLengths;

    modalRef.result
      .then(({ data, numero, titolo, autore }) => {
        if (!this.additionalInfo) {
          this.additionalInfo = {};
        }

        this.additionalInfo.atto = {
          data,
          numero,
          titolo,
          autore,
        };
      })
      .catch((err) => {});
  }

  getErrorsFormAllegato(): string[] {
    let errors = [];
    Object.keys(this.formAllegati.controls).forEach((key) => {
      const controlErrors: ValidationErrors = this.formAllegati.get(key).errors;
      if (controlErrors != null) {
        errors.push(key);
      }
    });
    return errors;
  }

  onInserisciAllegato(fileInput) {
    if (!this.formAllegati.valid) {
      let errors = this.getErrorsFormAllegato();
      if (errors.includes('nota')) {
        this.messageService.showMessage(
          'E017',
          'containerUploadDocumenti',
          false
        );
      } else if (errors.includes('categoria')) {
        this.messageService.showMessageStatic(
          'containerUploadDocumenti',
          false,
          'Attenzione il campo Categoria allegato è obbligatorio',
          'E',
          'STATIC_AAA01'
        );
      } else if (errors.includes('tipologia')) {
        this.messageService.showMessageStatic(
          'containerUploadDocumenti',
          false,
          'Attenzione il campo Tipologia allegato è obbligatorio',
          'E',
          'STATIC_AAA02'
        );
      } else {
        this.messageService.showMessage(
          'E018',
          'containerUploadDocumenti',
          false
        );
      }
      return;
    }

    fileInput.click();
  }

  inserisciAllegato(files) {
    if (files?.length > 0) {
      this.spinner.show();
      files = Array.from(files);

      let totalFileSize = 0;
      files.forEach((file) => (totalFileSize += file.size));
      if (totalFileSize / 1048576 > this.maxFileSize) {
        this.messageService.showMessage(
          'I010',
          'containerUploadDocumenti',
          true
        );
        return;
      }

      const data: Partial<Allegato> = {
        id_istanza: this.idIstanza,
        tipologia_allegato: this.tipologiaSelezionata.tipologia_allegato,
        flg_riservato: this.formAllegati.get('riservato')?.value,
        nome_allegato: '',
        num_protocollo_allegato: this.formAllegati.get('numProtocolloAllegato')
          ?.value,
        data_protocollo_allegato: this.formAllegati.get(
          'dataProtocolloAllegato'
        )?.value
          ? this.formAllegati.get('dataProtocolloAllegato')?.value + ' 00:00:00'
          : undefined,
        num_atto: this.additionalInfo?.atto?.numero,
        data_atto: this.additionalInfo?.atto?.data,
        titolo_allegato: this.additionalInfo?.atto?.titolo,
        autore_allegato: this.additionalInfo?.atto?.autore,
        note: this.formAllegati.get('nota')?.value,
      };

      if (this.chiave === 'INS_DOC_ISTR') {
        data.classe_allegato = {
          cod_classe_allegato: 'DOC_ISTRUTTORIA',
        };
      }

      if (this.tipoIntegrazione) {
        data.tipo_integra_allegato = {
          cod_tipo_integra_allegato: this.tipoIntegrazione,
        };
      }

      if (files.length === 1) {
        this.singleUpload(data, files[0]);
      } else {
        this.multipleUpload(data, files);
      }
    }
  }

  /**
   * Funzione che gestisce le logiche al momento di caricamento di un singolo file.
   * @param data any con le informazioni del file salvato.
   */
  private onFileSingoloCaricato(data: any) {
    // Vado a generare un identificativo per il salvataggio di questo file
    const id: string = this._scrivaUtilities.generateRandomId();
    // Definisco l'oggetto di emissione
    const emitData: IACAFileAggiunto = { id, data };
    // Emetto l'oggetto con le informazioni
    this.onSingleFileUploaded$.emit(emitData);
  }

  /**
   * Funzione che gestisce le logiche al momento di caricamento di più file.
   * @param data any con le informazioni del file salvato.
   * @param id string con l'identificativo per gestire il salvataggio dei files.
   */
  private onFileMultiploCaricato(data: any, id: string) {
    // Definisco l'oggetto di emissione
    const emitData: IACAFileAggiunto = { id, data };
    // Emetto l'oggetto con le informazioni
    this.onMultiFileUploaded$.emit(emitData);
  }

  singleUpload(data, file) {
    const split = file.name.split('.');
    const checkExtensions = this.fileExtensionsArray.some(
      (el) => el === split[split.length - 1].toLowerCase()
    );
    if (!checkExtensions) {
      this.messageService.showMessage(
        'E019',
        'containerUploadDocumenti',
        false
      );
      return;
    }

    data.nome_allegato = file.name;
    this.allegatiService.postAllegati(JSON.stringify(data), file).subscribe({
      next: (res) => {
        if (res.ind_firma === 1 || res.ind_firma === 3) {
          this.messageService.showMessage(
            'I009',
            'containerUploadDocumenti',
            false
          );
        }
        this.spinner.hide();
        this.annullaInserisciAllegato(true);

        // SCRIVA-1506 => Per gestire la tracciatura delle modifiche utente, emetto un evento specifico
        this.onFileSingoloCaricato(data);
        // #
      },
      error: (err) => {
        if (err.error?.code) {
          this.messageService.showMessage(
            err.error.code,
            'containerUploadDocumenti',
            false,
            null,
            err.error.title ? err.error.title : null
          );
        } else {
          this.messageService.showMessage(
            'E100',
            'containerUploadDocumenti',
            true,
            null,
            err.error?.title ? err.error.title : null
          );
        }
        this.annullaInserisciAllegato(false);
      },
    });
  }

  multipleUpload(data, files) {
    const allowedFileList = this.checkFileExtensions(files);
    if (allowedFileList.length === 0) {
      console.log('# No file passed extension check.');
      return;
    }

    let firmaInvalid = false;
    const firstFile = allowedFileList.splice(0, 1)[0];
    data.nome_allegato = firstFile.name;

    let elapsedTime = 0;
    const timer = setInterval(() => {
      elapsedTime++;
    }, 1000);

    // Vado a generare un identificativo per il salvataggio dei files
    const idFiles: string = this._scrivaUtilities.generateRandomId();

    this.allegatiService
      .postAllegati(JSON.stringify(data), firstFile)
      .subscribe(
        (resp) => {
          // SCRIVA-1506 => Per gestire la tracciatura delle modifiche utente, emetto un evento specifico
          this.onFileMultiploCaricato(data, idFiles);

          if (resp.ind_firma === 1 || resp.ind_firma === 3) {
            firmaInvalid = true;
          }

          if (allowedFileList.length === 0 || elapsedTime >= 120) {
            this.spinner.hide();
            this.annullaInserisciAllegato(true);
            if (firmaInvalid) {
              this.messageService.showMessage(
                'I012',
                'containerUploadDocumenti',
                false
              );
            }
            if (elapsedTime >= 120) {
              clearInterval(timer);
              this.messageService.showMessage(
                'E020',
                'containerUploadDocumenti',
                false
              );
            }
            return;
          }

          allowedFileList.forEach((file, index) => {
            this.spinner.show();
            data.nome_allegato = file.name;
            this.allegatiService
              .postAllegati(JSON.stringify(data), file)
              .subscribe(
                (res) => {
                  // SCRIVA-1506 => Per gestire la tracciatura delle modifiche utente, emetto un evento specifico
                  this.onFileMultiploCaricato(data, idFiles);

                  if (resp.ind_firma === 1 || resp.ind_firma === 3) {
                    firmaInvalid = true;
                  }

                  if (
                    index === allowedFileList.length - 1 ||
                    elapsedTime >= 120
                  ) {
                    this.spinner.hide();
                    this.annullaInserisciAllegato(true);
                    if (firmaInvalid) {
                      this.messageService.showMessage(
                        'I012',
                        'containerUploadDocumenti',
                        false
                      );
                    }
                    if (elapsedTime >= 120) {
                      this.messageService.showMessage(
                        'E020',
                        'containerUploadDocumenti',
                        false
                      );
                    }
                  }
                },
                (err) => {
                  if (err.error?.code) {
                    if (err.error.code === 'E015') {
                      const swapPh = {
                        ph: '{PH_NOME_FILE}',
                        swap: file.name,
                      };
                      this.messageService.showMessage(
                        err.error.code,
                        'containerUploadDocumenti',
                        false,
                        swapPh,
                        err.error.title ? err.error.title : null
                      );
                    } else {
                      this.messageService.showMessage(
                        err.error.code,
                        'containerUploadDocumenti',
                        false,
                        null,
                        err.error.title ? err.error.title : null
                      );
                    }
                  } else {
                    this.messageService.showMessage(
                      'E100',
                      'containerUploadDocumenti',
                      true,
                      null,
                      err.error?.title ? err.error.title : null
                    );
                  }

                  if (
                    index === allowedFileList.length - 1 ||
                    elapsedTime >= 120
                  ) {
                    this.annullaInserisciAllegato(true);
                    if (firmaInvalid) {
                      this.messageService.showMessage(
                        'I012',
                        'containerUploadDocumenti',
                        false
                      );
                    }
                    if (elapsedTime >= 120) {
                      this.messageService.showMessage(
                        'E020',
                        'containerUploadDocumenti',
                        false
                      );
                    }
                  }
                }
              );
          });
        },
        (error) => {
          if (error.error?.code === 'E015') {
            const swapPh = {
              ph: '{PH_NOME_FILE}',
              swap: firstFile.name,
            };
            this.messageService.showMessage(
              error.error.code,
              'containerUploadDocumenti',
              false,
              swapPh,
              error.error.title ? error.error.title : null
            );
          } else {
            this.messageService.showMessage(
              error.error.code,
              'containerUploadDocumenti',
              false,
              null,
              error.error.title ? error.error.title : null
            );
          }
          this.getAllegatiQuadro();
        }
      );
  }

  checkFileExtensions(fileList) {
    fileList.forEach((file, index) => {
      const split = file.name.split('.');
      const checkExtensions = this.fileExtensionsArray.some(
        (el) => el === split[split.length - 1]
      );
      if (!checkExtensions) {
        fileList.splice(index, 1);
        this.messageService.showMessage(
          'I013',
          'containerUploadDocumenti',
          false
        );
        if (fileList.length > 0) {
          this.checkFileExtensions(fileList);
        } else {
          return [];
        }
      }
    });
    return fileList;
  }

  /**
   *
   * @param getAllegatiQuadro boolean
   */
  annullaInserisciAllegato(getAllegatiQuadro: boolean = false) {
    this.formAllegati.reset();
    this.additionalInfo = null;
    // SCRIVA-1455 il reset deve resettare abilitazione inserimento riferimenti atto
    this.disableBtnRiferimentiAtto = false;
    if (getAllegatiQuadro) {
      this.getAllegatiQuadro();
    }
  }

  /**
   * Funzione che gestisce le logiche al momento della modifica di un allegato.
   * @param allegato Allegato con le informazioni dell'allegato modificato.
   */
  private onAllegatoModificato(allegato: Allegato) {
    // Vado a generare un identificativo per l'azione utente
    const id: string = this._scrivaUtilities.generateRandomId();
    // Definisco l'oggetto di emissione
    const emitData: IACAAllegatoModificato = { id, allegato };
    // Emetto l'oggetto con le informazioni
    this.onAllegatoUpdated$.emit(emitData);
  }

  /**
   * Funzione che apre la modale per la modifica dati relativi all'allegato in input.
   * @param allegato Allegato con le informazioni dell'allegato da modificare.
   * @param readonly boolean come flag che forza la modale in sola lettura. Per default è: false.
   */
  modificaAllegato(row: Allegato, readonly: boolean = false) {
    const modalRef = this.modalService.open(EditAllegatoModalComponent, {
      centered: true,
      scrollable: true,
      backdrop: 'static',
      size: 'xl',
    });

    modalRef.componentInstance.allegatoInEdit = { ...row };
    modalRef.componentInstance.categorie = this.categorie;
    modalRef.componentInstance.fullListTipiAllegato = this.fullListTipiAllegato;
    modalRef.componentInstance.maxNote = this.maxNote;
    modalRef.componentInstance.maxLengths = this.maxLengths;
    modalRef.componentInstance.isFrontOffice = this.isFrontOffice;
    modalRef.componentInstance.oggAppBtnProtocolla = this.oggAppBtnProtocolla;
    modalRef.componentInstance.visualizzazione = readonly;

    modalRef.result
      .then((metaData: Allegato) => {
        this.spinner.show();
        this.allegatiService.updateAllegati(metaData).subscribe({
          next: (res) => {
            this.formAllegati.reset();
            this.setPage();

            // SCRIVA-1506 => Per gestire la tracciatura delle modifiche utente, emetto un evento specifico
            this.onAllegatoModificato(metaData);
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
      })
      .catch((err) => {});
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

  deleteRow(row: Allegato) {
    this.spinner.show();
    this.allegatiService.deleteAllegatoByUuid(row.uuid_index).subscribe(
      (res) => {
        this.getAllegatiQuadro();
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
      id_allegato_istanza: row.id_allegato_istanza,
      id_istanza: this.idIstanza,
      flg_cancellato: true,
      data_cancellazione: new Date().toLocaleDateString() + ' 02:00:00',
    };

    this.allegatiService.updateAllegati(request).subscribe(
      (res) => {
        this.getAllegatiQuadro();
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

  getAllegatiQuadro() {
    this.spinner.show();
    this.allegatiService.getAllAllegatiIstanza(this.idIstanza).subscribe(
      (res) => {
        this.buildDataQuadro(res);
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

  buildDataQuadro(allegatiFullList: Allegato[]) {
    const docSYS = allegatiFullList.filter(
      (doc) =>
        doc.tipologia_allegato.categoria_allegato.cod_categoria_allegato ===
          'SYS' &&
        doc.tipologia_allegato.cod_tipologia_allegato.includes('ELENCO_')
    );
    const allegatiUtente = allegatiFullList.filter(
      (doc) =>
        doc.tipologia_allegato.categoria_allegato.cod_categoria_allegato !==
          'SYS' &&
        doc.tipologia_allegato.categoria_allegato.cod_categoria_allegato !==
          'INTEG' &&
        doc.flg_cancellato === false
    );

    // if (docSYS.length === 0 && this.isFrontOffice) {
    //   this.messageService.showMessage('E100', 'containerUploadDocumenti', false);
    //   console.log('# Documenti di sistema non trovati.');
    //   return;
    // }

    let categoryList = [];
    allegatiUtente.forEach((doc) =>
      categoryList.push(
        doc.tipologia_allegato.categoria_allegato.des_categoria_allegato
      )
    );
    categoryList = categoryList.filter(
      (cat, index) => categoryList.indexOf(cat) === index
    );

    const riepilogoAllegati = [];
    categoryList.forEach((cat) => {
      riepilogoAllegati.push({
        categoria: cat,
        count: 0,
      });
    });
    riepilogoAllegati.forEach((el) => {
      allegatiUtente.forEach((allegato) => {
        if (
          allegato.tipologia_allegato.categoria_allegato
            .des_categoria_allegato === el.categoria
        ) {
          el.count++;
        }
      });
    });

    const documentiDiSistema = [];
    docSYS.forEach((doc) => {
      const dataFullSplit = doc.data_upload.split(' ');
      const dataSplit = dataFullSplit[0].split('-');
      const dataUpload = dataSplit[2] + '/' + dataSplit[1] + '/' + dataSplit[0];
      const timeUpload = dataFullSplit[1];

      documentiDiSistema.push({
        nomeDocumento: doc.nome_allegato,
        dataDocumento: dataUpload + ' ' + timeUpload,
        codAllegato: doc.cod_allegato,
        codTipologia: doc.tipologia_allegato.cod_tipologia_allegato,
      });
    });

    const nestedBlock = this.codQuadro !== this.codTipoQuadro;
    const dataRiepilogo = {
      numeroAllegatiUtente: allegatiUtente.length,
      riepilogoAllegati: riepilogoAllegati,
      documentiDiSistema,
    };

    if (!this.qdr_riepilogo) {
      this.qdr_riepilogo = {};
    }
    this.qdr_riepilogo[this.codTipoQuadro] = dataRiepilogo;

    const requestDataRiepilogo = {
      id_istanza: this.idIstanza,
      id_template_quadro:
        this.stepManagerService.getIdTemplateQuadroRiepilogo(),
      json_data_quadro: JSON.stringify(this.qdr_riepilogo),
    };

    // TODO remove Recuperare logica da getQuadriStepper di presentazione istanza
    requestDataRiepilogo.id_template_quadro = 212;

    this.saveDataQuadro(requestDataRiepilogo);
  }

  saveDataQuadro(requestDataRiepilogo) {
    this.stepManagerService
      .salvaJsonDataQuadro(requestDataRiepilogo, true)
      .subscribe(
        (res) => {
          this.setPage();
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

  /** end prese da allegati  */

  /**
   * Funzione collegata all'event emitter del componente che gestisce le informazioni degli allegati.
   * @param event { action: any; args?: any } con le informazioni emesse dall'evento.
   */
  emitEventChild(event: { action: any; args?: any }) {
    // Estraggo dall'input le informazioni
    const { action, args } = event ?? {};
    // Verifico se esiste effettivamente l'informazione per l'azione da gestire
    if (!action) {
      // Non è definito il tipo di operazione da gestire, blocco il flusso
      return;
    }

    // Verifico quale evento è stato richiesto di gestire
    switch (action) {
      case 'annullaInserisciAllegatoReload':
        // Richiamo la funzione di gestione
        this.annullaInserisciAllegato(true);
        break;
      // #
      case 'reloadAllegati':
        // Richiamo la funzione di gestione
        this.getAllegatiQuadro();
        break;
      // #
      case CardRiepilogoAllegatiActsion.modificaAllegato:
        // Richiamo la funzione di gestione
        this.modificaAllegato(args);
        break;
      // #
      case CardRiepilogoAllegatiActsion.visualizzaAllegato:
        // Richiamo la funzione di gestione
        this.modificaAllegato(event.args, true);
        break;
      // #
    }
  }

  /**
   * Funzione che rimane in ascolto dell'evento di cancellazione Allegato.
   * @param allegato Allegato cancellato dall'utente.
   */
  onAllegatoDeleted(allegato: Allegato) {
    // Vado a generare un identificativo per l'azione
    const id: string = this._scrivaUtilities.generateRandomId();
    // Definisco l'oggetto di emissione
    const emitData: IACAAllegatoCancellato = { id, allegato };
    // Emetto l'oggetto con le informazioni
    this.onAllegatoDeleted$.emit(emitData);
  }

  /**
   * Funzione che rimane in ascolto dell'evento di pubblicazione/annullamento di una lista di allegati.
   * @param pubblicazione ICRAPubblicazioneAllegati con le informazioni ottenute dalla procedura.
   */
  onAllegatiPubblicati(pubblicazione: ICRAPubblicazioneAllegati) {
    // Vado a generare un identificativo per l'azione
    const id: string = this._scrivaUtilities.generateRandomId();
    // Estraggo dall'input le informazioni per rimapparle
    const { allegati, risultati } = pubblicazione ?? {};
    // Definisco l'oggetto di emissione
    const emitData: IACAAllegatiPubblicati = { id, allegati, risultati };
    // Emetto l'oggetto con le informazioni
    this.onAllegatiPubblicati$.emit(emitData);
  }
}
