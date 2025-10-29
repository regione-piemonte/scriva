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

import it.csi.scriva.scrivabesrv.dto.EsitoProcedimentoDTO;

import java.util.List;

/**
 * The interface Esiti procedimento service.
 *
 * @author CSI PIEMONTE
 */
public interface EsitiProcedimentoService {

    /**
     * Load esiti procedimento list.
     *
     * @return the list
     */
    List<EsitoProcedimentoDTO> loadEsitiProcedimento();

    /**
     * Load esiti procedimento by id list.
     *
     * @param idEsitoProcedimento the id esito procedimento
     * @return the list
     */
    List<EsitoProcedimentoDTO> loadEsitiProcedimentoById(Long idEsitoProcedimento);

    /**
     * Load esiti procedimento by cid list.
     *
     * @param codEsitoProcedimento the cod esito procedimento
     * @return the list
     */
    List<EsitoProcedimentoDTO> loadEsitiProcedimentoByCod(String codEsitoProcedimento);

    /**
     * Load esiti procedimento list.
     *
     * @param idEsitoProcedimento  the id esito procedimento
     * @param codEsitoProcedimento the cod esito procedimento
     * @return the list
     */
    List<EsitoProcedimentoDTO> loadEsitiProcedimento(Long idEsitoProcedimento, String codEsitoProcedimento);

}