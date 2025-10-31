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
import { CommonModule } from '@angular/common';
import { ThemeModule } from '@theme/theme.module';
import { TranslateModule } from '@eng-ds/translate';
import { SharedModule } from '@shared/shared.module';
import { DettaglioPraticaRoutingModule } from './dettaglio-pratica-routing.module';
import { DettaglioPraticaComponent } from '@pages/pratiche/pages/dettaglio-pratica/pages/dettaglio-pratica/dettaglio-pratica.component';
import { MappaPraticaComponent } from './components/mappa-pratica/mappa-pratica.component';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { RiepilogoPraticaComponent } from './components/riepilogo-pratica/riepilogo-pratica.component';
import { AllegatiPraticaComponent } from './components/allegati-pratica/allegati-pratica.component';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import { DettaglioPraticaResolver } from './resolvers/dettaglio-pratica.resolver';
import { IstruttoriaPraticaComponent } from './components/istruttoria-pratica/istruttoria-pratica.component';
import { MapPraticaResolver } from './resolvers/map-pratica.resolver';
import { HelpModalComponent } from './components/help-modal/help-modal.component';
import { CaptchaModalComponent } from './components/captcha-modal/captcha-modal.component';

@NgModule({
  declarations: [
    DettaglioPraticaComponent,
    MappaPraticaComponent,
    RiepilogoPraticaComponent,
    AllegatiPraticaComponent,
    IstruttoriaPraticaComponent,
    HelpModalComponent,
    CaptchaModalComponent
  ],
  imports: [
    CommonModule,
    ThemeModule,
    TranslateModule,
    SharedModule,
    DettaglioPraticaRoutingModule,
    LeafletModule,
    NgxDatatableModule
  ],
  providers: [DettaglioPraticaResolver, MapPraticaResolver]
})
export class DettaglioPraticaModule {}
