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

import java.util.Objects;

/**
 * The type Istanza competenza oggetto dto.
 *
 * @author CSI PIEMONTE
 */
public class IstanzaCompetenzaOggettoDTO extends BaseDTO{

    private Long idIstanza;

    private Long idCompetenzaTerritorio;

    private Long idOggettoIstanza;
    
    private Boolean flgAutoritaPrincipale;

    /**
	 * @return the flgAutoritaPrincipale
	 */
	public Boolean getFlgAutoritaPrincipale() {
		return flgAutoritaPrincipale;
	}

	/**
	 * @param flgAutoritaPrincipale the flgAutoritaPrincipale to set
	 */
	public void setFlgAutoritaPrincipale(Boolean flgAutoritaPrincipale) {
		this.flgAutoritaPrincipale = flgAutoritaPrincipale;
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
     * Gets id competenza territorio.
     *
     * @return the id competenza territorio
     */
    public Long getIdCompetenzaTerritorio() {
        return idCompetenzaTerritorio;
    }

    /**
     * Sets id competenza territorio.
     *
     * @param idCompetenzaTerritorio the id competenza territorio
     */
    public void setIdCompetenzaTerritorio(Long idCompetenzaTerritorio) {
        this.idCompetenzaTerritorio = idCompetenzaTerritorio;
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
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IstanzaCompetenzaOggettoDTO that = (IstanzaCompetenzaOggettoDTO) o;
        return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idCompetenzaTerritorio, that.idCompetenzaTerritorio) && Objects.equals(idOggettoIstanza, that.idOggettoIstanza);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idIstanza, idCompetenzaTerritorio, idOggettoIstanza);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "IstanzaCompetenzaOggettoDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idIstanza:" + idIstanza +
                ",\n         idCompetenzaTerritorio:" + idCompetenzaTerritorio +
                ",\n         idOggettoIstanza:" + idOggettoIstanza +
                "}\n";
    }
}