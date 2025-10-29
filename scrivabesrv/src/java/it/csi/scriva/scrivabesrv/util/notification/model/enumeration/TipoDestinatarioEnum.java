/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.notification.model.enumeration;

import java.util.Arrays;

// TODO: Auto-generated Javadoc
/**
 * The Enum TipoDestinatarioEnum.
 *
 * @author CSI PIEMONTE
 */
public enum TipoDestinatarioEnum {
	
	/** The profilo fo. */
	PROFILO_FO("PROFILO FO"),
	
	/** The profilo bo. */
	PROFILO_BO("PROFILO BO"),
	
	/** The ac principale. */
	AC_PRINCIPALE("AC PRINCIPALE"),
	
	/** The ac econdaria. */
	AC_SECONDARIA("AC SECONDARIA"),
	
	/** The attore fo. */
	ATTORE_FO ("ATTORE FO"),
	
	/** The attore bo. */
	ATTORE_BO ("ATTORE BO"),
	
	/** The profilo richiedente. */
	PROFILO_RICHIEDENTE ("PROFILO RICHIEDENTE"),
	
	/** The altro destinatario. */
	ALTRO_DESTINATARIO ("ALTRO DESTINATARIO"),
	
	/** The altro sistema. */
	ALTRO_SISTEMA("ALTRO SISTEMA");

	
	/** The descrizione. */
	private final String descrizione;
	
	/**
	 * Instantiates a new tipo destinatario enum.
	 *
	 * @param descrizione the descrizione
	 */
	TipoDestinatarioEnum(String descrizione) {
		this.descrizione = descrizione;
		// TODO Auto-generated constructor stub
	}

    /**
     * Gets the descrizione.
     *
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }
    
    /**
     * Find by descr.
     *
     * @param descrizione the descrizione
     * @return the tipo destinatario enum
     */
    public static TipoDestinatarioEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}
