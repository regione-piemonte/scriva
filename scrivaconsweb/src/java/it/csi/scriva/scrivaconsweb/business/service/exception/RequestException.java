/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.business.service.exception;

public class RequestException extends RuntimeException {

	private static final long serialVersionUID = -2631969419398877860L;

	public RequestException(String message) {
		super(message);
	}

	public RequestException(Throwable cause) {
		super(cause);
	}

	public RequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestException() {
		super();
	}

}
