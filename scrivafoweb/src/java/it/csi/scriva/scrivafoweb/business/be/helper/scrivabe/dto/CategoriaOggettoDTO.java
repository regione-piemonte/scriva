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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author CSI PIEMONTE
 */
public class CategoriaOggettoDTO implements Serializable {

    @JsonProperty("id_categoria_oggetto")
    private Long idCategoriaOggetto;

    @JsonProperty("id_tipologia_oggetto")
    private Long idTipologiaOggetto;

    @JsonProperty("cod_categoria_oggetto")
    private String codCategoriaOggetto;

    @JsonProperty("des_categoria_oggetto")
    private String desCategoriaOggetto;

    @JsonProperty("des_categoria_oggetto_estesa")
    private String desCategoriaOggettoEstesa;

    @JsonProperty("ordinamento_categoria_oggetto")
    private Integer ordinamentoCategoriaOggetto;

    @JsonProperty("data_inizio_validita")
    private Date dataInizioValidita;

    @JsonProperty("data_fine_validita")
    private Date dataFineValidita;

    @JsonProperty("ind_visibile")
    private String indVisibile;

    /**
     * @return idCategoriaOggetto
     */
    public Long getIdCategoriaOggetto() {
        return idCategoriaOggetto;
    }

    /**
     * @param idCategoriaOggetto idCategoriaOggetto
     */
    public void setIdCategoriaOggetto(Long idCategoriaOggetto) {
        this.idCategoriaOggetto = idCategoriaOggetto;
    }

    /**
     * @return idTipologiaOggetto
     */
    public Long getIdTipologiaOggetto() {
        return idTipologiaOggetto;
    }

    /**
     * @param idTipologiaOggetto idTipologiaOggetto
     */
    public void setIdTipologiaOggetto(Long idTipologiaOggetto) {
        this.idTipologiaOggetto = idTipologiaOggetto;
    }

    /**
     * @return codCategoriaOggetto
     */
    public String getCodCategoriaOggetto() {
        return codCategoriaOggetto;
    }

    /**
     * @param codCategoriaOggetto codCategoriaOggetto
     */
    public void setCodCategoriaOggetto(String codCategoriaOggetto) {
        this.codCategoriaOggetto = codCategoriaOggetto;
    }

    /**
     * @return desCategoriaOggetto
     */
    public String getDesCategoriaOggetto() {
        return desCategoriaOggetto;
    }

    /**
     * @param desCategoriaOggetto desCategoriaOggetto
     */
    public void setDesCategoriaOggetto(String desCategoriaOggetto) {
        this.desCategoriaOggetto = desCategoriaOggetto;
    }

    /**
     * @return desCategoriaOggettoEstesa
     */
    public String getDesCategoriaOggettoEstesa() {
        return desCategoriaOggettoEstesa;
    }

    /**
     * @param desCategoriaOggettoEstesa desCategoriaOggettoEstesa
     */
    public void setDesCategoriaOggettoEstesa(String desCategoriaOggettoEstesa) {
        this.desCategoriaOggettoEstesa = desCategoriaOggettoEstesa;
    }

    /**
     * @return ordinamentoCategoriaOggetto
     */
    public Integer getOrdinamentoCategoriaOggetto() {
        return ordinamentoCategoriaOggetto;
    }

    /**
     * @param ordinamentoCategoriaOggetto ordinamentoCategoriaOggetto
     */
    public void setOrdinamentoCategoriaOggetto(Integer ordinamentoCategoriaOggetto) {
        this.ordinamentoCategoriaOggetto = ordinamentoCategoriaOggetto;
    }

    /**
     * @return dataInizioValidita
     */
    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * @param dataInizioValidita dataInizioValidita
     */
    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    /**
     * @return dataFineValidita
     */
    public Date getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * @param dataFineValidita dataFineValidita
     */
    public void setDataFineValidita(Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    /**
     * @return indVisibile
     */
    public String getIndVisibile() {
        return indVisibile;
    }

    /**
     * @param indVisibile indVisibile
     */
    public void setIndVisibile(String indVisibile) {
        this.indVisibile = indVisibile;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaOggettoDTO that = (CategoriaOggettoDTO) o;
        return Objects.equals(idCategoriaOggetto, that.idCategoriaOggetto) && Objects.equals(idTipologiaOggetto, that.idTipologiaOggetto) && Objects.equals(codCategoriaOggetto, that.codCategoriaOggetto) && Objects.equals(desCategoriaOggetto, that.desCategoriaOggetto) && Objects.equals(desCategoriaOggettoEstesa, that.desCategoriaOggettoEstesa) && Objects.equals(ordinamentoCategoriaOggetto, that.ordinamentoCategoriaOggetto) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataFineValidita, that.dataFineValidita) && Objects.equals(indVisibile, that.indVisibile);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idCategoriaOggetto, idTipologiaOggetto, codCategoriaOggetto, desCategoriaOggetto, desCategoriaOggettoEstesa, ordinamentoCategoriaOggetto, dataInizioValidita, dataFineValidita, indVisibile);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CategoriaOggettoDTO {");
        sb.append("         idCategoriaOggetto:").append(idCategoriaOggetto);
        sb.append(",         idTipologiaOggetto:").append(idTipologiaOggetto);
        sb.append(",         codCategoriaOggetto:'").append(codCategoriaOggetto).append("'");
        sb.append(",         desCategoriaOggetto:'").append(desCategoriaOggetto).append("'");
        sb.append(",         desCategoriaOggettoEstesa:'").append(desCategoriaOggettoEstesa).append("'");
        sb.append(",         ordinamentoCategoriaOggetto:").append(ordinamentoCategoriaOggetto);
        sb.append(",         dataInizioValidita:").append(dataInizioValidita);
        sb.append(",         dataFineValidita:").append(dataFineValidita);
        sb.append(",         indVisibile:'").append(indVisibile).append("'");
        sb.append("}");
        return sb.toString();
    }
}