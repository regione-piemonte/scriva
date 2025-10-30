/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.business;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import it.csi.scriva.scrivaconsweb.util.SpringInjectorInterceptor;
import it.csi.scriva.scrivaconsweb.util.SpringSupportedResource;
import it.csi.scriva.scrivaconsweb.web.ProxyResource;

@ApplicationPath("api/")
public class RestApplication extends Application{
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public RestApplication(){
		singletons.add(new SpringInjectorInterceptor());
		singletons.add(new ProxyResource());

		for (Object c : singletons) {
			if (c instanceof SpringSupportedResource) {
				SpringApplicationContextHelper.registerRestEasyController(c);
			}
		}

	}

	@Override
	public Set<Class<?>> getClasses() {
		return empty;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
