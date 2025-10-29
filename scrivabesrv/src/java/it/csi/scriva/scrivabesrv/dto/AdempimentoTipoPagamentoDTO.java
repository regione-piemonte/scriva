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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * The type Adempimento tipo pagamento dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdempimentoTipoPagamentoDTO implements Serializable {

    @JsonProperty("id_adempi_tipo_pagamento")
    private Long idAdempiTipoPagamento;

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    @JsonProperty("id_tipo_pagamento")
    private Long idTipoPagamento;

    @JsonProperty("id_ente_creditore")
    private Long idEnteCreditore;

    @JsonProperty("id_competenza_territorio")
    private Long idCompetenzaTerritorio;

    @JsonProperty("codice_versamento")
    private String codiceVersamento;

    @JsonProperty("importo_previsto")
    private BigDecimal importoPrevisto;

    @JsonProperty("giorni_max_attesa_rt")
    private Integer giorniMaxAttesaRt;

    @JsonProperty("ind_importo_pagamento")
    private String indImportoPagamento;

    @JsonIgnore
    private Boolean flgAttivo;

    /**
     * Gets id adempi tipo pagamento.
     *
     * @return the id adempi tipo pagamento
     */
    public Long getIdAdempiTipoPagamento() {
        return idAdempiTipoPagamento;
    }

    /**
     * Sets id adempi tipo pagamento.
     *
     * @param idAdempiTipoPagamento the id adempi tipo pagamento
     */
    public void setIdAdempiTipoPagamento(Long idAdempiTipoPagamento) {
        this.idAdempiTipoPagamento = idAdempiTipoPagamento;
    }

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
     * Gets id tipo pagamento.
     *
     * @return the id tipo pagamento
     */
    public Long getIdTipoPagamento() {
        return idTipoPagamento;
    }

    /**
     * Sets id tipo pagamento.
     *
     * @param idTipoPagamento the id tipo pagamento
     */
    public void setIdTipoPagamento(Long idTipoPagamento) {
        this.idTipoPagamento = idTipoPagamento;
    }

    /**
     * Gets id ente creditore.
     *
     * @return the id ente creditore
     */
    public Long getIdEnteCreditore() {
        return idEnteCreditore;
    }

    /**
     * Sets id ente creditore.
     *
     * @param idEnteCreditore the id ente creditore
     */
    public void setIdEnteCreditore(Long idEnteCreditore) {
        this.idEnteCreditore = idEnteCreditore;
    }

    /**
     * Gets id competenza territorio.
     *
     * @return the id competenza territorio
     */
    public Long getIdCompetenzaTerritorio() {
        return idCompetenzaTerritorio;
    }

    /**
     * Sets id competenza territorio.
     *
     * @param idCompetenzaTerritorio the id competenza territorio
     */
    public void setIdCompetenzaTerritorio(Long idCompetenzaTerritorio) {
        this.idCompetenzaTerritorio = idCompetenzaTerritorio;
    }

    /**
     * Gets codice versamento.
     *
     * @return the codice versamento
     */
    public String getCodiceVersamento() {
        return codiceVersamento;
    }

    /**
     * Sets codice versamento.
     *
     * @param codiceVersamento the codice versamento
     */
    public void setCodiceVersamento(String codiceVersamento) {
        this.codiceVersamento = codiceVersamento;
    }

    /**
     * Gets importo previsto.
     *
     * @return the importo previsto
     */
    public BigDecimal getImportoPrevisto() {
        return importoPrevisto;
    }

    /**
     * Sets importo previsto.
     *
     * @param importoPrevisto the importo previsto
     */
    public void setImportoPrevisto(BigDecimal importoPrevisto) {
        this.importoPrevisto = importoPrevisto;
    }

    /**
     * Gets giorni max attesa rt.
     *
     * @return the giorni max attesa rt
     */
    public Integer getGiorniMaxAttesaRt() {
        return giorniMaxAttesaRt;
    }

    /**
     * Sets giorni max attesa rt.
     *
     * @param giorniMaxAttesaRt the giorni max attesa rt
     */
    public void setGiorniMaxAttesaRt(Integer giorniMaxAttesaRt) {
        this.giorniMaxAttesaRt = giorniMaxAttesaRt;
    }

    /**
     * Gets ind importo pagamento.
     *
     * @return the ind importo pagamento
     */
    public String getIndImportoPagamento() {
        return indImportoPagamento;
    }

    /**
     * Sets ind importo pagamento.
     *
     * @param indImportoPagamento the ind importo pagamento
     */
    public void setIndImportoPagamento(String indImportoPagamento) {
        this.indImportoPagamento = indImportoPagamento;
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
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdempimentoTipoPagamentoDTO that = (AdempimentoTipoPagamentoDTO) o;
        return Objects.equals(idAdempiTipoPagamento, that.idAdempiTipoPagamento) && Objects.equals(idAdempimento, that.idAdempimento) && Objects.equals(idTipoPagamento, that.idTipoPagamento) && Objects.equals(idEnteCreditore, that.idEnteCreditore) && Objects.equals(idCompetenzaTerritorio, that.idCompetenzaTerritorio) && Objects.equals(codiceVersamento, that.codiceVersamento) && Objects.equals(importoPrevisto, that.importoPrevisto) && Objects.equals(giorniMaxAttesaRt, that.giorniMaxAttesaRt) && Objects.equals(indImportoPagamento, that.indImportoPagamento) && Objects.equals(flgAttivo, that.flgAttivo);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idAdempiTipoPagamento, idAdempimento, idTipoPagamento, idEnteCreditore, idCompetenzaTerritorio, codiceVersamento, importoPrevisto, giorniMaxAttesaRt, indImportoPagamento, flgAttivo);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdempimentoTipoPagamentoDTO {\n");
        sb.append("         idAdempiTipoPagamento:").append(idAdempiTipoPagamento);
        sb.append(",\n         idAdempimento:").append(idAdempimento);
        sb.append(",\n         idTipoPagamento:").append(idTipoPagamento);
        sb.append(",\n         idEnteCreditore:").append(idEnteCreditore);
        sb.append(",\n         idCompetenzaTerritorio:").append(idCompetenzaTerritorio);
        sb.append(",\n         codiceVersamento:'").append(codiceVersamento).append("'");
        sb.append(",\n         importoPrevisto:").append(importoPrevisto);
        sb.append(",\n         giorniMaxAttesaRt:").append(giorniMaxAttesaRt);
        sb.append(",\n         indImportoPagamento:'").append(indImportoPagamento).append("'");
        sb.append(",\n         flgAttivo:").append(flgAttivo);
        sb.append("}\n");
        return sb.toString();
    }
}