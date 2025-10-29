/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service;

import java.security.NoSuchAlgorithmException;

public interface CaptchaService {

	String[] generateCaptchaImage(String previousCaptchaId) throws NoSuchAlgorithmException;
	boolean validateCaptcha(String captchaId, String captchaAnswer) throws NoSuchAlgorithmException;
	

}
