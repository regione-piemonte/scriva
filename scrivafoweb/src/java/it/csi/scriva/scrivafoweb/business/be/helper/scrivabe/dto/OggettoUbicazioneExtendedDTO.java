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
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OggettoUbicazioneExtendedDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_oggetto")
    private Long idOggetto;

    @JsonProperty("tipologia_oggetto")
    private TipologiaOggettoExtendedDTO tipologiaOggetto;

    @JsonProperty("stato_oggetto")
    private StatoOggettoDTO statoOggetto;

    @JsonProperty("ubicazione_oggetto")
    private List<UbicazioneOggettoExtendedDTO> ubicazioneOggetto;

    @JsonProperty("cod_oggetto_fonte")
    private String codOggettoFonte;

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

    @JsonProperty("id_istanza_aggiornamento")
    private Long idIstanzaAggiornamento;

    public Long getIdOggetto() {
        return idOggetto;
    }

    public void setIdOggetto(Long idOggetto) {
        this.idOggetto = idOggetto;
    }

    public TipologiaOggettoExtendedDTO getTipologiaOggetto() {
        return tipologiaOggetto;
    }

    public void setTipologiaOggetto(TipologiaOggettoExtendedDTO tipologiaOggetto) {
        this.tipologiaOggetto = tipologiaOggetto;
    }

    public StatoOggettoDTO getStatoOggetto() {
        return statoOggetto;
    }

    public void setStatoOggetto(StatoOggettoDTO statoOggetto) {
        this.statoOggetto = statoOggetto;
    }

    public List<UbicazioneOggettoExtendedDTO> getUbicazioneOggetto() {
        return ubicazioneOggetto;
    }

    public void setUbicazioneOggetto(List<UbicazioneOggettoExtendedDTO> ubicazioneOggetto) {
        this.ubicazioneOggetto = ubicazioneOggetto;
    }

    public String getCodOggettoFonte() {
        return codOggettoFonte;
    }

    public void setCodOggettoFonte(String codOggettoFonte) {
        this.codOggettoFonte = codOggettoFonte;
    }

    public String getCodScriva() {
        return codScriva;
    }

    public void setCodScriva(String codScriva) {
        this.codScriva = codScriva;
    }

    public String getDenOggetto() {
        return denOggetto;
    }

    public void setDenOggetto(String denOggetto) {
        this.denOggetto = denOggetto;
    }

    public String getDesOggetto() {
        return desOggetto;
    }

    public void setDesOggetto(String desOggetto) {
        this.desOggetto = desOggetto;
    }

    public BigDecimal getCoordinataX() {
        return coordinataX;
    }

    public void setCoordinataX(BigDecimal coordinataX) {
        this.coordinataX = coordinataX;
    }

    public BigDecimal getCoordinataY() {
        return coordinataY;
    }

    public void setCoordinataY(BigDecimal coordinataY) {
        this.coordinataY = coordinataY;
    }

    public Long getIdMasterdataOrigine() {
        return idMasterdataOrigine;
    }

    public void setIdMasterdataOrigine(Long idMasterdataOrigine) {
        this.idMasterdataOrigine = idMasterdataOrigine;
    }

    public Long getIdMasterdata() {
        return idMasterdata;
    }

    public void setIdMasterdata(Long idMasterdata) {
        this.idMasterdata = idMasterdata;
    }

    public Long getIdIstanzaAggiornamento() {
        return idIstanzaAggiornamento;
    }

    public void setIdIstanzaAggiornamento(Long idIstanzaAggiornamento) {
        this.idIstanzaAggiornamento = idIstanzaAggiornamento;
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
        OggettoUbicazioneExtendedDTO that = (OggettoUbicazioneExtendedDTO) o;
        return Objects.equals(idOggetto, that.idOggetto) && Objects.equals(tipologiaOggetto, that.tipologiaOggetto) && Objects.equals(statoOggetto, that.statoOggetto) && Objects.equals(ubicazioneOggetto, that.ubicazioneOggetto) && Objects.equals(codOggettoFonte, that.codOggettoFonte) && Objects.equals(codScriva, that.codScriva) && Objects.equals(denOggetto, that.denOggetto) && Objects.equals(desOggetto, that.desOggetto) && Objects.equals(coordinataX, that.coordinataX) && Objects.equals(coordinataY, that.coordinataY) && Objects.equals(idMasterdataOrigine, that.idMasterdataOrigine) && Objects.equals(idMasterdata, that.idMasterdata) && Objects.equals(idIstanzaAggiornamento, that.idIstanzaAggiornamento);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idOggetto, tipologiaOggetto, statoOggetto, ubicazioneOggetto, codOggettoFonte, codScriva, denOggetto, desOggetto, coordinataX, coordinataY, idMasterdataOrigine, idMasterdata, idIstanzaAggiornamento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "OggettoUbicazioneExtendedDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idOggetto:" + idOggetto +
                ",\n         tipologiaOggetto:" + tipologiaOggetto +
                ",\n         statoOggetto:" + statoOggetto +
                ",\n         ubicazioneOggetto:" + ubicazioneOggetto +
                ",\n         codOggettoFonte:'" + codOggettoFonte + "'" +
                ",\n         codScriva:'" + codScriva + "'" +
                ",\n         denOggetto:'" + denOggetto + "'" +
                ",\n         desOggetto:'" + desOggetto + "'" +
                ",\n         coordinataX:" + coordinataX +
                ",\n         coordinataY:" + coordinataY +
                ",\n         idMasterdataOrigine:" + idMasterdataOrigine +
                ",\n         idMasterdata:" + idMasterdata +
                ",\n         idIstanzaAggiornamento:" + idIstanzaAggiornamento +
                "}\n";
    }
}