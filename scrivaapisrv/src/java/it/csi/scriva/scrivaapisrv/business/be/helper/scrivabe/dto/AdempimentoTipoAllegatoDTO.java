/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */

package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author CSI PIEMONTE
 */
public class AdempimentoTipoAllegatoDTO implements Serializable {

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    @JsonProperty("id_tipologia_allegato")
    private Long idTipologiaAllegato;

    @JsonProperty("flg_obbligo")
    private Boolean flgObbligo;

    @JsonProperty("flg_firma_digitale")
    private Boolean flgFirmaDigitale;

    @JsonProperty("flg_riservato")
    private Boolean flgRiservato;

    @JsonProperty("flg_nascondi_fo")
    private Boolean flgNascondiFO;

    @JsonProperty("flg_nota")
    private Boolean flgNota;

    @JsonProperty("flg_firma_non_valida_bloccante")
    private Boolean flgFirmaNonValidaBloccante;

    @JsonProperty("flg_integrazione")
    private Boolean flgIntegrazione;

    public Long getIdAdempimento() {
        return idAdempimento;
    }

    public void setIdAdempimento(Long idAdempimento) {
        this.idAdempimento = idAdempimento;
    }

    public Long getIdTipologiaAllegato() {
        return idTipologiaAllegato;
    }

    public void setIdTipologiaAllegato(Long idTipologiaAllegato) {
        this.idTipologiaAllegato = idTipologiaAllegato;
    }

    public Boolean getFlgObbligo() {
        return flgObbligo;
    }

    public void setFlgObbligo(Boolean flgObbligo) {
        this.flgObbligo = flgObbligo;
    }

    public Boolean getFlgFirmaDigitale() {
        return flgFirmaDigitale;
    }

    public void setFlgFirmaDigitale(Boolean flgFirmaDigitale) {
        this.flgFirmaDigitale = flgFirmaDigitale;
    }

    public Boolean getFlgRiservato() {
        return flgRiservato;
    }

    public void setFlgRiservato(Boolean flgRiservato) {
        this.flgRiservato = flgRiservato;
    }

    public Boolean getFlgNascondiFO() {
        return flgNascondiFO;
    }

    public void setFlgNascondiFO(Boolean flgNascondiFO) {
        this.flgNascondiFO = flgNascondiFO;
    }

    public Boolean getFlgNota() {
        return flgNota;
    }

    public void setFlgNota(Boolean flgNota) {
        this.flgNota = flgNota;
    }

    public Boolean getFlgFirmaNonValidaBloccante() {
        return flgFirmaNonValidaBloccante;
    }

    public void setFlgFirmaNonValidaBloccante(Boolean flgFirmaNonValidaBloccante) {
        this.flgFirmaNonValidaBloccante = flgFirmaNonValidaBloccante;
    }

    public Boolean getFlgIntegrazione() { return flgIntegrazione;}

    public void setFlgIntegrazione(Boolean flgIntegrazione) {
        this.flgIntegrazione = flgIntegrazione;
    }

    /**
     *
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdempimentoTipoAllegatoDTO that = (AdempimentoTipoAllegatoDTO) o;
        return Objects.equals(idAdempimento, that.idAdempimento) && Objects.equals(idTipologiaAllegato, that.idTipologiaAllegato) && Objects.equals(flgObbligo, that.flgObbligo) && Objects.equals(flgFirmaDigitale, that.flgFirmaDigitale) && Objects.equals(flgRiservato, that.flgRiservato) && Objects.equals(flgNascondiFO, that.flgNascondiFO) && Objects.equals(flgNota, that.flgNota) && Objects.equals(flgFirmaNonValidaBloccante, that.flgFirmaNonValidaBloccante) && Objects.equals(flgIntegrazione, that.flgIntegrazione);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idAdempimento, idTipologiaAllegato, flgObbligo, flgFirmaDigitale, flgRiservato, flgNascondiFO, flgNota, flgFirmaNonValidaBloccante, flgIntegrazione);
    }

    /**
     *
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdempimentoTipoAllegatoDTO {");
        sb.append("         idAdempimento:").append(idAdempimento);
        sb.append(",         idTipologiaAllegato:").append(idTipologiaAllegato);
        sb.append(",         flgObbligo:").append(flgObbligo);
        sb.append(",         flgFirmaDigitale:").append(flgFirmaDigitale);
        sb.append(",         flgRiservato:").append(flgRiservato);
        sb.append(",         flgNascondiFO:").append(flgNascondiFO);
        sb.append(",         flgNota:").append(flgNota);
        sb.append(",         flgFirmaNonValidaBloccante:").append(flgFirmaNonValidaBloccante);
        sb.append(",         flgIntegrazione:").append(flgIntegrazione);
        sb.append("}");
        return sb.toString();
    }
}