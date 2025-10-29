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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Oggetto istanza natura 2000 extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OggettoIstanzaNatura2000ExtendedDTO extends OggettoIstanzaNatura2000DTO implements Serializable {

    @JsonProperty("oggetto_istanza")
    private OggettoIstanzaDTO oggettoIstanza;

    @JsonProperty("competenza_territorio")
    private CompetenzaTerritorioDTO competenzaTerritorio;

    @JsonProperty("tipo_natura_2000")
    private TipoNatura2000DTO tipoNatura2000;

    /**
     * Gets oggetto istanza.
     *
     * @return the oggetto istanza
     */
    public OggettoIstanzaDTO getOggettoIstanza() {
        return oggettoIstanza;
    }

    /**
     * Sets oggetto istanza.
     *
     * @param oggettoIstanza the oggetto istanza
     */
    public void setOggettoIstanza(OggettoIstanzaDTO oggettoIstanza) {
        this.oggettoIstanza = oggettoIstanza;
    }

    /**
     * Gets competenza territorio.
     *
     * @return the competenza territorio
     */
    public CompetenzaTerritorioDTO getCompetenzaTerritorio() {
        return competenzaTerritorio;
    }

    /**
     * Sets competenza territorio.
     *
     * @param competenzaTerritorio the competenza territorio
     */
    public void setCompetenzaTerritorio(CompetenzaTerritorioDTO competenzaTerritorio) {
        this.competenzaTerritorio = competenzaTerritorio;
    }

    /**
     * Gets tipo natura 2000.
     *
     * @return the tipo natura 2000
     */
    public TipoNatura2000DTO getTipoNatura2000() {
        return tipoNatura2000;
    }

    /**
     * Sets tipo natura 2000.
     *
     * @param tipoNatura2000 the tipo natura 2000
     */
    public void setTipoNatura2000(TipoNatura2000DTO tipoNatura2000) {
        this.tipoNatura2000 = tipoNatura2000;
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
        OggettoIstanzaNatura2000ExtendedDTO that = (OggettoIstanzaNatura2000ExtendedDTO) o;
        return Objects.equals(oggettoIstanza, that.oggettoIstanza) && Objects.equals(competenzaTerritorio, that.competenzaTerritorio) && Objects.equals(tipoNatura2000, that.tipoNatura2000);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), oggettoIstanza, competenzaTerritorio, tipoNatura2000);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoIstanzaNatura2000ExtendedDTO {\n");
        sb.append(super.toString()).append("\n");
        sb.append("         oggettoIstanza:").append(oggettoIstanza);
        sb.append(",\n         competenzaTerritorio:").append(competenzaTerritorio);
        sb.append(",\n         tipoNatura2000:").append(tipoNatura2000);
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public OggettoIstanzaNatura2000DTO getDTO() {
        OggettoIstanzaNatura2000DTO dto = new OggettoIstanzaNatura2000DTO();
        dto.setIdOggettoNatura2000(this.getIdOggettoNatura2000());
        if (this.oggettoIstanza != null) {
            dto.setIdOggettoIstanza(this.oggettoIstanza.getIdOggettoIstanza());
        }
        if (this.competenzaTerritorio != null) {
            dto.setIdCompetenzaTerritorio(this.competenzaTerritorio.getIdCompetenzaTerritorio());
        }
        if (this.tipoNatura2000 != null) {
            dto.setIdTipoNatura2000(this.tipoNatura2000.getIdTipoNatura2000());
        }
        dto.setCodAmministrativo(this.getCodAmministrativo());
        dto.setCodGestoreFonte(this.getCodGestoreFonte());
        dto.setDesSitoNatura2000(this.getDesSitoNatura2000());
        dto.setNumDistanza(this.getNumDistanza());
        dto.setFlgRicade(this.getFlgRicade());
        dto.setDesElementoDiscontinuita(this.getDesElementoDiscontinuita());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestDataUpd(this.getGestDataUpd());
        dto.setGestUID(this.getGestUID());
        return dto;
    }

}