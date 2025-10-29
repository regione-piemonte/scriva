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
 * The type Oggetto istanza categoria dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoIstanzaCategoriaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_oggetto_istanza")
    private Long idOggettoIstanza;

    @JsonProperty("id_categoria_oggetto")
    private Long idCategoriaOggetto;

    @JsonProperty("data_inizio_validita")
    private Date dataInizioValidita;

    @JsonProperty("data_fine_validita")
    private Date dataFineValidita;

    @JsonProperty("flg_cat_nuovo_oggetto")
    private Boolean flgCatNuovoOggetto;

    @JsonProperty("flg_cat_modifica_oggetto")
    private Boolean flgCatModificaOggetto;

    @JsonProperty("flg_cat_principale")
    private Boolean flgCatPrincipale;

    @JsonProperty("ordinamento_istanza_competenza")
    private Integer ordinamentoIstanzaCompetenza;

    /**
     * Gets id oggetto istanza.
     *
     * @return idOggettoIstanza id oggetto istanza
     */
    public Long getIdOggettoIstanza() {
        return idOggettoIstanza;
    }

    /**
     * Sets id oggetto istanza.
     *
     * @param idOggettoIstanza idOggettoIstanza
     */
    public void setIdOggettoIstanza(Long idOggettoIstanza) {
        this.idOggettoIstanza = idOggettoIstanza;
    }

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
     * Gets flg cat nuovo oggetto.
     *
     * @return flgACatNuovoOggetto flg cat nuovo oggetto
     */
    public Boolean getFlgCatNuovoOggetto() {
        return flgCatNuovoOggetto;
    }

    /**
     * Sets flg cat nuovo oggetto.
     *
     * @param flgCatNuovoOggetto flgACatNuovoOggetto
     */
    public void setFlgCatNuovoOggetto(Boolean flgCatNuovoOggetto) {
        this.flgCatNuovoOggetto = flgCatNuovoOggetto;
    }

    /**
     * Gets flg cat modifica oggetto.
     *
     * @return flgCatModificaOggetto flg cat modifica oggetto
     */
    public Boolean getFlgCatModificaOggetto() {
        return flgCatModificaOggetto;
    }

    /**
     * Sets flg cat modifica oggetto.
     *
     * @param flgCatModificaOggetto flgCatModificaOggetto
     */
    public void setFlgCatModificaOggetto(Boolean flgCatModificaOggetto) {
        this.flgCatModificaOggetto = flgCatModificaOggetto;
    }

    /**
     * Gets flg cat principale.
     *
     * @return flgCatPrincipale flg cat principale
     */
    public Boolean getFlgCatPrincipale() {
        return flgCatPrincipale;
    }

    /**
     * Sets flg cat principale.
     *
     * @param flgCatPrincipale flgCatPrincipale
     */
    public void setFlgCatPrincipale(Boolean flgCatPrincipale) {
        this.flgCatPrincipale = flgCatPrincipale;
    }

    /**
     * Gets ordinamento istanza competenza.
     *
     * @return ordinamentoIstanzaCompetenza ordinamento istanza competenza
     */
    public Integer getOrdinamentoIstanzaCompetenza() {
        return ordinamentoIstanzaCompetenza;
    }

    /**
     * Sets ordinamento istanza competenza.
     *
     * @param ordinamentoIstanzaCompetenza ordinamentoIstanzaCompetenza
     */
    public void setOrdinamentoIstanzaCompetenza(Integer ordinamentoIstanzaCompetenza) {
        this.ordinamentoIstanzaCompetenza = ordinamentoIstanzaCompetenza;
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
        OggettoIstanzaCategoriaDTO that = (OggettoIstanzaCategoriaDTO) o;
        return Objects.equals(idOggettoIstanza, that.idOggettoIstanza) && Objects.equals(idCategoriaOggetto, that.idCategoriaOggetto) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataFineValidita, that.dataFineValidita) && Objects.equals(flgCatNuovoOggetto, that.flgCatNuovoOggetto) && Objects.equals(flgCatModificaOggetto, that.flgCatModificaOggetto) && Objects.equals(flgCatPrincipale, that.flgCatPrincipale) && Objects.equals(ordinamentoIstanzaCompetenza, that.ordinamentoIstanzaCompetenza);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idOggettoIstanza, idCategoriaOggetto, dataInizioValidita, dataFineValidita, flgCatNuovoOggetto, flgCatModificaOggetto, flgCatPrincipale, ordinamentoIstanzaCompetenza);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoIstanzaCategoriaDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         idOggettoIstanza:").append(idOggettoIstanza);
        sb.append(",         idCategoriaOggetto:").append(idCategoriaOggetto);
        sb.append(",         dataInizioValidita:").append(dataInizioValidita);
        sb.append(",         dataFineValidita:").append(dataFineValidita);
        sb.append(",         flgACatNuovoOggetto:").append(flgCatNuovoOggetto);
        sb.append(",         flgCatModificaOggetto:").append(flgCatModificaOggetto);
        sb.append(",         flgCatPrincipale:").append(flgCatPrincipale);
        sb.append(",         ordinamentoIstanzaCompetenza:").append(ordinamentoIstanzaCompetenza);
        sb.append("}");
        return sb.toString();
    }
}