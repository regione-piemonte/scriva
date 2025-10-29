/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Comune", namespace = "http://mapserver.gis.umn.edu/mapserver")
public class Comune {
	
	@XmlElement(name = "comune_ist", namespace = "http://mapserver.gis.umn.edu/mapserver")
	private String comune_ist;
	@XmlElement(name = "comune_nom", namespace = "http://mapserver.gis.umn.edu/mapserver")
    private String comune_nom;

    // Getter e Setter
    public String getComune_ist() {
		return comune_ist;
	}

	public void setComune_ist(String comune_ist) {
		this.comune_ist = comune_ist;
	}

	public String getComune_nom() {
		return comune_nom;
	}

	public void setComune_nom(String comune_nom) {
		this.comune_nom = comune_nom;
	}


}
