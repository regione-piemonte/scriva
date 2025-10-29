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
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoEventoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.TipiEventoService;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.TipoEventoExtendedDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Tipi evento service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class TipiEventoServiceImpl extends BaseApiServiceImpl implements TipiEventoService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private TipoEventoDAO tipoEventoDAO;

    /**
     * Load configurazioni list.
     *
     * @return the list
     */
    @Override
    public List<TipoEventoExtendedDTO> loadTipiEvento(String codComponenteApp) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        List<TipoEventoExtendedDTO> tipoEventoList = null;
        try {
            tipoEventoList = tipoEventoDAO.loadTipiEvento(codComponenteApp);
        } catch (Exception e) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            LOGGER.error(getClassFunctionErrorInfo(className, methodName, e));
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
        }
        return tipoEventoList;
    }

    /**
     * Load tipo evento by code list.
     *
     * @param codTipoEvento    the cod tipo evento
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    @Override
    public List<TipoEventoExtendedDTO> loadTipoEventoByCode(String codTipoEvento, String codComponenteApp) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        List<TipoEventoExtendedDTO> tipoEventoList = null;
        try {
            tipoEventoList = StringUtils.isNotBlank(codTipoEvento) ? tipoEventoDAO.loadTipoEventoByCode(codTipoEvento, codComponenteApp) : loadTipiEvento(codComponenteApp);
        } catch (Exception e) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            LOGGER.error(getClassFunctionErrorInfo(className, methodName, e));
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
        }
        return tipoEventoList;
    }

    /**
     * Gets tipo evento by stato istanza.
     *
     * @param idStatoIstanza the id stato istanza
     * @return the tipo evento by stato istanza
     */
    @Override
    public List<TipoEventoExtendedDTO> getTipoEventoByStatoIstanza(Long idStatoIstanza) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        List<TipoEventoExtendedDTO> tipoEventoList = null;
        try {
            tipoEventoList = tipoEventoDAO.loadTipoEventoByIdStatoIstanza(idStatoIstanza, null);
        } catch (Exception e) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            LOGGER.error(getClassFunctionErrorInfo(className, methodName, e));
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
        }
        return tipoEventoList;
    }

    /**
     * Gets tipo evento by code.
     *
     * @param codTipoEvento the cod tipo evento
     * @return the tipo evento by code
     */
    @Override
    public TipoEventoExtendedDTO getTipoEventoByCode(String codTipoEvento) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        List<TipoEventoExtendedDTO> tipoEventoList = null;
        try {
            tipoEventoList = tipoEventoDAO.loadTipoEventoByCode(codTipoEvento, null);
        } catch (Exception e) {
            LOGGER.error(getClassFunctionErrorInfo(className, methodName, e));
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
        }
        return tipoEventoList != null && !tipoEventoList.isEmpty() ? tipoEventoList.get(0) : null;
    }
}