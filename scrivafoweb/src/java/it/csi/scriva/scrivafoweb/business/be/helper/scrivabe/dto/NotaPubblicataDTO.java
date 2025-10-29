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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * The type Nota pubblicata dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotaPubblicataDTO extends BaseDTO implements Serializable {


    @JsonProperty("id_nota_istanza")
    private Long idNotaIstanza;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_funzionario")
    private Long idFunzionario;

    @JsonProperty("data_nota")
    private Timestamp dataNota;

    @JsonProperty("des_nota")
    private String des_nota;

    @JsonProperty("ind_visibile")
    private String indVisibile;

    /**
     * Gets id nota istanza.
     *
     * @return the id nota istanza
     */
    public Long getIdNotaIstanza() {
        return idNotaIstanza;
    }

    /**
     * Sets id nota istanza.
     *
     * @param idNotaIstanza the id nota istanza
     */
    public void setIdNotaIstanza(Long idNotaIstanza) {
        this.idNotaIstanza = idNotaIstanza;
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
     * Gets id funzionario.
     *
     * @return the id funzionario
     */
    public Long getIdFunzionario() {
        return idFunzionario;
    }

    /**
     * Sets id funzionario.
     *
     * @param idFunzionario the id funzionario
     */
    public void setIdFunzionario(Long idFunzionario) {
        this.idFunzionario = idFunzionario;
    }

    /**
     * Gets data nota.
     *
     * @return the data nota
     */
    public Timestamp getDataNota() {
        return dataNota;
    }

    /**
     * Sets data nota.
     *
     * @param dataNota the data nota
     */
    public void setDataNota(Timestamp dataNota) {
        this.dataNota = dataNota;
    }

    /**
     * Gets des nota.
     *
     * @return the des nota
     */
    public String getDes_nota() {
        return des_nota;
    }

    /**
     * Sets des nota.
     *
     * @param des_nota the des nota
     */
    public void setDes_nota(String des_nota) {
        this.des_nota = des_nota;
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
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NotaPubblicataDTO that = (NotaPubblicataDTO) o;
        return Objects.equals(idNotaIstanza, that.idNotaIstanza) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idFunzionario, that.idFunzionario) && Objects.equals(dataNota, that.dataNota) && Objects.equals(des_nota, that.des_nota) && Objects.equals(indVisibile, that.indVisibile);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idNotaIstanza, idIstanza, idFunzionario, dataNota, des_nota, indVisibile);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "NotaPubblicataDTO {\n" +
                "         idNotaIstanza:" + idNotaIstanza +
                ",\n         idIstanza:" + idIstanza +
                ",\n         idFunzionario:" + idFunzionario +
                ",\n         dataNota:" + dataNota +
                ",\n         des_nota:'" + des_nota + "'" +
                ",\n         indVisibile:'" + indVisibile + "'" +
                "}\n";
    }
}