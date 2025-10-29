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
 * Outcome.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.util.service.integration.anagamb;

/**
 * The type Outcome.
 */
public class Outcome implements java.io.Serializable {
    private CSIException eccezione;

    private java.lang.String messaggio;

    private int status;

    /**
     * Instantiates a new Outcome.
     */
    public Outcome() {
    }

    /**
     * Instantiates a new Outcome.
     *
     * @param eccezione the eccezione
     * @param messaggio the messaggio
     * @param status    the status
     */
    public Outcome(
            CSIException eccezione,
            java.lang.String messaggio,
            int status) {
        this.eccezione = eccezione;
        this.messaggio = messaggio;
        this.status = status;
    }


    /**
     * Gets the eccezione value for this Outcome.
     *
     * @return eccezione eccezione
     */
    public CSIException getEccezione() {
        return eccezione;
    }


    /**
     * Sets the eccezione value for this Outcome.
     *
     * @param eccezione the eccezione
     */
    public void setEccezione(CSIException eccezione) {
        this.eccezione = eccezione;
    }


    /**
     * Gets the messaggio value for this Outcome.
     *
     * @return messaggio messaggio
     */
    public java.lang.String getMessaggio() {
        return messaggio;
    }


    /**
     * Sets the messaggio value for this Outcome.
     *
     * @param messaggio the messaggio
     */
    public void setMessaggio(java.lang.String messaggio) {
        this.messaggio = messaggio;
    }


    /**
     * Gets the status value for this Outcome.
     *
     * @return status status
     */
    public int getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Outcome.
     *
     * @param status the status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Outcome)) return false;
        Outcome other = (Outcome) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = ((this.eccezione == null && other.getEccezione() == null) ||
                (this.eccezione != null &&
                        this.eccezione.equals(other.getEccezione()))) &&
                ((this.messaggio == null && other.getMessaggio() == null) ||
                        (this.messaggio != null &&
                                this.messaggio.equals(other.getMessaggio()))) &&
                this.status == other.getStatus();
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
        if (getEccezione() != null) {
            _hashCode += getEccezione().hashCode();
        }
        if (getMessaggio() != null) {
            _hashCode += getMessaggio().hashCode();
        }
        _hashCode += getStatus();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(Outcome.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "Outcome"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eccezione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eccezione"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messaggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "messaggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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