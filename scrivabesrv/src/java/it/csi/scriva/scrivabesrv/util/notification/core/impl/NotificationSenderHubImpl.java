/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.notification.core.impl;

import it.csi.scriva.scrivabesrv.business.be.service.impl.BaseServiceImpl;
import it.csi.scriva.scrivabesrv.dto.NotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.util.notification.core.NotificationSenderEmailAdapter;
import it.csi.scriva.scrivabesrv.util.notification.core.NotificationSenderHub;
import it.csi.scriva.scrivabesrv.util.notification.core.NotificationSenderPecAdapter;
import it.csi.scriva.scrivabesrv.util.notification.core.NotificationSenderWebAdapter;
import it.csi.scriva.scrivabesrv.util.notification.model.Message;
import it.csi.scriva.scrivabesrv.util.notification.model.enumeration.ChannelTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface NotificationSenderAdapter hub.
 *
 * @author CSI PIEMONTE
 */
@Component
public class NotificationSenderHubImpl extends BaseServiceImpl implements NotificationSenderHub {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    NotificationSenderEmailAdapter notificationSenderEmailAdapter;

    @Autowired
    NotificationSenderWebAdapter notificationSenderWebAdapter;

    @Autowired
    NotificationSenderPecAdapter notificationSenderPecAdapter;

    @Autowired
    NotificationSenderAppIOAdapterImpl notificationSenderAppIOAdapter;


    /**
     * Notify long.
     *
     * @param channelType the channel type
     * @param messageList the message list
     * @return the long
     */
    @Override
    public long notify(ChannelTypeEnum channelType, List<Message> messageList) throws Exception {
        logBegin(className);
        switch (channelType) {
            case EMAIL:
                return notificationSenderEmailAdapter.notify(messageList);
            case WEB:
            case SCRIVA_FO:
            case SCRIVA_BO:
                return notificationSenderWebAdapter.notify(messageList);
            case PEC:
                return notificationSenderPecAdapter.notify(messageList);
            default:
                break;
        }
        logEnd(className);
        return 0;
    }

    /**
     * Notify by notifica long.
     *
     * @param channelType  the channel type
     * @param notificaList the notifica list
     * @return the long
     */
    @Override
    public long notifyByNotificaList(ChannelTypeEnum channelType, List<NotificaExtendedDTO> notificaList) throws Exception {
        logBegin(className);
        switch (channelType) {
            case EMAIL:
                return notificationSenderEmailAdapter.notifyByNotificaList(notificaList);
            case WEB:
            case SCRIVA_FO:
            case SCRIVA_BO:
                return notificationSenderWebAdapter.notifyByNotificaList(notificaList);
            case PEC:
                return notificationSenderPecAdapter.notifyByNotificaList(notificaList);
            default:
                break;
        }
        logEnd(className);
        return 0;
    }

    /**
     * Notify long.
     *
     * @param channelType the channel type
     * @param message     the message
     * @return the long
     */
    @Override
    public long notify(ChannelTypeEnum channelType, Message message) throws Exception {
        logBegin(className);
        switch (channelType) {
            case EMAIL:
                return notificationSenderEmailAdapter.notify(message);
            case WEB:
                return notificationSenderWebAdapter.notify(message);
            case PEC:
                return notificationSenderPecAdapter.notify(message);
            default:
                break;
        }
        logEnd(className);
        return 0;
    }

    /**
     * Notify by notifica long.
     *
     * @param channelType the channel type
     * @param notifica    the notifica extended dto
     * @return the long
     */
    @Override
    public long notifyByNotifica(ChannelTypeEnum channelType, NotificaExtendedDTO notifica) throws Exception {
        logBegin(className);
        switch (channelType) {
            case EMAIL:
                return notificationSenderEmailAdapter.notifyByNotifica(notifica);
            case SCRIVA_FO:
            case SCRIVA_BO:
            case WEB:
                return notificationSenderWebAdapter.notifyByNotifica(notifica);
            case PEC:
                return notificationSenderPecAdapter.notifyByNotifica(notifica);
            case APP_IO:
                return notificationSenderAppIOAdapter.notifyByNotifica(notifica);    
            default:
                break;
        }
        logEnd(className);
        return 1;
    }
}