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
 * The type Adempimento ruolo compilante dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdempimentoRuoloCompilanteDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_ruolo_compilante")
    private Long idRuoloCompilante;

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    @JsonProperty("flg_popola_richiedente")
    private Boolean flgPopolaRichiedente;

    @JsonProperty("flg_modulo_delega")
    private Boolean flgModuloDelega;

    @JsonProperty("flg_modulo_procura")
    private Boolean flgModuloProcura;

    @JsonProperty("flg_ruolo_default")
    private Boolean flgRuoloDefault;

    /**
     * Gets id ruolo compilante.
     *
     * @return idRuoloCompilante id ruolo compilante
     */
    public Long getIdRuoloCompilante() {
        return idRuoloCompilante;
    }

    /**
     * Sets id ruolo compilante.
     *
     * @param idRuoloCompilante idRuoloCompilante
     */
    public void setIdRuoloCompilante(Long idRuoloCompilante) {
        this.idRuoloCompilante = idRuoloCompilante;
    }

    /**
     * Gets id adempimento.
     *
     * @return idAdempimento id adempimento
     */
    public Long getIdAdempimento() {
        return idAdempimento;
    }

    /**
     * Sets id adempimento.
     *
     * @param idAdempimento idAdempimento
     */
    public void setIdAdempimento(Long idAdempimento) {
        this.idAdempimento = idAdempimento;
    }

    /**
     * Gets flg popola richiedente.
     *
     * @return flgPopolaRichiedente flg popola richiedente
     */
    public Boolean getFlgPopolaRichiedente() {
        return flgPopolaRichiedente;
    }

    /**
     * Sets flg popola richiedente.
     *
     * @param flgPopolaRichiedente flgPopolaRichiedente
     */
    public void setFlgPopolaRichiedente(Boolean flgPopolaRichiedente) {
        this.flgPopolaRichiedente = flgPopolaRichiedente;
    }

    /**
     * Gets flg modulo delega.
     *
     * @return flgModuloDelega flg modulo delega
     */
    public Boolean getFlgModuloDelega() {
        return flgModuloDelega;
    }

    /**
     * Sets flg modulo delega.
     *
     * @param flgModuloDelega flgModuloDelega
     */
    public void setFlgModuloDelega(Boolean flgModuloDelega) {
        this.flgModuloDelega = flgModuloDelega;
    }

    /**
     * Gets flg modulo procura.
     *
     * @return flgModuloProcura flg modulo procura
     */
    public Boolean getFlgModuloProcura() {
        return flgModuloProcura;
    }

    /**
     * Sets flg modulo procura.
     *
     * @param flgModuloProcura flgModuloProcura
     */
    public void setFlgModuloProcura(Boolean flgModuloProcura) {
        this.flgModuloProcura = flgModuloProcura;
    }

    /**
     * Gets flg ruolo default.
     *
     * @return flgRuoloDefault flg ruolo default
     */
    public Boolean getFlgRuoloDefault() {
        return flgRuoloDefault;
    }

    /**
     * Sets flg ruolo default.
     *
     * @param flgRuoloDefault flgRuoloDefault
     */
    public void setFlgRuoloDefault(Boolean flgRuoloDefault) {
        this.flgRuoloDefault = flgRuoloDefault;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(flgModuloDelega, flgModuloProcura, flgPopolaRichiedente, flgRuoloDefault, idRuoloCompilante, idAdempimento);
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
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AdempimentoRuoloCompilanteDTO other = (AdempimentoRuoloCompilanteDTO) obj;
        return Objects.equals(flgModuloDelega, other.flgModuloDelega)
                && Objects.equals(flgModuloProcura, other.flgModuloProcura)
                && Objects.equals(flgPopolaRichiedente, other.flgPopolaRichiedente)
                && Objects.equals(flgRuoloDefault, other.flgRuoloDefault)
                && Objects.equals(idRuoloCompilante, other.idRuoloCompilante)
                && Objects.equals(idAdempimento, other.idAdempimento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AdempimentoRuoloCompilanteDTO {\n    idRuoloCompilante: ").append(idRuoloCompilante).append("\n    idAdempimento: ").append(idAdempimento).append("\n    flgPopolaRichiedente: ").append(flgPopolaRichiedente).append("\n    flgModuloDelega: ").append(flgModuloDelega).append("\n    flgModuloProcura: ").append(flgModuloProcura).append("\n    flgRuoloDefault: ").append(flgRuoloDefault).append("\n}");
        return builder.toString();
    }

}