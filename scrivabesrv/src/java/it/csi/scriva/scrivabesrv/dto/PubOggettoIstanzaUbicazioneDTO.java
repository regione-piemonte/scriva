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
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * The type Pub oggetto istanza ubicazione dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PubOggettoIstanzaUbicazioneDTO implements Serializable {

	@JsonProperty("id_oggetto_istanza")
    private Long idOggettoIstanza;

    @JsonProperty("cod_scriva")
    private String codScriva;

    @JsonProperty("coordinata_x")
    private BigDecimal coordinataX;

    @JsonProperty("coordinata_y")
    private BigDecimal coordinataY;

    @JsonProperty("den_oggetto")
    private String denOggetto;

    @JsonProperty("des_oggetto")
    private String desOggetto;

    @JsonProperty("tipologia_oggetto")
    private TipologiaOggettoDTO tipologiaOggetto;

    @JsonProperty("ubicazione_oggetto")
    private List<PubUbicazioneOggettoIstanzaDTO> ubicazioneOoggetto;

    @JsonProperty("flg_georeferito")
    private boolean flgGeoreferito;
    
    @JsonProperty("siti_natura_2000")
    private List<OggettoIstanzaNatura2000ExtendedDTO> oggettoIstanzaNatura2000;
    
    @JsonProperty("aree_protette")
    private List<OggettoIstanzaAreaProtettaExtendedDTO> oggettoIstanzaAreaProtetta;


	/*
     * Gets is Flg Georeferito.
     *
     * @return the isFlgGeoreferito
     */
    public boolean isFlgGeoreferito() {
		return flgGeoreferito;
	}

	public void setFlgGeoreferito(boolean flgGeoreferito) {
		this.flgGeoreferito = flgGeoreferito;
	}

	/**
     * Gets id oggetto istanza.
     *
     * @return the id oggetto istanza
     */
    public Long getIdOggettoIstanza() {
        return idOggettoIstanza;
    }

    /**
     * Sets id oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     */
    public void setIdOggettoIstanza(Long idOggettoIstanza) {
        this.idOggettoIstanza = idOggettoIstanza;
    }

    /**
     * Gets cod scriva.
     *
     * @return the cod scriva
     */
    public String getCodScriva() {
        return codScriva;
    }

    /**
     * Sets cod scriva.
     *
     * @param codScriva the cod scriva
     */
    public void setCodScriva(String codScriva) {
        this.codScriva = codScriva;
    }

    /**
     * Gets coordinata x.
     *
     * @return the coordinata x
     */
    public BigDecimal getCoordinataX() {
        return coordinataX;
    }

    /**
     * Sets coordinata x.
     *
     * @param coordinataX the coordinata x
     */
    public void setCoordinataX(BigDecimal coordinataX) {
        this.coordinataX = coordinataX;
    }

    /**
     * Gets coordinata y.
     *
     * @return the coordinata y
     */
    public BigDecimal getCoordinataY() {
        return coordinataY;
    }

    /**
     * Sets coordinata y.
     *
     * @param coordinataY the coordinata y
     */
    public void setCoordinataY(BigDecimal coordinataY) {
        this.coordinataY = coordinataY;
    }

    /**
     * Gets den oggetto.
     *
     * @return the den oggetto
     */
    public String getDenOggetto() {
        return denOggetto;
    }

    /**
     * Sets den oggetto.
     *
     * @param denOggetto the den oggetto
     */
    public void setDenOggetto(String denOggetto) {
        this.denOggetto = denOggetto;
    }

    /**
     * Gets des oggetto.
     *
     * @return the des oggetto
     */
    public String getDesOggetto() {
        return desOggetto;
    }

    /**
     * Sets des oggetto.
     *
     * @param desOggetto the des oggetto
     */
    public void setDesOggetto(String desOggetto) {
        this.desOggetto = desOggetto;
    }




    public TipologiaOggettoDTO getTipologiaOggetto() {
		return tipologiaOggetto;
	}

	public void setTipologiaOggetto(TipologiaOggettoDTO tipologiaOggetto) {
		this.tipologiaOggetto = tipologiaOggetto;
	}

	public List<PubUbicazioneOggettoIstanzaDTO> getUbicazioneOoggetto() {
		return ubicazioneOoggetto;
	}

	public void setUbicazioneOoggetto(List<PubUbicazioneOggettoIstanzaDTO> ubicazioneOoggetto) {
		this.ubicazioneOoggetto = ubicazioneOoggetto;
	}
	
    public List<OggettoIstanzaAreaProtettaExtendedDTO> getOggettoIstanzaAreaProtetta() {
		return oggettoIstanzaAreaProtetta;
	}

	public void setOggettoIstanzaAreaProtetta(List<OggettoIstanzaAreaProtettaExtendedDTO> oggettoIstanzaAreaProtetta) {
		this.oggettoIstanzaAreaProtetta = oggettoIstanzaAreaProtetta;
	}

    public List<OggettoIstanzaNatura2000ExtendedDTO> getOggettoIstanzaNatura2000() {
		return oggettoIstanzaNatura2000;
	}

	public void setOggettoIstanzaNatura2000(List<OggettoIstanzaNatura2000ExtendedDTO> oggettoIstanzaNatura2000) {
		this.oggettoIstanzaNatura2000 = oggettoIstanzaNatura2000;
	}
	
	/**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PubOggettoIstanzaUbicazioneDTO that = (PubOggettoIstanzaUbicazioneDTO) o;
        return Objects.equals(idOggettoIstanza, that.idOggettoIstanza) && Objects.equals(codScriva, that.codScriva) && Objects.equals(coordinataX, that.coordinataX) && Objects.equals(coordinataY, that.coordinataY) && Objects.equals(denOggetto, that.denOggetto) && Objects.equals(desOggetto, that.desOggetto) && Objects.equals(tipologiaOggetto, that.tipologiaOggetto) && Objects.equals(ubicazioneOoggetto, that.ubicazioneOoggetto) && Objects.equals(oggettoIstanzaNatura2000, that.oggettoIstanzaNatura2000) && Objects.equals(oggettoIstanzaAreaProtetta, that.oggettoIstanzaAreaProtetta);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idOggettoIstanza, codScriva, coordinataX, coordinataY, denOggetto, desOggetto, tipologiaOggetto, ubicazioneOoggetto, oggettoIstanzaNatura2000, oggettoIstanzaAreaProtetta);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PubOggettoIstanzaUbicazioneDTO {\n");
        sb.append("         idOggettoIstanza:").append(idOggettoIstanza);
        sb.append(",\n         codScriva:'").append(codScriva).append("'");
        sb.append(",\n         coordinataX:").append(coordinataX);
        sb.append(",\n         coordinataY:").append(coordinataY);
        sb.append(",\n         denOggetto:'").append(denOggetto).append("'");
        sb.append(",\n         desOggetto:'").append(desOggetto).append("'");
        sb.append(",\n         tipologiaOggetto:'").append(tipologiaOggetto).append("'");
        sb.append(",\n         ubicazioneOoggetto:").append(ubicazioneOoggetto);
        sb.append(",\n         oggettoIstanzaNatura2000:").append(oggettoIstanzaNatura2000);
        sb.append(",\n         oggettoIstanzaAreaProtetta:").append(oggettoIstanzaAreaProtetta);
        sb.append("}\n");
        return sb.toString();
    }
}