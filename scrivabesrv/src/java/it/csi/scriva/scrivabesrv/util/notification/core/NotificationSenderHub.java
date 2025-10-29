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
import it.csi.scriva.scrivabesrv.util.notification.model.enumeration.ChannelTypeEnum;

import java.util.List;

/**
 * The interface NotificationSenderAdapter hub.
 *
 * @author CSI PIEMONTE
 */
public interface NotificationSenderHub {

    /**
     * Notify long.
     *
     * @param channelType the channel type
     * @param messageList the message list
     * @return the long
     */
    long notify(ChannelTypeEnum channelType, List<Message> messageList) throws Exception;

    /**
     * Notify by notifica long.
     *
     * @param channelType  the channel type
     * @param notificaList the notifica list
     * @return the long
     */
    long notifyByNotificaList(ChannelTypeEnum channelType, List<NotificaExtendedDTO> notificaList) throws Exception;

    /**
     * Notify long.
     *
     * @param channelType the channel type
     * @param message     the message
     * @return the long
     */
    long notify(ChannelTypeEnum channelType, Message message) throws Exception;

    /**
     * Notify by notifica long.
     *
     * @param channelType         the channel type
     * @param NotificaExtendedDTO the notifica extended dto
     * @return the long
     */
    long notifyByNotifica(ChannelTypeEnum channelType, NotificaExtendedDTO NotificaExtendedDTO) throws Exception;

}