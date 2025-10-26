/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */

package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class OggettoUbicazioneExtendedDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_oggetto")
    private Long idOggetto;

    @JsonProperty("tipologia_oggetto")
    private TipologiaOggettoExtendedDTO tipologiaOggetto;

    @JsonProperty("stato_oggetto")
    private StatoOggettoDTO statoOggetto;

    @JsonProperty("ubicazione_oggetto")
    private List<UbicazioneOggettoExtendedDTO> ubicazioneOggetto;

    @JsonProperty("cod_sira")
    private Long codSira;

    @JsonProperty("cod_scriva")
    private Long codScriva;

    @JsonProperty("den_oggetto")
    private String denOggetto;

    @JsonProperty("des_oggetto")
    private String desOggetto;

    @JsonProperty("coordinata_x")
    private BigDecimal coordinataX;

    @JsonProperty("coordinata_y")
    private BigDecimal coordinataY;

    @JsonProperty("flg_esistente")
    private Boolean flgEsistente;

    @JsonProperty("id_masterdata_origine")
    private Long idMasterdataOrigine;

    @JsonProperty("id_masterdata")
    private Long idMasterdata;

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

    public Long getCodSira() {
        return codSira;
    }

    public void setCodSira(Long codSira) {
        this.codSira = codSira;
    }

    public Long getCodScriva() {
        return codScriva;
    }

    public void setCodScriva(Long codScriva) {
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

    public Boolean getFlgEsistente() {
        return flgEsistente;
    }

    public void setFlgEsistente(Boolean flgEsistente) {
        this.flgEsistente = flgEsistente;
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

    @Override
    public int hashCode() {
        return Objects.hash(codScriva, codSira, coordinataX, coordinataY, denOggetto, desOggetto, flgEsistente, idMasterdata, idMasterdataOrigine, idOggetto, statoOggetto, tipologiaOggetto, ubicazioneOggetto);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OggettoUbicazioneExtendedDTO other = (OggettoUbicazioneExtendedDTO) obj;
        return Objects.equals(codScriva, other.codScriva) && Objects.equals(codSira, other.codSira) && Objects.equals(coordinataX, other.coordinataX) && Objects.equals(coordinataY, other.coordinataY) && Objects.equals(denOggetto, other.denOggetto) && Objects.equals(desOggetto, other.desOggetto) && Objects.equals(flgEsistente, other.flgEsistente) && Objects.equals(idMasterdata, other.idMasterdata) && Objects.equals(idMasterdataOrigine, other.idMasterdataOrigine) && Objects.equals(idOggetto, other.idOggetto) && Objects.equals(statoOggetto, other.statoOggetto) && Objects.equals(tipologiaOggetto, other.tipologiaOggetto) && Objects.equals(ubicazioneOggetto, other.ubicazioneOggetto);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OggettoUbicazioneExtendedDTO {\n    idOggetto: ").append(idOggetto).append("\n    tipologiaOggetto: ").append(tipologiaOggetto).append("\n    statoOggetto: ").append(statoOggetto).append("\n    ubicazioneOggetto: ").append(ubicazioneOggetto).append("\n    codSira: ").append(codSira).append("\n    codScriva: ").append(codScriva).append("\n    denOggetto: ").append(denOggetto).append("\n    desOggetto: ").append(desOggetto).append("\n    coordinataX: ").append(coordinataX).append("\n    coordinataY: ").append(coordinataY).append("\n    flgEsistente: ").append(flgEsistente).append("\n    idMasterdataOrigine: ").append(idMasterdataOrigine).append("\n    idMasterdata: ").append(idMasterdata).append("\n    gestDataIns: ").append(gestDataIns).append("\n    gestAttoreIns: ").append(gestAttoreIns).append("\n    gestDataUpd: ").append(gestDataUpd).append("\n    gestAttoreUpd: ").append(gestAttoreUpd).append("\n    gestUID: ").append(gestUID).append("\n}");
        return builder.toString();
    }

}