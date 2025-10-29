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
 * TemaAutorizzativo.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.util.service.integration.anagamb;

/**
 * The type Tema autorizzativo.
 */
public class TemaAutorizzativo implements java.io.Serializable {
    private java.lang.String codiceAutorizzazione;

    private java.lang.String dataFine;

    private java.lang.String dataInizio;

    private java.lang.String descrizioneAmbitoTematico;

    private java.lang.String descrizioneAutorizzazione;

    /**
     * Instantiates a new Tema autorizzativo.
     */
    public TemaAutorizzativo() {
    }

    /**
     * Instantiates a new Tema autorizzativo.
     *
     * @param codiceAutorizzazione      the codice autorizzazione
     * @param dataFine                  the data fine
     * @param dataInizio                the data inizio
     * @param descrizioneAmbitoTematico the descrizione ambito tematico
     * @param descrizioneAutorizzazione the descrizione autorizzazione
     */
    public TemaAutorizzativo(
            java.lang.String codiceAutorizzazione,
            java.lang.String dataFine,
            java.lang.String dataInizio,
            java.lang.String descrizioneAmbitoTematico,
            java.lang.String descrizioneAutorizzazione) {
        this.codiceAutorizzazione = codiceAutorizzazione;
        this.dataFine = dataFine;
        this.dataInizio = dataInizio;
        this.descrizioneAmbitoTematico = descrizioneAmbitoTematico;
        this.descrizioneAutorizzazione = descrizioneAutorizzazione;
    }


    /**
     * Gets the codiceAutorizzazione value for this TemaAutorizzativo.
     *
     * @return codiceAutorizzazione codice autorizzazione
     */
    public java.lang.String getCodiceAutorizzazione() {
        return codiceAutorizzazione;
    }


    /**
     * Sets the codiceAutorizzazione value for this TemaAutorizzativo.
     *
     * @param codiceAutorizzazione the codice autorizzazione
     */
    public void setCodiceAutorizzazione(java.lang.String codiceAutorizzazione) {
        this.codiceAutorizzazione = codiceAutorizzazione;
    }


    /**
     * Gets the dataFine value for this TemaAutorizzativo.
     *
     * @return dataFine data fine
     */
    public java.lang.String getDataFine() {
        return dataFine;
    }


    /**
     * Sets the dataFine value for this TemaAutorizzativo.
     *
     * @param dataFine the data fine
     */
    public void setDataFine(java.lang.String dataFine) {
        this.dataFine = dataFine;
    }


    /**
     * Gets the dataInizio value for this TemaAutorizzativo.
     *
     * @return dataInizio data inizio
     */
    public java.lang.String getDataInizio() {
        return dataInizio;
    }


    /**
     * Sets the dataInizio value for this TemaAutorizzativo.
     *
     * @param dataInizio the data inizio
     */
    public void setDataInizio(java.lang.String dataInizio) {
        this.dataInizio = dataInizio;
    }


    /**
     * Gets the descrizioneAmbitoTematico value for this TemaAutorizzativo.
     *
     * @return descrizioneAmbitoTematico descrizione ambito tematico
     */
    public java.lang.String getDescrizioneAmbitoTematico() {
        return descrizioneAmbitoTematico;
    }


    /**
     * Sets the descrizioneAmbitoTematico value for this TemaAutorizzativo.
     *
     * @param descrizioneAmbitoTematico the descrizione ambito tematico
     */
    public void setDescrizioneAmbitoTematico(java.lang.String descrizioneAmbitoTematico) {
        this.descrizioneAmbitoTematico = descrizioneAmbitoTematico;
    }


    /**
     * Gets the descrizioneAutorizzazione value for this TemaAutorizzativo.
     *
     * @return descrizioneAutorizzazione descrizione autorizzazione
     */
    public java.lang.String getDescrizioneAutorizzazione() {
        return descrizioneAutorizzazione;
    }


    /**
     * Sets the descrizioneAutorizzazione value for this TemaAutorizzativo.
     *
     * @param descrizioneAutorizzazione the descrizione autorizzazione
     */
    public void setDescrizioneAutorizzazione(java.lang.String descrizioneAutorizzazione) {
        this.descrizioneAutorizzazione = descrizioneAutorizzazione;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TemaAutorizzativo)) return false;
        TemaAutorizzativo other = (TemaAutorizzativo) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = ((this.codiceAutorizzazione == null && other.getCodiceAutorizzazione() == null) ||
                (this.codiceAutorizzazione != null &&
                        this.codiceAutorizzazione.equals(other.getCodiceAutorizzazione()))) &&
                ((this.dataFine == null && other.getDataFine() == null) ||
                        (this.dataFine != null &&
                                this.dataFine.equals(other.getDataFine()))) &&
                ((this.dataInizio == null && other.getDataInizio() == null) ||
                        (this.dataInizio != null &&
                                this.dataInizio.equals(other.getDataInizio()))) &&
                ((this.descrizioneAmbitoTematico == null && other.getDescrizioneAmbitoTematico() == null) ||
                        (this.descrizioneAmbitoTematico != null &&
                                this.descrizioneAmbitoTematico.equals(other.getDescrizioneAmbitoTematico()))) &&
                ((this.descrizioneAutorizzazione == null && other.getDescrizioneAutorizzazione() == null) ||
                        (this.descrizioneAutorizzazione != null &&
                                this.descrizioneAutorizzazione.equals(other.getDescrizioneAutorizzazione())));
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
        if (getCodiceAutorizzazione() != null) {
            _hashCode += getCodiceAutorizzazione().hashCode();
        }
        if (getDataFine() != null) {
            _hashCode += getDataFine().hashCode();
        }
        if (getDataInizio() != null) {
            _hashCode += getDataInizio().hashCode();
        }
        if (getDescrizioneAmbitoTematico() != null) {
            _hashCode += getDescrizioneAmbitoTematico().hashCode();
        }
        if (getDescrizioneAutorizzazione() != null) {
            _hashCode += getDescrizioneAutorizzazione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(TemaAutorizzativo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "TemaAutorizzativo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceAutorizzazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceAutorizzazione"));
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
        elemField.setFieldName("descrizioneAmbitoTematico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneAmbitoTematico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneAutorizzazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneAutorizzazione"));
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