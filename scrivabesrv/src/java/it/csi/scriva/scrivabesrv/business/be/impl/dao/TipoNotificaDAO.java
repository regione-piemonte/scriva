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

import it.csi.scriva.scrivabesrv.dto.TipoNotificaDTO;

import java.util.List;

/**
 * The interface Tipo notifica dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoNotificaDAO {

    /**
     * Load tipi messaggio list.
     *
     * @return List<TipoNotificaDTO>     list
     */
    List<TipoNotificaDTO> loadTipiNotifica();

    /**
     * Load tipo messaggio list.
     *
     * @param idTipoNotifica the id tipo notifica
     * @return List<TipoNotificaDTO>     list
     */
    List<TipoNotificaDTO> loadTipoNotifica(Long idTipoNotifica);

    /**
     * Load tipo messaggio by code list.
     *
     * @param codTipoNotifica the cod tipo notifica
     * @return List<TipoNotificaDTO>     list
     */
    List<TipoNotificaDTO> loadTipoNotificaByCode(String codTipoNotifica);

}