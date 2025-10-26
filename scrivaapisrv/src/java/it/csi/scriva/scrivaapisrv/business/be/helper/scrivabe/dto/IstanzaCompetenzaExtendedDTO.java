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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class IstanzaCompetenzaExtendedDTO extends IstanzaCompetenzaDTO implements Serializable {

    @JsonProperty("competenza_territorio")
    private CompetenzaTerritorioExtendedDTO competenzaTerritorio;

    /**
     * @return competenzaTerritorio
     */
    public CompetenzaTerritorioExtendedDTO getCompetenzaTerritorio() {
        return competenzaTerritorio;
    }

    /**
     * @param competenzaTerritorio competenzaTerritorio
     */
    public void setCompetenzaTerritorio(CompetenzaTerritorioExtendedDTO competenzaTerritorio) {
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
        IstanzaCompetenzaExtendedDTO that = (IstanzaCompetenzaExtendedDTO) o;
        return Objects.equals(competenzaTerritorio, that.competenzaTerritorio);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), competenzaTerritorio);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IstanzaCompetenzaExtendedDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         competenzaTerritorio:").append(competenzaTerritorio);
        sb.append("}");
        return sb.toString();
    }

}