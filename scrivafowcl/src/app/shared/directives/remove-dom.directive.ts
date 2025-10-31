/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import {
  Directive,
  Input,
  OnInit,
  TemplateRef,
  ViewContainerRef,
} from '@angular/core';

@Directive({
  selector: '[scrivaRemove]',
})
export class ScrivaRemoveDomDirective implements OnInit {
  constructor(
    private _templateRef: TemplateRef<any>,
    private _viewContainerRef: ViewContainerRef
  ) {}

  ngOnInit() {}

  @Input()
  set scrivaRemove(remove: boolean) {
    // Richiamo la funzione di remove
    this.remove(remove);
  }

  /**
   * Funzione che mantiene o rimuove il DOM.
   * @param remove boolean che definisce se il DOM è da rimuovere.
   */
  private remove(remove: boolean) {
    if (remove) {
      this._viewContainerRef.clear();
    } else {
      this._viewContainerRef.createEmbeddedView(this._templateRef);
    }
  }
}

@Directive({
  selector: '[scrivaMaintain]',
})
export class ScrivaMaintainDomDirective implements OnInit {
  constructor(
    private _templateRef: TemplateRef<any>,
    private _viewContainerRef: ViewContainerRef
  ) {}

  ngOnInit() {}

  @Input()
  set scrivaMaintain(maintain: boolean) {
    // Richiamo la funzione di mantain
    this.maintain(maintain);
  }

  /**
   * Funzione che mantiene o rimuove il DOM.
   * @param maintain boolean che definisce se il DOM è da mantenere.
   */
  private maintain(maintain: boolean) {
    if (maintain) {
      this._viewContainerRef.createEmbeddedView(this._templateRef);
    } else {
      this._viewContainerRef.clear();
    }
  }
}
