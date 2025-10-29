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
 * The type Istanza stato extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IstanzaStatoExtendedDTO extends BaseDTO implements Serializable {

    @JsonProperty("stato_istanza")
    private StatoIstanzaDTO statoIstanza;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("data_cambio_stato")
    private Date dataCambioStato;

    /**
     * Gets stato istanza.
     *
     * @return the stato istanza
     */
    public StatoIstanzaDTO getStatoIstanza() {
        return statoIstanza;
    }

    /**
     * Sets stato istanza.
     *
     * @param statoIstanza the stato istanza
     */
    public void setStatoIstanza(StatoIstanzaDTO statoIstanza) {
        this.statoIstanza = statoIstanza;
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
        IstanzaStatoExtendedDTO that = (IstanzaStatoExtendedDTO) o;
        return Objects.equals(statoIstanza, that.statoIstanza) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(dataCambioStato, that.dataCambioStato);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(statoIstanza, idIstanza, dataCambioStato);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IstanzaStatoDTO {");
        sb.append("         statoIstanza:").append(statoIstanza);
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

    /**
     * @return IstanzaStatoDTO
     */
    public IstanzaStatoDTO getDTO() {
        IstanzaStatoDTO dto = new IstanzaStatoDTO();
        dto.setIdIstanza(this.idIstanza);
        if (null != this.statoIstanza) {
            dto.setIdStatoIstanza(this.statoIstanza.getIdStatoIstanza());
        }
        dto.setDataCambioStato(this.dataCambioStato);
        dto.setGestAttoreIns(this.gestAttoreIns);
        dto.setGestDataIns(this.gestDataIns);
        dto.setGestAttoreUpd(this.gestAttoreUpd);
        dto.setGestDataUpd(this.gestDataUpd);
        dto.setGestUID(this.gestUID);

        return dto;
    }
}