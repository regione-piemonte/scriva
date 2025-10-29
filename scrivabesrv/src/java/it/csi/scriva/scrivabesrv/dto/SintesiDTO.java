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

import java.io.Serializable;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * The type Pub istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SintesiDTO extends PubIstanzaSintesiDTO implements Serializable {

	private static final long serialVersionUID = 1L;

    @JsonProperty("contatore_conclusa")
    private Long contatoreConclusa;

    @JsonProperty("contatore_in_corso")
    private Long contatoreInCorso;

    @JsonProperty("contatore_in_consultazione")
    private Long contatoreInConsultazione;

    @JsonProperty("contatore_all")
    private Long contatoreAll;
    
    
	public Long getContatoreConclusa() {
		return contatoreConclusa;
	}

	public void setContatoreConclusa(Long contatoreConclusa) {
		this.contatoreConclusa = contatoreConclusa;
	}

	public Long getContatoreInCorso() {
		return contatoreInCorso;
	}

	public void setContatoreInCorso(Long contatoreInCorso) {
		this.contatoreInCorso = contatoreInCorso;
	}

	public Long getContatoreInConsultazione() {
		return contatoreInConsultazione;
	}

	public void setContatoreInConsultazione(Long contatoreInConsultazione) {
		this.contatoreInConsultazione = contatoreInConsultazione;
	}

	public Long getContatoreAll() {
		return contatoreAll;
	}

	public void setContatoreAll(Long contatoreAll) {
		this.contatoreAll = contatoreAll;
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
        JSONObject obj = new JSONObject(sJson);
        obj.remove("json_data");
        return obj;
    }
}