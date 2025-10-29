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
 * DettaglioSedeLegale.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.util.service.integration.anagamb;

/**
 * The type Dettaglio sede legale.
 */
public class DettaglioSedeLegale  implements java.io.Serializable {
    private java.lang.String cap;

    private java.lang.String codiceFiscale;

    private java.lang.String codiceIstatComune;

    private java.lang.String codiceIstatProvincia;

    private java.lang.String codiceSira;

    private java.lang.String codiceVia;

    private java.lang.String dataCessazione;

    private java.lang.String dataInizioAttivita;

    private java.lang.String descrizioneComune;

    private java.lang.String descrizioneFonte;

    private java.lang.String descrizioneProvincia;

    private DettaglioRappresentanteSedeLegale dettaglioRappresentanteLegale;

    private java.lang.String email;

    private java.lang.String fax;

    private java.lang.String idComune;

    private java.lang.String idFonte;

    private java.lang.String idProvincia;

    private java.lang.String indirizzo;

    private java.lang.String localita;

    private java.lang.String numeroCivico;

    private java.lang.String partitaIva;

    private java.lang.String ragioneSociale;

    private java.lang.String telefono;

    private java.lang.String tipoSoggetto;

    /**
     * Instantiates a new Dettaglio sede legale.
     */
    public DettaglioSedeLegale() {
    }

    /**
     * Instantiates a new Dettaglio sede legale.
     *
     * @param cap                           the cap
     * @param codiceFiscale                 the codice fiscale
     * @param codiceIstatComune             the codice istat comune
     * @param codiceIstatProvincia          the codice istat provincia
     * @param codiceSira                    the codice sira
     * @param codiceVia                     the codice via
     * @param dataCessazione                the data cessazione
     * @param dataInizioAttivita            the data inizio attivita
     * @param descrizioneComune             the descrizione comune
     * @param descrizioneFonte              the descrizione fonte
     * @param descrizioneProvincia          the descrizione provincia
     * @param dettaglioRappresentanteLegale the dettaglio rappresentante legale
     * @param email                         the email
     * @param fax                           the fax
     * @param idComune                      the id comune
     * @param idFonte                       the id fonte
     * @param idProvincia                   the id provincia
     * @param indirizzo                     the indirizzo
     * @param localita                      the localita
     * @param numeroCivico                  the numero civico
     * @param partitaIva                    the partita iva
     * @param ragioneSociale                the ragione sociale
     * @param telefono                      the telefono
     * @param tipoSoggetto                  the tipo soggetto
     */
    public DettaglioSedeLegale(
           java.lang.String cap,
           java.lang.String codiceFiscale,
           java.lang.String codiceIstatComune,
           java.lang.String codiceIstatProvincia,
           java.lang.String codiceSira,
           java.lang.String codiceVia,
           java.lang.String dataCessazione,
           java.lang.String dataInizioAttivita,
           java.lang.String descrizioneComune,
           java.lang.String descrizioneFonte,
           java.lang.String descrizioneProvincia,
           DettaglioRappresentanteSedeLegale dettaglioRappresentanteLegale,
           java.lang.String email,
           java.lang.String fax,
           java.lang.String idComune,
           java.lang.String idFonte,
           java.lang.String idProvincia,
           java.lang.String indirizzo,
           java.lang.String localita,
           java.lang.String numeroCivico,
           java.lang.String partitaIva,
           java.lang.String ragioneSociale,
           java.lang.String telefono,
           java.lang.String tipoSoggetto) {
           this.cap = cap;
           this.codiceFiscale = codiceFiscale;
           this.codiceIstatComune = codiceIstatComune;
           this.codiceIstatProvincia = codiceIstatProvincia;
           this.codiceSira = codiceSira;
           this.codiceVia = codiceVia;
           this.dataCessazione = dataCessazione;
           this.dataInizioAttivita = dataInizioAttivita;
           this.descrizioneComune = descrizioneComune;
           this.descrizioneFonte = descrizioneFonte;
           this.descrizioneProvincia = descrizioneProvincia;
           this.dettaglioRappresentanteLegale = dettaglioRappresentanteLegale;
           this.email = email;
           this.fax = fax;
           this.idComune = idComune;
           this.idFonte = idFonte;
           this.idProvincia = idProvincia;
           this.indirizzo = indirizzo;
           this.localita = localita;
           this.numeroCivico = numeroCivico;
           this.partitaIva = partitaIva;
           this.ragioneSociale = ragioneSociale;
           this.telefono = telefono;
           this.tipoSoggetto = tipoSoggetto;
    }


    /**
     * Gets the cap value for this DettaglioSedeLegale.
     *
     * @return cap cap
     */
    public java.lang.String getCap() {
        return cap;
    }


    /**
     * Sets the cap value for this DettaglioSedeLegale.
     *
     * @param cap the cap
     */
    public void setCap(java.lang.String cap) {
        this.cap = cap;
    }


    /**
     * Gets the codiceFiscale value for this DettaglioSedeLegale.
     *
     * @return codiceFiscale codice fiscale
     */
    public java.lang.String getCodiceFiscale() {
        return codiceFiscale;
    }


    /**
     * Sets the codiceFiscale value for this DettaglioSedeLegale.
     *
     * @param codiceFiscale the codice fiscale
     */
    public void setCodiceFiscale(java.lang.String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }


    /**
     * Gets the codiceIstatComune value for this DettaglioSedeLegale.
     *
     * @return codiceIstatComune codice istat comune
     */
    public java.lang.String getCodiceIstatComune() {
        return codiceIstatComune;
    }


    /**
     * Sets the codiceIstatComune value for this DettaglioSedeLegale.
     *
     * @param codiceIstatComune the codice istat comune
     */
    public void setCodiceIstatComune(java.lang.String codiceIstatComune) {
        this.codiceIstatComune = codiceIstatComune;
    }


    /**
     * Gets the codiceIstatProvincia value for this DettaglioSedeLegale.
     *
     * @return codiceIstatProvincia codice istat provincia
     */
    public java.lang.String getCodiceIstatProvincia() {
        return codiceIstatProvincia;
    }


    /**
     * Sets the codiceIstatProvincia value for this DettaglioSedeLegale.
     *
     * @param codiceIstatProvincia the codice istat provincia
     */
    public void setCodiceIstatProvincia(java.lang.String codiceIstatProvincia) {
        this.codiceIstatProvincia = codiceIstatProvincia;
    }


    /**
     * Gets the codiceSira value for this DettaglioSedeLegale.
     *
     * @return codiceSira codice sira
     */
    public java.lang.String getCodiceSira() {
        return codiceSira;
    }


    /**
     * Sets the codiceSira value for this DettaglioSedeLegale.
     *
     * @param codiceSira the codice sira
     */
    public void setCodiceSira(java.lang.String codiceSira) {
        this.codiceSira = codiceSira;
    }


    /**
     * Gets the codiceVia value for this DettaglioSedeLegale.
     *
     * @return codiceVia codice via
     */
    public java.lang.String getCodiceVia() {
        return codiceVia;
    }


    /**
     * Sets the codiceVia value for this DettaglioSedeLegale.
     *
     * @param codiceVia the codice via
     */
    public void setCodiceVia(java.lang.String codiceVia) {
        this.codiceVia = codiceVia;
    }


    /**
     * Gets the dataCessazione value for this DettaglioSedeLegale.
     *
     * @return dataCessazione data cessazione
     */
    public java.lang.String getDataCessazione() {
        return dataCessazione;
    }


    /**
     * Sets the dataCessazione value for this DettaglioSedeLegale.
     *
     * @param dataCessazione the data cessazione
     */
    public void setDataCessazione(java.lang.String dataCessazione) {
        this.dataCessazione = dataCessazione;
    }


    /**
     * Gets the dataInizioAttivita value for this DettaglioSedeLegale.
     *
     * @return dataInizioAttivita data inizio attivita
     */
    public java.lang.String getDataInizioAttivita() {
        return dataInizioAttivita;
    }


    /**
     * Sets the dataInizioAttivita value for this DettaglioSedeLegale.
     *
     * @param dataInizioAttivita the data inizio attivita
     */
    public void setDataInizioAttivita(java.lang.String dataInizioAttivita) {
        this.dataInizioAttivita = dataInizioAttivita;
    }


    /**
     * Gets the descrizioneComune value for this DettaglioSedeLegale.
     *
     * @return descrizioneComune descrizione comune
     */
    public java.lang.String getDescrizioneComune() {
        return descrizioneComune;
    }


    /**
     * Sets the descrizioneComune value for this DettaglioSedeLegale.
     *
     * @param descrizioneComune the descrizione comune
     */
    public void setDescrizioneComune(java.lang.String descrizioneComune) {
        this.descrizioneComune = descrizioneComune;
    }


    /**
     * Gets the descrizioneFonte value for this DettaglioSedeLegale.
     *
     * @return descrizioneFonte descrizione fonte
     */
    public java.lang.String getDescrizioneFonte() {
        return descrizioneFonte;
    }


    /**
     * Sets the descrizioneFonte value for this DettaglioSedeLegale.
     *
     * @param descrizioneFonte the descrizione fonte
     */
    public void setDescrizioneFonte(java.lang.String descrizioneFonte) {
        this.descrizioneFonte = descrizioneFonte;
    }


    /**
     * Gets the descrizioneProvincia value for this DettaglioSedeLegale.
     *
     * @return descrizioneProvincia descrizione provincia
     */
    public java.lang.String getDescrizioneProvincia() {
        return descrizioneProvincia;
    }


    /**
     * Sets the descrizioneProvincia value for this DettaglioSedeLegale.
     *
     * @param descrizioneProvincia the descrizione provincia
     */
    public void setDescrizioneProvincia(java.lang.String descrizioneProvincia) {
        this.descrizioneProvincia = descrizioneProvincia;
    }


    /**
     * Gets the dettaglioRappresentanteLegale value for this DettaglioSedeLegale.
     *
     * @return dettaglioRappresentanteLegale dettaglio rappresentante legale
     */
    public DettaglioRappresentanteSedeLegale getDettaglioRappresentanteLegale() {
        return dettaglioRappresentanteLegale;
    }


    /**
     * Sets the dettaglioRappresentanteLegale value for this DettaglioSedeLegale.
     *
     * @param dettaglioRappresentanteLegale the dettaglio rappresentante legale
     */
    public void setDettaglioRappresentanteLegale(DettaglioRappresentanteSedeLegale dettaglioRappresentanteLegale) {
        this.dettaglioRappresentanteLegale = dettaglioRappresentanteLegale;
    }


    /**
     * Gets the email value for this DettaglioSedeLegale.
     *
     * @return email email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this DettaglioSedeLegale.
     *
     * @param email the email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }


    /**
     * Gets the fax value for this DettaglioSedeLegale.
     *
     * @return fax fax
     */
    public java.lang.String getFax() {
        return fax;
    }


    /**
     * Sets the fax value for this DettaglioSedeLegale.
     *
     * @param fax the fax
     */
    public void setFax(java.lang.String fax) {
        this.fax = fax;
    }


    /**
     * Gets the idComune value for this DettaglioSedeLegale.
     *
     * @return idComune id comune
     */
    public java.lang.String getIdComune() {
        return idComune;
    }


    /**
     * Sets the idComune value for this DettaglioSedeLegale.
     *
     * @param idComune the id comune
     */
    public void setIdComune(java.lang.String idComune) {
        this.idComune = idComune;
    }


    /**
     * Gets the idFonte value for this DettaglioSedeLegale.
     *
     * @return idFonte id fonte
     */
    public java.lang.String getIdFonte() {
        return idFonte;
    }


    /**
     * Sets the idFonte value for this DettaglioSedeLegale.
     *
     * @param idFonte the id fonte
     */
    public void setIdFonte(java.lang.String idFonte) {
        this.idFonte = idFonte;
    }


    /**
     * Gets the idProvincia value for this DettaglioSedeLegale.
     *
     * @return idProvincia id provincia
     */
    public java.lang.String getIdProvincia() {
        return idProvincia;
    }


    /**
     * Sets the idProvincia value for this DettaglioSedeLegale.
     *
     * @param idProvincia the id provincia
     */
    public void setIdProvincia(java.lang.String idProvincia) {
        this.idProvincia = idProvincia;
    }


    /**
     * Gets the indirizzo value for this DettaglioSedeLegale.
     *
     * @return indirizzo indirizzo
     */
    public java.lang.String getIndirizzo() {
        return indirizzo;
    }


    /**
     * Sets the indirizzo value for this DettaglioSedeLegale.
     *
     * @param indirizzo the indirizzo
     */
    public void setIndirizzo(java.lang.String indirizzo) {
        this.indirizzo = indirizzo;
    }


    /**
     * Gets the localita value for this DettaglioSedeLegale.
     *
     * @return localita localita
     */
    public java.lang.String getLocalita() {
        return localita;
    }


    /**
     * Sets the localita value for this DettaglioSedeLegale.
     *
     * @param localita the localita
     */
    public void setLocalita(java.lang.String localita) {
        this.localita = localita;
    }


    /**
     * Gets the numeroCivico value for this DettaglioSedeLegale.
     *
     * @return numeroCivico numero civico
     */
    public java.lang.String getNumeroCivico() {
        return numeroCivico;
    }


    /**
     * Sets the numeroCivico value for this DettaglioSedeLegale.
     *
     * @param numeroCivico the numero civico
     */
    public void setNumeroCivico(java.lang.String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }


    /**
     * Gets the partitaIva value for this DettaglioSedeLegale.
     *
     * @return partitaIva partita iva
     */
    public java.lang.String getPartitaIva() {
        return partitaIva;
    }


    /**
     * Sets the partitaIva value for this DettaglioSedeLegale.
     *
     * @param partitaIva the partita iva
     */
    public void setPartitaIva(java.lang.String partitaIva) {
        this.partitaIva = partitaIva;
    }


    /**
     * Gets the ragioneSociale value for this DettaglioSedeLegale.
     *
     * @return ragioneSociale ragione sociale
     */
    public java.lang.String getRagioneSociale() {
        return ragioneSociale;
    }


    /**
     * Sets the ragioneSociale value for this DettaglioSedeLegale.
     *
     * @param ragioneSociale the ragione sociale
     */
    public void setRagioneSociale(java.lang.String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }


    /**
     * Gets the telefono value for this DettaglioSedeLegale.
     *
     * @return telefono telefono
     */
    public java.lang.String getTelefono() {
        return telefono;
    }


    /**
     * Sets the telefono value for this DettaglioSedeLegale.
     *
     * @param telefono the telefono
     */
    public void setTelefono(java.lang.String telefono) {
        this.telefono = telefono;
    }


    /**
     * Gets the tipoSoggetto value for this DettaglioSedeLegale.
     *
     * @return tipoSoggetto tipo soggetto
     */
    public java.lang.String getTipoSoggetto() {
        return tipoSoggetto;
    }


    /**
     * Sets the tipoSoggetto value for this DettaglioSedeLegale.
     *
     * @param tipoSoggetto the tipo soggetto
     */
    public void setTipoSoggetto(java.lang.String tipoSoggetto) {
        this.tipoSoggetto = tipoSoggetto;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DettaglioSedeLegale)) return false;
        DettaglioSedeLegale other = (DettaglioSedeLegale) obj;
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
                ((this.codiceFiscale == null && other.getCodiceFiscale() == null) ||
                        (this.codiceFiscale != null &&
                                this.codiceFiscale.equals(other.getCodiceFiscale()))) &&
                ((this.codiceIstatComune == null && other.getCodiceIstatComune() == null) ||
                        (this.codiceIstatComune != null &&
                                this.codiceIstatComune.equals(other.getCodiceIstatComune()))) &&
                ((this.codiceIstatProvincia == null && other.getCodiceIstatProvincia() == null) ||
                        (this.codiceIstatProvincia != null &&
                                this.codiceIstatProvincia.equals(other.getCodiceIstatProvincia()))) &&
                ((this.codiceSira == null && other.getCodiceSira() == null) ||
                        (this.codiceSira != null &&
                                this.codiceSira.equals(other.getCodiceSira()))) &&
                ((this.codiceVia == null && other.getCodiceVia() == null) ||
                        (this.codiceVia != null &&
                                this.codiceVia.equals(other.getCodiceVia()))) &&
                ((this.dataCessazione == null && other.getDataCessazione() == null) ||
                        (this.dataCessazione != null &&
                                this.dataCessazione.equals(other.getDataCessazione()))) &&
                ((this.dataInizioAttivita == null && other.getDataInizioAttivita() == null) ||
                        (this.dataInizioAttivita != null &&
                                this.dataInizioAttivita.equals(other.getDataInizioAttivita()))) &&
                ((this.descrizioneComune == null && other.getDescrizioneComune() == null) ||
                        (this.descrizioneComune != null &&
                                this.descrizioneComune.equals(other.getDescrizioneComune()))) &&
                ((this.descrizioneFonte == null && other.getDescrizioneFonte() == null) ||
                        (this.descrizioneFonte != null &&
                                this.descrizioneFonte.equals(other.getDescrizioneFonte()))) &&
                ((this.descrizioneProvincia == null && other.getDescrizioneProvincia() == null) ||
                        (this.descrizioneProvincia != null &&
                                this.descrizioneProvincia.equals(other.getDescrizioneProvincia()))) &&
                ((this.dettaglioRappresentanteLegale == null && other.getDettaglioRappresentanteLegale() == null) ||
                        (this.dettaglioRappresentanteLegale != null &&
                                this.dettaglioRappresentanteLegale.equals(other.getDettaglioRappresentanteLegale()))) &&
                ((this.email == null && other.getEmail() == null) ||
                        (this.email != null &&
                                this.email.equals(other.getEmail()))) &&
                ((this.fax == null && other.getFax() == null) ||
                        (this.fax != null &&
                                this.fax.equals(other.getFax()))) &&
                ((this.idComune == null && other.getIdComune() == null) ||
                        (this.idComune != null &&
                                this.idComune.equals(other.getIdComune()))) &&
                ((this.idFonte == null && other.getIdFonte() == null) ||
                        (this.idFonte != null &&
                                this.idFonte.equals(other.getIdFonte()))) &&
                ((this.idProvincia == null && other.getIdProvincia() == null) ||
                        (this.idProvincia != null &&
                                this.idProvincia.equals(other.getIdProvincia()))) &&
                ((this.indirizzo == null && other.getIndirizzo() == null) ||
                        (this.indirizzo != null &&
                                this.indirizzo.equals(other.getIndirizzo()))) &&
                ((this.localita == null && other.getLocalita() == null) ||
                        (this.localita != null &&
                                this.localita.equals(other.getLocalita()))) &&
                ((this.numeroCivico == null && other.getNumeroCivico() == null) ||
                        (this.numeroCivico != null &&
                                this.numeroCivico.equals(other.getNumeroCivico()))) &&
                ((this.partitaIva == null && other.getPartitaIva() == null) ||
                        (this.partitaIva != null &&
                                this.partitaIva.equals(other.getPartitaIva()))) &&
                ((this.ragioneSociale == null && other.getRagioneSociale() == null) ||
                        (this.ragioneSociale != null &&
                                this.ragioneSociale.equals(other.getRagioneSociale()))) &&
                ((this.telefono == null && other.getTelefono() == null) ||
                        (this.telefono != null &&
                                this.telefono.equals(other.getTelefono()))) &&
                ((this.tipoSoggetto == null && other.getTipoSoggetto() == null) ||
                        (this.tipoSoggetto != null &&
                                this.tipoSoggetto.equals(other.getTipoSoggetto())));
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
        if (getCodiceFiscale() != null) {
            _hashCode += getCodiceFiscale().hashCode();
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
        if (getCodiceVia() != null) {
            _hashCode += getCodiceVia().hashCode();
        }
        if (getDataCessazione() != null) {
            _hashCode += getDataCessazione().hashCode();
        }
        if (getDataInizioAttivita() != null) {
            _hashCode += getDataInizioAttivita().hashCode();
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
        if (getDettaglioRappresentanteLegale() != null) {
            _hashCode += getDettaglioRappresentanteLegale().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getFax() != null) {
            _hashCode += getFax().hashCode();
        }
        if (getIdComune() != null) {
            _hashCode += getIdComune().hashCode();
        }
        if (getIdFonte() != null) {
            _hashCode += getIdFonte().hashCode();
        }
        if (getIdProvincia() != null) {
            _hashCode += getIdProvincia().hashCode();
        }
        if (getIndirizzo() != null) {
            _hashCode += getIndirizzo().hashCode();
        }
        if (getLocalita() != null) {
            _hashCode += getLocalita().hashCode();
        }
        if (getNumeroCivico() != null) {
            _hashCode += getNumeroCivico().hashCode();
        }
        if (getPartitaIva() != null) {
            _hashCode += getPartitaIva().hashCode();
        }
        if (getRagioneSociale() != null) {
            _hashCode += getRagioneSociale().hashCode();
        }
        if (getTelefono() != null) {
            _hashCode += getTelefono().hashCode();
        }
        if (getTipoSoggetto() != null) {
            _hashCode += getTipoSoggetto().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DettaglioSedeLegale.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "DettaglioSedeLegale"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cap");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFiscale"));
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
        elemField.setFieldName("codiceVia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceVia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataCessazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataCessazione"));
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
        elemField.setFieldName("dettaglioRappresentanteLegale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dettaglioRappresentanteLegale"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "DettaglioRappresentanteSedeLegale"));
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
        elemField.setFieldName("idComune");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idComune"));
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
        elemField.setFieldName("idProvincia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idProvincia"));
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
        elemField.setFieldName("numeroCivico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCivico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partitaIva");
        elemField.setXmlName(new javax.xml.namespace.QName("", "partitaIva"));
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
        elemField.setFieldName("tipoSoggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoSoggetto"));
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
          new  org.apache.axis.encoding.ser.BeanSerializer(
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
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}