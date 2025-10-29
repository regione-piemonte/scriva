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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.NotificaApplicativaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.impl.BaseServiceImpl;
import it.csi.scriva.scrivabesrv.dto.NotificaApplicativaDTO;
import it.csi.scriva.scrivabesrv.dto.NotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.util.notification.core.NotificationSenderWebAdapter;
import it.csi.scriva.scrivabesrv.util.notification.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Notification web adapter.
 *
 * @author CSI PIEMONTE
 */
@Component
public class NotificationSenderWebAdapterImpl extends BaseServiceImpl implements NotificationSenderWebAdapter {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    NotificaApplicativaDAO notificaApplicativaDAO;

    /**
     * Notify long.
     *
     * @param messageList the message list
     * @return the long
     */
    @Override
    public long notify(List<Message> messageList) {
        return 0;
    }

    /**
     * Notify long.
     *
     * @param message the message
     * @return the long
     */
    @Override
    public long notify(Message message) {
        return 0;
    }

    /**
     * Notify long.
     *
     * @param notificaList the notifica list
     * @return the long
     */
    @Override
    public long notifyByNotificaList(List<NotificaExtendedDTO> notificaList) throws Exception {
        logBegin(className);
        try {
            long result = 0;
            for (NotificaExtendedDTO notifica : notificaList) {
                try {
                    result += this.notifyByNotifica(notifica);
                } catch (Exception e) {
                    logError(className, e);
                }
            }
            return result;
        } catch (Exception e) {
            logError(className, e);
            throw new Exception(e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Notify long.
     *
     * @param notifica the notifica
     * @return the long
     */
    @Override
    public long notifyByNotifica(NotificaExtendedDTO notifica) throws Exception {
        logBeginInfo(className, notifica);
        try {
            NotificaApplicativaDTO notificaApplicativa = notifica.getNotificaApplicativaDTO();
            return notificaApplicativaDAO.saveNotificaApplicativa(notificaApplicativa);
        } catch (Exception e) {
            logError(className, e);
            throw new Exception(e);
        } finally {
            logEnd(className);
        }
    }


}