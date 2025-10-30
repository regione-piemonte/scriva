/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.web.exception;

import it.csi.scriva.scrivaconsweb.web.enumerations.APIError;

public class ApiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3071029066029892212L;
	
	private APIError apiError;
	
	
	public ApiException() {
		setApiError(APIError.GENERIC_ERROR);
	}

	public ApiException(APIError apiError, String message) {
		super(message);
		this.setApiError(apiError);
	}

	public ApiException(APIError apiError, Throwable cause) {
		super(cause);
		this.setApiError(apiError);
	}

	public ApiException(APIError apiError, String message, Throwable cause) {
		super(message, cause);
		this.setApiError(apiError);
	}

	public ApiException(APIError apiError, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.setApiError(apiError);
	}

	public APIError getApiError() {
		return apiError;
	}

	public void setApiError(APIError apiError) {
		this.apiError = apiError;
	}

}
