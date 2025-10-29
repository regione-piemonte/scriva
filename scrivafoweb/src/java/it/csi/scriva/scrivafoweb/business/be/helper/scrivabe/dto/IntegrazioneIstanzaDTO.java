/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


/**
 * The type Integrazione istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IntegrazioneIstanzaDTO  extends BaseDTO implements Serializable {

    @JsonProperty("id_integrazione_istanza")
    private Long idIntegrazioneIstanza;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_tipo_integrazione")
    private Long idTipoIntegrazione;

    @JsonProperty("data_inserimento")
    private Date dataInserimento;

    @JsonProperty("data_invio")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataInvio;

    @JsonProperty("num_protocollo")
    private String numProtocollo;

    @JsonProperty("data_protocollo")
    private Date dataProtocollo;

    /**
     * Gets id integrazione istanza.
     *
     * @return the id integrazione istanza
     */
    public Long getIdIntegrazioneIstanza() {
        return idIntegrazioneIstanza;
    }

    /**
     * Sets id integrazione istanza.
     *
     * @param idIntegrazioneIstanza the id integrazione istanza
     */
    public void setIdIntegrazioneIstanza(Long idIntegrazioneIstanza) {
        this.idIntegrazioneIstanza = idIntegrazioneIstanza;
    }

    /**
     * Gets id istanza.
     *
     * @return the id istanza
     */
    public Long getIdIstanza() {
        return idIstanza;
    }

    /**
     * Sets id istanza.
     *
     * @param idIstanza the id istanza
     */
    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    /**
     * Gets id tipo integrazione.
     *
     * @return the id tipo integrazione
     */
    public Long getIdTipoIntegrazione() {
        return idTipoIntegrazione;
    }

    /**
     * Sets id tipo integrazione.
     *
     * @param idTipoIntegrazione the id tipo integrazione
     */
    public void setIdTipoIntegrazione(Long idTipoIntegrazione) {
        this.idTipoIntegrazione = idTipoIntegrazione;
    }

    /**
     * Gets data inserimento.
     *
     * @return the data inserimento
     */
    public Date getDataInserimento() {
        return dataInserimento;
    }

    /**
     * Sets data inserimento.
     *
     * @param dataInserimento the data inserimento
     */
    public void setDataInserimento(Date dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    /**
     * Gets data invio.
     *
     * @return the data invio
     */
    public Date getDataInvio() {
        return dataInvio;
    }

    /**
     * Sets data invio.
     *
     * @param dataInvio the data invio
     */
    public void setDataInvio(Date dataInvio) {
        this.dataInvio = dataInvio;
    }

    /**
     * Gets num protocollo.
     *
     * @return the num protocollo
     */
    public String getNumProtocollo() {
        return numProtocollo;
    }

    /**
     * Sets num protocollo.
     *
     * @param numProtocollo the num protocollo
     */
    public void setNumProtocollo(String numProtocollo) {
        this.numProtocollo = numProtocollo;
    }

    /**
     * Gets data protocollo.
     *
     * @return the data protocollo
     */
    public Date getDataProtocollo() {
        return dataProtocollo;
    }

    /**
     * Sets data protocollo.
     *
     * @param dataProtocollo the data protocollo
     */
    public void setDataProtocollo(Date dataProtocollo) {
        this.dataProtocollo = dataProtocollo;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IntegrazioneIstanzaDTO that = (IntegrazioneIstanzaDTO) o;
        return Objects.equals(idIntegrazioneIstanza, that.idIntegrazioneIstanza) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idTipoIntegrazione, that.idTipoIntegrazione) && Objects.equals(dataInserimento, that.dataInserimento) && Objects.equals(dataInvio, that.dataInvio) && Objects.equals(numProtocollo, that.numProtocollo) && Objects.equals(dataProtocollo, that.dataProtocollo);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idIntegrazioneIstanza, idIstanza, idTipoIntegrazione, dataInserimento, dataInvio, numProtocollo, dataProtocollo);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "IntegrazioneIstanzaDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idIntegrazioneIstanza:" + idIntegrazioneIstanza +
                ",\n         idIstanza:" + idIstanza +
                ",\n         idTipoIntegrazione:" + idTipoIntegrazione +
                ",\n         dataInserimento:" + dataInserimento +
                ",\n         dataInvio:" + dataInvio +
                ",\n         numProtocollo:'" + numProtocollo + "'" +
                ",\n         dataProtocollo:" + dataProtocollo +
                "}\n";
    }
}