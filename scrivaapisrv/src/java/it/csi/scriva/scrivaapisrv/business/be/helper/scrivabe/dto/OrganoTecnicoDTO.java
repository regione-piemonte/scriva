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
 * The type Organo tecnico dto.
 *
 * @author CSI PIEMONTE
 */
public class OrganoTecnicoDTO implements Serializable {

    @JsonProperty("des_direzione_competente")
    private String desDirezioneCompetente;

    @JsonProperty("des_rappresentante_nucleo")
    private String desRappresentanteNucleo;

    @JsonProperty("des_responsabile_procedimento")
    private String desResponsabileProcedimento;

    /**
     * Gets des direzione competente.
     *
     * @return the des direzione competente
     */
    public String getDesDirezioneCompetente() {
        return desDirezioneCompetente;
    }

    /**
     * Sets des direzione competente.
     *
     * @param desDirezioneCompetente the des direzione competente
     */
    public void setDesDirezioneCompetente(String desDirezioneCompetente) {
        this.desDirezioneCompetente = desDirezioneCompetente;
    }

    /**
     * Gets des rappresentante nucleo.
     *
     * @return the des rappresentante nucleo
     */
    public String getDesRappresentanteNucleo() {
        return desRappresentanteNucleo;
    }

    /**
     * Sets des rappresentante nucleo.
     *
     * @param desRappresentanteNucleo the des rappresentante nucleo
     */
    public void setDesRappresentanteNucleo(String desRappresentanteNucleo) {
        this.desRappresentanteNucleo = desRappresentanteNucleo;
    }

    /**
     * Gets des responsabile procedimento.
     *
     * @return the des responsabile procedimento
     */
    public String getDesResponsabileProcedimento() {
        return desResponsabileProcedimento;
    }

    /**
     * Sets des responsabile procedimento.
     *
     * @param desResponsabileProcedimento the des responsabile procedimento
     */
    public void setDesResponsabileProcedimento(String desResponsabileProcedimento) {
        this.desResponsabileProcedimento = desResponsabileProcedimento;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganoTecnicoDTO that = (OrganoTecnicoDTO) o;
        return Objects.equals(desDirezioneCompetente, that.desDirezioneCompetente) && Objects.equals(desRappresentanteNucleo, that.desRappresentanteNucleo) && Objects.equals(desResponsabileProcedimento, that.desResponsabileProcedimento);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(desDirezioneCompetente, desRappresentanteNucleo, desResponsabileProcedimento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrganoTecnicoDTO {\n");
        sb.append("         desDirezioneCompetente:'").append(desDirezioneCompetente).append("'");
        sb.append(",\n         desRappresentanteNucleo:'").append(desRappresentanteNucleo).append("'");
        sb.append(",\n         desResponsabileProcedimento:'").append(desResponsabileProcedimento).append("'");
        sb.append("}\n");
        return sb.toString();
    }

}