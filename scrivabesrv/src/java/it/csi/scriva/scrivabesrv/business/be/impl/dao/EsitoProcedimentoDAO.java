/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl.dao;

import it.csi.scriva.scrivabesrv.dto.EsitoProcedimentoDTO;

import java.util.List;

/**
 * The interface Ambito dao.
 *
 * @author CSI PIEMONTE
 */
public interface EsitoProcedimentoDAO {

    /**
     * Load esito procedimento list.
     *
     * @param idEsitoProcedimento  the id esito procedimento
     * @param codEsitoProcedimento the cod esito procedimento
     * @return the list
     */
    List<EsitoProcedimentoDTO> loadEsitoProcedimento(Long idEsitoProcedimento, String codEsitoProcedimento);

}