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
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * The type Oggetto dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_oggetto")
    private Long idOggetto;

    @JsonProperty("id_tipologia_oggetto")
    private Long idTipologiaOggetto;

    @JsonProperty("id_stato_oggetto")
    private Long idStatoOggetto;

    @JsonProperty("cod_scriva")
    private String codScriva;

    @JsonProperty("den_oggetto")
    private String denOggetto;

    @JsonProperty("des_oggetto")
    private String desOggetto;

    @JsonProperty("coordinata_x")
    private BigDecimal coordinataX;

    @JsonProperty("coordinata_y")
    private BigDecimal coordinataY;

    @JsonProperty("id_masterdata_origine")
    private Long idMasterdataOrigine;

    @JsonProperty("id_masterdata")
    private Long idMasterdata;

    @JsonProperty("cod_oggetto_fonte")
    private String codOggettoFonte;

    @JsonProperty("id_istanza_aggiornamento")
    private Long idIstanzaAggiornamento;

    @JsonIgnore
    private Long idFunzionario;

    /**
     * Gets id oggetto.
     *
     * @return idOggetto id oggetto
     */
    public Long getIdOggetto() {
        return idOggetto;
    }

    /**
     * Sets id oggetto.
     *
     * @param idOggetto idOggetto
     */
    public void setIdOggetto(Long idOggetto) {
        this.idOggetto = idOggetto;
    }

    /**
     * Gets id tipologia oggetto.
     *
     * @return idTipologiaOggetto id tipologia oggetto
     */
    public Long getIdTipologiaOggetto() {
        return idTipologiaOggetto;
    }

    /**
     * Sets id tipologia oggetto.
     *
     * @param idTipologiaOggetto idTipologiaOggetto
     */
    public void setIdTipologiaOggetto(Long idTipologiaOggetto) {
        this.idTipologiaOggetto = idTipologiaOggetto;
    }

    /**
     * Gets id stato oggetto.
     *
     * @return idStatoOggetto id stato oggetto
     */
    public Long getIdStatoOggetto() {
        return idStatoOggetto;
    }

    /**
     * Sets id stato oggetto.
     *
     * @param idStatoOggetto idStatoOggetto
     */
    public void setIdStatoOggetto(Long idStatoOggetto) {
        this.idStatoOggetto = idStatoOggetto;
    }

    /**
     * Gets cod oggetto fonte.
     *
     * @return the cod oggetto fonte
     */
    public String getCodOggettoFonte() {
        return codOggettoFonte;
    }

    /**
     * Sets cod oggetto fonte.
     *
     * @param codOggettoFonte the cod oggetto fonte
     */
    public void setCodOggettoFonte(String codOggettoFonte) {
        this.codOggettoFonte = codOggettoFonte;
    }

    /**
     * Gets cod scriva.
     *
     * @return codScriva cod scriva
     */
    public String getCodScriva() {
        return codScriva;
    }

    /**
     * Sets cod scriva.
     *
     * @param codScriva codScriva
     */
    public void setCodScriva(String codScriva) {
        this.codScriva = codScriva;
    }

    /**
     * Gets den oggetto.
     *
     * @return denOggetto den oggetto
     */
    public String getDenOggetto() {
        return denOggetto;
    }

    /**
     * Sets den oggetto.
     *
     * @param denOggetto denOggetto
     */
    public void setDenOggetto(String denOggetto) {
        this.denOggetto = denOggetto;
    }

    /**
     * Gets des oggetto.
     *
     * @return desOggetto des oggetto
     */
    public String getDesOggetto() {
        return desOggetto;
    }

    /**
     * Sets des oggetto.
     *
     * @param desOggetto desOggetto
     */
    public void setDesOggetto(String desOggetto) {
        this.desOggetto = desOggetto;
    }

    /**
     * Gets coordinata x.
     *
     * @return coordinataX coordinata x
     */
    public BigDecimal getCoordinataX() {
        return coordinataX;
    }

    /**
     * Sets coordinata x.
     *
     * @param coordinataX coordinataX
     */
    public void setCoordinataX(BigDecimal coordinataX) {
        this.coordinataX = coordinataX;
    }

    /**
     * Gets coordinata y.
     *
     * @return coordinataY coordinata y
     */
    public BigDecimal getCoordinataY() {
        return coordinataY;
    }

    /**
     * Sets coordinata y.
     *
     * @param coordinataY coordinataY
     */
    public void setCoordinataY(BigDecimal coordinataY) {
        this.coordinataY = coordinataY;
    }

    /**
     * Gets id masterdata origine.
     *
     * @return idMasterdataOrigine id masterdata origine
     */
    public Long getIdMasterdataOrigine() {
        return idMasterdataOrigine;
    }

    /**
     * Sets id masterdata origine.
     *
     * @param idMasterdataOrigine idMasterdataOrigine
     */
    public void setIdMasterdataOrigine(Long idMasterdataOrigine) {
        this.idMasterdataOrigine = idMasterdataOrigine;
    }

    /**
     * Gets id masterdata.
     *
     * @return idMasterdata id masterdata
     */
    public Long getIdMasterdata() {
        return idMasterdata;
    }

    /**
     * Sets id masterdata.
     *
     * @param idMasterdata idMasterdata
     */
    public void setIdMasterdata(Long idMasterdata) {
        this.idMasterdata = idMasterdata;
    }

    /**
     * Gets id istanza aggiornamento.
     *
     * @return the id istanza aggiornamento
     */
    public Long getIdIstanzaAggiornamento() {
        return idIstanzaAggiornamento;
    }

    /**
     * Sets id istanza aggiornamento.
     *
     * @param idIstanzaAggiornamento the id istanza aggiornamento
     */
    public void setIdIstanzaAggiornamento(Long idIstanzaAggiornamento) {
        this.idIstanzaAggiornamento = idIstanzaAggiornamento;
    }

    /**
     * Gets id funzionario.
     *
     * @return the id funzionario
     */
    public Long getIdFunzionario() {
        return idFunzionario;
    }

    /**
     * Sets id funzionario.
     *
     * @param idFunzionario the id funzionario
     */
    public void setIdFunzionario(Long idFunzionario) {
        this.idFunzionario = idFunzionario;
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
        OggettoDTO that = (OggettoDTO) o;
        return Objects.equals(idOggetto, that.idOggetto) && Objects.equals(idTipologiaOggetto, that.idTipologiaOggetto) && Objects.equals(idStatoOggetto, that.idStatoOggetto) && Objects.equals(codScriva, that.codScriva) && Objects.equals(denOggetto, that.denOggetto) && Objects.equals(desOggetto, that.desOggetto) && Objects.equals(coordinataX, that.coordinataX) && Objects.equals(coordinataY, that.coordinataY) && Objects.equals(idMasterdataOrigine, that.idMasterdataOrigine) && Objects.equals(idMasterdata, that.idMasterdata) && Objects.equals(codOggettoFonte, that.codOggettoFonte) && Objects.equals(idIstanzaAggiornamento, that.idIstanzaAggiornamento) && Objects.equals(idFunzionario, that.idFunzionario);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idOggetto, idTipologiaOggetto, idStatoOggetto, codScriva, denOggetto, desOggetto, coordinataX, coordinataY, idMasterdataOrigine, idMasterdata, codOggettoFonte, idIstanzaAggiornamento, idFunzionario);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoDTO {\n");
        sb.append("         idOggetto:").append(idOggetto);
        sb.append(",\n         idTipologiaOggetto:").append(idTipologiaOggetto);
        sb.append(",\n         idStatoOggetto:").append(idStatoOggetto);
        sb.append(",\n         codScriva:'").append(codScriva).append("'");
        sb.append(",\n         denOggetto:'").append(denOggetto).append("'");
        sb.append(",\n         desOggetto:'").append(desOggetto).append("'");
        sb.append(",\n         coordinataX:").append(coordinataX);
        sb.append(",\n         coordinataY:").append(coordinataY);
        sb.append(",\n         idMasterdataOrigine:").append(idMasterdataOrigine);
        sb.append(",\n         idMasterdata:").append(idMasterdata);
        sb.append(",\n         codOggettoFonte:'").append(codOggettoFonte).append("'");
        sb.append(",\n         idIstanzaAggiornamento:").append(idIstanzaAggiornamento);
        sb.append(",\n         idFunzionario:").append(idFunzionario);
        sb.append(super.toString());
        sb.append("}\n");
        return sb.toString();
    }

}