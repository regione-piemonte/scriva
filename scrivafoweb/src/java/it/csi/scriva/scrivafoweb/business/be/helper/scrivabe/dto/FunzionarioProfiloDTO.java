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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * The type Funzionario profilo dto.
 *
 * @author CSI PIEMONTE
 */
public class FunzionarioProfiloDTO implements Serializable {

    @JsonProperty("id_funzionario")
    private Long idFunzionario;

    @JsonProperty("id_profilo_app")
    private Long idProfiloApp;

    @JsonProperty("data_inizio_validita")
    private Date dataInizioValidita;

    @JsonProperty("data_fine_validita")
    private Date dataFineValidita;

    /**
     * Gets id funzionario.
     *
     * @return the id funzionario
     */
    public Long getIdFunzionario() {
        return idFunzionario;
    }

    /**
     * Sets id funzionario.
     *
     * @param idFunzionario the id funzionario
     */
    public void setIdFunzionario(Long idFunzionario) {
        this.idFunzionario = idFunzionario;
    }

    /**
     * Gets id profilo app.
     *
     * @return the id profilo app
     */
    public Long getIdProfiloApp() {
        return idProfiloApp;
    }

    /**
     * Sets id profilo app.
     *
     * @param idProfiloApp the id profilo app
     */
    public void setIdProfiloApp(Long idProfiloApp) {
        this.idProfiloApp = idProfiloApp;
    }

    /**
     * Gets data inizio validita.
     *
     * @return the data inizio validita
     */
    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Sets data inizio validita.
     *
     * @param dataInizioValidita the data inizio validita
     */
    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    /**
     * Gets data fine validita.
     *
     * @return the data fine validita
     */
    public Date getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Sets data fine validita.
     *
     * @param dataFineValidita the data fine validita
     */
    public void setDataFineValidita(Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunzionarioProfiloDTO that = (FunzionarioProfiloDTO) o;
        return Objects.equals(idFunzionario, that.idFunzionario) && Objects.equals(idProfiloApp, that.idProfiloApp) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataFineValidita, that.dataFineValidita);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFunzionario, idProfiloApp, dataInizioValidita, dataFineValidita);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FunzionarioProfiloDTO {");
        sb.append("         idFunzionario:").append(idFunzionario);
        sb.append(",         idProfiloApp:").append(idProfiloApp);
        sb.append(",         dataInizioValidita:").append(dataInizioValidita);
        sb.append(",         dataFineValidita:").append(dataFineValidita);
        sb.append("}");
        return sb.toString();
    }
}