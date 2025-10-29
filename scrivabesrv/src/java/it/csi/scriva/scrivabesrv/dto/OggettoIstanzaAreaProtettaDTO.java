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
 * The type Oggetto istanza area protetta dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OggettoIstanzaAreaProtettaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_oggetto_area_protetta")
    private Long idOggettoAreaProtetta;

    @JsonProperty("id_oggetto_istanza")
    private Long idOggettoIstanza;

    @JsonProperty("id_competenza_territorio")
    private Long idCompetenzaTerritorio;

    @JsonProperty("id_tipo_area_protetta")
    private Long idTipoAreaProtetta;

    @JsonProperty("cod_amministrativo")
    private String codAmministrativo;

    @JsonProperty("cod_gestore_fonte")
    private String codGestoreFonte;

    @JsonProperty("des_area_protetta")
    private String desAreaProtetta;

    @JsonProperty("flg_ricade")
    private Boolean flgRicade;

    /**
     * Gets id oggetto area protetta.
     *
     * @return the id oggetto area protetta
     */
    public Long getIdOggettoAreaProtetta() {
        return idOggettoAreaProtetta;
    }

    /**
     * Sets id oggetto area protetta.
     *
     * @param idOggettoAreaProtetta the id oggetto area protetta
     */
    public void setIdOggettoAreaProtetta(Long idOggettoAreaProtetta) {
        this.idOggettoAreaProtetta = idOggettoAreaProtetta;
    }

    /**
     * Gets id oggetto istanza.
     *
     * @return the id oggetto istanza
     */
    public Long getIdOggettoIstanza() {
        return idOggettoIstanza;
    }

    /**
     * Sets id oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     */
    public void setIdOggettoIstanza(Long idOggettoIstanza) {
        this.idOggettoIstanza = idOggettoIstanza;
    }

    /**
     * Gets id competenza territorio.
     *
     * @return the id competenza territorio
     */
    public Long getIdCompetenzaTerritorio() {
        return idCompetenzaTerritorio;
    }

    /**
     * Sets id competenza territorio.
     *
     * @param idCompetenzaTerritorio the id competenza territorio
     */
    public void setIdCompetenzaTerritorio(Long idCompetenzaTerritorio) {
        this.idCompetenzaTerritorio = idCompetenzaTerritorio;
    }

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
     * Gets cod amministrativo.
     *
     * @return the cod amministrativo
     */
    public String getCodAmministrativo() {
        return codAmministrativo;
    }

    /**
     * Sets cod amministrativo.
     *
     * @param codAmministrativo the cod amministrativo
     */
    public void setCodAmministrativo(String codAmministrativo) {
        this.codAmministrativo = codAmministrativo;
    }

    /**
     * Gets cod gestore fonte.
     *
     * @return the cod gestore fonte
     */
    public String getCodGestoreFonte() {
        return codGestoreFonte;
    }

    /**
     * Sets cod gestore fonte.
     *
     * @param codGestoreFonte the cod gestore fonte
     */
    public void setCodGestoreFonte(String codGestoreFonte) {
        this.codGestoreFonte = codGestoreFonte;
    }

    /**
     * Gets des area protetta.
     *
     * @return the des area protetta
     */
    public String getDesAreaProtetta() {
        return desAreaProtetta;
    }

    /**
     * Sets des area protetta.
     *
     * @param desAreaProtetta the des area protetta
     */
    public void setDesAreaProtetta(String desAreaProtetta) {
        this.desAreaProtetta = desAreaProtetta;
    }

    /**
     * Gets flg ricade.
     *
     * @return the flg ricade
     */
    public Boolean getFlgRicade() {
        return flgRicade;
    }

    /**
     * Sets flg ricade.
     *
     * @param flgRicade the flg ricade
     */
    public void setFlgRicade(Boolean flgRicade) {
        this.flgRicade = flgRicade;
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
        OggettoIstanzaAreaProtettaDTO that = (OggettoIstanzaAreaProtettaDTO) o;
        return Objects.equals(idOggettoAreaProtetta, that.idOggettoAreaProtetta) && Objects.equals(idOggettoIstanza, that.idOggettoIstanza) && Objects.equals(idCompetenzaTerritorio, that.idCompetenzaTerritorio) && Objects.equals(idTipoAreaProtetta, that.idTipoAreaProtetta) && Objects.equals(codAmministrativo, that.codAmministrativo) && Objects.equals(codGestoreFonte, that.codGestoreFonte) && Objects.equals(desAreaProtetta, that.desAreaProtetta) && Objects.equals(flgRicade, that.flgRicade);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idOggettoAreaProtetta, idOggettoIstanza, idCompetenzaTerritorio, idTipoAreaProtetta, codAmministrativo, codGestoreFonte, desAreaProtetta, flgRicade);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoIstanzaAreaProtettaDTO {\n");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",\n         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",\n         gestDataUpd:").append(gestDataUpd);
        sb.append(",\n         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",\n         gestUID:'").append(gestUID).append("'");
        sb.append(",\n         idOggettoAreaProtetta:").append(idOggettoAreaProtetta);
        sb.append(",\n         idOggettoIstanza:").append(idOggettoIstanza);
        sb.append(",\n         idCompetenzaTerritorio:").append(idCompetenzaTerritorio);
        sb.append(",\n         idTipoAreaProtetta:").append(idTipoAreaProtetta);
        sb.append(",\n         codAmministrativo:'").append(codAmministrativo).append("'");
        sb.append(",\n         codGestoreFonte:'").append(codGestoreFonte).append("'");
        sb.append(",\n         desAreaProtetta:'").append(desAreaProtetta).append("'");
        sb.append(",\n         flgRicade:").append(flgRicade);
        sb.append("}\n");
        return sb.toString();
    }
}