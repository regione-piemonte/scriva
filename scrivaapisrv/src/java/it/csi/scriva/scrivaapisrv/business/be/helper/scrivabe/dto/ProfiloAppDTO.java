/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class ProfiloAppDTO implements Serializable {

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
     * @return idProfiloApp
     */
    public Long getIdProfiloApp() {
        return idProfiloApp;
    }

    /**
     * @param idProfiloApp idProfiloApp
     */
    public void setIdProfiloApp(Long idProfiloApp) {
        this.idProfiloApp = idProfiloApp;
    }

    /**
     * @return idComponenteApp
     */
    public Long getIdComponenteApp() {
        return idComponenteApp;
    }

    /**
     * @param idComponenteApp idComponenteApp
     */
    public void setIdComponenteApp(Long idComponenteApp) {
        this.idComponenteApp = idComponenteApp;
    }

    /**
     * @return codProfiloApp
     */
    public String getCodProfiloApp() {
        return codProfiloApp;
    }

    /**
     * @param codProfiloApp codProfiloApp
     */
    public void setCodProfiloApp(String codProfiloApp) {
        this.codProfiloApp = codProfiloApp;
    }

    /**
     * @return desProfiloApp
     */
    public String getDesProfiloApp() {
        return desProfiloApp;
    }

    /**
     * @param desProfiloApp desProfiloApp
     */
    public void setDesProfiloApp(String desProfiloApp) {
        this.desProfiloApp = desProfiloApp;
    }

    /**
     * @return flgProfiloIride
     */
    public Boolean getFlgProfiloIride() {
        return flgProfiloIride;
    }

    /**
     * @param flgProfiloIride flgProfiloIride
     */
    public void setFlgProfiloIride(Boolean flgProfiloIride) {
        this.flgProfiloIride = flgProfiloIride;
    }

    /**
     * @return flgCompetenza
     */
    public Boolean getFlgCompetenza() {
        return flgCompetenza;
    }

    /**
     * @param flgCompetenza flgCompetenza
     */
    public void setFlgCompetenza(Boolean flgCompetenza) {
        this.flgCompetenza = flgCompetenza;
    }

    /**
     * @return ordinamentoProfiloApp
     */
    public Integer getOrdinamentoProfiloApp() {
        return ordinamentoProfiloApp;
    }

    /**
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
        return Objects.equals(idProfiloApp, that.idProfiloApp) && Objects.equals(idComponenteApp, that.idComponenteApp) && Objects.equals(codProfiloApp, that.codProfiloApp) && Objects.equals(desProfiloApp, that.desProfiloApp) && Objects.equals(flgProfiloIride, that.flgProfiloIride) && Objects.equals(flgCompetenza, that.flgCompetenza) && Objects.equals(ordinamentoProfiloApp, that.ordinamentoProfiloApp);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idProfiloApp, idComponenteApp, codProfiloApp, desProfiloApp, flgProfiloIride, flgCompetenza, ordinamentoProfiloApp);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProfiloApp {");
        sb.append("         idProfiloApp:").append(idProfiloApp);
        sb.append(",         idComponenteApp:").append(idComponenteApp);
        sb.append(",         codProfiloApp:'").append(codProfiloApp).append("'");
        sb.append(",         desProfiloApp:'").append(desProfiloApp).append("'");
        sb.append(",         flgProfiloIride:").append(flgProfiloIride);
        sb.append(",         flgCompetenza:").append(flgCompetenza);
        sb.append(",         ordinamentoProfiloApp:").append(ordinamentoProfiloApp);
        sb.append("}");
        return sb.toString();
    }
}