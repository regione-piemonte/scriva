/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.csi.scriva.scrivaconsweb.web.enumerations.APIError;
import it.csi.scriva.scrivaconsweb.web.enumerations.APIResult;

@JsonInclude(Include.NON_NULL)
public abstract class AbstractResponseVO {

	protected APIResult result = APIResult.SUCCESS;
	protected APIError error;
	protected String message;	
	
	public APIResult getResult() {
		return result;
	}

	public void setResult(APIResult result) {
		this.result = result;
	}

	public APIError getError() {
		return error;
	}

	public void setError(APIError error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
