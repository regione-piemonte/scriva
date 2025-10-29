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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Ruolo soggetto dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RuoloSoggettoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_ruolo_soggetto")
    private Long idRuoloSoggetto;

    @JsonProperty("cod_ruolo_soggetto")
    private String codiceRuoloSoggetto;

    @JsonProperty("des_ruolo_soggetto")
    private String descrizioneRuoloSoggetto;

    /**
     * Gets id ruolo soggetto.
     *
     * @return the id ruolo soggetto
     */
    public Long getIdRuoloSoggetto() {
        return idRuoloSoggetto;
    }

    /**
     * Sets id ruolo soggetto.
     *
     * @param idRuoloSoggetto the id ruolo soggetto
     */
    public void setIdRuoloSoggetto(Long idRuoloSoggetto) {
        this.idRuoloSoggetto = idRuoloSoggetto;
    }

    /**
     * Gets codice ruolo soggetto.
     *
     * @return the codice ruolo soggetto
     */
    public String getCodiceRuoloSoggetto() {
        return codiceRuoloSoggetto;
    }

    /**
     * Sets codice ruolo soggetto.
     *
     * @param codiceRuoloSoggetto the codice ruolo soggetto
     */
    public void setCodiceRuoloSoggetto(String codiceRuoloSoggetto) {
        this.codiceRuoloSoggetto = codiceRuoloSoggetto;
    }

    /**
     * Gets descrizione ruolo soggetto.
     *
     * @return the descrizione ruolo soggetto
     */
    public String getDescrizioneRuoloSoggetto() {
        return descrizioneRuoloSoggetto;
    }

    /**
     * Sets descrizione ruolo soggetto.
     *
     * @param descrizioneRuoloSoggetto the descrizione ruolo soggetto
     */
    public void setDescrizioneRuoloSoggetto(String descrizioneRuoloSoggetto) {
        this.descrizioneRuoloSoggetto = descrizioneRuoloSoggetto;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codiceRuoloSoggetto, descrizioneRuoloSoggetto, idRuoloSoggetto);
    }

    /**
     * @param obj Object
     * @return booelan
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RuoloSoggettoDTO other = (RuoloSoggettoDTO) obj;
        return Objects.equals(codiceRuoloSoggetto, other.codiceRuoloSoggetto)
                && Objects.equals(descrizioneRuoloSoggetto, other.descrizioneRuoloSoggetto)
                && Objects.equals(idRuoloSoggetto, other.idRuoloSoggetto);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RuoloSoggettoDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         idRuoloSoggetto:").append(idRuoloSoggetto);
        sb.append(",         codiceRuoloSoggetto:'").append(codiceRuoloSoggetto).append("'");
        sb.append(",         descrizioneRuoloSoggetto:'").append(descrizioneRuoloSoggetto).append("'");
        sb.append("}");
        return sb.toString();
    }

}