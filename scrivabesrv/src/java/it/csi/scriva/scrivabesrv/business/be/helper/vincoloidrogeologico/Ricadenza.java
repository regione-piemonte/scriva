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
 * Ricadenza.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.vincoloidrogeologico;

public class Ricadenza implements java.io.Serializable {
    private boolean flagRicadenzaSignificativa;

    private int percentualeDiVincIdroOccupataDaGeo;

    public Ricadenza() {
    }

    public Ricadenza(
            boolean flagRicadenzaSignificativa,
            int percentualeDiVincIdroOccupataDaGeo) {
        this.flagRicadenzaSignificativa = flagRicadenzaSignificativa;
        this.percentualeDiVincIdroOccupataDaGeo = percentualeDiVincIdroOccupataDaGeo;
    }


    /**
     * Gets the flagRicadenzaSignificativa value for this Ricadenza.
     *
     * @return flagRicadenzaSignificativa
     */
    public boolean isFlagRicadenzaSignificativa() {
        return flagRicadenzaSignificativa;
    }


    /**
     * Sets the flagRicadenzaSignificativa value for this Ricadenza.
     *
     * @param flagRicadenzaSignificativa
     */
    public void setFlagRicadenzaSignificativa(boolean flagRicadenzaSignificativa) {
        this.flagRicadenzaSignificativa = flagRicadenzaSignificativa;
    }


    /**
     * Gets the percentualeDiVincIdroOccupataDaGeo value for this Ricadenza.
     *
     * @return percentualeDiVincIdroOccupataDaGeo
     */
    public int getPercentualeDiVincIdroOccupataDaGeo() {
        return percentualeDiVincIdroOccupataDaGeo;
    }


    /**
     * Sets the percentualeDiVincIdroOccupataDaGeo value for this Ricadenza.
     *
     * @param percentualeDiVincIdroOccupataDaGeo
     */
    public void setPercentualeDiVincIdroOccupataDaGeo(int percentualeDiVincIdroOccupataDaGeo) {
        this.percentualeDiVincIdroOccupataDaGeo = percentualeDiVincIdroOccupataDaGeo;
    }

    private Object __equalsCalc = null;

    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Ricadenza)) return false;
        Ricadenza other = (Ricadenza) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                this.flagRicadenzaSignificativa == other.isFlagRicadenzaSignificativa() &&
                this.percentualeDiVincIdroOccupataDaGeo == other.getPercentualeDiVincIdroOccupataDaGeo();
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
        _hashCode += (isFlagRicadenzaSignificativa() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getPercentualeDiVincIdroOccupataDaGeo();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(Ricadenza.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("vincoloIdrogeologico", "Ricadenza"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagRicadenzaSignificativa");
        elemField.setXmlName(new javax.xml.namespace.QName("vincoloIdrogeologico", "flagRicadenzaSignificativa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentualeDiVincIdroOccupataDaGeo");
        elemField.setXmlName(new javax.xml.namespace.QName("vincoloIdrogeologico", "percentualeDiVincIdroOccupataDaGeo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
                new org.apache.axis.encoding.ser.BeanSerializer(
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
                new org.apache.axis.encoding.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }

}