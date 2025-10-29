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
 * The type Tipo abilitazione dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoAbilitazioneDTO implements Serializable {

    @JsonProperty("id_tipo_abilitazione")
    private Long idTipoAbilitazione;

    @JsonProperty("cod_tipo_abilitazione")
    private String codTipoAbilitazione;

    @JsonProperty("des_tipo_abilitazione")
    private String desTipoAbilitazione;

    /**
     * Gets id tipo abilitazione.
     *
     * @return idTipoAbilitazione id tipo abilitazione
     */
    public Long getIdTipoAbilitazione() {
        return idTipoAbilitazione;
    }

    /**
     * Sets id tipo abilitazione.
     *
     * @param idTipoAbilitazione idTipoAbilitazione
     */
    public void setIdTipoAbilitazione(Long idTipoAbilitazione) {
        this.idTipoAbilitazione = idTipoAbilitazione;
    }

    /**
     * Gets cod tipo abilitazione.
     *
     * @return codTipoAbilitazione cod tipo abilitazione
     */
    public String getCodTipoAbilitazione() {
        return codTipoAbilitazione;
    }

    /**
     * Sets cod tipo abilitazione.
     *
     * @param codTipoAbilitazione codTipoAbilitazione
     */
    public void setCodTipoAbilitazione(String codTipoAbilitazione) {
        this.codTipoAbilitazione = codTipoAbilitazione;
    }

    /**
     * Gets des tipo abilitazione.
     *
     * @return desTipoAbilitazione des tipo abilitazione
     */
    public String getDesTipoAbilitazione() {
        return desTipoAbilitazione;
    }

    /**
     * Sets des tipo abilitazione.
     *
     * @param desTipoAbilitazione desTipoAbilitazione
     */
    public void setDesTipoAbilitazione(String desTipoAbilitazione) {
        this.desTipoAbilitazione = desTipoAbilitazione;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoAbilitazioneDTO that = (TipoAbilitazioneDTO) o;
        return Objects.equals(idTipoAbilitazione, that.idTipoAbilitazione) && Objects.equals(codTipoAbilitazione, that.codTipoAbilitazione) && Objects.equals(desTipoAbilitazione, that.desTipoAbilitazione);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTipoAbilitazione, codTipoAbilitazione, desTipoAbilitazione);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoAbilitazione {");
        sb.append("         idTipoAbilitazione:").append(idTipoAbilitazione);
        sb.append(",         codTipoAbilitazione:'").append(codTipoAbilitazione).append("'");
        sb.append(",         desTipoAbilitazione:'").append(desTipoAbilitazione).append("'");
        sb.append("}");
        return sb.toString();
    }
}