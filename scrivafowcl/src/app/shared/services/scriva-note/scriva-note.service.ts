/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Injectable } from '@angular/core';
import { IstanzaService } from '../istanza/istanza.service';
import { NotaIstanza } from '../../models';
import { Observable, forkJoin, of } from 'rxjs';
import { AuthStoreService } from '../auth/auth-store.service';

/**
 * Servizio di utility con funzionalità di gestione per le noteistanza
 */
@Injectable({ providedIn: 'root' })
export class ScrivaNoteService {
  /**
   * Costruttore.
   */
  constructor(
    private authStoreService: AuthStoreService,
    private istanzaService: IstanzaService,
  ) {}

  /**
   * Salva e update della nota che funziona sia salvando direttamente che tenendo 
   * in pancia le note per essere salvate in un momento successivo
   * @param nuovaNota NotaIstanza
   * @param notSaveBE boolean che indica se tenere in ram la nota o salvarla subito
   * @returns Observable<NotaIstanza>
   */
  saveNotaIstanza(nuovaNota: NotaIstanza, notSaveBE:boolean): Observable<NotaIstanza> {
    if(notSaveBE) {
      // le note sono create in automatico con BO anche quando
      // si lavora in memoria settiamo il flag ind_visibile
      nuovaNota.ind_visibile =  nuovaNota.ind_visibile ?  nuovaNota.ind_visibile : "BO";
      nuovaNota.funzionario = nuovaNota.funzionario? nuovaNota.funzionario:{
        cognome_funzionario : this.authStoreService.loggedUserCognome,
        nome_funzionario : this.authStoreService.loggedUserNome,
      };
      nuovaNota.data_nota = nuovaNota.data_nota ? nuovaNota.data_nota : new Date().toISOString();
      // genero un gestUID casuale per la eventuale cancellazione/edit
      nuovaNota.gestUID = nuovaNota.gestUID ? nuovaNota.gestUID : Math.floor(Math.random() * Date.now()).toString(36);
      nuovaNota.id_nota_istanza = nuovaNota.id_nota_istanza ? nuovaNota.id_nota_istanza : Date.now();
      return of(nuovaNota);
    }
    return this.istanzaService.saveNotaIstanza(nuovaNota);
  }

  /**
   * 
   * @param gestUID string che identifca la nota per la cancellazione
   * @param notSaveBE oolean che indica se tenere in ram la nota o salvarla subito
   * @returns Observable con la chiamta al BE o null
   */
  deleteNotaIstanza(gestUID: string, notSaveBE:boolean): Observable<any>{
    if(notSaveBE) {
      return of(null);
    }
    return this.istanzaService.deleteNotaIstanza(gestUID);
  }

  /**
   * Recupero un fokjoin con le chimate insieme  con un forkjoin 
   * le note vengono ripuilite di dati fake inseriti per il funzionamento in meemoria
   * @param noteIstanza NotaIstanza[] array di note
   * @returns Observable con il forkJoin delle chiamate
   */
  getSaveNoteIstanza(noteIstanza: NotaIstanza[] = []) {
     let calls = [];
     calls = noteIstanza.map(function (notaIstanza) { 
      const notaIstanzaSave:NotaIstanza = {
          id_istanza: notaIstanza.id_istanza,
          des_nota: notaIstanza.des_nota,
          ind_visibile: notaIstanza.ind_visibile,
          data_nota: notaIstanza.data_nota
      }; 
      return this.istanzaService.saveNotaIstanza(notaIstanzaSave) 
     }.bind(this));
     return forkJoin(calls);
  }
  
}
