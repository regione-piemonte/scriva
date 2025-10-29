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

import it.csi.scriva.scrivabesrv.dto.NotificaAllegatoDTO;

import java.util.List;

/**
 * The interface Notifica Allegato dao.
 *
 * @author CSI PIEMONTE
 */
public interface NotificaAllegatoDAO {

    /**
     * Load notifica allegato list.
     *
     * @return the list
     */
    List<NotificaAllegatoDTO> loadNotificaAllegato();

    /**
     * Load notifica allegato list.
     *
     * @param idNotificaAllegato the id notifica allegato
     * @param idNotifica         the id notifica
     * @param idAllegatoIstanza  the id allegato istanza
     * @param idIstanza          the id istanza
     * @param codAllegato        the cod allegato
     * @param gestUidRichiesta   the gest uid richiesta
     * @param desTipoRichiesta   the des tipo richiesta
     * @return the list
     */
    List<NotificaAllegatoDTO> loadNotificaAllegato(Long idNotificaAllegato,
                                                   Long idNotifica,
                                                   Long idAllegatoIstanza,
                                                   Long idIstanza,
                                                   String codAllegato,
                                                   String gestUidRichiesta,
                                                   String desTipoRichiesta);

    /**
     * Save notifica allegato long.
     *
     * @param notifica the notifica
     * @return the long
     */
    Long saveNotificaAllegato(NotificaAllegatoDTO notifica);

    /**
     * Update notifica allegato integer.
     *
     * @param notifica the notifica
     * @return the integer
     */
    Integer updateNotificaAllegato(NotificaAllegatoDTO notifica);

    /**
     * Delete notifica allegato integer.
     *
     * @param gestUid the gest uid
     * @return Integer integer
     */
    Integer deleteNotificaAllegato(String gestUid);

}