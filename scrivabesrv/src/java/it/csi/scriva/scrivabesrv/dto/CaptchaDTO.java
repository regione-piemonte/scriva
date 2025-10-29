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
public class CaptchaDTO implements Serializable {

	@JsonProperty("captchaCode")
	private String captchaId;
	@JsonProperty("captchaImg")
	private String captchaImg;
	public String getCaptchaId() {
		return captchaId;
	}
	public void setCaptchaId(String captchaId) {
		this.captchaId = captchaId;
	}
	public String getCaptchaImg() {
		return captchaImg;
	}
	public void setCaptchaImg(String captchaImg) {
		this.captchaImg = captchaImg;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((captchaId == null) ? 0 : captchaId.hashCode());
		result = prime * result + ((captchaImg == null) ? 0 : captchaImg.hashCode());
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
		CaptchaDTO other = (CaptchaDTO) obj;
		if (captchaId == null) {
			if (other.captchaId != null)
				return false;
		} else if (!captchaId.equals(other.captchaId))
			return false;
		if (captchaImg == null) {
			if (other.captchaImg != null)
				return false;
		} else if (!captchaImg.equals(other.captchaImg))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CaptchaDTO [captchaId=" + captchaId + ", captchaImg=" + captchaImg + "]";
	}



}