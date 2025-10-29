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


import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Competenza Territorio Responsabile dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CompetenzaTerritorioResponsabileDTO extends BaseDTO implements Serializable {

		@JsonProperty("id_competenza_responsabile")
	    private Long idCompetenzaResponsabile;
	
	    @JsonProperty("id_competenza_territorio")
	    private Long idCompetenzaTerritorio;

	    @JsonProperty("tipo_responsabile")
	    private TipoResponsabileDTO tipoResponsabile;

//	    @JsonProperty("des_tipo_responsabile")
//	    private String descrizioneTipoResponsabile;
	    
	    @JsonProperty("label_responsabile")
	    private String labelResponsabile;
	    
	    @JsonProperty("nominativo_responsabile")
	    private String nominativoResponsabile;
	    
	    @JsonProperty("recapito_responsabile")
	    private String recapitoResponsabile;
	    
	    @JsonProperty("flg_riservato")
	    private Boolean flgRiservato;
	    
		/**
		 * Gets id competenza territorio responsabile
		 * 
		 * @return idCompetenzaTerritorioResponsabile
		 */
	    public Long getIdCompetenzaResponsabile() {
	    	return idCompetenzaResponsabile;
	    }

		/**
		 * Sets id competenza territorio responsabile
		 * 
		 * @param idCompetenzaTerritorioResponsabile
		 */
	    public void setIdCompetenzaResponsabile(Long idCompetenzaResponsabile) {
	    	this.idCompetenzaResponsabile = idCompetenzaResponsabile;
	    }

		/**
		 * Gets id competenza territorio
		 * 
		 * @return idCompetenzaTerritorio
		 */
		public Long getIdCompetenzaTerritorio() {
			return idCompetenzaTerritorio;
		}

		/**
		 * Sets id competenza territorio
		 * 
		 * @param idCompetenzaTerritorio
		 */
		public void setIdCompetenzaTerritorio(Long idCompetenzaTerritorio) {
			this.idCompetenzaTerritorio = idCompetenzaTerritorio;
		}

		/**
		 * Gets tipo responsabile
		 * 
		 * @return tipoResponsabile
		 */
		public TipoResponsabileDTO getTipoResponsabile() {
			return tipoResponsabile;
		}

		/**
		 * Sets tipo responsabile
		 * 
		 * @param tipoResponsabile
		 */
		public void setTipoResponsabile(TipoResponsabileDTO tipoResponsabile) {
			this.tipoResponsabile = tipoResponsabile;
		}

//		/**
//		 * Gets descrizione tipo responsabile
//		 * 
//		 * @return descrizioneTipoResponsabile
//		 */
//		public String getDescrizioneTipoResponsabile() {
//			return descrizioneTipoResponsabile;
//		}
//
//		/**
//		 * Sets descrizione tipo responsabile
//		 * 
//		 * @param descrizioneTipoResponsabile
//		 */
//		public void setDescrizioneTipoResponsabile(String descrizioneTipoResponsabile) {
//			this.descrizioneTipoResponsabile = descrizioneTipoResponsabile;
//		}

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
	        return Objects.hash(flgRiservato, recapitoResponsabile, nominativoResponsabile, labelResponsabile, /*descrizioneTipoResponsabile,*/ tipoResponsabile, idCompetenzaTerritorio);
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
	        CompetenzaTerritorioResponsabileDTO other = (CompetenzaTerritorioResponsabileDTO) obj;
	        return Objects.equals(flgRiservato, other.flgRiservato)
	                && Objects.equals(recapitoResponsabile, other.recapitoResponsabile)
	                && Objects.equals(nominativoResponsabile, other.nominativoResponsabile)
	                && Objects.equals(labelResponsabile, other.labelResponsabile)
	                //&& Objects.equals(descrizioneTipoResponsabile, other.descrizioneTipoResponsabile)
	                && Objects.equals(tipoResponsabile, other.tipoResponsabile)
	                && Objects.equals(idCompetenzaTerritorio, other.idCompetenzaTerritorio);
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
	        sb.append(",         idCompetenzaTerritorio:").append(idCompetenzaTerritorio);
	        sb.append(",         tipoResponsabile:'").append(tipoResponsabile).append("'");
	        //sb.append(",         descrizioneTipoResponsabile:'").append(descrizioneTipoResponsabile).append("'");
	        sb.append(",         labelResponsabile:'").append(labelResponsabile).append("'");
	        sb.append(",         nominativoResponsabile:'").append(nominativoResponsabile).append("'");
	        sb.append(",         recapitoResponsabile:'").append(recapitoResponsabile).append("'");
	        sb.append(",         flgRiservato:'").append(flgRiservato).append("'");
	        sb.append("}");
	        return sb.toString();
	    }
}