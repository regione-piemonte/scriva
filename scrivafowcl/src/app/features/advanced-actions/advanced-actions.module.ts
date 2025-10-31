/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { NgbActiveModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { SharedModule } from 'src/app/shared/shared.module';
import { AdvancedActionsRoutingModule } from './advanced-actions-routing.module';
import { AdvancedActionAllegatiComponent } from './components/advanced-action-allegati/advanced-action-allegati.component';
import { AdavancedActionFooterComponent } from './components/advanced-action-footer/advanced-action-footer.component';
import { AdvancedActionIntegrazioneComponent } from './components/advanced-action-integrazione/advanced-action-integrazione.component';
import { AdvancedActionIntegrazioniComponent } from './components/advanced-action-integrazioni/advanced-action-integrazioni.component';
import { AdvancedActionResponsabileComponent } from './components/advanced-action-responsabile/advanced-action-responsabile.component';
import { AdvancedActionResponsabili } from './components/advanced-action-responsabili/advanced-action-responsabili.component';
import { AdvancedActionRiepilogoIstanza } from './components/advanced-action-riepilogo-istanza/advanced-action-riepilogo-istanza.component';
import { AdvancedActionsRiepilogoModalComponent } from './components/advanced-actions-riepilogo-modal/advanced-actions-riepilogo-modal.component';
import { AbilitaVisualizzazioneComponent } from './pages/abilita-visualizzazione/abilita-visualizzazione.component';
import { AvviaProcedimentoComponent } from './pages/avvia-procedimento/avvia-procedimento.component';
import { ConclusioneProcedimentoComponent } from './pages/conclusione-procedimento/conclusione-procedimento.component';
import { GestioneAbilitazioniComponent } from './pages/gestione-abilitazioni/gestione-abilitazioni.component';
import { GestisciNoteComponent } from './pages/gestisci-note/gestisci-note.component';
import { IntegraAllegComponent } from './pages/integra-alleg/integra-alleg.component';
import { MockRiepilogoComponent } from './pages/mock-riepilogo/mock-riepilogo.component';
import { PresaInCaricoUnicoComponent } from './pages/presa-in-carico-unico/presa-in-carico-unico.component';
import { PresaInCaricoComponent } from './pages/presa-in-carico/presa-in-carico.component';
import { RevocaDelegaComponent } from './pages/revoca-delega/revoca-delega.component';
import { RichPerfAllegatiComponent } from './pages/rich-perf-allegati/rich-perf-allegati.component';
import { RifiutaIstanzaComponent } from './pages/rifiuta-istanza/rifiuta-istanza.component';
import { SospendiComponent } from './pages/sospendi/sospendi.component';
import { InserisciDocIstruttoriaComponent } from './pages/inserisci-doc-istruttoria/inserisci-doc-istruttoria.component';

@NgModule({
  entryComponents: [
    AdvancedActionIntegrazioneComponent,
    AdvancedActionResponsabileComponent,
    AdvancedActionsRiepilogoModalComponent,
  ],
  declarations: [
    AdvancedActionAllegatiComponent,
    AdavancedActionFooterComponent,
    AdvancedActionsRiepilogoModalComponent,
    AdvancedActionIntegrazioneComponent,
    AdvancedActionIntegrazioniComponent,
    AdvancedActionResponsabili,
    AdvancedActionResponsabileComponent,
    AdvancedActionRiepilogoIstanza,
    AbilitaVisualizzazioneComponent,
    AvviaProcedimentoComponent,
    ConclusioneProcedimentoComponent,
    GestioneAbilitazioniComponent,
    GestisciNoteComponent,
    PresaInCaricoComponent,
    PresaInCaricoUnicoComponent,
    RevocaDelegaComponent,
    RichPerfAllegatiComponent,
    RifiutaIstanzaComponent,
    SospendiComponent,
    MockRiepilogoComponent,
    IntegraAllegComponent,
    InserisciDocIstruttoriaComponent,
  ],
  imports: [
    CommonModule,
    NgbModule,
    NgxDatatableModule,
    SharedModule,
    AdvancedActionsRoutingModule,
  ],
  exports: [AdvancedActionsRiepilogoModalComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [NgbActiveModal],
})
export class AdvancesActionsModule {}
