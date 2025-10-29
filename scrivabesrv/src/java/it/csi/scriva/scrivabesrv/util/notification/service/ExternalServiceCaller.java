/*
* ========================LICENSE_START=================================
* 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
package it.csi.scriva.scrivabesrv.util.notification.service;

import org.springframework.http.ResponseEntity;

public interface ExternalServiceCaller {
        ResponseEntity<String> callExternalService(String url, String token, String uuid, String id, String userId, String subject, String markdown);


}
