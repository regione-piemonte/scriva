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

import it.csi.scriva.scrivabesrv.dto.TipoDestinatarioDTO;

import java.util.List;

/**
 * The interface Tipo destinatario dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoDestinatarioDAO {

    /**
     * Load tipi messaggio list.
     *
     * @return List<TipoNotificaDTO>      list
     */
    List<TipoDestinatarioDTO> loadTipiDestinatario();

    /**
     * Load tipo messaggio list.
     *
     * @param idTipoDestinatario the id tipo destinatario
     * @return List<TipoNotificaDTO>      list
     */
    List<TipoDestinatarioDTO> loadTipoDestinatario(Long idTipoDestinatario);

    /**
     * Load tipo messaggio by code list.
     *
     * @param codTipoDestinatario the cod tipo destinatario
     * @return List<TipoNotificaDTO>      list
     */
    List<TipoDestinatarioDTO> loadTipoDestinatarioByCode(String codTipoDestinatario);

}