/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.business.service.exception;

public class ProxyRequestException  extends Exception {

	private static final long serialVersionUID = 1L;

	public ProxyRequestException(String message) {
		super(message);
	}

	public ProxyRequestException(Throwable cause) {
		super(cause);
	}

	public ProxyRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProxyRequestException() {
		super();
	}

}
