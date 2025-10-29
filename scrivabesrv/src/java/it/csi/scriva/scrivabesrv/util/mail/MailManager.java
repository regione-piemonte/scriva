/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.mail;

import it.csi.scriva.scrivabesrv.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * The type Mail manager.
 *
 * @author CSI PIEMONTE
 */
public class MailManager {
    private static final Logger LOGGER = Logger.getLogger(Constants.LOGGER_NAME + ".util");

    private static final String EMAIL_PROTOCOL = "smtp";

    /**
     * Send mail.
     *
     * @param emailDTO emailDTO
     * @throws Exception Exception
     */
    public static void sendMailOLD(EmailDTO emailDTO) throws Exception {
        LOGGER.debug("[MailManager::sendMail] BEGIN");
        Message msg = null;
        try {
            if (emailDTO == null)
                throw new InvalidParameterException("emaildto is null ...");

            Properties props = new Properties();
            props.put("mail.smtp.host", emailDTO.getHost());
            props.put("mail.smtp.port", emailDTO.getPort());

            props.put("mail.smtp.ssl.enable", emailDTO.getEnableSSL());
            props.put("mail.smtp.starttls.enable", emailDTO.getEnableTLS());
            props.put("mail.smtp.auth", emailDTO.getIsAuth()); // enable authentication

            if (StringUtils.isNotBlank(emailDTO.getUserID())) {
                props.put("mail.smtp.user", emailDTO.getUserID());
            }
            if (StringUtils.isNotBlank(emailDTO.getPassword())) {
                props.put("mail.smtp.password", emailDTO.getPassword());
            }
            if (StringUtils.isNotBlank(emailDTO.getSocketFactoryClass())) {
                props.put("mail.smtp.socketFactory.class", emailDTO.getSocketFactoryClass());
            }
            if (StringUtils.isNotBlank(emailDTO.getSocketFactoryPort())) {
                props.put("mail.smtp.socketFactory.port", emailDTO.getSocketFactoryPort());
            }
            props.put("mail.smtp.socketFactory.fallback", emailDTO.getSocketFactoryFallback());

            props.put("mail.debug", emailDTO.getEnableDebug());
            /**/

            Session session = Session.getInstance(props);
            msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(emailDTO.getMittente()));

            // Message TO
            Address[] addressesTo = new Address[emailDTO.getDestinatari().size()];
            for (int i = 0; i < addressesTo.length; i++) {
                addressesTo[i] = new InternetAddress(emailDTO.getDestinatari().get(i));
            }

            // Message CC
            Address[] addressesToCC = new Address[emailDTO.getDestinatariCC().size()];
            for (int i = 0; i < addressesToCC.length; i++) {
                addressesToCC[i] = new InternetAddress(emailDTO.getDestinatariCC().get(i));
            }

            msg.setRecipients(Message.RecipientType.TO, addressesTo);
            msg.setRecipients(Message.RecipientType.CC, addressesToCC);

            // Subject
            msg.setSubject(emailDTO.getOggetto());

            // Message body
            msg.setText(emailDTO.getCorpo());

            Transport transport = session.getTransport(EMAIL_PROTOCOL);
            if (StringUtils.isNotBlank(emailDTO.getUserID())) {
                transport.connect(emailDTO.getHost(), emailDTO.getUserID(), emailDTO.getPassword());
            } else {
                transport.connect();
            }
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
//            Transport.send(msg);
        } catch (Exception e) {
            LOGGER.error("[MailManager::sendMail] ERROR ", e);
            throw e;
        } finally {
            LOGGER.debug("[MailManager::sendMail] END");
        }
    }
    
    public static void sendMail(EmailDTO emailDTO) throws Exception {
        LOGGER.debug("[MailManager::sendMail] BEGIN");
        Message msg = null;
        try {
            if (emailDTO == null)
                throw new InvalidParameterException("emaildto is null ...");

            Properties props = new Properties();
            props.put("mail.smtp.host", emailDTO.getHost());
            props.put("mail.smtp.port", emailDTO.getPort());

            props.put("mail.smtp.ssl.enable", emailDTO.getEnableSSL());
            props.put("mail.smtp.starttls.enable", emailDTO.getEnableTLS());
            props.put("mail.smtp.auth", emailDTO.getIsAuth()); // enable authentication

            if (StringUtils.isNotBlank(emailDTO.getUserID())) {
                props.put("mail.smtp.user", emailDTO.getUserID());
            }
            if (StringUtils.isNotBlank(emailDTO.getPassword())) {
                props.put("mail.smtp.password", emailDTO.getPassword());
            }
            if (StringUtils.isNotBlank(emailDTO.getSocketFactoryClass())) {
                props.put("mail.smtp.socketFactory.class", emailDTO.getSocketFactoryClass());
            }
            if (StringUtils.isNotBlank(emailDTO.getSocketFactoryPort())) {
                props.put("mail.smtp.socketFactory.port", emailDTO.getSocketFactoryPort());
            }
            props.put("mail.smtp.socketFactory.fallback", emailDTO.getSocketFactoryFallback());

            props.put("mail.debug", emailDTO.getEnableDebug());
            /**/

            Session session = Session.getInstance(props);
            msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(emailDTO.getMittente()));

            // Message TO
            Address[] addressesTo = new Address[emailDTO.getDestinatari().size()];
            for (int i = 0; i < addressesTo.length; i++) {
                addressesTo[i] = new InternetAddress(emailDTO.getDestinatari().get(i));
            }

            // Message CC
            Address[] addressesToCC = new Address[emailDTO.getDestinatariCC().size()];
            for (int i = 0; i < addressesToCC.length; i++) {
                addressesToCC[i] = new InternetAddress(emailDTO.getDestinatariCC().get(i));
            }

            msg.setRecipients(Message.RecipientType.TO, addressesTo);
            msg.setRecipients(Message.RecipientType.CC, addressesToCC);

            // Subject
            msg.setSubject(emailDTO.getOggetto());

            // Message body
            msg.setText(emailDTO.getCorpo());

            Transport transport = session.getTransport(EMAIL_PROTOCOL);
            if (StringUtils.isNotBlank(emailDTO.getUserID())) {
                transport.connect(emailDTO.getHost(), emailDTO.getUserID(), emailDTO.getPassword());
            } else {
                transport.connect();
            }
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
//            Transport.send(msg);
        } catch (Exception e) {
            LOGGER.error("[MailManager::sendMail] ERROR ", e);
            throw e;
        } finally {
            LOGGER.debug("[MailManager::sendMail] END");
        }
    }

}