/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tipologia allegato dto.
 *
 * @author CSI PIEMONTE
 */
public class TipologiaAllegatoDTO implements Serializable {

    @JsonProperty("id_tipologia_allegato")
    private Long idTipologiaAllegato;

    @JsonProperty("id_categoria_allegato")
    private Long idCategoriaAllegato;

    @JsonProperty("cod_tipologia_allegato")
    private String codTipologiaAllegato;

    @JsonProperty("des_tipologia_allegato")
    private String desTipologiaAllegato;

    @JsonProperty("ordinamento_tipologia_allegato")
    private Integer ordinamentoTipologiaAllegato;

    @JsonProperty("flg_attivo")
    private Boolean flgAttivo;

    /**
     * Gets id tipologia allegato.
     *
     * @return the id tipologia allegato
     */
    public Long getIdTipologiaAllegato() {
        return idTipologiaAllegato;
    }

    /**
     * Sets id tipologia allegato.
     *
     * @param idTipologiaAllegato the id tipologia allegato
     */
    public void setIdTipologiaAllegato(Long idTipologiaAllegato) {
        this.idTipologiaAllegato = idTipologiaAllegato;
    }

    /**
     * Gets id categoria allegato.
     *
     * @return the id categoria allegato
     */
    public Long getIdCategoriaAllegato() {
        return idCategoriaAllegato;
    }

    /**
     * Sets id categoria allegato.
     *
     * @param idCategoriaAllegato the id categoria allegato
     */
    public void setIdCategoriaAllegato(Long idCategoriaAllegato) {
        this.idCategoriaAllegato = idCategoriaAllegato;
    }

    /**
     * Gets cod tipologia allegato.
     *
     * @return the cod tipologia allegato
     */
    public String getCodTipologiaAllegato() {
        return codTipologiaAllegato;
    }

    /**
     * Sets cod tipologia allegato.
     *
     * @param codTipologiaAllegato the cod tipologia allegato
     */
    public void setCodTipologiaAllegato(String codTipologiaAllegato) {
        this.codTipologiaAllegato = codTipologiaAllegato;
    }

    /**
     * Gets des tipologia allegato.
     *
     * @return the des tipologia allegato
     */
    public String getDesTipologiaAllegato() {
        return desTipologiaAllegato;
    }

    /**
     * Sets des tipologia allegato.
     *
     * @param desTipologiaAllegato the des tipologia allegato
     */
    public void setDesTipologiaAllegato(String desTipologiaAllegato) {
        this.desTipologiaAllegato = desTipologiaAllegato;
    }

    /**
     * Gets ordinamento tipologia allegato.
     *
     * @return the ordinamento tipologia allegato
     */
    public Integer getOrdinamentoTipologiaAllegato() {
        return ordinamentoTipologiaAllegato;
    }

    /**
     * Sets ordinamento tipologia allegato.
     *
     * @param ordinamentoTipologiaAllegato the ordinamento tipologia allegato
     */
    public void setOrdinamentoTipologiaAllegato(Integer ordinamentoTipologiaAllegato) {
        this.ordinamentoTipologiaAllegato = ordinamentoTipologiaAllegato;
    }

    /**
     * Gets flg attivo.
     *
     * @return the flg attivo
     */
    public Boolean getFlgAttivo() {
        return flgAttivo;
    }

    /**
     * Sets flg attivo.
     *
     * @param flgAttivo the flg attivo
     */
    public void setFlgAttivo(Boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }

    /**
     * @param o object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TipologiaAllegatoDTO that = (TipologiaAllegatoDTO) o;
        return Objects.equals(idTipologiaAllegato, that.idTipologiaAllegato) && Objects.equals(idCategoriaAllegato, that.idCategoriaAllegato) && Objects.equals(codTipologiaAllegato, that.codTipologiaAllegato) && Objects.equals(desTipologiaAllegato, that.desTipologiaAllegato) && Objects.equals(ordinamentoTipologiaAllegato, that.ordinamentoTipologiaAllegato) && Objects.equals(flgAttivo, that.flgAttivo);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTipologiaAllegato, idCategoriaAllegato, codTipologiaAllegato, desTipologiaAllegato, ordinamentoTipologiaAllegato, flgAttivo);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipologiaAllegatoDTO {");
        sb.append("         idTipologiaAllegato:").append(idTipologiaAllegato);
        sb.append(",         idCategoriaAllegato:").append(idCategoriaAllegato);
        sb.append(",         codTipologiaAllegato:'").append(codTipologiaAllegato).append("'");
        sb.append(",         desTipologiaAllegato:'").append(desTipologiaAllegato).append("'");
        sb.append(",         ordinamentoTipologiaAllegato:'").append(ordinamentoTipologiaAllegato).append("'");
        sb.append(",         flgAttivo:").append(flgAttivo);
        sb.append("}");
        return sb.toString();
    }
}