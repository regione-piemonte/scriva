/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Stato procedimento statale dto.
 *
 * @author CSI PIEMONTE
 */
public class StatoProcedimentoStataleDTO implements Serializable {

    @JsonProperty("id_stato_proced_statale")
    private Long idStatoProcedStatale;

    @JsonProperty("cod_stato_proced_statale")
    private String codStatoProcedStatale;

    @JsonProperty("des_stato_proced_statale")
    private String desStatoProcedStatale;

    @JsonIgnore
    private String indVisibile;

    @JsonProperty("des_estesa_stato_proced_statale")
    private String desEstesaStatoProcedStatale;

    @JsonProperty("label_stato_proced_statale")
    private String labelStatoProcedStatale;

    /**
     * Gets id stato proced statale.
     *
     * @return the id stato proced statale
     */
    public Long getIdStatoProcedStatale() {
        return idStatoProcedStatale;
    }

    /**
     * Sets id stato proced statale.
     *
     * @param idStatoProcedStatale the id stato proced statale
     */
    public void setIdStatoProcedStatale(Long idStatoProcedStatale) {
        this.idStatoProcedStatale = idStatoProcedStatale;
    }

    /**
     * Gets cod stato proced statale.
     *
     * @return the cod stato proced statale
     */
    public String getCodStatoProcedStatale() {
        return codStatoProcedStatale;
    }

    /**
     * Sets cod stato proced statale.
     *
     * @param codStatoProcedStatale the cod stato proced statale
     */
    public void setCodStatoProcedStatale(String codStatoProcedStatale) {
        this.codStatoProcedStatale = codStatoProcedStatale;
    }

    /**
     * Gets des stato proced statale.
     *
     * @return the des stato proced statale
     */
    public String getDesStatoProcedStatale() {
        return desStatoProcedStatale;
    }

    /**
     * Sets des stato proced statale.
     *
     * @param desStatoProcedStatale the des stato proced statale
     */
    public void setDesStatoProcedStatale(String desStatoProcedStatale) {
        this.desStatoProcedStatale = desStatoProcedStatale;
    }

    /**
     * Gets ind visibile.
     *
     * @return the ind visibile
     */
    public String getIndVisibile() {
        return indVisibile;
    }

    /**
     * Sets ind visibile.
     *
     * @param indVisibile the ind visibile
     */
    public void setIndVisibile(String indVisibile) {
        this.indVisibile = indVisibile;
    }

    /**
     * Gets des estesa stato proced statale.
     *
     * @return the des estesa stato proced statale
     */
    public String getDesEstesaStatoProcedStatale() {
        return desEstesaStatoProcedStatale;
    }

    /**
     * Sets des estesa stato proced statale.
     *
     * @param desEstesaStatoProcedStatale the des estesa stato proced statale
     */
    public void setDesEstesaStatoProcedStatale(String desEstesaStatoProcedStatale) {
        this.desEstesaStatoProcedStatale = desEstesaStatoProcedStatale;
    }

    /**
     * Gets label stato proced statale.
     *
     * @return the label stato proced statale
     */
    public String getLabelStatoProcedStatale() {
        return labelStatoProcedStatale;
    }

    /**
     * Sets label stato proced statale.
     *
     * @param labelStatoProcedStatale the label stato proced statale
     */
    public void setLabelStatoProcedStatale(String labelStatoProcedStatale) {
        this.labelStatoProcedStatale = labelStatoProcedStatale;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatoProcedimentoStataleDTO that = (StatoProcedimentoStataleDTO) o;
        return Objects.equals(idStatoProcedStatale, that.idStatoProcedStatale) && Objects.equals(codStatoProcedStatale, that.codStatoProcedStatale) && Objects.equals(desStatoProcedStatale, that.desStatoProcedStatale) && Objects.equals(indVisibile, that.indVisibile) && Objects.equals(desEstesaStatoProcedStatale, that.desEstesaStatoProcedStatale) && Objects.equals(labelStatoProcedStatale, that.labelStatoProcedStatale);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idStatoProcedStatale, codStatoProcedStatale, desStatoProcedStatale, indVisibile, desEstesaStatoProcedStatale, labelStatoProcedStatale);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "StatoProcedimentoStataleDTO {\n" +
                "         idStatoProcedStatale:" + idStatoProcedStatale +
                ",\n         codStatoProcedStatale:'" + codStatoProcedStatale + "'" +
                ",\n         desStatoProcedStatale:'" + desStatoProcedStatale + "'" +
                ",\n         indVisibile:'" + indVisibile + "'" +
                ",\n         desEstesaStatoProcedStatale:'" + desEstesaStatoProcedStatale + "'" +
                ",\n         labelStatoProcedStatale:'" + labelStatoProcedStatale + "'" +
                "}\n";
    }
}