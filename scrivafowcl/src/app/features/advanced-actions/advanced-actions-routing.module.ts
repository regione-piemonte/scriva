/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GestioneAbilitazioniComponent } from './pages/gestione-abilitazioni/gestione-abilitazioni.component';
import { RevocaDelegaComponent } from './pages/revoca-delega/revoca-delega.component';
import { ConclusioneProcedimentoComponent } from './pages/conclusione-procedimento/conclusione-procedimento.component';
import { PresaInCaricoComponent } from './pages/presa-in-carico/presa-in-carico.component';
import { PresaInCaricoUnicoComponent } from './pages/presa-in-carico-unico/presa-in-carico-unico.component';
import { MockRiepilogoComponent } from './pages/mock-riepilogo/mock-riepilogo.component';
import { RichPerfAllegatiComponent } from './pages/rich-perf-allegati/rich-perf-allegati.component';
import { SospendiComponent } from './pages/sospendi/sospendi.component';
import { AvviaProcedimentoComponent } from './pages/avvia-procedimento/avvia-procedimento.component';
import { GestisciNoteComponent } from './pages/gestisci-note/gestisci-note.component';
import { AbilitaVisualizzazioneComponent } from './pages/abilita-visualizzazione/abilita-visualizzazione.component';
import { RifiutaIstanzaComponent } from './pages/rifiuta-istanza/rifiuta-istanza.component';
import { IntegraAllegComponent } from './pages/integra-alleg/integra-alleg.component';
import { InserisciDocIstruttoriaComponent } from './pages/inserisci-doc-istruttoria/inserisci-doc-istruttoria.component';

const routes: Routes = [
  {
    path: 'mock/:id',
    component: MockRiepilogoComponent,
  },
  {
    path: 'mock',
    component: MockRiepilogoComponent,
  },
  {
    path: 'abilitazioni',
    component: GestioneAbilitazioniComponent,
  },
  {
    path: 'concludi-proc',
    component: ConclusioneProcedimentoComponent,
  },
  {
    path: 'prendi-in-carico',
    component: PresaInCaricoComponent,
  },
  {
    path: 'prendi-in-carico-unico',
    component: PresaInCaricoUnicoComponent,
  },
  {
    path: 'revoca-delega',
    component: RevocaDelegaComponent,
  },
  {
    path: 'abilitazioni/:id',
    component: GestioneAbilitazioniComponent,
  },
  {
    path: 'concludi-proc/:id',
    component: ConclusioneProcedimentoComponent,
  },
  {
    path: 'prendi-in-carico/:id',
    component: PresaInCaricoComponent,
  },
  {
    path: 'prendi-in-carico-unico/:id',
    component: PresaInCaricoUnicoComponent,
  },
  {
    path: 'revoca-delega/:id',
    component: RevocaDelegaComponent,
  },
  {
    path: 'rich-perf-allegati',
    component: RichPerfAllegatiComponent,
  },
  {
    path: 'rich-perf-allegati/:id',
    component: RichPerfAllegatiComponent,
  },
  {
    path: 'sospendi',
    component: SospendiComponent,
  },
  {
    path: 'sospendi/:id',
    component: SospendiComponent,
  },
  {
    path: 'avvia-pratica',
    component: AvviaProcedimentoComponent,
  },
  {
    path: 'avvia-pratica/:id',
    component: AvviaProcedimentoComponent,
  },
  {
    path: 'gestisci-note',
    component: GestisciNoteComponent,
  },
  {
    path: 'gestisci-note/:id',
    component: GestisciNoteComponent,
  },
  {
    path: 'abilita-visualizzazione',
    component: AbilitaVisualizzazioneComponent,
  },
  {
    path: 'abilita-visualizzazione/:id',
    component: AbilitaVisualizzazioneComponent,
  },
  {
    path: 'rifiuta-istanza',
    component: RifiutaIstanzaComponent,
  },
  {
    path: 'rifiuta-istanza/:id',
    component: RifiutaIstanzaComponent,
  },
  {
    path: 'integra-alleg-dichiarazioni',
    component: IntegraAllegComponent,
  },
  {
    path: 'integra-alleg-dichiarazioni/:id',
    component: IntegraAllegComponent,
  },
  {
    path: 'inserisci-doc-istruttoria',
    component: InserisciDocIstruttoriaComponent,
  },
  {
    path: 'inserisci-doc-istruttoria/:id',
    component: InserisciDocIstruttoriaComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdvancedActionsRoutingModule {}
