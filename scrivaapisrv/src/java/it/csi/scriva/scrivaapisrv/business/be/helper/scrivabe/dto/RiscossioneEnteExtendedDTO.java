/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.scriva.scrivaapisrv.dto.StatoIstanzaDTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Riscossione ente dto.
 *
 * @author CSI PIEMONTE
 */
public class RiscossioneEnteExtendedDTO extends RiscossioneEnteDTO implements Serializable {

    @JsonProperty("adempi_tipo_pagamento")
    private AdempimentoTipoPagamentoExtendedDTO adempimentoTipoPagamento;

    @JsonProperty("gruppo_pagamento")
    private GruppoPagamentoDTO gruppoPagamento;

    @JsonProperty("stato_istanza_blocco")
    private StatoIstanzaDTO statoIstanzaBlocco;

    @JsonProperty("url_oneri_previsti")
    private String urloOneriPrevisti;

    @JsonIgnore
    private Boolean flgCompetenzaPrincipale;

    /**
     * Gets adempimento tipo pagamento.
     *
     * @return the adempimento tipo pagamento
     */
    public AdempimentoTipoPagamentoExtendedDTO getAdempimentoTipoPagamento() {
        return adempimentoTipoPagamento;
    }

    /**
     * Sets adempimento tipo pagamento.
     *
     * @param adempimentoTipoPagamento the adempimento tipo pagamento
     */
    public void setAdempimentoTipoPagamento(AdempimentoTipoPagamentoExtendedDTO adempimentoTipoPagamento) {
        this.adempimentoTipoPagamento = adempimentoTipoPagamento;
    }

    /**
     * Gets id gruppo pagamento.
     *
     * @return the id gruppo pagamento
     */
    public GruppoPagamentoDTO getGruppoPagamento() {
        return gruppoPagamento;
    }

    /**
     * Sets id gruppo pagamento.
     *
     * @param gruppoPagamento the id gruppo pagamento
     */
    public void setGruppoPagamento(GruppoPagamentoDTO gruppoPagamento) {
        this.gruppoPagamento = gruppoPagamento;
    }

    /**
     * Gets stato istanza blocco.
     *
     * @return the stato istanza blocco
     */
    public StatoIstanzaDTO getStatoIstanzaBlocco() {
        return statoIstanzaBlocco;
    }

    /**
     * Sets stato istanza blocco.
     *
     * @param statoIstanzaBlocco the stato istanza blocco
     */
    public void setStatoIstanzaBlocco(StatoIstanzaDTO statoIstanzaBlocco) {
        this.statoIstanzaBlocco = statoIstanzaBlocco;
    }

    /**
     * Gets urlo oneri previsti.
     *
     * @return the urlo oneri previsti
     */
    public String getUrloOneriPrevisti() {
        return urloOneriPrevisti;
    }

    /**
     * Sets urlo oneri previsti.
     *
     * @param urloOneriPrevisti the urlo oneri previsti
     */
    public void setUrloOneriPrevisti(String urloOneriPrevisti) {
        this.urloOneriPrevisti = urloOneriPrevisti;
    }

    /**
     * Gets flg competenza principale.
     *
     * @return the flg competenza principale
     */
    public Boolean getFlgCompetenzaPrincipale() {
        return flgCompetenzaPrincipale;
    }

    /**
     * Sets flg competenza principale.
     *
     * @param flgCompetenzaPrincipale the flg competenza principale
     */
    public void setFlgCompetenzaPrincipale(Boolean flgCompetenzaPrincipale) {
        this.flgCompetenzaPrincipale = flgCompetenzaPrincipale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RiscossioneEnteExtendedDTO that = (RiscossioneEnteExtendedDTO) o;
        return Objects.equals(adempimentoTipoPagamento, that.adempimentoTipoPagamento) && Objects.equals(gruppoPagamento, that.gruppoPagamento) && Objects.equals(statoIstanzaBlocco, that.statoIstanzaBlocco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), adempimentoTipoPagamento, gruppoPagamento, statoIstanzaBlocco);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RiscossioneEnteExtendedDTO {");
        sb.append(super.toString());
        sb.append("         adempimentoTipoPagamento:").append(adempimentoTipoPagamento);
        sb.append(",         gruppoPagamento:").append(gruppoPagamento);
        sb.append(",         statoIstanzaBlocco:").append(statoIstanzaBlocco);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public RiscossioneEnteDTO getDTO() {
        RiscossioneEnteDTO dto = new RiscossioneEnteDTO();
        dto.setIdRiscossioneEnte(this.getIdRiscossioneEnte());
        if (this.adempimentoTipoPagamento != null) {
            dto.setIdAdempimentoTipoPagamento(this.adempimentoTipoPagamento.getIdAdempiTipoPagamento());
        }
        if (this.gruppoPagamento != null) {
            dto.setIdGruppoPagamento(this.gruppoPagamento.getIdGruppoPagamento());
        }
        if (this.statoIstanzaBlocco != null) {
            this.setIdStatoIstanzaBlocco(this.statoIstanzaBlocco.getIdStatoIstanza());
        }
        dto.setDatiSpecificiRiscossione(this.getDatiSpecificiRiscossione());
        dto.setAccertamentoAnno(this.getAccertamentoAnno());
        dto.setNumeroAccertamento(this.getNumeroAccertamento());
        dto.setDesPagamentoVersoCittadino(this.getDesPagamentoVersoCittadino());
        dto.setOrdinamentoRiscossioneEnte(this.getOrdinamentoRiscossioneEnte());
        dto.setFlgAttivo(this.getFlgAttivo());
        return dto;
    }
}