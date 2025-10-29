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
 * The enum P pay codice esito enum.
 */
public enum PPayCodiceEsitoEnum {

    /**
     * Annullato p pay codice esito enum.
     */
    ANNULLATO("ANNULLATO", "200",0L, 0L),
    /**
     * Ok p pay codice esito enum.
     */
    SUCCESSO("SUCCESSO", "000",1L, 2L),
    /**
     * Ko p pay codice esito enum.
     */
    FALLITO("FALLITO", "100",2L, 3L);

    private final String descrizione;
    private final String codiceEsito;
    private final Long idStatoTentativoPagamento;
    private final Long idStatoPagamento;


    PPayCodiceEsitoEnum(String descrizione, String codiceEsito, Long idStatoTentativoPagamento, Long idStatoPagamento) {
        this.descrizione = descrizione;
        this.codiceEsito = codiceEsito;
        this.idStatoTentativoPagamento = idStatoTentativoPagamento;
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
     * Gets codice esito.
     *
     * @return the codice esito
     */
    public String getCodiceEsito() {
        return codiceEsito;
    }

    /**
     * Gets id stato tentativo pagamento.
     *
     * @return the id stato tentativo pagamento
     */
    public Long getIdStatoTentativoPagamento() {
        return idStatoTentativoPagamento;
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
     * Find by descr p pay codice esito enum.
     *
     * @param descrizione the descrizione
     * @return the p pay codice esito enum
     */
    public static PPayCodiceEsitoEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}