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
import it.csi.scriva.scrivabesrv.util.notification.core.NotificationSenderEmailAdapter;
import it.csi.scriva.scrivabesrv.util.notification.model.AttachmentData;
import it.csi.scriva.scrivabesrv.util.notification.model.Message;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.ByteArrayDataSource;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * The type Notification email adapter.
 *
 * @author CSI PIEMONTE
 */
@Component
public class NotificationSenderEmailAdapterImpl extends BaseServiceImpl implements NotificationSenderEmailAdapter {

    private final String className = this.getClass().getSimpleName();

    private static final String EMAIL_PROTOCOL = "smtp";
    private static final String USER_ID = null;
    private static final String PASSWORD = null;
    private static final String SOCKET_FACTORY_CLASS = null;
    private static final String SOCKET_FACTORY_PORT = null;
    private static final String SOCKET_FACTORY_FALLBACK = null;
    private static final String ENABLE_DEBUG = null;

    private static final List<String> CONF_KEYS_SERVER_POSTA = Arrays.asList(Constants.CONF_KEY_SERVER_POSTA_HOST, Constants.CONF_KEY_SERVER_POSTA_PORTA, Constants.CONF_KEY_SERVER_POSTA_MITTENTE,Constants.CONF_KEY_SERVER_POSTA_USERNAME,  Constants.CONF_KEY_SERVER_POSTA_PASSWORD);

    private Map<String, String> configs;
    private Properties props;
    /**
     * The Configurazione dao.
     */
    @Autowired
    ConfigurazioneDAO configurazioneDAO;

    /**
     * Instantiates a new Notification sender email adapter.
     */
    public NotificationSenderEmailAdapterImpl() {
        super();
        configurazioneDAO = (ConfigurazioneDAO) SpringApplicationContextHelper.getBean("configurazioneDAO");
        getConfServerPosta();
        getServerProps();
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
            email.setHostName(configs.get(Constants.CONF_KEY_SERVER_POSTA_HOST));
            email.setSmtpPort(Integer.parseInt(configs.get(Constants.CONF_KEY_SERVER_POSTA_PORTA)));
            String user = configs.get(Constants.CONF_KEY_SERVER_POSTA_USERNAME);
            String password = configs.get(Constants.CONF_KEY_SERVER_POSTA_PASSWORD);
            email.setAuthentication(user,password );
            email.setFrom(configs.get(Constants.CONF_KEY_SERVER_POSTA_MITTENTE));
            email.setMsg(message.getBody());
            email.setSubject(message.getSubject());
            //email.addTo(String.join(";", message.getTo()), "");
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
            if(CollectionUtils.isNotEmpty(message.getAttachmentDataList())) {
                for (AttachmentData attachment : message.getAttachmentDataList()) {
                    DataSource source = new ByteArrayDataSource(attachment.getFileAsByteArray(), attachment.getMimeType());
                    email.attach(source, attachment.getFilename(), StringUtils.EMPTY);
                }
            }

            email.send();

        } catch (Exception e) {
            logError(className, "Failed to send email :\n" + e);
            throw new Exception(e);
        } finally {
            logEnd(className);
        }
        return 1;
    }

    private long notifyOLD(Message message) throws Exception {
        logBeginInfo(className, message);
        javax.mail.Message msg;
        try {
            if (message == null) {
                throw new InvalidParameterException("message is null ...");
            }

            Session session = Session.getInstance(props);
            msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(message.getFrom()));

            // Message TO
            Address[] addressesTo = new Address[message.getTo().size()];
            for (int i = 0; i < addressesTo.length; i++) {
                addressesTo[i] = new InternetAddress(message.getTo().get(i));
            }

            // Message CC
            if (CollectionUtils.isNotEmpty(message.getCc())) {

                Address[] addressesToCC = new Address[message.getCc().size()];

                for (int i = 0; i < addressesToCC.length; i++) {
                    addressesToCC[i] = new InternetAddress(message.getCc().get(i));
                }
                msg.setRecipients(javax.mail.Message.RecipientType.CC, addressesToCC);
            }


            msg.setRecipients(javax.mail.Message.RecipientType.TO, addressesTo);


            // Subject
            msg.setSubject(message.getSubject());

            // Message body
            msg.setText(message.getBody());
            getServerProps();
            Transport transport = session.getTransport(EMAIL_PROTOCOL);

            /*
            if (StringUtils.isNotBlank(props.getProperty(USER_ID)))
            {
                transport.connect(props.getProperty(Constants.CONF_KEY_SERVER_POSTA_HOST), props.getProperty(USER_ID), props.getProperty(PASSWORD));
            } else {
                transport.connect();
            }
            */
            transport.connect();
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            return 1;
        } catch (Exception e) {
            logError(className, message + "\n" + e);
            throw new Exception(e);
        } finally {
            logEnd(className);
        }
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
            Message message = new Message(notifica, configs.get(Constants.CONF_KEY_SERVER_POSTA_MITTENTE));
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
                props.put("mail.smtp.host", configs.get(Constants.CONF_KEY_SERVER_POSTA_HOST));
                props.put("mail.smtp.port", configs.get(Constants.CONF_KEY_SERVER_POSTA_PORTA));
                
                props.put("mail.smtp.ssl.enable", Boolean.TRUE);
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.ssl.socketFactory.fallback", Boolean.FALSE);
                
                //props.put("mail.smtp.ssl.enable", Boolean.TRUE);
                //props.put("mail.smtp.starttls.enable", Boolean.TRUE);
                //props.put("mail.smtp.auth", Boolean.TRUE); // enable authentication
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

}