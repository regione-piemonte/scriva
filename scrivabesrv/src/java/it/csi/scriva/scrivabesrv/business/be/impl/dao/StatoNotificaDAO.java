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

import it.csi.scriva.scrivabesrv.dto.StatoNotificaDTO;

import java.util.List;

/**
 * The interface Stato notifica dao.
 *
 * @author CSI PIEMONTE
 */
public interface StatoNotificaDAO {

    /**
     * Load stati notifica list.
     *
     * @return the list
     */
    List<StatoNotificaDTO> loadStatiNotifica();

    /**
     * Load stato notifica by id list.
     *
     * @param idStatoNotifica the id stato notifica
     * @return the list
     */
    List<StatoNotificaDTO> loadStatoNotificaById(Long idStatoNotifica);

    /**
     * Load stato notifica by code list.
     *
     * @param codStatoNotifica the cod stato notifica
     * @return the list
     */
    List<StatoNotificaDTO> loadStatoNotificaByCode(String codStatoNotifica);

}