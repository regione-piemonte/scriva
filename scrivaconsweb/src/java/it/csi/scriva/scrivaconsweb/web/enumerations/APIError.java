/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.web.enumerations;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = APIErrorSerializer.class)
public enum APIError {
	
	GENERIC_ERROR("0000", "Generic error"),
	REQUEST_VALIDATION_ERROR("0100", "Request validation error"),
	TOPIC_SUBSCRIPTION_ERROR("0401", "")
	;
	
	private String code;
	private String description;

	private APIError(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
