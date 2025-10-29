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
 * The type Gruppo soggetto dto.
 *
 * @author CSI PIEMONTE
 */
public class GruppoSoggettoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_gruppo_soggetto")
    private Long idGruppoSoggetto;

    @JsonProperty("cod_gruppo_soggetto")
    private String codGruppoSoggetto;

    @JsonProperty("des_gruppo_soggetto")
    private String desGruppoSoggetto;

    @JsonProperty("flg_creazione_automatica")
    private Boolean flgCreazioneAutomatica;

    @JsonProperty("id_istanza_attore")
    private Long idIstanzaAttore;

    /**
     * Gets id gruppo soggetto.
     *
     * @return the id gruppo soggetto
     */
    public Long getIdGruppoSoggetto() {
        return idGruppoSoggetto;
    }

    /**
     * Sets id gruppo soggetto.
     *
     * @param idGruppoSoggetto the id gruppo soggetto
     */
    public void setIdGruppoSoggetto(Long idGruppoSoggetto) {
        this.idGruppoSoggetto = idGruppoSoggetto;
    }

    /**
     * Gets cod gruppo soggetto.
     *
     * @return the cod gruppo soggetto
     */
    public String getCodGruppoSoggetto() {
        return codGruppoSoggetto;
    }

    /**
     * Sets cod gruppo soggetto.
     *
     * @param codGruppoSoggetto the cod gruppo soggetto
     */
    public void setCodGruppoSoggetto(String codGruppoSoggetto) {
        this.codGruppoSoggetto = codGruppoSoggetto;
    }

    /**
     * Gets des gruppo soggetto.
     *
     * @return the des gruppo soggetto
     */
    public String getDesGruppoSoggetto() {
        return desGruppoSoggetto;
    }

    /**
     * Sets des gruppo soggetto.
     *
     * @param desGruppoSoggetto the des gruppo soggetto
     */
    public void setDesGruppoSoggetto(String desGruppoSoggetto) {
        this.desGruppoSoggetto = desGruppoSoggetto;
    }

    /**
     * Gets flg creazione automatica.
     *
     * @return the flg creazione automatica
     */
    public Boolean getFlgCreazioneAutomatica() {
        return flgCreazioneAutomatica;
    }

    /**
     * Sets flg creazione automatica.
     *
     * @param flgCreazioneAutomatica the flg creazione automatica
     */
    public void setFlgCreazioneAutomatica(Boolean flgCreazioneAutomatica) {
        this.flgCreazioneAutomatica = flgCreazioneAutomatica;
    }

    /**
     * Gets id istanza attore.
     *
     * @return the id istanza attore
     */
    public Long getIdIstanzaAttore() {
        return idIstanzaAttore;
    }

    /**
     * Sets id istanza attore.
     *
     * @param idIstanzaAttore the id istanza attore
     */
    public void setIdIstanzaAttore(Long idIstanzaAttore) {
        this.idIstanzaAttore = idIstanzaAttore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GruppoSoggettoDTO that = (GruppoSoggettoDTO) o;
        return Objects.equals(idGruppoSoggetto, that.idGruppoSoggetto) && Objects.equals(codGruppoSoggetto, that.codGruppoSoggetto) && Objects.equals(desGruppoSoggetto, that.desGruppoSoggetto) && Objects.equals(flgCreazioneAutomatica, that.flgCreazioneAutomatica) && Objects.equals(idIstanzaAttore, that.idIstanzaAttore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idGruppoSoggetto, codGruppoSoggetto, desGruppoSoggetto, flgCreazioneAutomatica, idIstanzaAttore);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GruppoSoggettoDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         idGruppoSoggetto:").append(idGruppoSoggetto);
        sb.append(",         codGruppoSoggetto:'").append(codGruppoSoggetto).append("'");
        sb.append(",         desGruppoSoggetto:'").append(desGruppoSoggetto).append("'");
        sb.append(",         flgCreazioneAutomatica:").append(flgCreazioneAutomatica);
        sb.append(",         idIstanzaAttore:").append(idIstanzaAttore);
        sb.append("}");
        return sb.toString();
    }
}