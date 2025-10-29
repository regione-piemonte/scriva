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
import java.util.List;
import java.util.Objects;

/**
 * The type Oggetto ubicazione pratica extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoUbicazionePraticaExtendedDTO extends OggettoUbicazioneExtendedDTO implements Serializable {

    @JsonProperty("pratica_collegata")
    private List<PraticaCollegataDTO> praticaCollegata;

    /**
     * Instantiates a new Oggetto ubicazione pratica extended dto.
     *
     * @param another the another
     */
    public OggettoUbicazionePraticaExtendedDTO(OggettoUbicazioneExtendedDTO another) {
        super(another);
    }

    /**
     * Instantiates a new Oggetto ubicazione pratica extended dto.
     */
    public OggettoUbicazionePraticaExtendedDTO() {
        super();
    }

    /**
     * Sets pratica collegata.
     *
     * @param praticaCollegata the pratica collegata
     */
    public void setPraticaCollegata(List<PraticaCollegataDTO> praticaCollegata) {
        this.praticaCollegata = praticaCollegata;
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
        OggettoUbicazionePraticaExtendedDTO that = (OggettoUbicazionePraticaExtendedDTO) o;
        return Objects.equals(praticaCollegata, that.praticaCollegata);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), praticaCollegata);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoUbicazionePraticaExtendedDTO {\n");
        sb.append(super.toString()).append("\n");
        sb.append("         praticaCollegata:").append(praticaCollegata);
        sb.append("}\n");
        return sb.toString();
    }
}