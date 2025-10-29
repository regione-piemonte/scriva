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
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipologiaOggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.TipologieOggettoService;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoExtendedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The Tipologie oggetto service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class TipologieOggettoServiceImpl extends BaseApiServiceImpl implements TipologieOggettoService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private TipologiaOggettoDAO tipologiaOggettoDAO;


    /**
     * Load Tipologie Oggetto list.
     *
     * @return the list
     */
    @Override
    public List<TipologiaOggettoExtendedDTO> loadAll() {
        logBegin(className);
        List<TipologiaOggettoExtendedDTO> tipologiaOggettoList = null;
        try {
            tipologiaOggettoList = tipologiaOggettoDAO.loadAll();
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return tipologiaOggettoList;
    }


    /**
     * Load Tipologie Oggetto by idTipologiaOggetto list.
     *
     * @param idTipologiaOggetto the id
     * @return the tipologia oggetto by id
     */
    @Override
    public List<TipologiaOggettoExtendedDTO> getTipologiaOggetto(Long idTipologiaOggetto) {
        logBegin(className);
        List<TipologiaOggettoExtendedDTO> tipologiaOggettoList = null;
        try {
            tipologiaOggettoList = tipologiaOggettoDAO.loadTipologiaOggetto(idTipologiaOggetto);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return tipologiaOggettoList;
    }


    /**
     * Gets tipo evento by code.
     *
     * @param codTipologiaOggetto the cod tipo evento
     * @return the tipologia oggetto by code
     */
    @Override
    public List<TipologiaOggettoExtendedDTO> getTipologiaOggettoByCode(String codTipologiaOggetto) {
        logBegin(className);
        List<TipologiaOggettoExtendedDTO> tipologiaOggettoList = null;
        try {
            tipologiaOggettoList = tipologiaOggettoDAO.loadTipologiaOggettoByCode(codTipologiaOggetto);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return tipologiaOggettoList;
    }

    /**
     * Gets tipo evento by stato istanza.
     *
     * @param codAdempimento the adempimento code
     * @return the list by code
     */
    @Override
    public List<TipologiaOggettoExtendedDTO> loadTipologiaOggettoByCodeAdempimento(String codAdempimento) {
        logBegin(className);
        List<TipologiaOggettoExtendedDTO> tipologiaOggettoList = null;
        try {
            tipologiaOggettoList = tipologiaOggettoDAO.loadTipologiaOggettoByCodeAdempimento(codAdempimento);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return tipologiaOggettoList;
    }

    /**
     * Load tipologia oggetto by id layers list.
     *
     * @param idLayerList the id layer list
     * @return the list
     */
    @Override
    public List<TipologiaOggettoDTO> loadTipologiaOggettoByIdLayers(List<Long> idLayerList) {
        logBegin(className);
        List<TipologiaOggettoDTO> tipologiaOggettoList = null;
        try {
            tipologiaOggettoList = tipologiaOggettoDAO.loadTipologiaOggettoByIdLayers(idLayerList);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return tipologiaOggettoList;
    }

}