/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * The type Oggetto geo json.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoGeoJson implements Serializable {

	private List<OggettoGeoJsonLabel> service;

	/**
	 * Gets service.
	 *
	 * @return the service
	 */
	public List<OggettoGeoJsonLabel> getService() {
		return service;
	}

	/**
	 * Sets service.
	 *
	 * @param service the service
	 */
	public void setService(List<OggettoGeoJsonLabel> service) {
		this.service = service;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OggettoGeoJson other = (OggettoGeoJson) obj;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		return true;
	}






}