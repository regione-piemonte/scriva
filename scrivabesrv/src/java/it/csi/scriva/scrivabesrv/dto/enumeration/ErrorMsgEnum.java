/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.dto.enumeration;

import java.util.Arrays;

/**
 * The enum Error Messaage.
 *
 * @author CSI PIEMONTE
 */
public enum ErrorMsgEnum {
  EN01("Notifica - Allegati : se non è stato trovato neanche un documento padre per il record di configurazione allegati di interesse"),
  EN02("Notifica - Allegati : se è stato trovato un documento padre per il record di configurazione allegati di interesse ma non figli"),
  EN03("Notifica - Allegati : se non è stato trovato un documento figlio per il record di configurazione allegati di interesse");

  private final String descrizione;

  ErrorMsgEnum(String descrizione) {
    this.descrizione = descrizione;
  }

  /**
   * Gets descrizione.
   *
   * @return string descrizione
   */
  public String getDescrizione() {
    return descrizione;
  }

  /**
   * Find by descr azioni base enum.
   *
   * @param descrizione descrizione
   * @return ProfiloAppEnum azioni base enum
   */
  public static ErrorMsgEnum findByDescr(final String descrizione) {
    return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione))
            .findFirst().orElse(null);
  }

}
