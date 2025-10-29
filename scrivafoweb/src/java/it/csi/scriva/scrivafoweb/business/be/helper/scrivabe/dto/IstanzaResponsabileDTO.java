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


import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;	

/**
 * The type Istanza Responsabile dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IstanzaResponsabileDTO extends BaseDTO implements Serializable {

	@JsonProperty("id_istanza_responsabile")
    private Long idIstanzaResponsabile;
	
	@JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_tipo_responsabile")
    private Long idTipoResponsabile;
    
    @JsonProperty("label_responsabile")
    private String labelResponsabile;
    
    @JsonProperty("nominativo_responsabile")
    private String nominativoResponsabile;
    
    @JsonProperty("recapito_responsabile")
    private String recapitoResponsabile;
    
    @JsonProperty("flg_riservato")
    private Boolean flgRiservato;
    
	/**
	 * Gets id istanza responsabile
	 * 
	 * @return idIstanzaResponsabile
	 */
	public Long getIdIstanzaResponsabile() {
		return idIstanzaResponsabile;
	}

	/**
	 * Sets id istanza responsabile
	 * 
	 * @param idIstanzaResponsabile
	 */
	public void setIdIstanzaResponsabile(Long idIstanzaResponsabile) {
		this.idIstanzaResponsabile = idIstanzaResponsabile;
	}

	/**
	 * Gets id istanza
	 * 
	 * @return idIstanza
	 */
	public Long getIdIstanza() {
		return idIstanza;
	}

	/**
	 * Sets id istanza
	 * 
	 * @param idIstanza
	 */
	public void setIdIstanza(Long idIstanza) {
		this.idIstanza = idIstanza;
	}

	/**
	 * Gets id tipo responsabile
	 * 
	 * @return idTipoResponsabile
	 */
	public Long getIdTipoResponsabile() {
		return idTipoResponsabile;
	}

	/**
	 * Sets id tipo responsabile
	 * 
	 * @param idTipoResponsabile
	 */
	public void setIdTipoResponsabile(Long idTipoResponsabile) {
		this.idTipoResponsabile = idTipoResponsabile;
	}

//	/**
//	 * Gets descrizione tipo responsabile
//	 * 
//	 * @return descrizioneTipoResponsabile
//	 */
//	public String getDescrizioneTipoResponsabile() {
//		return descrizioneTipoResponsabile;
//	}
//
//	/**
//	 * Sets descrizione tipo responsabile
//	 * 
//	 * @param descrizioneTipoResponsabile
//	 */
//	public void setDescrizioneTipoResponsabile(String descrizioneTipoResponsabile) {
//		this.descrizioneTipoResponsabile = descrizioneTipoResponsabile;
//	}

	/**
	 * Gets label responsabile
	 * 
	 * @return
	 */
	public String getLabelResponsabile() {
		return labelResponsabile;
	}

	/**
	 * Sets label responsabile
	 * 
	 * @param labelResponsabile
	 */
	public void setLabelResponsabile(String labelResponsabile) {
		this.labelResponsabile = labelResponsabile;
	}

	/**
	 * Gets nominativo responsabile
	 * 
	 * @return nominativoResponsabile
	 */
	public String getNominativoResponsabile() {
		return nominativoResponsabile;
	}

	/**
	 * Sets nominativo responsabile
	 * 
	 * @param nominativoResponsabile
	 */
	public void setNominativoResponsabile(String nominativoResponsabile) {
		this.nominativoResponsabile = nominativoResponsabile;
	}

	/**
	 * Gets recapito responsabile
	 * 
	 * @return recapitoResponsabile
	 */
	public String getRecapitoResponsabile() {
		return recapitoResponsabile;
	}

	/**
	 * Sets recapito responsabile
	 * 
	 * @param recapitoResponsabile
	 */
	public void setRecapitoResponsabile(String recapitoResponsabile) {
		this.recapitoResponsabile = recapitoResponsabile;
	}

	/**
	 * Gets flg riservato
	 * 
	 * @return flgRiservato
	 */
	public Boolean getFlgRiservato() {
		return flgRiservato;
	}

	/**
	 * Sets flg riservato
	 * 
	 * @param flgRiservato
	 */
	public void setFlgRiservato(Boolean flgRiservato) {
		this.flgRiservato = flgRiservato;
	}
	
    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(flgRiservato, recapitoResponsabile, nominativoResponsabile, labelResponsabile, /*descrizioneTipoResponsabile,*/ idTipoResponsabile, idIstanza, idIstanzaResponsabile);
    }

    /**
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        IstanzaResponsabileDTO other = (IstanzaResponsabileDTO) obj;
        return Objects.equals(flgRiservato, other.flgRiservato)
                && Objects.equals(recapitoResponsabile, other.recapitoResponsabile)
                && Objects.equals(nominativoResponsabile, other.nominativoResponsabile)
                && Objects.equals(labelResponsabile, other.labelResponsabile)
                //&& Objects.equals(descrizioneTipoResponsabile, other.descrizioneTipoResponsabile)
                && Objects.equals(idTipoResponsabile, other.idTipoResponsabile)
                && Objects.equals(idIstanza, other.idIstanza)
                && Objects.equals(idIstanzaResponsabile, other.idIstanzaResponsabile);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CompetenzaTerritorioResponsabileDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         idIstanza:").append(idIstanza);
        sb.append(",         idIstanzaResponsabile:").append(idIstanzaResponsabile);
        sb.append(",         idTipoResponsabile:'").append(idTipoResponsabile).append("'");
        sb.append(",         labelResponsabile:'").append(labelResponsabile).append("'");
        sb.append(",         nominativoResponsabile:'").append(nominativoResponsabile).append("'");
        sb.append(",         recapitoResponsabile:'").append(recapitoResponsabile).append("'");
        sb.append(",         flgRiservato:'").append(flgRiservato).append("'");
        sb.append("}");
        return sb.toString();
    }
  
}