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
 * The type Tipo integrazione dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoIntegrazioneDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_tipo_integrazione")
    private Long idTipoIntegrazione;

    @JsonProperty("cod_tipo_integrazione")
    private String codTipoIntegrazione;

    @JsonProperty("des_tipo_integrazione")
    private String desTipoIntegrazione;

    /**
     * Gets id tipo integrazione.
     *
     * @return the id tipo integrazione
     */
    public Long getIdTipoIntegrazione() {
        return idTipoIntegrazione;
    }

    /**
     * Sets id tipo integrazione.
     *
     * @param idTipoIntegrazione the id tipo integrazione
     */
    public void setIdTipoIntegrazione(Long idTipoIntegrazione) {
        this.idTipoIntegrazione = idTipoIntegrazione;
    }

    /**
     * Gets cod tipo integrazione.
     *
     * @return the cod tipo integrazione
     */
    public String getCodTipoIntegrazione() {
        return codTipoIntegrazione;
    }

    /**
     * Sets cod tipo integrazione.
     *
     * @param codTipoIntegrazione the cod tipo integrazione
     */
    public void setCodTipoIntegrazione(String codTipoIntegrazione) {
        this.codTipoIntegrazione = codTipoIntegrazione;
    }

    /**
     * Gets des tipo integrazione.
     *
     * @return the des tipo integrazione
     */
    public String getDesTipoIntegrazione() {
        return desTipoIntegrazione;
    }

    /**
     * Sets des tipo integrazione.
     *
     * @param desTipoIntegrazione the des tipo integrazione
     */
    public void setDesTipoIntegrazione(String desTipoIntegrazione) {
        this.desTipoIntegrazione = desTipoIntegrazione;
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
        TipoIntegrazioneDTO that = (TipoIntegrazioneDTO) o;
        return Objects.equals(idTipoIntegrazione, that.idTipoIntegrazione) && Objects.equals(codTipoIntegrazione, that.codTipoIntegrazione) && Objects.equals(desTipoIntegrazione, that.desTipoIntegrazione);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idTipoIntegrazione, codTipoIntegrazione, desTipoIntegrazione);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TipoIntegrazioneDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idTipoIntegrazione:" + idTipoIntegrazione +
                ",\n         codTipoIntegrazione:'" + codTipoIntegrazione + "'" +
                ",\n         desTipoIntegrazione:'" + desTipoIntegrazione + "'" +
                "}\n";
    }
}