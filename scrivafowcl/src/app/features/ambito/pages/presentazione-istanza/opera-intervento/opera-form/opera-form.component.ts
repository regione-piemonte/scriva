import { ScrivaComponentConfig } from './../../../../../../shared/components/form-inputs/test-input-generici/utilities/test-input-form.interfaces';
/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Location } from '@angular/common';
import {
  AfterViewInit,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ColumnMode, SelectionType } from '@swimlane/ngx-datatable';
import { NgxSpinnerService } from 'ngx-spinner';
import { BehaviorSubject, EMPTY, Observable, of } from 'rxjs';
import {
  catchError,
  debounceTime,
  distinctUntilChanged,
  filter,
  map,
  switchMap,
  takeUntil,
  tap,
} from 'rxjs/operators';
import { AutoUnsubscribe } from 'src/app/core/components';
import {
  Catasto,
  CatastoFoglio,
  CatastoParticella,
  CatastoSezione,
  OggettoIstanza,
  OggettoIstanzaAreaProtetta,
  OggettoIstanzaNatura2000,
  OggettoIstanzaVincoloAutorizza,
  Opera,
  QdrOggettoConfig,
  TipologiaOggetto,
  UbicazioneOggettoIstanza,
  VincoloAutorizza,
} from 'src/app/features/ambito/models';
import { AmbitoService } from 'src/app/features/ambito/services';
import { OperaService } from 'src/app/features/ambito/services/opera/opera.service';
import { GeoProvenienzaEnum } from 'src/app/features/ambito/utils';
import {
  Comune,
  Help,
  LOCCSILocation,
  MessaggioUtente,
  Provincia,
} from 'src/app/shared/models';
import { IstanzaSitoNatura } from 'src/app/shared/models/istanza/istanza-sitinatura.model';
import {
  AuthStoreService,
  LocationService,
  MessageService,
  VincoliAutService,
} from 'src/app/shared/services';
import { AttoreGestioneIstanzaEnum } from 'src/app/shared/utils';
import { ScrivaCodesMesseges } from '../../../../../../core/enums/scriva-codes-messages.enums';
import { ScrivaCodTipiAdempimenti } from '../../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import { ScrivaUtilitiesService } from '../../../../../../shared/services/scriva-utilities/scriva-utilities.service';
import { SitiNaturaFormComponent } from './siti-natura-form/siti-natura-form.component';
import { OperaFormConsts } from './utilities/opera-form.consts';
import { GestioniDatiProgetto } from './utilities/opera-form.enums';
import {
  IOperaFormPayload,
  IValidaInfoDettaglio,
} from './utilities/opera-form.interfaces';
import { ScrivaFormInputTypeahead } from 'src/app/shared/components/form-inputs/form-input/utilities/form-input.classes';
import { ScrivaFormBuilderService } from 'src/app/shared/services/form-inputs/scriva-form-builder.service';

enum TabTitleEnum {
  RETE_NATURA = 'RETE_NATURA',
  AREE_PROTETTE = 'AREE_PROTETTE',
  DATI_CATASTALI = 'DATI_CATASTALI',
  VINCOLI = 'VINCOLI',
}

@Component({
  selector: 'app-opera-form',
  templateUrl: './opera-form.component.html',
  styleUrls: ['./opera-form.component.scss'],
})
export class OperaFormComponent
  extends AutoUnsubscribe
  implements OnInit, AfterViewInit
{
  /** OperaFormConsts con le costanti legate al componente. */
  OF_C = new OperaFormConsts();

  /** GestioniDatiProgetto come variabile per l'utilizzo dell'enumeratore all'interno dell'HTML. */
  gestioniDatiProgetto = GestioniDatiProgetto;

  /** Input string con il codice del tipo adempimento su cui si sta lavorando. Questo vale quindi per identificare la macro sezione: VIA, VINCA, etc... */
  @Input() codTipoAdempimento: string;

  @Input() loadAsModal = false;
  @Input() componente:string;
  @Input() attoreGestioneIstanza:string;
  @Input() idAdempimento:number;
  @Input() configOggetto: QdrOggettoConfig;
  @Input() operaEdit: Opera | OggettoIstanza;
  @Input() infoOggettoIstanza: {
    id_oggetto_istanza: number;
    flg_nuovo_oggetto: boolean;
  } = null;
  @Input() elemDiscontinuita: {
    id_oggetto_istanza: number;
    flg_valore: boolean;
    des_note: string;
  } = null;
  @Input() codScriva: string = null;

  @Input() province: Provincia[] = [];
  @Input() tipologieOggetto: TipologiaOggetto[] = [];
  @Input() helpList: Help[];
  @Input() disableBtn:boolean;
  @Input() comuneSingolo;
  @Input() showGeoRefBtn = false;
  @Input() showViaconvinca:boolean;

  @Output() dismiss = new EventEmitter();
  @Output() closeEvent = new EventEmitter();

  @ViewChild('checkboxHeaderTemplate') checkboxHeaderTemplate: TemplateRef<any>;
  @ViewChild('checkboxCellTemplate') checkboxCellTemplate: TemplateRef<any>;
  @ViewChild('azioniTemplate') azioniTemplate: TemplateRef<any>;
  @ViewChild('azioniEditTemplate') azioniEditTemplate: TemplateRef<any>;

  @ViewChild('distanzaTemplate') distanzaTemplate: TemplateRef<any>;

  @ViewChild('ricadenzaHeaderTemplate')
  ricadenzaHeaderTemplate: TemplateRef<any>;
  @ViewChild('ricadenzaTemplate') ricadenzaTemplate: TemplateRef<any>;

  @ViewChild('comuneDatiCatastaliTemplate')
  comuneDatiCatastaliTemplate: TemplateRef<any>;
  @ViewChild('sezioneTemplate') sezioneTemplate: TemplateRef<any>;
  @ViewChild('accatastamentoTemplate') accatastamentoTemplate: TemplateRef<any>;

  @ViewChild('desVincoloTemplate') desVincoloTemplate: TemplateRef<any>;

  gestioneEnum = AttoreGestioneIstanzaEnum;

  isOggettoIstanza: boolean;
  operaForm: FormGroup;
  infoDettaglioForm: FormGroup;

  operaComuniList: Comune[] = [];
  ubicazioniSelected: UbicazioneOggettoIstanza[];
  comuneNotInserted: boolean;
  comuneMancanteErrore: boolean = false;
  ubicazioneRemoved: boolean;
  showDettaglioIndirizzo = false;
  loadingAddress = false;
  searchResultsAddress: LOCCSILocation[] = [];

  flgVisibileNoteAttoPrecedente = false;
  flgMandatoryNoteAttoPrecedente = false;
  labelNoteAttoPrecedente = '';

  ubicazioniTableColumns = [
    {
      label: 'Provincia',
      properties: ['comune.provincia.denom_provincia'],
      colStyles: { 'min-width': '130px' },
    },
    {
      label: 'Comune',
      properties: ['comune.denom_comune'],
      colStyles: { 'min-width': '130px' },
    },
    {
      label: 'Località',
      properties: ['des_localita'],
      colStyles: { 'min-width': '130px' },
    },
    {
      label: 'Indirizzo',
      properties: ['den_indirizzo', 'num_civico'],
      colStyles: {},
    },
  ];
  ubicazioniTableTitle = '*Comuni di interesse';

  showInfoDettaglio = false;
  tabTitles = TabTitleEnum;
  selectedTab: string;

  ColumnMode = ColumnMode;
  tableSelect = SelectionType.checkbox;

  // siti rete Natura 2000
  sitiNaturaList: OggettoIstanzaNatura2000[] = [];
  sitiNaturaColumns;
  sitiNaturaInterferitiColumns;
  sitiNaturaInterferitiColumnsVinca;
  sitiNaturaCheckedList = [];
  sitiNaturaInterferitiCheckedList = [];

  codAdempimento: string;

  // aree protette
  areeProtetteList: OggettoIstanzaAreaProtetta[] = [];
  areeProtetteColumns;
  areeProtetteCheckedList = [];

  // dati catastali
  sezioniList: CatastoSezione[] = [];
  fogliList: CatastoFoglio[] = [];
  particelleList: CatastoParticella[] = [];
  datiCatastaliTableSource;
  datiCatastaliColumns;
  datiCatastaliCheckedList = [];

  // vincoli
  showVincoloLibero = false;
  vincoliList: VincoloAutorizza[] = [];
  vincoloVGEN: VincoloAutorizza;
  vincoliColumns;
  vincoliCheckedList = [];

  showTableSitiNatura = false;
  showTableSitiNaturaInterferiti = false;
  showTableAreeProtette = false;
  showTableDatiCatastali = false;
  showTableVincoli = false;

  sitiNaturaConfig: ScrivaComponentConfig<ScrivaFormInputTypeahead>;
  areeProtetteConfig: ScrivaComponentConfig<ScrivaFormInputTypeahead>;
  comuneCatastoConfig: ScrivaComponentConfig<ScrivaFormInputTypeahead>;
  private ubicazioneOggettoSubject = new BehaviorSubject<
    UbicazioneOggettoIstanza[]
  >([]);
  ubicazioneOggetto$ = this.ubicazioneOggettoSubject.asObservable();

  searchAddress = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(1000),
      distinctUntilChanged(),
      filter((input) => !!input),
      tap(() => (this.loadingAddress = true)),
      switchMap((input) => {
        return this.getAddressFromLOCCSI(input).pipe(
          tap((list) => (this.searchResultsAddress = list)),
          map((list) => {
            const resultStrings = [];
            if (list) {
              const comune: Comune = this.operaForm.get('comune').value;
              let indirizziList;
              if (comune) {
                indirizziList = list.find(
                  (result) => result.name === 'ind_loccsi_civici_full'
                )?.featureCollection.features;
              } else {
                indirizziList = list.find(
                  (result) => result.name === 'ind_loccsi_strade'
                )?.featureCollection.features;
              }
              indirizziList?.forEach((result) =>
                resultStrings.push(result.properties?.loccsi_label)
              );
            }
            return resultStrings;
          })
        );
      }),
      tap(() => (this.loadingAddress = false))
    );

  constructor(
    private fb: FormBuilder,
    private authStoreService: AuthStoreService,
    private ambitoService: AmbitoService,
    private locationService: LocationService,
    private vincoliService: VincoliAutService,
    private messageService: MessageService,
    private operaService: OperaService,
    private spinner: NgxSpinnerService,
    public activeModal: NgbActiveModal,
    private modalService: NgbModal,
    private location: Location,
    private _scrivaUtilities: ScrivaUtilitiesService,
    private scrivaFormBuilderService: ScrivaFormBuilderService
  ) {
    super();
    this.operaForm = fb.group({
      provincia: ['', Validators.required],
      comune: ['', Validators.required],
    });
  }

  ngOnInit() {
    /*** MOCK ***/
    // this.setMockedData();
    /*** --- ***/
    this.isOggettoIstanza =
      this.operaEdit.hasOwnProperty('id_oggetto_istanza') || !this.loadAsModal;
    this.buildForms();

    this.onOperaFormValueChanges();
    // this.checkComuniMultipli();
    this.ubicazioneRemoved = false;
    this.resetForm();
    if (this.isOggettoIstanza) {
      this.onInfoDettaglioFormValueChanges();
      this.getConfigsTypeaheadSitiEAree();
      this.buildDatiCatastaliTableSource();
      //Imposto l'ubicazione iniziale
      this.ubicazioneOggettoSubject.next(this.operaEdit.ubicazione_oggetto);
      //Setto la configurazione della typeahead per il comune catasto
      this.setComuneCatastoConfig();
      this.getInfoDettaglioSelectLists();
      this.setValidatedRecords();
    }

    // get codAdempimento
    const currentUrl: string = this.location.path();
    const parts: string[] = currentUrl.split('/');
    this.codAdempimento = parts[parts.length - 1];

    if (this.showViaconvinca && this.operaEdit && this.operaEdit.id_istanza) {
      //GET Vincoli filtrati
      this.vincoliService
        .getVincoliAutByIstanza(this.operaEdit.id_istanza)
        .subscribe(
          (res) => {
            const vinVNCS = res.find(
              (item) => item.vincolo_autorizza.cod_vincolo_autorizza === 'VNCS'
            );
            if (vinVNCS) {
              this.infoDettaglioForm.get('valutazioneIncidenza').setValue('si');
            } else {
              this.infoDettaglioForm.get('valutazioneIncidenza').setValue('no');
            }
          },
          (err) => {
            this.infoDettaglioForm.get('valutazioneIncidenza').setValue('no');
          }
        );
    } else {
      this.infoDettaglioForm.get('valutazioneIncidenza').setValue('no');
    }
  }

  ngAfterViewInit(): void {
    this.configInfoDettaglioTables();
    if (this.loadAsModal) {
      const modalContent = document.getElementById('content');
      setTimeout(() => {
        modalContent.scrollTo(0, 0);
      }, 0);
    }
  }

  getInfoDettaglioSelectLists() {
    if (this.configOggetto.dettaglio_oggetto.vincoli?.visibile) {
      this.vincoliService
        .getVincoliAutByAdempimento(this.idAdempimento)
        .subscribe(
          (res) => {
            this.vincoliList = res
              .filter(
                (vincolo) =>
                  vincolo.tipo_vincolo_aut.cod_tipo_vincolo_aut === 'V'
              )
              .filter((vincolo) => vincolo.cod_vincolo_autorizza !== 'VGEN');
            this.vincoloVGEN = res.find(
              (vincolo) => vincolo.cod_vincolo_autorizza === 'VGEN'
            );
          },
          (err) => {
            if (err.error?.code) {
              this.messageService.showMessage(err.error.code, 'content', false);
            } else {
              this.messageService.showMessage('E100', 'content', true);
            }
          }
        );
      // Recupero siti_natura_2000 provenienti da geeco
      if (this.operaService.callSuggestOpera(this.operaEdit)) {
        this.getVincoliIdroByIdOggettoIstanza();
      }
    }
  }

  //Funzione per recuperare la lista dei siti rete natura
  getSitiReteNaturaList() {
    // Recupero siti_natura_2000 provenienti da geeco
    if (this.operaService.callSuggestOpera(this.operaEdit)) {
      this.getSitiReteNatura2000ByIdOggettoIstanza(!this.showViaconvinca);
    }
    return this.ambitoService.getSitiReteNatura2000List().pipe(
      map((siti: OggettoIstanzaNatura2000[]) => {
        this.sitiNaturaList = siti.sort((a, b) =>
          a.des_sito_natura_2000 > b.des_sito_natura_2000
            ? 1
            : b.des_sito_natura_2000 > a.des_sito_natura_2000
            ? -1
            : 0
        );
        return this.sitiNaturaList;
      }),
      catchError((err) => {
        if (err.error?.code) {
          this.messageService.showMessage(err.error.code, 'content', false);
        } else {
          this.messageService.showMessage('E100', 'content', true);
        }
        return of(err);
      })
    );
  }

  //Funzione per recuperare la lista delle aree protette
  getAreeProtetteList() {
    // Recupero aree_protette provenienti da geeco
    if (this.operaService.callSuggestOpera(this.operaEdit)) {
      this.getAreeProtetteByIdOggettoIstanza(!this.showViaconvinca);
    }
    return this.ambitoService.getAreeProtetteList().pipe(
      map((res) => {
        this.areeProtetteList = res.sort((a, b) =>
          a.des_area_protetta > b.des_area_protetta
            ? 1
            : b.des_area_protetta > a.des_area_protetta
            ? -1
            : 0
        );
        return this.areeProtetteList;
      }),
      catchError((err) => {
        if (err.error?.code) {
          this.messageService.showMessage(err.error.code, 'content', false);
        } else {
          this.messageService.showMessage('E100', 'content', true);
        }
        return of(err);
      })
    );
  }

  buildForms() {
    this.buildFormOpera();
    this.buildFormInfoDettaglio();
    this.setValidatorOperaForm();
    if (this.isOggettoIstanza) {
      let configItem;
      if (this.loadAsModal) {
        configItem =
          this.configOggetto?.dettaglio_oggetto?.note_atto_precedente.find(
            (item) =>
              item.ricerca_oggetto === this.configOggetto?.ricerca_oggetto &&
              item.flg_nuovo_oggetto ===
                this.infoOggettoIstanza?.flg_nuovo_oggetto
          );
      } else {
        configItem =
          this.configOggetto?.dettaglio_oggetto?.note_atto_precedente?.find(
            (item) =>
              item.ricerca_oggetto === this.configOggetto.ricerca_oggetto &&
              item.flg_nuovo_oggetto === true
          );
      }

      if (configItem) {
        this.flgVisibileNoteAttoPrecedente = configItem.visibile
          .toString()
          .includes(this.componente);
        this.flgMandatoryNoteAttoPrecedente =
          configItem.obbligatorio && this.flgVisibileNoteAttoPrecedente;
        this.labelNoteAttoPrecedente = configItem.label;
        if (configItem.obbligatorio && this.flgVisibileNoteAttoPrecedente) {
          this.operaForm
            .get('noteAttoPrecedente')
            .setValidators(Validators.required);
        }
      }
      //seleziono il tab in base alla visibilità dei campi dalla configurazione e setto elemDiscontinuita
      this.selectTabInfoDettaglio();
    }

    if (this.disableBtn) {
      this.operaForm.disable();
      this.infoDettaglioForm.disable();
    }
  }

  //Funzione per settare le configurazioni dei siti rete natura e aree protette
  getConfigsTypeaheadSitiEAree() {
    if (this.configOggetto.dettaglio_oggetto.siti_rete_natura?.visibile) {
      // Ottengo la lista dei siti rete natura e configuro la typeahead,
      // la faccio una volta sola perché scarica tutti i siti natura
      this.getSitiReteNaturaList().subscribe(() => {
        this.setConfigSitiNatura();
        // Abilito/disabilito i campi di siti rete natura in base alla valutazione di incidenza
        this.setStatusSitiNatura();
      });
    }
    if (this.configOggetto.dettaglio_oggetto.aree_protette?.visibile) {
      this.getAreeProtetteList().subscribe(() => {
        this.setConfigAreeProtette();
      });
    }
  }

  //Funzione per fare la build del form dell'opera
  buildFormOpera() {
    this.operaForm = this.fb.group({
      denominazione: [this.operaEdit.den_oggetto],
      descrizione: [this.operaEdit.des_oggetto],
      codiceSCRIVA: [{ value: null, disabled: true }],
      codiceFonte: [this.operaEdit.cod_oggetto_fonte],
      codiceUtenza: [this.operaEdit['cod_utenza']],
      noteAttoPrecedente: [this.operaEdit['note_atto_precedente']],
      coordinataX: [
        this.operaEdit.coordinata_x,
        Validators.pattern('(^\\d*$)|(^\\d*[,.]\\d+$)'),
      ],
      coordinataY: [
        this.operaEdit.coordinata_y,
        Validators.pattern('(^\\d*$)|(^\\d*[,.]\\d+$)'),
      ],
      tipologia: [this.operaEdit.tipologia_oggetto],
      provincia: null,
      comune: [{ value: null, disabled: true }],
      address: this.fb.group({
        indirizzo: null,
        civico: [{ value: null, disabled: true }],
        localita: null,
      }),
    });
  }

  //Funzione per fare la build del form del dettaglio
  buildFormInfoDettaglio() {
    this.infoDettaglioForm = this.fb.group({
      sitoReteNatura: null,
      sitoReteNaturaInterferito: null,
      valutazioneIncidenza: null,
      flg_elemento_discontinuita: null,
      des_elemento_discontinuita: null,
      num_distanza: [null, Validators.max(99999)],
      areaProtetta: null,
      comuneCatasto: null,
      sezione: [{ value: null, disabled: true }],
      foglio: [{ value: null, disabled: true }, Validators.pattern('(^\\d*$)')],
      particella: [{ value: null, disabled: true }],
      vincolo: null,
      vincoloLibero: null,
    });
  }

  //Funzione per settare i validator del form dell'opera
  setValidatorOperaForm() {
    let codiceScriva;
    if (this.isOggettoIstanza) {
      codiceScriva = this.codScriva?.startsWith('SCRV') ? null : this.codScriva;
    } else {
      codiceScriva = this.operaEdit['cod_scriva']?.startsWith('SCRV')
        ? null
        : this.operaEdit['cod_scriva'];
    }
    this.operaForm.get('codiceSCRIVA').setValue(codiceScriva);

    if (
      this.configOggetto.dettaglio_oggetto.den_oggetto.obbligatorio &&
      this.configOggetto.dettaglio_oggetto.den_oggetto.visibile
        .toString()
        .includes(this.componente)
    ) {
      this.operaForm
        .get('denominazione')
        .setValidators([Validators.required, Validators.maxLength(500)]);
    }
    if (
      this.configOggetto.dettaglio_oggetto.des_oggetto.obbligatorio &&
      this.configOggetto.dettaglio_oggetto.des_oggetto.visibile
        .toString()
        .includes(this.componente)
    ) {
      this.operaForm
        .get('descrizione')
        .setValidators([Validators.required, Validators.maxLength(2000)]);
    }
    if (
      this.configOggetto.dettaglio_oggetto.cod_oggetto_fonte.obbligatorio &&
      this.configOggetto.dettaglio_oggetto.cod_oggetto_fonte.visibile
        .toString()
        .includes(this.componente)
    ) {
      this.operaForm.get('codiceFonte').setValidators(Validators.required);
    }
    if (
      this.configOggetto.dettaglio_oggetto.cod_utenza.obbligatorio &&
      this.configOggetto.dettaglio_oggetto.cod_utenza.visibile
        .toString()
        .includes(this.componente)
    ) {
      this.operaForm.get('codiceUtenza').setValidators(Validators.required);
    }
    if (
      this.configOggetto.dettaglio_oggetto.coordinata_x.obbligatorio &&
      this.configOggetto.dettaglio_oggetto.coordinata_x.visibile
        .toString()
        .includes(this.componente)
    ) {
      this.operaForm.get('coordinataX').setValidators(Validators.required);
    }
    if (
      this.configOggetto.dettaglio_oggetto.coordinata_y.obbligatorio &&
      this.configOggetto.dettaglio_oggetto.coordinata_y.visibile
        .toString()
        .includes(this.componente)
    ) {
      this.operaForm.get('coordinataY').setValidators(Validators.required);
    }
    if (
      this.configOggetto.dettaglio_oggetto.tipologia_oggetto.obbligatorio &&
      this.configOggetto.dettaglio_oggetto.tipologia_oggetto.visibile
        .toString()
        .includes(this.componente)
    ) {
      this.operaForm.get('tipologia').setValidators(Validators.required);
    }

    // this.operaForm.get('provincia').setValidators(Validators.required);
    if (!!this.operaForm.get('provincia').value) {
      this.operaForm.get('comune').setValidators(Validators.required);
    }
  }

  //Funzione per selezionare il tab in base alla visibilità dei campi e
  // settaggio dell'elemento di discontinuità in base al flg_valore
  selectTabInfoDettaglio() {
    if (this.configOggetto.dettaglio_oggetto.siti_rete_natura?.visibile) {
      this.showInfoDettaglio = true;
      this.selectTab(this.tabTitles.RETE_NATURA);

      if (this.elemDiscontinuita) {
        let value = null;
        if (this.elemDiscontinuita.flg_valore === true) {
          value = 'si';
        } else if (this.elemDiscontinuita.flg_valore === false) {
          value = 'no';
        }
        this.infoDettaglioForm
          .get('flg_elemento_discontinuita')
          .setValue(value);
        this.infoDettaglioForm
          .get('des_elemento_discontinuita')
          .setValue(this.elemDiscontinuita.des_note);
      }
    } else if (this.configOggetto.dettaglio_oggetto.aree_protette?.visibile) {
      this.showInfoDettaglio = true;
      this.selectTab(this.tabTitles.AREE_PROTETTE);
    } else if (this.configOggetto.dettaglio_oggetto.dati_catastali?.visibile) {
      this.showInfoDettaglio = true;
      this.selectTab(this.tabTitles.DATI_CATASTALI);
    } else if (this.configOggetto.dettaglio_oggetto.vincoli?.visibile) {
      this.showInfoDettaglio = true;
      this.selectTab(this.tabTitles.VINCOLI);
    }
  }

  /**
   * Funzione per la configurazione delle typeahead siti rete natura
   */
  setConfigSitiNatura() {
    // clono la lista dei siti rete natura per poi filtrarla
    const lista = [...this.sitiNaturaList];
    // Definisco la funzione di recupero dati alla digitazione
    const typeaheadSearch = (v: string) => {
      const listaFiltrata = lista.filter((item) =>
        item.des_sito_natura_2000.toLowerCase().includes(v.toLowerCase())
      );
      return of(listaFiltrata ? listaFiltrata : []);
    };
    // Definisco la funzione di mapping dati
    const typeaheadMap = (v: OggettoIstanzaNatura2000) => {
      const valore: string =
        v && v.des_sito_natura_2000 ? v.des_sito_natura_2000.trim() : '';
      return valore;
    };

    this.sitiNaturaConfig = this.scrivaFormBuilderService.genInputTypeahead({
      label: this.OF_C.SITO_RETE_NATURA_LABEL,
      showErrorFG: true,
      showErrorFC: true,
      typeaheadSearch,
      typeaheadMap,
      maxLength: 500,
      showOnClick: true,
    });
  }

  //Funzione per la configurazione delle typeahead aree protette
  setConfigAreeProtette() {
    // clono la lista dei siti rete natura per poi filtrarla
    const lista = [...this.areeProtetteList];
    // Definisco la funzione di recupero dati alla digitazione
    const typeaheadSearch = (v: string) => {
      const listaFiltrata = lista.filter((item) =>
        item.des_area_protetta.toLowerCase().includes(v.toLowerCase())
      );
      return of(listaFiltrata ? listaFiltrata : []);
    };
    // Definisco la funzione di mapping dati
    const typeaheadMap = (v: OggettoIstanzaAreaProtetta) => {
      const valore: string =
        v && v.des_area_protetta ? v.des_area_protetta.trim() : '';
      return valore;
    };

    this.areeProtetteConfig = this.scrivaFormBuilderService.genInputTypeahead({
      label: this.OF_C.AREA_PROTETTA_LABEL,
      showErrorFG: true,
      showErrorFC: true,
      typeaheadSearch,
      typeaheadMap,
      maxLength: 500,
      showOnClick: true,
    });
  }
  //Funzione per la configurazione delle typeahead comune catasto
  setComuneCatastoConfig() {
    const typeaheadSearch = (v: string) => {
      return this.ubicazioneOggetto$.pipe(
        map((ubicazioni) =>
          ubicazioni.filter((item) =>
            item.comune.denom_comune.toLowerCase().includes(v.toLowerCase())
          )
        )
      );
    };
    const typeaheadMap = (v: UbicazioneOggettoIstanza) => {
      const valore: string =
        v && v.comune.denom_comune ? v.comune.denom_comune.trim() : '';
      return valore;
    };
    this.comuneCatastoConfig = this.scrivaFormBuilderService.genInputTypeahead({
      label: this.OF_C.COMUNE_LABEL,
      showErrorFG: true,
      showErrorFC: true,
      typeaheadSearch,
      typeaheadMap,
      maxLength: 500,
      showOnClick: true,
    });
  }

  /**
   * Funzione per abilitare o disabilitare i campi dei siti rete natura 2000 e interferiti
   * in base alla valutazione di incidenza.
   * solo se via con vinca altrimenti è tutto abilitato
   */
  setStatusSitiNatura() {
    if (this.showViaconvinca) {
      if (this.infoDettaglioForm.get('valutazioneIncidenza').value == 'si') {
        this.infoDettaglioForm.get('sitoReteNatura').enable();
        this.infoDettaglioForm.get('sitoReteNaturaInterferito').enable();
      } else {
        this.infoDettaglioForm.get('sitoReteNatura').disable();
        this.infoDettaglioForm.get('sitoReteNaturaInterferito').disable();
      }
      this.infoDettaglioForm
        .get('valutazioneIncidenza')
        .valueChanges.subscribe((res) => {
          if (res === 'no') {
            this.infoDettaglioForm.get('sitoReteNatura').disable();
            this.infoDettaglioForm.get('sitoReteNaturaInterferito').disable();
          } else {
            this.infoDettaglioForm.get('sitoReteNatura').enable();
            this.infoDettaglioForm.get('sitoReteNaturaInterferito').enable();
          }
        });
    } else {
        this.infoDettaglioForm.get('sitoReteNatura').enable();
        this.infoDettaglioForm.get('sitoReteNaturaInterferito').enable();
    }
  }

  onOperaFormValueChanges() {
    this.operaForm
      .get('provincia')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        filter((prov) => !!prov),
        distinctUntilChanged(),
        tap(() => {
          this.operaForm.get('address').get('indirizzo').setValue(null);
          this.operaForm.get('address').get('localita').setValue(null);
          this.spinner.show();
        }),
        switchMap((prov: Provincia) => {
          return this.locationService.getComuniByProvincia(prov.cod_provincia);
        })
      )
      .subscribe(
        (res) => {
          this.operaComuniList = res.filter(
            (comune) => !comune.data_fine_validita
          );
          this.operaForm.get('comune').reset();
          this.operaForm.get('comune').enable();
          this.spinner.hide();
        },
        (err) => {
          if (err.error?.code) {
            this.messageService.showMessage(
              err.error.code,
              'aggiungiUbicazioneRow',
              false
            );
          } else {
            this.messageService.showMessage(
              'E100',
              'aggiungiUbicazioneRow',
              true
            );
          }
        }
      );

    this.operaForm
      .get('comune')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        filter((comune) => !!comune),
        distinctUntilChanged()
      )
      .subscribe((comune) => {
        this.operaForm.get('address').get('indirizzo').setValue(null);
        this.operaForm.get('address').get('localita').setValue(null);
      });

    this.operaForm
      .get('address')
      .get('localita')
      .valueChanges.pipe(takeUntil(this.destroy$), distinctUntilChanged())
      .subscribe((localita) => {
        if (localita) {
          this.operaForm.get('address').get('civico').enable();
        } else {
          if (!this.operaForm.get('address').get('indirizzo').value) {
            this.operaForm.get('address').get('civico').reset();
            this.operaForm.get('address').get('civico').disable();
          }
        }
      });

    this.operaForm
      .get('address')
      .get('indirizzo')
      .valueChanges.pipe(takeUntil(this.destroy$), distinctUntilChanged())
      .subscribe((indirizzo) => {
        // nested subscribe()... not very elegant, look for a different rxjs approach
        if (indirizzo) {
          this.operaForm.get('address').get('civico').enable();
          if (
            !this.searchResultsAddress ||
            this.searchResultsAddress.length === 0
          ) {
            return;
          }

          const comune: Comune = this.operaForm.get('comune').value;
          const selectedIndirizzo = this.searchResultsAddress
            .find(
              (result) =>
                result.name ===
                (comune ? 'ind_loccsi_civici_full' : 'ind_loccsi_strade')
            )
            .featureCollection.features?.find(
              (ind) => ind.properties.loccsi_label === indirizzo
            );

          if (comune && selectedIndirizzo?.properties) {
            this.operaForm
              .get('address')
              .get('indirizzo')
              .setValue(
                `${selectedIndirizzo.properties.tipo_via} ${selectedIndirizzo.properties.nome_via}`,
                { emitEvent: false }
              );
            this.operaForm
              .get('address')
              .get('civico')
              .setValue(selectedIndirizzo.properties.civico_num, {
                emitEvent: false,
              });
            if (
              selectedIndirizzo.properties.localita !==
              selectedIndirizzo.properties.comune
            ) {
              this.operaForm
                .get('address')
                .get('localita')
                .setValue(selectedIndirizzo.properties.localita, {
                  emitEvent: false,
                });
            }
          } else if (selectedIndirizzo?.properties) {
            this.spinner.show();
            this.locationService
              .getComuneByCodiceIstat(selectedIndirizzo.properties.codice_istat)
              .subscribe(
                (res) => {
                  const comuneFromBE: Comune = res;
                  const provincia = comuneFromBE.provincia;
                  const check = this.province.some(
                    (p) => p.cod_provincia === provincia.cod_provincia
                  );
                  if (!check) {
                    // todo: set a specific error
                    const text =
                      "L'indirizzo selezionato ricade in una provincia non disponibile per questo procedimento.";
                    this.messageService.showMessage(
                      null,
                      'aggiungiUbicazioneRow',
                      false,
                      null,
                      text
                    );
                    this.operaForm
                      .get('address')
                      .get('indirizzo')
                      .setValue(null);
                    return;
                  }

                  this.operaForm
                    .get('address')
                    .get('indirizzo')
                    .setValue(
                      `${selectedIndirizzo.properties.tipo_via} ${selectedIndirizzo.properties.nome_via}`,
                      { emitEvent: false }
                    );
                  if (
                    selectedIndirizzo.properties.localita !==
                    selectedIndirizzo.properties.comune
                  ) {
                    this.operaForm
                      .get('address')
                      .get('localita')
                      .setValue(selectedIndirizzo.properties.localita, {
                        emitEvent: false,
                      });
                  }
                  this.operaForm
                    .get('provincia')
                    .setValue(provincia, { emitEvent: false });

                  this.locationService
                    .getComuniByProvincia(provincia.cod_provincia)
                    .subscribe(
                      (resp) => {
                        this.operaComuniList = resp;
                        this.operaForm.get('comune').reset();
                        this.operaForm.get('comune').enable();
                        this.operaForm
                          .get('comune')
                          .setValue(comuneFromBE, { emitEvent: false });
                        this.spinner.hide();
                      },
                      (error) => {
                        if (error.error?.code) {
                          this.messageService.showMessage(
                            error.error.code,
                            'aggiungiUbicazioneRow',
                            false
                          );
                        } else {
                          this.messageService.showMessage(
                            'E100',
                            'aggiungiUbicazioneRow',
                            true
                          );
                        }
                      }
                    );
                },
                (err) => {
                  if (err.error?.code) {
                    this.messageService.showMessage(
                      err.error.code,
                      'aggiungiUbicazioneRow',
                      false
                    );
                  } else {
                    this.messageService.showMessage(
                      'E100',
                      'aggiungiUbicazioneRow',
                      true
                    );
                  }
                }
              );
          }
        } else {
          if (!this.operaForm.get('address').get('localita').value) {
            this.operaForm.get('address').get('civico').reset();
            this.operaForm.get('address').get('civico').disable();
          }
        }
      });
  }

  onInfoDettaglioFormValueChanges() {
    // dati catastali
    this.infoDettaglioForm
      .get('comuneCatasto')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        debounceTime(300),
        filter((comuneCatasto) => !!comuneCatasto),
        tap(() => {
          this.sezioniList = [];
          this.fogliList = [];
          this.particelleList = [];
          this.infoDettaglioForm
            .get('sezione')
            .reset(null, { emitEvent: false });
          this.infoDettaglioForm
            .get('foglio')
            .reset(null, { emitEvent: false });
          this.infoDettaglioForm
            .get('particella')
            .reset(null, { emitEvent: false });
        }),
        switchMap((comuneCatasto: UbicazioneOggettoIstanza) => {
          return this.ambitoService.getSezioniCatastaliByComune(
            comuneCatasto.comune.cod_belfiore_comune
          );
        })
      )
      .subscribe(
        (res) => {
          this.sezioniList = res;
          this.infoDettaglioForm.get('sezione').enable({ emitEvent: false });
        },
        (err) => {
          this.infoDettaglioForm.get('sezione').enable({ emitEvent: false });
          if (err.error?.code) {
            this.messageService.showMessage(
              err.error.code,
              'aggiungiCatastoRow',
              false
            );
          } else {
            this.messageService.showMessage('E100', 'aggiungiCatastoRow', true);
          }
        }
      );

    this.infoDettaglioForm
      .get('sezione')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        debounceTime(300),
        distinctUntilChanged(),
        tap((sezioneString) => {
          this.fogliList = [];
          if (!sezioneString) {
            this.infoDettaglioForm
              .get('foglio')
              .reset(null, { emitEvent: false });
            this.infoDettaglioForm.get('foglio').disable({ emitEvent: false });
            this.infoDettaglioForm.get('particella').reset();
            this.infoDettaglioForm.get('particella').disable();
          }
        }),
        switchMap((sezioneString) => {
          if (sezioneString) {
            const sezione = this.sezioniList.find(
              (s) => s.nome_sezione === sezioneString
            );
            if (sezione) {
              return this.ambitoService.getFogliCatastaliBySezione(sezione);
            }
            this.infoDettaglioForm.get('foglio').enable({ emitEvent: false });
          }
          return EMPTY;
        })
      )
      .subscribe(
        (res) => {
          this.fogliList = res;
          this.infoDettaglioForm.get('foglio').enable({ emitEvent: false });
        },
        (err) => {
          this.infoDettaglioForm.get('foglio').enable({ emitEvent: false });
          if (err.error?.code) {
            this.messageService.showMessage(
              err.error.code,
              'aggiungiCatastoRow',
              false
            );
          } else {
            this.messageService.showMessage('E100', 'aggiungiCatastoRow', true);
          }
        }
      );

    this.infoDettaglioForm
      .get('foglio')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        debounceTime(300),
        distinctUntilChanged(),
        tap((foglioString) => {
          this.particelleList = [];
          if (!foglioString) {
            this.infoDettaglioForm.get('particella').reset();
            this.infoDettaglioForm.get('particella').disable();
          }
        }),
        switchMap((foglioString) => {
          if (foglioString) {
            const foglio = this.fogliList.find(
              (f) => f.foglio === foglioString
            );
            if (foglio) {
              return this.ambitoService.getParticelleCatastaliByFoglio(foglio);
            }
            this.infoDettaglioForm.get('particella').enable();
          }
          return EMPTY;
        })
      )
      .subscribe(
        (res) => {
          this.particelleList = res;
          this.infoDettaglioForm.get('particella').enable();
        },
        (err) => {
          this.infoDettaglioForm.get('particella').enable();
          if (err.error?.code) {
            this.messageService.showMessage(
              err.error.code,
              'aggiungiCatastoRow',
              false
            );
          } else {
            this.messageService.showMessage('E100', 'aggiungiCatastoRow', true);
          }
        }
      );
  }

  configInfoDettaglioTables() {
    this.sitiNaturaColumns = [
      {
        name: '',
        sortable: false,
        canAutoResize: false,
        draggable: false,
        resizable: false,
        width: 55,
        minWidth: 55,
        headerTemplate: this.checkboxHeaderTemplate,
        cellTemplate: this.checkboxCellTemplate,
        cellClass: 'checkbox-cell',
      },
      { name: 'Codice', minWidth: 120, prop: 'cod_amministrativo' },
      {
        name: 'Denominazione<br> Siti Rete Natura 2000',
        minWidth: 220,
        prop: 'des_sito_natura_2000',
      },
      {
        name: 'Tipo sito',
        minWidth: 100,
        width: 100,
        prop: 'tipo_natura_2000.des_tipo_natura_2000',
      },
      {
        name: 'Soggetto gestore',
        minWidth: 300,
        prop: 'competenza_territorio.des_competenza_territorio',
      },
      {
        name: 'Azioni',
        sortable: false,
        minWidth: 90,
        maxWidth: 90,
        resizable: false,
        cellTemplate: this.azioniTemplate,
      },
    ];

    this.sitiNaturaInterferitiColumns = [
      {
        name: '',
        sortable: false,
        canAutoResize: false,
        draggable: false,
        resizable: false,
        width: 55,
        minWidth: 55,
        headerTemplate: this.checkboxHeaderTemplate,
        cellTemplate: this.checkboxCellTemplate,
        cellClass: 'checkbox-cell',
      },
      { name: 'Codice', minWidth: 120, prop: 'cod_amministrativo' },
      {
        name: 'Denominazione<br> Siti Rete Natura 2000',
        minWidth: 220,
        prop: 'des_sito_natura_2000',
      },
      {
        name: 'Tipo sito',
        minWidth: 100,
        width: 100,
        prop: 'tipo_natura_2000.des_tipo_natura_2000',
      },
      {
        name: 'Soggetto gestore',
        minWidth: 300,
        prop: 'competenza_territorio.des_competenza_territorio',
      },
      this.configOggetto.dettaglio_oggetto.siti_rete_natura
        ?.elementi_discontinuita?.distanza?.visibile == 'true'
        ? {
            name:
              this.configOggetto.dettaglio_oggetto.siti_rete_natura
                ?.elementi_discontinuita?.distanza?.label || 'Distanza (m)',
            minWidth: 100,
            cellTemplate: this.distanzaTemplate,
            prop: 'num_distanza',
          }
        : null,

      {
        name: 'Azioni',
        sortable: false,
        minWidth: 90,
        maxWidth: 90,
        resizable: false,
        cellTemplate: this.azioniEditTemplate,
      },
    ].filter((column) => column !== null);
    this.sitiNaturaInterferitiColumnsVinca = [
      {
        name: '',
        sortable: false,
        canAutoResize: false,
        draggable: false,
        resizable: false,
        width: 55,
        minWidth: 55,
        headerTemplate: this.checkboxHeaderTemplate,
        cellTemplate: this.checkboxCellTemplate,
        cellClass: 'checkbox-cell',
      },
      {
        name: 'Codice',
        minWidth: 120,
        maxWidth: 120,
        width: 120,
        prop: 'cod_amministrativo',
      },
      {
        name: 'Denominazione<br> Siti Rete Natura 2000',
        minWidth: 220,
        prop: 'des_sito_natura_2000',
      },
      {
        name: 'Tipo sito',
        minWidth: 100,
        width: 100,
        prop: 'tipo_natura_2000.des_tipo_natura_2000',
      },
      {
        name: 'Soggetto gestore',
        minWidth: 300,
        prop: 'competenza_territorio.des_competenza_territorio',
      },
      this.configOggetto.dettaglio_oggetto.siti_rete_natura
        ?.elementi_discontinuita?.distanza?.visibile == 'true'
        ? {
            name:
              this.configOggetto.dettaglio_oggetto.siti_rete_natura
                ?.elementi_discontinuita?.distanza?.label || 'Distanza (m)',
            minWidth: 170,
            maxWidth: 170,
            width: 170,
            prop: 'num_distanza',
          }
        : null,

      {
        name: 'Azioni',
        sortable: false,
        minWidth: 130,
        maxWidth: 130,
        width: 130,
        resizable: false,
        cellTemplate: this.azioniEditTemplate,
      },
    ].filter((column) => column !== null);

    this.areeProtetteColumns = [
      {
        name: '',
        sortable: false,
        canAutoResize: false,
        draggable: false,
        resizable: false,
        width: 55,
        minWidth: 55,
        headerTemplate: this.checkboxHeaderTemplate,
        cellTemplate: this.checkboxCellTemplate,
        cellClass: 'checkbox-cell',
      },
      { name: 'Codice', minWidth: 120, prop: 'cod_amministrativo' },
      {
        name: 'Denominazione<br> Area Naturale Protetta',
        minWidth: 220,
        prop: 'des_area_protetta',
      },
      {
        name: 'Tipo area protetta',
        minWidth: 120,
        width: 120,
        prop: 'tipo_area_protetta.des_tipo_area_protetta',
      },
      {
        name: 'Soggetto gestore',
        minWidth: 300,
        prop: 'competenza_territorio.des_competenza_territorio',
      },
      {
        name: 'Ricadenza',
        minWidth: 300,
        headerTemplate: this.ricadenzaHeaderTemplate,
        cellTemplate: this.ricadenzaTemplate,
      },
      {
        name: 'Azioni',
        sortable: false,
        minWidth: 90,
        maxWidth: 90,
        resizable: false,
        cellTemplate: this.azioniTemplate,
      },
    ];

    this.datiCatastaliColumns = [
      {
        name: '',
        sortable: false,
        canAutoResize: false,
        draggable: false,
        resizable: false,
        width: 55,
        headerTemplate: this.checkboxHeaderTemplate,
        cellTemplate: this.checkboxCellTemplate,
        cellClass: 'checkbox-cell',
      },
      { name: 'Codice Belfiore', minWidth: 100, prop: 'cod_belfiore' },
      {
        name: 'Comune',
        minWidth: 160,
        cellTemplate: this.comuneDatiCatastaliTemplate,
        sortable: false,
      },
      {
        name: 'Sezione',
        minWidth: 100,
        width: 100,
        prop: 'sezione',
        cellTemplate: this.sezioneTemplate,
      },
      { name: 'Foglio', minWidth: 100, width: 100, prop: 'foglio' },
      { name: 'Particella', minWidth: 110, width: 110, prop: 'particella' },
      {
        name: 'Azioni',
        sortable: false,
        minWidth: 90,
        maxWidth: 90,
        resizable: false,
        cellTemplate: this.azioniTemplate,
      },
    ];

    // if (true) {
    if (
      this.configOggetto.dettaglio_oggetto.dati_catastali?.origine_dato?.visibile
        .toString()
        .includes(this.componente)
    ) {
      const accatastamentoCol = {
        name: 'Origine dato',
        minWidth: 160,
        width: 160,
        cellTemplate: this.accatastamentoTemplate,
        sortable: false,
      };
      this.datiCatastaliColumns.splice(6, 0, accatastamentoCol);
    }

    this.vincoliColumns = [
      {
        name: '',
        sortable: false,
        canAutoResize: false,
        draggable: false,
        resizable: false,
        width: 55,
        headerTemplate: this.checkboxHeaderTemplate,
        cellTemplate: this.checkboxCellTemplate,
        cellClass: 'checkbox-cell',
      },
      {
        name: 'Vincolo',
        minWidth: 400,
        cellTemplate: this.desVincoloTemplate,
        sortable: false,
      },
      {
        name: 'Azioni',
        sortable: false,
        minWidth: 90,
        maxWidth: 90,
        resizable: false,
        cellTemplate: this.azioniTemplate,
      },
    ];

    // workaround needed to prevent ExpressionChangedAfterViewCheck error
    setTimeout(() => {
      this.checkTableContents();
    }, 0);
  }

  checkTableContents() {
    this.showTableSitiNatura =
      this.filterSitiReteNatura2000(true)?.length > 0 &&
      this.sitiNaturaColumns?.length > 0;
    this.showTableSitiNaturaInterferiti =
      this.filterSitiReteNatura2000(false)?.length > 0 &&
      this.sitiNaturaInterferitiColumns?.length > 0;
    this.showTableAreeProtette =
      this.operaEdit['aree_protette']?.length > 0 &&
      this.areeProtetteColumns?.length > 0;
    this.showTableDatiCatastali =
      this.datiCatastaliTableSource?.length > 0 &&
      this.datiCatastaliColumns?.length > 0;
    this.showTableVincoli =
      this.operaEdit['vincoli']?.length > 0 && this.vincoliColumns?.length > 0;
  }

  onCheckboxIndirizzo(event) {
    this.showDettaglioIndirizzo = event.target.checked;
    if (!this.showDettaglioIndirizzo) {
      this.operaForm.get('address').get('indirizzo').setValue(null);
      this.operaForm.get('address').get('localita').setValue(null);
    }
  }

  aggiungiUbicazione() {
    const newComune: Comune = this.operaForm.get('comune').value;
    const indirizzo = this.operaForm.get('address').get('indirizzo').value;
    const civico = this.operaForm.get('address').get('civico').value;
    const localita = this.operaForm.get('address').get('localita').value;

    if (!newComune) {
      this.messageService.showMessage('E050', 'aggiungiUbicazioneRow', true);
      return;
    }

    this.comuneMancanteErrore = false;

    const newUbicazione: UbicazioneOggettoIstanza = {
      comune: newComune,
      id_oggetto_istanza: (<OggettoIstanza>this.operaEdit).id_oggetto_istanza,
      ind_geo_provenienza: this.authStoreService.isBoComponent()
        ? GeoProvenienzaEnum.BO
        : GeoProvenienzaEnum.FO,
      des_localita: localita,
      den_indirizzo: indirizzo,
      num_civico: civico,
    };

    let isPresent = false;
    if (this.operaEdit.ubicazione_oggetto) {
      isPresent = this.operaEdit.ubicazione_oggetto.some(
        (ubicazione) =>
          ubicazione.comune.cod_istat_comune === newComune.cod_istat_comune
      );
    } else {
      this.operaEdit.ubicazione_oggetto = [];
    }

    if (isPresent) {
      this.messageService.showMessage('E049', 'aggiungiUbicazioneRow', true);
      return;
    }

    let ubi = [...this.operaEdit.ubicazione_oggetto];
    ubi.push(newUbicazione);
    this.operaEdit.ubicazione_oggetto = ubi;
    //aggiorno lista typeahead ubicazione
    this.ubicazioneOggettoSubject.next(this.operaEdit.ubicazione_oggetto);
    //this.operaEdit.ubicazione_oggetto.push(newUbicazione);
    // this.checkComuniMultipli();
    this.resetForm();
    this.operaForm.markAsDirty();
    this.infoDettaglioForm.markAsDirty();

    if (
      this.configOggetto.dettaglio_oggetto.siti_rete_natura?.visibile &&
      !this.showViaconvinca
    ) {
      this.getSitiReteNaturaSuggeriti();
    }
    if (
      this.configOggetto.dettaglio_oggetto.aree_protette?.visibile &&
      !this.showViaconvinca
    ) {
      this.getAreeProtetteSuggerite();
    }
    if (indirizzo) {
      this.setCoordinates(indirizzo);
    }
  }

  /**
   * Funzione che gestisce la logica quando l'utente cambia il valore per la valutazione d'incidenza (flag VIA con VINCA).
   * @param viaConVincaToSI boolean che definisce se l'utente sta impostando "Sì" come valore del flag. Altrimenti vuol dire che l'utente sta premendo su "No".
   * @author Ismaele Bottelli
   * @date 16/01/2025
   * @jira SCRIVA-1588
   * @notes Effettuo un refactor del codice e c'è da sistemare il fatto dei dati catastali.
   *        Il problema è che il giro sempre complesso perché comprende diversi dati dei COMUNI della tabella iniziale presente per il progetto.
   */
  onValutazioneIncidenzaChange(viaConVincaToSI: boolean) {
    // Verifico cosa sta facendo l'utente a livello di cambiamento informazioni
    if (viaConVincaToSI) {
      // Gestisco la casistica del SI
      this.valutazioneIncidenzaSI();
      // #
    } else {
      // Gestisco la casistica del NO
      this.valutazioneIncidenzaNO();
      // #
    }
  }

  /**
   * Funzione che gestisce la logica quando l'utente cambia il valore per la valutazione d'incidenza (flag VIA con VINCA).
   * La funzione specifica è quando l'utente sta impostando "No" come valore del flag.
   * @author Ismaele Bottelli
   * @date 16/01/2025
   * @jira SCRIVA-1588
   * @notes Effettuo un refactor del codice e c'è da sistemare il fatto dei dati catastali.
   *        Il problema è che il giro sempre complesso perché comprende diversi dati dei COMUNI della tabella iniziale presente per il progetto.
   */
  private valutazioneIncidenzaSI() {
    // Richiamo lo scarico e le logiche per le varie sezioni dati per l'istanza
    this.getSitiReteNatura2000ByIdOggettoIstanza();
    this.getAreeProtetteByIdOggettoIstanza();
    this.getVincoliIdroByIdOggettoIstanza();

    // NOTA BENE: manca la gestione dei dati-catastali, ma le informazioni hanno qualche collegamento con i comuni visibili nella parte superiore dei dati del progetto
    //            bisogna fare attenzione a come gestire le informazioni.
  }

  /**
   * Funzione che gestisce la logica quando l'utente cambia il valore per la valutazione d'incidenza (flag VIA con VINCA).
   * La funzione specifica è quando l'utente sta impostando "No" come valore del flag.
   * @author Ismaele Bottelli
   * @date 16/01/2025
   * @jira SCRIVA-1588
   * @notes Refactor codice.
   */
  private valutazioneIncidenzaNO() {
    // Chiedo conferma all'utente per la gestione della valutazione incidenza
    this.messageService.showConfirmation({
      title: 'Attenzione',
      codMess: 'A054',
      buttons: [
        {
          label: 'ANNULLA',
          type: 'btn-link',
          callback: () => {
            // Re-imposto il valore su "Sì"
            this.infoDettaglioForm
              .get('valutazioneIncidenza')
              .setValue('si', { emitEvent: false });
          },
        },
        {
          label: 'CONFERMA',
          type: 'btn-primary',
          callback: () => {
            // Vado a cancellare solo le informazioni per i siti rete natura 2000
            (<OggettoIstanza>this.operaEdit).siti_natura_2000 = [];
            // Lancio la gestione per il check sulle tabelle per i dati
            this.checkTableContents();
            // #
          },
        },
      ],
    });
  }

  getSitiReteNatura2000ByIdOggettoIstanza(checkComuni: boolean = false) {
    if (
      !('id_oggetto_istanza' in this.operaEdit) ||
      !this.configOggetto.dettaglio_oggetto.siti_rete_natura?.visibile
    ) {
      return;
    }
    this.ambitoService
      .getSitiReteNatura2000ByUbicazioni(
        null,
        this.operaEdit.id_oggetto_istanza,
        checkComuni
      )
      .subscribe(
        (res) => {
          this.compareSuggeriti(res, 'siti_natura_2000');
        },
        (err) => {
          if (err.error?.code) {
            this.messageService.showMessage(err.error.code, 'content', false);
          }
        }
      );
  }

  getAreeProtetteByIdOggettoIstanza(checkComuni: boolean = false) {
    if (
      !('id_oggetto_istanza' in this.operaEdit) ||
      !this.configOggetto.dettaglio_oggetto.aree_protette?.visibile
    ) {
      return;
    }

    this.ambitoService
      .getAreeProtetteByUbicazioni(
        null,
        this.operaEdit.id_oggetto_istanza,
        checkComuni
      )
      .subscribe(
        (res) => {
          this.compareSuggeriti(res, 'aree_protette');
        },
        (err) => {
          if (err.error?.code) {
            this.messageService.showMessage(err.error.code, 'content', false);
          }
        }
      );
  }

  getVincoliIdroByIdOggettoIstanza() {
    if (
      !('id_oggetto_istanza' in this.operaEdit) ||
      !this.configOggetto.dettaglio_oggetto.vincoli?.visibile
    ) {
      return;
    }

    this.vincoliService
      .getVincoliIdroByIdOggettoIstanza(this.operaEdit.id_oggetto_istanza)
      .subscribe(
        (res: VincoloAutorizza[]) => {
          if (!this.operaEdit['vincoli']) {
            this.operaEdit['vincoli'] = [];
          }
          res.forEach((vincolo: VincoloAutorizza) => {
            if (
              !this.operaEdit['vincoli'].find(
                (i) =>
                  i.vincolo_autorizza.cod_vincolo_autorizza ===
                  vincolo.cod_vincolo_autorizza
              )
            ) {
              const nuovoVincolo: OggettoIstanzaVincoloAutorizza = {
                oggetto_istanza: {
                  id_oggetto_istanza:
                    (<OggettoIstanza>this.operaEdit).id_oggetto_istanza || null,
                  id_istanza: (<OggettoIstanza>this.operaEdit).id_istanza,
                },
                vincolo_autorizza: vincolo,
                des_vincolo_calcolato: null,
                des_ente_utente: null,
                des_email_pec_ente_utente: null,
                to_be_validated: true,
              };
              this.operaEdit['vincoli'].push(nuovoVincolo);
            }
          });
          this.checkTableContents();
        },
        (err) => {
          if (err.error?.code) {
            this.messageService.showMessage(err.error.code, 'content', false);
          }
        }
      );
  }

  getDatiCatastali() {
    if (
      !('id_oggetto_istanza' in this.operaEdit) ||
      !this.configOggetto.dettaglio_oggetto.dati_catastali?.visibile
    ) {
      return;
    }

    // this.ambitoService
    //   .getOggettiIstanzaByIdOggettoIstanza(this.operaEdit.id_oggetto_istanza)
    //   .subscribe((res: OggettoIstanza[]) => {
    //     if (res[0].ubicazione_oggetto) {
    //       // aggiorno le ubicazioni per aggiornare i dati catastali
    //       (<OggettoIstanza>this.operaEdit).ubicazione_oggetto =
    //         res[0].ubicazione_oggetto;
    //       this.buildDatiCatastaliTableSource();
    //     }
    //   });
  }

  onSelectedUbicazione(event) {
    if (!this.propostaComuneDaGeo) {
      return;
    }
    this.ubicazioniSelected = event;
  }

  onRimuoviUbicazione(event) {
    const datoCatastale = this.datiCatastaliTableSource?.some(
      (catasto) =>
        catasto.id_ubica_oggetto_istanza ===
          event.record.id_ubicazione_oggetto_istanza ||
        catasto.cod_istat_comune === event.record.comune.cod_istat_comune
    );

    if (!datoCatastale) {
      this.rimuoviUbicazione(event);
    } else {
      const newMessDesc = this.messageService.messaggi
        .find((m) => m.cod_messaggio === 'A033')
        .des_testo_messaggio.replace(
          '{PH_COMUNE}',
          event.record.comune.denom_comune
        );
      this.messageService.showConfirmation({
        title: 'Conferma eliminazione',
        codMess: null,
        content: newMessDesc,
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
              for (
                let i = this.datiCatastaliTableSource.length - 1;
                i >= 0;
                i--
              ) {
                if (
                  (this.datiCatastaliTableSource[i].id_ubica_oggetto_istanza &&
                    this.datiCatastaliTableSource[i]
                      .id_ubica_oggetto_istanza ===
                      event.record.id_ubicazione_oggetto_istanza) ||
                  this.datiCatastaliTableSource[i].cod_istat_comune ===
                    event.record.comune.cod_istat_comune
                ) {
                  this.datiCatastaliTableSource.splice(i, 1);
                }
              }
              this.datiCatastaliTableSource = [
                ...this.datiCatastaliTableSource,
              ];
              this.rimuoviUbicazione(event);
              this.messageService.showMessage('P002', 'content', true);
              this.checkTableContents();
            },
          },
        ],
      });
    }
  }

  rimuoviUbicazione(event) {
    if (this.operaEdit.ubicazione_oggetto[event.index].den_indirizzo) {
      this.operaEdit.coordinata_x = null;
      this.operaEdit.coordinata_y = null;
      this.operaForm.get('coordinataX').reset();
      this.operaForm.get('coordinataY').reset();
    }

    this.operaEdit.ubicazione_oggetto.splice(event.index, 1);
    //aggiorno lista typeahead
    this.ubicazioneOggettoSubject.next(this.operaEdit.ubicazione_oggetto);
    //Controllo se è selezionato il comune appena rimosso, resetto valore del control
    if (
      this.infoDettaglioForm.get('comuneCatasto').value &&
      this.infoDettaglioForm.get('comuneCatasto').value.comune.denom_comune ===
        event.record.comune.denom_comune
    ) {
      this.infoDettaglioForm.get('comuneCatasto').reset();
    }

    // this.checkComuniMultipli();
    this.resetForm();
    this.operaForm.markAsDirty();
    this.infoDettaglioForm.markAsDirty();

    // salvo se ho rimosso ubicazione per mostrare un messaggio opportuno
    this.ubicazioneRemoved = true;

    /*     if (this.configOggetto.dettaglio_oggetto.siti_rete_natura?.visibile) {
      this.getSitiReteNaturaSuggeriti();
    }

    if (this.configOggetto.dettaglio_oggetto.aree_protette?.visibile) {
      this.getAreeProtetteSuggerite();
    } */
  }

  resetForm() {
    this.operaForm.get('provincia').reset();
    this.operaForm.get('comune').reset();
    this.operaForm.get('address').reset();
  }

  checkComuniMultipli() {
    const check = this.operaEdit.ubicazione_oggetto?.some(
      (ubicazione) => !!ubicazione.des_localita || !!ubicazione.den_indirizzo
    );

    if (check) {
      this.operaForm.get('address').reset();
      this.operaForm.get('address').disable();
    } else {
      this.operaForm.get('address').get('localita').enable();
      this.operaForm.get('address').get('indirizzo').enable();
    }
  }

  setCoordinates(indirizzo: string) {
    this.getAddressFromLOCCSI(indirizzo).subscribe(
      (res) => {
        const indirizziSenzaCivico = res.find(
          (result) => result.name === 'ind_loccsi_strade'
        ).featureCollection.features;
        const indirizziConCivico = res.find(
          (result) => result.name === 'ind_loccsi_civici_full'
        ).featureCollection.features;

        let indirizzoFromLOCCSI = null;
        if (indirizziSenzaCivico?.length === 1) {
          indirizzoFromLOCCSI = indirizziSenzaCivico[0];
        }

        if (indirizziConCivico?.length === 1) {
          indirizzoFromLOCCSI = indirizziConCivico[0];
        }

        if (indirizzoFromLOCCSI) {
          const coordinates = indirizzoFromLOCCSI.geometry.coordinates;
          this.ambitoService
            .convertiCoordinate(coordinates[0], coordinates[1])
            .subscribe(
              (resp) => {
                this.operaEdit.coordinata_x = resp.latitudine_destinazione;
                this.operaEdit.coordinata_y = resp.longitudine_destinazione;
              },
              (error) => {
                if (error.error?.code) {
                  this.messageService.showMessage(
                    error.error.code,
                    'aggiungiUbicazioneRow',
                    false
                  );
                } else {
                  this.messageService.showMessage(
                    'E100',
                    'aggiungiUbicazioneRow',
                    true
                  );
                }
              }
            );
        }
      },
      (err) => {
        if (err.error?.code) {
          this.messageService.showMessage(
            err.error.code,
            'aggiungiUbicazioneRow',
            false
          );
        } else {
          this.messageService.showMessage(
            'E100',
            'aggiungiUbicazioneRow',
            true
          );
        }
      }
    );
  }

  getAddressFromLOCCSI(street: string): Observable<LOCCSILocation[]> {
    let searchString = street;
    const comune: Comune = this.operaForm.get('comune').value;
    const civico = this.operaForm.get('address').get('civico').value;

    if (comune) {
      searchString += ` ${comune.denom_comune}`;
    }

    if (civico) {
      searchString += ` ${civico}`;
    }

    return this.locationService.searchAddress(searchString);
  }

  compareProvincia(p1: Provincia, p2: Provincia) {
    return p1 && p2 && p1.cod_provincia === p2.cod_provincia;
  }

  compareComune(c1: Comune, c2: Comune) {
    return c1 && c2 && c1.cod_istat_comune === c2.cod_istat_comune;
  }

  compareTipologia(t1: TipologiaOggetto, t2: TipologiaOggetto) {
    return t1 && t2 && t1.id_tipologia_oggetto === t2.id_tipologia_oggetto;
  }

  /*
   * Informazioni di dettaglio
   */

  selectTab(tab: string) {
    this.selectedTab = tab;
  }

  checkHighlightedRow(row) {
    return {
      'highlighted-row': row.flg_fuori_geometrie || row.to_be_validated,
    };
  }

  setValidatedRecords() {
    (<OggettoIstanza>this.operaEdit).siti_natura_2000?.forEach((sito) => {
      if (!sito.to_be_validated) {
        if (sito.flg_ricade) {
          this.sitiNaturaCheckedList.push(sito);
        } else {
          this.sitiNaturaInterferitiCheckedList.push(sito);
        }
      }
    });

    (<OggettoIstanza>this.operaEdit).aree_protette?.forEach((area) => {
      if (!area.to_be_validated) {
        this.areeProtetteCheckedList.push(area);
      }
    });

    (<OggettoIstanza>this.operaEdit).vincoli?.forEach((vincolo) => {
      if (!vincolo.to_be_validated) {
        this.vincoliCheckedList.push(vincolo);
      }
    });

    // datiCatastaliCheckedList already set in buildDatiCatastaliTableSource()
  }

  compareSuggeriti(
    suggeriti: OggettoIstanzaNatura2000[] | OggettoIstanzaAreaProtetta[],
    arrayName: string,
    forceRicadeTrue: boolean = false
  ) {
    if (this.operaEdit[arrayName]?.length > 0) {
      this.operaEdit[arrayName].forEach((element) => {
        const isSuggested = suggeriti.some(
          (sugg) => sugg.cod_amministrativo === element.cod_amministrativo
        );
        if (!isSuggested) {
          element.flg_fuori_geometrie = true;
        }
      });

      suggeriti.forEach((sugg) => {
        const isPresent = this.operaEdit[arrayName].some(
          (element) => element.cod_amministrativo === sugg.cod_amministrativo
        );
        if (!isPresent) {
          if (forceRicadeTrue) {
            sugg.flg_ricade = true;
          }
          sugg.to_be_validated = true;
          this.operaEdit[arrayName].push(sugg);
        }
      });
    } else {
      suggeriti.forEach((sugg) => {
        sugg.to_be_validated = true;
        if (forceRicadeTrue) {
          sugg.flg_ricade = true;
        }
      });
      if (!this.operaEdit[arrayName]) {
        this.operaEdit[arrayName] = [];
      }
      this.operaEdit[arrayName].push(...suggeriti);
    }
    this.checkTableContents();
  }

  getBannerMessage(arrayName: string): MessaggioUtente {
    let message = null;

    if (arrayName === 'dati_catastali') {
      let showMessage = false;
      (<OggettoIstanza>this.operaEdit).ubicazione_oggetto?.forEach(
        (ubicazione) => {
          const tbv = ubicazione.dati_catastali?.some(
            (catasto) => catasto.to_be_validated
          );
          if (tbv) {
            showMessage = true;
          }
        }
      );
      if (showMessage) {
        message = this.messageService.messaggi.find(
          (mess) =>
            mess.cod_messaggio ===
            this.operaService.getCodeMessageByKey(
              'cod_messaggio_post_geeco_tab_1'
            )
        );
      }
    } else {
      const checkSuggeriti = this.operaEdit[arrayName]?.some(
        (element) => element.to_be_validated
      );
      const checkIncoerenti = this.operaEdit[arrayName]?.some(
        (element) => element.flg_fuori_geometrie
      );

      if (checkSuggeriti && checkIncoerenti) {
        message = this.messageService.messaggi.find(
          (mess) =>
            mess.cod_messaggio ===
            this.operaService.getCodeMessageByKey(
              'cod_messaggio_post_geeco_tab_3'
            )
        );
      } else if (checkSuggeriti) {
        message = this.messageService.messaggi.find(
          (mess) =>
            mess.cod_messaggio ===
            this.operaService.getCodeMessageByKey(
              'cod_messaggio_post_geeco_tab_1'
            )
        );
      } else if (checkIncoerenti) {
        message = this.messageService.messaggi.find(
          (mess) =>
            mess.cod_messaggio ===
            this.operaService.getCodeMessageByKey(
              'cod_messaggio_post_geeco_tab_2'
            )
        );
      } else if (this.ubicazioneRemoved) {
        let showMessage = false;
        // SCRIVA-1273 il messagio deve comparire solo se nell'elenco corrente è presente almeno una riga
        if (
          arrayName === 'siti_natura_2000' &&
          (<OggettoIstanza>this.operaEdit).siti_natura_2000?.length > 0
        ) {
          showMessage = true;
        } else if (
          arrayName === 'aree_protette' &&
          (<OggettoIstanza>this.operaEdit).aree_protette?.length > 0
        ) {
          showMessage = true;
        } else if (
          arrayName === 'vincoli' &&
          (<OggettoIstanza>this.operaEdit).vincoli?.length > 0
        ) {
          showMessage = true;
        }

        if (showMessage) {
          message = this.messageService.messaggi.find(
            (mess) =>
              mess.cod_messaggio ===
              this.operaService.getCodeMessageByKey(
                'cod_messaggio_post_geeco_tab_2'
              )
          );
        }
      }
    }

    return message;
  }

  getHelpBannerText(chiave: string): string {
    const helpBanner = this.helpList?.find(
      (help) =>
        help.tipo_help.cod_tipo_help === 'BNR' &&
        help.chiave_help.includes(chiave)
    );
    if (!helpBanner) {
      return "Errore: il testo di questo paragrafo non è stato trovato. Contattare l'assistenza";
    } else {
      return helpBanner.des_testo_help;
    }
  }

  /* siti rete Natura2000 */
  filterSitiReteNatura2000(flgRicadente: boolean) {
    return (<OggettoIstanza>this.operaEdit).siti_natura_2000?.filter(
      (siti) => siti.flg_ricade === flgRicadente
    );
  }

  getSitiReteNaturaSuggeriti() {
    if (this.operaEdit.ubicazione_oggetto?.length < 1) {
      return;
    }
    this.ambitoService
      .getSitiReteNatura2000ByUbicazioni(
        (<OggettoIstanza>this.operaEdit).ubicazione_oggetto
      )
      .subscribe(
        (res) => {
          this.compareSuggeriti(res, 'siti_natura_2000', true);
        },
        (err) => {
          if (err.error?.code) {
            this.messageService.showMessage(err.error.code, 'content', false);
          } else {
            if (err.status === 404) {
              // todo: check if here or in res block
              // SCRIVA-1089 (<OggettoIstanza>this.operaEdit).siti_natura_2000?.forEach(sito => sito.flg_fuori_geometrie = true);
            } else {
              this.messageService.showMessage('E100', 'content', true);
            }
          }
        }
      );
  }

  aggiungiSitoReteNatura(flgEsterno = false) {
    let sito;
    if (flgEsterno) {
      sito = {
        ...(this.infoDettaglioForm.get('sitoReteNaturaInterferito')
          .value as OggettoIstanzaNatura2000),
        flg_elemento_discontinuita: this.infoDettaglioForm.get(
          'flg_elemento_discontinuita'
        ).value,
        des_elemento_discontinuita: this.infoDettaglioForm.get(
          'des_elemento_discontinuita'
        ).value,
        num_distanza: this.infoDettaglioForm.get('num_distanza').value,
      };
    } else {
      sito = this.infoDettaglioForm.get('sitoReteNatura')
        .value as OggettoIstanzaNatura2000;
    }

    if (!sito) {
      this.messageService.showMessage(
        'E060',
        flgEsterno ? 'aggiungiSitoInterferitoRow' : 'aggiungiSitoRow',
        true
      );
      return;
    }

    // controllo per Siti Rete Natura 2000 Esterno
    if (
      flgEsterno &&
      this.configOggetto.dettaglio_oggetto.siti_rete_natura
        ?.elementi_discontinuita?.visibile == 'true'
    ) {
      if (
        !this.infoDettaglioForm.get('flg_elemento_discontinuita').value ||
        (this.infoDettaglioForm.get('flg_elemento_discontinuita').value ==
          'si' &&
          !this.infoDettaglioForm.get('des_elemento_discontinuita').value) ||
        (!this.infoDettaglioForm.get('num_distanza').value &&
          this.configOggetto.dettaglio_oggetto.siti_rete_natura
            ?.elementi_discontinuita?.distanza?.obbligatorio == 'true')
      ) {
        this.messageService.showMessage(
          'E001',
          'aggiungiSitoInterferitoRow',
          true
        );
        return;
      } else if (
        !this.infoDettaglioForm.get('sitoReteNaturaInterferito').value
      ) {
        this.messageService.showMessage(
          'E060',
          flgEsterno ? 'aggiungiSitoInterferitoRow' : 'aggiungiSitoRow',
          true
        );
        return;
      } else if (this.infoDettaglioForm.get('num_distanza').value > 99999) {
        this.messageService.showMessage(
          'E004',
          flgEsterno ? 'aggiungiSitoInterferitoRow' : 'aggiungiSitoRow',
          true
        );
        return;
      }
    }

    // SCRIVA-1355
    if (
      flgEsterno &&
      this.configOggetto.dettaglio_oggetto.siti_rete_natura
        ?.elementi_discontinuita?.visibile != 'true' &&
      !this.infoDettaglioForm.get('sitoReteNaturaInterferito').value
    ) {
      this.messageService.showMessage(
        'E060',
        flgEsterno ? 'aggiungiSitoInterferitoRow' : 'aggiungiSitoRow',
        true
      );
      return;
    }

    if (!(<OggettoIstanza>this.operaEdit).siti_natura_2000) {
      (<OggettoIstanza>this.operaEdit).siti_natura_2000 = [];
    }

    const presentItem = (<OggettoIstanza>this.operaEdit).siti_natura_2000.find(
      (element) => element.cod_amministrativo === sito.cod_amministrativo
    );
    if (presentItem) {
      if (presentItem.to_be_validated) {
        delete presentItem.to_be_validated;
      } else {
        this.messageService.showMessage(
          'E049',
          flgEsterno ? 'aggiungiSitoInterferitoRow' : 'aggiungiSitoRow',
          true
        );
      }
      return;
    }

    sito.oggetto_istanza = {
      id_oggetto_istanza:
        (<OggettoIstanza>this.operaEdit).id_oggetto_istanza || null,
      id_istanza: (<OggettoIstanza>this.operaEdit).id_istanza,
    };
    sito.flg_ricade = !flgEsterno;

    if (flgEsterno) {
      this.sitiNaturaInterferitiCheckedList.push(sito);
      this.sitiNaturaInterferitiCheckedList = [
        ...this.sitiNaturaInterferitiCheckedList,
      ];
    } else {
      this.sitiNaturaCheckedList.push(sito);
      this.sitiNaturaCheckedList = [...this.sitiNaturaCheckedList];
    }

    (<OggettoIstanza>this.operaEdit).siti_natura_2000.push(sito);
    (<OggettoIstanza>this.operaEdit).siti_natura_2000 = [
      ...(<OggettoIstanza>this.operaEdit).siti_natura_2000,
    ];
    this.infoDettaglioForm.get('sitoReteNatura').reset();
    this.infoDettaglioForm.get('sitoReteNaturaInterferito').reset();
    this.infoDettaglioForm.get('des_elemento_discontinuita').reset();
    this.infoDettaglioForm.get('flg_elemento_discontinuita').reset();
    this.infoDettaglioForm.get('num_distanza').reset();
    this.infoDettaglioForm.markAsDirty();
    this.checkTableContents();
  }

  /**
   * Funzione invocata al check/uncheck del "seleziona tutte le righe" della tabella.
   * La funzione non va a modificare la variabile che tiene traccia delle righe selezionate, va quindi gestita manualmente questo tipo d'informazione.
   * @param selected any[] con la lista di tutti gli oggetti selezionati.
   * @param flag_ricade boolean che distingue i siti rete natura "interferiti" con il valore a [false].
   * @param checkedList any[] con il riferimento all'array delle righe selezionate.
   */
  validaSitoReteNatura2000(
    selected: any[],
    flag_ricade: boolean,
    checkedList: any[]
  ) {
    // Recupero le informazioni per la gestione dei controlli
    let tableSource: any[];
    tableSource = (<OggettoIstanza>this.operaEdit).siti_natura_2000;

    // Per la gestione dei siti rete natura, devo distinguere gli oggetti "interferiti", rispetto al parametro in input (interferiti = flg_ricade a false)
    tableSource
      .filter((element) => element.flg_ricade === flag_ricade)
      .forEach((element) => {
        // Verifico se all'interno della lista degli elementi selezionati è presente l'oggetto ciclato della tabella
        const index = selected.indexOf(element);
        // Se non esiste l'oggetto, allora la riga è da validare (obbligatoria)
        if (index === -1) {
          // Setto il flag di validazione richiesta
          element.to_be_validated = true;
          // #
        } else {
          // L'elemento è presente, verifico se esiste già la proprietà che definisce che l'oggetto è da validare
          if (element.to_be_validated) {
            // Esiste il flag, lo rimuovo dall'oggetto della tabella
            delete element.to_be_validated;
            // #
          }
        }
      });

    // Verifico se è stato fatto il check per la selezione di tutte le righe per gestire le righe selezionate
    const allChecked: boolean = selected?.length > 0;
    // Gestisco manualmente l'aggiornamento dati per l'array dei selezionati - NOTA BENE: destrutturando l'array, mantengo il riferimento ai singoli oggetti, logica sulla quale si basa la tabella
    checkedList = allChecked ? [...selected] : [];

    // Lancio la funzione per definire la form dati come "modificata"
    this.infoDettaglioForm.markAsDirty();
  }

  onChangeDistanza(codAmministrativo: string, value) {
    const sito = (<OggettoIstanza>this.operaEdit).siti_natura_2000.find(
      (element) => element.cod_amministrativo === codAmministrativo
    );

    if (this.checkDistanzaFormat(value)) {
      value.replace(',', '.');
      sito.num_distanza = Number(value);
      (<OggettoIstanza>this.operaEdit).siti_natura_2000 = [
        ...(<OggettoIstanza>this.operaEdit).siti_natura_2000,
      ];
      this.infoDettaglioForm.markAsDirty();
    }
  }

  checkDistanzaFormat(value): boolean {
    const v = Number(value.replace(',', '.'));
    return v >= 0 && v < 100000;
  }

  showDomandaElementiDiscontinuita() {
    return (
      this.filterSitiReteNatura2000(false)?.length > 0 &&
      this.filterSitiReteNatura2000(false).some((item) => !item.to_be_validated)
    );
  }

  /* aree protette */
  getAreeProtetteSuggerite() {
    this.ambitoService
      .getAreeProtetteByUbicazioni(
        (<OggettoIstanza>this.operaEdit).ubicazione_oggetto
      )
      .subscribe(
        (res) => {
          this.compareSuggeriti(res, 'aree_protette');
        },
        (err) => {
          if (err.error?.code) {
            this.messageService.showMessage(err.error.code, 'content', false);
          } else {
            if (err.status === 404) {
              // todo: check if here or in res block
              // SCRIVA-1089 (<OggettoIstanza>this.operaEdit).aree_protette?.forEach(sito => sito.flg_fuori_geometrie = true);
            } else {
              this.messageService.showMessage('E100', 'content', true);
            }
          }
        }
      );
  }

  aggiungiAreaProtetta() {
    const area = this.infoDettaglioForm.get('areaProtetta')
      .value as OggettoIstanzaAreaProtetta;
    if (!area) {
      this.messageService.showMessage('E060', 'aggiungiAreaRow', true);
      return;
    }

    if (!(<OggettoIstanza>this.operaEdit).aree_protette) {
      (<OggettoIstanza>this.operaEdit).aree_protette = [];
    }

    const presentItem = (<OggettoIstanza>this.operaEdit).aree_protette.find(
      (element) => element.cod_amministrativo === area.cod_amministrativo
    );
    if (presentItem) {
      if (presentItem.to_be_validated) {
        delete presentItem.to_be_validated;
      } else {
        this.messageService.showMessage('E049', 'aggiungiAreaRow', true);
      }
      return;
    }

    area.oggetto_istanza = {
      id_oggetto_istanza:
        (<OggettoIstanza>this.operaEdit).id_oggetto_istanza || null,
      id_istanza: (<OggettoIstanza>this.operaEdit).id_istanza,
    };
    area.flg_ricade = null; // todo: check if needed; flg_ricade should be already null for every element in the select list

    this.areeProtetteCheckedList.push(area);
    this.areeProtetteCheckedList = [...this.areeProtetteCheckedList];
    (<OggettoIstanza>this.operaEdit).aree_protette.push(area);
    (<OggettoIstanza>this.operaEdit).aree_protette = [
      ...(<OggettoIstanza>this.operaEdit).aree_protette,
    ];
    this.infoDettaglioForm.get('areaProtetta').reset();
    this.infoDettaglioForm.markAsDirty();
    this.checkTableContents();
  }

  /**
   * Funzione invocata al check/uncheck del "seleziona tutte le righe" della tabella.
   * La funzione non va a modificare la variabile che tiene traccia delle righe selezionate, va quindi gestita manualmente questo tipo d'informazione.
   * @param selected any[] con la lista di tutti gli oggetti selezionati.
   */
  validaAreaProtetta(selected: any[]) {
    // Recupero le informazioni per la gestione dei controlli
    const tableSource: any[] = (<OggettoIstanza>this.operaEdit).aree_protette;
    // Definisco le informazioni per la gestione della verifica
    const params: IValidaInfoDettaglio = {
      tableSource: tableSource,
      selectedFromTable: selected,
    };
    // Richiamo la funzione di validazione delle righe
    this.validaInfoDettaglio(params);

    // Verifico se è stato fatto il check per la selezione di tutte le righe per gestire le righe selezionate
    const allChecked: boolean = selected?.length > 0;
    // Gestisco manualmente l'aggiornamento dati per l'array dei selezionati - NOTA BENE: destrutturando l'array, mantengo il riferimento ai singoli oggetti, logica sulla quale si basa la tabella
    this.areeProtetteCheckedList = allChecked ? [...selected] : [];

    // Lancio la funzione per definire la form dati come "modificata"
    this.infoDettaglioForm.markAsDirty();
  }

  onChangeRicadenza(codAmministrativo: string, flgRicade: string) {
    const area = (<OggettoIstanza>this.operaEdit).aree_protette.find(
      (element) => element.cod_amministrativo === codAmministrativo
    );
    area.flg_ricade = JSON.parse(flgRicade);
    (<OggettoIstanza>this.operaEdit).aree_protette = [
      ...(<OggettoIstanza>this.operaEdit).aree_protette,
    ];
    this.infoDettaglioForm.markAsDirty();
  }

  /* dati catastali */
  buildDatiCatastaliTableSource() {
    this.datiCatastaliTableSource = [];
    (<OggettoIstanza>this.operaEdit).ubicazione_oggetto?.forEach((ubi) => {
      ubi.dati_catastali?.forEach((catasto) => {
        catasto.cod_belfiore = ubi.comune.cod_belfiore_comune;
        this.datiCatastaliTableSource.push(catasto);
        if (!catasto.to_be_validated) {
          this.datiCatastaliCheckedList.push(catasto);
        }
      });
    });
    this._buildDatiCatastaliTableSourceGeeco();
  }

  /**
   * Prendo i dati catastali suggeriti da Geeco e li propongo all'utente
   */
  _buildDatiCatastaliTableSourceGeeco() {
    if (
      this.operaService.callSuggestOpera(this.operaEdit) &&
      'id_oggetto_istanza' in this.operaEdit
    ) {
      let cp: CatastoParticella[] =
        this.operaService.getGeecoOggettiIstanzaCatastoParticella(
          this.operaEdit.id_oggetto_istanza
        );
      cp.forEach((c: CatastoParticella) => {
        const cat: Catasto = {
          id_ubica_oggetto_istanza: null,
          sezione: c.sezione,
          foglio: Number(c.foglio),
          particella: c.particella,
          cod_istat_comune: c.comune.cod_istat_comune,
          ind_fonte_dato: 'S',
          cod_belfiore: c.comune.cod_belfiore_comune,
          to_be_validated: true,
        };
        // Recupero ubicazione per la particella che voglio proporre
        const ubicazione = (<OggettoIstanza>(
          this.operaEdit
        )).ubicazione_oggetto.find(
          (ubi) =>
            ubi.comune.cod_belfiore_comune === c.comune.cod_belfiore_comune
        );
        // per mostrare banner message devo aggiungere il dato catastale al comune corretto
        if (ubicazione) {
          this.datiCatastaliTableSource.push(cat);
          if (!ubicazione.dati_catastali) {
            ubicazione.dati_catastali = [];
          }
          ubicazione.dati_catastali.push(cat);
        }
      });
    }
  }

  getComuneFromUbicazione(row): Comune {
    const ubicazione = (<OggettoIstanza>this.operaEdit).ubicazione_oggetto.find(
      (ubi) =>
        (ubi.id_ubicazione_oggetto_istanza &&
          ubi.id_ubicazione_oggetto_istanza === row.id_ubica_oggetto_istanza) ||
        ubi.comune.cod_istat_comune === row.cod_istat_comune
    );
    return ubicazione?.comune;
  }

  onAggiungiDatiCatastali() {
    const ubicazione: UbicazioneOggettoIstanza =
      this.infoDettaglioForm.get('comuneCatasto').value;
    const sezione = this.infoDettaglioForm.get('sezione').value;
    const foglio = this.infoDettaglioForm.get('foglio').value;
    const particella = this.infoDettaglioForm.get('particella').value;

    if (!ubicazione || !sezione || !foglio || !particella) {
      this.messageService.showMessage('E060', 'aggiungiCatastoRow', false);
      return;
    }

    const checkSezione = this.sezioniList.some(
      (s) => s.nome_sezione === sezione
    );
    const checkFoglio = this.fogliList.some((f) => f.foglio === foglio);
    const checkParticella = this.particelleList.some(
      (p) => p.particella === particella
    );

    if (checkSezione && checkFoglio && checkParticella) {
      this.aggiungiDatiCatastali();
    } else {
      this.messageService.showConfirmation({
        title: 'Attenzione',
        codMess: 'A034',
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
              this.aggiungiDatiCatastali(true);
            },
          },
        ],
      });
    }
  }

  aggiungiDatiCatastali(manual = false) {
    const ubicazione: UbicazioneOggettoIstanza =
      this.infoDettaglioForm.get('comuneCatasto').value;
    const sezione = this.infoDettaglioForm.get('sezione').value;
    const sezioneObj = this.sezioniList.find((s) => s.nome_sezione === sezione);
    const foglio = this.infoDettaglioForm.get('foglio').value;
    const particella = this.infoDettaglioForm.get('particella').value;
    const indFonte = manual ? 'M' : 'S';

    if (isNaN(Number(foglio))) {
      this.messageService.showMessage('E004', 'aggiungiCatastoRow', false);
      // this.messageService.showMessage(null, 'aggiungiCatastoRow', false, null, 'Inserisci un valore numerico per il foglio.');
      return;
    }

    const newCatasto: Catasto = {
      id_ubica_oggetto_istanza:
        ubicazione.id_ubicazione_oggetto_istanza || null,
      sezione: sezioneObj?.sezione || sezione,
      foglio: Number(foglio),
      particella: particella,
      cod_istat_comune: ubicazione.id_ubicazione_oggetto_istanza
        ? null
        : ubicazione.comune.cod_istat_comune,
      ind_fonte_dato: indFonte,
      cod_belfiore: ubicazione.comune.cod_belfiore_comune,
    };

    if (!ubicazione.dati_catastali) {
      ubicazione.dati_catastali = [];
    }
    const presentItem = ubicazione.dati_catastali.find(
      (record) =>
        record.sezione === newCatasto.sezione &&
        record.foglio === newCatasto.foglio &&
        record.particella === newCatasto.particella &&
        ((newCatasto.id_ubica_oggetto_istanza &&
          newCatasto.id_ubica_oggetto_istanza ===
            record.id_ubica_oggetto_istanza) ||
          record.cod_istat_comune === newCatasto.cod_istat_comune)
    );
    if (presentItem) {
      if (presentItem.to_be_validated) {
        delete presentItem.to_be_validated;
      } else {
        this.messageService.showMessage('E049', 'aggiungiCatastoRow', true);
      }
      return;
    }

    ubicazione.dati_catastali.push(newCatasto);
    this.datiCatastaliCheckedList.push(newCatasto);
    this.datiCatastaliCheckedList = [...this.datiCatastaliCheckedList];
    this.datiCatastaliTableSource.push(newCatasto);
    this.datiCatastaliTableSource = [...this.datiCatastaliTableSource];

    this.infoDettaglioForm.get('comuneCatasto').reset();
    this.infoDettaglioForm.get('sezione').reset();
    this.infoDettaglioForm.get('sezione').disable();
    // this.infoDettaglioForm.get('foglio').reset();
    // this.infoDettaglioForm.get('particella').reset();
    setTimeout(() => {
      this.infoDettaglioForm.markAsDirty();
    }, 500);
    this.checkTableContents();
  }

  /**
   * Funzione invocata al check/uncheck del "seleziona tutte le righe" della tabella.
   * La funzione non va a modificare la variabile che tiene traccia delle righe selezionate, va quindi gestita manualmente questo tipo d'informazione.
   * @param selected any[] con la lista di tutti gli oggetti selezionati.
   */
  validaDatoCatastale(selected: any[]) {
    // Recupero le informazioni per la gestione dei controlli
    const tableSource: any[] = this.datiCatastaliTableSource;
    // Definisco le informazioni per la gestione della verifica
    const params: IValidaInfoDettaglio = {
      tableSource: tableSource,
      selectedFromTable: selected,
    };
    // Richiamo la funzione di validazione delle righe (aggiorna i dati per riferimento)
    this.validaInfoDettaglio(params);

    // Verifico se è stato fatto il check per la selezione di tutte le righe per gestire le righe selezionate
    const allChecked: boolean = selected?.length > 0;
    // Gestisco manualmente l'aggiornamento dati per l'array dei selezionati - NOTA BENE: destrutturando l'array, mantengo il riferimento ai singoli oggetti, logica sulla quale si basa la tabella
    this.datiCatastaliCheckedList = allChecked ? [...selected] : [];

    // Lancio la funzione per definire la form dati come "modificata"
    this.infoDettaglioForm.markAsDirty();
  }

  /* vincoli */
  onInserisciNuovoVincolo() {
    this.showVincoloLibero = true;
  }

  aggiungiVincolo(flgVincoloLibero = false) {
    if (!(<OggettoIstanza>this.operaEdit).vincoli) {
      (<OggettoIstanza>this.operaEdit).vincoli = [];
    }

    if (!flgVincoloLibero) {
      const vincolo = this.infoDettaglioForm.get('vincolo').value;
      if (!vincolo) {
        this.messageService.showMessage('E060', 'aggiungiVincoloRow', true);
        return;
      }

      const presentItem = (<OggettoIstanza>this.operaEdit).vincoli.find(
        (element) =>
          element.vincolo_autorizza.id_vincolo_autorizza ===
          vincolo.id_vincolo_autorizza
      );
      if (presentItem) {
        if (presentItem.to_be_validated) {
          delete presentItem.to_be_validated;
        } else {
          this.messageService.showMessage('E049', 'aggiungiVincoloRow', true);
        }
        return;
      }

      const nuovoVincolo: OggettoIstanzaVincoloAutorizza = {
        oggetto_istanza: {
          id_oggetto_istanza:
            (<OggettoIstanza>this.operaEdit).id_oggetto_istanza || null,
          id_istanza: (<OggettoIstanza>this.operaEdit).id_istanza,
        },
        vincolo_autorizza: vincolo,
        des_vincolo_calcolato: null,
        des_ente_utente: null,
        des_email_pec_ente_utente: null,
      };

      this.vincoliCheckedList.push(nuovoVincolo);
      this.vincoliCheckedList = [...this.vincoliCheckedList];
      (<OggettoIstanza>this.operaEdit).vincoli.push(nuovoVincolo);
      (<OggettoIstanza>this.operaEdit).vincoli = [
        ...(<OggettoIstanza>this.operaEdit).vincoli,
      ];
      this.infoDettaglioForm.get('vincolo').reset();
    } else {
      const desVincoloLibero =
        this.infoDettaglioForm.get('vincoloLibero').value;

      if (!desVincoloLibero) {
        this.messageService.showMessage(
          'E060',
          'aggiungiVincoloLiberoRow',
          true
        );
        return;
      }

      const presentItem = (<OggettoIstanza>this.operaEdit).vincoli.find(
        (vincolo) =>
          vincolo.des_vincolo_calcolato === desVincoloLibero ||
          vincolo.vincolo_autorizza.des_vincolo_autorizza === desVincoloLibero
      );

      if (presentItem) {
        if (presentItem.to_be_validated) {
          delete presentItem.to_be_validated;
        } else {
          this.messageService.showMessage(
            'E049',
            'aggiungiVincoloLiberoRow',
            true
          );
        }
        return;
      }

      const nuovoVincolo: OggettoIstanzaVincoloAutorizza = {
        oggetto_istanza: {
          id_oggetto_istanza:
            (<OggettoIstanza>this.operaEdit).id_oggetto_istanza || null,
          id_istanza: (<OggettoIstanza>this.operaEdit).id_istanza,
        },
        vincolo_autorizza: this.vincoloVGEN,
        des_vincolo_calcolato: desVincoloLibero,
        des_ente_utente: null,
        des_email_pec_ente_utente: null,
      };

      this.vincoliCheckedList.push(nuovoVincolo);
      this.vincoliCheckedList = [...this.vincoliCheckedList];
      (<OggettoIstanza>this.operaEdit).vincoli.push(nuovoVincolo);
      (<OggettoIstanza>this.operaEdit).vincoli = [
        ...(<OggettoIstanza>this.operaEdit).vincoli,
      ];
      this.infoDettaglioForm.get('vincoloLibero').reset();
    }
    this.infoDettaglioForm.markAsDirty();
    this.showVincoloLibero = false;
    this.checkTableContents();
  }

  /**
   * Funzione invocata al check/uncheck del "seleziona tutte le righe" della tabella.
   * La funzione non va a modificare la variabile che tiene traccia delle righe selezionate, va quindi gestita manualmente questo tipo d'informazione.
   * @param selected any[] con la lista di tutti gli oggetti selezionati.
   */
  validaVincolo(selected: any[]) {
    // Recupero le informazioni per la gestione dei controlli
    const tableSource: any[] = (<OggettoIstanza>this.operaEdit).vincoli;
    // Definisco le informazioni per la gestione della verifica
    const params: IValidaInfoDettaglio = {
      tableSource: tableSource,
      selectedFromTable: selected,
    };
    // Richiamo la funzione di validazione delle righe
    this.validaInfoDettaglio(params);

    // Verifico se è stato fatto il check per la selezione di tutte le righe per gestire le righe selezionate
    const allChecked: boolean = selected?.length > 0;
    // Gestisco manualmente l'aggiornamento dati per l'array dei selezionati - NOTA BENE: destrutturando l'array, mantengo il riferimento ai singoli oggetti, logica sulla quale si basa la tabella
    this.vincoliCheckedList = allChecked ? [...selected] : [];

    // Lancio la funzione per definire la form dati come "modificata"
    this.infoDettaglioForm.markAsDirty();
  }

  /* cancellazione info dettaglio */
  deleteRow(row, rowIndex) {
    // siti rete natura2000 AND aree protette
    if (row.hasOwnProperty('tipo_area_protetta')) {
      const sitoIndex = (<OggettoIstanza>(
        this.operaEdit
      )).siti_natura_2000?.findIndex(
        (sito) => sito.cod_amministrativo === row.cod_amministrativo
      );
      const areaIndex = (<OggettoIstanza>(
        this.operaEdit
      )).aree_protette?.findIndex(
        (area) => area.cod_amministrativo === row.cod_amministrativo
      );

      if (sitoIndex > -1) {
        (<OggettoIstanza>this.operaEdit).siti_natura_2000.splice(sitoIndex, 1);
        (<OggettoIstanza>this.operaEdit).siti_natura_2000 = [
          ...(<OggettoIstanza>this.operaEdit).siti_natura_2000,
        ];
      }
      if (areaIndex > -1) {
        (<OggettoIstanza>this.operaEdit).aree_protette.splice(areaIndex, 1);
        (<OggettoIstanza>this.operaEdit).aree_protette = [
          ...(<OggettoIstanza>this.operaEdit).aree_protette,
        ];
      }
    }

    // siti rete natura2000
    if (
      !row.hasOwnProperty('tipo_area_protetta') &&
      row.hasOwnProperty('des_sito_natura_2000')
    ) {
      const sitoIndex = (<OggettoIstanza>(
        this.operaEdit
      )).siti_natura_2000?.findIndex(
        (sito) => sito.cod_amministrativo === row.cod_amministrativo
      );

      if (sitoIndex > -1) {
        (<OggettoIstanza>this.operaEdit).siti_natura_2000.splice(sitoIndex, 1);
        (<OggettoIstanza>this.operaEdit).siti_natura_2000 = [
          ...(<OggettoIstanza>this.operaEdit).siti_natura_2000,
        ];
      }
    }

    // dati catastali
    if (row.hasOwnProperty('id_ubica_oggetto_istanza')) {
      const ubicazione = (<OggettoIstanza>(
        this.operaEdit
      )).ubicazione_oggetto.find(
        (ubi) =>
          ubi.comune.cod_istat_comune === row.cod_istat_comune ||
          ubi.id_ubicazione_oggetto_istanza === row.id_ubica_oggetto_istanza
      );
      const ubiIndex = ubicazione.dati_catastali.findIndex(
        (catasto) =>
          catasto.sezione === row.sezione &&
          catasto.foglio === row.foglio &&
          catasto.particella === row.particella &&
          ((catasto.id_ubica_oggetto_istanza &&
            catasto.id_ubica_oggetto_istanza ===
              row.id_ubica_oggetto_istanza) ||
            catasto.cod_istat_comune === row.cod_istat_comune)
      );
      ubicazione.dati_catastali.splice(ubiIndex, 1);
      this.datiCatastaliTableSource.splice(rowIndex, 1);
      this.datiCatastaliTableSource = [...this.datiCatastaliTableSource];
    }

    // vincoli
    if (row.hasOwnProperty('vincolo_autorizza')) {
      // SCRIVA-1283 des_vincolo_calcolato deve coincidere nullo per i vincoli aggiounti tramite la select
      // e la stringa inserita per quelli di tipo testuale da inserisci nuovo
      const vincoloIndex = (<OggettoIstanza>this.operaEdit).vincoli.findIndex(
        (vincolo) =>
          vincolo.vincolo_autorizza.id_vincolo_autorizza ===
            row.vincolo_autorizza.id_vincolo_autorizza &&
          row.des_vincolo_calcolato == vincolo.des_vincolo_calcolato
      );
      (<OggettoIstanza>this.operaEdit).vincoli.splice(vincoloIndex, 1);
      (<OggettoIstanza>this.operaEdit).vincoli = [
        ...(<OggettoIstanza>this.operaEdit).vincoli,
      ];
    }

    this.infoDettaglioForm.markAsDirty();
    this.checkTableContents();
  }

  /**
   * Click per editare IstanzaSitoNatura in input che lavora su modale
   * @param istanzaSitoNatura IstanzaSitoNatura
   * @param readonly boolean che definisce se aprire in modalità "sola lettura" la modale dati. Per default è: false.
   */
  editSitoNatura(row: IstanzaSitoNatura, readonly: boolean = false) {
    const modalRef = this.modalService.open(SitiNaturaFormComponent, {
      centered: true,
      scrollable: true,
      backdrop: 'static',
      size: 'lg',
    });

    modalRef.componentInstance.readonly = readonly;
    modalRef.componentInstance.sitoNatura = row;
    modalRef.componentInstance.dettaglioOggetto =
      this.configOggetto.dettaglio_oggetto;

    modalRef.result
      .then((sitoNatura) => {
        (<OggettoIstanza>this.operaEdit).siti_natura_2000.find(
          (i) => i.cod_amministrativo == sitoNatura.cod_amministrativo
        ).num_distanza = sitoNatura.num_distanza;
        (<OggettoIstanza>this.operaEdit).siti_natura_2000.find(
          (i) => i.cod_amministrativo == sitoNatura.cod_amministrativo
        ).des_elemento_discontinuita = sitoNatura.des_elemento_discontinuita;
        if (sitoNatura.des_elemento_discontinuita) {
          (<OggettoIstanza>this.operaEdit).siti_natura_2000.find(
            (i) => i.cod_amministrativo == sitoNatura.cod_amministrativo
          ).flg_elemento_discontinuita = 'si';
        } else {
          (<OggettoIstanza>this.operaEdit).siti_natura_2000.find(
            (i) => i.cod_amministrativo == sitoNatura.cod_amministrativo
          ).flg_elemento_discontinuita = 'no';
        }

        if (sitoNatura.flg_elemento_discontinuita != 'si') {
          delete (<OggettoIstanza>this.operaEdit).siti_natura_2000.find(
            (i) => i.cod_amministrativo == sitoNatura.cod_amministrativo
          ).des_elemento_discontinuita;
        }
      })
      .catch();

    this.infoDettaglioForm.markAsDirty();
    this.checkTableContents();
  }

  /* check finali */
  checkInfoDettaglioDaValidare(): string {
    const _operaEdit = this.operaEdit as OggettoIstanza;
    let placeholder = null;

    let faultySitiNatura = false;
    let faultyAreeProtette = false;
    let faultyDatiCatastali = false;
    let faultyVincoli = false;
    const faultyTabs = [];

    faultySitiNatura = _operaEdit.siti_natura_2000?.some(
      (sito) => sito.to_be_validated
    );
    faultyAreeProtette = _operaEdit.aree_protette?.some(
      (area) => area.to_be_validated
    );
    faultyVincoli = _operaEdit.vincoli?.some(
      (catasto) => catasto.to_be_validated
    );

    if (_operaEdit.ubicazione_oggetto) {
      _operaEdit.ubicazione_oggetto.forEach((ubicazione) => {
        ubicazione.dati_catastali?.forEach((catasto) => {
          if (catasto.to_be_validated) {
            faultyDatiCatastali = true;
          }
        });
      });
    }

    if (faultySitiNatura) {
      faultyTabs.push('Siti Rete Natura 2000');
    }

    if (faultyAreeProtette) {
      faultyTabs.push('Aree Naturali Protette');
    }

    if (faultyDatiCatastali) {
      faultyTabs.push('Dati catastali');
    }

    if (faultyVincoli) {
      faultyTabs.push('Vincoli');
    }

    if (faultyTabs.length > 0) {
      faultyTabs.forEach((tab) => {
        if (!placeholder) {
          placeholder = tab;
        } else {
          placeholder += `, ${tab}`;
        }
      });
    }

    return placeholder;
  }

  checkDistanzeAndElementiDiscontinuita(): boolean {
    let check = true;
    if (
      this.configOggetto.dettaglio_oggetto.siti_rete_natura
        ?.elementi_discontinuita?.visibile == 'true' &&
      this.configOggetto.dettaglio_oggetto.siti_rete_natura
        ?.elementi_discontinuita?.obbligatorio == 'true' &&
      this.showDomandaElementiDiscontinuita()
    ) {
      const answer = this.infoDettaglioForm.get(
        'flg_elemento_discontinuita'
      ).value;
      const descr = this.infoDettaglioForm.get(
        'des_elemento_discontinuita'
      ).value;
      if (!answer || (answer === 'si' && !descr)) {
        check = false;
      }
    }

    const invalidDistanzeFields =
      document.getElementsByClassName('invalid-field');
    if (invalidDistanzeFields.length > 0) {
      console.log('# distanza non valida...');
      check = false;
    }
    return check;
  }

  checkInfoDettaglioObbligatorie(): string[] {
    const _operaEdit = this.operaEdit as OggettoIstanza;
    let errorCodeList = [];

    if (
      this.configOggetto.dettaglio_oggetto.siti_rete_natura?.obbligatorio ||
      this.infoDettaglioForm.get('valutazioneIncidenza')?.value == 'si'
    ) {
      if (!(_operaEdit.siti_natura_2000?.length > 0)) {
        errorCodeList.push('E043');
      }
    }

    if (
      this.configOggetto.dettaglio_oggetto.aree_protette?.obbligatorio &&
      this.configOggetto.dettaglio_oggetto.aree_protette?.visibile
    ) {
      if (!(_operaEdit.aree_protette?.length > 0)) {
        errorCodeList.push('E044');
      }
    }

    if (
      this.configOggetto.dettaglio_oggetto.dati_catastali?.obbligatorio &&
      this.configOggetto.dettaglio_oggetto.dati_catastali?.visibile
    ) {
      _operaEdit.ubicazione_oggetto.forEach((ubicazione) => {
        if (!(ubicazione.dati_catastali?.length > 0)) {
          errorCodeList.push('E046');
        }
      });
    }

    if (
      this.configOggetto.dettaglio_oggetto.vincoli?.obbligatorio &&
      this.configOggetto.dettaglio_oggetto.vincoli?.visibile
    ) {
      if (!(_operaEdit.vincoli?.length > 0)) {
        errorCodeList.push('E045');
      }
    }

    if (errorCodeList.length > 0) {
      errorCodeList = [...new Set(errorCodeList)];
    }

    return errorCodeList;
  }

  /**
   * Controllo se ci sono dei comunin non selezionati e stampo errori oppoertuni SCRIVA-1308
   * @returns boolean
   */
  checkComuniUnSelected(): boolean {
    if (!this.ubicazioniSelected || !this.operaEdit.ubicazione_oggetto) {
      return false;
    }
    // SCRIVA-1211
    if (
      this.operaEdit.ubicazione_oggetto.length === 0 ||
      this.ubicazioniSelected.length ===
        this.operaEdit.ubicazione_oggetto.length
    ) {
      return false;
    }
    let hasGeo: boolean = false;
    let hasGeod: boolean = false;
    let hasNormal: boolean = false;
    this.operaEdit.ubicazione_oggetto.forEach((item) => {
      if (item.comune.cod_belfiore_comune) {
        if (
          !this.ubicazioniSelected.find(
            (s) =>
              s.comune.cod_belfiore_comune === item.comune.cod_belfiore_comune
          )
        ) {
          if (item.ind_geo_provenienza === GeoProvenienzaEnum.GEO) {
            hasGeo = true;
          } else if (item.ind_geo_provenienza === GeoProvenienzaEnum.GEOD) {
            hasGeod = true;
          } else {
            hasNormal = true;
          }
        }
      }
    });
    let codMessage = this.operaService.getCodeMessageByKey(
      'cod_messaggio_comuni_1'
    );
    if (hasGeo && hasGeod) {
      codMessage = this.operaService.getCodeMessageByKey(
        'cod_messaggio_comuni_4'
      );
    } else if (hasGeo) {
      codMessage = this.operaService.getCodeMessageByKey(
        'cod_messaggio_comuni_2'
      );
    } else if (hasGeod) {
      codMessage = this.operaService.getCodeMessageByKey(
        'cod_messaggio_comuni_3'
      );
    }
    this.messageService.showMessage(codMessage, 'content', false);
    return true;
  }

  checkComuniAttivi(): string {
    let comuniList = '';
    if (
      !this.operaEdit.ubicazione_oggetto ||
      !(this.operaEdit.ubicazione_oggetto.length > 0)
    ) {
      return comuniList;
    } else {
      this.operaEdit.ubicazione_oggetto.forEach((ubi) => {
        if (ubi.comune.data_fine_validita) {
          if (comuniList.length === 0) {
            comuniList = ubi.comune.denom_comune;
          } else {
            comuniList += `, ${ubi.comune.denom_comune}`;
          }
        }
      });
      return comuniList;
    }
  }

  /* azioni utente */
  confirmCancel() {
    if (this.operaForm.dirty || this.infoDettaglioForm.dirty) {
      this.messageService.showConfirmation({
        title: 'Conferma annullamento modifiche',
        codMess: 'A001',
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
              this.onDismiss();
            },
          },
        ],
      });
    } else {
      this.onDismiss();
    }
  }

  onDismiss() {
    if (this.loadAsModal) {
      this.activeModal.dismiss();
    } else {
      this.dismiss.emit();
    }
  }

  onClose(action: GestioniDatiProgetto) {
    const comuniNonValidi = this.checkComuniAttivi();
    // SCRIVA-1308 mostri direttamente i messaggi dentro il metodo
    if (this.propostaComuneDaGeo && this.checkComuniUnSelected()) {
      return;
    }
    const dettagliObbligatoriErrors = this.checkInfoDettaglioObbligatorie();
    const dettagliDaValidare = this.checkInfoDettaglioDaValidare();
    this.comuneNotInserted =
      this.operaEdit.ubicazione_oggetto &&
      this.operaEdit.ubicazione_oggetto.length > 0 &&
      this.operaEdit.ubicazione_oggetto[0].comune.denom_comune.length > 0;
    this.operaForm.markAllAsTouched();

    if (
      this.operaForm.invalid ||
      !(this.operaEdit.ubicazione_oggetto?.length > 0) ||
      !this.comuneNotInserted
    ) {
      this.comuneMancanteErrore = !!this.operaEdit.ubicazione_oggetto?.length
        ? false
        : true;
      if (this.operaForm.get('comune').value === null) {
        this.operaForm.get('comune').markAsTouched();
      }

      // SCRIVA-1211
      let codMessage = 'E079';
      // Verifico se l'errore è effettivamente sul form
      if (this.operaForm.invalid) {
        // Verifico se l'errore è sull'ubicazione oggetto, mancante o senza elementi
        const existUbiOgg = !!this.operaEdit.ubicazione_oggetto?.length;
        // Verifico se è la casistica
        if (!existUbiOgg) {
          // Modifico il codice d'errore
          codMessage = ScrivaCodesMesseges.E078;
          // #
        } else {
          // E' un altro tipo d'errore, verifico, definisco variabili di comodo
          const f = this.operaForm;
          const required = 'required';
          const maxLenght = 'maxLength';
          // Verifico le condizioni d'errore
          let requiredError: boolean;
          requiredError = this._scrivaUtilities.formHasErrors(f, required);
          // NOTA DELLO SVILUPPATORE: sta roba qui è stata gestita molto male, tecnicamente bisognerebbe mettere le verifiche su tutti i possibili errori che non siano per "required".
          // Se ci saranno segnalazioni puntuali si gestirà in maniera puntuale.

          // Verifico il tipo di errore
          if (requiredError) {
            // Errore di obbligatorietà
            codMessage = ScrivaCodesMesseges.E001;
            // #
          } else {
            // Errore di formattazione dei campi
            codMessage = ScrivaCodesMesseges.E004;
            // #
          }
        }
      }
      // Visualizzo il messaggio d'errore per il codice gestito nelle istruzioni precedenti
      this.messageService.showMessage(codMessage, 'content', true);
      // Blocco il flusso
      return;
    }

    this.comuneMancanteErrore = false;

    if (comuniNonValidi.length > 0) {
      const swapPh = {
        ph: '[{PH_COMUNE}]',
        swap: comuniNonValidi,
      };
      this.messageService.showMessage('E065', 'operaContainer', false, swapPh);
    }

    if (dettagliObbligatoriErrors.length > 0) {
      dettagliObbligatoriErrors.forEach((errorCode) => {
        this.messageService.showMessage(errorCode, 'content', false);
      });
      return;
    }

    if (dettagliDaValidare) {
      const swapPh = {
        ph: '{PH_DES_SEZIONE}',
        swap: dettagliDaValidare,
      };
      this.messageService.showMessage('E042', 'content', false, swapPh);
      return;
    }

    this.updateOggettoIstanzaAndClose(action);
  }

  updateOggettoIstanzaAndClose(action: GestioniDatiProgetto) {
    const coordX = this.operaForm.get('coordinataX').value as string;
    coordX?.replace(',', '.');
    const coordY = this.operaForm.get('coordinataY').value as string;
    coordY?.replace(',', '.');

    this.operaEdit.den_oggetto = this.operaForm.get('denominazione').value;
    this.operaEdit.des_oggetto = this.operaForm.get('descrizione').value;
    this.operaEdit.cod_oggetto_fonte = this.operaForm.get('codiceFonte').value;
    this.operaEdit.coordinata_x = Number(coordX) || null;
    this.operaEdit.coordinata_y = Number(coordY) || null;
    this.operaEdit.tipologia_oggetto = this.operaForm.get('tipologia').value;
    (<OggettoIstanza>this.operaEdit).cod_utenza =
      this.operaForm.get('codiceUtenza').value;
    (<OggettoIstanza>this.operaEdit).note_atto_precedente =
      this.operaForm.get('noteAttoPrecedente').value;
    (<OggettoIstanza>this.operaEdit).siti_natura_2000?.forEach((element) => {
      if (element.flg_fuori_geometrie) {
        delete element.flg_fuori_geometrie;
      }
      //eliminare flg perche' in BE non lo prende SCRIVA-1271
      if (
        element.flg_elemento_discontinuita ||
        element.flg_elemento_discontinuita == null
      ) {
        delete element.flg_elemento_discontinuita;
      }
    });
    (<OggettoIstanza>this.operaEdit).aree_protette?.forEach((element) => {
      if (element.flg_fuori_geometrie) {
        delete element.flg_fuori_geometrie;
      }
    });
    (<OggettoIstanza>this.operaEdit).vincoli?.forEach((element) => {
      if (element.flg_fuori_geometrie) {
        delete element.flg_fuori_geometrie;
      }
    });
    (<OggettoIstanza>this.operaEdit).ubicazione_oggetto.forEach((ubi) => {
      ubi.ind_geo_provenienza = this.authStoreService.isBoComponent()
        ? GeoProvenienzaEnum.BO
        : GeoProvenienzaEnum.FO;
      ubi.dati_catastali?.forEach((element) => {
        delete element.flg_fuori_geometrie;
      });
    });

    /**
     * @author Ismaele Bottelli
     * @date 15/01/2025
     * @jira SCRIVA-1539
     * @notes La collega ha segnalato che la georeferenzazione non avviene nel caso in cui l'utente cambi/inserisca i dati per i comuni.
     *        Aggiungo la gestione per la modifica dei dati relativi ai comuni.
     */
    // Per la gestione del salvataggio delle informazioni potrei aver bisogno di cambiare l'azione
    let actionToExecute: GestioniDatiProgetto;
    // Verifico se l'azione richiesta è quella di georiferimento
    const isActionGeoRef: boolean =
      action === GestioniDatiProgetto.georiferisci;
    // Verifico se l'utente ha effettivamente effettuato delle modifiche, o comunque "toccato", il form dati
    const userTouchForms: boolean =
      this.operaForm.dirty || this.infoDettaglioForm.dirty;

    // Verifico le condizioni per impostare il salvataggio PRE georeferenziazione
    if (isActionGeoRef && userTouchForms) {
      // Vado a modificare l'azione impostando quella di salvataggio e georeferenziazione
      actionToExecute = GestioniDatiProgetto.salvaEGeoriferisci;
      // #
    } else {
      // L'azione è quella passata come parametro
      actionToExecute = action;
      // #
    }

    const data: IOperaFormPayload = {
      action: actionToExecute,
      opera: this.operaEdit,
      codScriva: null,
      elemDiscontinuita: null,
      valutazioneIncidenza: this.infoDettaglioForm.get('valutazioneIncidenza')
        .value,
    };

    const newCodScriva = this.operaForm.get('codiceSCRIVA').value;
    if (newCodScriva && newCodScriva !== this.codScriva) {
      data.codScriva = newCodScriva;
    }

    if (
      this.configOggetto.dettaglio_oggetto.siti_rete_natura
        ?.elementi_discontinuita?.visibile == 'true' &&
      this.showDomandaElementiDiscontinuita()
    ) {
      const answer = this.infoDettaglioForm.get(
        'flg_elemento_discontinuita'
      ).value;
      const descr = this.infoDettaglioForm.get(
        'des_elemento_discontinuita'
      ).value;
      this.elemDiscontinuita = {
        id_oggetto_istanza:
          (<OggettoIstanza>this.operaEdit).id_oggetto_istanza || null,
        flg_valore: answer === 'si',
        des_note: descr,
      };

      data.elemDiscontinuita = this.elemDiscontinuita;
    }

    this.closeEvent.emit(data);
  }

  /**
   * ###################
   * FUNZIONI DI UTILITY
   * ###################
   */

  /**
   * Funzione di verifica per i dati di dettaglio dell'opera.
   * Lo scopo principale della funzione è la verifica dati al check/uncheck di "seleziona tutte le righe" delle tabelle:
   * - Siti Rete Natura 2000;
   * - Aree Naturali Protette;
   * - Dati catastali;
   * - Vincoli;
   * Le informazioni in input verranno modificate per referenza.
   * @param params IValidaInfoDettaglio con le informazioni da verificare/gestire per il controllo.
   */
  private validaInfoDettaglio(params: IValidaInfoDettaglio) {
    // Verifico l'input
    if (!params) {
      // Manca la configurazione
      return;
    }

    // Estraggo dall'input le informazioni per la gestione dati
    const tableSource: any[] = params.tableSource ?? [];
    const selectedFromTable: any[] = params.selectedFromTable ?? [];

    // Ciclo l'array di elementi della tabella
    tableSource.forEach((element: any) => {
      // Verifico se all'interno della lista degli elementi selezionati è presente l'oggetto ciclato della tabella
      const index = selectedFromTable.indexOf(element);
      // Se non esiste l'oggetto, allora la riga è da validare (obbligatoria)
      if (index === -1) {
        // Setto il flag di validazione richiesta
        element.to_be_validated = true;
        // #
      } else {
        // L'elemento è presente, verifico se esiste già la proprietà che definisce che l'oggetto è da validare
        if (element.to_be_validated) {
          // Esiste il flag, lo rimuovo dall'oggetto della tabella
          delete element.to_be_validated;
          // #
        }
      }
    });
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter che ritorna la condizione per il tipo di adempimento: VIA.
   * @returns boolean con il risultato del check.
   */
  get isTipoAdempimentoVIA(): boolean {
    // Verifico se il tipo adempimento è VIA
    return this.codTipoAdempimento === ScrivaCodTipiAdempimenti.VIA;
  }

  /**
   * Getter che verifica il tipo di adempimento su cui si sta lavorando e genera una descrizione, come placeholder, per il campo: descrizione opera (o descrizione sintetica).
   * @returns string con la descrizione da visualizzare.
   */
  get phDescrizioneOpera(): string {
    // Gestisco le casistiche sui tipi adempimenti
    if (this.isTipoAdempimentoVIA) {
      // Ritorno la stringa apposita
      return this.OF_C.phDescrizioneVIA;
      // #
    } else {
      // Casistica non censita/necessaria
      return '';
    }
  }

  /**
   * Getter che verifica il tipo di adempimento su cui si sta lavorando e genera una lista di string, come classi css, da assegnare al campo.
   * @returns string[] con le classi di stile d'applicare al campo.
   */
  get classesDescrizioneOpera(): string[] {
    // Definisco le classi di stile
    const noPhOnFocus: string = 'no-placeholder-on-focus';

    // Gestisco le casistiche sui tipi adempimenti
    if (this.isTipoAdempimentoVIA) {
      // Ritorno la stringa apposita
      return [noPhOnFocus];
      // #
    } else {
      // Casistica non censita/necessaria
      return [];
    }
  }

  /**
   * Getter che ritorna proposta_comune_da_geo della configurazione QDR_OGGETTO proposta_comune_da_geo
   * @returns boolean con il risultato del check.
   */
  get propostaComuneDaGeo(): boolean {
    return this.configOggetto?.proposta_comune_da_geo ?? false;
  }
}
