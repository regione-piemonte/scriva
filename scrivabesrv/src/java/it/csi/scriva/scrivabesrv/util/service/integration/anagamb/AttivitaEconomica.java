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
 * AttivitaEconomica.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.util.service.integration.anagamb;

/**
 * The type Attivita economica.
 */
public class AttivitaEconomica implements java.io.Serializable {
    private java.lang.String capacitaProduttivaMax;

    private java.lang.String codiceIppc;

    private java.lang.String codiceIstatAteco91;

    private java.lang.String codiceIstatComune;

    private java.lang.String codiceIstatNace;

    private java.lang.String codiceIstatProvincia;

    private java.lang.String codiceNoseP;

    private java.lang.String codiceSira;

    private java.lang.String codiceSnap;

    private java.lang.String dataFine;

    private java.lang.String dataInizio;

    private java.lang.String descrizioneComune;

    private java.lang.String descrizioneFonte;

    private java.lang.String descrizioneIppc;

    private java.lang.String descrizioneIstatNace;

    private java.lang.String descrizioneNoseP;

    private java.lang.String descrizioneProvincia;

    private java.lang.String descrizioneSnap;

    private java.lang.String flagAttivitaPrincipale;

    private java.lang.String flagAttivitaPrincipaleIppc;

    private java.lang.String idFonte;

    private java.lang.String idUnitaMisura;

    private java.lang.String numeroOreEsercizio;

    /**
     * Instantiates a new Attivita economica.
     */
    public AttivitaEconomica() {
    }

    /**
     * Instantiates a new Attivita economica.
     *
     * @param capacitaProduttivaMax      the capacita produttiva max
     * @param codiceIppc                 the codice ippc
     * @param codiceIstatAteco91         the codice istat ateco 91
     * @param codiceIstatComune          the codice istat comune
     * @param codiceIstatNace            the codice istat nace
     * @param codiceIstatProvincia       the codice istat provincia
     * @param codiceNoseP                the codice nose p
     * @param codiceSira                 the codice sira
     * @param codiceSnap                 the codice snap
     * @param dataFine                   the data fine
     * @param dataInizio                 the data inizio
     * @param descrizioneComune          the descrizione comune
     * @param descrizioneFonte           the descrizione fonte
     * @param descrizioneIppc            the descrizione ippc
     * @param descrizioneIstatNace       the descrizione istat nace
     * @param descrizioneNoseP           the descrizione nose p
     * @param descrizioneProvincia       the descrizione provincia
     * @param descrizioneSnap            the descrizione snap
     * @param flagAttivitaPrincipale     the flag attivita principale
     * @param flagAttivitaPrincipaleIppc the flag attivita principale ippc
     * @param idFonte                    the id fonte
     * @param idUnitaMisura              the id unita misura
     * @param numeroOreEsercizio         the numero ore esercizio
     */
    public AttivitaEconomica(
            java.lang.String capacitaProduttivaMax,
            java.lang.String codiceIppc,
            java.lang.String codiceIstatAteco91,
            java.lang.String codiceIstatComune,
            java.lang.String codiceIstatNace,
            java.lang.String codiceIstatProvincia,
            java.lang.String codiceNoseP,
            java.lang.String codiceSira,
            java.lang.String codiceSnap,
            java.lang.String dataFine,
            java.lang.String dataInizio,
            java.lang.String descrizioneComune,
            java.lang.String descrizioneFonte,
            java.lang.String descrizioneIppc,
            java.lang.String descrizioneIstatNace,
            java.lang.String descrizioneNoseP,
            java.lang.String descrizioneProvincia,
            java.lang.String descrizioneSnap,
            java.lang.String flagAttivitaPrincipale,
            java.lang.String flagAttivitaPrincipaleIppc,
            java.lang.String idFonte,
            java.lang.String idUnitaMisura,
            java.lang.String numeroOreEsercizio) {
        this.capacitaProduttivaMax = capacitaProduttivaMax;
        this.codiceIppc = codiceIppc;
        this.codiceIstatAteco91 = codiceIstatAteco91;
        this.codiceIstatComune = codiceIstatComune;
        this.codiceIstatNace = codiceIstatNace;
        this.codiceIstatProvincia = codiceIstatProvincia;
        this.codiceNoseP = codiceNoseP;
        this.codiceSira = codiceSira;
        this.codiceSnap = codiceSnap;
        this.dataFine = dataFine;
        this.dataInizio = dataInizio;
        this.descrizioneComune = descrizioneComune;
        this.descrizioneFonte = descrizioneFonte;
        this.descrizioneIppc = descrizioneIppc;
        this.descrizioneIstatNace = descrizioneIstatNace;
        this.descrizioneNoseP = descrizioneNoseP;
        this.descrizioneProvincia = descrizioneProvincia;
        this.descrizioneSnap = descrizioneSnap;
        this.flagAttivitaPrincipale = flagAttivitaPrincipale;
        this.flagAttivitaPrincipaleIppc = flagAttivitaPrincipaleIppc;
        this.idFonte = idFonte;
        this.idUnitaMisura = idUnitaMisura;
        this.numeroOreEsercizio = numeroOreEsercizio;
    }


    /**
     * Gets the capacitaProduttivaMax value for this AttivitaEconomica.
     *
     * @return capacitaProduttivaMax capacita produttiva max
     */
    public java.lang.String getCapacitaProduttivaMax() {
        return capacitaProduttivaMax;
    }


    /**
     * Sets the capacitaProduttivaMax value for this AttivitaEconomica.
     *
     * @param capacitaProduttivaMax the capacita produttiva max
     */
    public void setCapacitaProduttivaMax(java.lang.String capacitaProduttivaMax) {
        this.capacitaProduttivaMax = capacitaProduttivaMax;
    }


    /**
     * Gets the codiceIppc value for this AttivitaEconomica.
     *
     * @return codiceIppc codice ippc
     */
    public java.lang.String getCodiceIppc() {
        return codiceIppc;
    }


    /**
     * Sets the codiceIppc value for this AttivitaEconomica.
     *
     * @param codiceIppc the codice ippc
     */
    public void setCodiceIppc(java.lang.String codiceIppc) {
        this.codiceIppc = codiceIppc;
    }


    /**
     * Gets the codiceIstatAteco91 value for this AttivitaEconomica.
     *
     * @return codiceIstatAteco91 codice istat ateco 91
     */
    public java.lang.String getCodiceIstatAteco91() {
        return codiceIstatAteco91;
    }


    /**
     * Sets the codiceIstatAteco91 value for this AttivitaEconomica.
     *
     * @param codiceIstatAteco91 the codice istat ateco 91
     */
    public void setCodiceIstatAteco91(java.lang.String codiceIstatAteco91) {
        this.codiceIstatAteco91 = codiceIstatAteco91;
    }


    /**
     * Gets the codiceIstatComune value for this AttivitaEconomica.
     *
     * @return codiceIstatComune codice istat comune
     */
    public java.lang.String getCodiceIstatComune() {
        return codiceIstatComune;
    }


    /**
     * Sets the codiceIstatComune value for this AttivitaEconomica.
     *
     * @param codiceIstatComune the codice istat comune
     */
    public void setCodiceIstatComune(java.lang.String codiceIstatComune) {
        this.codiceIstatComune = codiceIstatComune;
    }


    /**
     * Gets the codiceIstatNace value for this AttivitaEconomica.
     *
     * @return codiceIstatNace codice istat nace
     */
    public java.lang.String getCodiceIstatNace() {
        return codiceIstatNace;
    }


    /**
     * Sets the codiceIstatNace value for this AttivitaEconomica.
     *
     * @param codiceIstatNace the codice istat nace
     */
    public void setCodiceIstatNace(java.lang.String codiceIstatNace) {
        this.codiceIstatNace = codiceIstatNace;
    }


    /**
     * Gets the codiceIstatProvincia value for this AttivitaEconomica.
     *
     * @return codiceIstatProvincia codice istat provincia
     */
    public java.lang.String getCodiceIstatProvincia() {
        return codiceIstatProvincia;
    }


    /**
     * Sets the codiceIstatProvincia value for this AttivitaEconomica.
     *
     * @param codiceIstatProvincia the codice istat provincia
     */
    public void setCodiceIstatProvincia(java.lang.String codiceIstatProvincia) {
        this.codiceIstatProvincia = codiceIstatProvincia;
    }


    /**
     * Gets the codiceNoseP value for this AttivitaEconomica.
     *
     * @return codiceNoseP codice nose p
     */
    public java.lang.String getCodiceNoseP() {
        return codiceNoseP;
    }


    /**
     * Sets the codiceNoseP value for this AttivitaEconomica.
     *
     * @param codiceNoseP the codice nose p
     */
    public void setCodiceNoseP(java.lang.String codiceNoseP) {
        this.codiceNoseP = codiceNoseP;
    }


    /**
     * Gets the codiceSira value for this AttivitaEconomica.
     *
     * @return codiceSira codice sira
     */
    public java.lang.String getCodiceSira() {
        return codiceSira;
    }


    /**
     * Sets the codiceSira value for this AttivitaEconomica.
     *
     * @param codiceSira the codice sira
     */
    public void setCodiceSira(java.lang.String codiceSira) {
        this.codiceSira = codiceSira;
    }


    /**
     * Gets the codiceSnap value for this AttivitaEconomica.
     *
     * @return codiceSnap codice snap
     */
    public java.lang.String getCodiceSnap() {
        return codiceSnap;
    }


    /**
     * Sets the codiceSnap value for this AttivitaEconomica.
     *
     * @param codiceSnap the codice snap
     */
    public void setCodiceSnap(java.lang.String codiceSnap) {
        this.codiceSnap = codiceSnap;
    }


    /**
     * Gets the dataFine value for this AttivitaEconomica.
     *
     * @return dataFine data fine
     */
    public java.lang.String getDataFine() {
        return dataFine;
    }


    /**
     * Sets the dataFine value for this AttivitaEconomica.
     *
     * @param dataFine the data fine
     */
    public void setDataFine(java.lang.String dataFine) {
        this.dataFine = dataFine;
    }


    /**
     * Gets the dataInizio value for this AttivitaEconomica.
     *
     * @return dataInizio data inizio
     */
    public java.lang.String getDataInizio() {
        return dataInizio;
    }


    /**
     * Sets the dataInizio value for this AttivitaEconomica.
     *
     * @param dataInizio the data inizio
     */
    public void setDataInizio(java.lang.String dataInizio) {
        this.dataInizio = dataInizio;
    }


    /**
     * Gets the descrizioneComune value for this AttivitaEconomica.
     *
     * @return descrizioneComune descrizione comune
     */
    public java.lang.String getDescrizioneComune() {
        return descrizioneComune;
    }


    /**
     * Sets the descrizioneComune value for this AttivitaEconomica.
     *
     * @param descrizioneComune the descrizione comune
     */
    public void setDescrizioneComune(java.lang.String descrizioneComune) {
        this.descrizioneComune = descrizioneComune;
    }


    /**
     * Gets the descrizioneFonte value for this AttivitaEconomica.
     *
     * @return descrizioneFonte descrizione fonte
     */
    public java.lang.String getDescrizioneFonte() {
        return descrizioneFonte;
    }


    /**
     * Sets the descrizioneFonte value for this AttivitaEconomica.
     *
     * @param descrizioneFonte the descrizione fonte
     */
    public void setDescrizioneFonte(java.lang.String descrizioneFonte) {
        this.descrizioneFonte = descrizioneFonte;
    }


    /**
     * Gets the descrizioneIppc value for this AttivitaEconomica.
     *
     * @return descrizioneIppc descrizione ippc
     */
    public java.lang.String getDescrizioneIppc() {
        return descrizioneIppc;
    }


    /**
     * Sets the descrizioneIppc value for this AttivitaEconomica.
     *
     * @param descrizioneIppc the descrizione ippc
     */
    public void setDescrizioneIppc(java.lang.String descrizioneIppc) {
        this.descrizioneIppc = descrizioneIppc;
    }


    /**
     * Gets the descrizioneIstatNace value for this AttivitaEconomica.
     *
     * @return descrizioneIstatNace descrizione istat nace
     */
    public java.lang.String getDescrizioneIstatNace() {
        return descrizioneIstatNace;
    }


    /**
     * Sets the descrizioneIstatNace value for this AttivitaEconomica.
     *
     * @param descrizioneIstatNace the descrizione istat nace
     */
    public void setDescrizioneIstatNace(java.lang.String descrizioneIstatNace) {
        this.descrizioneIstatNace = descrizioneIstatNace;
    }


    /**
     * Gets the descrizioneNoseP value for this AttivitaEconomica.
     *
     * @return descrizioneNoseP descrizione nose p
     */
    public java.lang.String getDescrizioneNoseP() {
        return descrizioneNoseP;
    }


    /**
     * Sets the descrizioneNoseP value for this AttivitaEconomica.
     *
     * @param descrizioneNoseP the descrizione nose p
     */
    public void setDescrizioneNoseP(java.lang.String descrizioneNoseP) {
        this.descrizioneNoseP = descrizioneNoseP;
    }


    /**
     * Gets the descrizioneProvincia value for this AttivitaEconomica.
     *
     * @return descrizioneProvincia descrizione provincia
     */
    public java.lang.String getDescrizioneProvincia() {
        return descrizioneProvincia;
    }


    /**
     * Sets the descrizioneProvincia value for this AttivitaEconomica.
     *
     * @param descrizioneProvincia the descrizione provincia
     */
    public void setDescrizioneProvincia(java.lang.String descrizioneProvincia) {
        this.descrizioneProvincia = descrizioneProvincia;
    }


    /**
     * Gets the descrizioneSnap value for this AttivitaEconomica.
     *
     * @return descrizioneSnap descrizione snap
     */
    public java.lang.String getDescrizioneSnap() {
        return descrizioneSnap;
    }


    /**
     * Sets the descrizioneSnap value for this AttivitaEconomica.
     *
     * @param descrizioneSnap the descrizione snap
     */
    public void setDescrizioneSnap(java.lang.String descrizioneSnap) {
        this.descrizioneSnap = descrizioneSnap;
    }


    /**
     * Gets the flagAttivitaPrincipale value for this AttivitaEconomica.
     *
     * @return flagAttivitaPrincipale flag attivita principale
     */
    public java.lang.String getFlagAttivitaPrincipale() {
        return flagAttivitaPrincipale;
    }


    /**
     * Sets the flagAttivitaPrincipale value for this AttivitaEconomica.
     *
     * @param flagAttivitaPrincipale the flag attivita principale
     */
    public void setFlagAttivitaPrincipale(java.lang.String flagAttivitaPrincipale) {
        this.flagAttivitaPrincipale = flagAttivitaPrincipale;
    }


    /**
     * Gets the flagAttivitaPrincipaleIppc value for this AttivitaEconomica.
     *
     * @return flagAttivitaPrincipaleIppc flag attivita principale ippc
     */
    public java.lang.String getFlagAttivitaPrincipaleIppc() {
        return flagAttivitaPrincipaleIppc;
    }


    /**
     * Sets the flagAttivitaPrincipaleIppc value for this AttivitaEconomica.
     *
     * @param flagAttivitaPrincipaleIppc the flag attivita principale ippc
     */
    public void setFlagAttivitaPrincipaleIppc(java.lang.String flagAttivitaPrincipaleIppc) {
        this.flagAttivitaPrincipaleIppc = flagAttivitaPrincipaleIppc;
    }


    /**
     * Gets the idFonte value for this AttivitaEconomica.
     *
     * @return idFonte id fonte
     */
    public java.lang.String getIdFonte() {
        return idFonte;
    }


    /**
     * Sets the idFonte value for this AttivitaEconomica.
     *
     * @param idFonte the id fonte
     */
    public void setIdFonte(java.lang.String idFonte) {
        this.idFonte = idFonte;
    }


    /**
     * Gets the idUnitaMisura value for this AttivitaEconomica.
     *
     * @return idUnitaMisura id unita misura
     */
    public java.lang.String getIdUnitaMisura() {
        return idUnitaMisura;
    }


    /**
     * Sets the idUnitaMisura value for this AttivitaEconomica.
     *
     * @param idUnitaMisura the id unita misura
     */
    public void setIdUnitaMisura(java.lang.String idUnitaMisura) {
        this.idUnitaMisura = idUnitaMisura;
    }


    /**
     * Gets the numeroOreEsercizio value for this AttivitaEconomica.
     *
     * @return numeroOreEsercizio numero ore esercizio
     */
    public java.lang.String getNumeroOreEsercizio() {
        return numeroOreEsercizio;
    }


    /**
     * Sets the numeroOreEsercizio value for this AttivitaEconomica.
     *
     * @param numeroOreEsercizio the numero ore esercizio
     */
    public void setNumeroOreEsercizio(java.lang.String numeroOreEsercizio) {
        this.numeroOreEsercizio = numeroOreEsercizio;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AttivitaEconomica)) return false;
        AttivitaEconomica other = (AttivitaEconomica) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = ((this.capacitaProduttivaMax == null && other.getCapacitaProduttivaMax() == null) ||
                (this.capacitaProduttivaMax != null &&
                        this.capacitaProduttivaMax.equals(other.getCapacitaProduttivaMax()))) &&
                ((this.codiceIppc == null && other.getCodiceIppc() == null) ||
                        (this.codiceIppc != null &&
                                this.codiceIppc.equals(other.getCodiceIppc()))) &&
                ((this.codiceIstatAteco91 == null && other.getCodiceIstatAteco91() == null) ||
                        (this.codiceIstatAteco91 != null &&
                                this.codiceIstatAteco91.equals(other.getCodiceIstatAteco91()))) &&
                ((this.codiceIstatComune == null && other.getCodiceIstatComune() == null) ||
                        (this.codiceIstatComune != null &&
                                this.codiceIstatComune.equals(other.getCodiceIstatComune()))) &&
                ((this.codiceIstatNace == null && other.getCodiceIstatNace() == null) ||
                        (this.codiceIstatNace != null &&
                                this.codiceIstatNace.equals(other.getCodiceIstatNace()))) &&
                ((this.codiceIstatProvincia == null && other.getCodiceIstatProvincia() == null) ||
                        (this.codiceIstatProvincia != null &&
                                this.codiceIstatProvincia.equals(other.getCodiceIstatProvincia()))) &&
                ((this.codiceNoseP == null && other.getCodiceNoseP() == null) ||
                        (this.codiceNoseP != null &&
                                this.codiceNoseP.equals(other.getCodiceNoseP()))) &&
                ((this.codiceSira == null && other.getCodiceSira() == null) ||
                        (this.codiceSira != null &&
                                this.codiceSira.equals(other.getCodiceSira()))) &&
                ((this.codiceSnap == null && other.getCodiceSnap() == null) ||
                        (this.codiceSnap != null &&
                                this.codiceSnap.equals(other.getCodiceSnap()))) &&
                ((this.dataFine == null && other.getDataFine() == null) ||
                        (this.dataFine != null &&
                                this.dataFine.equals(other.getDataFine()))) &&
                ((this.dataInizio == null && other.getDataInizio() == null) ||
                        (this.dataInizio != null &&
                                this.dataInizio.equals(other.getDataInizio()))) &&
                ((this.descrizioneComune == null && other.getDescrizioneComune() == null) ||
                        (this.descrizioneComune != null &&
                                this.descrizioneComune.equals(other.getDescrizioneComune()))) &&
                ((this.descrizioneFonte == null && other.getDescrizioneFonte() == null) ||
                        (this.descrizioneFonte != null &&
                                this.descrizioneFonte.equals(other.getDescrizioneFonte()))) &&
                ((this.descrizioneIppc == null && other.getDescrizioneIppc() == null) ||
                        (this.descrizioneIppc != null &&
                                this.descrizioneIppc.equals(other.getDescrizioneIppc()))) &&
                ((this.descrizioneIstatNace == null && other.getDescrizioneIstatNace() == null) ||
                        (this.descrizioneIstatNace != null &&
                                this.descrizioneIstatNace.equals(other.getDescrizioneIstatNace()))) &&
                ((this.descrizioneNoseP == null && other.getDescrizioneNoseP() == null) ||
                        (this.descrizioneNoseP != null &&
                                this.descrizioneNoseP.equals(other.getDescrizioneNoseP()))) &&
                ((this.descrizioneProvincia == null && other.getDescrizioneProvincia() == null) ||
                        (this.descrizioneProvincia != null &&
                                this.descrizioneProvincia.equals(other.getDescrizioneProvincia()))) &&
                ((this.descrizioneSnap == null && other.getDescrizioneSnap() == null) ||
                        (this.descrizioneSnap != null &&
                                this.descrizioneSnap.equals(other.getDescrizioneSnap()))) &&
                ((this.flagAttivitaPrincipale == null && other.getFlagAttivitaPrincipale() == null) ||
                        (this.flagAttivitaPrincipale != null &&
                                this.flagAttivitaPrincipale.equals(other.getFlagAttivitaPrincipale()))) &&
                ((this.flagAttivitaPrincipaleIppc == null && other.getFlagAttivitaPrincipaleIppc() == null) ||
                        (this.flagAttivitaPrincipaleIppc != null &&
                                this.flagAttivitaPrincipaleIppc.equals(other.getFlagAttivitaPrincipaleIppc()))) &&
                ((this.idFonte == null && other.getIdFonte() == null) ||
                        (this.idFonte != null &&
                                this.idFonte.equals(other.getIdFonte()))) &&
                ((this.idUnitaMisura == null && other.getIdUnitaMisura() == null) ||
                        (this.idUnitaMisura != null &&
                                this.idUnitaMisura.equals(other.getIdUnitaMisura()))) &&
                ((this.numeroOreEsercizio == null && other.getNumeroOreEsercizio() == null) ||
                        (this.numeroOreEsercizio != null &&
                                this.numeroOreEsercizio.equals(other.getNumeroOreEsercizio())));
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
        if (getCapacitaProduttivaMax() != null) {
            _hashCode += getCapacitaProduttivaMax().hashCode();
        }
        if (getCodiceIppc() != null) {
            _hashCode += getCodiceIppc().hashCode();
        }
        if (getCodiceIstatAteco91() != null) {
            _hashCode += getCodiceIstatAteco91().hashCode();
        }
        if (getCodiceIstatComune() != null) {
            _hashCode += getCodiceIstatComune().hashCode();
        }
        if (getCodiceIstatNace() != null) {
            _hashCode += getCodiceIstatNace().hashCode();
        }
        if (getCodiceIstatProvincia() != null) {
            _hashCode += getCodiceIstatProvincia().hashCode();
        }
        if (getCodiceNoseP() != null) {
            _hashCode += getCodiceNoseP().hashCode();
        }
        if (getCodiceSira() != null) {
            _hashCode += getCodiceSira().hashCode();
        }
        if (getCodiceSnap() != null) {
            _hashCode += getCodiceSnap().hashCode();
        }
        if (getDataFine() != null) {
            _hashCode += getDataFine().hashCode();
        }
        if (getDataInizio() != null) {
            _hashCode += getDataInizio().hashCode();
        }
        if (getDescrizioneComune() != null) {
            _hashCode += getDescrizioneComune().hashCode();
        }
        if (getDescrizioneFonte() != null) {
            _hashCode += getDescrizioneFonte().hashCode();
        }
        if (getDescrizioneIppc() != null) {
            _hashCode += getDescrizioneIppc().hashCode();
        }
        if (getDescrizioneIstatNace() != null) {
            _hashCode += getDescrizioneIstatNace().hashCode();
        }
        if (getDescrizioneNoseP() != null) {
            _hashCode += getDescrizioneNoseP().hashCode();
        }
        if (getDescrizioneProvincia() != null) {
            _hashCode += getDescrizioneProvincia().hashCode();
        }
        if (getDescrizioneSnap() != null) {
            _hashCode += getDescrizioneSnap().hashCode();
        }
        if (getFlagAttivitaPrincipale() != null) {
            _hashCode += getFlagAttivitaPrincipale().hashCode();
        }
        if (getFlagAttivitaPrincipaleIppc() != null) {
            _hashCode += getFlagAttivitaPrincipaleIppc().hashCode();
        }
        if (getIdFonte() != null) {
            _hashCode += getIdFonte().hashCode();
        }
        if (getIdUnitaMisura() != null) {
            _hashCode += getIdUnitaMisura().hashCode();
        }
        if (getNumeroOreEsercizio() != null) {
            _hashCode += getNumeroOreEsercizio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(AttivitaEconomica.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AttivitaEconomica"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacitaProduttivaMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacitaProduttivaMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceIppc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceIppc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceIstatAteco91");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceIstatAteco91"));
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
        elemField.setFieldName("codiceIstatNace");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceIstatNace"));
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
        elemField.setFieldName("codiceNoseP");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceNoseP"));
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
        elemField.setFieldName("codiceSnap");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceSnap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataFine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInizio"));
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
        elemField.setFieldName("descrizioneIppc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneIppc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneIstatNace");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneIstatNace"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneNoseP");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneNoseP"));
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
        elemField.setFieldName("descrizioneSnap");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneSnap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagAttivitaPrincipale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagAttivitaPrincipale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagAttivitaPrincipaleIppc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagAttivitaPrincipaleIppc"));
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
        elemField.setFieldName("idUnitaMisura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idUnitaMisura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroOreEsercizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroOreEsercizio"));
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
            java.lang.String mechType,
            java.lang.Class _javaType,
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
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new org.apache.axis.encoding.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }

}