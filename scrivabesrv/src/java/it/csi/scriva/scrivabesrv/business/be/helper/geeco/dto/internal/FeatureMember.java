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
@XmlType(name = "FeatureMember", namespace = "http://www.opengis.net/gml")
public class FeatureMember {
	@XmlElement(name = "comune", namespace = "http://mapserver.gis.umn.edu/mapserver")
    private Comune comune;

	
    // Getter e Setter
    public Comune getComune() {
        return comune;
    }

    public void setComune(Comune comune) {
        this.comune = comune;
    }
}