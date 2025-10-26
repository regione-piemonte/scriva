/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * The type Procedimento statale dto.
 *
 * @author CSI PIEMONTE
 */
public class ProcedimentoStataleDTO implements Serializable {

    @JsonProperty("data_pubblicazione_esterna")
    private Timestamp dataPubblicazioneEsterna;

    @JsonProperty("data_consegna_doc_esterna")
    private Timestamp dataConsegnaDocEsterna;

    @JsonProperty("url_portale_esterno")
    private String urlPortaleEsterno;

    /**
     * Gets data pubblicazione esterna.
     *
     * @return the data pubblicazione esterna
     */
    public Timestamp getDataPubblicazioneEsterna() {
        return dataPubblicazioneEsterna;
    }

    /**
     * Sets data pubblicazione esterna.
     *
     * @param dataPubblicazioneEsterna the data pubblicazione esterna
     */
    public void setDataPubblicazioneEsterna(Timestamp dataPubblicazioneEsterna) {
        this.dataPubblicazioneEsterna = dataPubblicazioneEsterna;
    }

    /**
     * Gets data consegna doc esterna.
     *
     * @return the data consegna doc esterna
     */
    public Timestamp getDataConsegnaDocEsterna() {
        return dataConsegnaDocEsterna;
    }

    /**
     * Sets data consegna doc esterna.
     *
     * @param dataConsegnaDocEsterna the data consegna doc esterna
     */
    public void setDataConsegnaDocEsterna(Timestamp dataConsegnaDocEsterna) {
        this.dataConsegnaDocEsterna = dataConsegnaDocEsterna;
    }

    /**
     * Gets url portale esterno.
     *
     * @return the url portale esterno
     */
    public String getUrlPortaleEsterno() {
        return urlPortaleEsterno;
    }

    /**
     * Sets url portale esterno.
     *
     * @param urlPortaleEsterno the url portale esterno
     */
    public void setUrlPortaleEsterno(String urlPortaleEsterno) {
        this.urlPortaleEsterno = urlPortaleEsterno;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcedimentoStataleDTO that = (ProcedimentoStataleDTO) o;
        return Objects.equals(dataPubblicazioneEsterna, that.dataPubblicazioneEsterna) && Objects.equals(dataConsegnaDocEsterna, that.dataConsegnaDocEsterna) && Objects.equals(urlPortaleEsterno, that.urlPortaleEsterno);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(dataPubblicazioneEsterna, dataConsegnaDocEsterna, urlPortaleEsterno);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "ProcedimentoStataleDTO {\n" +
                "         dataPubblicazioneEsterna:" + dataPubblicazioneEsterna +
                ",\n         dataConsegnaDocEsterna:" + dataConsegnaDocEsterna +
                ",\n         urlPortaleEsterno:'" + urlPortaleEsterno + "'" +
                "}\n";
    }

}