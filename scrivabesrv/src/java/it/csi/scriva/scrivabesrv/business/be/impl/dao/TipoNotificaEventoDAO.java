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

import it.csi.scriva.scrivabesrv.dto.TipoNotificaEventoExtendedDTO;

import java.util.List;

/**
 * The interface Tipo notifica dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoNotificaEventoDAO {

    /**
     * Load tipi messaggio list.
     *
     * @param flgAttivi the flg attivi
     * @return List<TipoNotificaDTO>         list
     */
    List<TipoNotificaEventoExtendedDTO> loadTipiNotificaEvento(Boolean flgAttivi);

    /**
     * Load tipo messaggio list.
     *
     * @param idTipoNotificaEvento the id tipo notifica evento
     * @return List<TipoNotificaDTO>         list
     */
    List<TipoNotificaEventoExtendedDTO> loadTipoNotificaEvento(Long idTipoNotificaEvento);

    /**
     * Load tipo messaggio by code list.
     *
     * @param codTipoNotifica the cod tipo notifica
     * @param codTipoEvento   the cod tipo evento
     * @return List<TipoNotificaDTO>         list
     */
    List<TipoNotificaEventoExtendedDTO> loadTipoNotificaEvento(String codTipoNotifica, String codTipoEvento);

    /**
     * Load tipo notifica evento by cod adempimento list.
     *
     * @param codAdempimento the cod adempimento
     * @param flgAttivi      the flg attivi
     * @return the list
     */
    List<TipoNotificaEventoExtendedDTO> loadTipoNotificaEventoByCodAdempimento(String codAdempimento, Boolean flgAttivi);

    /**
     * Load tipo notifica evento by id istanza list.
     *
     * @param idIstanza the id istanza
     * @param flgAttivi the flg attivi
     * @return the list
     */
    List<TipoNotificaEventoExtendedDTO> loadTipoNotificaEventoByIdIstanza(Long idIstanza, Boolean flgAttivi);

	/**
	*
	* @param codTipoEvento
	* @return
	*
	*/
	List<TipoNotificaEventoExtendedDTO> loadTipoNotificaEvento(String codTipoEvento);

}