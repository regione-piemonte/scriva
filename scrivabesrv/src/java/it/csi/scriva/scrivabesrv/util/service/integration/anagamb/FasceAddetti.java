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
 * FasceAddetti.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.util.service.integration.anagamb;

/**
 * The type Fasce addetti.
 */
public class FasceAddetti implements java.io.Serializable {
    private java.lang.String descrizioneFascia;

    private java.lang.String fasciaA;

    private java.lang.String fasciaDa;

    private java.lang.String idFascia;

    /**
     * Instantiates a new Fasce addetti.
     */
    public FasceAddetti() {
    }

    /**
     * Instantiates a new Fasce addetti.
     *
     * @param descrizioneFascia the descrizione fascia
     * @param fasciaA           the fascia a
     * @param fasciaDa          the fascia da
     * @param idFascia          the id fascia
     */
    public FasceAddetti(
            java.lang.String descrizioneFascia,
            java.lang.String fasciaA,
            java.lang.String fasciaDa,
            java.lang.String idFascia) {
        this.descrizioneFascia = descrizioneFascia;
        this.fasciaA = fasciaA;
        this.fasciaDa = fasciaDa;
        this.idFascia = idFascia;
    }


    /**
     * Gets the descrizioneFascia value for this FasceAddetti.
     *
     * @return descrizioneFascia descrizione fascia
     */
    public java.lang.String getDescrizioneFascia() {
        return descrizioneFascia;
    }


    /**
     * Sets the descrizioneFascia value for this FasceAddetti.
     *
     * @param descrizioneFascia the descrizione fascia
     */
    public void setDescrizioneFascia(java.lang.String descrizioneFascia) {
        this.descrizioneFascia = descrizioneFascia;
    }


    /**
     * Gets the fasciaA value for this FasceAddetti.
     *
     * @return fasciaA fascia a
     */
    public java.lang.String getFasciaA() {
        return fasciaA;
    }


    /**
     * Sets the fasciaA value for this FasceAddetti.
     *
     * @param fasciaA the fascia a
     */
    public void setFasciaA(java.lang.String fasciaA) {
        this.fasciaA = fasciaA;
    }


    /**
     * Gets the fasciaDa value for this FasceAddetti.
     *
     * @return fasciaDa fascia da
     */
    public java.lang.String getFasciaDa() {
        return fasciaDa;
    }


    /**
     * Sets the fasciaDa value for this FasceAddetti.
     *
     * @param fasciaDa the fascia da
     */
    public void setFasciaDa(java.lang.String fasciaDa) {
        this.fasciaDa = fasciaDa;
    }


    /**
     * Gets the idFascia value for this FasceAddetti.
     *
     * @return idFascia id fascia
     */
    public java.lang.String getIdFascia() {
        return idFascia;
    }


    /**
     * Sets the idFascia value for this FasceAddetti.
     *
     * @param idFascia the id fascia
     */
    public void setIdFascia(java.lang.String idFascia) {
        this.idFascia = idFascia;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FasceAddetti)) return false;
        FasceAddetti other = (FasceAddetti) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = ((this.descrizioneFascia == null && other.getDescrizioneFascia() == null) ||
                (this.descrizioneFascia != null &&
                        this.descrizioneFascia.equals(other.getDescrizioneFascia()))) &&
                ((this.fasciaA == null && other.getFasciaA() == null) ||
                        (this.fasciaA != null &&
                                this.fasciaA.equals(other.getFasciaA()))) &&
                ((this.fasciaDa == null && other.getFasciaDa() == null) ||
                        (this.fasciaDa != null &&
                                this.fasciaDa.equals(other.getFasciaDa()))) &&
                ((this.idFascia == null && other.getIdFascia() == null) ||
                        (this.idFascia != null &&
                                this.idFascia.equals(other.getIdFascia())));
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
        if (getDescrizioneFascia() != null) {
            _hashCode += getDescrizioneFascia().hashCode();
        }
        if (getFasciaA() != null) {
            _hashCode += getFasciaA().hashCode();
        }
        if (getFasciaDa() != null) {
            _hashCode += getFasciaDa().hashCode();
        }
        if (getIdFascia() != null) {
            _hashCode += getIdFascia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(FasceAddetti.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "FasceAddetti"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneFascia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneFascia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fasciaA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fasciaA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fasciaDa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fasciaDa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFascia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idFascia"));
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