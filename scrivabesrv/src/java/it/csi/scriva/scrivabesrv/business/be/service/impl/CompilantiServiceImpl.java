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
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CompilanteDAO;
import it.csi.scriva.scrivabesrv.business.be.service.CompilantiService;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Compilanti service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class CompilantiServiceImpl extends BaseApiServiceImpl implements CompilantiService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private CompilanteDAO compilanteDAO;

    /**
     * Load compilanti list.
     *
     * @return the list
     */
    @Override
    public List<CompilanteDTO> loadCompilanti() {
        logBegin(className);
        return compilanteDAO.loadCompilanti();
    }

    /**
     * Load compilanti list.
     *
     * @param codiceFiscale the codice fiscale
     * @return the list
     */
    @Override
    public List<CompilanteDTO> loadCompilanti(String codiceFiscale) {
        logBeginInfo(className, codiceFiscale);
        return compilanteDAO.loadCompilanteByCodiceFiscale(codiceFiscale);
    }

    /**
     * Updatec compilante integer.
     *
     * @param compilante the compilante
     * @return the integer
     */
    @Override
    public Integer updateCompilante(CompilanteDTO compilante) {
        logBeginInfo(className, compilante);
        return compilanteDAO.updateCompilante(compilante);
    }
}