/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */

package it.csi.scriva.scrivaconsweb.web;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.HttpMethod;

import org.apache.log4j.Logger;

public class ProxyResourceFilter {
	
	private static Logger logger = Logger.getLogger(ProxyResourceFilter.class);
	
	private static List<ProxyResourceElement> allowedPaths;
	
	public static boolean isPathAllowed(String scope, String method, String path) {
	
		if (null == allowedPaths) {
			populateFilter();
		}
		
		var isAllowed = allowedPaths.stream().anyMatch(e -> 
		e.getMethod().equalsIgnoreCase(method != null ? method : "") &&
		e.getScope().equalsIgnoreCase(scope != null ? scope : "") &&
		matchPath(e, path));
		
		if (!isAllowed) {
			logger.warn(String.format("Accesso verso la risorsa %s/%s con metodo %s non consentito", scope, path, method));
		}
		
		return isAllowed;

	}
	
	private static boolean matchPath(ProxyResourceElement e, String path) {
		String currentPath = path != null ? path : "";
		return e.isExactMatch() ? currentPath.equals(e.getPath())
								: currentPath.startsWith(e.getPath());
	}
	
	private static void populateFilter() {
		allowedPaths = new ArrayList<>();
		
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "adempimenti"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "adempimenti-config"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "ambiti"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.POST, "", "allegati/_download"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.POST, "", "allegati/_search"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "adempimenti"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.POST, "", "captcha/_validate"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "captcha"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "categorie-allegato"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "categorie-progettuali/id-oggetto-istanza"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "competenze-territorio"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "estensioni-allegato/code-adempimento/APP"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "help"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "istanza-vincoli-aut"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.POST, "", "istanze-pubblicate"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "istanze-pubblicate/mappe"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "limiti-amministrativi/comuni"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "limiti-amministrativi/province"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "messaggi"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "note-pubblicate"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.POST, "secure", "eventi"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "secure", "istanze"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.POST, "secure/multipart", "allegati"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "secure", "osservazioni"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.PUT, "secure", "osservazioni"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.POST, "secure", "osservazioni"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.DELETE, "secure", "osservazioni"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.DELETE, "secure", "allegati"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "secure", "ping/spid"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "ping",true));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "stati-istanza"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "tipi-adempimento"));
		allowedPaths.add(new ProxyResourceElement(HttpMethod.GET, "", "tipologie-allegato/code-adempimento"));
	}

}

class ProxyResourceElement {
	
	private String scope;
	private String method;
	private String path;
	private boolean exactMatch; // false di default

	public ProxyResourceElement(String method, String scope, String path) {
		this(method, scope, path, false);
	}

	public ProxyResourceElement(String method, String scope, String path, boolean exactMatch) {
		this.method = method;
		this.scope = scope;
		this.path = path;
		this.exactMatch = exactMatch;
	}

	protected String getScope() {
		return scope != null ? scope : "";
	}

	protected String getMethod() {
		return method != null ? method : "";
	}

	protected String getPath() {
		return path != null ? path : "";
	}

	public boolean isExactMatch() {
		return exactMatch;
	}
	
}
