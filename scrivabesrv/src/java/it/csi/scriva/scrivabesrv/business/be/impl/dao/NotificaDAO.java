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

import it.csi.scriva.scrivabesrv.dto.NotificaDTO;

import java.util.List;
import java.util.Map;

/**
 * The interface Notifica dao.
 *
 * @author CSI PIEMONTE
 */
public interface NotificaDAO {

    /**
     * Load notifica list.
     *
     * @return the list
     */
    List<NotificaDTO> loadNotifica();

    /**
     * Load notifica list.
     *
     * @param idNotifica       the id notifica
     * @param codStatoNotifica the cod stato notifica
     * @param idIstanza        the id istanza
     * @param codComponenteApp the cod componente app
     * @param cfDestinatario   the cf destinatario
     * @param rifCanale        the rif canale
     * @return the list
     */
    List<NotificaDTO> loadNotifica(Long idNotifica, String codStatoNotifica, Long idIstanza, String codComponenteApp, String cfDestinatario, String rifCanale);

    /**
     * Save notifica long.
     *
     * @param notifica the notifica
     * @return the long
     */
    Map.Entry<Long, String> saveNotifica(NotificaDTO notifica);

    /**
     * Update notifica integer.
     *
     * @param notifica the notifica
     * @return the integer
     */
    Integer updateNotifica(NotificaDTO notifica);

    /**
     * Delete notifica.
     *
     * @param gestUid the gest uid
     * @return Integer
     */
    Integer deleteNotifica(String gestUid);

}