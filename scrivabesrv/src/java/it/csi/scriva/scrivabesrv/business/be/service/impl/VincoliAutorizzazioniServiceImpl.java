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
import it.csi.scriva.scrivabesrv.business.be.impl.dao.VincoloAutorizzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.VincoliAutorizzazioniService;
import it.csi.scriva.scrivabesrv.dto.VincoloAutorizzaExtendedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The Vincoli service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class VincoliAutorizzazioniServiceImpl extends BaseApiServiceImpl implements VincoliAutorizzazioniService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private VincoloAutorizzaDAO vincoloAutorizzaDAO;

    /**
     * Load vincoli autorizzazioni list.
     *
     * @return the list
     */
    @Override
    public List<VincoloAutorizzaExtendedDTO> loadVincoliAutorizzazioni() {
        logBegin(className);
        List<VincoloAutorizzaExtendedDTO> vincoliList = null;
        try {
            vincoliList = vincoloAutorizzaDAO.loadVincoliAutorizzazioni();
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return vincoliList;
    }

    /**
     * Load Vincoli autorizzazione by adempimento list.
     *
     * @param idAdempimento the id adempimento
     * @return the list
     */
    @Override
    public List<VincoloAutorizzaExtendedDTO> loadVincoloAutorizzazioneByAdempimento(Long idAdempimento) {
        logBegin(className);
        List<VincoloAutorizzaExtendedDTO> vincoliList = null;
        try {
            vincoliList = vincoloAutorizzaDAO.loadVincoloAutorizzazioneByAdempimento(idAdempimento);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return vincoliList;
    }

    /**
     * Load Vincoli idrogeologici by id_oggetto_istanza list.
     *
     * @param id_oggetto_istanza the id_oggetto_istanza
     * @return the list
     */
    @Override
    public List<VincoloAutorizzaExtendedDTO> loadVincoloIdrogeologico(Long id_oggetto_istanza) {
        logBegin(className);
        List<VincoloAutorizzaExtendedDTO> vincoliList = null;
        try {
            vincoliList = vincoloAutorizzaDAO.loadVincoloIdrogeologico(id_oggetto_istanza);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return vincoliList;
    }



}