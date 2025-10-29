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

import it.csi.scriva.scrivabesrv.dto.StatoProcedimentoStataleDTO;

import java.util.List;

/**
 * The interface Ambito dao.
 *
 * @author CSI PIEMONTE
 */
public interface StatoProcedimentoStataleDAO {

    /**
     * Load stati procedimento statale list.
     *
     * @param codComponenteApp the componente app
     * @return the list
     */
    List<StatoProcedimentoStataleDTO> loadStatiProcedimentoStatale(String codComponenteApp);

    /**
     * Load stati procedimento statale by id list.
     *
     * @param idStatoProcStatale the id stato proc statale
     * @return the list
     */
    List<StatoProcedimentoStataleDTO> loadStatiProcedimentoStataleById(Long idStatoProcStatale);

    /**
     * Load stati procedimento statale by code list.
     *
     * @param codStatoProcStatale the cod stato proc statale
     * @return the list
     */
    List<StatoProcedimentoStataleDTO> loadStatiProcedimentoStataleByCode(String codStatoProcStatale);

}