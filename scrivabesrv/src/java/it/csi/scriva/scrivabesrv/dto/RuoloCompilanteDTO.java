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
 * The type Ruolo compilante dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RuoloCompilanteDTO implements Serializable {

    @JsonProperty("id_ruolo_compilante")
    private Long idRuoloCompilante;

    @JsonProperty("cod_ruolo_compilante")
    private String codiceRuoloCompilante;

    @JsonProperty("des_ruolo_compilante")
    private String descrizioneRuoloCompilante;

    @JsonProperty("flg_modulo_delega")
    private Boolean flgModuloDelega;

    @JsonProperty("flg_modulo_procura")
    private Boolean flgModuloProcura;

    /**
     * Gets id ruolo compilante.
     *
     * @return the id ruolo compilante
     */
    public Long getIdRuoloCompilante() {
        return idRuoloCompilante;
    }

    /**
     * Sets id ruolo compilante.
     *
     * @param idRuoloCompilante the id ruolo compilante
     */
    public void setIdRuoloCompilante(Long idRuoloCompilante) {
        this.idRuoloCompilante = idRuoloCompilante;
    }

    /**
     * Gets codice ruolo compilante.
     *
     * @return the codice ruolo compilante
     */
    public String getCodiceRuoloCompilante() {
        return codiceRuoloCompilante;
    }

    /**
     * Sets codice ruolo compilante.
     *
     * @param codiceRuoloCompilante the codice ruolo compilante
     */
    public void setCodiceRuoloCompilante(String codiceRuoloCompilante) {
        this.codiceRuoloCompilante = codiceRuoloCompilante;
    }

    /**
     * Gets descrizione ruolo compilante.
     *
     * @return the descrizione ruolo compilante
     */
    public String getDescrizioneRuoloCompilante() {
        return descrizioneRuoloCompilante;
    }

    /**
     * Sets descrizione ruolo compilante.
     *
     * @param descrizioneRuoloCompilante the descrizione ruolo compilante
     */
    public void setDescrizioneRuoloCompilante(String descrizioneRuoloCompilante) {
        this.descrizioneRuoloCompilante = descrizioneRuoloCompilante;
    }

    /**
     * Gets flg modulo delega.
     *
     * @return the flg modulo delega
     */
    public Boolean getFlgModuloDelega() {
        return flgModuloDelega;
    }

    /**
     * Sets flg modulo delega.
     *
     * @param flgModuloDelega the flg modulo delega
     */
    public void setFlgModuloDelega(Boolean flgModuloDelega) {
        this.flgModuloDelega = flgModuloDelega;
    }

    /**
     * Gets flg modulo procura.
     *
     * @return the flg modulo procura
     */
    public Boolean getFlgModuloProcura() {
        return flgModuloProcura;
    }

    /**
     * Sets flg modulo procura.
     *
     * @param flgModuloProcura the flg modulo procura
     */
    public void setFlgModuloProcura(Boolean flgModuloProcura) {
        this.flgModuloProcura = flgModuloProcura;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codiceRuoloCompilante, descrizioneRuoloCompilante, idRuoloCompilante, flgModuloDelega, flgModuloProcura);
    }

    /**
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RuoloCompilanteDTO other = (RuoloCompilanteDTO) obj;
        return Objects.equals(codiceRuoloCompilante, other.codiceRuoloCompilante) && Objects.equals(descrizioneRuoloCompilante, other.descrizioneRuoloCompilante) && Objects.equals(idRuoloCompilante, other.idRuoloCompilante) && Objects.equals(flgModuloDelega, other.flgModuloDelega) && Objects.equals(flgModuloProcura, other.flgModuloProcura);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RuoloCompilanteDTO {\n");
        sb.append("         idRuoloCompilante:").append(idRuoloCompilante);
        sb.append(",\n         codiceRuoloCompilante:'").append(codiceRuoloCompilante).append("'");
        sb.append(",\n         descrizioneRuoloCompilante:'").append(descrizioneRuoloCompilante).append("'");
        sb.append(",\n         flgModuloDelega:").append(flgModuloDelega);
        sb.append(",\n         flgModuloProcura:").append(flgModuloProcura);
        sb.append("}\n");
        return sb.toString();
    }
}