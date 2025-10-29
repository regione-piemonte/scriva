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
 * The type Oggetto istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoIstanzaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_oggetto_istanza")
    private Long idOggettoIstanza;

    @JsonProperty("id_oggetto")
    private Long idOggetto;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_tipologia_oggetto")
    private Long idTipologiaOggetto;

    @JsonProperty("ind_geo_stato")
    private String indGeoStato;

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

    @JsonIgnore
    private Long idIstanzaAttore;

    @JsonIgnore
    private Long idFunzionario;

    @JsonProperty("cod_oggetto_fonte")
    private String codOggettoFonte;

    @JsonProperty("cod_utenza")
    private String codUtenza;

    @JsonProperty("note_atto_precedente")
    private String noteAttoPrecedente;

    @JsonProperty("flg_geo_modificato")
    private Boolean flgGeoModificato;

    @JsonProperty("ind_livello")
    private Integer indLivello;

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
     * Gets id oggetto.
     *
     * @return the id oggetto
     */
    public Long getIdOggetto() {
        return idOggetto;
    }

    /**
     * Sets id oggetto.
     *
     * @param idOggetto the id oggetto
     */
    public void setIdOggetto(Long idOggetto) {
        this.idOggetto = idOggetto;
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
     * Gets id tipologia oggetto.
     *
     * @return the id tipologia oggetto
     */
    public Long getIdTipologiaOggetto() {
        return idTipologiaOggetto;
    }

    /**
     * Sets id tipologia oggetto.
     *
     * @param idTipologiaOggetto the id tipologia oggetto
     */
    public void setIdTipologiaOggetto(Long idTipologiaOggetto) {
        this.idTipologiaOggetto = idTipologiaOggetto;
    }

    /**
     * Gets ind geo stato.
     *
     * @return the ind geo stato
     */
    public String getIndGeoStato() {
        return indGeoStato;
    }

    /**
     * Sets ind geo stato.
     *
     * @param indGeoStato the ind geo stato
     */
    public void setIndGeoStato(String indGeoStato) {
        this.indGeoStato = indGeoStato;
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
     * Gets id masterdata origine.
     *
     * @return the id masterdata origine
     */
    public Long getIdMasterdataOrigine() {
        return idMasterdataOrigine;
    }

    /**
     * Sets id masterdata origine.
     *
     * @param idMasterdataOrigine the id masterdata origine
     */
    public void setIdMasterdataOrigine(Long idMasterdataOrigine) {
        this.idMasterdataOrigine = idMasterdataOrigine;
    }

    /**
     * Gets id masterdata.
     *
     * @return the id masterdata
     */
    public Long getIdMasterdata() {
        return idMasterdata;
    }

    /**
     * Sets id masterdata.
     *
     * @param idMasterdata the id masterdata
     */
    public void setIdMasterdata(Long idMasterdata) {
        this.idMasterdata = idMasterdata;
    }

    /**
     * Gets id istanza attore.
     *
     * @return the id istanza attore
     */
    public Long getIdIstanzaAttore() {
        return idIstanzaAttore;
    }

    /**
     * Sets id istanza attore.
     *
     * @param idIstanzaAttore the id istanza attore
     */
    public void setIdIstanzaAttore(Long idIstanzaAttore) {
        this.idIstanzaAttore = idIstanzaAttore;
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
     * Gets cod utenza.
     *
     * @return the cod utenza
     */
    public String getCodUtenza() {
        return codUtenza;
    }

    /**
     * Sets cod utenza.
     *
     * @param codUtenza the cod utenza
     */
    public void setCodUtenza(String codUtenza) {
        this.codUtenza = codUtenza;
    }

    /**
     * Gets note atto precedente.
     *
     * @return the note atto precedente
     */
    public String getNoteAttoPrecedente() {
        return noteAttoPrecedente;
    }

    /**
     * Sets note atto precedente.
     *
     * @param noteAttoPrecedente the note atto precedente
     */
    public void setNoteAttoPrecedente(String noteAttoPrecedente) {
        this.noteAttoPrecedente = noteAttoPrecedente;
    }

    /**
     * Gets flg geo modificato.
     *
     * @return the flg geo modificato
     */
    public Boolean getFlgGeoModificato() {
        return flgGeoModificato;
    }

    /**
     * Sets flg geo modificato.
     *
     * @param flgGeoModificato the flg geo modificato
     */
    public void setFlgGeoModificato(Boolean flgGeoModificato) {
        this.flgGeoModificato = flgGeoModificato;
    }

    /**
     * Gets ind livello.
     *
     * @return the ind livello
     */
    public Integer getIndLivello() {
        return indLivello;
    }

    /**
     * Sets ind livello.
     *
     * @param indLivello the ind livello
     */
    public void setIndLivello(Integer indLivello) {
        this.indLivello = indLivello;
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
        OggettoIstanzaDTO that = (OggettoIstanzaDTO) o;
        return Objects.equals(idOggettoIstanza, that.idOggettoIstanza) && Objects.equals(idOggetto, that.idOggetto) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idTipologiaOggetto, that.idTipologiaOggetto) && Objects.equals(indGeoStato, that.indGeoStato) && Objects.equals(denOggetto, that.denOggetto) && Objects.equals(desOggetto, that.desOggetto) && Objects.equals(coordinataX, that.coordinataX) && Objects.equals(coordinataY, that.coordinataY) && Objects.equals(idMasterdataOrigine, that.idMasterdataOrigine) && Objects.equals(idMasterdata, that.idMasterdata) && Objects.equals(idIstanzaAttore, that.idIstanzaAttore) && Objects.equals(idFunzionario, that.idFunzionario) && Objects.equals(codOggettoFonte, that.codOggettoFonte) && Objects.equals(codUtenza, that.codUtenza) && Objects.equals(noteAttoPrecedente, that.noteAttoPrecedente) && Objects.equals(flgGeoModificato, that.flgGeoModificato) && Objects.equals(indLivello, that.indLivello);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idOggettoIstanza, idOggetto, idIstanza, idTipologiaOggetto, indGeoStato, denOggetto, desOggetto, coordinataX, coordinataY, idMasterdataOrigine, idMasterdata, idIstanzaAttore, idFunzionario, codOggettoFonte, codUtenza, noteAttoPrecedente, flgGeoModificato, indLivello);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoIstanzaDTO {\n");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",\n         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",\n         gestDataUpd:").append(gestDataUpd);
        sb.append(",\n         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",\n         gestUID:'").append(gestUID).append("'");
        sb.append(",\n         idOggettoIstanza:").append(idOggettoIstanza);
        sb.append(",\n         idOggetto:").append(idOggetto);
        sb.append(",\n         idIstanza:").append(idIstanza);
        sb.append(",\n         idTipologiaOggetto:").append(idTipologiaOggetto);
        sb.append(",\n         indGeoStato:'").append(indGeoStato).append("'");
        sb.append(",\n         denOggetto:'").append(denOggetto).append("'");
        sb.append(",\n         desOggetto:'").append(desOggetto).append("'");
        sb.append(",\n         coordinataX:").append(coordinataX);
        sb.append(",\n         coordinataY:").append(coordinataY);
        sb.append(",\n         idMasterdataOrigine:").append(idMasterdataOrigine);
        sb.append(",\n         idMasterdata:").append(idMasterdata);
        sb.append(",\n         idIstanzaAttore:").append(idIstanzaAttore);
        sb.append(",\n         idFunzionario:").append(idFunzionario);
        sb.append(",\n         codOggettoFonte:'").append(codOggettoFonte).append("'");
        sb.append(",\n         codUtenza:'").append(codUtenza).append("'");
        sb.append(",\n         noteAttoPrecedente:'").append(noteAttoPrecedente).append("'");
        sb.append(",\n         flgGeoModificato:").append(flgGeoModificato);
        sb.append(",\n         indLivello:").append(indLivello);
        sb.append("}\n");
        return sb.toString();
    }

}