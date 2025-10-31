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
import { ActivatedRoute, Router } from '@angular/router';

import { AutoUnsubscribe } from 'src/app/core/components';
import { AllegatiIntegrazioniService } from 'src/app/features/ambito/services/allegati-integrazioni.service';
import { IstanzaSearch } from 'src/app/shared/models';
import { AuthStoreService } from 'src/app/shared/services';
import {
  AttoreGestioneIstanzaEnum,
  StatoIstanzaEnum,
} from 'src/app/shared/utils';

@Component({
  selector: 'app-riepilogo-istanze-list',
  templateUrl: './riepilogo-istanze-list.component.html',
  styleUrls: ['./riepilogo-istanze-list.component.scss'],
})
export class RiepilogoIstanzeListComponent
  extends AutoUnsubscribe
  implements OnInit
{
  @Input() title: string;
  @Input() icon: string;
  @Input() count: string;
  @Input() flgDaPagare = false;

  @Input() istanze: IstanzaSearch[];
  @Input() status: string;

  @Output() vediTutte = new EventEmitter();

  @Input() colNum = 1;

  componente: string;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authStoreService: AuthStoreService
  ) {
    super();
    this.componente = this.authStoreService.getComponente();
  }

  ngOnInit(): void {
    // force colNum in [1, 2, 3, 4]
    if (this.colNum < 1) {
      this.colNum = 1;
    }
    if (this.colNum > 4) {
      this.colNum = 4;
    }
  }

  onVediTutte() {
    this.vediTutte.emit({
      status: this.status,
      flgDaPagare: this.flgDaPagare,
    });
  }

  /**
   * Funzione invocata al click sull'istanza. Verrà aperta l'istanza secondo la configurazione dell'attore gestione istanza.
   * @param istanza IstanzaSearch con i dati dell'istanza.
   * @update SCRIVA-1485 è stato rimosso il blocco di codice relativo alla verifica per lo stato "integrazione" fatto manualmente dal fe. Ora viene sempre preso "attore_gestione_istanza".
   */
  visualizzaIstanza(istanza: IstanzaSearch) {
    const codAdempimento = istanza.cod_adempimento;
    const codAmbito = istanza.cod_ambito;

    // // Verifico se l'istanza è in integrazione
    // const isIntegrazioneStatus: boolean =
    //   AllegatiIntegrazioniService.isIntegrazioneStatus(
    //     istanza.cod_stato_istanza as StatoIstanzaEnum
    //   );
    // // Definisco l'attore gestione istanza sulla base dello stato integrazione
    // const attoreGestioneIstanza = isIntegrazioneStatus
    //   ? AttoreGestioneIstanzaEnum.READ_ONLY
    //   : istanza.attore_gestione_istanza;

    // Recupero l'attore gestione istanza per capire in che modalità aprire l'istanza
    const attoreGestioneIstanza = istanza.attore_gestione_istanza;

    this.router.navigate([`../ambito/${codAmbito}/istanza/${codAdempimento}`], {
      relativeTo: this.route.parent,
      state: {
        idIstanza: istanza.id_istanza,
        attoreGestioneIstanza: attoreGestioneIstanza,
      },
    });
  }
}
