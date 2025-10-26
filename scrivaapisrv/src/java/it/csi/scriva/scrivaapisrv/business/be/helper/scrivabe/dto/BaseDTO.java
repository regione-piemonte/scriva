/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */

package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class BaseDTO implements Serializable {

    @JsonIgnore
    protected Timestamp gestDataIns;

    protected String gestAttoreIns;

    @JsonIgnore
    protected Timestamp gestDataUpd;

    protected String gestAttoreUpd;

    protected String gestUID;

    public Timestamp getGestDataIns() {
        return gestDataIns;
    }

    public void setGestDataIns(Timestamp gestDataIns) {
        this.gestDataIns = gestDataIns;
    }

    public String getGestAttoreIns() {
        return gestAttoreIns;
    }

    public void setGestAttoreIns(String gestAttoreIns) {
        this.gestAttoreIns = gestAttoreIns;
    }

    public Timestamp getGestDataUpd() {
        return gestDataUpd;
    }

    public void setGestDataUpd(Timestamp gestDataUpd) {
        this.gestDataUpd = gestDataUpd;
    }

    public String getGestAttoreUpd() {
        return gestAttoreUpd;
    }

    public void setGestAttoreUpd(String gestAttoreUpd) {
        this.gestAttoreUpd = gestAttoreUpd;
    }

    public String getGestUID() {
        return gestUID;
    }

    public void setGestUID(String gestUID) {
        this.gestUID = gestUID;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseDTO baseDTO = (BaseDTO) o;
        return Objects.equals(gestDataIns, baseDTO.gestDataIns) && Objects.equals(gestAttoreIns, baseDTO.gestAttoreIns) && Objects.equals(gestDataUpd, baseDTO.gestDataUpd) && Objects.equals(gestAttoreUpd, baseDTO.gestAttoreUpd) && Objects.equals(gestUID, baseDTO.gestUID);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(gestDataIns, gestAttoreIns, gestDataUpd, gestAttoreUpd, gestUID);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "BaseDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                "}\n";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    protected String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }


}