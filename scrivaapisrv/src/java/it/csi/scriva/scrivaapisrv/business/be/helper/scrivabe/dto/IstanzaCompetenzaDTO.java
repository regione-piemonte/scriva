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
import java.util.Date;
import java.util.Objects;

public class IstanzaCompetenzaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_competenza_territorio")
    private Long idCompetenzaTerritorio;

    @JsonProperty("data_inizio_validita")
    private Date dataInizioValidita;

    @JsonProperty("data_fine_validita")
    private Date dataFineValidita;

    @JsonProperty("flg_autorita_principale")
    private Boolean flgAutoritaPrincipale;

    @JsonProperty("flg_autorita_assegnata_bo")
    private Boolean flgAutoritaAssegnataBO;

    /**
     * @return idIstanza
     */
    public Long getIdIstanza() {
        return idIstanza;
    }

    /**
     * @param idIstanza idIstanza
     */
    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    /**
     * @return idCompetenzaTerritorio
     */
    public Long getIdCompetenzaTerritorio() {
        return idCompetenzaTerritorio;
    }

    /**
     * @param idCompetenzaTerritorio idCompetenzaTerritorio
     */
    public void setIdCompetenzaTerritorio(Long idCompetenzaTerritorio) {
        this.idCompetenzaTerritorio = idCompetenzaTerritorio;
    }

    /**
     * @return dataInizioValidita
     */
    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * @param dataInizioValidita dataInizioValidita
     */
    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    /**
     * @return dataFineValidita
     */
    public Date getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * @param dataFineValidita dataFineValidita
     */
    public void setDataFineValidita(Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    /**
     * @return flgAutoritaPrincipale
     */
    public Boolean getFlgAutoritaPrincipale() {
        return flgAutoritaPrincipale;
    }

    /**
     * @param flgAutoritaPrincipale flgAutoritaPrincipale
     */
    public void setFlgAutoritaPrincipale(Boolean flgAutoritaPrincipale) {
        this.flgAutoritaPrincipale = flgAutoritaPrincipale;
    }

    /**
     * @return flgAutoritaAssegnataBO
     */
    public Boolean getFlgAutoritaAssegnataBO() {
        return flgAutoritaAssegnataBO;
    }

    /**
     * @param flgAutoritaAssegnataBO flgAutoritaAssegnataBO
     */
    public void setFlgAutoritaAssegnataBO(Boolean flgAutoritaAssegnataBO) {
        this.flgAutoritaAssegnataBO = flgAutoritaAssegnataBO;
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
        IstanzaCompetenzaDTO that = (IstanzaCompetenzaDTO) o;
        return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idCompetenzaTerritorio, that.idCompetenzaTerritorio) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataFineValidita, that.dataFineValidita) && Objects.equals(flgAutoritaPrincipale, that.flgAutoritaPrincipale) && Objects.equals(flgAutoritaAssegnataBO, that.flgAutoritaAssegnataBO);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idIstanza, idCompetenzaTerritorio, dataInizioValidita, dataFineValidita, flgAutoritaPrincipale, flgAutoritaAssegnataBO);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IstanzaCompetenzaDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         idIstanza:").append(idIstanza);
        sb.append(",         idCompetenzaTerritorio:").append(idCompetenzaTerritorio);
        sb.append(",         dataInizioValidita:").append(dataInizioValidita);
        sb.append(",         dataFineValidita:").append(dataFineValidita);
        sb.append(",         flgAutoritaPrincipale:").append(flgAutoritaPrincipale);
        sb.append(",         flgAutoritaAssegnataBO:").append(flgAutoritaAssegnataBO);
        sb.append("}");
        return sb.toString();
    }
}