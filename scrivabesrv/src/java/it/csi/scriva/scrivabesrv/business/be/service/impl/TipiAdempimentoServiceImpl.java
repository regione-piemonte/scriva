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
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAdempimentoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.TipiAdempimentoService;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * The type Tipi adempimento service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class TipiAdempimentoServiceImpl extends BaseApiServiceImpl implements TipiAdempimentoService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private TipoAdempimentoDAO tipoAdempimentoDAO;

    /**
     * Load ambito list.
     *
     * @param idAmbito           the id ambito
     * @param idCompilante       the id compilante
     * @param codTipoAdempimento the cod tipo adempimento
     * @param codAmbito          the cod ambito
     * @return the list
     */
    @Override
    public List<TipoAdempimentoExtendedDTO> loadTipiAdempimento(Long idAmbito, Long idCompilante, String codTipoAdempimento, String codAmbito, Boolean flgAttivo) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        List<TipoAdempimentoExtendedDTO> tipoAdempimentoList = null;
        try {
            tipoAdempimentoList = tipoAdempimentoDAO.loadTipiAdempimento(idAmbito, idCompilante, codTipoAdempimento, codAmbito, flgAttivo);
        } catch (Exception e) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            LOGGER.error(getClassFunctionErrorInfo(className, methodName, e));
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
        }
        return tipoAdempimentoList;
    }

    /**
     * Load ambito list.
     *
     * @param idTipoAdempimento the id tipo adempimento
     * @return the list
     */
    @Override
    public List<TipoAdempimentoExtendedDTO> loadTipoAdempimento(Long idTipoAdempimento) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        List<TipoAdempimentoExtendedDTO> tipoAdempimentoList = null;
        try {
            tipoAdempimentoList = tipoAdempimentoDAO.loadTipoAdempimento(idTipoAdempimento);
        } catch (Exception e) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            LOGGER.error(getClassFunctionErrorInfo(className, methodName, e));
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
        }
        return tipoAdempimentoList;
    }

    /**
     * Load tipi adempimento by cf funzionario list.
     *
     * @param cfFunzionario the cf funzionario
     * @return the list
     */
    @Override
    public List<TipoAdempimentoExtendedDTO> loadTipiAdempimentoByCfFunzionario(String cfFunzionario, Long idAmbito, String codTipoAdempimento, String codAmbito, Boolean flgAttivo) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        List<TipoAdempimentoExtendedDTO> tipoAdempimentoList = null;
        try {
            tipoAdempimentoList = tipoAdempimentoDAO.loadTipiAdempimentoByCfFunzionario(cfFunzionario, idAmbito, codTipoAdempimento, codAmbito, flgAttivo);
        } catch (Exception e) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            LOGGER.error(getClassFunctionErrorInfo(className, methodName, e));
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
        }
        return tipoAdempimentoList;
    }
}