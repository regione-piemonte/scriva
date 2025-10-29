/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.notification.core;

import it.csi.scriva.scrivabesrv.dto.NotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.util.notification.model.Message;

import java.util.List;

/**
 * The interface NotificationSenderAdapter.
 *
 * @author CSI PIEMONTE
 */
public interface NotificationSenderAdapter {

    /**
     * Notify long.
     *
     * @param messageList the message list
     * @return the long
     */
    long notify(List<Message> messageList) throws Exception;

    /**
     * Notify long.
     *
     * @param message the message
     * @return the long
     */
    long notify(Message message) throws Exception;

    /**
     * Notify long.
     *
     * @param notificaList the notifica list
     * @return the long
     */
    long notifyByNotificaList(List<NotificaExtendedDTO> notificaList) throws Exception;


    /**
     * Notify long.
     *
     * @param notifica the notifica
     * @return the long
     */
    long notifyByNotifica(NotificaExtendedDTO notifica) throws Exception;

}