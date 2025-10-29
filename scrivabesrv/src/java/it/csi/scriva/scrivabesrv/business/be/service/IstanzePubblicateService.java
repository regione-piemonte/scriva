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

import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.PubIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.PubSearchDTO;
import it.csi.scriva.scrivabesrv.dto.SintesiDTO;

import java.util.List;

/**
 * The interface Istanze pubblicate service.
 *
 * @author CSI PIEMONTE
 */
public interface IstanzePubblicateService {

    /**
     * Search istanze pubblicate list.
     *
     * @param searchCriteria the search criteria
     * @param attoreScriva   the attore scriva
     * @param offset         the offset
     * @param limit          the limit
     * @param sort           the sort
     * @return the list
     */
    List<PubIstanzaDTO> searchIstanzePubblicate(PubSearchDTO searchCriteria, AttoreScriva attoreScriva,Integer offset, Integer limit, String sort);

    /**
     * Load istanza pubblicata by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    PubIstanzaDTO loadIstanzaPubblicataByIdIstanza(Long idIstanza);

    /**
     * Load mappe string.
     *
     * @param id the id
     * @return the string
     */
    String loadMappe(Long id);
    
    /**
     * Search istanze pubblicate list.
     *
     * @param searchCriteria the search criteria
     * @param attoreScriva   the attore scriva
     * @param offset         the offset
     * @param limit          the limit
     * @param sort           the sort
     * @return the list
     */
	List<SintesiDTO> searchSintesiIstanzePubblicate(PubSearchDTO searchCriteria, AttoreScriva attoreScriva, Integer offset, Integer limit, String sort);

}