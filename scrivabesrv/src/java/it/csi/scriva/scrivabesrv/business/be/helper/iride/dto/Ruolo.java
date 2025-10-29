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
 * Ruolo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.iride.dto;

public class Ruolo  implements java.io.Serializable {
    private String codiceRuolo;

    private String mnemonico;

    private String codiceDominio;

    public Ruolo() {
    }

    public Ruolo(
           String codiceRuolo,
           String mnemonico,
           String codiceDominio) {
           this.codiceRuolo = codiceRuolo;
           this.mnemonico = mnemonico;
           this.codiceDominio = codiceDominio;
    }


    /**
     * Gets the codiceRuolo value for this Ruolo.
     *
     * @return codiceRuolo
     */
    public String getCodiceRuolo() {
        return codiceRuolo;
    }


    /**
     * Sets the codiceRuolo value for this Ruolo.
     *
     * @param codiceRuolo
     */
    public void setCodiceRuolo(String codiceRuolo) {
        this.codiceRuolo = codiceRuolo;
    }


    /**
     * Gets the mnemonico value for this Ruolo.
     *
     * @return mnemonico
     */
    public String getMnemonico() {
        return mnemonico;
    }


    /**
     * Sets the mnemonico value for this Ruolo.
     *
     * @param mnemonico
     */
    public void setMnemonico(String mnemonico) {
        this.mnemonico = mnemonico;
    }


    /**
     * Gets the codiceDominio value for this Ruolo.
     *
     * @return codiceDominio
     */
    public String getCodiceDominio() {
        return codiceDominio;
    }


    /**
     * Sets the codiceDominio value for this Ruolo.
     *
     * @param codiceDominio
     */
    public void setCodiceDominio(String codiceDominio) {
        this.codiceDominio = codiceDominio;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Ruolo)) return false;
        Ruolo other = (Ruolo) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.codiceRuolo==null && other.getCodiceRuolo()==null) ||
             (this.codiceRuolo!=null &&
              this.codiceRuolo.equals(other.getCodiceRuolo()))) &&
            ((this.mnemonico==null && other.getMnemonico()==null) ||
             (this.mnemonico!=null &&
              this.mnemonico.equals(other.getMnemonico()))) &&
            ((this.codiceDominio==null && other.getCodiceDominio()==null) ||
             (this.codiceDominio!=null &&
              this.codiceDominio.equals(other.getCodiceDominio())));
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
        if (getCodiceRuolo() != null) {
            _hashCode += getCodiceRuolo().hashCode();
        }
        if (getMnemonico() != null) {
            _hashCode += getMnemonico().hashCode();
        }
        if (getCodiceDominio() != null) {
            _hashCode += getCodiceDominio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Ruolo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:PolicyEnforcerBase", "Ruolo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceRuolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceRuolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mnemonico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mnemonico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceDominio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceDominio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}