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
 * InvocationNode.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.util.service.integration.anagamb;

/**
 * The type Invocation node.
 */
public class InvocationNode implements java.io.Serializable {


    private InvocationNode[] childs;

    private Outcome outcome;

    private CalledResource resource;

    private long startTime;

    private long stopTime;

    /**
     * Instantiates a new Invocation node.
     */
    public InvocationNode() {
    }

    /**
     * Instantiates a new Invocation node.
     *
     * @param childs    the childs
     * @param outcome   the outcome
     * @param resource  the resource
     * @param startTime the start time
     * @param stopTime  the stop time
     */
    public InvocationNode(
            InvocationNode[] childs,
            Outcome outcome,
            CalledResource resource,
            long startTime,
            long stopTime) {
        this.childs = childs;
        this.outcome = outcome;
        this.resource = resource;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }


    /**
     * Gets the childs value for this InvocationNode.
     *
     * @return childs invocation node [ ]
     */
    public InvocationNode[] getChilds() {
        return childs;
    }


    /**
     * Sets the childs value for this InvocationNode.
     *
     * @param childs the childs
     */
    public void setChilds(InvocationNode[] childs) {
        this.childs = childs;
    }


    /**
     * Gets the outcome value for this InvocationNode.
     *
     * @return outcome outcome
     */
    public Outcome getOutcome() {
        return outcome;
    }


    /**
     * Sets the outcome value for this InvocationNode.
     *
     * @param outcome the outcome
     */
    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }


    /**
     * Gets the resource value for this InvocationNode.
     *
     * @return resource resource
     */
    public CalledResource getResource() {
        return resource;
    }


    /**
     * Sets the resource value for this InvocationNode.
     *
     * @param resource the resource
     */
    public void setResource(CalledResource resource) {
        this.resource = resource;
    }


    /**
     * Gets the startTime value for this InvocationNode.
     *
     * @return startTime start time
     */
    public long getStartTime() {
        return startTime;
    }


    /**
     * Sets the startTime value for this InvocationNode.
     *
     * @param startTime the start time
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }


    /**
     * Gets the stopTime value for this InvocationNode.
     *
     * @return stopTime stop time
     */
    public long getStopTime() {
        return stopTime;
    }


    /**
     * Sets the stopTime value for this InvocationNode.
     *
     * @param stopTime the stop time
     */
    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InvocationNode)) return false;
        InvocationNode other = (InvocationNode) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = ((this.childs == null && other.getChilds() == null) ||
                (this.childs != null &&
                        java.util.Arrays.equals(this.childs, other.getChilds()))) &&
                ((this.outcome == null && other.getOutcome() == null) ||
                        (this.outcome != null &&
                                this.outcome.equals(other.getOutcome()))) &&
                ((this.resource == null && other.getResource() == null) ||
                        (this.resource != null &&
                                this.resource.equals(other.getResource()))) &&
                this.startTime == other.getStartTime() &&
                this.stopTime == other.getStopTime();
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
        if (getChilds() != null) {
            for (int i = 0;
                 i < java.lang.reflect.Array.getLength(getChilds());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getChilds(), i);
                if (obj != null &&
                        !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getOutcome() != null) {
            _hashCode += getOutcome().hashCode();
        }
        if (getResource() != null) {
            _hashCode += getResource().hashCode();
        }
        _hashCode += Long.valueOf(getStartTime()).hashCode();
        _hashCode += Long.valueOf(getStopTime()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(InvocationNode.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "InvocationNode"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("childs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "childs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "InvocationNode"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outcome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "outcome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "Outcome"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resource");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resource"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "CalledResource"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "startTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stopTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stopTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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