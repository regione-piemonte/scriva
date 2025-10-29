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
 * The type Referente istanza dto.
 *
 * @author CSI PIEMONTE
 */
public class ReferenteIstanzaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_referente_istanza")
    private Long idReferenteIstanza;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("cognome_referente")
    private String cognomeReferente;

    @JsonProperty("nome_referente")
    private String nomeReferente;

    @JsonProperty("num_tel_referente")
    private String numTelReferente;

    @JsonProperty("des_email_referente")
    private String desEmailReferente;

    @JsonProperty("des_mansione_referente")
    private String desMansioneReferente;

    @JsonProperty("num_cellulare_referente")
    private String numCellulareReferente;

    @JsonProperty("des_pec_referente")
    private String desPecReferente;

    /**
     * Gets id referente istanza.
     *
     * @return the id referente istanza
     */
    public Long getIdReferenteIstanza() {
        return idReferenteIstanza;
    }

    /**
     * Sets id referente istanza.
     *
     * @param idReferenteIstanza the id referente istanza
     */
    public void setIdReferenteIstanza(Long idReferenteIstanza) {
        this.idReferenteIstanza = idReferenteIstanza;
    }

    /**
     * Gets id istanza.
     *
     * @return the id istanza
     */
    public Long getIdIstanza() {
        return idIstanza;
    }

    /**
     * Sets id istanza.
     *
     * @param idIstanza the id istanza
     */
    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    /**
     * Gets cognome referente.
     *
     * @return the cognome referente
     */
    public String getCognomeReferente() {
        return cognomeReferente;
    }

    /**
     * Sets cognome referente.
     *
     * @param cognomeReferente the cognome referente
     */
    public void setCognomeReferente(String cognomeReferente) {
        this.cognomeReferente = cognomeReferente;
    }

    /**
     * Gets nome referente.
     *
     * @return the nome referente
     */
    public String getNomeReferente() {
        return nomeReferente;
    }

    /**
     * Sets nome referente.
     *
     * @param nomeReferente the nome referente
     */
    public void setNomeReferente(String nomeReferente) {
        this.nomeReferente = nomeReferente;
    }

    /**
     * Gets num tel referente.
     *
     * @return the num tel referente
     */
    public String getNumTelReferente() {
        return numTelReferente;
    }

    /**
     * Sets num tel referente.
     *
     * @param numTelReferente the num tel referente
     */
    public void setNumTelReferente(String numTelReferente) {
        this.numTelReferente = numTelReferente;
    }

    /**
     * Gets des email referente.
     *
     * @return the des email referente
     */
    public String getDesEmailReferente() {
        return desEmailReferente;
    }

    /**
     * Sets des email referente.
     *
     * @param desEmailReferente the des email referente
     */
    public void setDesEmailReferente(String desEmailReferente) {
        this.desEmailReferente = desEmailReferente;
    }

    /**
     * Gets des mansione referente.
     *
     * @return the des mansione referente
     */
    public String getDesMansioneReferente() {
        return desMansioneReferente;
    }

    /**
     * Sets des mansione referente.
     *
     * @param desMansioneReferente the des mansione referente
     */
    public void setDesMansioneReferente(String desMansioneReferente) {
        this.desMansioneReferente = desMansioneReferente;
    }

    /**
     * Gets num cellulare referente.
     *
     * @return the num cellulare referente
     */
    public String getNumCellulareReferente() {
        return numCellulareReferente;
    }

    /**
     * Sets num cellulare referente.
     *
     * @param numCellulareReferente the num cellulare referente
     */
    public void setNumCellulareReferente(String numCellulareReferente) {
        this.numCellulareReferente = numCellulareReferente;
    }

    /**
     * Gets des pec referente.
     *
     * @return the des pec referente
     */
    public String getDesPecReferente() {
        return desPecReferente;
    }

    /**
     * Sets des pec referente.
     *
     * @param desPecReferente the des pec referente
     */
    public void setDesPecReferente(String desPecReferente) {
        this.desPecReferente = desPecReferente;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idReferenteIstanza, idIstanza, cognomeReferente, nomeReferente, numTelReferente, desEmailReferente, desMansioneReferente, numCellulareReferente, desPecReferente);
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
        ReferenteIstanzaDTO that = (ReferenteIstanzaDTO) o;
        return Objects.equals(idReferenteIstanza, that.idReferenteIstanza) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(cognomeReferente, that.cognomeReferente) && Objects.equals(nomeReferente, that.nomeReferente) && Objects.equals(numTelReferente, that.numTelReferente) && Objects.equals(desEmailReferente, that.desEmailReferente) && Objects.equals(desMansioneReferente, that.desMansioneReferente) && Objects.equals(numCellulareReferente, that.numCellulareReferente) && Objects.equals(desPecReferente, that.desPecReferente);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReferenteIstanzaDTO {\n");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",\n         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",\n         gestDataUpd:").append(gestDataUpd);
        sb.append(",\n         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",\n         gestUID:'").append(gestUID).append("'");
        sb.append(",\n         idReferenteIstanza:").append(idReferenteIstanza);
        sb.append(",\n         idIstanza:").append(idIstanza);
        sb.append(",\n         cognomeReferente:'").append(cognomeReferente).append("'");
        sb.append(",\n         nomeReferente:'").append(nomeReferente).append("'");
        sb.append(",\n         numTelReferente:'").append(numTelReferente).append("'");
        sb.append(",\n         desEmailReferente:'").append(desEmailReferente).append("'");
        sb.append(",\n         desMansioneReferente:'").append(desMansioneReferente).append("'");
        sb.append(",\n         numCellulareReferente:'").append(numCellulareReferente).append("'");
        sb.append(",\n         desPecReferente:'").append(desPecReferente).append("'");
        sb.append("}\n");
        return sb.toString();
    }

}