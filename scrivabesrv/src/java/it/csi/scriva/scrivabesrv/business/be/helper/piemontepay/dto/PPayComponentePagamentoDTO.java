/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * The type P pay componente pagamento dto.
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class PPayComponentePagamentoDTO {

    private Long progressivo;

    private BigDecimal importo;

    private String causale;

    private String datiSpecificiRiscossione;

    private Integer annoAccertamento;

    private String numeroAccertamento;

    /**
     * Gets progressivo.
     *
     * @return progressivo progressivo
     */
    public Long getProgressivo() {
        return progressivo;
    }

    /**
     * Sets progressivo.
     *
     * @param progressivo progressivo
     */
    public void setProgressivo(Long progressivo) {
        this.progressivo = progressivo;
    }

    /**
     * Gets importo.
     *
     * @return importo importo
     */
    public BigDecimal getImporto() {
        return importo;
    }

    /**
     * Sets importo.
     *
     * @param importo importo
     */
    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    /**
     * Gets causale.
     *
     * @return causale causale
     */
    public String getCausale() {
        return causale;
    }

    /**
     * Sets causale.
     *
     * @param causale causale
     */
    public void setCausale(String causale) {
        this.causale = causale;
    }

    /**
     * Gets dati specifici riscossione.
     *
     * @return datiSpecificiRiscossione dati specifici riscossione
     */
    public String getDatiSpecificiRiscossione() {
        return datiSpecificiRiscossione;
    }

    /**
     * Sets dati specifici riscossione.
     *
     * @param datiSpecificiRiscossione datiSpecificiRiscossione
     */
    public void setDatiSpecificiRiscossione(String datiSpecificiRiscossione) {
        this.datiSpecificiRiscossione = datiSpecificiRiscossione;
    }

    /**
     * Gets anno accertamento.
     *
     * @return annoAccertamento anno accertamento
     */
    public Integer getAnnoAccertamento() {
        return annoAccertamento;
    }

    /**
     * Sets anno accertamento.
     *
     * @param annoAccertamento annoAccertamento
     */
    public void setAnnoAccertamento(Integer annoAccertamento) {
        this.annoAccertamento = annoAccertamento;
    }

    /**
     * Gets numero accertamento.
     *
     * @return numeroAccertamento numero accertamento
     */
    public String getNumeroAccertamento() {
        return numeroAccertamento;
    }

    /**
     * Sets numero accertamento.
     *
     * @param numeroAccertamento numeroAccertamento
     */
    public void setNumeroAccertamento(String numeroAccertamento) {
        this.numeroAccertamento = numeroAccertamento;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PPayComponentePagamentoDTO that = (PPayComponentePagamentoDTO) o;
        return Objects.equals(progressivo, that.progressivo) && Objects.equals(importo, that.importo) && Objects.equals(causale, that.causale) && Objects.equals(datiSpecificiRiscossione, that.datiSpecificiRiscossione) && Objects.equals(annoAccertamento, that.annoAccertamento) && Objects.equals(numeroAccertamento, that.numeroAccertamento);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(progressivo, importo, causale, datiSpecificiRiscossione, annoAccertamento, numeroAccertamento);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PPayComponentiPagamentoDTO {");
        sb.append("         progressivo:").append(progressivo);
        sb.append(",         importo:").append(importo);
        sb.append(",         causale:'").append(causale).append("'");
        sb.append(",         datiSpecificiRiscossione:'").append(datiSpecificiRiscossione).append("'");
        sb.append(",         annoAccertamento:").append(annoAccertamento);
        sb.append(",         numeroAccertamento:'").append(numeroAccertamento).append("'");
        sb.append("}");
        return sb.toString();
    }
}