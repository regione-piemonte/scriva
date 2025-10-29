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
import java.sql.Timestamp;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((dataNota == null) ? 0 : dataNota.hashCode());
        result = prime * result + ((des_nota == null) ? 0 : des_nota.hashCode());
        result = prime * result + ((idFunzionario == null) ? 0 : idFunzionario.hashCode());
        result = prime * result + ((idIstanza == null) ? 0 : idIstanza.hashCode());
        result = prime * result + ((idNotaIstanza == null) ? 0 : idNotaIstanza.hashCode());
        result = prime * result + ((indVisibile == null) ? 0 : indVisibile.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        NotaPubblicataDTO other = (NotaPubblicataDTO) obj;
        if (dataNota == null) {
            if (other.dataNota != null)
                return false;
        } else if (!dataNota.equals(other.dataNota))
            return false;
        if (des_nota == null) {
            if (other.des_nota != null)
                return false;
        } else if (!des_nota.equals(other.des_nota))
            return false;
        if (idFunzionario == null) {
            if (other.idFunzionario != null)
                return false;
        } else if (!idFunzionario.equals(other.idFunzionario))
            return false;
        if (idIstanza == null) {
            if (other.idIstanza != null)
                return false;
        } else if (!idIstanza.equals(other.idIstanza))
            return false;
        if (idNotaIstanza == null) {
            if (other.idNotaIstanza != null)
                return false;
        } else if (!idNotaIstanza.equals(other.idNotaIstanza))
            return false;
        if (indVisibile == null) {
            if (other.indVisibile != null)
                return false;
        } else if (!indVisibile.equals(other.indVisibile))
            return false;
        return true;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NotaPubblicata {");
        sb.append("         idNotaIstanza:").append(idNotaIstanza);
        sb.append(",         idIstanza:").append(idIstanza);
        sb.append(",         dataNota:'").append(dataNota).append("'");
        sb.append(",         des_nota:'").append(des_nota).append("'");
        sb.append(",         indVisibile:'").append(indVisibile).append("'");
        sb.append("}");
        return sb.toString();
    }


}