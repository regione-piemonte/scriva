/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.notification.service;

import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.NotificaExtendedDTO;

import java.util.List;

/**
 * The interface Scheduling service.
 *
 * @author CSI PIEMONTE
 */
public interface SchedulingService {

    /**
     * Instantiates a new Insert notifiche.
     *
     * @param notifyall    the notifyall
     * @param notificaList the notifica list
     */
    void insertNotifiche(String notifyall, List<NotificaExtendedDTO> notificaList);

    /**
     * Instantiates a new Send notifiche.
     *
     * @param notifyall    the notifyall
     * @param notificaList the notifica list
     * @param attoreScriva the attore scriva
     */
    void sendNotifiche(String notifyall, List<NotificaExtendedDTO> notificaList, AttoreScriva attoreScriva);

    /**
     * Instantiates a new Send notifiche create.
     *
     * @param notifyAll the notify all
     */
    void sendNotificheCreate(String notifyAll);

    /**
     * Generate ricevuta.
     *
     * @param idIstanza     the id istanza
     * @param codTipoevento the cod tipoevento
     * @param attoreScriva  the attore scriva
     */
    void generateRicevuta(Long idIstanza, String codTipoevento, AttoreScriva attoreScriva);

}