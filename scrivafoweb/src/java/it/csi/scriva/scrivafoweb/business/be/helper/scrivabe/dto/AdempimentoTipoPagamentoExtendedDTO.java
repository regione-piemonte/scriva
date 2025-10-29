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
 * The type Adempimento tipo pagamento extended dto.
 *
 * @author CSI PIEMONTE
 */
public class AdempimentoTipoPagamentoExtendedDTO extends AdempimentoTipoPagamentoDTO implements Serializable {

    @JsonProperty("adempimento")
    private AdempimentoExtendedDTO adempimento;

    @JsonProperty("tipo_pagamento")
    private TipoPagamentoExtendedDTO tipoPagamento;

    @JsonProperty("ente_creditore")
    private EnteCreditoreDTO enteCreditore;

    @JsonProperty("competenza_territorio")
    private CompetenzaTerritorioDTO competenzaTerritorio;

    /**
     * Gets adempimento.
     *
     * @return the adempimento
     */
    public AdempimentoExtendedDTO getAdempimento() {
        return adempimento;
    }

    /**
     * Sets adempimento.
     *
     * @param adempimento the adempimento
     */
    public void setAdempimento(AdempimentoExtendedDTO adempimento) {
        this.adempimento = adempimento;
    }

    /**
     * Gets tipo pagamento.
     *
     * @return the tipo pagamento
     */
    public TipoPagamentoExtendedDTO getTipoPagamento() {
        return tipoPagamento;
    }

    /**
     * Sets tipo pagamento.
     *
     * @param tipoPagamento the tipo pagamento
     */
    public void setTipoPagamento(TipoPagamentoExtendedDTO tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    /**
     * Gets ente creditore.
     *
     * @return the ente creditore
     */
    public EnteCreditoreDTO getEnteCreditore() {
        return enteCreditore;
    }

    /**
     * Sets ente creditore.
     *
     * @param enteCreditore the ente creditore
     */
    public void setEnteCreditore(EnteCreditoreDTO enteCreditore) {
        this.enteCreditore = enteCreditore;
    }

    /**
     * Gets competenza territorio.
     *
     * @return the competenza territorio
     */
    public CompetenzaTerritorioDTO getCompetenzaTerritorio() {
        return competenzaTerritorio;
    }

    /**
     * Sets competenza territorio.
     *
     * @param competenzaTerritorio the competenza territorio
     */
    public void setCompetenzaTerritorio(CompetenzaTerritorioDTO competenzaTerritorio) {
        this.competenzaTerritorio = competenzaTerritorio;
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
        AdempimentoTipoPagamentoExtendedDTO that = (AdempimentoTipoPagamentoExtendedDTO) o;
        return Objects.equals(adempimento, that.adempimento) && Objects.equals(tipoPagamento, that.tipoPagamento) && Objects.equals(enteCreditore, that.enteCreditore) && Objects.equals(competenzaTerritorio, that.competenzaTerritorio);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), adempimento, tipoPagamento, enteCreditore, competenzaTerritorio);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdempimentoTipoPagamentoExtendedDTO {\n");
        sb.append(super.toString()).append("\n");
        sb.append("         adempimento:").append(adempimento);
        sb.append(",\n         tipoPagamento:").append(tipoPagamento);
        sb.append(",\n         enteCreditore:").append(enteCreditore);
        sb.append(",\n         competenzaTerritorio:").append(competenzaTerritorio);
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public AdempimentoTipoPagamentoDTO getDTO() {
        AdempimentoTipoPagamentoDTO dto = new AdempimentoTipoPagamentoDTO();
        dto.setIdAdempiTipoPagamento(this.getIdAdempiTipoPagamento());
        if (this.adempimento != null) {
            dto.setIdAdempimento(this.adempimento.getIdAdempimento());
        }
        if (this.tipoPagamento != null) {
            dto.setIdTipoPagamento(this.tipoPagamento.getIdTipoPagamento());
        }
        if (this.enteCreditore != null) {
            dto.setIdEnteCreditore(this.enteCreditore.getIdEnteCreditore());
        }
        if (this.competenzaTerritorio != null) {
            dto.setIdCompetenzaTerritorio(this.competenzaTerritorio.getIdCompetenzaTerritorio());
        }
        dto.setCodiceVersamento(this.getCodiceVersamento());
        dto.setImportoPrevisto(this.getImportoPrevisto());
        dto.setGiorniMaxAttesaRt(this.getGiorniMaxAttesaRt());
        dto.setIndImportoPagamento(this.getIndImportoPagamento());
        dto.setFlgAttivo(this.getFlgAttivo());
        return dto;
    }
}