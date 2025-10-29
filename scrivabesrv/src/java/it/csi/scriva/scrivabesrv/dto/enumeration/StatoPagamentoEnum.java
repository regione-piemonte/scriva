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
 * The enum Stato pagamento enum.
 */
public enum StatoPagamentoEnum {

    /**
     * The Da effettuare.
     */
    DA_EFFETTUARE("Da effettuare", "010", 1L),
    /**
     * The Attesa ricevuta.
     */
    ATTESA_RICEVUTA("In attesa di ricevuta", "020", 2L),
    /**
     * The Effettuato fallito.
     */
    EFFETTUATO_FALLITO("Effettuato ma fallito", "030", 3L),
    /**
     * Pagato stato pagamento enum.
     */
    PAGATO("Pagato", "040", 4L),
    /**
     * Fallito stato pagamento enum.
     */
    FALLITO("Fallito", "050", 5L),
    /**
     * The Effettuato scaduto.
     */
    EFFETTUATO_SCADUTO("Effettuato ma scaduto", "060", 6L),

    /**
     * The Annullato utente.
     */
    ANNULLATO_UTENTE("Annullato da utente", "070", 7L);

    private final String descrizione;
    private final String codice;
    private final Long idStatoPagamento;


    StatoPagamentoEnum(String descrizione, String codice, Long idStatoPagamento) {
        this.descrizione = descrizione;
        this.codice = codice;
        this.idStatoPagamento = idStatoPagamento;
    }

    /**
     * Gets descrizione.
     *
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Gets codice.
     *
     * @return the codice
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Gets id stato pagamento.
     *
     * @return the id stato pagamento
     */
    public Long getIdStatoPagamento() {
        return idStatoPagamento;
    }

    /**
     * Find by descr stato pagamento enum.
     *
     * @param descrizione the descrizione
     * @return the stato pagamento enum
     */
    public static StatoPagamentoEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }
}