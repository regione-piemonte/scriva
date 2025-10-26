/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.TipoQuadroDTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Quadro fo dto.
 *
 * @author CSI PIEMONTE
 */
public class QuadroFoDTO implements Serializable {

    @JsonProperty("id_template_quadro")
    private Long idTemplateQuadro;

    @JsonProperty("id_quadro")
    private Long idQuadro;

    @JsonProperty("tipo_quadro")
    private TipoQuadroDTO tipoQuadro;

    @JsonProperty("num_versione")
    private Integer numVersione;

    @JsonProperty("flg_tipo_gestione")
    private String flgTipoGestione;

    @JsonProperty("json_configura_quadro")
    private String jsonConfiguraQuadro;

    @JsonProperty("ordinamento_template_quadro")
    private Integer ordinamentoTemplateQuadro;

    @JsonProperty("json_data_quadro")
    private String jsonDataQuadro;

    @JsonProperty("json_configura_riepilogo")
    private String jsonConfiguraRiepilogo;

    /**
     * Gets id template quadro.
     *
     * @return the id template quadro
     */
    public Long getIdTemplateQuadro() {
        return idTemplateQuadro;
    }

    /**
     * Sets id template quadro.
     *
     * @param idTemplateQuadro the id template quadro
     */
    public void setIdTemplateQuadro(Long idTemplateQuadro) {
        this.idTemplateQuadro = idTemplateQuadro;
    }

    /**
     * Gets id quadro.
     *
     * @return the id quadro
     */
    public Long getIdQuadro() {
        return idQuadro;
    }

    /**
     * Sets id quadro.
     *
     * @param idQuadro the id quadro
     */
    public void setIdQuadro(Long idQuadro) {
        this.idQuadro = idQuadro;
    }

    /**
     * Gets tipo quadro.
     *
     * @return the tipo quadro
     */
    public TipoQuadroDTO getTipoQuadro() {
        return tipoQuadro;
    }

    /**
     * Sets tipo quadro.
     *
     * @param tipoQuadro the tipo quadro
     */
    public void setTipoQuadro(TipoQuadroDTO tipoQuadro) {
        this.tipoQuadro = tipoQuadro;
    }

    /**
     * Gets num versione.
     *
     * @return the num versione
     */
    public Integer getNumVersione() {
        return numVersione;
    }

    /**
     * Sets num versione.
     *
     * @param numVersione the num versione
     */
    public void setNumVersione(Integer numVersione) {
        this.numVersione = numVersione;
    }

    /**
     * Gets flg tipo gestione.
     *
     * @return the flg tipo gestione
     */
    public String getFlgTipoGestione() {
        return flgTipoGestione;
    }

    /**
     * Sets flg tipo gestione.
     *
     * @param flgTipoGestione the flg tipo gestione
     */
    public void setFlgTipoGestione(String flgTipoGestione) {
        this.flgTipoGestione = flgTipoGestione;
    }

    /**
     * Gets json configura quadro.
     *
     * @return the json configura quadro
     */
    public String getJsonConfiguraQuadro() {
        return jsonConfiguraQuadro;
    }

    /**
     * Sets json configura quadro.
     *
     * @param jsonConfiguraQuadro the json configura quadro
     */
    public void setJsonConfiguraQuadro(String jsonConfiguraQuadro) {
        this.jsonConfiguraQuadro = jsonConfiguraQuadro;
    }

    /**
     * Gets ordinamento template quadro.
     *
     * @return the ordinamento template quadro
     */
    public Integer getOrdinamentoTemplateQuadro() {
        return ordinamentoTemplateQuadro;
    }

    /**
     * Sets ordinamento template quadro.
     *
     * @param ordinamentoTemplateQuadro the ordinamento template quadro
     */
    public void setOrdinamentoTemplateQuadro(Integer ordinamentoTemplateQuadro) {
        this.ordinamentoTemplateQuadro = ordinamentoTemplateQuadro;
    }

    /**
     * Gets json data quadro.
     *
     * @return the json data quadro
     */
    public String getJsonDataQuadro() {
        return jsonDataQuadro;
    }

    /**
     * Sets json data quadro.
     *
     * @param jsonDataQuadro the json data quadro
     */
    public void setJsonDataQuadro(String jsonDataQuadro) {
        this.jsonDataQuadro = jsonDataQuadro;
    }

    /**
     * Gets json configura riepilogo.
     *
     * @return the json configura riepilogo
     */
    public String getJsonConfiguraRiepilogo() {
        return jsonConfiguraRiepilogo;
    }

    /**
     * Sets json configura riepilogo.
     *
     * @param jsonConfiguraRiepilogo the json configura riepilogo
     */
    public void setJsonConfiguraRiepilogo(String jsonConfiguraRiepilogo) {
        this.jsonConfiguraRiepilogo = jsonConfiguraRiepilogo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuadroFoDTO that = (QuadroFoDTO) o;
        return Objects.equals(idTemplateQuadro, that.idTemplateQuadro) && Objects.equals(idQuadro, that.idQuadro) && Objects.equals(tipoQuadro, that.tipoQuadro) && Objects.equals(numVersione, that.numVersione) && Objects.equals(flgTipoGestione, that.flgTipoGestione) && Objects.equals(jsonConfiguraQuadro, that.jsonConfiguraQuadro) && Objects.equals(ordinamentoTemplateQuadro, that.ordinamentoTemplateQuadro) && Objects.equals(jsonDataQuadro, that.jsonDataQuadro) && Objects.equals(jsonConfiguraRiepilogo, that.jsonConfiguraRiepilogo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTemplateQuadro, idQuadro, tipoQuadro, numVersione, flgTipoGestione, jsonConfiguraQuadro, ordinamentoTemplateQuadro, jsonDataQuadro, jsonConfiguraRiepilogo);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QuadroFoDTO {");
        sb.append("         idTemplateQuadro:").append(idTemplateQuadro);
        sb.append(",         idQuadro:").append(idQuadro);
        sb.append(",         tipoQuadro:").append(tipoQuadro);
        sb.append(",         numVersione:").append(numVersione);
        sb.append(",         flgTipoGestione:'").append(flgTipoGestione).append("'");
        sb.append(",         jsonConfiguraQuadro:'").append(jsonConfiguraQuadro).append("'");
        sb.append(",         ordinamentoTemplateQuadro:").append(ordinamentoTemplateQuadro);
        sb.append(",         jsonDataQuadro:'").append(jsonDataQuadro).append("'");
        sb.append(",         jsonConfiguraRiepilogo:'").append(jsonConfiguraRiepilogo).append("'");
        sb.append("}");
        return sb.toString();
    }
}