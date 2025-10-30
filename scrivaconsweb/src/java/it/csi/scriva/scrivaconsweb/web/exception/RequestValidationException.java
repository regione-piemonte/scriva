/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.web.exception;

import it.csi.scriva.scrivaconsweb.web.enumerations.APIError;

public class RequestValidationException extends ApiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1847516978835151418L;

	public RequestValidationException() {
		super();
		this.setApiError(APIError.REQUEST_VALIDATION_ERROR);
	}

	public RequestValidationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(APIError.REQUEST_VALIDATION_ERROR, message, cause, enableSuppression, writableStackTrace);
	}

	public RequestValidationException(String message, Throwable cause) {
		super(APIError.REQUEST_VALIDATION_ERROR, message, cause);
	}

	public RequestValidationException(String message) {
		super(APIError.REQUEST_VALIDATION_ERROR, message);
	}

	public RequestValidationException(Throwable cause) {
		super(APIError.REQUEST_VALIDATION_ERROR, cause);
	}



}
