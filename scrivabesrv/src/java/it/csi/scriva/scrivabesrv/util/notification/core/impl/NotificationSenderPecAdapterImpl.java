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

import it.csi.scriva.scrivabesrv.business.SpringApplicationContextHelper;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.service.impl.BaseServiceImpl;
import it.csi.scriva.scrivabesrv.dto.NotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.mail.InvalidParameterException;
import it.csi.scriva.scrivabesrv.util.notification.core.NotificationSenderPecAdapter;
import it.csi.scriva.scrivabesrv.util.notification.model.AttachmentData;
import it.csi.scriva.scrivabesrv.util.notification.model.Message;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.ByteArrayDataSource;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * The type Notification pec adapter.
 *
 * @author CSI PIEMONTE
 */
@Component
public class NotificationSenderPecAdapterImpl extends BaseServiceImpl implements NotificationSenderPecAdapter {

    private final String className = this.getClass().getSimpleName();

    private static final List<String> CONF_KEYS_SERVER_POSTA = Arrays.asList(Constants.CONF_KEY_SERVER_PEC_HOST,
            Constants.CONF_KEY_SERVER_PEC_PORTA,
            Constants.CONF_KEY_SERVER_PEC_MITTENTE,
            Constants.CONF_KEY_SERVER_PEC_USERNAME,
            Constants.CONF_KEY_SERVER_PEC_PASSWORD);

    private Map<String, String> configs;
    private Properties props;

    ConfigurazioneDAO configurazioneDAO;

    @Autowired
    public NotificationSenderPecAdapterImpl() {
        super();
        configurazioneDAO = (ConfigurazioneDAO) SpringApplicationContextHelper.getBean("configurazioneDAO");
        getConfServerPosta();
        //getServerProps();
    }


    /**
     * Notify long.
     *
     * @param messageList the message list
     * @return the long
     */
    @Override
    public long notify(List<Message> messageList) throws Exception {
        logBegin(className);
        try {
            long result = 0;
            for (Message message : messageList) {
                try {
                    result += this.notify(message);
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
     * @param message the message
     * @return the long
     */
    @Override
    public long notify(Message message) throws Exception {
        logBeginInfo(className, message);
        try {
            if (message == null) {
                throw new InvalidParameterException("Message is null ...");
            }

            getConfServerPosta();
            MultiPartEmail email = new MultiPartEmail();

            email.setDebug(Boolean.TRUE);
            email.setSSL(Boolean.TRUE);
            email.setHostName(configs.get(Constants.CONF_KEY_SERVER_PEC_HOST));
            email.setSmtpPort(Integer.parseInt(configs.get(Constants.CONF_KEY_SERVER_PEC_PORTA)));
            email.setAuthentication(configs.get(Constants.CONF_KEY_SERVER_PEC_USERNAME), configs.get(Constants.CONF_KEY_SERVER_PEC_PASSWORD));
            email.setFrom(configs.get(Constants.CONF_KEY_SERVER_PEC_MITTENTE));
            email.setMsg(message.getBody());
            email.setSubject(message.getSubject());

            if (CollectionUtils.isNotEmpty(message.getTo())) {
                for (String toAddress : message.getTo()) {
                    email.addTo(toAddress);
                }
            }
            if (CollectionUtils.isNotEmpty(message.getCc())) {
                for (String ccAddress : message.getCc()) {
                    email.addCc(ccAddress);
                }
            }
            if (CollectionUtils.isNotEmpty(message.getAttachmentDataList())) {
                for (AttachmentData attachment : message.getAttachmentDataList()) {
                    DataSource source = new ByteArrayDataSource(attachment.getFileAsByteArray(), attachment.getMimeType());
                    email.attach(source, attachment.getFilename(), StringUtils.EMPTY);
                }
            }

            email.send();

        } catch (Exception e) {
            logError(className, "Failed to send PEC :\n" + e);
            throw new Exception(e);
        } finally {
            logEnd(className);
        }
        return 1;
    }


    /**
     * Notify long.
     *
     * @param notificaList the notifica list
     * @return the long
     */
    @Override
    public long notifyByNotificaList(List<NotificaExtendedDTO> notificaList) {
        return 0;
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
            Message message = new Message(notifica, configs.get(Constants.CONF_KEY_SERVER_PEC_MITTENTE));
            return this.notify(message);
        } catch (Exception e) {
            logError(className, e);
            throw new Exception(e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets conf server posta.
     */
    private void getConfServerPosta() {
        logBegin(className);
        if (configs == null || configs.isEmpty()) {
            configs = configurazioneDAO.loadConfigByKeyList(CONF_KEYS_SERVER_POSTA);
        }
    }

    /**
     * Gets server props.
     */
    private void getServerProps() {
        logBegin(className);
        try {
            if (props == null) {
                props = new Properties();
                props.put("mail.smtp.host", configs.get(Constants.CONF_KEY_SERVER_PEC_HOST));
                props.put("mail.smtp.port", configs.get(Constants.CONF_KEY_SERVER_PEC_PORTA));
                props.put("mail.smtp.ssl.enable", Boolean.TRUE);
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.ssl.socketFactory.fallback", Boolean.FALSE);

                props.put("mail.smtp.ssl.checkserveridentity", Boolean.TRUE); //sonarqube suggestion

                //props.put("mail.smtp.starttls.enable", Boolean.FALSE);
                //props.put("mail.smtp.auth", Boolean.FALSE); // enable authentication

            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

}