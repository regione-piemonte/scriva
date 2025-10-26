/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.TipologiaOggettoDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * The type Pub oggetto istanza ubicazione dto.
 *
 * @author CSI PIEMONTE
 */
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

    /**
     * Is flg georeferito boolean.
     *
     * @return the boolean
     */
    /*
     * Gets is Flg Georeferito.
     *
     * @return the isFlgGeoreferito
     */
    public boolean isFlgGeoreferito() {
        return flgGeoreferito;
    }

    /**
     * Sets flg georeferito.
     *
     * @param flgGeoreferito the flg georeferito
     */
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


    /**
     * Gets tipologia oggetto.
     *
     * @return the tipologia oggetto
     */
    public TipologiaOggettoDTO getTipologiaOggetto() {
        return tipologiaOggetto;
    }

    /**
     * Sets tipologia oggetto.
     *
     * @param tipologiaOggetto the tipologia oggetto
     */
    public void setTipologiaOggetto(TipologiaOggettoDTO tipologiaOggetto) {
        this.tipologiaOggetto = tipologiaOggetto;
    }

    /**
     * Gets ubicazione ooggetto.
     *
     * @return the ubicazione ooggetto
     */
    public List<PubUbicazioneOggettoIstanzaDTO> getUbicazioneOoggetto() {
        return ubicazioneOoggetto;
    }

    /**
     * Sets ubicazione ooggetto.
     *
     * @param ubicazioneOoggetto the ubicazione ooggetto
     */
    public void setUbicazioneOoggetto(List<PubUbicazioneOggettoIstanzaDTO> ubicazioneOoggetto) {
        this.ubicazioneOoggetto = ubicazioneOoggetto;
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
        return Objects.equals(idOggettoIstanza, that.idOggettoIstanza) && Objects.equals(codScriva, that.codScriva) && Objects.equals(coordinataX, that.coordinataX) && Objects.equals(coordinataY, that.coordinataY) && Objects.equals(denOggetto, that.denOggetto) && Objects.equals(desOggetto, that.desOggetto) && Objects.equals(tipologiaOggetto, that.tipologiaOggetto) && Objects.equals(ubicazioneOoggetto, that.ubicazioneOoggetto);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idOggettoIstanza, codScriva, coordinataX, coordinataY, denOggetto, desOggetto, tipologiaOggetto, ubicazioneOoggetto);
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
        sb.append("}\n");
        return sb.toString();
    }
}