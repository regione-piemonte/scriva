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
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedModule } from 'src/app/shared/shared.module';
import { ProfiloDatoUtenteComponent } from './components/profilo-dato-utente/profilo-dato-utente.component';
import { ProfiloContattiModificaEmailModalComponent } from './modals/profilo-contatti-modifica-email/profilo-contatti-modifica-email.component';
import { ProfiloAnagraficaComponent } from './pages/profilo-anagrafica/profilo-anagrafica.component';
import { ProfiloContattiComponent } from './pages/profilo-contatti/profilo-contatti.component';
import { ProfiloNotificheComponent } from './pages/profilo-notifiche/profilo-notifiche.component';
import { ProfiloComponent } from './pages/profilo/profilo.component';
import { ProfiloRoutingModule } from './profilo-routing.module';
import { ProfiloContattiModalService } from './services/profilo-contatti/profilo-contatti-modal.service';
import { PreferenzeNotificheComponent } from './components/preferenze-notifiche/preferenze-notifiche.component';
import { PreferenzaNotificaComponent } from './components/preferenza-notifica/preferenza-notifica.component';

@NgModule({
  imports: [CommonModule, NgbModule, SharedModule, ProfiloRoutingModule],
  declarations: [
    ProfiloComponent,
    ProfiloAnagraficaComponent,
    ProfiloContattiComponent,
    ProfiloNotificheComponent,
    ProfiloDatoUtenteComponent,
    ProfiloContattiModificaEmailModalComponent,
    PreferenzeNotificheComponent,
    PreferenzaNotificaComponent,
  ],
  exports: [ProfiloContattiModificaEmailModalComponent],
  providers: [ProfiloContattiModalService],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  entryComponents: [ProfiloContattiModificaEmailModalComponent],
})
export class ProfiloModule {}
