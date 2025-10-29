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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement(name = "FeatureCollection", namespace = "http://www.opengis.net/wfs")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FeatureCollection", namespace = "http://www.opengis.net/wfs")
public class FeatureCollection implements java.io.Serializable{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "featureMember", namespace = "http://www.opengis.net/gml")
	private List<FeatureMember> featureMember;
    
    public FeatureCollection featureMember(List<FeatureMember> featureMembers) {
    	  this.featureMember = featureMembers;
    	  return this;
    }

    // Getter e Setter
    public List<FeatureMember> getFeatureMembers() {
        return featureMember;
    }

    public void setFeatureMembers(List<FeatureMember> featureMembers) {
        this.featureMember = featureMembers;
    }
}