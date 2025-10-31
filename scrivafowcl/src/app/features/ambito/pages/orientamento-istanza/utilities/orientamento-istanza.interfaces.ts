/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { FormioForm } from 'angular-formio';
import { CompetenzaTerritorio } from '../../../../../shared/models';
import { FormioFormBuilderTypes } from '../../../../../shared/services/formio/utilities/formio.types';

/**
 * Interfaccia che cerca di rappresentare quanto più in maniera utile la struttura del FormIo per VIA.
 */
export interface IFormIoOrientamentoDichiarazioni extends FormioForm {
  components?: FormioFormBuilderTypes[];
  display?: string;
  ACPratica?: CompetenzaTerritorio[];
  imports?: IFormIoOrientamentoDichiarazioniImports;
}

/**
 * Interfaccia che cerca di rappresentare quanto più in maniera utile la struttura del FormIo per VIA.
 * Oggetto: imports.
 * @author Ismaele Bottelli
 * @date 10/12/2024
 * @jira SCRIVA-1568
 * @notes La mappatura delle proprietà dell'oggetto hanno la seguente logica: tutte le proprietà di primo livello sono collegate all'AC di Regione Piemonte.
 *        Se nella gestione delle AC è stata selezionata un'altra AC rispetto a Regione Piemonte, le informazioni saranno all'interno della proprietà "autoritaCompetenteSpecifica".
 */
export interface IFormIoOrientamentoDichiarazioniImports {
  acWeb?: string;
  codAutCompetente?: string;
  codTipoAdempimento?: string;
  // In caso di regione piemonte questa proprietà non è valorizzata
  autoritaCompetente?: CompetenzaTerritorio;
  // In caso di regione piemonte questa proprietà non è valorizzata
  autoritaCompetenteSpecifica?: IFormIoOrientamentoDichiarazioniImports;
}