/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * The type Allegato integrazione dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllegatoIntegrazioneDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_integra_istanza_allegato")
    private Long idIntegraIstanzaAllegato;

    @JsonProperty("id_integrazione_istanza")
    private Long idIntegrazioneIstanza;

    @JsonProperty("id_allegato_istanza")
    private Long idAllegatoIstanza;

    @JsonProperty("flg_allegato_rif_protocollo")
    private Boolean flgAllegatoRifProtocollo;

    @JsonProperty("nome_allegato")
    private String nomeAllegato;

    @JsonProperty("cod_allegato")
    private String codAllegato;

    @JsonProperty("uuid_index")
    private String uuidIndex;

    @JsonIgnore
    private Date dataProtocolloAllegato;

    @JsonIgnore
    private String numProtocolloAllegato;

    /**
     * Gets id integra istanza allegato.
     *
     * @return the id integra istanza allegato
     */
    public Long getIdIntegraIstanzaAllegato() {
        return idIntegraIstanzaAllegato;
    }

    /**
     * Sets id integra istanza allegato.
     *
     * @param idIntegraIstanzaAllegato the id integra istanza allegato
     */
    public void setIdIntegraIstanzaAllegato(Long idIntegraIstanzaAllegato) {
        this.idIntegraIstanzaAllegato = idIntegraIstanzaAllegato;
    }

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
     * Gets id allegato istanza.
     *
     * @return the id allegato istanza
     */
    public Long getIdAllegatoIstanza() {
        return idAllegatoIstanza;
    }

    /**
     * Sets id allegato istanza.
     *
     * @param idAllegatoIstanza the id allegato istanza
     */
    public void setIdAllegatoIstanza(Long idAllegatoIstanza) {
        this.idAllegatoIstanza = idAllegatoIstanza;
    }

    /**
     * Gets flg allegato rif protocollo.
     *
     * @return the flg allegato rif protocollo
     */
    public Boolean getFlgAllegatoRifProtocollo() {
        return flgAllegatoRifProtocollo;
    }

    /**
     * Sets flg allegato rif protocollo.
     *
     * @param flgAllegatoRifProtocollo the flg allegato rif protocollo
     */
    public void setFlgAllegatoRifProtocollo(Boolean flgAllegatoRifProtocollo) {
        this.flgAllegatoRifProtocollo = flgAllegatoRifProtocollo;
    }

    /**
     * Gets nome allegato.
     *
     * @return the nome allegato
     */
    public String getNomeAllegato() {
        return nomeAllegato;
    }

    /**
     * Sets nome allegato.
     *
     * @param nomeAllegato the nome allegato
     */
    public void setNomeAllegato(String nomeAllegato) {
        this.nomeAllegato = nomeAllegato;
    }

    /**
     * Gets cod allegato.
     *
     * @return the cod allegato
     */
    public String getCodAllegato() {
        return codAllegato;
    }

    /**
     * Sets cod allegato.
     *
     * @param codAllegato the cod allegato
     */
    public void setCodAllegato(String codAllegato) {
        this.codAllegato = codAllegato;
    }

    /**
     * Gets uuid index.
     *
     * @return the uuid index
     */
    public String getUuidIndex() {
        return uuidIndex;
    }

    /**
     * Sets uuid index.
     *
     * @param uuidIndex the uuid index
     */
    public void setUuidIndex(String uuidIndex) {
        this.uuidIndex = uuidIndex;
    }

    /**
     * Gets data protocollo allegato.
     *
     * @return the data protocollo allegato
     */
    public Date getDataProtocolloAllegato() {
        return dataProtocolloAllegato;
    }

    /**
     * Sets data protocollo allegato.
     *
     * @param dataProtocolloAllegato the data protocollo allegato
     */
    public void setDataProtocolloAllegato(Date dataProtocolloAllegato) {
        this.dataProtocolloAllegato = dataProtocolloAllegato;
    }

    /**
     * Gets num protocollo allegato.
     *
     * @return the num protocollo allegato
     */
    public String getNumProtocolloAllegato() {
        return numProtocolloAllegato;
    }

    /**
     * Sets num protocollo allegato.
     *
     * @param numProtocolloAllegato the num protocollo allegato
     */
    public void setNumProtocolloAllegato(String numProtocolloAllegato) {
        this.numProtocolloAllegato = numProtocolloAllegato;
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
        AllegatoIntegrazioneDTO that = (AllegatoIntegrazioneDTO) o;
        return Objects.equals(idIntegraIstanzaAllegato, that.idIntegraIstanzaAllegato) && Objects.equals(idIntegrazioneIstanza, that.idIntegrazioneIstanza) && Objects.equals(idAllegatoIstanza, that.idAllegatoIstanza) && Objects.equals(flgAllegatoRifProtocollo, that.flgAllegatoRifProtocollo) && Objects.equals(nomeAllegato, that.nomeAllegato) && Objects.equals(codAllegato, that.codAllegato) && Objects.equals(uuidIndex, that.uuidIndex) && Objects.equals(dataProtocolloAllegato, that.dataProtocolloAllegato) && Objects.equals(numProtocolloAllegato, that.numProtocolloAllegato);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idIntegraIstanzaAllegato, idIntegrazioneIstanza, idAllegatoIstanza, flgAllegatoRifProtocollo, nomeAllegato, codAllegato, uuidIndex, dataProtocolloAllegato, numProtocolloAllegato);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "AllegatoIntegrazioneDTO {\n" +
                "         idIntegraIstanzaAllegato:" + idIntegraIstanzaAllegato +
                ",\n         idIntegrazioneIstanza:" + idIntegrazioneIstanza +
                ",\n         idAllegatoIstanza:" + idAllegatoIstanza +
                ",\n         flgAllegatoRifProtocollo:" + flgAllegatoRifProtocollo +
                ",\n         nomeAllegato:'" + nomeAllegato + "'" +
                ",\n         codAllegato:'" + codAllegato + "'" +
                ",\n         uuidIndex:'" + uuidIndex + "'" +
                ",\n         dataProtocolloAllegato:'" + dataProtocolloAllegato + "'" +
                ",\n         numProtocolloAllegato:'" + numProtocolloAllegato + "'" +
                ",\n         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                "}\n";
    }
}