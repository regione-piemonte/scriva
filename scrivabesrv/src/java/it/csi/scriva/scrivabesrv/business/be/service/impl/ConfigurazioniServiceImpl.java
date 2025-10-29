/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service.impl;

import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.service.ConfigurazioniService;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Configurazioni service.
 */
@Component
public class ConfigurazioniServiceImpl extends BaseApiServiceImpl implements ConfigurazioniService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private ConfigurazioneDAO configurazioneDAO;

    /**
     * Load configurazioni list.
     *
     * @return the list
     */
    @Override
    public List<ConfigurazioneDTO> loadConfigurazioni() {
        logBegin(className);
        return getConfigurazioni(null);
    }

    /**
     * Load configurazione by key list.
     *
     * @param key the key
     * @return the list
     */
    @Override
    public List<ConfigurazioneDTO> loadConfigurazioneByKey(String key) {
        logBeginInfo(className, key);
        return getConfigurazioni(key);
    }

    private List<ConfigurazioneDTO> getConfigurazioni(String key) {
        logBeginInfo(className, key);
        List<ConfigurazioneDTO> listConfigurazioni = null;
        try {
            listConfigurazioni = StringUtils.isBlank(key) ? configurazioneDAO.loadConfig() : configurazioneDAO.loadConfigByKey(key);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return listConfigurazioni;
    }
}