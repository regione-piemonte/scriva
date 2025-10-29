/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Oggetto istanza area protetta extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OggettoIstanzaAreaProtettaExtendedDTO extends OggettoIstanzaAreaProtettaDTO implements Serializable {

    @JsonProperty("oggetto_istanza")
    private OggettoIstanzaDTO oggettoIstanza;

    @JsonProperty("competenza_territorio")
    private CompetenzaTerritorioDTO competenzaTerritorio;

    @JsonProperty("tipo_area_protetta")
    private TipoAreaProtettaDTO tipoAreaProtetta;

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
     * Gets tipo area protetta.
     *
     * @return the tipo area protetta
     */
    public TipoAreaProtettaDTO getTipoAreaProtetta() {
        return tipoAreaProtetta;
    }

    /**
     * Sets tipo area protetta.
     *
     * @param tipoAreaProtetta the tipo area protetta
     */
    public void setTipoAreaProtetta(TipoAreaProtettaDTO tipoAreaProtetta) {
        this.tipoAreaProtetta = tipoAreaProtetta;
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
        OggettoIstanzaAreaProtettaExtendedDTO that = (OggettoIstanzaAreaProtettaExtendedDTO) o;
        return Objects.equals(oggettoIstanza, that.oggettoIstanza) && Objects.equals(competenzaTerritorio, that.competenzaTerritorio) && Objects.equals(tipoAreaProtetta, that.tipoAreaProtetta);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), oggettoIstanza, competenzaTerritorio, tipoAreaProtetta);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoIstanzaAreaProtettaExtendedDTO {\n");
        sb.append(super.toString()).append("\n");
        sb.append("         oggettoIstanza:").append(oggettoIstanza);
        sb.append(",\n         competenzaTerritorio:").append(competenzaTerritorio);
        sb.append(",\n         tipoAreaProtetta:").append(tipoAreaProtetta);
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public OggettoIstanzaAreaProtettaDTO getDTO() {
        OggettoIstanzaAreaProtettaDTO dto = new OggettoIstanzaAreaProtettaDTO();
        dto.setIdOggettoAreaProtetta(this.getIdOggettoAreaProtetta());
        if (this.oggettoIstanza != null) {
            dto.setIdOggettoIstanza(this.oggettoIstanza.getIdOggettoIstanza());
        }
        if (this.competenzaTerritorio != null) {
            dto.setIdCompetenzaTerritorio(this.competenzaTerritorio.getIdCompetenzaTerritorio());
        }
        if (this.tipoAreaProtetta != null) {
            dto.setIdTipoAreaProtetta(this.tipoAreaProtetta.getIdTipoAreaProtetta());
        }
        dto.setCodAmministrativo(this.getCodAmministrativo());
        dto.setCodGestoreFonte(this.getCodGestoreFonte());
        dto.setDesAreaProtetta(this.getDesAreaProtetta());
        dto.setFlgRicade(this.getFlgRicade());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestDataUpd(this.getGestDataUpd());
        dto.setGestUID(this.getGestUID());
        return dto;
    }
}