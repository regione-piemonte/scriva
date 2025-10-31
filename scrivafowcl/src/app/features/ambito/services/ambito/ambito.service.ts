/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { forkJoin, Observable, of } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { RuoloCompilante } from 'src/app/shared/models';
import { AppConfigService } from 'src/app/shared/services';
import {
  CatastoFoglio,
  CatastoParticella,
  CatastoSezione,
  ConfigRuoloAdempimento,
  OggettoIstanza,
  OggettoIstanzaAreaProtetta,
  OggettoIstanzaGeometrie,
  OggettoIstanzaGeometrieFE,
  OggettoIstanzaNatura2000,
  Opera,
  RecapitoAlternativo,
  Referente,
  RuoloSoggetto,
  Soggetto,
  SoggettoIstanza,
  TipologiaOggetto,
  TipoNaturaGiuridica,
  TipoSoggetto,
  UbicazioneOggettoIstanza,
} from '../../models';
import { OggettiSearchRequest } from '../../models/oggetti-search/oggetti-search-request';

@Injectable({ providedIn: 'root' })
export class AmbitoService {
  private beUrl = '';

  constructor(private http: HttpClient, private config: AppConfigService) {
    this.beUrl = this.config.getBEUrl();
  }

  /*
   * API per select
   */
  getTipiSoggetto(): Observable<TipoSoggetto[]> {
    return this.http.get<TipoSoggetto[]>(`${this.beUrl}/tipi-soggetto`);
  }

  getTipiNaturaGiuridica(): Observable<TipoNaturaGiuridica[]> {
    return this.http.get<TipoNaturaGiuridica[]>(
      `${this.beUrl}/tipi-natura-giuridica`
    );
  }

  getRuoliCompilante(): Observable<RuoloCompilante[]> {
    return this.http.get<RuoloCompilante[]>(`${this.beUrl}/ruoli-compilante`);
  }

  getRuoliSoggetto(): Observable<RuoloSoggetto[]> {
    return this.http.get<RuoloSoggetto[]>(`${this.beUrl}/ruoli-soggetto`);
  }

  getTipologieOggettoByAdempimento(
    codAdempimento: string
  ): Observable<TipologiaOggetto[]> {
    return this.http.get<TipologiaOggetto[]>(
      `${this.beUrl}/tipologie-oggetto?cod_adempimento=${codAdempimento}`
    );
  }

  /*
   * API configurazioni
   */
  getConfigurazioneRuoliCompilanteByAdempimento(
    idAdempimento
  ): Observable<ConfigRuoloAdempimento[]> {
    return this.http.get<ConfigRuoloAdempimento[]>(
      `${this.beUrl}/adempimenti-ruoli-compilante/id-adempimento/${idAdempimento}`
    );
  }

  getConfigurazioneRuoloCompilanteByAdempimento(
    idRuoloCompilante,
    idAdempimento
  ): Observable<ConfigRuoloAdempimento> {
    return this.http
      .get<ConfigRuoloAdempimento>(
        `${this.beUrl}/adempimenti-ruoli-compilante/id-ruolo-compilante/${idRuoloCompilante}/id-adempimento/${idAdempimento}`
      )
      .pipe(
        map((res) => {
          // console.log(res);
          return res[0];
        })
      );
  }

  getRuoliSoggettoFromRuoloCompilante(
    idRuoloCompilante,
    idAdempimento
  ): Observable<RuoloSoggetto[]> {
    return this.http.get<RuoloSoggetto[]>(
      `${this.beUrl}/ruoli-soggetto/id-adempimento/${idAdempimento}/id-ruolo-compilante/${idRuoloCompilante}`
    );
  }

  /*
   * API soggetti (e richiedente)
   */
  getSoggettoByCFTipoSoggettoTipoAdempimento(
    cf: string,
    codTipoSoggetto: string,
    codAdempimento: string
  ): Observable<Soggetto> {
    return this.http
      .get<Soggetto[]>(
        `${this.beUrl}/soggetti/cf/${cf}/tipo-soggetto/${codTipoSoggetto}/tipo-adempimento/${codAdempimento}`
      )
      .pipe(
        map((res) => {
          // console.log(res);
          return res[0];
        })
      );
  }

  getSoggettoAdempimento(
    cf: string,
    codTipoSoggetto: string,
    codAdempimento: string
  ): Observable<Soggetto> {
    // Richiamo la funzione comune
    return this.getSoggettoByCFTipoSoggettoTipoAdempimento(
      cf,
      codTipoSoggetto,
      codAdempimento
    );
  }

  getRichiedenteAdepimento(
    cfImpresa: string,
    cfSoggetto: string,
    codAdempimento: string
  ): Observable<Soggetto> {
    return this.http
      .get<Soggetto[]>(
        `${this.beUrl}/soggetti/cf-impresa/${cfImpresa}/cf-soggetto/${cfSoggetto}/tipo-soggetto/pf/tipo-adempimento/${codAdempimento}`
      )
      .pipe(
        map((res) => {
          // console.log(res);
          return res[0];
        })
      );
  }

  salvaSoggetti(soggetto: Soggetto): Observable<Soggetto> {
    if (soggetto.gestUID) {
      return this.http.put<Soggetto>(`${this.beUrl}/soggetti`, soggetto);
    } else {
      return this.http.post<Soggetto>(`${this.beUrl}/soggetti`, soggetto);
    }
  }

  /*
   * API soggetto-istanza
   */
  getSoggettiIstanzaByIstanza(
    idIstanza: number
  ): Observable<SoggettoIstanza[]> {
    return this.http.get<SoggettoIstanza[]>(
      `${this.beUrl}/soggetti-istanza?id_istanza=${idIstanza}`
    );
  }

  getSoggettoIstanzaById(
    idSoggettoIstanza: number
  ): Observable<SoggettoIstanza> {
    return this.http
      .get<SoggettoIstanza[]>(
        `${this.beUrl}/soggetti-istanza/id/${idSoggettoIstanza}`
      )
      .pipe(map((res) => res[0]));
  }

  /**
   * Funzione che estrae tutti i soggetti istanza data una matrice di id soggetto.
   * @param matrixId number[][] con la matrice di id per lo scarico dei dati.
   * @returns Observable<SoggettoIstanza[]>[] con la matrice di SoggettoIstanza scaricati.
   */
  getSoggettiIstanzaByIdMatrix(
    matrixId: number[][]
  ): Observable<SoggettoIstanza[][]> {
    // Verifico che esista una matrice corretta di elementi
    if (!matrixId || matrixId.length === 0) {
      // Manca la configurazione, ritorno una richiesta vuota
      return of([]);
      // #
    }

    // Definisco una variabile con le richieste per la matrice dati
    let reqMatrix: Observable<SoggettoIstanza[]>[] = matrixId.map(
      (listaId: number[]) => {
        // Richiamo la funzione per la generazione delle richieste per una lista di dati
        const reqList: Observable<SoggettoIstanza[]> =
          this.getSoggettiIstanzaByIdList(listaId);
        // Richiamo e itorno la lista di request per ogni id soggetto istanza
        return reqList;
        // #
      }
    );

    // Effettuo la richiesta unendo le liste per lo scarico dati
    return forkJoin(reqMatrix);
  }

  /**
   * Funzione che estrae tutti i soggetti istanza data una lista di id soggetto.
   * @param listaId number[] con la lista di id per lo scarico dei dati.
   * @returns Observable<SoggettoIstanza[]> con la lista di SoggettoIstanza scaricati.
   */
  getSoggettiIstanzaByIdList(listaId: number[]): Observable<SoggettoIstanza[]> {
    // Verifico che esista una matrice corretta di elementi
    if (!listaId || listaId.length === 0) {
      // Manca la configurazione, ritorno una richiesta vuota
      return of([]);
      // #
    }

    // Creo una request interna per scaricare tutti i soggetti per ogni id soggetto
    let reqList: Observable<SoggettoIstanza>[] = listaId.map(
      (idSoggetto: number) => {
        // Richiamo e ritorno l'API di scarico dati
        return this.getSoggettoIstanzaById(idSoggetto);
        // #
      }
    );

    // Ritorno la lista di request per ogni id soggetto istanza
    return forkJoin(reqList);
  }

  salvaSoggettiIstanza(
    soggettoIstanza: SoggettoIstanza
  ): Observable<SoggettoIstanza> {
    if (soggettoIstanza.gestUID) {
      return this.http.put<SoggettoIstanza>(
        `${this.beUrl}/soggetti-istanza`,
        soggettoIstanza
      );
    } else {
      return this.http.post<SoggettoIstanza>(
        `${this.beUrl}/soggetti-istanza`,
        soggettoIstanza
      );
    }
  }

  eliminaSoggettiIstanza(
    uidSoggettoIstanza: string
  ): Observable<SoggettoIstanza> {
    return this.http.delete<SoggettoIstanza>(
      `${this.beUrl}/soggetti-istanza/${uidSoggettoIstanza}`
    );
  }

  /*
   * API referenti
   */
  getReferentiIstanza(idIstanza: number): Observable<Referente[]> {
    return this.http.get<Referente[]>(
      `${this.beUrl}/istanze/referenti/id-istanza/${idIstanza}`
    );
  }

  salvaReferente(referente: Referente): Observable<Referente> {
    if (referente.gestUID) {
      return this.http.put<Referente>(
        `${this.beUrl}/istanze/referenti`,
        referente
      );
    } else {
      return this.http.post<Referente>(
        `${this.beUrl}/istanze/referenti`,
        referente
      );
    }
  }

  eliminaReferente(uidReferenteIstanza: string): Observable<Referente> {
    return this.http.delete<Referente>(
      `${this.beUrl}/istanze/referenti/${uidReferenteIstanza}`
    );
  }

  /*
   * API recapito alternativo
   */
  getRecapitoAlternativo(
    idSoggettoIstanza: number
  ): Observable<RecapitoAlternativo> {
    return this.http
      .get<RecapitoAlternativo[]>(
        `${this.beUrl}/soggetti-istanza/recapiti-alternativi/id/${idSoggettoIstanza}`
      )
      .pipe(map((res) => res[0]));
  }

  salvaRecapitoAlternativo(
    recAlt: RecapitoAlternativo
  ): Observable<RecapitoAlternativo> {
    if (recAlt.gestUID) {
      return this.http.put<RecapitoAlternativo>(
        `${this.beUrl}/soggetti-istanza/recapiti-alternativi`,
        recAlt
      );
    } else {
      return this.http.post<RecapitoAlternativo>(
        `${this.beUrl}/soggetti-istanza/recapiti-alternativi`,
        recAlt
      );
    }
  }

  eliminaRecapitoAlternativo(idSoggettoIstanza: number) {
    return this.http.delete(
      `${this.beUrl}/soggetti-istanza/recapiti-alternativi/id/${idSoggettoIstanza}`
    );
  }

  /*
   * API opere
   */
  getOpereByFilters(payload: OggettiSearchRequest): Observable<Opera[]> {
    return this.http.post<Opera[]>(`${this.beUrl}/oggetti/search`, payload);
  }

  getOpereByIstanza(idIstanza: number): Observable<Opera[]> {
    return this.http.get<Opera[]>(
      `${this.beUrl}/oggetti?id_istanza=${idIstanza}`
    );
  }

  salvaOpera(opera: Opera, codAdempimento: string) {
    if (opera.gestUID) {
      return this.http.put<Opera>(`${this.beUrl}/oggetti`, opera);
    } else {
      return this.http.post<Opera>(
        `${this.beUrl}/oggetti?cod_adempimento=${codAdempimento}`,
        opera
      );
    }
  }

  eliminaOpera(gestUID: string) {
    return this.http.delete<Opera>(`${this.beUrl}/oggetti/${gestUID}`);
  }

  /*
   * API oggetto-istanza
   */
  getOggettiIstanzaByIstanza(idIstanza: number): Observable<OggettoIstanza[]> {
    return this.http.get<OggettoIstanza[]>(
      `${this.beUrl}/oggetti-istanza?id_istanza=${idIstanza}`
    );
  }

  /**
   * Funzione che recupera la lista degli oggetti-istanza dato un id istanza di riferimento
   * La funzione inoltre andrà a recuperare le informazioni relative ai possibili soggetti associati ad ogni oggetto istanza.
   * Il recupero dei soggetti si basa sulle informazioni delle geometrie, dove potrebbe essere presente l'id soggetto salvato.
   * Se manca questa informazione, i dati del soggetto saranno undefined.
   * @param idIstanza number con l'id istanza per lo scarico dati.
   * @returns Observable<OggettoIstanzaGeometrieFE[]> con le informazioni scaricate.
   */
  getOggettiIstanzaESoggettiByIstanza(
    idIstanza: number
  ): Observable<OggettoIstanzaGeometrieFE[]> {
    // Scarico la lista degli oggetti istanza dato l'id_istanza
    return this.getOggettiIstanzaByIstanza(idIstanza).pipe(
      map((oggettiIstanza: OggettoIstanza[]) => {
        // Vado a filtrare le informazioni e ritorno la lista degli oggetti-istanza
        return oggettiIstanza?.map((oggIst: OggettoIstanza) => {
          // Ritorno solo l'id oggetto-istanza
          return oggIst?.id_oggetto_istanza;
          // #
        });
        // #
      }),
      switchMap((idOggettiIstanza: number[]) => {
        // Definisco la lista di chiamate per lo scarico delle informazioni per gli oggetti istanza
        let req: Observable<OggettoIstanzaGeometrie>[];
        req = idOggettiIstanza?.map((idOggIst: number) => {
          // Effettuo la chiamata per lo scarico dei dati
          return this.getOggettiIstanzaByIdOggettoIstanza(idOggIst);
          // #
        });

        // Effettuo una forkJoin e scarico le informazioni specifiche per ogni oggetto istanza
        return forkJoin(req);
        // #
      }),
      map((oggettiIstanzaGeometrie: OggettoIstanzaGeometrie[]) => {
        // Ottenute le informazioni vado a creare delle classi specifiche per la gestione FE
        let oggettiIstanzaGeometrieFE: OggettoIstanzaGeometrieFE[];
        oggettiIstanzaGeometrieFE = oggettiIstanzaGeometrie.map(
          (oggIstGeom: OggettoIstanzaGeometrie) => {
            // Creo la classe specifica
            return new OggettoIstanzaGeometrieFE(oggIstGeom);
            // #
          }
        );

        // Ritorno la lista di oggetti generati
        return oggettiIstanzaGeometrieFE;
        // #
      }),
      switchMap((oggettiIstanzaGeometrieFE: OggettoIstanzaGeometrieFE[]) => {
        // Dalla lista degli oggetti istanza geometrie, estraggo gli id per i soggetti delle geometrie
        let idSoggIstGeom: number[][];
        idSoggIstGeom = oggettiIstanzaGeometrieFE.map(
          (oggIstGeomFE: OggettoIstanzaGeometrieFE) => {
            // Recupero la lista dei soggetti istanza per le geometrie
            return oggIstGeomFE?.idSoggettiGeometrie;
            // #
          }
        );

        // Scarico la lista di tutti i soggetti per ogni oggetto-istanza, e rimappo le informazioni dentro gli oggetti-istanza stessi
        return this.getSoggettiIstanzaByIdMatrix(idSoggIstGeom).pipe(
          map((soggettiIstanzaMatrix: SoggettoIstanza[][]) => {
            // Il legame tra la risposta e l'oggetto istanza è che ogni oggetto istanza ha "n" soggetti, gestisco con stesso indice posizionale
            soggettiIstanzaMatrix?.forEach(
              (soggettiIstanza: SoggettoIstanza[], i: number) => {
                // Assegno per ogni oggetto istanza i propri soggetti
                oggettiIstanzaGeometrieFE[i].soggettiGeometrie =
                  soggettiIstanza;
                // #
              }
            );

            // Ritorno la lista di oggetti-istanza aggiornati con i soggetti
            return oggettiIstanzaGeometrieFE;
            // #
          })
        );
        // #
      })
    );
  }

  getOggettiIstanzaByIdOggettoIstanza(
    IdOggettoIstanza: number
  ): Observable<OggettoIstanzaGeometrie> {
    return this.http
      .get<OggettoIstanzaGeometrie[]>(
        `${this.beUrl}/oggetti-istanza/id/${IdOggettoIstanza}`
      )
      .pipe(
        map((listaOggIstGeom: OggettoIstanzaGeometrie[]) => {
          // Estraggo solo il primo elemento
          return listaOggIstGeom[0];
          // #
        })
      );
  }

  /**
   * Funzione che effettua il salvataggio delle informazioni per un oggetto-istanza.
   * @param oggettoIstanza OggettoIstanza con le informazioni da salvare.
   */
  salvaOggettoIstanza(oggettoIstanza: OggettoIstanza) {
    if (oggettoIstanza.gestUID) {
      return this.http.put<OggettoIstanza>(
        `${this.beUrl}/oggetti-istanza`,
        oggettoIstanza
      );
    } else {
      return this.http.post<OggettoIstanza>(
        `${this.beUrl}/oggetti-istanza`,
        oggettoIstanza
      );
    }
  }

  eliminaOggettoIstanza(gestUID: string, checkFigli: boolean) {
    return this.http.delete<OggettoIstanza>(
      `${this.beUrl}/oggetti-istanza/${gestUID}?check_figli=${checkFigli}`
    );
  }

  convertiCoordinate(coordX, coordY) {
    return this.http
      .get(`${this.beUrl}/geopoint-converter?lat=${coordX}&lon=${coordY}`)
      .pipe(map((res) => res[0]));
  }

  checkGeometrie(
    codAdempimento: string,
    idIstanza: number
  ): Observable<OggettoIstanza[]> {
    return this.http.get<OggettoIstanza[]>(
      `${this.beUrl}/oggetti-istanza/adempimento/${codAdempimento}/id-istanza/${idIstanza}`
    );
  }

  /*
   * API Info dettaglio oggetto-istanza
   */
  private parchiParams(
    ubicazioni: UbicazioneOggettoIstanza[],
    checkComuni: boolean = true,
    idOggettoIstanza?: number
  ): HttpParams {
    let params = new HttpParams();

    if (idOggettoIstanza) {
      params = params.append('idOggettoIstanza', idOggettoIstanza.toString());
    }

    if (!checkComuni) {
      params = params.append('checkComuni', checkComuni.toString());
    }

    if (ubicazioni?.length > 0) {
      let codIstatComuni: string = '';
      ubicazioni.forEach((ubi) => {
        codIstatComuni += ubi.comune.cod_istat_comune + ',';
      });
      codIstatComuni = codIstatComuni.substring(0, codIstatComuni.length - 1);
      params = params.append('codIstatComuni', codIstatComuni);
    }

    return params;
  }

  getSitiReteNatura2000List(): Observable<OggettoIstanzaNatura2000[]> {
    return this.http.get<OggettoIstanzaNatura2000[]>(`${this.beUrl}/rn2000`);
    // return of([]);
  }

  getSitiReteNatura2000ByUbicazioni(
    ubicazioni: UbicazioneOggettoIstanza[],
    idOggettoIstanza?: number,
    checkComuni: boolean = true
  ): Observable<OggettoIstanzaNatura2000[]> {
    let url = `${this.beUrl}/rn2000`;
    const params = this.parchiParams(ubicazioni, checkComuni, idOggettoIstanza);
    return this.http.get<OggettoIstanzaNatura2000[]>(url, { params });
  }

  getAreeProtetteList(): Observable<OggettoIstanzaAreaProtetta[]> {
    return this.http.get<OggettoIstanzaAreaProtetta[]>(
      `${this.beUrl}/aree-protette`
    );
  }

  getAreeProtetteByUbicazioni(
    ubicazioni: UbicazioneOggettoIstanza[],
    idOggettoIstanza?: number,
    checkComuni: boolean = true
  ): Observable<OggettoIstanzaAreaProtetta[]> {
    let url = `${this.beUrl}/aree-protette`;
    const params = this.parchiParams(ubicazioni, checkComuni, idOggettoIstanza);
    return this.http.get<OggettoIstanzaAreaProtetta[]>(url, { params });
  }

  getSezioniCatastaliByComune(
    codBelfiore: string
  ): Observable<CatastoSezione[]> {
    return this.http
      .get<CatastoSezione[]>(
        `${this.beUrl}/catasto/comuni/${codBelfiore}/sezioni`
      )
      .pipe(
        map((sezioni: CatastoSezione[]) => {
          // Lancio la funzione di ordinamento
          return this.sortSezioni(sezioni);
        })
      );
  }

  getFogliCatastaliBySezione(
    sezione: CatastoSezione
  ): Observable<CatastoFoglio[]> {
    return this.http
      .get<CatastoFoglio[]>(
        `${this.beUrl}/catasto/comuni/${sezione.comune.cod_belfiore_comune}/sezioni/${sezione.sezione}/fogli`
      )
      .pipe(
        map((fogli: CatastoFoglio[]) => {
          // Lancio la funzione di ordinamento
          return this.sortFogli(fogli);
        })
      );
  }

  getParticelleCatastaliByFoglio(
    foglio: CatastoFoglio
  ): Observable<CatastoParticella[]> {
    return this.http
      .get<CatastoParticella[]>(
        `${this.beUrl}/catasto/comuni/${foglio.comune.cod_belfiore_comune}/sezioni/${foglio.sezione}/fogli/${foglio.foglio}/particelle`
      )
      .pipe(
        map((particelle: CatastoParticella[]) => {
          // Lancio la funzione di ordinamento
          return this.sortParticelle(particelle);
        })
      );
  }

  getParticelleCatastaliInterseca(
    idOggettoIstanza: number
  ): Observable<CatastoParticella[]> {
    return this.http.get<CatastoParticella[]>(
      `${this.beUrl}/catasto/particelle/interseca?id_oggetto_istanza=${idOggettoIstanza}`
    );
  }

  /**
   * #######################
   * FUNZIONI DI ORDINAMENTO
   * #######################
   */

  /**
   * Funzione di supporto che effettua l'ordinamento degli oggetti CatastoSezione.
   * La lista è gestita da un servizio di terzi, si forza l'ordiamento crescente per l'informazione che verrà usata in pagina: "sezione".
   * @param sezioni CatastoSezione[] da ordinare.
   * @returns CatastoSezione[] con la lista ordinata per la proprietà specifica.
   */
  private sortSezioni(sezioni: CatastoSezione[]): CatastoSezione[] {
    // Il servizio fa riferimento ad un esterno, per cui l'ordinamento è da fare FE, verifico i dati
    if (sezioni?.length > 0) {
      // La struttura dati non è sicura al 100%, gestisco con un try-catch
      try {
        // Ho delle informazioni, tento di ordinare con proprietà di riferimento come numero e non stringa
        let listSort: CatastoSezione[];
        listSort = sezioni.sort(
          (elementA: CatastoSezione, elementB: CatastoSezione) => {
            // Estraggo dagli oggetti le proprietà
            const paramA: string = elementA?.sezione;
            const paramB: string = elementB?.sezione;
            // Lancio la funzione per la gestione dell'ordinamento
            return this.sortStringsAsNumbers(paramA, paramB);
          }
        );

        // Ritorno il nuovo ordinamento
        return listSort;
        // #
      } catch (e) {}
    }

    // Ritorno la lista come da servizio
    return sezioni;
  }

  /**
   * Funzione di supporto che effettua l'ordinamento degli oggetti CatastoFoglio.
   * La lista è gestita da un servizio di terzi, si forza l'ordiamento crescente per l'informazione che verrà usata in pagina: "foglio".
   * @param fogli CatastoFoglio[] da ordinare.
   * @returns CatastoFoglio[] con la lista ordinata per la proprietà specifica.
   */
  private sortFogli(fogli: CatastoFoglio[]): CatastoFoglio[] {
    // Il servizio fa riferimento ad un esterno, per cui l'ordinamento è da fare FE, verifico i dati
    if (fogli?.length > 0) {
      // La struttura dati non è sicura al 100%, gestisco con un try-catch
      try {
        // Ho delle informazioni, tento di ordinare con proprietà di riferimento come numero e non stringa
        let listSort: CatastoFoglio[];
        listSort = fogli.sort(
          (elementA: CatastoFoglio, elementB: CatastoFoglio) => {
            // Estraggo dagli oggetti le proprietà
            const paramA: string = elementA?.foglio;
            const paramB: string = elementB?.foglio;
            // Lancio la funzione per la gestione dell'ordinamento
            return this.sortStringsAsNumbers(paramA, paramB);
          }
        );

        // Ritorno il nuovo ordinamento
        return listSort;
        // #
      } catch (e) {}
    }

    // Ritorno la lista come da servizio
    return fogli;
  }

  /**
   * Funzione di supporto che effettua l'ordinamento degli oggetti CatastoParticella.
   * La lista è gestita da un servizio di terzi, si forza l'ordiamento crescente per l'informazione che verrà usata in pagina: "particella".
   * @param particelle CatastoParticella[] da ordinare.
   * @returns CatastoParticella[] con la lista ordinata per la proprietà specifica.
   */
  private sortParticelle(particelle: CatastoParticella[]): CatastoParticella[] {
    // Il servizio fa riferimento ad un esterno, per cui l'ordinamento è da fare FE, verifico i dati
    if (particelle?.length > 0) {
      // La struttura dati non è sicura al 100%, gestisco con un try-catch
      try {
        // Ho delle informazioni, tento di ordinare con proprietà di riferimento come numero e non stringa
        let listSort: CatastoParticella[];
        listSort = particelle.sort(
          (elementA: CatastoParticella, elementB: CatastoParticella) => {
            // Estraggo dagli oggetti le proprietà
            const paramA: string = elementA?.particella;
            const paramB: string = elementB?.particella;
            // Lancio la funzione per la gestione dell'ordinamento
            return this.sortStringsAsNumbers(paramA, paramB);
          }
        );

        // Ritorno il nuovo ordinamento
        return listSort;
        // #
      } catch (e) {}
    }

    // Ritorno la lista come da servizio
    return particelle;
  }

  /**
   * Funzione di check per la gestione dell'ordinamento rispetto a due informazioni
   * @param a string per la definizione dell'ordinamento.
   * @param b string per la definizione dell'ordinamento.
   * @returns number con l'indicazione compatibile alla funzione Array.sort() di javascript.
   */
  private sortStringsAsNumbers(a: string, b: string): number {
    // Verifico l'input e forzo i valori per evitare errori
    a = a ?? '';
    b = b ?? '';

    // Tento di convertire le particelle in numeri
    const pANumber: number = parseInt(a);
    const pBNumber: number = parseInt(b);

    // Verifico e gestisco le condizioni
    const bothStrings: boolean = isNaN(pANumber) && isNaN(pBNumber);
    const bothNumbers: boolean = !isNaN(pANumber) && !isNaN(pBNumber);
    // Gestisco le casistiche per l'ordinamento
    if (bothStrings) {
      // Entrambi sono stringhe
      return a < b ? -1 : 1;
      // #
    } else if (bothNumbers) {
      // Entrambi sono numeri
      return pANumber < pBNumber ? -1 : 1;
      // #
    } else if (isNaN(pANumber)) {
      // La prima particella non è un numero, prende priorità rispetto al numero
      return -1;
      // #
    } else if (isNaN(pBNumber)) {
      // La seconda particella non è un numero, prende priorità rispetto al numero
      return 1;
      // #
    }

    // Non sono entrato nelle casistiche precedenti, ritorno un default
    return 0;
  }
}
