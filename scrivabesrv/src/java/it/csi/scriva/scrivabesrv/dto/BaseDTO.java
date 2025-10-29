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
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * The type Base dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseDTO implements Serializable {

    /**
     * The Gest data ins.
     */
    @JsonIgnore
    protected Timestamp gestDataIns;

    /**
     * The Gest attore ins.
     */
    @JsonProperty(access = Access.WRITE_ONLY)
    protected String gestAttoreIns;

    /**
     * The Gest data upd.
     */
    @JsonIgnore
    protected Timestamp gestDataUpd;

    /**
     * The Gest attore upd.
     */
    @JsonProperty(access = Access.WRITE_ONLY)
    protected String gestAttoreUpd;

    /**
     * The Gest uid.
     */
    protected String gestUID;

    /**
     * Gets gest data ins.
     *
     * @return the gest data ins
     */
    public Timestamp getGestDataIns() {
        return gestDataIns;
    }

    /**
     * Sets gest data ins.
     *
     * @param gestDataIns the gest data ins
     */
    public void setGestDataIns(Timestamp gestDataIns) {
        this.gestDataIns = gestDataIns;
    }

    /**
     * Gets gest attore ins.
     *
     * @return the gest attore ins
     */
    public String getGestAttoreIns() {
        return gestAttoreIns;
    }

    /**
     * Sets gest attore ins.
     *
     * @param gestAttoreIns the gest attore ins
     */
    public void setGestAttoreIns(String gestAttoreIns) {
        this.gestAttoreIns = gestAttoreIns;
    }

    /**
     * Gets gest data upd.
     *
     * @return the gest data upd
     */
    public Timestamp getGestDataUpd() {
        return gestDataUpd;
    }

    /**
     * Sets gest data upd.
     *
     * @param gestDataUpd the gest data upd
     */
    public void setGestDataUpd(Timestamp gestDataUpd) {
        this.gestDataUpd = gestDataUpd;
    }

    /**
     * Gets gest attore upd.
     *
     * @return the gest attore upd
     */
    public String getGestAttoreUpd() {
        return gestAttoreUpd;
    }

    /**
     * Sets gest attore upd.
     *
     * @param gestAttoreUpd the gest attore upd
     */
    public void setGestAttoreUpd(String gestAttoreUpd) {
        this.gestAttoreUpd = gestAttoreUpd;
    }

    /**
     * Gets gest uid.
     *
     * @return the gest uid
     */
    public String getGestUID() {
        return gestUID;
    }

    /**
     * Sets gest uid.
     *
     * @param gestUID the gest uid
     */
    public void setGestUID(String gestUID) {
        this.gestUID = gestUID;
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
        BaseDTO baseDTO = (BaseDTO) o;
        return Objects.equals(gestDataIns, baseDTO.gestDataIns) && Objects.equals(gestAttoreIns, baseDTO.gestAttoreIns) && Objects.equals(gestDataUpd, baseDTO.gestDataUpd) && Objects.equals(gestAttoreUpd, baseDTO.gestAttoreUpd) && Objects.equals(gestUID, baseDTO.gestUID);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(gestDataIns, gestAttoreIns, gestDataUpd, gestAttoreUpd, gestUID);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append("}");
        return sb.toString();
    }

    /**
     * To json string string.
     *
     * @return string string
     */
    @JsonIgnore
    public String toJsonString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(df);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
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
     * @return JSONObject json object
     */
    @JsonIgnore
    public JSONObject toJsonObj() {
        String sJson = this.toJsonString();
        return new JSONObject(sJson);
    }
}