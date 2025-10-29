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
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AmbitoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.AmbitiService;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Tipi evento service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class AmbitiServiceImpl extends BaseApiServiceImpl implements AmbitiService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private AmbitoDAO ambitoDAO;

    @Override
    public List<AmbitoDTO> loadAmbiti(Long idAmbito, String codAmbito) {
        logBeginInfo(className, "idAmbito : [" + idAmbito + "] - codAmbito : [" + codAmbito + "]");
        List<AmbitoDTO> ambitoList = null;
        try {
            ambitoList = ambitoDAO.loadAmbito(idAmbito, codAmbito);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return ambitoList;
    }

    @Override
    public String loadJsonAmbiti(Long idAmbito, String codAmbito) {
        logBeginInfo(className, "idAmbito : [" + idAmbito + "] - codAmbito : [" + codAmbito + "]");
        String jsonAmbitoList = null;
        try {
            jsonAmbitoList = ambitoDAO.loadJsonAmbitiAttivi();
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return jsonAmbitoList;
    }

    /**
     * Load ambito list.
     *
     * @param idAmbito the id ambito
     * @return the list
     */
    @Override
    public List<AmbitoDTO> loadAmbito(Long idAmbito) {
        logBeginInfo(className, "idAmbito : [" + idAmbito + "]");
        List<AmbitoDTO> ambitoList = null;
        try {
            ambitoList = ambitoDAO.loadAmbito(idAmbito, null);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return ambitoList;
    }
}