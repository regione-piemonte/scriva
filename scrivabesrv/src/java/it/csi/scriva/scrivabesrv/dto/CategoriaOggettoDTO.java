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
import java.util.Date;
import java.util.Objects;

/**
 * The type Categoria oggetto dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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
     * Gets id categoria oggetto.
     *
     * @return idCategoriaOggetto id categoria oggetto
     */
    public Long getIdCategoriaOggetto() {
        return idCategoriaOggetto;
    }

    /**
     * Sets id categoria oggetto.
     *
     * @param idCategoriaOggetto idCategoriaOggetto
     */
    public void setIdCategoriaOggetto(Long idCategoriaOggetto) {
        this.idCategoriaOggetto = idCategoriaOggetto;
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
     * Gets cod categoria oggetto.
     *
     * @return codCategoriaOggetto cod categoria oggetto
     */
    public String getCodCategoriaOggetto() {
        return codCategoriaOggetto;
    }

    /**
     * Sets cod categoria oggetto.
     *
     * @param codCategoriaOggetto codCategoriaOggetto
     */
    public void setCodCategoriaOggetto(String codCategoriaOggetto) {
        this.codCategoriaOggetto = codCategoriaOggetto;
    }

    /**
     * Gets des categoria oggetto.
     *
     * @return desCategoriaOggetto des categoria oggetto
     */
    public String getDesCategoriaOggetto() {
        return desCategoriaOggetto;
    }

    /**
     * Sets des categoria oggetto.
     *
     * @param desCategoriaOggetto desCategoriaOggetto
     */
    public void setDesCategoriaOggetto(String desCategoriaOggetto) {
        this.desCategoriaOggetto = desCategoriaOggetto;
    }

    /**
     * Gets des categoria oggetto estesa.
     *
     * @return desCategoriaOggettoEstesa des categoria oggetto estesa
     */
    public String getDesCategoriaOggettoEstesa() {
        return desCategoriaOggettoEstesa;
    }

    /**
     * Sets des categoria oggetto estesa.
     *
     * @param desCategoriaOggettoEstesa desCategoriaOggettoEstesa
     */
    public void setDesCategoriaOggettoEstesa(String desCategoriaOggettoEstesa) {
        this.desCategoriaOggettoEstesa = desCategoriaOggettoEstesa;
    }

    /**
     * Gets ordinamento categoria oggetto.
     *
     * @return ordinamentoCategoriaOggetto ordinamento categoria oggetto
     */
    public Integer getOrdinamentoCategoriaOggetto() {
        return ordinamentoCategoriaOggetto;
    }

    /**
     * Sets ordinamento categoria oggetto.
     *
     * @param ordinamentoCategoriaOggetto ordinamentoCategoriaOggetto
     */
    public void setOrdinamentoCategoriaOggetto(Integer ordinamentoCategoriaOggetto) {
        this.ordinamentoCategoriaOggetto = ordinamentoCategoriaOggetto;
    }

    /**
     * Gets data inizio validita.
     *
     * @return dataInizioValidita data inizio validita
     */
    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Sets data inizio validita.
     *
     * @param dataInizioValidita dataInizioValidita
     */
    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    /**
     * Gets data fine validita.
     *
     * @return dataFineValidita data fine validita
     */
    public Date getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Sets data fine validita.
     *
     * @param dataFineValidita dataFineValidita
     */
    public void setDataFineValidita(Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    /**
     * Gets ind visibile.
     *
     * @return indVisibile ind visibile
     */
    public String getIndVisibile() {
        return indVisibile;
    }

    /**
     * Sets ind visibile.
     *
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