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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Profilo app dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfiloAppDTO implements Serializable {

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_profilo_app")
    private Long idProfiloApp;

    @JsonProperty("id_componente_app")
    private Long idComponenteApp;

    @JsonProperty("cod_profilo_app")
    private String codProfiloApp;

    @JsonProperty("des_profilo_app")
    private String desProfiloApp;

    @JsonProperty("flg_profilo_iride")
    private Boolean flgProfiloIride;

    @JsonProperty("flg_competenza")
    private Boolean flgCompetenza;

    @JsonIgnore
    private Integer ordinamentoProfiloApp;

    /**
     * Gets id istanza.
     *
     * @return the id istanza
     */
    public Long getIdIstanza() {
        return idIstanza;
    }

    /**
     * Sets id istanza.
     *
     * @param idIstanza the id istanza
     */
    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    /**
     * Gets id profilo app.
     *
     * @return idProfiloApp id profilo app
     */
    public Long getIdProfiloApp() {
        return idProfiloApp;
    }

    /**
     * Sets id profilo app.
     *
     * @param idProfiloApp idProfiloApp
     */
    public void setIdProfiloApp(Long idProfiloApp) {
        this.idProfiloApp = idProfiloApp;
    }

    /**
     * Gets id componente app.
     *
     * @return idComponenteApp id componente app
     */
    public Long getIdComponenteApp() {
        return idComponenteApp;
    }

    /**
     * Sets id componente app.
     *
     * @param idComponenteApp idComponenteApp
     */
    public void setIdComponenteApp(Long idComponenteApp) {
        this.idComponenteApp = idComponenteApp;
    }

    /**
     * Gets cod profilo app.
     *
     * @return codProfiloApp cod profilo app
     */
    public String getCodProfiloApp() {
        return codProfiloApp;
    }

    /**
     * Sets cod profilo app.
     *
     * @param codProfiloApp codProfiloApp
     */
    public void setCodProfiloApp(String codProfiloApp) {
        this.codProfiloApp = codProfiloApp;
    }

    /**
     * Gets des profilo app.
     *
     * @return desProfiloApp des profilo app
     */
    public String getDesProfiloApp() {
        return desProfiloApp;
    }

    /**
     * Sets des profilo app.
     *
     * @param desProfiloApp desProfiloApp
     */
    public void setDesProfiloApp(String desProfiloApp) {
        this.desProfiloApp = desProfiloApp;
    }

    /**
     * Gets flg profilo iride.
     *
     * @return flgProfiloIride flg profilo iride
     */
    public Boolean getFlgProfiloIride() {
        return flgProfiloIride;
    }

    /**
     * Sets flg profilo iride.
     *
     * @param flgProfiloIride flgProfiloIride
     */
    public void setFlgProfiloIride(Boolean flgProfiloIride) {
        this.flgProfiloIride = flgProfiloIride;
    }

    /**
     * Gets flg competenza.
     *
     * @return flgCompetenza flg competenza
     */
    public Boolean getFlgCompetenza() {
        return flgCompetenza;
    }

    /**
     * Sets flg competenza.
     *
     * @param flgCompetenza flgCompetenza
     */
    public void setFlgCompetenza(Boolean flgCompetenza) {
        this.flgCompetenza = flgCompetenza;
    }

    /**
     * Gets ordinamento profilo app.
     *
     * @return ordinamentoProfiloApp ordinamento profilo app
     */
    public Integer getOrdinamentoProfiloApp() {
        return ordinamentoProfiloApp;
    }

    /**
     * Sets ordinamento profilo app.
     *
     * @param ordinamentoProfiloApp ordinamentoProfiloApp
     */
    public void setOrdinamentoProfiloApp(Integer ordinamentoProfiloApp) {
        this.ordinamentoProfiloApp = ordinamentoProfiloApp;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfiloAppDTO that = (ProfiloAppDTO) o;
        return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idProfiloApp, that.idProfiloApp) && Objects.equals(idComponenteApp, that.idComponenteApp) && Objects.equals(codProfiloApp, that.codProfiloApp) && Objects.equals(desProfiloApp, that.desProfiloApp) && Objects.equals(flgProfiloIride, that.flgProfiloIride) && Objects.equals(flgCompetenza, that.flgCompetenza) && Objects.equals(ordinamentoProfiloApp, that.ordinamentoProfiloApp);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idIstanza, idProfiloApp, idComponenteApp, codProfiloApp, desProfiloApp, flgProfiloIride, flgCompetenza, ordinamentoProfiloApp);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "ProfiloAppDTO {\n" +
                "         idIstanza:" + idIstanza +
                ",\n         idProfiloApp:" + idProfiloApp +
                ",\n         idComponenteApp:" + idComponenteApp +
                ",\n         codProfiloApp:'" + codProfiloApp + "'" +
                ",\n         desProfiloApp:'" + desProfiloApp + "'" +
                ",\n         flgProfiloIride:" + flgProfiloIride +
                ",\n         flgCompetenza:" + flgCompetenza +
                ",\n         ordinamentoProfiloApp:" + ordinamentoProfiloApp +
                "}\n";
    }

}