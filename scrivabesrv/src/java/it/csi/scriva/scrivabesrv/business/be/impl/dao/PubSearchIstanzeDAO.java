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

import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.PubIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.PubSearchDTO;
import it.csi.scriva.scrivabesrv.dto.SintesiDTO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

/**
 * The interface Search dao.
 *
 * @author CSI PIEMONTE
 */
public interface PubSearchIstanzeDAO {

    /**
     * Search istanze fo list.
     *
     * @param searchCriteria the search criteria
     * @param offset         the offset
     * @param limit          the limit
     * @param sort           the sort
     * @param attoreScriva   the attore scriva
     * @return the list
     */
    List<PubIstanzaDTO> searchIstanze(PubSearchDTO searchCriteria, String offset, String limit, String sort, AttoreScriva attoreScriva);
    
    List<SintesiDTO> searchSintesiIstanze(PubSearchDTO searchCriteria, String offset, String limit, String sort, AttoreScriva attoreScriva);

    /**
     * Load mappe string.
     *
     * @param id the id
     * @return the string
     */
    String loadMappe(Long id);

    /**
     * Load istanza by id list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    List<PubIstanzaDTO> loadIstanzaById(Long idIstanza);

	RowMapper<SintesiDTO> getRowMapper1() throws SQLException;
	
	/**
     * Storico istanza - get list data inizio osservazione by id istanza.
     *
     * @param idIstanza the id istanza
     * @return the list 
     */
	List<Timestamp> getStoricoDataInizioOsservazione(Long idIstanza);
	
	
	/**
     * Storico istanza - get max data fine osservazione by id istanza.
     *
     * @param idIstanza the id istanza
     * @return the list 
     */
	List<Timestamp> getStoricoMaxDataFineOsservazione(Long idIstanza);

	

}