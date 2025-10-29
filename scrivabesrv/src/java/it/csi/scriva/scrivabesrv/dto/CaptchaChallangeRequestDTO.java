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
public class CaptchaChallangeRequestDTO implements Serializable {

	@JsonProperty("captchaCode")
	private String captchaId;

	@JsonProperty("captchaAnswer")
	private String captchaAnswer;

	public String getCaptchaId() {
		return captchaId;
	}
	public void setCaptchaId(String captchaId) {
		this.captchaId = captchaId;
	}
	public String getCaptchaAnswer() {
		return captchaAnswer;
	}
	public void setCaptchaAnswer(String captchaAnswer) {
		this.captchaAnswer = captchaAnswer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((captchaAnswer == null) ? 0 : captchaAnswer.hashCode());
		result = prime * result + ((captchaId == null) ? 0 : captchaId.hashCode());
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
		CaptchaChallangeRequestDTO other = (CaptchaChallangeRequestDTO) obj;
		if (captchaAnswer == null) {
			if (other.captchaAnswer != null)
				return false;
		} else if (!captchaAnswer.equals(other.captchaAnswer))
			return false;
		if (captchaId == null) {
			if (other.captchaId != null)
				return false;
		} else if (!captchaId.equals(other.captchaId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CaptchaChallangeRequestDTO [captchaId=" + captchaId + ", captchaAnswer=" + captchaAnswer + "]";
	}

}