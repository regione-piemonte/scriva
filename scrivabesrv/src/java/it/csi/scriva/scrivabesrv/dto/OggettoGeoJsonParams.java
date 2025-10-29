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

/**
 * The type Oggetto geo json params.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoGeoJsonParams implements Serializable {

	private String url;
	private String layer;
	private String format;

	/**
	 * Gets url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets url.
	 *
	 * @param url the url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets layer.
	 *
	 * @return the layer
	 */
	public String getLayer() {
		return layer;
	}

	/**
	 * Sets layer.
	 *
	 * @param layer the layer
	 */
	public void setLayer(String layer) {
		this.layer = layer;
	}

	/**
	 * Gets format.
	 *
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * Sets format.
	 *
	 * @param format the format
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((layer == null) ? 0 : layer.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		OggettoGeoJsonParams other = (OggettoGeoJsonParams) obj;
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (layer == null) {
			if (other.layer != null)
				return false;
		} else if (!layer.equals(other.layer))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}



}