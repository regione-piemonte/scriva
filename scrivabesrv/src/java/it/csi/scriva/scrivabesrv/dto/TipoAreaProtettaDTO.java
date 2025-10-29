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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tipo area protetta dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TipoAreaProtettaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_tipo_area_protetta")
    private Long idTipoAreaProtetta;

    @JsonProperty("cod_tipo_area_protetta")
    private String codTipoAreaProtetta;

    @JsonProperty("des_tipo_area_protetta")
    private String desTipoAreaProtetta;

    /**
     * Gets id tipo area protetta.
     *
     * @return the id tipo area protetta
     */
    public Long getIdTipoAreaProtetta() {
        return idTipoAreaProtetta;
    }

    /**
     * Sets id tipo area protetta.
     *
     * @param idTipoAreaProtetta the id tipo area protetta
     */
    public void setIdTipoAreaProtetta(Long idTipoAreaProtetta) {
        this.idTipoAreaProtetta = idTipoAreaProtetta;
    }

    /**
     * Gets cod tipo area protetta.
     *
     * @return the cod tipo area protetta
     */
    public String getCodTipoAreaProtetta() {
        return codTipoAreaProtetta;
    }

    /**
     * Sets cod tipo area protetta.
     *
     * @param codTipoAreaProtetta the cod tipo area protetta
     */
    public void setCodTipoAreaProtetta(String codTipoAreaProtetta) {
        this.codTipoAreaProtetta = codTipoAreaProtetta;
    }

    /**
     * Gets des tipo area protetta.
     *
     * @return the des tipo area protetta
     */
    public String getDesTipoAreaProtetta() {
        return desTipoAreaProtetta;
    }

    /**
     * Sets des tipo area protetta.
     *
     * @param desTipoAreaProtetta the des tipo area protetta
     */
    public void setDesTipoAreaProtetta(String desTipoAreaProtetta) {
        this.desTipoAreaProtetta = desTipoAreaProtetta;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoAreaProtettaDTO that = (TipoAreaProtettaDTO) o;
        return Objects.equals(idTipoAreaProtetta, that.idTipoAreaProtetta) && Objects.equals(codTipoAreaProtetta, that.codTipoAreaProtetta) && Objects.equals(desTipoAreaProtetta, that.desTipoAreaProtetta);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTipoAreaProtetta, codTipoAreaProtetta, desTipoAreaProtetta);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TipoAreaProtettaDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idTipoAreaProtetta:" + idTipoAreaProtetta +
                ",\n         codTipoAreaProtetta:'" + codTipoAreaProtetta + "'" +
                ",\n         desTipoAreaProtetta:'" + desTipoAreaProtetta + "'" +
                "}\n";
    }

}