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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Compilante preferenza canale dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompilantePreferenzaCanaleExtendedDTO extends CompilantePreferenzaCanaleDTO implements Serializable {

    private CompilanteDTO compilante;

    private CanaleNotificaExtendedDTO canaleNotifica;

    /**
     * Gets compilante.
     *
     * @return the compilante
     */
    public CompilanteDTO getCompilante() {
        return compilante;
    }

    /**
     * Sets compilante.
     *
     * @param compilante the compilante
     */
    public void setCompilante(CompilanteDTO compilante) {
        this.compilante = compilante;
    }

    /**
     * Gets canale notifica.
     *
     * @return the canale notifica
     */
    public CanaleNotificaExtendedDTO getCanaleNotifica() {
        return canaleNotifica;
    }

    /**
     * Sets canale notifica.
     *
     * @param canaleNotifica the canale notifica
     */
    public void setCanaleNotifica(CanaleNotificaExtendedDTO canaleNotifica) {
        this.canaleNotifica = canaleNotifica;
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
        CompilantePreferenzaCanaleExtendedDTO that = (CompilantePreferenzaCanaleExtendedDTO) o;
        return Objects.equals(compilante, that.compilante) && Objects.equals(canaleNotifica, that.canaleNotifica);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), compilante, canaleNotifica);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "CompilantePreferenzaCanaleExtendedDTO {\n" +
                "         compilante:" + compilante +
                ",\n         canaleNotifica:" + canaleNotifica +
                super.toString() +
                "}\n";
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public CompilantePreferenzaCanaleDTO getDTO() {
        CompilantePreferenzaCanaleDTO dto = new CompilantePreferenzaCanaleDTO();
        dto.setIdCompilantePreferenzaCanale(this.getIdCompilantePreferenzaCanale());
        if (this.getCompilante() != null) {
            dto.setIdCompilante(this.getCompilante().getIdCompilante());
        }
        if (this.getCanaleNotifica() != null) {
            dto.setIdCanaleNotifica(this.getCanaleNotifica().getIdCanaleNotifica());
        }
        dto.setFlgAbilitato(this.getFlgAbilitato());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestDataUpd(this.getGestDataUpd());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestUID(this.getGestUID());
        return dto;
    }
}