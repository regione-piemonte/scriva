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
import java.sql.Date;
import java.util.Objects;

/**
 * The type Funzionario dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FunzionarioDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_funzionario")
    private Long idFunzionario;

    @JsonProperty("cf_funzionario")
    private String cfFunzionario;

    @JsonProperty("nome_funzionario")
    private String nomeFunzionario;

    @JsonProperty("cognome_funzionario")
    private String cognomeFunzionario;

    @JsonProperty("num_telefono_funzionario")
    private String numTelefonoFunzionario;

    @JsonProperty("des_email_funzionario")
    private String desEmailFunzionario;

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
     * Gets cf funzionario.
     *
     * @return the cf funzionario
     */
    public String getCfFunzionario() {
        return cfFunzionario;
    }

    /**
     * Sets cf funzionario.
     *
     * @param cfFunzionario the cf funzionario
     */
    public void setCfFunzionario(String cfFunzionario) {
        this.cfFunzionario = cfFunzionario;
    }

    /**
     * Gets nome funzionario.
     *
     * @return the nome funzionario
     */
    public String getNomeFunzionario() {
        return nomeFunzionario;
    }

    /**
     * Sets nome funzionario.
     *
     * @param nomeFunzionario the nome funzionario
     */
    public void setNomeFunzionario(String nomeFunzionario) {
        this.nomeFunzionario = nomeFunzionario;
    }

    /**
     * Gets cognome funzionario.
     *
     * @return the cognome funzionario
     */
    public String getCognomeFunzionario() {
        return cognomeFunzionario;
    }

    /**
     * Sets cognome funzionario.
     *
     * @param cognomeFunzionario the cognome funzionario
     */
    public void setCognomeFunzionario(String cognomeFunzionario) {
        this.cognomeFunzionario = cognomeFunzionario;
    }

    /**
     * Gets num telefono funzionario.
     *
     * @return the num telefono funzionario
     */
    public String getNumTelefonoFunzionario() {
        return numTelefonoFunzionario;
    }

    /**
     * Sets num telefono funzionario.
     *
     * @param numTelefonoFunzionario the num telefono funzionario
     */
    public void setNumTelefonoFunzionario(String numTelefonoFunzionario) {
        this.numTelefonoFunzionario = numTelefonoFunzionario;
    }

    /**
     * Gets des email funzionario.
     *
     * @return the des email funzionario
     */
    public String getDesEmailFunzionario() {
        return desEmailFunzionario;
    }

    /**
     * Sets des email funzionario.
     *
     * @param desEmailFunzionario the des email funzionario
     */
    public void setDesEmailFunzionario(String desEmailFunzionario) {
        this.desEmailFunzionario = desEmailFunzionario;
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
        if (!super.equals(o)) return false;
        FunzionarioDTO that = (FunzionarioDTO) o;
        return Objects.equals(idFunzionario, that.idFunzionario) && Objects.equals(cfFunzionario, that.cfFunzionario) && Objects.equals(nomeFunzionario, that.nomeFunzionario) && Objects.equals(cognomeFunzionario, that.cognomeFunzionario) && Objects.equals(numTelefonoFunzionario, that.numTelefonoFunzionario) && Objects.equals(desEmailFunzionario, that.desEmailFunzionario) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataFineValidita, that.dataFineValidita);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idFunzionario, cfFunzionario, nomeFunzionario, cognomeFunzionario, numTelefonoFunzionario, desEmailFunzionario, dataInizioValidita, dataFineValidita);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FunzionarioDTO {");
        sb.append("         idFunzionario:").append(idFunzionario);
        sb.append(",         cfFunzionario:'").append(cfFunzionario).append("'");
        sb.append(",         nomeFunzionario:'").append(nomeFunzionario).append("'");
        sb.append(",         cognomeFunzionario:'").append(cognomeFunzionario).append("'");
        sb.append(",         numTelefonoFunzionario:'").append(numTelefonoFunzionario).append("'");
        sb.append(",         desEmailFunzionario:'").append(desEmailFunzionario).append("'");
        sb.append(",         dataInizioValidita:").append(dataInizioValidita);
        sb.append(",         dataFineValidita:").append(dataFineValidita);
        sb.append(",         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append("}");
        return sb.toString();
    }
}