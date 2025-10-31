/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { forkJoin, Observable, of, Subject } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import {
  DataQuadro,
  Istanza,
  Quadro,
  Template,
  TemplateQuadro,
  TemplateQuadroJoinReq,
} from '../../models';
import { AppConfigService } from '../app-config.service';
import { MessageService } from '../message/message.service';
import { QDR_DER_CAPTAZIONE_DEBUG } from './formio/quadro/QDR_DER_CAPTAZIONE.formio';
import { QDR_DER_ALTRIUSI_RIEPILOGO_DEBUG } from './formio/riepilogo/QDR_DER_ALTRIUSI.riepilogo.formio';
import { IClickOnStepper } from './utilities/step-manager.interfaces';

@Injectable()
export class StepManagerService {
  
  idTemplateQuadroRiepilogo: number;

  private beUrl = '';

  /**
   * boolean ad uso esclusivo per lo sviluppo dei quadri formio. La funziona attiva la modifica dei quadri formio nel momento in cui vengono scaricati dal DB.
   * @debug Proprietà di debug. Al rilascio il suo valore deve essere: false.
   */
  private readonly _debugFormIoFlg: boolean = false;

  constructor(
    private _app: AppConfigService,
    private http: HttpClient,
    private config: AppConfigService,
    private messageService: MessageService
  ) {
    this.beUrl = this.config.getBEUrl();
  }

  setIdTemplateQuadroRiepilogo(id: number) {
    this.idTemplateQuadroRiepilogo = id;
  }

  getIdTemplateQuadroRiepilogo(): number {
    return this.idTemplateQuadroRiepilogo;
  }

  getQuadriByTemplate(codeTemplate: string): Observable<Template> {
    return this.http
      .get<Template[]>(
        `${this.beUrl}/template-quadri/code-template/${codeTemplate}`
      )
      .pipe(map((res) => res[0]));
  }

  /**
   * SCRIVA-1416
   * @param codAdempimento string
   * @param istanza Istanza
   * @returns Observable<Template>
   */
  getQuadri(
    codAdempimento: string,
    istanza: Istanza = null
  ): Observable<Template> {
    // Dichiaro una variable che andrà a contenere le logiche da eseguire per lo scarico dei quadri
    let quadriReq: Observable<Template>;

    // Verifico se è stata passata un'istanza come parametro in input
    if (!istanza) {
      // Non c'è l'istanza, recupero i quadri x codice adempimento
      quadriReq = this.getQuadriByAdempimento(codAdempimento);
      // #
    } else {
      // Istanza definita, recupero i quadri specifici per l'istanza
      quadriReq = this.getQuadriByIstanza(istanza);
      // #
    }

    // Verifico se sono in debug dei formio
    if (this.debugFormIoFlgDevelop) {
      // Vado a lanciare le logiche di gestione per il debug dei formio
      return this.debugFormIoTemplate(quadriReq);
    }

    // Ritorno la richiesta per i quadri
    return quadriReq;
  }

  /**
   * SCRIVA-1416
   * @param istanza Istanza
   * @returns Observable<Template>
   */
  getQuadriByIstanza(istanza: Istanza): Observable<Template> {
    return this.http
      .get<{ istanza: Istanza; template: Template }>(
        `${this.beUrl}/istanze-template-quadri/id-istanza/${istanza.id_istanza}`
      )
      .pipe(map((res) => res[0].template));
  }

  /**
   * Funzione che recupera un Template dato il codice adempimento.
   * @param codAdempimento string con il codice adempimento per lo scarico del template.
   * @returns Observable<Template> con la richiesta di scarico dati.
   */
  getQuadriByAdempimento(codAdempimento: string): Observable<Template> {
    // Definisco la variabile che conterrà la richiesta al servizio
    let template: Observable<Template>;

    // Definisco l'url per la chiamata al servizio
    let url: string;
    url = `${this.beUrl}/template-quadri/code-adempimento/${codAdempimento}`;

    // Definisco la richiesta da inviare al servizio (e ritorno solo il primo risultato ottenuto)
    template = this.http.get<Template>(url).pipe(map((res) => res[0]));

    // Verifico se il debug dei quadri formio sono attivi
    if (this.debugFormIoFlgDevelop) {
      // Il debug è attivo, assegno alla richiesta la funzione di gestione
      template = this.debugFormIoTemplate(template);
    }

    // Ritorno la richiesta composta
    return template;
  }

  getQuadro(idTemplateQuadro: number): Observable<Quadro> {
    return this.http
      .get<Template[]>(
        `${this.beUrl}/template-quadri/id-template-quadro/${idTemplateQuadro}`
      )
      .pipe(
        map((res) => {
          return res[0].quadri[0];
        })
      );
  }

  /**
   * Funzione che scarica il TemplateQuadro di un'istanza.
   * @param idIstanza number con l'id istanza da usare per la ricerca.
   * @param idTemplateQuadro number con l'id template quadro da ricercare per l'istanza.
   * @returns Observable<TemplateQuadro> con la risposta del servizio.
   */
  getQuadroByIdIstanza(
    idIstanza: number,
    idTemplateQuadro: number
  ): Observable<TemplateQuadro> {
    // Definisco la variabile che conterrà la richiesta al servizio
    let quadroReq: Observable<TemplateQuadro>;

    // Definisco l'url per la chiamata al servizio
    let url: string;
    url = `${this.beUrl}/istanze-template-quadri/id-istanza/${idIstanza}/id-template-quadro/${idTemplateQuadro}`;

    // Definisco la richiesta da inviare al servizio (e ritorno solo il primo risultato ottenuto)
    quadroReq = this.http.get<TemplateQuadro>(url).pipe(map((res) => res[0]));

    // Verifico se il debug dei quadri formio sono attivi
    if (this.debugFormIoFlgDevelop) {
      // Il debug è attivo, assegno alla richiesta la funzione di gestione
      quadroReq = this.debugFormIoTemplateQuadro(quadroReq);
    }

    // Ritorno la richiesta composta
    return quadroReq;
  }

  getJsonDataByCodTipoQuadro(
    idIstanza: number,
    codTipoQuadro: string
  ): Observable<any> {
    return this.http
      .get<Template[]>(
        `${this.beUrl}/istanze/${idIstanza}/json-data?cod_tipo_quadro=${codTipoQuadro}`
      )
      .pipe(map((res) => res[codTipoQuadro]));
  }

  getJsonDataAll(idIstanza: number): Observable<any> {
    // TODO creare model adHoc?
    return this.http.get<any>(`${this.beUrl}/istanze/${idIstanza}/json-data`);
  }

  salvaJsonDataQuadro(
    data: DataQuadro,
    update = false
  ): Observable<DataQuadro> {
    if (update) {
      return this.http.put<DataQuadro>(
        `${this.beUrl}/istanze-template-quadri`,
        data
      );
    } else {
      return this.http.post<DataQuadro>(
        `${this.beUrl}/istanze-template-quadri`,
        data
      );
    }
  }



  /**
   * ##############
   * DEBUGGING ZONE
   * ##############
   */

  /**
   * Funzione per gli sviluppatori. Questa funzione permette di manipolare il caricamento dei quadri formio, per velocizzare gli sviluppi.
   * @param templateQuadroReq Observable<TemplateQuadro> con la richiesta da manipolare per lo sviluppo dei quadri formio.
   * @returns Observable<TemplateQuadro> con le informazioni manipolate.
   */
  private debugFormIoTemplateQuadro(
    templateQuadroReq: Observable<TemplateQuadro>
  ): Observable<TemplateQuadro> {
    // Ritorno la richiesta con le logiche di manipolazione
    return templateQuadroReq.pipe(
      switchMap((templateQuadro: TemplateQuadro) => {
        // Loggo in console che si sta debuggando
        const t: string = `[FORMIO_DEBUG] debugFormIoTemplateQuadro`;
        console.log(t);

        // Estraggo dall'oggetto di ritorno le singole informazioni
        const { istanza, template, ind_visibile } = templateQuadro;

        // Effettuo una modifica per gestire correttamente il caricamento dei dati, modificando le informazioni del Temaplate
        const templateQuadroJoin: TemplateQuadroJoinReq = {
          istanza: of(istanza),
          template: this.debugFormIoTemplate(of(template)),
          ind_visibile: of(ind_visibile),
        };

        // Lancio la logica di gestione per i formio da sviluppare
        return forkJoin(templateQuadroJoin);
        // #
      })
    );
  }

  /**
   * Funzione per gli sviluppatori. Questa funzione permette di manipolare il caricamento dei quadri formio, per velocizzare gli sviluppi.
   * @param templateReq Observable<Template> con la richiesta da manipolare per lo sviluppo dei quadri formio.
   * @returns Observable<Template> con le informazioni manipolate.
   */
  private debugFormIoTemplate(
    templateReq: Observable<Template>
  ): Observable<Template> {
    // Ritorno la richiesta con le logiche di manipolazione
    return templateReq.pipe(
      switchMap((template: Template) => {
        // Loggo in console che si sta debuggando
        const t: string = `[FORMIO_DEBUG] debugFormIoTemplate`;
        console.log(t);

        // Lancio la logica di gestione per i formio da sviluppare
        return this.debugQDR_LOCALE(template);
        // #
      })
      // switchMap((template: Template) => {
      //   // Loggo in console che si sta debuggando
      //   const t: string = `[FORMIO_DEBUG_RIEPILOGO] debugFormIoTemplate Riepilogo`;
      //   console.log(t);

      //   // Lancio la logica di gestione per i formio da sviluppare
      //   return this.debugQDR_LOCAL_RIEPILOGO(template);
      //   // #
      // })
    );
  }

  /**
   * ################################
   * FUNZIONI DI MODIFICA DATI QUADRI
   * ################################
   */

  /**
   * Funzione di debug per la gestione del debug di un quadro.
   * @param template Template con le informazioni scaricate per i quadri.
   * @returns Observable<Template> con le informazioni manipolate per i quadri.
   */
  private debugQDR_LOCALE(template: Template): Observable<Template> {
    // Definisco le configurazioni per il quadro
    const codQuadro: string = 'QDR_DER_CAPTAZIONE';
    const debugQuadro: any = QDR_DER_CAPTAZIONE_DEBUG;
    // Loggo in console che si sta debuggando
    const t: string = `[FORMIO_DEBUG] debugQDR_LOCALE - ${codQuadro}`;
    console.log(t);

    // Aggiorno l'oggetto del template con le informazioni
    this.changeJsonConfiguraQuadro(template, codQuadro, debugQuadro);

    // Ritorno l'observable con l'oggetto modificato
    return of(template);
    // #
  }

  /**
   * ###################################
   * FUNZIONI DI MODIFICA DATI RIEPILOGO
   * ###################################
   */

  /**
   * Funzione di debug per la gestione di un quadro di riepilogo.
   * @param template Template con le informazioni scaricate per i quadri.
   * @returns Observable<Template> con le informazioni manipolate per i quadri.
   */
  private debugQDR_LOCAL_RIEPILOGO(template: Template): Observable<Template> {
    // Definisco le configurazioni per il quadro
    const codQuadro: string = 'QDR_DER_ALTRIUSI';
    const debugQuadro: any = QDR_DER_ALTRIUSI_RIEPILOGO_DEBUG;
    // Loggo in console che si sta debuggando
    const t: string = `[FORMIO_DEBUG] debugQDR_LOCAL_RIEPILOGO - ${codQuadro}`;
    console.log(t);

    // Aggiorno l'oggetto del template con le informazioni
    this.changeJsonConfiguraRiepilogo(template, codQuadro, debugQuadro);

    // Ritorno l'observable con l'oggetto modificato
    return of(template);
  }

  /**
   * ##########################
   * FUNZIONI DI UTILITY QUADRI
   * ##########################
   */

  /**
   * Funzione che modifica la configurazione json_configura_quadro di un quadro definito in input.
   * La modifica averrà per riferimento sull'oggetto Template.
   * @param template Template con i dati generati per i quadri dell'istanza.
   * @param codQuadro string con il codice del quadro d'andare ad aggiornare.
   * @param debugQuadro any con l'oggetto FormIo del quadro da modificare.
   */
  private changeJsonConfiguraQuadro(
    template: Template,
    codQuadro: string,
    debugQuadro: any
  ) {
    // Definisco la proprietà dell'oggetto Quadro da modificare per il debug
    const quadroProperty: string = 'json_configura_quadro';
    // Richiamo la funzione di aggiornamento dati per la proprietà: json_configura_riepilogo
    this.changeJsonConfiguraParametro(
      template,
      codQuadro,
      debugQuadro,
      quadroProperty
    );
  }

  /**
   * Funzione che modifica la configurazione json_configura_riepilogo di un quadro definito in input.
   * La modifica averrà per riferimento sull'oggetto Template.
   * @param template Template con i dati generati per i quadri dell'istanza.
   * @param codQuadro string con il codice del quadro d'andare ad aggiornare.
   * @param debugQuadro any con l'oggetto FormIo del quadro da modificare.
   */
  private changeJsonConfiguraRiepilogo(
    template: Template,
    codQuadro: string,
    debugQuadro: any
  ) {
    // Definisco la proprietà dell'oggetto Quadro da modificare per il debug
    const quadroProperty: string = 'json_configura_riepilogo';
    // Richiamo la funzione di aggiornamento dati per la proprietà: json_configura_riepilogo
    this.changeJsonConfiguraParametro(
      template,
      codQuadro,
      debugQuadro,
      quadroProperty
    );
  }

  /**
   * Funzione che modifica la configurazione di una proprietà di un quadro definito in input.
   * La modifica averrà per riferimento sull'oggetto Template.
   * @param template Template con i dati generati per i quadri dell'istanza.
   * @param codQuadro string con il codice del quadro d'andare ad aggiornare.
   * @param debugQuadro any con l'oggetto FormIo del quadro da modificare.
   */
  private changeJsonConfiguraParametro(
    template: Template,
    codQuadro: string,
    debugQuadro: any,
    parametro: string
  ) {
    // Verifico l'input
    if (!template || !codQuadro) {
      // Ritorno il template come oggetto
      return;
    }

    // Estraggo dal template le informazioni dei quadri
    const quadri: Quadro[] = template.quadri ?? [];
    // Cerco all'interno dei quadri l'indice posizione usando il codice come verifica
    let iQdrUpd: number;
    iQdrUpd = quadri.findIndex((q: Quadro) => q.cod_quadro === codQuadro);

    // Verifico se ho trovato corrispondenza
    if (iQdrUpd !== -1) {
      // Trovato il riferimento, effettuo un stringify dell'oggetto quadro per il debug
      const strQdr: string = JSON.stringify(debugQuadro);
      // Sostituisco l'informazione per la configurazione del quadro
      template.quadri[iQdrUpd][parametro] = strQdr;
      // #
    }
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter che verifica se la modalità di debug per i quadri formio è attiva.
   * Il getter è stato pensato per avere un layer di sicurezza sui rilasci, verificando che, oltre la configurazione attiva, l'applicazione non sia in run come "produzione".
   * @returns boolean con il risultato del check.
   */
  private get debugFormIoFlgDevelop(): boolean {
    // Ritorno le condizioni di sviluppo e check sul run dell'applicazione
    return this._debugFormIoFlg && !this._app.isProductionMode;
  }
}
