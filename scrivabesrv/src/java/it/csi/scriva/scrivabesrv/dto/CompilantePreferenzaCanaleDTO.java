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

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Compilante preferenza canale dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompilantePreferenzaCanaleDTO extends BaseDTO implements Serializable {

    private Long idCompilantePreferenzaCanale;

    private Long idCompilante;

    private Long idCanaleNotifica;

    private Boolean flgAbilitato;

    /**
     * Gets id compilante preferenza canale.
     *
     * @return the id compilante preferenza canale
     */
    public Long getIdCompilantePreferenzaCanale() {
        return idCompilantePreferenzaCanale;
    }

    /**
     * Sets id compilante preferenza canale.
     *
     * @param idCompilantePreferenzaCanale the id compilante preferenza canale
     */
    public void setIdCompilantePreferenzaCanale(Long idCompilantePreferenzaCanale) {
        this.idCompilantePreferenzaCanale = idCompilantePreferenzaCanale;
    }

    /**
     * Gets id compilante.
     *
     * @return the id compilante
     */
    public Long getIdCompilante() {
        return idCompilante;
    }

    /**
     * Sets id compilante.
     *
     * @param idCompilante the id compilante
     */
    public void setIdCompilante(Long idCompilante) {
        this.idCompilante = idCompilante;
    }

    /**
     * Gets id canale notifica.
     *
     * @return the id canale notifica
     */
    public Long getIdCanaleNotifica() {
        return idCanaleNotifica;
    }

    /**
     * Sets id canale notifica.
     *
     * @param idCanaleNotifica the id canale notifica
     */
    public void setIdCanaleNotifica(Long idCanaleNotifica) {
        this.idCanaleNotifica = idCanaleNotifica;
    }

    /**
     * Gets flg abilitato.
     *
     * @return the flg abilitato
     */
    public Boolean getFlgAbilitato() {
        return flgAbilitato;
    }

    /**
     * Sets flg abilitato.
     *
     * @param flgAbilitato the flg abilitato
     */
    public void setFlgAbilitato(Boolean flgAbilitato) {
        this.flgAbilitato = flgAbilitato;
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
        CompilantePreferenzaCanaleDTO that = (CompilantePreferenzaCanaleDTO) o;
        return Objects.equals(idCompilantePreferenzaCanale, that.idCompilantePreferenzaCanale) && Objects.equals(idCompilante, that.idCompilante) && Objects.equals(idCanaleNotifica, that.idCanaleNotifica) && Objects.equals(flgAbilitato, that.flgAbilitato);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idCompilantePreferenzaCanale, idCompilante, idCanaleNotifica, flgAbilitato);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "CompilantePreferenzaCanaleDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idCompilantePreferenzaCanale:" + idCompilantePreferenzaCanale +
                ",\n         idCompilante:" + idCompilante +
                ",\n         idCanaleNotifica:" + idCanaleNotifica +
                ",\n         flgAbilitato:" + flgAbilitato +
                "}\n";
    }
}