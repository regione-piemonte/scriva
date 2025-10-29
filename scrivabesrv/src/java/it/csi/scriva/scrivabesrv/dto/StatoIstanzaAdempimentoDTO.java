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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Stato istanza adempimento dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatoIstanzaAdempimentoDTO implements Serializable {

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    @JsonProperty("id_stato_istanza")
    private Long idStatoIstanza;

    @JsonProperty("id_stato_ammesso")
    private Long idStatoAmmesso;

    @JsonProperty("ind_modificabile")
    private String indModificabile;

    /**
     * Gets id adempimento.
     *
     * @return idAdempimento id adempimento
     */
    public Long getIdAdempimento() {
        return idAdempimento;
    }

    /**
     * Sets id adempimento.
     *
     * @param idAdempimento idAdempimento
     */
    public void setIdAdempimento(Long idAdempimento) {
        this.idAdempimento = idAdempimento;
    }

    /**
     * Gets id stato istanza.
     *
     * @return idStatoIstanza id stato istanza
     */
    public Long getIdStatoIstanza() {
        return idStatoIstanza;
    }

    /**
     * Sets id stato istanza.
     *
     * @param idStatoIstanza idStatoIstanza
     */
    public void setIdStatoIstanza(Long idStatoIstanza) {
        this.idStatoIstanza = idStatoIstanza;
    }

    /**
     * Gets id stato ammesso.
     *
     * @return idStatoAmmesso id stato ammesso
     */
    public Long getIdStatoAmmesso() {
        return idStatoAmmesso;
    }

    /**
     * Sets id stato ammesso.
     *
     * @param idStatoAmmesso idStatoIstanza ammesso
     */
    public void setIdStatoAmmesso(Long idStatoAmmesso) {
        this.idStatoAmmesso = idStatoAmmesso;
    }

    /**
     * Gets ind modificabile.
     *
     * @return indModificabile ind modificabile
     */
    public String getIndModificabile() {
        return indModificabile;
    }

    /**
     * Sets ind modificabile.
     *
     * @param indModificabile indModificabile
     */
    public void setIndModificabile(String indModificabile) {
        this.indModificabile = indModificabile;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatoIstanzaAdempimentoDTO that = (StatoIstanzaAdempimentoDTO) o;
        return Objects.equals(idAdempimento, that.idAdempimento) && Objects.equals(idStatoIstanza, that.idStatoIstanza) && Objects.equals(idStatoAmmesso, that.idStatoAmmesso) && Objects.equals(indModificabile, that.indModificabile);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idAdempimento, idStatoIstanza, idStatoAmmesso, indModificabile);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatoIstanzaAdempimentoDTO {");
        sb.append("         idAdempimento:").append(idAdempimento);
        sb.append(",         idStatoIstanza:").append(idStatoIstanza);
        sb.append(",         idStatoAmmesso:").append(idStatoAmmesso);
        sb.append(",         indModificabile:").append(indModificabile);
        sb.append("}");
        return sb.toString();
    }
}