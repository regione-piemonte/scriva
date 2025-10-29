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
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaChallangeDTO implements Serializable {

	@JsonProperty("captchaValidate")
	private String challange;

	public String getChallange() {
		return challange;
	}

	public void setChallange(String challange) {
		this.challange = challange;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((challange == null) ? 0 : challange.hashCode());
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
		CaptchaChallangeDTO other = (CaptchaChallangeDTO) obj;
		if (challange == null) {
			if (other.challange != null)
				return false;
		} else if (!challange.equals(other.challange))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CaptchaChallangeDTO [challange=" + challange + "]";
	}





}