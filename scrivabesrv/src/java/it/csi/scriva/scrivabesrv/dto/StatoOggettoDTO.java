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
import java.util.Objects;

/**
 * The type Stato oggetto dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatoOggettoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_stato_oggetto")
    private Long idStatoOggetto;

    @JsonProperty("cod_stato_oggetto")
    private String codStatoOggetto;

    @JsonProperty("des_stato_oggetto")
    private String desStatoOggetto;

    /**
     * Gets id stato oggetto.
     *
     * @return the id stato oggetto
     */
    public Long getIdStatoOggetto() {
        return idStatoOggetto;
    }

    /**
     * Sets id stato oggetto.
     *
     * @param idStatoOggetto the id stato oggetto
     */
    public void setIdStatoOggetto(Long idStatoOggetto) {
        this.idStatoOggetto = idStatoOggetto;
    }

    /**
     * Gets cod stato oggetto.
     *
     * @return the cod stato oggetto
     */
    public String getCodStatoOggetto() {
        return codStatoOggetto;
    }

    /**
     * Sets cod stato oggetto.
     *
     * @param codStatoOggetto the cod stato oggetto
     */
    public void setCodStatoOggetto(String codStatoOggetto) {
        this.codStatoOggetto = codStatoOggetto;
    }

    /**
     * Gets des stato oggetto.
     *
     * @return the des stato oggetto
     */
    public String getDesStatoOggetto() {
        return desStatoOggetto;
    }

    /**
     * Sets des stato oggetto.
     *
     * @param desStatoOggetto the des stato oggetto
     */
    public void setDesStatoOggetto(String desStatoOggetto) {
        this.desStatoOggetto = desStatoOggetto;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        StatoOggettoDTO that = (StatoOggettoDTO) o;
        return Objects.equals(idStatoOggetto, that.idStatoOggetto) && Objects.equals(codStatoOggetto, that.codStatoOggetto) && Objects.equals(desStatoOggetto, that.desStatoOggetto);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idStatoOggetto, codStatoOggetto, desStatoOggetto);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatoOggettoDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         idStatoOggetto:").append(idStatoOggetto);
        sb.append(",         codStatoOggetto:'").append(codStatoOggetto).append("'");
        sb.append(",         desStatoOggetto:'").append(desStatoOggetto).append("'");
        sb.append("}");
        return sb.toString();
    }
}