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
import it.csi.scriva.scrivabesrv.business.be.impl.dao.EsitoProcedimentoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.EsitiProcedimentoService;
import it.csi.scriva.scrivabesrv.dto.EsitoProcedimentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Esiti procedimento service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class EsitiProcedimentoServiceImpl extends BaseApiServiceImpl implements EsitiProcedimentoService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private EsitoProcedimentoDAO esitoProcedimentoDAO;

    /**
     * Load esiti procedimento list.
     *
     * @return the list
     */
    @Override
    public List<EsitoProcedimentoDTO> loadEsitiProcedimento() {
        logBegin(className);
        return this.loadEsitiProcedimento(null, null);
    }

    /**
     * Load esiti procedimento by id list.
     *
     * @param idEsitoProcedimento the id esito procedimento
     * @return the list
     */
    @Override
    public List<EsitoProcedimentoDTO> loadEsitiProcedimentoById(Long idEsitoProcedimento) {
        return this.loadEsitiProcedimento(idEsitoProcedimento, null);
    }

    /**
     * Load esiti procedimento by cid list.
     *
     * @param codEsitoProcedimento the cod esito procedimento
     * @return the list
     */
    @Override
    public List<EsitoProcedimentoDTO> loadEsitiProcedimentoByCod(String codEsitoProcedimento) {
        return this.loadEsitiProcedimento(null, codEsitoProcedimento);
    }

    /**
     * Load esiti procedimento list.
     *
     * @param idEsitoProcedimento  the id esito procedimento
     * @param codEsitoProcedimento the cod esito procedimento
     * @return the list
     */
    @Override
    public List<EsitoProcedimentoDTO> loadEsitiProcedimento(Long idEsitoProcedimento, String codEsitoProcedimento) {
        return esitoProcedimentoDAO.loadEsitoProcedimento(idEsitoProcedimento, codEsitoProcedimento);
    }
}