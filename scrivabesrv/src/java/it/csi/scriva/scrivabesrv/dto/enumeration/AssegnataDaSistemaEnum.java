/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 * 
 */
package it.csi.scriva.scrivabesrv.dto.enumeration;

import java.util.Arrays;

/**
 * The Enum AssegnataDaSistemaEnum.
 *
 * @author CSI PIEMONTE
 */
public enum AssegnataDaSistemaEnum {
	

	/** The utente. */
	UTENTE("0"),
	
	/** AC attribuita da sistema con scriva_r_adempi_competenza.ind_adesione_adempimento=ind_adesione=2 */
	ADESIONE_2("2"),
	
	/** AC attribuita da sistema con scriva_r_adempi_competenza.ind_adesione_adempimento=ind_adesione=3*/
	ADESIONE_3("3"),
	
	/** AC calcolata da sistema per SITO RETE NATURA */
	COMUNE("4"),
	
	/** The natura 2000. */
	NATURA_2000("5"),
	
	/** AC calcolata da sistema per categoria progettuale */
	CAT_PROGETTUALE("6");
	
	private final String descrizione;

	/**
	 * Instantiates a new assegnata da sistema enum.
	 *
	 * @param descrizione the descrizione
	 */
	AssegnataDaSistemaEnum(String descrizione) {
		this.descrizione = descrizione;
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
     * Find by descr tipo evento enum.
     *
     * @param descrizione the descrizione
     * @return the tipo evento enum
     */
    public static AssegnataDaSistemaEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }
	
}
