/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 * Classe contenete le costanti legate all'omonimo componente.
 */
export class OperaFormConsts {
  private PH_DESCRZIONE_VIA: string = `Testo libero con una breve descrizione delle principali caratteristiche dimensionali, tipologiche, funzionali del progetto e delle opere connesse, delle motivazioni della proposta progettuale, unitamente ad altre eventuali informazioni di sintesi pertinenti alla richiesta in oggetto. Esplicitare se trattasi di nuova realizzazione o di modifica/estensione di progetto/opera esistente`;

  SITO_RETE_NATURA_LABEL: string="Siti Rete Natura 2000";
  SITO_RETE_NATURA_FORM: string ="sitoReteNatura";
  SITO_RETE_NATURA_INTERFERITO_FORM: string ="sitoReteNaturaInterferito";
  AREA_PROTETTA_LABEL: string="Aree Naturali Protette";
  AREA_PROTETTA_FORM: string ="areaProtetta";
  COMUNE_LABEL: string="Comune";
  COMUNE_CATASTO_FORM: string ="comuneCatasto";

  /**
   * Getter per la variabile di classe.
   * @returns string con il valore della costante.
   */
  get phDescrizioneVIA(): string {
    // Ritorno il valore come costante
    return this.PH_DESCRZIONE_VIA;
  }
}
