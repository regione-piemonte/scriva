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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Riscossione ente dto.
 */
public class RiscossioneEnteDTO implements Serializable {

    @JsonProperty("id_riscossione_ente")
    private Long idRiscossioneEnte;

    @JsonProperty("id_adempi_tipo_pagamento")
    private Long idAdempimentoTipoPagamento;

    @JsonProperty("id_gruppo_pagamento")
    private Long idGruppoPagamento;

    @JsonProperty("id_stato_istanza_blocco")
    private Long idStatoIstanzaBlocco;

    @JsonProperty("dati_specifici_riscossione")
    private String datiSpecificiRiscossione;

    @JsonProperty("accertamento_anno")
    private Integer accertamentoAnno;

    @JsonProperty("numero_accertamento")
    private Long numeroAccertamento;

    @JsonProperty("des_pagamento_verso_cittadino")
    private String desPagamentoVersoCittadino;

    @JsonProperty("ordinamento_riscossione_ente")
    private Integer ordinamentoRiscossioneEnte;

    @JsonProperty("flg_attiva_pagamento")
    private Boolean flgAttivaPagamento;

    @JsonProperty("flg_attiva_importo_non_dovuto")
    private Boolean flgAttivaImportoNonDovuto;

    @JsonIgnore
    private Boolean flgAttivo;

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
     * Gets id adempimento tipo pagamento.
     *
     * @return the id adempimento tipo pagamento
     */
    public Long getIdAdempimentoTipoPagamento() {
        return idAdempimentoTipoPagamento;
    }

    /**
     * Sets id adempimento tipo pagamento.
     *
     * @param idAdempimentoTipoPagamento the id adempimento tipo pagamento
     */
    public void setIdAdempimentoTipoPagamento(Long idAdempimentoTipoPagamento) {
        this.idAdempimentoTipoPagamento = idAdempimentoTipoPagamento;
    }

    /**
     * Gets id gruppo pagamento.
     *
     * @return the id gruppo pagamento
     */
    public Long getIdGruppoPagamento() {
        return idGruppoPagamento;
    }

    /**
     * Sets id gruppo pagamento.
     *
     * @param idGruppoPagamento the id gruppo pagamento
     */
    public void setIdGruppoPagamento(Long idGruppoPagamento) {
        this.idGruppoPagamento = idGruppoPagamento;
    }

    /**
     * Gets id stato istanza blocco.
     *
     * @return the id stato istanza blocco
     */
    public Long getIdStatoIstanzaBlocco() {
        return idStatoIstanzaBlocco;
    }

    /**
     * Sets id stato istanza blocco.
     *
     * @param idStatoIstanzaBlocco the id stato istanza blocco
     */
    public void setIdStatoIstanzaBlocco(Long idStatoIstanzaBlocco) {
        this.idStatoIstanzaBlocco = idStatoIstanzaBlocco;
    }

    /**
     * Gets dati specifici riscossione.
     *
     * @return the dati specifici riscossione
     */
    public String getDatiSpecificiRiscossione() {
        return datiSpecificiRiscossione;
    }

    /**
     * Sets dati specifici riscossione.
     *
     * @param datiSpecificiRiscossione the dati specifici riscossione
     */
    public void setDatiSpecificiRiscossione(String datiSpecificiRiscossione) {
        this.datiSpecificiRiscossione = datiSpecificiRiscossione;
    }

    /**
     * Gets accertamento anno.
     *
     * @return the accertamento anno
     */
    public Integer getAccertamentoAnno() {
        return accertamentoAnno;
    }

    /**
     * Sets accertamento anno.
     *
     * @param accertamentoAnno the accertamento anno
     */
    public void setAccertamentoAnno(Integer accertamentoAnno) {
        this.accertamentoAnno = accertamentoAnno;
    }

    /**
     * Gets numero accertamento.
     *
     * @return the numero accertamento
     */
    public Long getNumeroAccertamento() {
        return numeroAccertamento;
    }

    /**
     * Sets numero accertamento.
     *
     * @param numeroAccertamento the numero accertamento
     */
    public void setNumeroAccertamento(Long numeroAccertamento) {
        this.numeroAccertamento = numeroAccertamento;
    }

    /**
     * Gets des pagamento verso cittadino.
     *
     * @return the des pagamento verso cittadino
     */
    public String getDesPagamentoVersoCittadino() {
        return desPagamentoVersoCittadino;
    }

    /**
     * Sets des pagamento verso cittadino.
     *
     * @param desPagamentoVersoCittadino the des pagamento verso cittadino
     */
    public void setDesPagamentoVersoCittadino(String desPagamentoVersoCittadino) {
        this.desPagamentoVersoCittadino = desPagamentoVersoCittadino;
    }

    /**
     * Gets ordinamento riscossione ente.
     *
     * @return the ordinamento riscossione ente
     */
    public Integer getOrdinamentoRiscossioneEnte() {
        return ordinamentoRiscossioneEnte;
    }

    /**
     * Sets ordinamento riscossione ente.
     *
     * @param ordinamentoRiscossioneEnte the ordinamento riscossione ente
     */
    public void setOrdinamentoRiscossioneEnte(Integer ordinamentoRiscossioneEnte) {
        this.ordinamentoRiscossioneEnte = ordinamentoRiscossioneEnte;
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
     * Gets flg attiva pagamento.
     *
     * @return the flg attiva pagamento
     */
    public Boolean getFlgAttivaPagamento() {
        return flgAttivaPagamento;
    }

    /**
     * Sets flg attiva pagamento.
     *
     * @param flgAttivaPagamento the flg attiva pagamento
     */
    public void setFlgAttivaPagamento(Boolean flgAttivaPagamento) {
        this.flgAttivaPagamento = flgAttivaPagamento;
    }

    public Boolean getFlgAttivaImportoNonDovuto() {
        return flgAttivaImportoNonDovuto;
    }

    public void setFlgAttivaImportoNonDovuto(Boolean flgAttivaImportoNonDovuto) {
        this.flgAttivaImportoNonDovuto = flgAttivaImportoNonDovuto;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RiscossioneEnteDTO that = (RiscossioneEnteDTO) o;
        return Objects.equals(idRiscossioneEnte, that.idRiscossioneEnte) && Objects.equals(idAdempimentoTipoPagamento, that.idAdempimentoTipoPagamento) && Objects.equals(idGruppoPagamento, that.idGruppoPagamento) && Objects.equals(idStatoIstanzaBlocco, that.idStatoIstanzaBlocco) && Objects.equals(datiSpecificiRiscossione, that.datiSpecificiRiscossione) && Objects.equals(accertamentoAnno, that.accertamentoAnno) && Objects.equals(numeroAccertamento, that.numeroAccertamento) && Objects.equals(desPagamentoVersoCittadino, that.desPagamentoVersoCittadino) && Objects.equals(ordinamentoRiscossioneEnte, that.ordinamentoRiscossioneEnte) && Objects.equals(flgAttivaPagamento, that.flgAttivaPagamento) && Objects.equals(flgAttivaImportoNonDovuto, that.flgAttivaImportoNonDovuto) && Objects.equals(flgAttivo, that.flgAttivo);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idRiscossioneEnte, idAdempimentoTipoPagamento, idGruppoPagamento, idStatoIstanzaBlocco, datiSpecificiRiscossione, accertamentoAnno, numeroAccertamento, desPagamentoVersoCittadino, ordinamentoRiscossioneEnte, flgAttivaPagamento, flgAttivaImportoNonDovuto, flgAttivo);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RiscossioneEnteDTO {");
        sb.append("         idRiscossioneEnte:").append(idRiscossioneEnte);
        sb.append(",         idAdempimentoTipoPagamento:").append(idAdempimentoTipoPagamento);
        sb.append(",         idGruppoPagamento:").append(idGruppoPagamento);
        sb.append(",         idStatoIstanzaBlocco:").append(idStatoIstanzaBlocco);
        sb.append(",         datiSpecificiRiscossione:'").append(datiSpecificiRiscossione).append("'");
        sb.append(",         accertamentoAnno:").append(accertamentoAnno);
        sb.append(",         numeroAccertamento:").append(numeroAccertamento);
        sb.append(",         desPagamentoVersoCittadino:'").append(desPagamentoVersoCittadino).append("'");
        sb.append(",         ordinamentoRiscossioneEnte:").append(ordinamentoRiscossioneEnte);
        sb.append(",         flgAttivaPagamento:").append(flgAttivaPagamento);
        sb.append(",         flgAttivaImportoNonDovuto:").append(flgAttivaImportoNonDovuto);
        sb.append(",         flgAttivo:").append(flgAttivo);
        sb.append("}");
        return sb.toString();
    }

}