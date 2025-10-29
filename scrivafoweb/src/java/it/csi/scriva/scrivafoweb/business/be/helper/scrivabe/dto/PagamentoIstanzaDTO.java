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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * The type Pagamento istanza.
 *
 * @author CSI PIEMONTE
 */
public class PagamentoIstanzaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_pagamento_istanza")
    private Long idPagamentoIstanza;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_riscossione_ente")
    private Long idRiscossioneEnte;

    @JsonProperty("id_stato_pagamento")
    private Long idStatoPagamento;

    @JsonProperty("id_onere_padre")
    private Long idOnerePadre;

    @JsonProperty("data_inserimento_pagamento")
    private Timestamp dataInserimentoPagamento;

    @JsonProperty("importo_dovuto")
    private BigDecimal importoDovuto;

    @JsonProperty("iuv")
    private String iuv;

    @JsonProperty("data_effettivo_pagamento")
    private Timestamp dataEffettivoPagamento;

    @JsonProperty("importo_pagato")
    private BigDecimal importoPagato;

    @JsonProperty("iubd")
    private String iubd;

    @JsonProperty("rt_xml")
    private String rtXml;

    @JsonProperty("id_allegato_istanza")
    private Long idAllegatoIstanza;

    @JsonProperty("ind_tipo_inserimento")
    private String indTipoInserimento;

    @JsonProperty("flg_non_dovuto")
    private Boolean flgNonDovuto;

    /**
     * Gets id pagamento istanza.
     *
     * @return the id pagamento istanza
     */
    public Long getIdPagamentoIstanza() {
        return idPagamentoIstanza;
    }

    /**
     * Sets id pagamento istanza.
     *
     * @param idPagamentoIstanza the id pagamento istanza
     */
    public void setIdPagamentoIstanza(Long idPagamentoIstanza) {
        this.idPagamentoIstanza = idPagamentoIstanza;
    }

    /**
     * Gets id istanza.
     *
     * @return the id istanza
     */
    public Long getIdIstanza() {
        return idIstanza;
    }

    /**
     * Sets id istanza.
     *
     * @param idIstanza the id istanza
     */
    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    /**
     * Gets id riscossione ente.
     *
     * @return the id riscossione ente
     */
    public Long getIdRiscossioneEnte() {
        return idRiscossioneEnte;
    }

    /**
     * Sets id riscossione ente.
     *
     * @param idRiscossioneEnte the id riscossione ente
     */
    public void setIdRiscossioneEnte(Long idRiscossioneEnte) {
        this.idRiscossioneEnte = idRiscossioneEnte;
    }

    /**
     * Gets id stato pagamento.
     *
     * @return the id stato pagamento
     */
    public Long getIdStatoPagamento() {
        return idStatoPagamento;
    }

    /**
     * Sets id stato pagamento.
     *
     * @param idStatoPagamento the id stato pagamento
     */
    public void setIdStatoPagamento(Long idStatoPagamento) {
        this.idStatoPagamento = idStatoPagamento;
    }

    /**
     * Gets id onere padre.
     *
     * @return the id onere padre
     */
    public Long getIdOnerePadre() {
        return idOnerePadre;
    }

    /**
     * Sets id onere padre.
     *
     * @param idOnerePadre the id onere padre
     */
    public void setIdOnerePadre(Long idOnerePadre) {
        this.idOnerePadre = idOnerePadre;
    }

    /**
     * Gets data inserimento pagamento.
     *
     * @return the data inserimento pagamento
     */
    public Timestamp getDataInserimentoPagamento() {
        return dataInserimentoPagamento;
    }

    /**
     * Sets data inserimento pagamento.
     *
     * @param dataInserimentoPagamento the data inserimento pagamento
     */
    public void setDataInserimentoPagamento(Timestamp dataInserimentoPagamento) {
        this.dataInserimentoPagamento = dataInserimentoPagamento;
    }

    /**
     * Gets importo dovuto.
     *
     * @return the importo dovuto
     */
    public BigDecimal getImportoDovuto() {
        return importoDovuto;
    }

    /**
     * Sets importo dovuto.
     *
     * @param importoDovuto the importo dovuto
     */
    public void setImportoDovuto(BigDecimal importoDovuto) {
        this.importoDovuto = importoDovuto;
    }

    /**
     * Gets iuv.
     *
     * @return the iuv
     */
    public String getIuv() {
        return iuv;
    }

    /**
     * Sets iuv.
     *
     * @param iuv the iuv
     */
    public void setIuv(String iuv) {
        this.iuv = iuv;
    }

    /**
     * Gets data effettivo pagamento.
     *
     * @return the data effettivo pagamento
     */
    public Timestamp getDataEffettivoPagamento() {
        return dataEffettivoPagamento;
    }

    /**
     * Sets data effettivo pagamento.
     *
     * @param dataEffettivoPagamento the data effettivo pagamento
     */
    public void setDataEffettivoPagamento(Timestamp dataEffettivoPagamento) {
        this.dataEffettivoPagamento = dataEffettivoPagamento;
    }

    /**
     * Gets importo pagato.
     *
     * @return the importo pagato
     */
    public BigDecimal getImportoPagato() {
        return importoPagato;
    }

    /**
     * Sets importo pagato.
     *
     * @param importoPagato the importo pagato
     */
    public void setImportoPagato(BigDecimal importoPagato) {
        this.importoPagato = importoPagato;
    }

    /**
     * Gets iubd.
     *
     * @return the iubd
     */
    public String getIubd() {
        return iubd;
    }

    /**
     * Sets iubd.
     *
     * @param iubd the iubd
     */
    public void setIubd(String iubd) {
        this.iubd = iubd;
    }

    /**
     * Gets rt xml.
     *
     * @return the rt xml
     */
    public String getRtXml() {
        return rtXml;
    }

    /**
     * Sets rt xml.
     *
     * @param rtXml the rt xml
     */
    public void setRtXml(String rtXml) {
        this.rtXml = rtXml;
    }

    /**
     * Gets id allegato istanza.
     *
     * @return the id allegato istanza
     */
    public Long getIdAllegatoIstanza() {
        return idAllegatoIstanza;
    }

    /**
     * Sets id allegato istanza.
     *
     * @param idAllegatoIstanza the id allegato istanza
     */
    public void setIdAllegatoIstanza(Long idAllegatoIstanza) {
        this.idAllegatoIstanza = idAllegatoIstanza;
    }

    /**
     * Gets ind tipo inserimento.
     *
     * @return the ind tipo inserimento
     */
    public String getIndTipoInserimento() {
        return indTipoInserimento;
    }

    /**
     * Sets ind tipo inserimento.
     *
     * @param indTipoInserimento the ind tipo inserimento
     */
    public void setIndTipoInserimento(String indTipoInserimento) {
        this.indTipoInserimento = indTipoInserimento;
    }

    /**
     * Gets flg non dovuto.
     *
     * @return the flg non dovuto
     */
    public Boolean getFlgNonDovuto() {
        return flgNonDovuto;
    }

    /**
     * Sets flg non dovuto.
     *
     * @param flgNonDovuto the flg non dovuto
     */
    public void setFlgNonDovuto(Boolean flgNonDovuto) {
        this.flgNonDovuto = flgNonDovuto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PagamentoIstanzaDTO that = (PagamentoIstanzaDTO) o;
        return Objects.equals(idPagamentoIstanza, that.idPagamentoIstanza) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idRiscossioneEnte, that.idRiscossioneEnte) && Objects.equals(idStatoPagamento, that.idStatoPagamento) && Objects.equals(idOnerePadre, that.idOnerePadre) && Objects.equals(dataInserimentoPagamento, that.dataInserimentoPagamento) && Objects.equals(importoDovuto, that.importoDovuto) && Objects.equals(iuv, that.iuv) && Objects.equals(dataEffettivoPagamento, that.dataEffettivoPagamento) && Objects.equals(importoPagato, that.importoPagato) && Objects.equals(iubd, that.iubd) && Objects.equals(rtXml, that.rtXml) && Objects.equals(idAllegatoIstanza, that.idAllegatoIstanza) && Objects.equals(indTipoInserimento, that.indTipoInserimento) && Objects.equals(flgNonDovuto, that.flgNonDovuto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idPagamentoIstanza, idIstanza, idRiscossioneEnte, idStatoPagamento, idOnerePadre, dataInserimentoPagamento, importoDovuto, iuv, dataEffettivoPagamento, importoPagato, iubd, rtXml, idAllegatoIstanza, indTipoInserimento, flgNonDovuto);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PagamentoIstanzaDTO {");
        sb.append("         idPagamentoIstanza:").append(idPagamentoIstanza);
        sb.append(",         idIstanza:").append(idIstanza);
        sb.append(",         idRiscossioneEnte:").append(idRiscossioneEnte);
        sb.append(",         idStatoPagamento:").append(idStatoPagamento);
        sb.append(",         idOnerePadre:").append(idOnerePadre);
        sb.append(",         dataInserimentoPagamento:").append(dataInserimentoPagamento);
        sb.append(",         importoDovuto:").append(importoDovuto);
        sb.append(",         iuv:'").append(iuv).append("'");
        sb.append(",         dataEffettivoPagamento:").append(dataEffettivoPagamento);
        sb.append(",         importoPagato:").append(importoPagato);
        sb.append(",         iubd:'").append(iubd).append("'");
        sb.append(",         rtXml:'").append(rtXml).append("'");
        sb.append(",         idAllegatoIstanza:").append(idAllegatoIstanza);
        sb.append(",         indTipoInserimento:'").append(indTipoInserimento).append("'");
        sb.append(",         flgNonDovuto:").append(flgNonDovuto);
        sb.append(",         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append("}");
        return sb.toString();
    }
}