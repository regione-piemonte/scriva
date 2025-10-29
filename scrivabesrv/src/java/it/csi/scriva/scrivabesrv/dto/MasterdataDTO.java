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
 * The type Masterdata dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MasterdataDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_masterdata")
    private Long idMasterdata;

    @JsonProperty("cod_masterdata")
    private String codMasterdata;

    @JsonProperty("des_masterdata")
    private String desMasterdata;

    /**
     * Gets id masterdata.
     *
     * @return the id masterdata
     */
    public Long getIdMasterdata() {
        return idMasterdata;
    }

    /**
     * Sets id masterdata.
     *
     * @param idMasterdata the id masterdata
     */
    public void setIdMasterdata(Long idMasterdata) {
        this.idMasterdata = idMasterdata;
    }

    /**
     * Gets cod masterdata.
     *
     * @return the cod masterdata
     */
    public String getCodMasterdata() {
        return codMasterdata;
    }

    /**
     * Sets cod masterdata.
     *
     * @param codMasterdata the cod masterdata
     */
    public void setCodMasterdata(String codMasterdata) {
        this.codMasterdata = codMasterdata;
    }

    /**
     * Gets des masterdata.
     *
     * @return the des masterdata
     */
    public String getDesMasterdata() {
        return desMasterdata;
    }

    /**
     * Sets des masterdata.
     *
     * @param desMasterdata the des masterdata
     */
    public void setDesMasterdata(String desMasterdata) {
        this.desMasterdata = desMasterdata;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codMasterdata, desMasterdata, idMasterdata);
    }

    /**
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        MasterdataDTO other = (MasterdataDTO) obj;
        return Objects.equals(codMasterdata, other.codMasterdata) && Objects.equals(desMasterdata, other.desMasterdata) && Objects.equals(idMasterdata, other.idMasterdata);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MasterdataDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         idMasterdata:").append(idMasterdata);
        sb.append(",         codMasterdata:'").append(codMasterdata).append("'");
        sb.append(",         desMasterdata:'").append(desMasterdata).append("'");
        sb.append("}");
        return sb.toString();
    }
}