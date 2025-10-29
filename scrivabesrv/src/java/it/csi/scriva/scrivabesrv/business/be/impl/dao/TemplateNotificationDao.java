/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 * 
 */
package it.csi.scriva.scrivabesrv.business.be.impl.dao;

import java.util.List;

import it.csi.scriva.scrivabesrv.dto.NotificaDTO;
import it.csi.scriva.scrivabesrv.dto.NotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TemplateExtendedDTO;

/**
 * @author CSI PIEMONTE
 *
 * @param
 * @return
 */
public interface TemplateNotificationDao {



	/**
	*
	* @param idIstanza
	* @param codProfiloApp
	* @return
	*
	*/
	List<NotificaExtendedDTO> loadCompilanti(Long idIstanza, String codProfiloApp);

	/**
	*
	* @param codProfiloApp
	* @return
	*
	*/
	List<NotificaExtendedDTO> loadFunzionari(String codProfiloApp);
	
	/**
	 * Load funzionari AC.
	 *
	 * @param idIstanza the id istanza
	 * @return the list
	 */
	List<NotificaExtendedDTO> loadFunzionariAcPrincipale(Long idIstanza);

	/**
	 * Load funzionari ac secondaria.
	 *
	 * @param idIstanza the id istanza
	 * @return the list
	 */
	List<NotificaExtendedDTO> loadFunzionariAcSecondaria(Long idIstanza);

	/**
	*
	* @param cfFunzionario
	* @return
	*
	*/
	List<NotificaExtendedDTO> loadFunzionarioInLinea(String cfFunzionario);

	/**
	*
	* @param cfCompilante
	* @return
	*
	*/
	List<NotificaExtendedDTO> loadCompilanteInLinea(String cfCompilante);

	/**
	*
	* @param idIstanza
	* @param emailOPec
	* @return
	*
	*/
	List<NotificaExtendedDTO> loadSoggettoPG(Long idIstanza, boolean emailOPec);

	/**
	*
	* @param idIstanza
	* @param emailOPec
	* @return
	*
	*/
	List<NotificaExtendedDTO> loadSoggettoPF(Long idIstanza, Boolean emailOPec);

	/**
	*
	* @param idIstanza
	* @param emailOPec
	* @return
	*
	*/
	List<NotificaExtendedDTO> loadSoggettoLR(Long idIstanza, Boolean emailOPec);

}
