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
 * The type Gruppo soggetto dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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

    @JsonProperty("id_funzionario")
    private Long idFunzionario;

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
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GruppoSoggettoDTO that = (GruppoSoggettoDTO) o;
        return Objects.equals(idGruppoSoggetto, that.idGruppoSoggetto) && Objects.equals(codGruppoSoggetto, that.codGruppoSoggetto) && Objects.equals(desGruppoSoggetto, that.desGruppoSoggetto) && Objects.equals(flgCreazioneAutomatica, that.flgCreazioneAutomatica) && Objects.equals(idIstanzaAttore, that.idIstanzaAttore) && Objects.equals(idFunzionario, that.idFunzionario);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idGruppoSoggetto, codGruppoSoggetto, desGruppoSoggetto, flgCreazioneAutomatica, idIstanzaAttore, idFunzionario);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "GruppoSoggettoDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idGruppoSoggetto:" + idGruppoSoggetto +
                ",\n         codGruppoSoggetto:'" + codGruppoSoggetto + "'" +
                ",\n         desGruppoSoggetto:'" + desGruppoSoggetto + "'" +
                ",\n         flgCreazioneAutomatica:" + flgCreazioneAutomatica +
                ",\n         idIstanzaAttore:" + idIstanzaAttore +
                ",\n         idFunzionario:" + idFunzionario +
                "}\n";
    }

}