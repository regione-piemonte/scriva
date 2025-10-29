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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * The type Oggetto istanza natura 2000 dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OggettoIstanzaNatura2000DTO extends BaseDTO implements Serializable {

    @JsonProperty("id_oggetto_natura_2000")
    private Long idOggettoNatura2000;

    @JsonProperty("id_oggetto_istanza")
    private Long idOggettoIstanza;

    @JsonProperty("id_competenza_territorio")
    private Long idCompetenzaTerritorio;

    @JsonProperty("id_tipo_natura_2000")
    private Long idTipoNatura2000;

    @JsonProperty("cod_amministrativo")
    private String codAmministrativo;

    @JsonProperty("cod_gestore_fonte")
    private String codGestoreFonte;

    @JsonProperty("des_sito_natura_2000")
    private String desSitoNatura2000;

    @JsonProperty("num_distanza")
    private BigDecimal numDistanza;

    @JsonProperty("flg_ricade")
    private Boolean flgRicade;

    @JsonProperty("des_elemento_discontinuita")
    private String desElementoDiscontinuita;

    /**
     * Gets id oggetto natura 2000.
     *
     * @return the id oggetto natura 2000
     */
    public Long getIdOggettoNatura2000() {
        return idOggettoNatura2000;
    }

    /**
     * Sets id oggetto natura 2000.
     *
     * @param idOggettoNatura2000 the id oggetto natura 2000
     */
    public void setIdOggettoNatura2000(Long idOggettoNatura2000) {
        this.idOggettoNatura2000 = idOggettoNatura2000;
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
     * Gets id competenza territorio.
     *
     * @return the id competenza territorio
     */
    public Long getIdCompetenzaTerritorio() {
        return idCompetenzaTerritorio;
    }

    /**
     * Sets id competenza territorio.
     *
     * @param idCompetenzaTerritorio the id competenza territorio
     */
    public void setIdCompetenzaTerritorio(Long idCompetenzaTerritorio) {
        this.idCompetenzaTerritorio = idCompetenzaTerritorio;
    }

    /**
     * Gets id tipo natura 2000.
     *
     * @return the id tipo natura 2000
     */
    public Long getIdTipoNatura2000() {
        return idTipoNatura2000;
    }

    /**
     * Sets id tipo natura 2000.
     *
     * @param idTipoNatura2000 the id tipo natura 2000
     */
    public void setIdTipoNatura2000(Long idTipoNatura2000) {
        this.idTipoNatura2000 = idTipoNatura2000;
    }

    /**
     * Gets cod amministrativo.
     *
     * @return the cod amministrativo
     */
    public String getCodAmministrativo() {
        return codAmministrativo;
    }

    /**
     * Sets cod amministrativo.
     *
     * @param codAmministrativo the cod amministrativo
     */
    public void setCodAmministrativo(String codAmministrativo) {
        this.codAmministrativo = codAmministrativo;
    }

    /**
     * Gets cod gestore fonte.
     *
     * @return the cod gestore fonte
     */
    public String getCodGestoreFonte() {
        return codGestoreFonte;
    }

    /**
     * Sets cod gestore fonte.
     *
     * @param codGestoreFonte the cod gestore fonte
     */
    public void setCodGestoreFonte(String codGestoreFonte) {
        this.codGestoreFonte = codGestoreFonte;
    }

    /**
     * Gets des sito natura 2000.
     *
     * @return the des sito natura 2000
     */
    public String getDesSitoNatura2000() {
        return desSitoNatura2000;
    }

    /**
     * Sets des sito natura 2000.
     *
     * @param desSitoNatura2000 the des sito natura 2000
     */
    public void setDesSitoNatura2000(String desSitoNatura2000) {
        this.desSitoNatura2000 = desSitoNatura2000;
    }

    /**
     * Gets num distanza.
     *
     * @return the num distanza
     */
    public BigDecimal getNumDistanza() {
        return numDistanza;
    }

    /**
     * Sets num distanza.
     *
     * @param numDistanza the num distanza
     */
    public void setNumDistanza(BigDecimal numDistanza) {
        this.numDistanza = numDistanza;
    }

    /**
     * Gets flg ricade.
     *
     * @return the flg ricade
     */
    public Boolean getFlgRicade() {
        return flgRicade;
    }

    /**
     * Sets flg ricade.
     *
     * @param flgRicade the flg ricade
     */
    public void setFlgRicade(Boolean flgRicade) {
        this.flgRicade = flgRicade;
    }

    /**
     * Gets des elemento discontinuita.
     *
     * @return the des elemento discontinuita
     */
    public String getDesElementoDiscontinuita() {
        return desElementoDiscontinuita;
    }

    /**
     * Sets des elemento discontinuita.
     *
     * @param desElementoDiscontinuita the des elemento discontinuita
     */
    public void setDesElementoDiscontinuita(String desElementoDiscontinuita) {
        this.desElementoDiscontinuita = desElementoDiscontinuita;
    }

    /**
     * Is equal boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @JsonIgnore
    public boolean isEqual(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OggettoIstanzaNatura2000DTO that = (OggettoIstanzaNatura2000DTO) o;
        //return Objects.equals(idOggettoIstanza, that.idOggettoIstanza) && Objects.equals(idCompetenzaTerritorio, that.idCompetenzaTerritorio) && Objects.equals(idTipoNatura2000, that.idTipoNatura2000) && Objects.equals(codAmministrativo, that.codAmministrativo) && Objects.equals(codGestoreFonte, that.codGestoreFonte) && Objects.equals(desSitoNatura2000, that.desSitoNatura2000);
        return Objects.equals(idOggettoIstanza, that.idOggettoIstanza) && Objects.equals(idCompetenzaTerritorio, that.idCompetenzaTerritorio);
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
        OggettoIstanzaNatura2000DTO that = (OggettoIstanzaNatura2000DTO) o;
        return Objects.equals(idOggettoNatura2000, that.idOggettoNatura2000) && Objects.equals(idOggettoIstanza, that.idOggettoIstanza) && Objects.equals(idCompetenzaTerritorio, that.idCompetenzaTerritorio) && Objects.equals(idTipoNatura2000, that.idTipoNatura2000) && Objects.equals(codAmministrativo, that.codAmministrativo) && Objects.equals(codGestoreFonte, that.codGestoreFonte) && Objects.equals(desSitoNatura2000, that.desSitoNatura2000) && Objects.equals(numDistanza, that.numDistanza) && Objects.equals(flgRicade, that.flgRicade) && Objects.equals(desElementoDiscontinuita, that.desElementoDiscontinuita);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idOggettoNatura2000, idOggettoIstanza, idCompetenzaTerritorio, idTipoNatura2000, codAmministrativo, codGestoreFonte, desSitoNatura2000, numDistanza, flgRicade, desElementoDiscontinuita);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "OggettoIstanzaNatura2000DTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idOggettoNatura2000:" + idOggettoNatura2000 +
                ",\n         idOggettoIstanza:" + idOggettoIstanza +
                ",\n         idCompetenzaTerritorio:" + idCompetenzaTerritorio +
                ",\n         idTipoNatura2000:" + idTipoNatura2000 +
                ",\n         codAmministrativo:'" + codAmministrativo + "'" +
                ",\n         codGestoreFonte:'" + codGestoreFonte + "'" +
                ",\n         desSitoNatura2000:'" + desSitoNatura2000 + "'" +
                ",\n         numDistanza:" + numDistanza +
                ",\n         flgRicade:" + flgRicade +
                ",\n         desElementoDiscontinuita:'" + desElementoDiscontinuita + "'" +
                "}\n";
    }
}