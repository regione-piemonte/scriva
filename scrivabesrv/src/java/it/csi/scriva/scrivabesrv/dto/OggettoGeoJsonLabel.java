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
 * The type Oggetto geo json label.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoGeoJsonLabel implements Serializable {

	private String label;
	private List<OggettoGeoJsonLevel> level;

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
	 * Gets level.
	 *
	 * @return the level
	 */
	public List<OggettoGeoJsonLevel> getLevel() {
		return level;
	}

	/**
	 * Sets level.
	 *
	 * @param level the level
	 */
	public void setLevel(List<OggettoGeoJsonLevel> level) {
		this.level = level;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
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
		OggettoGeoJsonLabel other = (OggettoGeoJsonLabel) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		return true;
	}



}