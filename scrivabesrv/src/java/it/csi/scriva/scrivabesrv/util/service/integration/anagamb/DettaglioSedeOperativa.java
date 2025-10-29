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
 * DettaglioSedeOperativa.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.util.service.integration.anagamb;

/**
 * The type Dettaglio sede operativa.
 */
public class DettaglioSedeOperativa  implements java.io.Serializable {
    private AttivitaEconomica[] attivitaEconomiche;

    private java.lang.String dataFineAssociazione;

    private java.lang.String dataInizioAssociazione;

    private DettaglioSedeLegale sedeLegaleAssociata;

    private SedeOperativa sedeOperativa;

    /**
     * Instantiates a new Dettaglio sede operativa.
     */
    public DettaglioSedeOperativa() {
    }

    /**
     * Instantiates a new Dettaglio sede operativa.
     *
     * @param attivitaEconomiche     the attivita economiche
     * @param dataFineAssociazione   the data fine associazione
     * @param dataInizioAssociazione the data inizio associazione
     * @param sedeLegaleAssociata    the sede legale associata
     * @param sedeOperativa          the sede operativa
     */
    public DettaglioSedeOperativa(
           AttivitaEconomica[] attivitaEconomiche,
           java.lang.String dataFineAssociazione,
           java.lang.String dataInizioAssociazione,
           DettaglioSedeLegale sedeLegaleAssociata,
           SedeOperativa sedeOperativa) {
           this.attivitaEconomiche = attivitaEconomiche;
           this.dataFineAssociazione = dataFineAssociazione;
           this.dataInizioAssociazione = dataInizioAssociazione;
           this.sedeLegaleAssociata = sedeLegaleAssociata;
           this.sedeOperativa = sedeOperativa;
    }


    /**
     * Gets the attivitaEconomiche value for this DettaglioSedeOperativa.
     *
     * @return attivitaEconomiche attivita economica [ ]
     */
    public AttivitaEconomica[] getAttivitaEconomiche() {
        return attivitaEconomiche;
    }


    /**
     * Sets the attivitaEconomiche value for this DettaglioSedeOperativa.
     *
     * @param attivitaEconomiche the attivita economiche
     */
    public void setAttivitaEconomiche(AttivitaEconomica[] attivitaEconomiche) {
        this.attivitaEconomiche = attivitaEconomiche;
    }


    /**
     * Gets the dataFineAssociazione value for this DettaglioSedeOperativa.
     *
     * @return dataFineAssociazione data fine associazione
     */
    public java.lang.String getDataFineAssociazione() {
        return dataFineAssociazione;
    }


    /**
     * Sets the dataFineAssociazione value for this DettaglioSedeOperativa.
     *
     * @param dataFineAssociazione the data fine associazione
     */
    public void setDataFineAssociazione(java.lang.String dataFineAssociazione) {
        this.dataFineAssociazione = dataFineAssociazione;
    }


    /**
     * Gets the dataInizioAssociazione value for this DettaglioSedeOperativa.
     *
     * @return dataInizioAssociazione data inizio associazione
     */
    public java.lang.String getDataInizioAssociazione() {
        return dataInizioAssociazione;
    }


    /**
     * Sets the dataInizioAssociazione value for this DettaglioSedeOperativa.
     *
     * @param dataInizioAssociazione the data inizio associazione
     */
    public void setDataInizioAssociazione(java.lang.String dataInizioAssociazione) {
        this.dataInizioAssociazione = dataInizioAssociazione;
    }


    /**
     * Gets the sedeLegaleAssociata value for this DettaglioSedeOperativa.
     *
     * @return sedeLegaleAssociata sede legale associata
     */
    public DettaglioSedeLegale getSedeLegaleAssociata() {
        return sedeLegaleAssociata;
    }


    /**
     * Sets the sedeLegaleAssociata value for this DettaglioSedeOperativa.
     *
     * @param sedeLegaleAssociata the sede legale associata
     */
    public void setSedeLegaleAssociata(DettaglioSedeLegale sedeLegaleAssociata) {
        this.sedeLegaleAssociata = sedeLegaleAssociata;
    }


    /**
     * Gets the sedeOperativa value for this DettaglioSedeOperativa.
     *
     * @return sedeOperativa sede operativa
     */
    public SedeOperativa getSedeOperativa() {
        return sedeOperativa;
    }


    /**
     * Sets the sedeOperativa value for this DettaglioSedeOperativa.
     *
     * @param sedeOperativa the sede operativa
     */
    public void setSedeOperativa(SedeOperativa sedeOperativa) {
        this.sedeOperativa = sedeOperativa;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DettaglioSedeOperativa)) return false;
        DettaglioSedeOperativa other = (DettaglioSedeOperativa) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = ((this.attivitaEconomiche == null && other.getAttivitaEconomiche() == null) ||
                (this.attivitaEconomiche != null &&
                        java.util.Arrays.equals(this.attivitaEconomiche, other.getAttivitaEconomiche()))) &&
                ((this.dataFineAssociazione == null && other.getDataFineAssociazione() == null) ||
                        (this.dataFineAssociazione != null &&
                                this.dataFineAssociazione.equals(other.getDataFineAssociazione()))) &&
                ((this.dataInizioAssociazione == null && other.getDataInizioAssociazione() == null) ||
                        (this.dataInizioAssociazione != null &&
                                this.dataInizioAssociazione.equals(other.getDataInizioAssociazione()))) &&
                ((this.sedeLegaleAssociata == null && other.getSedeLegaleAssociata() == null) ||
                        (this.sedeLegaleAssociata != null &&
                                this.sedeLegaleAssociata.equals(other.getSedeLegaleAssociata()))) &&
                ((this.sedeOperativa == null && other.getSedeOperativa() == null) ||
                        (this.sedeOperativa != null &&
                                this.sedeOperativa.equals(other.getSedeOperativa())));
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
        if (getAttivitaEconomiche() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAttivitaEconomiche());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAttivitaEconomiche(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDataFineAssociazione() != null) {
            _hashCode += getDataFineAssociazione().hashCode();
        }
        if (getDataInizioAssociazione() != null) {
            _hashCode += getDataInizioAssociazione().hashCode();
        }
        if (getSedeLegaleAssociata() != null) {
            _hashCode += getSedeLegaleAssociata().hashCode();
        }
        if (getSedeOperativa() != null) {
            _hashCode += getSedeOperativa().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DettaglioSedeOperativa.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "DettaglioSedeOperativa"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attivitaEconomiche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attivitaEconomiche"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AttivitaEconomica"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFineAssociazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataFineAssociazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizioAssociazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInizioAssociazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sedeLegaleAssociata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sedeLegaleAssociata"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "DettaglioSedeLegale"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sedeOperativa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sedeOperativa"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SedeOperativa"));
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