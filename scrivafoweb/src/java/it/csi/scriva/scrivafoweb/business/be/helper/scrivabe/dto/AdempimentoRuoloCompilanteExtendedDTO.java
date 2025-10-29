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
import java.util.Objects;

/**
 * @author CSI PIEMONTE
 */
public class AdempimentoRuoloCompilanteExtendedDTO implements Serializable {

    @JsonProperty("flg_popola_richiedente")
    private Boolean flgPopolaRichiedente;

    @JsonProperty("adempimento")
    private AdempimentoExtendedDTO adempimento;

    @JsonProperty("ruolo_compilante")
    private RuoloCompilanteDTO ruoloCompilante;

    @JsonProperty("flg_modulo_delega")
    private Boolean flgModuloDelega;

    @JsonProperty("flg_modulo_procura")
    private Boolean flgModuloProcura;

    @JsonProperty("flg_ruolo_default")
    private Boolean flgRuoloDefault;

    /**
     * @return flgPopolaRichiedente
     */
    public Boolean getFlgPopolaRichiedente() {
        return flgPopolaRichiedente;
    }

    /**
     * @param flgPopolaRichiedente flgPopolaRichiedente
     */
    public void setFlgPopolaRichiedente(Boolean flgPopolaRichiedente) {
        this.flgPopolaRichiedente = flgPopolaRichiedente;
    }

    /**
     * @return adempimento
     */
    public AdempimentoExtendedDTO getAdempimento() {
        return adempimento;
    }

    /**
     * @param adempimento adempimento
     */
    public void setAdempimento(AdempimentoExtendedDTO adempimento) {
        this.adempimento = adempimento;
    }

    /**
     * @return ruoloCompilante
     */
    public RuoloCompilanteDTO getRuoloCompilante() {
        return ruoloCompilante;
    }

    /**
     * @param ruoloCompilante ruoloCompilante
     */
    public void setRuoloCompilante(RuoloCompilanteDTO ruoloCompilante) {
        this.ruoloCompilante = ruoloCompilante;
    }

    /**
     * @return flgModuloDelega
     */
    public Boolean getFlgModuloDelega() {
        return flgModuloDelega;
    }

    /**
     * @param flgModuloDelega flgModuloDelega
     */
    public void setFlgModuloDelega(Boolean flgModuloDelega) {
        this.flgModuloDelega = flgModuloDelega;
    }

    /**
     * @return flgModuloProcura
     */
    public Boolean getFlgModuloProcura() {
        return flgModuloProcura;
    }

    /**
     * @param flgModuloProcura flgModuloProcura
     */
    public void setFlgModuloProcura(Boolean flgModuloProcura) {
        this.flgModuloProcura = flgModuloProcura;
    }

    /**
     * @return flgRuoloDefault
     */
    public Boolean getFlgRuoloDefault() {
        return flgRuoloDefault;
    }

    /**
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
        return Objects.hash(flgModuloDelega, flgModuloProcura, flgPopolaRichiedente, flgRuoloDefault, ruoloCompilante, adempimento);
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
        AdempimentoRuoloCompilanteExtendedDTO other = (AdempimentoRuoloCompilanteExtendedDTO) obj;
        return Objects.equals(flgModuloDelega, other.flgModuloDelega) && Objects.equals(flgModuloProcura, other.flgModuloProcura) && Objects.equals(flgPopolaRichiedente, other.flgPopolaRichiedente) && Objects.equals(flgRuoloDefault, other.flgRuoloDefault) && Objects.equals(ruoloCompilante, other.ruoloCompilante) && Objects.equals(adempimento, other.adempimento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ProcedimentoRuoloCompilanteExtendedDTO {\n    flgPopolaRichiedente: ").append(flgPopolaRichiedente).append("\n    adempimento: ").append(adempimento).append("\n    ruoloCompilante: ").append(ruoloCompilante).append("\n    flgModuloDelega: ").append(flgModuloDelega).append("\n    flgModuloProcura: ").append(flgModuloProcura).append("\n    flgRuoloDefault: ").append(flgRuoloDefault).append("\n}");
        return builder.toString();
    }

}