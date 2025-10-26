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
import java.util.Objects;

/**
 * The type Classe allegato dto.
 *
 * @author CSI PIEMONTE
 */
public class ClasseAllegatoDTO implements Serializable {

    @JsonProperty("id_classe_allegato")
    private Long idClasseAllegato;

    @JsonProperty("cod_classe_allegato")
    private String codClasseAllegato;

    @JsonProperty("des_classe_allegato")
    private String desClasseAllegato;

    @JsonProperty("ordinamento_classe_allegato")
    private Long ordinamentoClasseAllegato;

    @JsonProperty("flg_attivo")
    private Boolean flgAttivo;

    /**
     * Gets id classe allegato.
     *
     * @return the id classe allegato
     */
    public Long getIdClasseAllegato() {
        return idClasseAllegato;
    }

    /**
     * Sets id classe allegato.
     *
     * @param idClasseAllegato the id classe allegato
     */
    public void setIdClasseAllegato(Long idClasseAllegato) {
        this.idClasseAllegato = idClasseAllegato;
    }

    /**
     * Gets cod classe allegato.
     *
     * @return the cod classe allegato
     */
    public String getCodClasseAllegato() {
        return codClasseAllegato;
    }

    /**
     * Sets cod classe allegato.
     *
     * @param codClasseAllegato the cod classe allegato
     */
    public void setCodClasseAllegato(String codClasseAllegato) {
        this.codClasseAllegato = codClasseAllegato;
    }

    /**
     * Gets des classe allegato.
     *
     * @return the des classe allegato
     */
    public String getDesClasseAllegato() {
        return desClasseAllegato;
    }

    /**
     * Sets des classe allegato.
     *
     * @param desClasseAllegato the des classe allegato
     */
    public void setDesClasseAllegato(String desClasseAllegato) {
        this.desClasseAllegato = desClasseAllegato;
    }

    /**
     * Gets ordinamento classe allegato.
     *
     * @return the ordinamento classe allegato
     */
    public Long getOrdinamentoClasseAllegato() {
        return ordinamentoClasseAllegato;
    }

    /**
     * Sets ordinamento classe allegato.
     *
     * @param ordinamentoClasseAllegato the ordinamento classe allegato
     */
    public void setOrdinamentoClasseAllegato(Long ordinamentoClasseAllegato) {
        this.ordinamentoClasseAllegato = ordinamentoClasseAllegato;
    }

    /**
     * Gets flg attivo.
     *
     * @return the flg attivo
     */
    public Boolean getFlgAttivo() {
        return flgAttivo;
    }

    /**
     * Sets flg attivo.
     *
     * @param flgAttivo the flg attivo
     */
    public void setFlgAttivo(Boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClasseAllegatoDTO that = (ClasseAllegatoDTO) o;
        return Objects.equals(idClasseAllegato, that.idClasseAllegato) && Objects.equals(codClasseAllegato, that.codClasseAllegato) && Objects.equals(desClasseAllegato, that.desClasseAllegato) && Objects.equals(ordinamentoClasseAllegato, that.ordinamentoClasseAllegato) && Objects.equals(flgAttivo, that.flgAttivo);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idClasseAllegato, codClasseAllegato, desClasseAllegato, ordinamentoClasseAllegato, flgAttivo);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClasseAllegatoDTO {\n");
        sb.append("         idClasseAllegato:").append(idClasseAllegato);
        sb.append(",\n         codClasseAllegato:'").append(codClasseAllegato).append("'");
        sb.append(",\n         desClasseAllegato:'").append(desClasseAllegato).append("'");
        sb.append(",\n         ordinamentoClasseAllegato:").append(ordinamentoClasseAllegato);
        sb.append(",\n         flgAttivo:").append(flgAttivo);
        sb.append("}\n");
        return sb.toString();
    }
}