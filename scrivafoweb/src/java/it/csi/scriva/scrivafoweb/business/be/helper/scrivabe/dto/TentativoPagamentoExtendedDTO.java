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
import java.util.List;
import java.util.Objects;

/**
 * The type Tentativo pagamento dto.
 *
 * @author CSI PIEMONTE
 */
public class TentativoPagamentoExtendedDTO extends TentativoPagamentoDTO implements Serializable {

    @JsonProperty("stato_tentativo_pag")
    private StatoTentativoPagamentoDTO statoTentativoPagamento;

    @JsonProperty("tentativo_dettaglio")
    private List<TentativoDettaglioExtendedDTO> tentativoDettaglio;

    /**
     * Gets stato tentativo pagamento.
     *
     * @return the stato tentativo pagamento
     */
    public StatoTentativoPagamentoDTO getStatoTentativoPagamento() {
        return statoTentativoPagamento;
    }

    /**
     * Sets stato tentativo pagamento.
     *
     * @param statoTentativoPagamento the stato tentativo pagamento
     */
    public void setStatoTentativoPagamento(StatoTentativoPagamentoDTO statoTentativoPagamento) {
        this.statoTentativoPagamento = statoTentativoPagamento;
    }

    /**
     * Gets tentativo dettaglio.
     *
     * @return the tentativo dettaglio
     */
    public List<TentativoDettaglioExtendedDTO> getTentativoDettaglio() {
        return tentativoDettaglio;
    }

    /**
     * Sets tentativo dettaglio.
     *
     * @param tentativoDettaglio the tentativo dettaglio
     */
    public void setTentativoDettaglio(List<TentativoDettaglioExtendedDTO> tentativoDettaglio) {
        this.tentativoDettaglio = tentativoDettaglio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TentativoPagamentoExtendedDTO that = (TentativoPagamentoExtendedDTO) o;
        return Objects.equals(statoTentativoPagamento, that.statoTentativoPagamento) && Objects.equals(tentativoDettaglio, that.tentativoDettaglio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), statoTentativoPagamento, tentativoDettaglio);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TentativoPagamentoExtendedDTO {");
        sb.append(super.toString());
        sb.append("         statoTentativoPagamento:").append(statoTentativoPagamento);
        sb.append(",         tentativoDettaglio:").append(tentativoDettaglio);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public TentativoPagamentoDTO getDTO() {
        TentativoPagamentoDTO dto = new TentativoPagamentoDTO();
        dto.setIdTentativoPagamento(this.getIdTentativoPagamento());
        if (this.getStatoTentativoPagamento() != null) {
            dto.setIdStatoTentativoPagamento(this.getStatoTentativoPagamento().getIdStatoTentativoPagamento());
        }
        dto.setIdentificativoPagamentoPpay(this.getIdentificativoPagamentoPpay());
        dto.setHashPagamento(this.getHashPagamento());
        dto.setTipoBollo(this.getTipoBollo());
        dto.setFlgSoloMarca(this.getFlgSoloMarca());
        dto.setGestAttoreIns(this.gestAttoreIns);
        dto.setGestAttoreUpd(this.gestAttoreUpd);
        dto.setGestDataIns(this.gestDataIns);
        dto.setGestDataUpd(this.gestDataUpd);
        dto.setGestUID(this.gestUID);
        return dto;
    }
}