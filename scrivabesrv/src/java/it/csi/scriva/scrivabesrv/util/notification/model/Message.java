/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.notification.model;

import it.csi.scriva.scrivabesrv.dto.NotificaAllegatoDTO;
import it.csi.scriva.scrivabesrv.dto.NotificaDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.notification.model.enumeration.ResultEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * The type Message.
 *
 * @author CSI PIEMONTE
 */
public class Message {

    private long id;
    private String from;
    private List<String> to;
    private List<String> cc;
    private String subject;
    private String body;
    private String sentTime;
    private ResultEnum result;
    private List<AttachmentData> attachmentDataList;

  
    /**
     * Constructs a new Message object with the given notification and sender information.
     *
     * @param notifica The notification DTO object containing the notification details.
     * @param from The sender of the message.
     */
    public Message(NotificaDTO notifica, String from) {
        this.id = notifica.getIdNotifica();
        // ...

            this.from = from;
            this.to = StringUtils.isNotBlank(notifica.getRifCanale()) ?
                Arrays.stream(notifica.getRifCanale().split(Constants.CSV_ALLEGATI_DELIMITER)).map(String::trim).collect(Collectors.toList()) :
                null;
            //converte RifCanaleCc da stringa  in lista di indirizzi email splittando per virgola o punto e virgola a seconda di come arriva
            this.cc = StringUtils.isNotBlank(notifica.getRifCanaleCc()) ?
                Arrays.stream(notifica.getRifCanaleCc().split(";|, ")).map(String::trim).collect(Collectors.toList()) :
                null;

  
            /*this.cc = StringUtils.isNotBlank(notifica.getRifCanaleCc()) ?
            Arrays.stream(notifica.getRifCanaleCc().contains(", ") ? notifica.getRifCanaleCc().split(", ") : notifica.getRifCanaleCc().split(";"))
                .map(String::trim)
                .collect(Collectors.toList()) :
            null;*/


            this.subject = notifica.getDesOggetto();
            this.body = notifica.getDesMessaggio();
            this.sentTime = String.valueOf(System.currentTimeMillis());
            this.result = ResultEnum.INVIATA;

        // gestione allegati
        if (CollectionUtils.isNotEmpty(notifica.getNotificaAllegatoList())){
            for (NotificaAllegatoDTO notificaAllegato : notifica.getNotificaAllegatoList()){
                AttachmentData attachmentData = new AttachmentData();
                attachmentData.setFilename(notificaAllegato.getNomeAllegato());
                attachmentData.setFile(notificaAllegato.getFileAllegato());
                if(this.getAttachmentDataList()==null){
                    this.attachmentDataList = new ArrayList<>();
                }
                this.attachmentDataList.add(attachmentData);
            }
        }
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets from.
     *
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets from.
     *
     * @param from the from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Gets to.
     *
     * @return the to
     */
    public List<String> getTo() {
        return to;
    }

    /**
     * Sets to.
     *
     * @param to the to
     */
    public void setTo(List<String> to) {
        this.to = to;
    }

    /**
     * Gets cc.
     *
     * @return the cc
     */
    public List<String> getCc() {
        return cc;
    }

    /**
     * Sets cc.
     *
     * @param cc the cc
     */
    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    /**
     * Gets subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets subject.
     *
     * @param subject the subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets body.
     *
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets body.
     *
     * @param body the body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets sent time.
     *
     * @return the sent time
     */
    public String getSentTime() {
        return sentTime;
    }

    /**
     * Sets sent time.
     *
     * @param sentTime the sent time
     */
    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }

    /**
     * Gets result.
     *
     * @return the result
     */
    public ResultEnum getResult() {
        return result;
    }

    /**
     * Sets result.
     *
     * @param result the result
     */
    public void setResult(ResultEnum result) {
        this.result = result;
    }

    /**
     * Gets attachment data list.
     *
     * @return the attachment data list
     */
    public List<AttachmentData> getAttachmentDataList() {
        return attachmentDataList;
    }

    /**
     * Sets attachment data list.
     *
     * @param attachmentDataList the attachment data list
     */
    public void setAttachmentDataList(List<AttachmentData> attachmentDataList) {
        this.attachmentDataList = attachmentDataList;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id && Objects.equals(from, message.from) && Objects.equals(to, message.to) && Objects.equals(cc, message.cc) && Objects.equals(subject, message.subject) && Objects.equals(body, message.body) && Objects.equals(sentTime, message.sentTime) && result == message.result;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, cc, subject, body, sentTime, result);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "Message {\n" +
                "         id:" + id +
                ",\n         from:'" + from + "'" +
                ",\n         to:'" + to + "'" +
                ",\n         cc:'" + cc + "'" +
                ",\n         subject:'" + subject + "'" +
                ",\n         body:'" + body + "'" +
                ",\n         sentTime:'" + sentTime + "'" +
                ",\n         result:" + result +
                "}\n";
    }


}