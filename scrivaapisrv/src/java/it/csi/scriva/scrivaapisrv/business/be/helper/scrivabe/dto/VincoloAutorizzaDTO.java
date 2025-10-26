/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * The type Tipo vincolo aut dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VincoloAutorizzaDTO implements Serializable {

    @JsonProperty("id_vincolo_autorizza")
    private Long idVincoloAutorizza;

    @JsonProperty("id_tipo_vincolo_aut")
    private Long idTipoVincoloAut;

    @JsonProperty("cod_vincolo_autorizza")
    private String codVincoloAutorizza;

    @JsonProperty("des_vincolo_autorizza")
    private String desVincoloAutorizza;

    @JsonProperty("des_rif_normativo")
    private String desRifNormativo;

    @JsonProperty("data_inizio_validita")
    private Date dataInizioValidita;

    @JsonProperty("data_fine_validita")
    private Date dataFineValidita;

    @JsonProperty("flg_modifica")
    private Boolean flgModifica;

    @JsonProperty("ind_visibile")
    private String indVisibile;

    @JsonProperty("ordinamento_vincolo_aut")
    private Long ordinamentoVincoloAut;

    /**
     * Gets id vincolo autorizza.
     *
     * @return the id vincolo autorizza
     */
    public Long getIdVincoloAutorizza() {
        return idVincoloAutorizza;
    }

    /**
     * Sets id vincolo autorizza.
     *
     * @param idVincoloAutorizza the id vincolo autorizza
     */
    public void setIdVincoloAutorizza(Long idVincoloAutorizza) {
        this.idVincoloAutorizza = idVincoloAutorizza;
    }

    /**
     * Gets id tipo vincolo aut.
     *
     * @return the id tipo vincolo aut
     */
    public Long getIdTipoVincoloAut() {
        return idTipoVincoloAut;
    }

    /**
     * Sets id tipo vincolo aut.
     *
     * @param idTipoVincoloAut the id tipo vincolo aut
     */
    public void setIdTipoVincoloAut(Long idTipoVincoloAut) {
        this.idTipoVincoloAut = idTipoVincoloAut;
    }

    /**
     * Gets cod vincolo autorizza.
     *
     * @return the cod vincolo autorizza
     */
    public String getCodVincoloAutorizza() {
        return codVincoloAutorizza;
    }

    /**
     * Sets cod vincolo autorizza.
     *
     * @param codVincoloAutorizza the cod vincolo autorizza
     */
    public void setCodVincoloAutorizza(String codVincoloAutorizza) {
        this.codVincoloAutorizza = codVincoloAutorizza;
    }

    /**
     * Gets des vincolo autorizza.
     *
     * @return the des vincolo autorizza
     */
    public String getDesVincoloAutorizza() {
        return desVincoloAutorizza;
    }

    /**
     * Sets des vincolo autorizza.
     *
     * @param desVincoloAutorizza the des vincolo autorizza
     */
    public void setDesVincoloAutorizza(String desVincoloAutorizza) {
        this.desVincoloAutorizza = desVincoloAutorizza;
    }

    /**
     * Gets des rif normativo.
     *
     * @return the des rif normativo
     */
    public String getDesRifNormativo() {
        return desRifNormativo;
    }

    /**
     * Sets des rif normativo.
     *
     * @param desRifNormativo the des rif normativo
     */
    public void setDesRifNormativo(String desRifNormativo) {
        this.desRifNormativo = desRifNormativo;
    }

    /**
     * Gets data inizio validita.
     *
     * @return the data inizio validita
     */
    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Sets data inizio validita.
     *
     * @param dataInizioValidita the data inizio validita
     */
    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    /**
     * Gets data fine validita.
     *
     * @return the data fine validita
     */
    public Date getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Sets data fine validita.
     *
     * @param dataFineValidita the data fine validita
     */
    public void setDataFineValidita(Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    /**
     * Gets flg modifica.
     *
     * @return the flg modifica
     */
    public Boolean getFlgModifica() {
        return flgModifica;
    }

    /**
     * Sets flg modifica.
     *
     * @param flgModifica the flg modifica
     */
    public void setFlgModifica(Boolean flgModifica) {
        this.flgModifica = flgModifica;
    }

    /**
     * Gets ind visibile.
     *
     * @return the ind visibile
     */
    public String getIndVisibile() {
        return indVisibile;
    }

    /**
     * Sets ind visibile.
     *
     * @param indVisibile the ind visibile
     */
    public void setIndVisibile(String indVisibile) {
        this.indVisibile = indVisibile;
    }

    /**
     * Gets ordinamento vincolo aut.
     *
     * @return the ordinamento vincolo aut
     */
    public Long getOrdinamentoVincoloAut() {
        return ordinamentoVincoloAut;
    }

    /**
     * Sets ordinamento vincolo aut.
     *
     * @param ordinamentoVincoloAut the ordinamento vincolo aut
     */
    public void setOrdinamentoVincoloAut(Long ordinamentoVincoloAut) {
        this.ordinamentoVincoloAut = ordinamentoVincoloAut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VincoloAutorizzaDTO that = (VincoloAutorizzaDTO) o;
        return Objects.equals(idVincoloAutorizza, that.idVincoloAutorizza) && Objects.equals(idTipoVincoloAut, that.idTipoVincoloAut) && Objects.equals(codVincoloAutorizza, that.codVincoloAutorizza) && Objects.equals(desVincoloAutorizza, that.desVincoloAutorizza) && Objects.equals(desRifNormativo, that.desRifNormativo) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataFineValidita, that.dataFineValidita) && Objects.equals(flgModifica, that.flgModifica) && Objects.equals(indVisibile, that.indVisibile) && Objects.equals(ordinamentoVincoloAut, that.ordinamentoVincoloAut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVincoloAutorizza, idTipoVincoloAut, codVincoloAutorizza, desVincoloAutorizza, desRifNormativo, dataInizioValidita, dataFineValidita, flgModifica, indVisibile, ordinamentoVincoloAut);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VincoloAutorizzaDTO {");
        sb.append("         idVincoloAutorizza:").append(idVincoloAutorizza);
        sb.append(",         idTipoVincoloAut:").append(idTipoVincoloAut);
        sb.append(",         codVincoloAutorizza:'").append(codVincoloAutorizza).append("'");
        sb.append(",         desVincoloAutorizza:'").append(desVincoloAutorizza).append("'");
        sb.append(",         desRifNormativo:'").append(desRifNormativo).append("'");
        sb.append(",         dataInizioValidita:").append(dataInizioValidita);
        sb.append(",         dataFineValidita:").append(dataFineValidita);
        sb.append(",         flgModifica:").append(flgModifica);
        sb.append(",         indVisibile:'").append(indVisibile).append("'");
        sb.append(",         ordinamentoVincoloAut:").append(ordinamentoVincoloAut);
        sb.append("}");
        return sb.toString();
    }
}