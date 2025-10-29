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
 * The enum Informazioni scriva enum.
 *
 * @author CSI PIEMONTE
 */
public enum InformazioniScrivaEnum {

    /**
     * The Pf.
     */
    PF("Soggetto Persona Fisica"),

    /**
     * The Pg.
     */
    PG("Soggetto Persona Giuridica"),

    /**
     * The Pb.
     */
    PB("Persona Giuridica Pubblica"),

    /**
     * Oggetto informazioni scriva enum.
     */
    OGGETTO("Oggetto"),

    /**
     * Adempimento informazioni scriva enum.
     */
    ADEMPIMENTO("Adempimento"),

    /**
     * The Categoria oggetto.
     */
    CATEGORIA_OGGETTO("Categoria Oggetto"),

    /**
     * Csv allegati informazioni scriva enum.
     */
    CSV_ALLEGATI("CSV_ALLEGATI"),

    /**
     * Csv osservazioni cod informazione scriva informazioni scriva enum.
     */
    CSV_OSSERVAZIONI("CSV_DOC_OSS"),

    /**
     * Qdr der captazione informazioni scriva enum.
     */
    QDR_DER_CAPTAZIONE("QDR_DER_CAPTAZIONE"),

    /**
     * The Search tipo ogg.
     */
    SEARCH_TIPO_OGG("Tipologia oggetto da ricercare"),

    /**
     * The Classe doc.
     */
    CLASSE_DOC("Classe documento associata"),;

    private final String descrizione;

    InformazioniScrivaEnum(String descrizione) {
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
     * Find by descr informazioni scriva enum.
     *
     * @param descrizione descrizione
     * @return InformazioniScrivaEnum informazioni scriva enum
     */
    public static InformazioniScrivaEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}