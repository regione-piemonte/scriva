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
import it.csi.scriva.scrivabesrv.dto.LoadNotificheCountDTO;
import it.csi.scriva.scrivabesrv.dto.NotificaApplicativaDTO;
import it.csi.scriva.scrivabesrv.dto.SearchNotificheDTO;

import java.util.List;


/**
 * The interface Ambiti service.
 *
 * @author CSI PIEMONTE
 */
public interface NotificheService {

	/**
	 * Load Notifiche list.
	 *
	 * @param codiceFiscale    the codice fiscale
	 * @param codComponenteApp the cod componente app
	 * @param offset           the offset
	 * @param limit            the limit
	 * @param sort             the sort
	 * @return the list
	 */
	List<NotificaApplicativaDTO> loadNotifiche(String codiceFiscale, String codComponenteApp, String offset, String limit, String sort);


	/**
	 * Load notifiche list.
	 *
	 * @param codiceFiscale    the codice fiscale
	 * @param codComponenteApp the cod componente app
	 * @param searchCriteria   the search criteria
	 * @param offset           the offset
	 * @param limit            the limit
	 * @param sort             the sort
	 * @return the list
	 */
	List<NotificaApplicativaDTO> loadNotifiche(String codiceFiscale, String codComponenteApp, SearchNotificheDTO searchCriteria, String offset, String limit, String sort);

	/**
	 * Update notifiche tutte lette integer.
	 *
	 * @param notificaApplicativa the notifica applicativa
	 * @param attoreScriva        the attore scriva
	 * @return the integer
	 */
	Integer updateNotifica(NotificaApplicativaDTO notificaApplicativa, AttoreScriva attoreScriva);

	/**
	 * Update notifica integer.
	 *
	 * @param notificaApplicativaList the notifica applicativa list
	 * @param attoreScriva            the attore scriva
	 * @return the integer
	 */
	Integer updateNotifica(List<NotificaApplicativaDTO> notificaApplicativaList, AttoreScriva attoreScriva);

	/**
	 * Update notifiche tutte lette integer.
	 *
	 * @param attoreScriva the attore scriva
	 * @return the integer
	 */
	Integer updateNotificheTutteLette(AttoreScriva attoreScriva);

	/**
	 * Update notifiche tutte lette integer.
	 *
	 * @param attoreScriva   the attore scriva
	 * @param searchCriteria the search criteria
	 * @return the integer
	 */
	Integer updateNotificheTutteLette(AttoreScriva attoreScriva, SearchNotificheDTO searchCriteria);

	/**
	 * Gets pagination header.
	 *
	 * @param notificaApplicativaList the notifica applicativa list
	 * @return the pagination header
	 */
	LoadNotificheCountDTO getNotificheCountHeader(List<NotificaApplicativaDTO> notificaApplicativaList);

}