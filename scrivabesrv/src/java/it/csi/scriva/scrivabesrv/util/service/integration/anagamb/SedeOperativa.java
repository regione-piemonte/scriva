/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 * SedeOperativa.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.util.service.integration.anagamb;

/**
 * The type Sede operativa.
 */
public class SedeOperativa implements java.io.Serializable {
    private String cap;

    private String certificazioni;

    private String classificazioneAcustica;

    private String codiceIstatComune;

    private String codiceIstatProvincia;

    private String codiceSira;

    private String codiceSiraValidato;

    private String codiceVia;

    private String coordX;

    private String coordY;

    private String dataFineAttivita;

    private String dataInizioAttivita;

    private String denominazione;

    private String descrizioneComune;

    private String descrizioneFonte;

    private String descrizioneProvincia;

    private String descrizioneStatoSedeOperativa;

    private String email;

    private String fax;

    private String idFasciaAddetti;

    private String idFonte;

    private String idOggettoAmbientale;

    private String idSedeOperativa;

    private String idStatoSedeOperativa;

    private String indirizzo;

    private String localita;

    private String numeroAddetti;

    private String numeroCivico;

    private String ragioneSociale;

    private String telefono;

    private TemaAutorizzativo[] temiAutorizzativi;

    private String tipoGeorefCod;

    private String tipoGeorefDesc;

    /**
     * Instantiates a new Sede operativa.
     */
    public SedeOperativa() {
    }

    /**
     * Instantiates a new Sede operativa.
     *
     * @param cap                           the cap
     * @param certificazioni                the certificazioni
     * @param classificazioneAcustica       the classificazione acustica
     * @param codiceIstatComune             the codice istat comune
     * @param codiceIstatProvincia          the codice istat provincia
     * @param codiceSira                    the codice sira
     * @param codiceSiraValidato            the codice sira validato
     * @param codiceVia                     the codice via
     * @param coordX                        the coord x
     * @param coordY                        the coord y
     * @param dataFineAttivita              the data fine attivita
     * @param dataInizioAttivita            the data inizio attivita
     * @param denominazione                 the denominazione
     * @param descrizioneComune             the descrizione comune
     * @param descrizioneFonte              the descrizione fonte
     * @param descrizioneProvincia          the descrizione provincia
     * @param descrizioneStatoSedeOperativa the descrizione stato sede operativa
     * @param email                         the email
     * @param fax                           the fax
     * @param idFasciaAddetti               the id fascia addetti
     * @param idFonte                       the id fonte
     * @param idOggettoAmbientale           the id oggetto ambientale
     * @param idSedeOperativa               the id sede operativa
     * @param idStatoSedeOperativa          the id stato sede operativa
     * @param indirizzo                     the indirizzo
     * @param localita                      the localita
     * @param numeroAddetti                 the numero addetti
     * @param numeroCivico                  the numero civico
     * @param ragioneSociale                the ragione sociale
     * @param telefono                      the telefono
     * @param temiAutorizzativi             the temi autorizzativi
     * @param tipoGeorefCod                 the tipo georef cod
     * @param tipoGeorefDesc                the tipo georef desc
     */
    public SedeOperativa(
            String cap,
            String certificazioni,
            String classificazioneAcustica,
            String codiceIstatComune,
            String codiceIstatProvincia,
            String codiceSira,
            String codiceSiraValidato,
            String codiceVia,
            String coordX,
            String coordY,
            String dataFineAttivita,
            String dataInizioAttivita,
            String denominazione,
            String descrizioneComune,
            String descrizioneFonte,
            String descrizioneProvincia,
            String descrizioneStatoSedeOperativa,
            String email,
            String fax,
            String idFasciaAddetti,
            String idFonte,
            String idOggettoAmbientale,
            String idSedeOperativa,
            String idStatoSedeOperativa,
            String indirizzo,
            String localita,
            String numeroAddetti,
            String numeroCivico,
            String ragioneSociale,
            String telefono,
            TemaAutorizzativo[] temiAutorizzativi,
            String tipoGeorefCod,
            String tipoGeorefDesc) {
        this.cap = cap;
        this.certificazioni = certificazioni;
        this.classificazioneAcustica = classificazioneAcustica;
        this.codiceIstatComune = codiceIstatComune;
        this.codiceIstatProvincia = codiceIstatProvincia;
        this.codiceSira = codiceSira;
        this.codiceSiraValidato = codiceSiraValidato;
        this.codiceVia = codiceVia;
        this.coordX = coordX;
        this.coordY = coordY;
        this.dataFineAttivita = dataFineAttivita;
        this.dataInizioAttivita = dataInizioAttivita;
        this.denominazione = denominazione;
        this.descrizioneComune = descrizioneComune;
        this.descrizioneFonte = descrizioneFonte;
        this.descrizioneProvincia = descrizioneProvincia;
        this.descrizioneStatoSedeOperativa = descrizioneStatoSedeOperativa;
        this.email = email;
        this.fax = fax;
        this.idFasciaAddetti = idFasciaAddetti;
        this.idFonte = idFonte;
        this.idOggettoAmbientale = idOggettoAmbientale;
        this.idSedeOperativa = idSedeOperativa;
        this.idStatoSedeOperativa = idStatoSedeOperativa;
        this.indirizzo = indirizzo;
        this.localita = localita;
        this.numeroAddetti = numeroAddetti;
        this.numeroCivico = numeroCivico;
        this.ragioneSociale = ragioneSociale;
        this.telefono = telefono;
        this.temiAutorizzativi = temiAutorizzativi;
        this.tipoGeorefCod = tipoGeorefCod;
        this.tipoGeorefDesc = tipoGeorefDesc;
    }


    /**
     * Gets the cap value for this SedeOperativa.
     *
     * @return cap cap
     */
    public String getCap() {
        return cap;
    }


    /**
     * Sets the cap value for this SedeOperativa.
     *
     * @param cap the cap
     */
    public void setCap(String cap) {
        this.cap = cap;
    }


    /**
     * Gets the certificazioni value for this SedeOperativa.
     *
     * @return certificazioni certificazioni
     */
    public String getCertificazioni() {
        return certificazioni;
    }


    /**
     * Sets the certificazioni value for this SedeOperativa.
     *
     * @param certificazioni the certificazioni
     */
    public void setCertificazioni(String certificazioni) {
        this.certificazioni = certificazioni;
    }


    /**
     * Gets the classificazioneAcustica value for this SedeOperativa.
     *
     * @return classificazioneAcustica classificazione acustica
     */
    public String getClassificazioneAcustica() {
        return classificazioneAcustica;
    }


    /**
     * Sets the classificazioneAcustica value for this SedeOperativa.
     *
     * @param classificazioneAcustica the classificazione acustica
     */
    public void setClassificazioneAcustica(String classificazioneAcustica) {
        this.classificazioneAcustica = classificazioneAcustica;
    }


    /**
     * Gets the codiceIstatComune value for this SedeOperativa.
     *
     * @return codiceIstatComune codice istat comune
     */
    public String getCodiceIstatComune() {
        return codiceIstatComune;
    }


    /**
     * Sets the codiceIstatComune value for this SedeOperativa.
     *
     * @param codiceIstatComune the codice istat comune
     */
    public void setCodiceIstatComune(String codiceIstatComune) {
        this.codiceIstatComune = codiceIstatComune;
    }


    /**
     * Gets the codiceIstatProvincia value for this SedeOperativa.
     *
     * @return codiceIstatProvincia codice istat provincia
     */
    public String getCodiceIstatProvincia() {
        return codiceIstatProvincia;
    }


    /**
     * Sets the codiceIstatProvincia value for this SedeOperativa.
     *
     * @param codiceIstatProvincia the codice istat provincia
     */
    public void setCodiceIstatProvincia(String codiceIstatProvincia) {
        this.codiceIstatProvincia = codiceIstatProvincia;
    }


    /**
     * Gets the codiceSira value for this SedeOperativa.
     *
     * @return codiceSira codice sira
     */
    public String getCodiceSira() {
        return codiceSira;
    }


    /**
     * Sets the codiceSira value for this SedeOperativa.
     *
     * @param codiceSira the codice sira
     */
    public void setCodiceSira(String codiceSira) {
        this.codiceSira = codiceSira;
    }


    /**
     * Gets the codiceSiraValidato value for this SedeOperativa.
     *
     * @return codiceSiraValidato codice sira validato
     */
    public String getCodiceSiraValidato() {
        return codiceSiraValidato;
    }


    /**
     * Sets the codiceSiraValidato value for this SedeOperativa.
     *
     * @param codiceSiraValidato the codice sira validato
     */
    public void setCodiceSiraValidato(String codiceSiraValidato) {
        this.codiceSiraValidato = codiceSiraValidato;
    }


    /**
     * Gets the codiceVia value for this SedeOperativa.
     *
     * @return codiceVia codice via
     */
    public String getCodiceVia() {
        return codiceVia;
    }


    /**
     * Sets the codiceVia value for this SedeOperativa.
     *
     * @param codiceVia the codice via
     */
    public void setCodiceVia(String codiceVia) {
        this.codiceVia = codiceVia;
    }


    /**
     * Gets the coordX value for this SedeOperativa.
     *
     * @return coordX coord x
     */
    public String getCoordX() {
        return coordX;
    }


    /**
     * Sets the coordX value for this SedeOperativa.
     *
     * @param coordX the coord x
     */
    public void setCoordX(String coordX) {
        this.coordX = coordX;
    }


    /**
     * Gets the coordY value for this SedeOperativa.
     *
     * @return coordY coord y
     */
    public String getCoordY() {
        return coordY;
    }


    /**
     * Sets the coordY value for this SedeOperativa.
     *
     * @param coordY the coord y
     */
    public void setCoordY(String coordY) {
        this.coordY = coordY;
    }


    /**
     * Gets the dataFineAttivita value for this SedeOperativa.
     *
     * @return dataFineAttivita data fine attivita
     */
    public String getDataFineAttivita() {
        return dataFineAttivita;
    }


    /**
     * Sets the dataFineAttivita value for this SedeOperativa.
     *
     * @param dataFineAttivita the data fine attivita
     */
    public void setDataFineAttivita(String dataFineAttivita) {
        this.dataFineAttivita = dataFineAttivita;
    }


    /**
     * Gets the dataInizioAttivita value for this SedeOperativa.
     *
     * @return dataInizioAttivita data inizio attivita
     */
    public String getDataInizioAttivita() {
        return dataInizioAttivita;
    }


    /**
     * Sets the dataInizioAttivita value for this SedeOperativa.
     *
     * @param dataInizioAttivita the data inizio attivita
     */
    public void setDataInizioAttivita(String dataInizioAttivita) {
        this.dataInizioAttivita = dataInizioAttivita;
    }


    /**
     * Gets the denominazione value for this SedeOperativa.
     *
     * @return denominazione denominazione
     */
    public String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this SedeOperativa.
     *
     * @param denominazione the denominazione
     */
    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the descrizioneComune value for this SedeOperativa.
     *
     * @return descrizioneComune descrizione comune
     */
    public String getDescrizioneComune() {
        return descrizioneComune;
    }


    /**
     * Sets the descrizioneComune value for this SedeOperativa.
     *
     * @param descrizioneComune the descrizione comune
     */
    public void setDescrizioneComune(String descrizioneComune) {
        this.descrizioneComune = descrizioneComune;
    }


    /**
     * Gets the descrizioneFonte value for this SedeOperativa.
     *
     * @return descrizioneFonte descrizione fonte
     */
    public String getDescrizioneFonte() {
        return descrizioneFonte;
    }


    /**
     * Sets the descrizioneFonte value for this SedeOperativa.
     *
     * @param descrizioneFonte the descrizione fonte
     */
    public void setDescrizioneFonte(String descrizioneFonte) {
        this.descrizioneFonte = descrizioneFonte;
    }


    /**
     * Gets the descrizioneProvincia value for this SedeOperativa.
     *
     * @return descrizioneProvincia descrizione provincia
     */
    public String getDescrizioneProvincia() {
        return descrizioneProvincia;
    }


    /**
     * Sets the descrizioneProvincia value for this SedeOperativa.
     *
     * @param descrizioneProvincia the descrizione provincia
     */
    public void setDescrizioneProvincia(String descrizioneProvincia) {
        this.descrizioneProvincia = descrizioneProvincia;
    }


    /**
     * Gets the descrizioneStatoSedeOperativa value for this SedeOperativa.
     *
     * @return descrizioneStatoSedeOperativa descrizione stato sede operativa
     */
    public String getDescrizioneStatoSedeOperativa() {
        return descrizioneStatoSedeOperativa;
    }


    /**
     * Sets the descrizioneStatoSedeOperativa value for this SedeOperativa.
     *
     * @param descrizioneStatoSedeOperativa the descrizione stato sede operativa
     */
    public void setDescrizioneStatoSedeOperativa(String descrizioneStatoSedeOperativa) {
        this.descrizioneStatoSedeOperativa = descrizioneStatoSedeOperativa;
    }


    /**
     * Gets the email value for this SedeOperativa.
     *
     * @return email email
     */
    public String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this SedeOperativa.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Gets the fax value for this SedeOperativa.
     *
     * @return fax fax
     */
    public String getFax() {
        return fax;
    }


    /**
     * Sets the fax value for this SedeOperativa.
     *
     * @param fax the fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }


    /**
     * Gets the idFasciaAddetti value for this SedeOperativa.
     *
     * @return idFasciaAddetti id fascia addetti
     */
    public String getIdFasciaAddetti() {
        return idFasciaAddetti;
    }


    /**
     * Sets the idFasciaAddetti value for this SedeOperativa.
     *
     * @param idFasciaAddetti the id fascia addetti
     */
    public void setIdFasciaAddetti(String idFasciaAddetti) {
        this.idFasciaAddetti = idFasciaAddetti;
    }


    /**
     * Gets the idFonte value for this SedeOperativa.
     *
     * @return idFonte id fonte
     */
    public String getIdFonte() {
        return idFonte;
    }


    /**
     * Sets the idFonte value for this SedeOperativa.
     *
     * @param idFonte the id fonte
     */
    public void setIdFonte(String idFonte) {
        this.idFonte = idFonte;
    }


    /**
     * Gets the idOggettoAmbientale value for this SedeOperativa.
     *
     * @return idOggettoAmbientale id oggetto ambientale
     */
    public String getIdOggettoAmbientale() {
        return idOggettoAmbientale;
    }


    /**
     * Sets the idOggettoAmbientale value for this SedeOperativa.
     *
     * @param idOggettoAmbientale the id oggetto ambientale
     */
    public void setIdOggettoAmbientale(String idOggettoAmbientale) {
        this.idOggettoAmbientale = idOggettoAmbientale;
    }


    /**
     * Gets the idSedeOperativa value for this SedeOperativa.
     *
     * @return idSedeOperativa id sede operativa
     */
    public String getIdSedeOperativa() {
        return idSedeOperativa;
    }


    /**
     * Sets the idSedeOperativa value for this SedeOperativa.
     *
     * @param idSedeOperativa the id sede operativa
     */
    public void setIdSedeOperativa(String idSedeOperativa) {
        this.idSedeOperativa = idSedeOperativa;
    }


    /**
     * Gets the idStatoSedeOperativa value for this SedeOperativa.
     *
     * @return idStatoSedeOperativa id stato sede operativa
     */
    public String getIdStatoSedeOperativa() {
        return idStatoSedeOperativa;
    }


    /**
     * Sets the idStatoSedeOperativa value for this SedeOperativa.
     *
     * @param idStatoSedeOperativa the id stato sede operativa
     */
    public void setIdStatoSedeOperativa(String idStatoSedeOperativa) {
        this.idStatoSedeOperativa = idStatoSedeOperativa;
    }


    /**
     * Gets the indirizzo value for this SedeOperativa.
     *
     * @return indirizzo indirizzo
     */
    public String getIndirizzo() {
        return indirizzo;
    }


    /**
     * Sets the indirizzo value for this SedeOperativa.
     *
     * @param indirizzo the indirizzo
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }


    /**
     * Gets the localita value for this SedeOperativa.
     *
     * @return localita localita
     */
    public String getLocalita() {
        return localita;
    }


    /**
     * Sets the localita value for this SedeOperativa.
     *
     * @param localita the localita
     */
    public void setLocalita(String localita) {
        this.localita = localita;
    }


    /**
     * Gets the numeroAddetti value for this SedeOperativa.
     *
     * @return numeroAddetti numero addetti
     */
    public String getNumeroAddetti() {
        return numeroAddetti;
    }


    /**
     * Sets the numeroAddetti value for this SedeOperativa.
     *
     * @param numeroAddetti the numero addetti
     */
    public void setNumeroAddetti(String numeroAddetti) {
        this.numeroAddetti = numeroAddetti;
    }


    /**
     * Gets the numeroCivico value for this SedeOperativa.
     *
     * @return numeroCivico numero civico
     */
    public String getNumeroCivico() {
        return numeroCivico;
    }


    /**
     * Sets the numeroCivico value for this SedeOperativa.
     *
     * @param numeroCivico the numero civico
     */
    public void setNumeroCivico(String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }


    /**
     * Gets the ragioneSociale value for this SedeOperativa.
     *
     * @return ragioneSociale ragione sociale
     */
    public String getRagioneSociale() {
        return ragioneSociale;
    }


    /**
     * Sets the ragioneSociale value for this SedeOperativa.
     *
     * @param ragioneSociale the ragione sociale
     */
    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }


    /**
     * Gets the telefono value for this SedeOperativa.
     *
     * @return telefono telefono
     */
    public String getTelefono() {
        return telefono;
    }


    /**
     * Sets the telefono value for this SedeOperativa.
     *
     * @param telefono the telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    /**
     * Gets the temiAutorizzativi value for this SedeOperativa.
     *
     * @return temiAutorizzativi tema autorizzativo [ ]
     */
    public TemaAutorizzativo[] getTemiAutorizzativi() {
        return temiAutorizzativi;
    }


    /**
     * Sets the temiAutorizzativi value for this SedeOperativa.
     *
     * @param temiAutorizzativi the temi autorizzativi
     */
    public void setTemiAutorizzativi(TemaAutorizzativo[] temiAutorizzativi) {
        this.temiAutorizzativi = temiAutorizzativi;
    }


    /**
     * Gets the tipoGeorefCod value for this SedeOperativa.
     *
     * @return tipoGeorefCod tipo georef cod
     */
    public String getTipoGeorefCod() {
        return tipoGeorefCod;
    }


    /**
     * Sets the tipoGeorefCod value for this SedeOperativa.
     *
     * @param tipoGeorefCod the tipo georef cod
     */
    public void setTipoGeorefCod(String tipoGeorefCod) {
        this.tipoGeorefCod = tipoGeorefCod;
    }


    /**
     * Gets the tipoGeorefDesc value for this SedeOperativa.
     *
     * @return tipoGeorefDesc tipo georef desc
     */
    public String getTipoGeorefDesc() {
        return tipoGeorefDesc;
    }


    /**
     * Sets the tipoGeorefDesc value for this SedeOperativa.
     *
     * @param tipoGeorefDesc the tipo georef desc
     */
    public void setTipoGeorefDesc(String tipoGeorefDesc) {
        this.tipoGeorefDesc = tipoGeorefDesc;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SedeOperativa)) return false;
        SedeOperativa other = (SedeOperativa) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = ((this.cap == null && other.getCap() == null) ||
                (this.cap != null &&
                        this.cap.equals(other.getCap()))) &&
                ((this.certificazioni == null && other.getCertificazioni() == null) ||
                        (this.certificazioni != null &&
                                this.certificazioni.equals(other.getCertificazioni()))) &&
                ((this.classificazioneAcustica == null && other.getClassificazioneAcustica() == null) ||
                        (this.classificazioneAcustica != null &&
                                this.classificazioneAcustica.equals(other.getClassificazioneAcustica()))) &&
                ((this.codiceIstatComune == null && other.getCodiceIstatComune() == null) ||
                        (this.codiceIstatComune != null &&
                                this.codiceIstatComune.equals(other.getCodiceIstatComune()))) &&
                ((this.codiceIstatProvincia == null && other.getCodiceIstatProvincia() == null) ||
                        (this.codiceIstatProvincia != null &&
                                this.codiceIstatProvincia.equals(other.getCodiceIstatProvincia()))) &&
                ((this.codiceSira == null && other.getCodiceSira() == null) ||
                        (this.codiceSira != null &&
                                this.codiceSira.equals(other.getCodiceSira()))) &&
                ((this.codiceSiraValidato == null && other.getCodiceSiraValidato() == null) ||
                        (this.codiceSiraValidato != null &&
                                this.codiceSiraValidato.equals(other.getCodiceSiraValidato()))) &&
                ((this.codiceVia == null && other.getCodiceVia() == null) ||
                        (this.codiceVia != null &&
                                this.codiceVia.equals(other.getCodiceVia()))) &&
                ((this.coordX == null && other.getCoordX() == null) ||
                        (this.coordX != null &&
                                this.coordX.equals(other.getCoordX()))) &&
                ((this.coordY == null && other.getCoordY() == null) ||
                        (this.coordY != null &&
                                this.coordY.equals(other.getCoordY()))) &&
                ((this.dataFineAttivita == null && other.getDataFineAttivita() == null) ||
                        (this.dataFineAttivita != null &&
                                this.dataFineAttivita.equals(other.getDataFineAttivita()))) &&
                ((this.dataInizioAttivita == null && other.getDataInizioAttivita() == null) ||
                        (this.dataInizioAttivita != null &&
                                this.dataInizioAttivita.equals(other.getDataInizioAttivita()))) &&
                ((this.denominazione == null && other.getDenominazione() == null) ||
                        (this.denominazione != null &&
                                this.denominazione.equals(other.getDenominazione()))) &&
                ((this.descrizioneComune == null && other.getDescrizioneComune() == null) ||
                        (this.descrizioneComune != null &&
                                this.descrizioneComune.equals(other.getDescrizioneComune()))) &&
                ((this.descrizioneFonte == null && other.getDescrizioneFonte() == null) ||
                        (this.descrizioneFonte != null &&
                                this.descrizioneFonte.equals(other.getDescrizioneFonte()))) &&
                ((this.descrizioneProvincia == null && other.getDescrizioneProvincia() == null) ||
                        (this.descrizioneProvincia != null &&
                                this.descrizioneProvincia.equals(other.getDescrizioneProvincia()))) &&
                ((this.descrizioneStatoSedeOperativa == null && other.getDescrizioneStatoSedeOperativa() == null) ||
                        (this.descrizioneStatoSedeOperativa != null &&
                                this.descrizioneStatoSedeOperativa.equals(other.getDescrizioneStatoSedeOperativa()))) &&
                ((this.email == null && other.getEmail() == null) ||
                        (this.email != null &&
                                this.email.equals(other.getEmail()))) &&
                ((this.fax == null && other.getFax() == null) ||
                        (this.fax != null &&
                                this.fax.equals(other.getFax()))) &&
                ((this.idFasciaAddetti == null && other.getIdFasciaAddetti() == null) ||
                        (this.idFasciaAddetti != null &&
                                this.idFasciaAddetti.equals(other.getIdFasciaAddetti()))) &&
                ((this.idFonte == null && other.getIdFonte() == null) ||
                        (this.idFonte != null &&
                                this.idFonte.equals(other.getIdFonte()))) &&
                ((this.idOggettoAmbientale == null && other.getIdOggettoAmbientale() == null) ||
                        (this.idOggettoAmbientale != null &&
                                this.idOggettoAmbientale.equals(other.getIdOggettoAmbientale()))) &&
                ((this.idSedeOperativa == null && other.getIdSedeOperativa() == null) ||
                        (this.idSedeOperativa != null &&
                                this.idSedeOperativa.equals(other.getIdSedeOperativa()))) &&
                ((this.idStatoSedeOperativa == null && other.getIdStatoSedeOperativa() == null) ||
                        (this.idStatoSedeOperativa != null &&
                                this.idStatoSedeOperativa.equals(other.getIdStatoSedeOperativa()))) &&
                ((this.indirizzo == null && other.getIndirizzo() == null) ||
                        (this.indirizzo != null &&
                                this.indirizzo.equals(other.getIndirizzo()))) &&
                ((this.localita == null && other.getLocalita() == null) ||
                        (this.localita != null &&
                                this.localita.equals(other.getLocalita()))) &&
                ((this.numeroAddetti == null && other.getNumeroAddetti() == null) ||
                        (this.numeroAddetti != null &&
                                this.numeroAddetti.equals(other.getNumeroAddetti()))) &&
                ((this.numeroCivico == null && other.getNumeroCivico() == null) ||
                        (this.numeroCivico != null &&
                                this.numeroCivico.equals(other.getNumeroCivico()))) &&
                ((this.ragioneSociale == null && other.getRagioneSociale() == null) ||
                        (this.ragioneSociale != null &&
                                this.ragioneSociale.equals(other.getRagioneSociale()))) &&
                ((this.telefono == null && other.getTelefono() == null) ||
                        (this.telefono != null &&
                                this.telefono.equals(other.getTelefono()))) &&
                ((this.temiAutorizzativi == null && other.getTemiAutorizzativi() == null) ||
                        (this.temiAutorizzativi != null &&
                                java.util.Arrays.equals(this.temiAutorizzativi, other.getTemiAutorizzativi()))) &&
                ((this.tipoGeorefCod == null && other.getTipoGeorefCod() == null) ||
                        (this.tipoGeorefCod != null &&
                                this.tipoGeorefCod.equals(other.getTipoGeorefCod()))) &&
                ((this.tipoGeorefDesc == null && other.getTipoGeorefDesc() == null) ||
                        (this.tipoGeorefDesc != null &&
                                this.tipoGeorefDesc.equals(other.getTipoGeorefDesc())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCap() != null) {
            _hashCode += getCap().hashCode();
        }
        if (getCertificazioni() != null) {
            _hashCode += getCertificazioni().hashCode();
        }
        if (getClassificazioneAcustica() != null) {
            _hashCode += getClassificazioneAcustica().hashCode();
        }
        if (getCodiceIstatComune() != null) {
            _hashCode += getCodiceIstatComune().hashCode();
        }
        if (getCodiceIstatProvincia() != null) {
            _hashCode += getCodiceIstatProvincia().hashCode();
        }
        if (getCodiceSira() != null) {
            _hashCode += getCodiceSira().hashCode();
        }
        if (getCodiceSiraValidato() != null) {
            _hashCode += getCodiceSiraValidato().hashCode();
        }
        if (getCodiceVia() != null) {
            _hashCode += getCodiceVia().hashCode();
        }
        if (getCoordX() != null) {
            _hashCode += getCoordX().hashCode();
        }
        if (getCoordY() != null) {
            _hashCode += getCoordY().hashCode();
        }
        if (getDataFineAttivita() != null) {
            _hashCode += getDataFineAttivita().hashCode();
        }
        if (getDataInizioAttivita() != null) {
            _hashCode += getDataInizioAttivita().hashCode();
        }
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getDescrizioneComune() != null) {
            _hashCode += getDescrizioneComune().hashCode();
        }
        if (getDescrizioneFonte() != null) {
            _hashCode += getDescrizioneFonte().hashCode();
        }
        if (getDescrizioneProvincia() != null) {
            _hashCode += getDescrizioneProvincia().hashCode();
        }
        if (getDescrizioneStatoSedeOperativa() != null) {
            _hashCode += getDescrizioneStatoSedeOperativa().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getFax() != null) {
            _hashCode += getFax().hashCode();
        }
        if (getIdFasciaAddetti() != null) {
            _hashCode += getIdFasciaAddetti().hashCode();
        }
        if (getIdFonte() != null) {
            _hashCode += getIdFonte().hashCode();
        }
        if (getIdOggettoAmbientale() != null) {
            _hashCode += getIdOggettoAmbientale().hashCode();
        }
        if (getIdSedeOperativa() != null) {
            _hashCode += getIdSedeOperativa().hashCode();
        }
        if (getIdStatoSedeOperativa() != null) {
            _hashCode += getIdStatoSedeOperativa().hashCode();
        }
        if (getIndirizzo() != null) {
            _hashCode += getIndirizzo().hashCode();
        }
        if (getLocalita() != null) {
            _hashCode += getLocalita().hashCode();
        }
        if (getNumeroAddetti() != null) {
            _hashCode += getNumeroAddetti().hashCode();
        }
        if (getNumeroCivico() != null) {
            _hashCode += getNumeroCivico().hashCode();
        }
        if (getRagioneSociale() != null) {
            _hashCode += getRagioneSociale().hashCode();
        }
        if (getTelefono() != null) {
            _hashCode += getTelefono().hashCode();
        }
        if (getTemiAutorizzativi() != null) {
            for (int i = 0;
                 i < java.lang.reflect.Array.getLength(getTemiAutorizzativi());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTemiAutorizzativi(), i);
                if (obj != null &&
                        !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTipoGeorefCod() != null) {
            _hashCode += getTipoGeorefCod().hashCode();
        }
        if (getTipoGeorefDesc() != null) {
            _hashCode += getTipoGeorefDesc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(SedeOperativa.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SedeOperativa"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cap");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certificazioni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "certificazioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classificazioneAcustica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "classificazioneAcustica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceIstatComune");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceIstatComune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceIstatProvincia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceIstatProvincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceSira");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceSira"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceSiraValidato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceSiraValidato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceVia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceVia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coordX");
        elemField.setXmlName(new javax.xml.namespace.QName("", "coordX"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coordY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "coordY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFineAttivita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataFineAttivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizioAttivita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInizioAttivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneComune");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneComune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneFonte");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneFonte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneProvincia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneProvincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneStatoSedeOperativa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneStatoSedeOperativa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFasciaAddetti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idFasciaAddetti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFonte");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idFonte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idOggettoAmbientale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idOggettoAmbientale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSedeOperativa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idSedeOperativa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idStatoSedeOperativa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idStatoSedeOperativa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indirizzo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "indirizzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "localita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroAddetti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroAddetti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCivico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCivico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ragioneSociale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ragioneSociale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telefono");
        elemField.setXmlName(new javax.xml.namespace.QName("", "telefono"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("temiAutorizzativi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "temiAutorizzativi"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "TemaAutorizzativo"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoGeorefCod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoGeorefCod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoGeorefDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoGeorefDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     *
     * @return the type desc
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     *
     * @param mechType  the mech type
     * @param _javaType the java type
     * @param _xmlType  the xml type
     * @return the serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
            String mechType,
            Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new org.apache.axis.encoding.ser.BeanSerializer(
                        _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     *
     * @param mechType  the mech type
     * @param _javaType the java type
     * @param _xmlType  the xml type
     * @return the deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            String mechType,
            Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new org.apache.axis.encoding.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }

}