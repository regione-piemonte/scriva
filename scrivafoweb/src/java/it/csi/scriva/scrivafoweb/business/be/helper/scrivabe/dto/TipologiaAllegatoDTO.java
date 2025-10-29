/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tipologia allegato dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @JsonProperty("flg_atto")
    private Boolean flgAtto;
    
    @JsonProperty("flg_sistema")
    private Boolean flgSistema;

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
     * Gets flg atto.
     *
     * @return the flg atto
     */
    public Boolean getFlgAtto() {
        return flgAtto;
    }

    /**
     * Sets flg atto.
     *
     * @param flgAtto the flg atto
     */
    public void setFlgAtto(Boolean flgAtto) {
        this.flgAtto = flgAtto;
    }
    
    
    /**
     * Gets flg sistema.
     *
     * @return the flg sistema
     */
    public Boolean getFlgSistema() {
		return flgSistema;
	}

    /**
     * Sets flg sistema.
     *
     * @param flgSistema the flg sistema
     */
	public void setFlgSistema(Boolean flgSistema) {
		this.flgSistema = flgSistema;
	}

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipologiaAllegatoDTO that = (TipologiaAllegatoDTO) o;
        return Objects.equals(idTipologiaAllegato, that.idTipologiaAllegato) && Objects.equals(idCategoriaAllegato, that.idCategoriaAllegato) && Objects.equals(codTipologiaAllegato, that.codTipologiaAllegato) && Objects.equals(desTipologiaAllegato, that.desTipologiaAllegato) && Objects.equals(ordinamentoTipologiaAllegato, that.ordinamentoTipologiaAllegato) && Objects.equals(flgAttivo, that.flgAttivo) && Objects.equals(flgAtto, that.flgAtto) && Objects.equals(flgSistema, that.flgSistema);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTipologiaAllegato, idCategoriaAllegato, codTipologiaAllegato, desTipologiaAllegato, ordinamentoTipologiaAllegato, flgAttivo, flgAtto, flgSistema);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TipologiaAllegatoDTO {\n" +
                "         idTipologiaAllegato:" + idTipologiaAllegato +
                ",\n         idCategoriaAllegato:" + idCategoriaAllegato +
                ",\n         codTipologiaAllegato:'" + codTipologiaAllegato + "'" +
                ",\n         desTipologiaAllegato:'" + desTipologiaAllegato + "'" +
                ",\n         ordinamentoTipologiaAllegato:" + ordinamentoTipologiaAllegato +
                ",\n         flgAttivo:" + flgAttivo +
                ",\n         flgAtto:" + flgAtto +
                ",\n         flgSistema:" + flgSistema +
                "}\n";
    }
}