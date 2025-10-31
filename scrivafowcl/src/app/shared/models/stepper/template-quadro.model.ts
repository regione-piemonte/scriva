/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Istanza } from '../istanza/istanza.model';
import { Template } from './template.model';
import { Observable } from 'rxjs';

export interface TemplateQuadro {
  istanza: Istanza;
  template: Template;
  ind_visibile?: string;
}

export interface TemplateQuadroJoinReq {
  istanza: Observable<Istanza>;
  template: Observable<Template>;
  ind_visibile?: Observable<string>;
}