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

import it.csi.scriva.scrivabesrv.dto.ConfigurazioneDTO;

import java.util.List;

/**
 * The interface Configurazioni service.
 *
 * @author CSI PIEMONTE
 */
public interface ConfigurazioniService {

    /**
     * Load configurazioni list.
     *
     * @return the list
     */
    List<ConfigurazioneDTO> loadConfigurazioni();

    /**
     * Load configurazione by key list.
     *
     * @param key the key
     * @return the list
     */
    List<ConfigurazioneDTO> loadConfigurazioneByKey(String key);

}