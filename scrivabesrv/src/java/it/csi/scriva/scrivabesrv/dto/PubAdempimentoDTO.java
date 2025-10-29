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
 * The type Pub adempimento dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PubAdempimentoDTO implements Serializable {

    @JsonProperty("cod_adempimento")
    private String codAdempimento;

    @JsonProperty("des_adempimento")
    private String desAdempimento;

    @JsonProperty("cod_tipo_adempimento")
    private String codTipoAdempimento;

    @JsonProperty("des_tipo_adempimento")
    private String desTipoAdempimento;

    /**
     * Gets cod adempimento.
     *
     * @return the cod adempimento
     */
    public String getCodAdempimento() {
        return codAdempimento;
    }

    /**
     * Sets cod adempimento.
     *
     * @param codAdempimento the cod adempimento
     */
    public void setCodAdempimento(String codAdempimento) {
        this.codAdempimento = codAdempimento;
    }

    /**
     * Gets des adempimento.
     *
     * @return the des adempimento
     */
    public String getDesAdempimento() {
        return desAdempimento;
    }

    /**
     * Sets des adempimento.
     *
     * @param desAdempimento the des adempimento
     */
    public void setDesAdempimento(String desAdempimento) {
        this.desAdempimento = desAdempimento;
    }

    /**
     * Gets cod tipo adempimento.
     *
     * @return the cod tipo adempimento
     */
    public String getCodTipoAdempimento() {
        return codTipoAdempimento;
    }

    /**
     * Sets cod tipo adempimento.
     *
     * @param codTipoAdempimento the cod tipo adempimento
     */
    public void setCodTipoAdempimento(String codTipoAdempimento) {
        this.codTipoAdempimento = codTipoAdempimento;
    }

    /**
     * Gets des tipo adempimento.
     *
     * @return the des tipo adempimento
     */
    public String getDesTipoAdempimento() {
        return desTipoAdempimento;
    }

    /**
     * Sets des tipo adempimento.
     *
     * @param desTipoAdempimento the des tipo adempimento
     */
    public void setDesTipoAdempimento(String desTipoAdempimento) {
        this.desTipoAdempimento = desTipoAdempimento;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PubAdempimentoDTO that = (PubAdempimentoDTO) o;
        return Objects.equals(codAdempimento, that.codAdempimento) && Objects.equals(desAdempimento, that.desAdempimento) && Objects.equals(codTipoAdempimento, that.codTipoAdempimento) && Objects.equals(desTipoAdempimento, that.desTipoAdempimento);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codAdempimento, desAdempimento, codTipoAdempimento, desTipoAdempimento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PubAdempimentoDTO {\n");
        sb.append("         codAdempimento:'").append(codAdempimento).append("'");
        sb.append(",\n         desAdempimento:'").append(desAdempimento).append("'");
        sb.append(",\n         codTipoAdempimento:'").append(codTipoAdempimento).append("'");
        sb.append(",\n         desTipoAdempimento:'").append(desTipoAdempimento).append("'");
        sb.append("}\n");
        return sb.toString();
    }
}