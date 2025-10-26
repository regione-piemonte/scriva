/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Pagamento istanza.
 *
 * @author CSI PIEMONTE
 */
public class PagamentoIstanzaExtendedDTO extends PagamentoIstanzaDTO implements Serializable {

    @JsonProperty("riscossione_ente")
    private RiscossioneEnteExtendedDTO riscossioneEnte;

    @JsonProperty("stato_pagamento")
    private StatoPagamentoDTO statoPagamento;

    @JsonProperty("tentativo_dettaglio_pagamento")
    private List<TentativoDettaglioExtendedDTO> tentativoDettaglioPagamento;

    @JsonProperty("allegato_istanza")
    private AllegatoIstanzaDTO allegatoIstanza;

    /**
     * Gets riscossione ente.
     *
     * @return the riscossione ente
     */
    public RiscossioneEnteExtendedDTO getRiscossioneEnte() {
        return riscossioneEnte;
    }

    /**
     * Sets riscossione ente.
     *
     * @param riscossioneEnte the riscossione ente
     */
    public void setRiscossioneEnte(RiscossioneEnteExtendedDTO riscossioneEnte) {
        this.riscossioneEnte = riscossioneEnte;
    }

    /**
     * Gets stato pagamento.
     *
     * @return the stato pagamento
     */
    public StatoPagamentoDTO getStatoPagamento() {
        return statoPagamento;
    }

    /**
     * Sets stato pagamento.
     *
     * @param statoPagamento the stato pagamento
     */
    public void setStatoPagamento(StatoPagamentoDTO statoPagamento) {
        this.statoPagamento = statoPagamento;
    }

    /**
     * Gets tentativo dettaglio pagamento.
     *
     * @return the tentativo dettaglio pagamento
     */
    public List<TentativoDettaglioExtendedDTO> getTentativoDettaglioPagamento() {
        return tentativoDettaglioPagamento;
    }

    /**
     * Sets tentativo dettaglio pagamento.
     *
     * @param tentativoDettaglioPagamento the tentativo dettaglio pagamento
     */
    public void setTentativoDettaglioPagamento(List<TentativoDettaglioExtendedDTO> tentativoDettaglioPagamento) {
        this.tentativoDettaglioPagamento = tentativoDettaglioPagamento;
    }

    /**
     * Gets allegato istanza.
     *
     * @return the allegato istanza
     */
    public AllegatoIstanzaDTO getAllegatoIstanza() {
        return allegatoIstanza;
    }

    /**
     * Sets allegato istanza.
     *
     * @param allegatoIstanza the allegato istanza
     */
    public void setAllegatoIstanza(AllegatoIstanzaDTO allegatoIstanza) {
        this.allegatoIstanza = allegatoIstanza;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PagamentoIstanzaExtendedDTO that = (PagamentoIstanzaExtendedDTO) o;
        return Objects.equals(riscossioneEnte, that.riscossioneEnte) && Objects.equals(statoPagamento, that.statoPagamento) && Objects.equals(tentativoDettaglioPagamento, that.tentativoDettaglioPagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), riscossioneEnte, statoPagamento, tentativoDettaglioPagamento);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PagamentoIstanzaExtendedDTO {");
        sb.append(super.toString());
        sb.append("         riscossioneEnte:").append(riscossioneEnte);
        sb.append(",         statoPagamento:").append(statoPagamento);
        sb.append(",         tentativoDettaglioPagamento:").append(tentativoDettaglioPagamento);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public PagamentoIstanzaDTO getDTO() {
        PagamentoIstanzaDTO dto = new PagamentoIstanzaDTO();
        dto.setIdPagamentoIstanza(this.getIdPagamentoIstanza());
        dto.setIdIstanza(this.getIdIstanza());

        if (null != this.getRiscossioneEnte()) {
            dto.setIdRiscossioneEnte(this.getRiscossioneEnte().getIdRiscossioneEnte());
        }

        if (null != this.getStatoPagamento()) {
            dto.setIdStatoPagamento(this.getStatoPagamento().getIdStatoPagamento());
        }

        if (null != this.getAllegatoIstanza()) {
            dto.setIdAllegatoIstanza(this.getAllegatoIstanza().getIdAllegatoIstanza());
        }

        dto.setIdOnerePadre(this.getIdOnerePadre());
        dto.setDataInserimentoPagamento(this.getDataInserimentoPagamento());
        dto.setImportoDovuto(this.getImportoDovuto());
        dto.setIuv(this.getIuv());
        dto.setDataEffettivoPagamento(this.getDataEffettivoPagamento());
        dto.setImportoPagato(this.getImportoPagato());
        dto.setIubd(this.getIubd());
        dto.setFlgNonDovuto(this.getFlgNonDovuto());
        dto.setIndTipoInserimento(this.getIndTipoInserimento());
        dto.setRtXml(this.getRtXml());
        dto.setGestAttoreIns(this.gestAttoreIns);
        dto.setGestAttoreUpd(this.gestAttoreUpd);
        dto.setGestDataIns(this.gestDataIns);
        dto.setGestDataUpd(this.gestDataUpd);
        dto.setGestUID(this.gestUID);
        return dto;
    }
}