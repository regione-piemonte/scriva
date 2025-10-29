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
import java.util.Date;
import java.util.Objects;

/**
 * The type Istanza stato dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IstanzaStatoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_stato_istanza")
    private Long idStatoIstanza;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("data_cambio_stato")
    private Date dataCambioStato;

    /**
     * Gets id stato istanza.
     *
     * @return the id stato istanza
     */
    public Long getIdStatoIstanza() {
        return idStatoIstanza;
    }

    /**
     * Sets id stato istanza.
     *
     * @param idStatoIstanza the id stato istanza
     */
    public void setIdStatoIstanza(Long idStatoIstanza) {
        this.idStatoIstanza = idStatoIstanza;
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
     * Gets data cambio stato.
     *
     * @return the data cambio stato
     */
    public Date getDataCambioStato() {
        return dataCambioStato;
    }

    /**
     * Sets data cambio stato.
     *
     * @param dataCambioStato the data cambio stato
     */
    public void setDataCambioStato(Date dataCambioStato) {
        this.dataCambioStato = dataCambioStato;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        IstanzaStatoDTO that = (IstanzaStatoDTO) o;
        return Objects.equals(idStatoIstanza, that.idStatoIstanza) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(dataCambioStato, that.dataCambioStato);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idStatoIstanza, idIstanza, dataCambioStato);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IstanzaStatoDTO {");
        sb.append("         idStatoIstanza:").append(idStatoIstanza);
        sb.append(",         idIstanza:").append(idIstanza);
        sb.append(",         dataCambioStato:").append(dataCambioStato);
        sb.append(",         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append("}");
        return sb.toString();
    }
}