/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 * 
 */
package it.csi.scriva.scrivabesrv.business.be.exception;

/**
 * @author CSI PIEMONTE
 *
 * @param
 * @return
 */
public class NoRecipientsForNotificationException extends Exception {

	/**
	 * 
	 */
	public NoRecipientsForNotificationException() {
		
	}

	/**
	 * @param message
	 */
	public NoRecipientsForNotificationException(String message) {
		super(message);
		
	}

	/**
	 * @param cause
	 */
	public NoRecipientsForNotificationException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoRecipientsForNotificationException(String message, Throwable cause) {
		super(message, cause);
		
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NoRecipientsForNotificationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
