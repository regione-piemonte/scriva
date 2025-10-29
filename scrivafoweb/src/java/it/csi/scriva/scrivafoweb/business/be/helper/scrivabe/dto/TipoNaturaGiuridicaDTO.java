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
import java.util.Objects;

/**
 * The type Tipo natura giuridica dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TipoNaturaGiuridicaDTO implements Serializable {

    @JsonProperty("id_tipo_natura_giuridica")
    private Long idTipoNaturaGiuridica;

    @JsonProperty("cod_tipo_natura_giuridica")
    private String codiceTipoNaturaGiuridica;

    @JsonProperty("des_tipo_natura_giuridica")
    private String descrizioneTipoNaturaGiuridica;

    @JsonProperty("sigla_tipo_natura_giuridica")
    private String siglaTipoNaturaGiuridica;

    @JsonProperty("ordinamento_tipo_natura_giu")
    private Long ordinamentoTipoNaturaGiuridica;

    @JsonProperty("flg_pubblico")
    private Boolean flgPubblico;

    /**
     * Gets id tipo natura giuridica.
     *
     * @return the id tipo natura giuridica
     */
    public Long getIdTipoNaturaGiuridica() {
        return idTipoNaturaGiuridica;
    }

    /**
     * Sets id tipo natura giuridica.
     *
     * @param idTipoNaturaGiuridica the id tipo natura giuridica
     */
    public void setIdTipoNaturaGiuridica(Long idTipoNaturaGiuridica) {
        this.idTipoNaturaGiuridica = idTipoNaturaGiuridica;
    }

    /**
     * Gets codice tipo natura giuridica.
     *
     * @return the codice tipo natura giuridica
     */
    public String getCodiceTipoNaturaGiuridica() {
        return codiceTipoNaturaGiuridica;
    }

    /**
     * Sets codice tipo natura giuridica.
     *
     * @param codiceTipoNaturaGiuridica the codice tipo natura giuridica
     */
    public void setCodiceTipoNaturaGiuridica(String codiceTipoNaturaGiuridica) {
        this.codiceTipoNaturaGiuridica = codiceTipoNaturaGiuridica;
    }

    /**
     * Gets descrizione tipo natura giuridica.
     *
     * @return the descrizione tipo natura giuridica
     */
    public String getDescrizioneTipoNaturaGiuridica() {
        return descrizioneTipoNaturaGiuridica;
    }

    /**
     * Sets descrizione tipo natura giuridica.
     *
     * @param descrizioneTipoNaturaGiuridica the descrizione tipo natura giuridica
     */
    public void setDescrizioneTipoNaturaGiuridica(String descrizioneTipoNaturaGiuridica) {
        this.descrizioneTipoNaturaGiuridica = descrizioneTipoNaturaGiuridica;
    }

    /**
     * Gets sigla tipo natura giuridica.
     *
     * @return the sigla tipo natura giuridica
     */
    public String getSiglaTipoNaturaGiuridica() {
        return siglaTipoNaturaGiuridica;
    }

    /**
     * Sets sigla tipo natura giuridica.
     *
     * @param siglaTipoNaturaGiuridica the sigla tipo natura giuridica
     */
    public void setSiglaTipoNaturaGiuridica(String siglaTipoNaturaGiuridica) {
        this.siglaTipoNaturaGiuridica = siglaTipoNaturaGiuridica;
    }

    /**
     * Gets ordinamento tipo natura giuridica.
     *
     * @return the ordinamento tipo natura giuridica
     */
    public Long getOrdinamentoTipoNaturaGiuridica() {
        return ordinamentoTipoNaturaGiuridica;
    }

    /**
     * Sets ordinamento tipo natura giuridica.
     *
     * @param ordinamentoTipoNaturaGiuridica the ordinamento tipo natura giuridica
     */
    public void setOrdinamentoTipoNaturaGiuridica(Long ordinamentoTipoNaturaGiuridica) {
        this.ordinamentoTipoNaturaGiuridica = ordinamentoTipoNaturaGiuridica;
    }

    /**
     * Gets flg pubblico.
     *
     * @return the flg pubblico
     */
    public Boolean getFlgPubblico() {
        return flgPubblico;
    }

    /**
     * Sets flg pubblico.
     *
     * @param flgPubblico the flg pubblico
     */
    public void setFlgPubblico(Boolean flgPubblico) {
        this.flgPubblico = flgPubblico;
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
        TipoNaturaGiuridicaDTO that = (TipoNaturaGiuridicaDTO) o;
        return Objects.equals(idTipoNaturaGiuridica, that.idTipoNaturaGiuridica) && Objects.equals(codiceTipoNaturaGiuridica, that.codiceTipoNaturaGiuridica) && Objects.equals(descrizioneTipoNaturaGiuridica, that.descrizioneTipoNaturaGiuridica) && Objects.equals(siglaTipoNaturaGiuridica, that.siglaTipoNaturaGiuridica) && Objects.equals(ordinamentoTipoNaturaGiuridica, that.ordinamentoTipoNaturaGiuridica) && Objects.equals(flgPubblico, that.flgPubblico);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idTipoNaturaGiuridica, codiceTipoNaturaGiuridica, descrizioneTipoNaturaGiuridica, siglaTipoNaturaGiuridica, ordinamentoTipoNaturaGiuridica, flgPubblico);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoNaturaGiuridicaDTO {\n");
        sb.append("         idTipoNaturaGiuridica:").append(idTipoNaturaGiuridica);
        sb.append(",\n         codiceTipoNaturaGiuridica:'").append(codiceTipoNaturaGiuridica).append("'");
        sb.append(",\n         descrizioneTipoNaturaGiuridica:'").append(descrizioneTipoNaturaGiuridica).append("'");
        sb.append(",\n         siglaTipoNaturaGiuridica:'").append(siglaTipoNaturaGiuridica).append("'");
        sb.append(",\n         ordinamentoTipoNaturaGiuridica:").append(ordinamentoTipoNaturaGiuridica);
        sb.append(",\n         flgPubblico:").append(flgPubblico);
        sb.append("}\n");
        return sb.toString();
    }
}