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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * The type Istanza osservazione dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IstanzaOsservazioneDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_istanza_osservazione")
    private Long idIstanzaOsservazione;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_stato_osservazione")
    private Long idStatoOsservazione;

    @JsonProperty("id_funzionario")
    private Long idFunzionario;

    @JsonProperty("cf_osservazione_ins")
    private String cfOsservazioneIns;

    @JsonProperty("data_osservazione")
    private Timestamp dataOsservazione;

    @JsonProperty("data_pubblicazione")
    private Timestamp dataPubblicazione;

    @JsonProperty("num_protocollo_osservazione")
    private String numProtocolloOsservazione;

    @JsonProperty("data_protocollo_osservazione")
    private Timestamp dataProtocolloOsservazione;

    public Long getIdIstanzaOsservazione() {
        return idIstanzaOsservazione;
    }

    public void setIdIstanzaOsservazione(Long idIstanzaOsservazione) {
        this.idIstanzaOsservazione = idIstanzaOsservazione;
    }

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public Long getIdStatoOsservazione() {
        return idStatoOsservazione;
    }

    public void setIdStatoOsservazione(Long idStatoOsservazione) {
        this.idStatoOsservazione = idStatoOsservazione;
    }

    public Long getIdFunzionario() {
        return idFunzionario;
    }

    public void setIdFunzionario(Long idFunzionario) {
        this.idFunzionario = idFunzionario;
    }

    public String getCfOsservazioneIns() {
        return cfOsservazioneIns;
    }

    public void setCfOsservazioneIns(String cfOsservazioneIns) {
        this.cfOsservazioneIns = cfOsservazioneIns;
    }

    public Timestamp getDataOsservazione() {
        return dataOsservazione;
    }

    public void setDataOsservazione(Timestamp dataOsservazione) {
        this.dataOsservazione = dataOsservazione;
    }

    public Timestamp getDataPubblicazione() {
        return dataPubblicazione;
    }

    public void setDataPubblicazione(Timestamp dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    public String getNumProtocolloOsservazione() {
        return numProtocolloOsservazione;
    }

    public void setNumProtocolloOsservazione(String numProtocolloOsservazione) {
        this.numProtocolloOsservazione = numProtocolloOsservazione;
    }

    public Timestamp getDataProtocolloOsservazione() {
        return dataProtocolloOsservazione;
    }

    public void setDataProtocolloOsservazione(Timestamp dataProtocolloOsservazione) {
        this.dataProtocolloOsservazione = dataProtocolloOsservazione;
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
        IstanzaOsservazioneDTO that = (IstanzaOsservazioneDTO) o;
        return Objects.equals(idIstanzaOsservazione, that.idIstanzaOsservazione) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idStatoOsservazione, that.idStatoOsservazione) && Objects.equals(idFunzionario, that.idFunzionario) && Objects.equals(cfOsservazioneIns, that.cfOsservazioneIns) && Objects.equals(dataOsservazione, that.dataOsservazione) && Objects.equals(dataPubblicazione, that.dataPubblicazione) && Objects.equals(numProtocolloOsservazione, that.numProtocolloOsservazione) && Objects.equals(dataProtocolloOsservazione, that.dataProtocolloOsservazione);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idIstanzaOsservazione, idIstanza, idStatoOsservazione, idFunzionario, cfOsservazioneIns, dataOsservazione, dataPubblicazione, numProtocolloOsservazione, dataProtocolloOsservazione);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IstanzaOsservazioneDTO {\n");
        sb.append(super.toString()).append("\n");
        sb.append("         idIstanzaOsservazione:").append(idIstanzaOsservazione);
        sb.append(",\n         idIstanza:").append(idIstanza);
        sb.append(",\n         idStatoOsservazione:").append(idStatoOsservazione);
        sb.append(",\n         idFunzionario:").append(idFunzionario);
        sb.append(",\n         cfOsservazioneIns:'").append(cfOsservazioneIns).append("'");
        sb.append(",\n         dataOsservazione:").append(dataOsservazione);
        sb.append(",\n         dataPubblicazione:").append(dataPubblicazione);
        sb.append(",\n         numProtocolloOsservazione:'").append(numProtocolloOsservazione).append("'");
        sb.append(",\n         dataProtocolloOsservazione:").append(dataProtocolloOsservazione);
        sb.append("}\n");
        return sb.toString();
    }

}