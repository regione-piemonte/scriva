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

import it.csi.scriva.scrivabesrv.dto.IstanzaSearchResultDTO;
import it.csi.scriva.scrivabesrv.dto.SearchDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ProfiloAppEnum;

import java.util.List;

/**
 * The interface Search dao.
 *
 * @author CSI PIEMONTE
 */
public interface SearchDAO {


    /**
     * Search istanze fo list.
     *
     * @param searchCriteria the search criteria
     * @param offset         the offset
     * @param limit          the limit
     * @param sort           the sort
     * @return the list
     */
    List<IstanzaSearchResultDTO> searchIstanze(SearchDTO searchCriteria, String offset, String limit, String sort);

    /**
     * Search istanze bo list.
     *
     * @param searchCriteria the search criteria
     * @param offset         the offset
     * @param limit          the limit
     * @param sort           the sort
     * @param profiloApp     the profilo app
     * @return the list
     */
    List<IstanzaSearchResultDTO> searchIstanze(SearchDTO searchCriteria, String offset, String limit, String sort, ProfiloAppEnum profiloApp);

    /**
     * Search istanze old list.
     *
     * @param searchCriteria the search criteria
     * @param offset         the offset
     * @param limit          the limit
     * @param sort           the sort
     * @return the list
     */
    List<IstanzaSearchResultDTO> searchIstanzeOLD(SearchDTO searchCriteria, String offset, String limit, String sort);

    /**
     * Search istanze old list.
     *
     * @param searchCriteria the search criteria
     * @param offset         the offset
     * @param limit          the limit
     * @param sort           the sort
     * @param profiloApp     the profilo app
     * @return the list
     */
    List<IstanzaSearchResultDTO> searchIstanzeOLD(SearchDTO searchCriteria, String offset, String limit, String sort, ProfiloAppEnum profiloApp);

}