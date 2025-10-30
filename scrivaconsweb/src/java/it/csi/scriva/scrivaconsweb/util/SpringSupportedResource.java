/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.util;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

public abstract class SpringSupportedResource {

	public boolean springBeansInjected = false;

	private static Logger logger = Logger.getLogger(SpringSupportedResource.class);

	public void contextInitialized(ServletContext sc) {
		logger.info("inizializzo risorsa " + this.getClass().getSimpleName());
	}

	public boolean isSpringBeansInjected() {
		return springBeansInjected;
	}

	public void setSpringBeansInjected(boolean springBeansInjected) {
		this.springBeansInjected = springBeansInjected;
	}
}