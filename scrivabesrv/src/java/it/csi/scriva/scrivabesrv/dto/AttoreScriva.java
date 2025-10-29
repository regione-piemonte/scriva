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

import it.csi.scriva.scrivabesrv.business.be.helper.iride.dto.Identita;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Attore scriva.
 *
 * @author CSI PIEMONTE
 */
public class AttoreScriva implements Serializable {

    private Long idAttore; //id attore della IstanzaAttore

    private String gestUidAttore; //UID attore della IstanzaAttore

    private String componente;

    private String codiceFiscale;

    private Boolean canReadIstanza;

    private Boolean canWriteIstanza;

    private String profiloAppIstanza; // permessi attore READ WRITE WRITE-LOCK ecc (detta anche attore-gestione:)

    private String ruolo;

    private Identita identita;

    /**
     * Gets id attore.
     *
     * @return the id attore
     */
    public Long getIdAttore() {
        return idAttore;
    }

    /**
     * Sets id attore.
     *
     * @param idAttore the id attore
     */
    public void setIdAttore(Long idAttore) {
        this.idAttore = idAttore;
    }

    /**
     * Gets gest uid attore.
     *
     * @return the gest uid attore
     */
    public String getGestUidAttore() {
        return gestUidAttore;
    }

    /**
     * Sets gest uid attore.
     *
     * @param gestUidAttore the gest uid attore
     */
    public void setGestUidAttore(String gestUidAttore) {
        this.gestUidAttore = gestUidAttore;
    }

    /**
     * Gets componente.
     *
     * @return the componente
     */
    public String getComponente() {
        return componente;
    }

    /**
     * Sets componente.
     *
     * @param componente the componente
     */
    public void setComponente(String componente) {
        this.componente = componente;
    }

    /**
     * Gets codice fiscale.
     *
     * @return the codice fiscale
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Sets codice fiscale.
     *
     * @param codiceFiscale the codice fiscale
     */
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    /**
     * Gets can read istanza.
     *
     * @return the can read istanza
     */
    public Boolean getCanReadIstanza() {
        return canReadIstanza;
    }

    /**
     * Sets can read istanza.
     *
     * @param canReadIstanza the can read istanza
     */
    public void setCanReadIstanza(Boolean canReadIstanza) {
        this.canReadIstanza = canReadIstanza;
    }

    /**
     * Gets can write istanza.
     *
     * @return the can write istanza
     */
    public Boolean getCanWriteIstanza() {
        return canWriteIstanza;
    }

    /**
     * Sets can write istanza.
     *
     * @param canWriteIstanza the can write istanza
     */
    public void setCanWriteIstanza(Boolean canWriteIstanza) {
        this.canWriteIstanza = canWriteIstanza;
    }

    /**
     * Gets profilo app istanza.
     *
     * @return the profilo app istanza
     */
    public String getProfiloAppIstanza() {
        return profiloAppIstanza;
    }

    /**
     * Sets profilo app istanza.
     *
     * @param profiloAppIstanza the profilo app istanza
     */
    public void setProfiloAppIstanza(String profiloAppIstanza) {
        this.profiloAppIstanza = profiloAppIstanza;
    }

    /**
     * Gets ruolo.
     *
     * @return the ruolo
     */
    public String getRuolo() {
        return ruolo;
    }

    /**
     * Sets ruolo.
     *
     * @param ruolo the ruolo
     */
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    /**
     * Gets identita.
     *
     * @return the identita
     */
    public Identita getIdentita() {
        return identita;
    }

    /**
     * Sets identita.
     *
     * @param identita the identita
     */
    public void setIdentita(Identita identita) {
        this.identita = identita;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttoreScriva that = (AttoreScriva) o;
        return Objects.equals(idAttore, that.idAttore) && Objects.equals(gestUidAttore, that.gestUidAttore) && Objects.equals(componente, that.componente) && Objects.equals(codiceFiscale, that.codiceFiscale) && Objects.equals(canReadIstanza, that.canReadIstanza) && Objects.equals(canWriteIstanza, that.canWriteIstanza) && Objects.equals(profiloAppIstanza, that.profiloAppIstanza) && Objects.equals(ruolo, that.ruolo) && Objects.equals(identita, that.identita);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idAttore, gestUidAttore, componente, codiceFiscale, canReadIstanza, canWriteIstanza, profiloAppIstanza, ruolo, identita);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "AttoreScriva {\n" +
                "         idAttore:" + idAttore +
                ",\n         gestUidAttore:'" + gestUidAttore + "'" +
                ",\n         componente:'" + componente + "'" +
                ",\n         codiceFiscale:'" + codiceFiscale + "'" +
                ",\n         canReadIstanza:" + canReadIstanza +
                ",\n         canWriteIstanza:" + canWriteIstanza +
                ",\n         profiloAppIstanza:'" + profiloAppIstanza + "'" +
                ",\n         ruolo:'" + ruolo + "'" +
                ",\n         identita:" + identita +
                "}\n";
    }
}