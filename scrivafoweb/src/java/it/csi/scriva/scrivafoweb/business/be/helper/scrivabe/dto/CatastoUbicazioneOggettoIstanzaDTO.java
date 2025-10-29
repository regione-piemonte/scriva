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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Catasto ubicazione oggetto istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(Include.NON_NULL)
public class CatastoUbicazioneOggettoIstanzaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_catasto_ubica_oggetto_ist")
    private Long idCatastoUbicaOggettoIstanza;

    @JsonProperty("id_ubica_oggetto_istanza")
    private Long idUbicaOggettoIstanza;

    @JsonProperty("sezione")
    private String sezione;

    @JsonProperty("foglio")
    private Integer foglio;

    @JsonProperty("particella")
    private String particella;

    @JsonProperty("cod_istat_comune")
    private String codIstatComune;

    @JsonProperty("ind_fonte_dato")
    private String indFonteDato;

    @JsonProperty("cod_belfiore")
    private String codBelfiore;

    /**
     * Gets id catasto ubica oggetto istanza.
     *
     * @return the id catasto ubica oggetto istanza
     */
    public Long getIdCatastoUbicaOggettoIstanza() {
        return idCatastoUbicaOggettoIstanza;
    }

    /**
     * Sets id catasto ubica oggetto istanza.
     *
     * @param idCatastoUbicaOggettoIstanza the id catasto ubica oggetto istanza
     */
    public void setIdCatastoUbicaOggettoIstanza(Long idCatastoUbicaOggettoIstanza) {
        this.idCatastoUbicaOggettoIstanza = idCatastoUbicaOggettoIstanza;
    }

    /**
     * Gets id ubica oggetto istanza.
     *
     * @return the id ubica oggetto istanza
     */
    public Long getIdUbicaOggettoIstanza() {
        return idUbicaOggettoIstanza;
    }

    /**
     * Sets id ubica oggetto istanza.
     *
     * @param idUbicaOggettoIstanza the id ubica oggetto istanza
     */
    public void setIdUbicaOggettoIstanza(Long idUbicaOggettoIstanza) {
        this.idUbicaOggettoIstanza = idUbicaOggettoIstanza;
    }

    /**
     * Gets sezione.
     *
     * @return the sezione
     */
    public String getSezione() {
        return sezione;
    }

    /**
     * Sets sezione.
     *
     * @param sezione the sezione
     */
    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

    /**
     * Gets foglio.
     *
     * @return the foglio
     */
    public Integer getFoglio() {
        return foglio;
    }

    /**
     * Sets foglio.
     *
     * @param foglio the foglio
     */
    public void setFoglio(Integer foglio) {
        this.foglio = foglio;
    }

    /**
     * Gets particella.
     *
     * @return the particella
     */
    public String getParticella() {
        return particella;
    }

    /**
     * Sets particella.
     *
     * @param particella the particella
     */
    public void setParticella(String particella) {
        this.particella = particella;
    }


    /**
     * Gets cod istat comune.
     *
     * @return the cod istat comune
     */
    public String getCodIstatComune() {
        return codIstatComune;
    }

    /**
     * Sets cod istat comune.
     *
     * @param codIstatComune the cod istat comune
     */
    public void setCodIstatComune(String codIstatComune) {
        this.codIstatComune = codIstatComune;
    }

    /**
     * Gets ind fonte dato.
     *
     * @return the ind fonte dato
     */
    public String getIndFonteDato() {
        return indFonteDato;
    }

    /**
     * Sets ind fonte dato.
     *
     * @param indFonteDato the ind fonte dato
     */
    public void setIndFonteDato(String indFonteDato) {
        this.indFonteDato = indFonteDato;
    }

    /**
     * Gets cod belfiore.
     *
     * @return the cod belfiore
     */
    public String getCodBelfiore() {
        return codBelfiore;
    }

    /**
     * Sets cod belfiore.
     *
     * @param codBelfiore the cod belfiore
     */
    public void setCodBelfiore(String codBelfiore) {
        this.codBelfiore = codBelfiore;
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
        CatastoUbicazioneOggettoIstanzaDTO that = (CatastoUbicazioneOggettoIstanzaDTO) o;
        return Objects.equals(idCatastoUbicaOggettoIstanza, that.idCatastoUbicaOggettoIstanza) && Objects.equals(idUbicaOggettoIstanza, that.idUbicaOggettoIstanza) && Objects.equals(sezione, that.sezione) && Objects.equals(foglio, that.foglio) && Objects.equals(particella, that.particella) && Objects.equals(codIstatComune, that.codIstatComune) && Objects.equals(indFonteDato, that.indFonteDato) && Objects.equals(codBelfiore, that.codBelfiore);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idCatastoUbicaOggettoIstanza, idUbicaOggettoIstanza, sezione, foglio, particella, codIstatComune, indFonteDato, codBelfiore);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CatastoUbicazioneOggettoIstanzaDTO {\n");
        sb.append("         idCatastoUbicaOggettoIstanza:").append(idCatastoUbicaOggettoIstanza);
        sb.append(",\n         idUbicaOggettoIstanza:").append(idUbicaOggettoIstanza);
        sb.append(",\n         sezione:'").append(sezione).append("'");
        sb.append(",\n         foglio:").append(foglio);
        sb.append(",\n         particella:'").append(particella).append("'");
        sb.append(",\n         codIstatComune:'").append(codIstatComune).append("'");
        sb.append(",\n         indFonteDato:'").append(indFonteDato).append("'");
        sb.append(",\n         codBelfiore:'").append(codBelfiore).append("'");
        sb.append(super.toString());
        sb.append("}\n");
        return sb.toString();
    }
}