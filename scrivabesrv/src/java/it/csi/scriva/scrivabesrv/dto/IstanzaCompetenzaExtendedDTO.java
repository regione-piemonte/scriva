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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * The type Istanza competenza extended dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IstanzaCompetenzaExtendedDTO extends IstanzaCompetenzaDTO implements Serializable {

    @JsonProperty("competenza_territorio")
    private CompetenzaTerritorioExtendedDTO competenzaTerritorio;

	/**
     * Gets competenza territorio.
     *
     * @return competenzaTerritorio competenza territorio
     */
    public CompetenzaTerritorioExtendedDTO getCompetenzaTerritorio() {
        return competenzaTerritorio;
    }

    /**
     * Sets competenza territorio.
     *
     * @param competenzaTerritorio competenzaTerritorio
     */
    public void setCompetenzaTerritorio(CompetenzaTerritorioExtendedDTO competenzaTerritorio) {
        this.competenzaTerritorio = competenzaTerritorio;
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
        IstanzaCompetenzaExtendedDTO that = (IstanzaCompetenzaExtendedDTO) o;
        return Objects.equals(competenzaTerritorio, that.competenzaTerritorio);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), competenzaTerritorio);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IstanzaCompetenzaExtendedDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         competenzaTerritorio:").append(competenzaTerritorio);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public IstanzaCompetenzaDTO getDTO() {
        IstanzaCompetenzaDTO dto = new IstanzaCompetenzaDTO();
        dto.setIdIstanza(this.getIdIstanza());
        if (null != this.getCompetenzaTerritorio()) {
            dto.setIdCompetenzaTerritorio(this.getCompetenzaTerritorio().getIdCompetenzaTerritorio());
        }
        dto.setDataInizioValidita(this.getDataInizioValidita());
        dto.setDataFineValidita(this.getDataFineValidita());
        dto.setFlgAutoritaPrincipale(this.getFlgAutoritaPrincipale());
        dto.setFlgAutoritaAssegnataBO(this.getFlgAutoritaAssegnataBO());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestDataUpd(this.getGestDataUpd());
        dto.setGestUID(this.getGestUID());
        return dto;
    }

    /**
     * To json string string.
     *
     * @return the string
     */
    @JsonIgnore
    public String toJsonString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.setDateFormat(df);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * To json obj json object.
     *
     * @return the json object
     */
    @JsonIgnore
    public JSONObject toJsonObj() {
        String sJson = this.toJsonString();
        JSONObject obj = new JSONObject(sJson);
        obj.remove("id_competenza_territorio");
        obj.remove("id_istanza");
        obj.remove("gestUID");
        return obj;
    }

}