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
 * SedeLegale.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.util.service.integration.anagamb;

/**
 * The type Sede legale.
 */
public class SedeLegale implements java.io.Serializable {
    private java.lang.String codiceFiscale;

    private java.lang.String codiceIstatComune;

    private java.lang.String codiceIstatProvincia;

    private java.lang.String codiceSira;

    private java.lang.String descrizioneComune;

    private java.lang.String descrizioneFonte;

    private java.lang.String descrizioneProvincia;

    private java.lang.String idFonte;

    private java.lang.String indirizzo;

    private java.lang.String numeroCivico;

    private java.lang.String partitaIva;

    private java.lang.String ragioneSociale;

    private java.lang.String tipoSoggetto;

    /**
     * Instantiates a new Sede legale.
     */
    public SedeLegale() {
    }

    /**
     * Instantiates a new Sede legale.
     *
     * @param codiceFiscale        the codice fiscale
     * @param codiceIstatComune    the codice istat comune
     * @param codiceIstatProvincia the codice istat provincia
     * @param codiceSira           the codice sira
     * @param descrizioneComune    the descrizione comune
     * @param descrizioneFonte     the descrizione fonte
     * @param descrizioneProvincia the descrizione provincia
     * @param idFonte              the id fonte
     * @param indirizzo            the indirizzo
     * @param numeroCivico         the numero civico
     * @param partitaIva           the partita iva
     * @param ragioneSociale       the ragione sociale
     * @param tipoSoggetto         the tipo soggetto
     */
    public SedeLegale(
            java.lang.String codiceFiscale,
            java.lang.String codiceIstatComune,
            java.lang.String codiceIstatProvincia,
            java.lang.String codiceSira,
            java.lang.String descrizioneComune,
            java.lang.String descrizioneFonte,
            java.lang.String descrizioneProvincia,
            java.lang.String idFonte,
            java.lang.String indirizzo,
            java.lang.String numeroCivico,
            java.lang.String partitaIva,
            java.lang.String ragioneSociale,
            java.lang.String tipoSoggetto) {
        this.codiceFiscale = codiceFiscale;
        this.codiceIstatComune = codiceIstatComune;
        this.codiceIstatProvincia = codiceIstatProvincia;
        this.codiceSira = codiceSira;
        this.descrizioneComune = descrizioneComune;
        this.descrizioneFonte = descrizioneFonte;
        this.descrizioneProvincia = descrizioneProvincia;
        this.idFonte = idFonte;
        this.indirizzo = indirizzo;
        this.numeroCivico = numeroCivico;
        this.partitaIva = partitaIva;
        this.ragioneSociale = ragioneSociale;
        this.tipoSoggetto = tipoSoggetto;
    }


    /**
     * Gets the codiceFiscale value for this SedeLegale.
     *
     * @return codiceFiscale codice fiscale
     */
    public java.lang.String getCodiceFiscale() {
        return codiceFiscale;
    }


    /**
     * Sets the codiceFiscale value for this SedeLegale.
     *
     * @param codiceFiscale the codice fiscale
     */
    public void setCodiceFiscale(java.lang.String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }


    /**
     * Gets the codiceIstatComune value for this SedeLegale.
     *
     * @return codiceIstatComune codice istat comune
     */
    public java.lang.String getCodiceIstatComune() {
        return codiceIstatComune;
    }


    /**
     * Sets the codiceIstatComune value for this SedeLegale.
     *
     * @param codiceIstatComune the codice istat comune
     */
    public void setCodiceIstatComune(java.lang.String codiceIstatComune) {
        this.codiceIstatComune = codiceIstatComune;
    }


    /**
     * Gets the codiceIstatProvincia value for this SedeLegale.
     *
     * @return codiceIstatProvincia codice istat provincia
     */
    public java.lang.String getCodiceIstatProvincia() {
        return codiceIstatProvincia;
    }


    /**
     * Sets the codiceIstatProvincia value for this SedeLegale.
     *
     * @param codiceIstatProvincia the codice istat provincia
     */
    public void setCodiceIstatProvincia(java.lang.String codiceIstatProvincia) {
        this.codiceIstatProvincia = codiceIstatProvincia;
    }


    /**
     * Gets the codiceSira value for this SedeLegale.
     *
     * @return codiceSira codice sira
     */
    public java.lang.String getCodiceSira() {
        return codiceSira;
    }


    /**
     * Sets the codiceSira value for this SedeLegale.
     *
     * @param codiceSira the codice sira
     */
    public void setCodiceSira(java.lang.String codiceSira) {
        this.codiceSira = codiceSira;
    }


    /**
     * Gets the descrizioneComune value for this SedeLegale.
     *
     * @return descrizioneComune descrizione comune
     */
    public java.lang.String getDescrizioneComune() {
        return descrizioneComune;
    }


    /**
     * Sets the descrizioneComune value for this SedeLegale.
     *
     * @param descrizioneComune the descrizione comune
     */
    public void setDescrizioneComune(java.lang.String descrizioneComune) {
        this.descrizioneComune = descrizioneComune;
    }


    /**
     * Gets the descrizioneFonte value for this SedeLegale.
     *
     * @return descrizioneFonte descrizione fonte
     */
    public java.lang.String getDescrizioneFonte() {
        return descrizioneFonte;
    }


    /**
     * Sets the descrizioneFonte value for this SedeLegale.
     *
     * @param descrizioneFonte the descrizione fonte
     */
    public void setDescrizioneFonte(java.lang.String descrizioneFonte) {
        this.descrizioneFonte = descrizioneFonte;
    }


    /**
     * Gets the descrizioneProvincia value for this SedeLegale.
     *
     * @return descrizioneProvincia descrizione provincia
     */
    public java.lang.String getDescrizioneProvincia() {
        return descrizioneProvincia;
    }


    /**
     * Sets the descrizioneProvincia value for this SedeLegale.
     *
     * @param descrizioneProvincia the descrizione provincia
     */
    public void setDescrizioneProvincia(java.lang.String descrizioneProvincia) {
        this.descrizioneProvincia = descrizioneProvincia;
    }


    /**
     * Gets the idFonte value for this SedeLegale.
     *
     * @return idFonte id fonte
     */
    public java.lang.String getIdFonte() {
        return idFonte;
    }


    /**
     * Sets the idFonte value for this SedeLegale.
     *
     * @param idFonte the id fonte
     */
    public void setIdFonte(java.lang.String idFonte) {
        this.idFonte = idFonte;
    }


    /**
     * Gets the indirizzo value for this SedeLegale.
     *
     * @return indirizzo indirizzo
     */
    public java.lang.String getIndirizzo() {
        return indirizzo;
    }


    /**
     * Sets the indirizzo value for this SedeLegale.
     *
     * @param indirizzo the indirizzo
     */
    public void setIndirizzo(java.lang.String indirizzo) {
        this.indirizzo = indirizzo;
    }


    /**
     * Gets the numeroCivico value for this SedeLegale.
     *
     * @return numeroCivico numero civico
     */
    public java.lang.String getNumeroCivico() {
        return numeroCivico;
    }


    /**
     * Sets the numeroCivico value for this SedeLegale.
     *
     * @param numeroCivico the numero civico
     */
    public void setNumeroCivico(java.lang.String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }


    /**
     * Gets the partitaIva value for this SedeLegale.
     *
     * @return partitaIva partita iva
     */
    public java.lang.String getPartitaIva() {
        return partitaIva;
    }


    /**
     * Sets the partitaIva value for this SedeLegale.
     *
     * @param partitaIva the partita iva
     */
    public void setPartitaIva(java.lang.String partitaIva) {
        this.partitaIva = partitaIva;
    }


    /**
     * Gets the ragioneSociale value for this SedeLegale.
     *
     * @return ragioneSociale ragione sociale
     */
    public java.lang.String getRagioneSociale() {
        return ragioneSociale;
    }


    /**
     * Sets the ragioneSociale value for this SedeLegale.
     *
     * @param ragioneSociale the ragione sociale
     */
    public void setRagioneSociale(java.lang.String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }


    /**
     * Gets the tipoSoggetto value for this SedeLegale.
     *
     * @return tipoSoggetto tipo soggetto
     */
    public java.lang.String getTipoSoggetto() {
        return tipoSoggetto;
    }


    /**
     * Sets the tipoSoggetto value for this SedeLegale.
     *
     * @param tipoSoggetto the tipo soggetto
     */
    public void setTipoSoggetto(java.lang.String tipoSoggetto) {
        this.tipoSoggetto = tipoSoggetto;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SedeLegale)) return false;
        SedeLegale other = (SedeLegale) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = ((this.codiceFiscale == null && other.getCodiceFiscale() == null) ||
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
                ((this.descrizioneComune == null && other.getDescrizioneComune() == null) ||
                        (this.descrizioneComune != null &&
                                this.descrizioneComune.equals(other.getDescrizioneComune()))) &&
                ((this.descrizioneFonte == null && other.getDescrizioneFonte() == null) ||
                        (this.descrizioneFonte != null &&
                                this.descrizioneFonte.equals(other.getDescrizioneFonte()))) &&
                ((this.descrizioneProvincia == null && other.getDescrizioneProvincia() == null) ||
                        (this.descrizioneProvincia != null &&
                                this.descrizioneProvincia.equals(other.getDescrizioneProvincia()))) &&
                ((this.idFonte == null && other.getIdFonte() == null) ||
                        (this.idFonte != null &&
                                this.idFonte.equals(other.getIdFonte()))) &&
                ((this.indirizzo == null && other.getIndirizzo() == null) ||
                        (this.indirizzo != null &&
                                this.indirizzo.equals(other.getIndirizzo()))) &&
                ((this.numeroCivico == null && other.getNumeroCivico() == null) ||
                        (this.numeroCivico != null &&
                                this.numeroCivico.equals(other.getNumeroCivico()))) &&
                ((this.partitaIva == null && other.getPartitaIva() == null) ||
                        (this.partitaIva != null &&
                                this.partitaIva.equals(other.getPartitaIva()))) &&
                ((this.ragioneSociale == null && other.getRagioneSociale() == null) ||
                        (this.ragioneSociale != null &&
                                this.ragioneSociale.equals(other.getRagioneSociale()))) &&
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
        if (getDescrizioneComune() != null) {
            _hashCode += getDescrizioneComune().hashCode();
        }
        if (getDescrizioneFonte() != null) {
            _hashCode += getDescrizioneFonte().hashCode();
        }
        if (getDescrizioneProvincia() != null) {
            _hashCode += getDescrizioneProvincia().hashCode();
        }
        if (getIdFonte() != null) {
            _hashCode += getIdFonte().hashCode();
        }
        if (getIndirizzo() != null) {
            _hashCode += getIndirizzo().hashCode();
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
        if (getTipoSoggetto() != null) {
            _hashCode += getTipoSoggetto().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(SedeLegale.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SedeLegale"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("idFonte");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idFonte"));
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