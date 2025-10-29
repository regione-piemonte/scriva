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
 * Zps.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.parkapi.zps;

public class Zps  implements java.io.Serializable {
    private String codiceAmministrativo;

    private java.util.Calendar dataIstituzione;

    private java.util.Calendar dataModificaIstituzionale;

    private String geometriaGML;

    private String idInspire;

    private String nome;

    private double perimetroAmministrativo;

    private double superficieAmministrativa;

    private String[] url;

    public Zps() {
    }

    public Zps(
           String codiceAmministrativo,
           java.util.Calendar dataIstituzione,
           java.util.Calendar dataModificaIstituzionale,
           String geometriaGML,
           String idInspire,
           String nome,
           double perimetroAmministrativo,
           double superficieAmministrativa,
           String[] url) {
           this.codiceAmministrativo = codiceAmministrativo;
           this.dataIstituzione = dataIstituzione;
           this.dataModificaIstituzionale = dataModificaIstituzionale;
           this.geometriaGML = geometriaGML;
           this.idInspire = idInspire;
           this.nome = nome;
           this.perimetroAmministrativo = perimetroAmministrativo;
           this.superficieAmministrativa = superficieAmministrativa;
           this.url = url;
    }


    /**
     * Gets the codiceAmministrativo value for this Zps.
     *
     * @return codiceAmministrativo
     */
    public String getCodiceAmministrativo() {
        return codiceAmministrativo;
    }


    /**
     * Sets the codiceAmministrativo value for this Zps.
     *
     * @param codiceAmministrativo
     */
    public void setCodiceAmministrativo(String codiceAmministrativo) {
        this.codiceAmministrativo = codiceAmministrativo;
    }


    /**
     * Gets the dataIstituzione value for this Zps.
     *
     * @return dataIstituzione
     */
    public java.util.Calendar getDataIstituzione() {
        return dataIstituzione;
    }


    /**
     * Sets the dataIstituzione value for this Zps.
     *
     * @param dataIstituzione
     */
    public void setDataIstituzione(java.util.Calendar dataIstituzione) {
        this.dataIstituzione = dataIstituzione;
    }


    /**
     * Gets the dataModificaIstituzionale value for this Zps.
     *
     * @return dataModificaIstituzionale
     */
    public java.util.Calendar getDataModificaIstituzionale() {
        return dataModificaIstituzionale;
    }


    /**
     * Sets the dataModificaIstituzionale value for this Zps.
     *
     * @param dataModificaIstituzionale
     */
    public void setDataModificaIstituzionale(java.util.Calendar dataModificaIstituzionale) {
        this.dataModificaIstituzionale = dataModificaIstituzionale;
    }


    /**
     * Gets the geometriaGML value for this Zps.
     *
     * @return geometriaGML
     */
    public String getGeometriaGML() {
        return geometriaGML;
    }


    /**
     * Sets the geometriaGML value for this Zps.
     *
     * @param geometriaGML
     */
    public void setGeometriaGML(String geometriaGML) {
        this.geometriaGML = geometriaGML;
    }


    /**
     * Gets the idInspire value for this Zps.
     *
     * @return idInspire
     */
    public String getIdInspire() {
        return idInspire;
    }


    /**
     * Sets the idInspire value for this Zps.
     *
     * @param idInspire
     */
    public void setIdInspire(String idInspire) {
        this.idInspire = idInspire;
    }


    /**
     * Gets the nome value for this Zps.
     *
     * @return nome
     */
    public String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this Zps.
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }


    /**
     * Gets the perimetroAmministrativo value for this Zps.
     *
     * @return perimetroAmministrativo
     */
    public double getPerimetroAmministrativo() {
        return perimetroAmministrativo;
    }


    /**
     * Sets the perimetroAmministrativo value for this Zps.
     *
     * @param perimetroAmministrativo
     */
    public void setPerimetroAmministrativo(double perimetroAmministrativo) {
        this.perimetroAmministrativo = perimetroAmministrativo;
    }


    /**
     * Gets the superficieAmministrativa value for this Zps.
     *
     * @return superficieAmministrativa
     */
    public double getSuperficieAmministrativa() {
        return superficieAmministrativa;
    }


    /**
     * Sets the superficieAmministrativa value for this Zps.
     *
     * @param superficieAmministrativa
     */
    public void setSuperficieAmministrativa(double superficieAmministrativa) {
        this.superficieAmministrativa = superficieAmministrativa;
    }


    /**
     * Gets the url value for this Zps.
     *
     * @return url
     */
    public String[] getUrl() {
        return url;
    }


    /**
     * Sets the url value for this Zps.
     *
     * @param url
     */
    public void setUrl(String[] url) {
        this.url = url;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Zps)) return false;
        Zps other = (Zps) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = ((this.codiceAmministrativo == null && other.getCodiceAmministrativo() == null) ||
                (this.codiceAmministrativo != null &&
                        this.codiceAmministrativo.equals(other.getCodiceAmministrativo()))) &&
                ((this.dataIstituzione == null && other.getDataIstituzione() == null) ||
                        (this.dataIstituzione != null &&
                                this.dataIstituzione.equals(other.getDataIstituzione()))) &&
                ((this.dataModificaIstituzionale == null && other.getDataModificaIstituzionale() == null) ||
                        (this.dataModificaIstituzionale != null &&
                                this.dataModificaIstituzionale.equals(other.getDataModificaIstituzionale()))) &&
                ((this.geometriaGML == null && other.getGeometriaGML() == null) ||
                        (this.geometriaGML != null &&
                                this.geometriaGML.equals(other.getGeometriaGML()))) &&
                ((this.idInspire == null && other.getIdInspire() == null) ||
                        (this.idInspire != null &&
                                this.idInspire.equals(other.getIdInspire()))) &&
                ((this.nome == null && other.getNome() == null) ||
                        (this.nome != null &&
                                this.nome.equals(other.getNome()))) &&
                this.perimetroAmministrativo == other.getPerimetroAmministrativo() &&
                this.superficieAmministrativa == other.getSuperficieAmministrativa() &&
                ((this.url == null && other.getUrl() == null) ||
                        (this.url != null &&
                                java.util.Arrays.equals(this.url, other.getUrl())));
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
        if (getCodiceAmministrativo() != null) {
            _hashCode += getCodiceAmministrativo().hashCode();
        }
        if (getDataIstituzione() != null) {
            _hashCode += getDataIstituzione().hashCode();
        }
        if (getDataModificaIstituzionale() != null) {
            _hashCode += getDataModificaIstituzionale().hashCode();
        }
        if (getGeometriaGML() != null) {
            _hashCode += getGeometriaGML().hashCode();
        }
        if (getIdInspire() != null) {
            _hashCode += getIdInspire().hashCode();
        }
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        _hashCode += Double.valueOf(getPerimetroAmministrativo()).hashCode();
        _hashCode += Double.valueOf(getSuperficieAmministrativa()).hashCode();
        if (getUrl() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUrl());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getUrl(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Zps.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("zps", "Zps"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceAmministrativo");
        elemField.setXmlName(new javax.xml.namespace.QName("zps", "codiceAmministrativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataIstituzione");
        elemField.setXmlName(new javax.xml.namespace.QName("zps", "dataIstituzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataModificaIstituzionale");
        elemField.setXmlName(new javax.xml.namespace.QName("zps", "dataModificaIstituzionale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("geometriaGML");
        elemField.setXmlName(new javax.xml.namespace.QName("zps", "geometriaGML"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idInspire");
        elemField.setXmlName(new javax.xml.namespace.QName("zps", "idInspire"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("zps", "nome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("perimetroAmministrativo");
        elemField.setXmlName(new javax.xml.namespace.QName("zps", "perimetroAmministrativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieAmministrativa");
        elemField.setXmlName(new javax.xml.namespace.QName("zps", "superficieAmministrativa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("url");
        elemField.setXmlName(new javax.xml.namespace.QName("zps", "url"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("zps", "item"));
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