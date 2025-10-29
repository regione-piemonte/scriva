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
 * The type Oggetto geo json level.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoGeoJsonLevel implements Serializable {

	private String label;
	private String type;
	private String enabled;
	private List<OggettoGeoJsonCoordinates> geojson;
	private OggettoGeoJsonParams params;

	/**
	 * Gets label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets label.
	 *
	 * @param label the label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets type.
	 *
	 * @param type the type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets enabled.
	 *
	 * @return the enabled
	 */
	public String getEnabled() {
		return enabled;
	}

	/**
	 * Sets enabled.
	 *
	 * @param enabled the enabled
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets geojson.
	 *
	 * @return the geojson
	 */
	public List<OggettoGeoJsonCoordinates> getGeojson() {
		return geojson;
	}

	/**
	 * Sets geojson.
	 *
	 * @param geojson the geojson
	 */
	public void setGeojson(List<OggettoGeoJsonCoordinates> geojson) {
		this.geojson = geojson;
	}

	/**
	 * Gets params.
	 *
	 * @return the params
	 */
	public OggettoGeoJsonParams getParams() {
		return params;
	}

	/**
	 * Sets params.
	 *
	 * @param params the params
	 */
	public void setParams(OggettoGeoJsonParams params) {
		this.params = params;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
		result = prime * result + ((geojson == null) ? 0 : geojson.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		OggettoGeoJsonLevel other = (OggettoGeoJsonLevel) obj;
		if (enabled == null) {
			if (other.enabled != null)
				return false;
		} else if (!enabled.equals(other.enabled))
			return false;
		if (geojson == null) {
			if (other.geojson != null)
				return false;
		} else if (!geojson.equals(other.geojson))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}







}