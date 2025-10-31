/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { CommonModule, registerLocaleData } from '@angular/common';
import localeIt from '@angular/common/locales/it';
import { CUSTOM_ELEMENTS_SCHEMA, LOCALE_ID, NgModule } from '@angular/core';
import { NgbActiveModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { FormioAppConfig } from 'angular-formio';
import { FormioModule } from 'angular-formio/formio.module';
import { AppConfig } from '../../shared/services/formio/configs/formio-config';
import { SharedModule } from '../../shared/shared.module';
import { AdvancesActionsModule } from '../advanced-actions/advanced-actions.module';
import { AmbitoRoutingModule } from './ambito-routing.module';
import { HeaderHelpQuadroComponent } from './components/header-help-quadro/header-help-quadro.component';
import { OggettiSearchComponent } from './components/oggetti-search/oggetti-search.component';
import { AmbitoDashboardComponent } from './pages/ambito-dashboard/ambito-dashboard.component';
import { OrientamentoIstanzaComponent } from './pages/orientamento-istanza/orientamento-istanza.component';
import { AllegatiComponent } from './pages/presentazione-istanza/allegati/allegati.component';
import { CaptazioniComponent } from './pages/presentazione-istanza/captazioni/captazioni.component';
import { CaptazioniOperaModalComponent } from './pages/presentazione-istanza/captazioni/modals/captazioni-opera-modal/captazioni-opera-modal.component';
import { ConfermaPresentazioneComponent } from './pages/presentazione-istanza/conferma-presentazione/conferma-presentazione.component';
import { DettaglioProgettoComponent } from './pages/presentazione-istanza/dettaglio-progetto/dettaglio-progetto.component';
import { DettaglioViaStataleComponent } from './pages/presentazione-istanza/dettaglio-via-statale/dettaglio-via-statale.component';
import { DichiarazioniValComponent } from './pages/presentazione-istanza/dichiarazioni-val/dichiarazioni-val.component';
import { DichiarazioniVerComponent } from './pages/presentazione-istanza/dichiarazioni-ver/dichiarazioni-ver.component';
import { DichiarazioniVincaComponent } from './pages/presentazione-istanza/dichiarazioni-vinca/dichiarazioni-vinca.component';
import { InfrastruttureAccessorieComponent } from './pages/presentazione-istanza/infrastrutture-accessorie/infrastrutture-accessorie.component';
import { InfrastruttureAccessorieOperaModalComponent } from './pages/presentazione-istanza/infrastrutture-accessorie/modals/infrastrutture-accessorie-opera-modal/infrastrutture-accessorie-opera-modal.component';
import { OperaFormComponent } from './pages/presentazione-istanza/opera-intervento/opera-form/opera-form.component';
import { SitiNaturaFormComponent } from './pages/presentazione-istanza/opera-intervento/opera-form/siti-natura-form/siti-natura-form.component';
import { OperaInterventoComponent } from './pages/presentazione-istanza/opera-intervento/opera-intervento.component';
import { OpereTrasportoOperaModalComponent } from './pages/presentazione-istanza/opere-trasporto/modals/opere-trasporto-opera-modal/opere-trasporto-opera-modal.component';
import { OpereTrasportoComponent } from './pages/presentazione-istanza/opere-trasporto/opere-trasporto.component';
import { DatiTecniciOperaModalComponent } from './pages/presentazione-istanza/opere/modals/dati-tecnici-opera-modal/dati-tecnici-opera-modal.component';
import { OpereComponent } from './pages/presentazione-istanza/opere/opere.component';
import { PagamentiComponent } from './pages/presentazione-istanza/pagamenti/pagamenti.component';
import { PresentazioneIstanzaComponent } from './pages/presentazione-istanza/presentazione-istanza.component';
import { RestituzioniOperaModalComponent } from './pages/presentazione-istanza/restituzioni/modals/restituzioni-opera-modal/restituzioni-opera-modal.component';
import { RestituzioniComponent } from './pages/presentazione-istanza/restituzioni/restituzioni.component';
import { RiepilogoComponent } from './pages/presentazione-istanza/riepilogo/riepilogo.component';
import { ReferenteFormComponent } from './pages/presentazione-istanza/soggetti/referente-form/referente-form.component';
import { SoggettiComponent } from './pages/presentazione-istanza/soggetti/soggetti.component';
import { SoggettoFormComponent } from './pages/presentazione-istanza/soggetti/soggetto-form/soggetto-form.component';
import { TitoliAbilitativiComponent } from './pages/presentazione-istanza/titoli-abilitativi/titoli-abilitativi.component';
import { UsiUloDerivazioniModalComponent } from './pages/presentazione-istanza/usi-ulo-derivazioni/modals/usi-ulo-derivazioni-modal/usi-ulo-derivazioni-modal.component';
import { UsiUloDerivazioniComponent } from './pages/presentazione-istanza/usi-ulo-derivazioni/usi-ulo-derivazioni.component';
import {
  DichiarazioneVIAHTMLPipe,
  DichiarazioniOrientamentoVIAACSpecificaPipe,
  DichiarazioniOrientamentoVIARegionePiemontePipe,
} from './pipes/dichiarazioni/dichiarazioni-via/dichiarazioni-via.pipe';
import {
  LabelCriterioRicercaOggettiSearchPipe,
  MostraCriterioRicercaGenericoOggettiSearchPipe,
  MostraCriterioRicercaOggettiSearchPipe,
} from './pipes/oggetti-search/oggetti-search.pipe';
import { OpereTrasportoAccessoDatiTecniciOperaPipe } from './pipes/opere/opere-trasporto/opere-trasporto.pipe';
import {
  OpereCodiceRilievoOperaPipe,
  OpereCodiceScrivaOggettoIstanzaPipe,
  OpereCodiceScrivaOperaPipe,
  OpereComuniOggettoIstanzaPipe,
  OpereComuniOperaPipe,
  OpereIndirizzoUbicazionePipe,
  OpereLocalitaOggettoIstanzaPipe,
} from './pipes/opere/opere.pipe';
import {
  OpereCFAziendaOggettoIstanzaPipe,
  OpereDenominazioneAziendaOggettoIstanzaPipe,
} from './pipes/opere/usi-ulo-derivazioni/usi-ulo-derivazioni.pipe';
import { LoadStepperComponentPipe } from './pipes/presentazione-istanza/presentazione-istanza.pipe';
import { AmbitoStoreService, SoggettoStoreService } from './services';

registerLocaleData(localeIt);

@NgModule({
  declarations: [
    // COMPONENTS
    AmbitoDashboardComponent,
    OrientamentoIstanzaComponent,
    PresentazioneIstanzaComponent,
    SoggettiComponent,
    SoggettoFormComponent,
    ReferenteFormComponent,
    OperaInterventoComponent,
    OperaFormComponent,
    OpereComponent,
    CaptazioniComponent,
    RestituzioniComponent,
    InfrastruttureAccessorieComponent,
    OpereTrasportoComponent,
    UsiUloDerivazioniComponent,
    DatiTecniciOperaModalComponent,
    CaptazioniOperaModalComponent,
    RestituzioniOperaModalComponent,
    InfrastruttureAccessorieOperaModalComponent,
    OpereTrasportoOperaModalComponent,
    UsiUloDerivazioniModalComponent,
    DettaglioProgettoComponent,
    PagamentiComponent,
    AllegatiComponent,
    ConfermaPresentazioneComponent,
    TitoliAbilitativiComponent,
    DettaglioViaStataleComponent,
    RiepilogoComponent,
    OggettiSearchComponent,
    SitiNaturaFormComponent,
    DichiarazioniValComponent,
    DichiarazioniVerComponent,
    DichiarazioniVincaComponent,
    HeaderHelpQuadroComponent,
    // PIPES
    LoadStepperComponentPipe,
    OpereComuniOperaPipe,
    OpereComuniOggettoIstanzaPipe,
    OpereLocalitaOggettoIstanzaPipe,
    OpereCodiceScrivaOperaPipe,
    OpereCodiceRilievoOperaPipe,
    OpereCodiceScrivaOggettoIstanzaPipe,
    OpereIndirizzoUbicazionePipe,
    OpereTrasportoAccessoDatiTecniciOperaPipe,
    DichiarazioniOrientamentoVIARegionePiemontePipe,
    DichiarazioniOrientamentoVIAACSpecificaPipe,
    DichiarazioneVIAHTMLPipe,
    MostraCriterioRicercaOggettiSearchPipe,
    MostraCriterioRicercaGenericoOggettiSearchPipe,
    LabelCriterioRicercaOggettiSearchPipe,
    OpereCFAziendaOggettoIstanzaPipe,
    OpereDenominazioneAziendaOggettoIstanzaPipe,
  ],
  entryComponents: [
    SoggettiComponent,
    SoggettoFormComponent,
    ReferenteFormComponent,
    OperaInterventoComponent,
    OperaFormComponent,
    OpereComponent,
    CaptazioniComponent,
    RestituzioniComponent,
    InfrastruttureAccessorieComponent,
    OpereTrasportoComponent,
    UsiUloDerivazioniComponent,
    DatiTecniciOperaModalComponent,
    CaptazioniOperaModalComponent,
    RestituzioniOperaModalComponent,
    InfrastruttureAccessorieOperaModalComponent,
    OpereTrasportoOperaModalComponent,
    UsiUloDerivazioniModalComponent,
    DettaglioProgettoComponent,
    PagamentiComponent,
    AllegatiComponent,
    TitoliAbilitativiComponent,
    DettaglioViaStataleComponent,
    RiepilogoComponent,
    SitiNaturaFormComponent,
    DichiarazioniValComponent,
    DichiarazioniVerComponent,
    DichiarazioniVincaComponent,
  ],
  exports: [
    DatiTecniciOperaModalComponent,
    CaptazioniOperaModalComponent,
    RestituzioniOperaModalComponent,
  ],
  imports: [
    CommonModule,
    NgbModule,
    AdvancesActionsModule,
    SharedModule,
    AmbitoRoutingModule,
    FormioModule,
    NgxDatatableModule,
  ],
  providers: [
    AmbitoStoreService,
    NgbActiveModal,
    SoggettoStoreService,
    { provide: LOCALE_ID, useValue: 'it' },
    { provide: FormioAppConfig, useValue: AppConfig },
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AmbitoModule {}
