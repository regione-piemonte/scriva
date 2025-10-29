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
 * The type Oggetto istanza geeco dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoIstanzaGeecoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_oggetto_istanza")
    private Long idOggettoIstanza;

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    @JsonProperty("den_oggetto_istanza")
    private String denOggettoIstanza;

    @JsonProperty("ind_livello")
    private Integer indLivello;

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
     * Gets id oggetto istanza.
     *
     * @return the id oggetto istanza
     */
    public Long getIdOggettoIstanza() {
        return idOggettoIstanza;
    }

    /**
     * Sets id oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     */
    public void setIdOggettoIstanza(Long idOggettoIstanza) {
        this.idOggettoIstanza = idOggettoIstanza;
    }

    /**
     * Gets id adempimento.
     *
     * @return the id adempimento
     */
    public Long getIdAdempimento() {
        return idAdempimento;
    }

    /**
     * Sets id adempimento.
     *
     * @param idAdempimento the id adempimento
     */
    public void setIdAdempimento(Long idAdempimento) {
        this.idAdempimento = idAdempimento;
    }

    /**
     * Gets den oggetto istanza.
     *
     * @return the den oggetto istanza
     */
    public String getDenOggettoIstanza() {
        return denOggettoIstanza;
    }

    /**
     * Sets den oggetto istanza.
     *
     * @param denOggettoIstanza the den oggetto istanza
     */
    public void setDenOggettoIstanza(String denOggettoIstanza) {
        this.denOggettoIstanza = denOggettoIstanza;
    }

    /**
     * Gets ind livello.
     *
     * @return the ind livello
     */
    public Integer getIndLivello() {
        return indLivello;
    }

    /**
     * Sets ind livello.
     *
     * @param indLivello the ind livello
     */
    public void setIndLivello(Integer indLivello) {
        this.indLivello = indLivello;
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
        OggettoIstanzaGeecoDTO that = (OggettoIstanzaGeecoDTO) o;
        return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idOggettoIstanza, that.idOggettoIstanza) && Objects.equals(idAdempimento, that.idAdempimento) && Objects.equals(denOggettoIstanza, that.denOggettoIstanza) && Objects.equals(indLivello, that.indLivello);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idIstanza, idOggettoIstanza, idAdempimento, denOggettoIstanza, indLivello);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "OggettoIstanzaGeecoDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idIstanza:" + idIstanza +
                ",\n         idOggettoIstanza:" + idOggettoIstanza +
                ",\n         idAdempimento:" + idAdempimento +
                ",\n         denOggettoIstanza:'" + denOggettoIstanza + "'" +
                ",\n         indLivello:" + indLivello +
                "}\n";
    }
}