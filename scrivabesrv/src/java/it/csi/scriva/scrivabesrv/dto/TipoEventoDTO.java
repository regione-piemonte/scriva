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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tipo evento dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoEventoDTO implements Serializable {

    @JsonProperty("id_tipo_evento")
    private Long idTipoEvento;

    @JsonProperty("id_stato_istanza_evento")
    private Long idStatoIstanzaEvento;

    @JsonProperty("cod_tipo_evento")
    private String codTipoEvento;

    @JsonProperty("des_tipo_evento")
    private String desTipoEvento;

    @JsonProperty("ind_visibile")
    private String indVisibile;

    @JsonIgnore
    private Long idComponenteGestoreProcesso;

    @JsonIgnore
    private Boolean flgGeneraRicevuta;

    /**
     * Gets id tipo evento.
     *
     * @return the id tipo evento
     */
    public Long getIdTipoEvento() {
        return idTipoEvento;
    }

    /**
     * Sets id tipo evento.
     *
     * @param idTipoEvento the id tipo evento
     */
    public void setIdTipoEvento(Long idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    /**
     * Gets id stato istanza evento.
     *
     * @return the id stato istanza evento
     */
    public Long getIdStatoIstanzaEvento() {
        return idStatoIstanzaEvento;
    }

    /**
     * Sets id stato istanza evento.
     *
     * @param idStatoIstanzaEvento the id stato istanza evento
     */
    public void setIdStatoIstanzaEvento(Long idStatoIstanzaEvento) {
        this.idStatoIstanzaEvento = idStatoIstanzaEvento;
    }

    /**
     * Gets cod tipo evento.
     *
     * @return the cod tipo evento
     */
    public String getCodTipoEvento() {
        return codTipoEvento;
    }

    /**
     * Sets cod tipo evento.
     *
     * @param codTipoEvento the cod tipo evento
     */
    public void setCodTipoEvento(String codTipoEvento) {
        this.codTipoEvento = codTipoEvento;
    }

    /**
     * Gets des tipo evento.
     *
     * @return the des tipo evento
     */
    public String getDesTipoEvento() {
        return desTipoEvento;
    }

    /**
     * Sets des tipo evento.
     *
     * @param desTipoEvento the des tipo evento
     */
    public void setDesTipoEvento(String desTipoEvento) {
        this.desTipoEvento = desTipoEvento;
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
     * Gets id componente gestore processo.
     *
     * @return the id componente gestore processo
     */
    public Long getIdComponenteGestoreProcesso() {
        return idComponenteGestoreProcesso;
    }

    /**
     * Sets id componente gestore processo.
     *
     * @param idComponenteGestoreProcesso the id componente gestore processo
     */
    public void setIdComponenteGestoreProcesso(Long idComponenteGestoreProcesso) {
        this.idComponenteGestoreProcesso = idComponenteGestoreProcesso;
    }

    /**
     * Gets flg genera ricevuta.
     *
     * @return the flg genera ricevuta
     */
    public Boolean getFlgGeneraRicevuta() {
        return flgGeneraRicevuta;
    }

    /**
     * Sets flg genera ricevuta.
     *
     * @param flgGeneraRicevuta the flg genera ricevuta
     */
    public void setFlgGeneraRicevuta(Boolean flgGeneraRicevuta) {
        this.flgGeneraRicevuta = flgGeneraRicevuta;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoEventoDTO that = (TipoEventoDTO) o;
        return Objects.equals(idTipoEvento, that.idTipoEvento) && Objects.equals(idStatoIstanzaEvento, that.idStatoIstanzaEvento) && Objects.equals(codTipoEvento, that.codTipoEvento) && Objects.equals(desTipoEvento, that.desTipoEvento) && Objects.equals(indVisibile, that.indVisibile) && Objects.equals(idComponenteGestoreProcesso, that.idComponenteGestoreProcesso) && Objects.equals(flgGeneraRicevuta, that.flgGeneraRicevuta);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTipoEvento, idStatoIstanzaEvento, codTipoEvento, desTipoEvento, indVisibile, idComponenteGestoreProcesso, flgGeneraRicevuta);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TipoEventoDTO {\n" +
                "         idTipoEvento:" + idTipoEvento +
                ",\n         idStatoIstanzaEvento:" + idStatoIstanzaEvento +
                ",\n         codTipoEvento:'" + codTipoEvento + "'" +
                ",\n         desTipoEvento:'" + desTipoEvento + "'" +
                ",\n         indVisibile:'" + indVisibile + "'" +
                ",\n         idComponenteGestoreProcesso:" + idComponenteGestoreProcesso +
                ",\n         flgGeneraRicevuta:" + flgGeneraRicevuta +
                "}\n";
    }
}