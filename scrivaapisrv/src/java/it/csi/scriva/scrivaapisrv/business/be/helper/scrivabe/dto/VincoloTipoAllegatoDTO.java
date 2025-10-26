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
import java.util.Objects;

/**
 * The type Vincolo tipo allegato dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VincoloTipoAllegatoDTO implements Serializable {

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    @JsonProperty("id_vincolo_autorizza")
    private Long idVincoloAutorizza;

    @JsonProperty("id_tipologia_allegato")
    private Long idTipologiaAllegato;

    @JsonProperty("flg_obbligo")
    private Boolean flgObbligo;

    @JsonProperty("flg_firma_digitale")
    private Boolean flgFirmaDigitale;

    @JsonProperty("flg_riservato")
    private Boolean flgRiservato;

    @JsonProperty("flg_nascondi_fo")
    private Boolean flgNascondiFo;

    @JsonProperty("flg_nota")
    private Boolean flgNota;

    @JsonProperty("flg_firma_non_valida_bloccante")
    private Boolean flgFirmaNonValidaBloccante;

    /**
     * Gets id adempimento.
     *
     * @return the id adempimento
     */
    public Long getIdAdempimento() {
        return idAdempimento;
    }

    /**
     * Sets id adempimento.
     *
     * @param idAdempimento the id adempimento
     */
    public void setIdAdempimento(Long idAdempimento) {
        this.idAdempimento = idAdempimento;
    }

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
     * Gets flg obbligo.
     *
     * @return the flg obbligo
     */
    public Boolean getFlgObbligo() {
        return flgObbligo;
    }

    /**
     * Sets flg obbligo.
     *
     * @param flgObbligo the flg obbligo
     */
    public void setFlgObbligo(Boolean flgObbligo) {
        this.flgObbligo = flgObbligo;
    }

    /**
     * Gets flg firma digitale.
     *
     * @return the flg firma digitale
     */
    public Boolean getFlgFirmaDigitale() {
        return flgFirmaDigitale;
    }

    /**
     * Sets flg firma digitale.
     *
     * @param flgFirmaDigitale the flg firma digitale
     */
    public void setFlgFirmaDigitale(Boolean flgFirmaDigitale) {
        this.flgFirmaDigitale = flgFirmaDigitale;
    }

    /**
     * Gets flg riservato.
     *
     * @return the flg riservato
     */
    public Boolean getFlgRiservato() {
        return flgRiservato;
    }

    /**
     * Sets flg riservato.
     *
     * @param flgRiservato the flg riservato
     */
    public void setFlgRiservato(Boolean flgRiservato) {
        this.flgRiservato = flgRiservato;
    }

    /**
     * Gets flg nascondi fo.
     *
     * @return the flg nascondi fo
     */
    public Boolean getFlgNascondiFo() {
        return flgNascondiFo;
    }

    /**
     * Sets flg nascondi fo.
     *
     * @param flgNascondiFo the flg nascondi fo
     */
    public void setFlgNascondiFo(Boolean flgNascondiFo) {
        this.flgNascondiFo = flgNascondiFo;
    }

    /**
     * Gets flg nota.
     *
     * @return the flg nota
     */
    public Boolean getFlgNota() {
        return flgNota;
    }

    /**
     * Sets flg nota.
     *
     * @param flgNota the flg nota
     */
    public void setFlgNota(Boolean flgNota) {
        this.flgNota = flgNota;
    }

    /**
     * Gets flg firma non valida bloccante.
     *
     * @return the flg firma non valida bloccante
     */
    public Boolean getFlgFirmaNonValidaBloccante() {
        return flgFirmaNonValidaBloccante;
    }

    /**
     * Sets flg firma non valida bloccante.
     *
     * @param flgFirmaNonValidaBloccante the flg firma non valida bloccante
     */
    public void setFlgFirmaNonValidaBloccante(Boolean flgFirmaNonValidaBloccante) {
        this.flgFirmaNonValidaBloccante = flgFirmaNonValidaBloccante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VincoloTipoAllegatoDTO that = (VincoloTipoAllegatoDTO) o;
        return Objects.equals(idAdempimento, that.idAdempimento) && Objects.equals(idVincoloAutorizza, that.idVincoloAutorizza) && Objects.equals(idTipologiaAllegato, that.idTipologiaAllegato) && Objects.equals(flgObbligo, that.flgObbligo) && Objects.equals(flgFirmaDigitale, that.flgFirmaDigitale) && Objects.equals(flgRiservato, that.flgRiservato) && Objects.equals(flgNascondiFo, that.flgNascondiFo) && Objects.equals(flgNota, that.flgNota) && Objects.equals(flgFirmaNonValidaBloccante, that.flgFirmaNonValidaBloccante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdempimento, idVincoloAutorizza, idTipologiaAllegato, flgObbligo, flgFirmaDigitale, flgRiservato, flgNascondiFo, flgNota, flgFirmaNonValidaBloccante);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VincoloTipoAllegatoDTO {");
        sb.append("         idAdempimento:").append(idAdempimento);
        sb.append(",         idVincoloAutorizza:").append(idVincoloAutorizza);
        sb.append(",         idTipologiaAllegato:").append(idTipologiaAllegato);
        sb.append(",         flgObbligo:").append(flgObbligo);
        sb.append(",         flgFirmaDigitale:").append(flgFirmaDigitale);
        sb.append(",         flgRiservato:").append(flgRiservato);
        sb.append(",         flgNascondiFo:").append(flgNascondiFo);
        sb.append(",         flgNota:").append(flgNota);
        sb.append(",         flgFirmaNonValidaBloccante:").append(flgFirmaNonValidaBloccante);
        sb.append("}");
        return sb.toString();
    }
}