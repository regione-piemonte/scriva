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
 * Identita.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.iride.dto;

import it.csi.iride2.policy.exceptions.MalformedIdTokenException;

public class Identita implements java.io.Serializable {
    private String nome;

    private int livelloAutenticazione;

    private String codFiscale;

    private String timestamp;

    private String mac;

    private String idProvider;

    private String rappresentazioneInterna;

    private String cognome;

    public Identita() {
    }

    public Identita(
            String nome,
            int livelloAutenticazione,
            String codFiscale,
            String timestamp,
            String mac,
            String idProvider,
            String rappresentazioneInterna,
            String cognome) {
        this.nome = nome;
        this.livelloAutenticazione = livelloAutenticazione;
        this.codFiscale = codFiscale;
        this.timestamp = timestamp;
        this.mac = mac;
        this.idProvider = idProvider;
        this.rappresentazioneInterna = rappresentazioneInterna;
        this.cognome = cognome;
    }

    public Identita(String token) throws it.csi.iride2.policy.exceptions.MalformedIdTokenException {
        int slash1Index = token.indexOf(47);
        if (slash1Index == -1) {
            throw new it.csi.iride2.policy.exceptions.MalformedIdTokenException(token);
        } else {
            this.codFiscale = token.substring(0, slash1Index);
            int slash2Index = token.indexOf(47, slash1Index + 1);
            if (slash2Index == -1) {
                throw new it.csi.iride2.policy.exceptions.MalformedIdTokenException(token);
            } else {
                this.nome = token.substring(slash1Index + 1, slash2Index);
                int slash3Index = token.indexOf(47, slash2Index + 1);
                if (slash3Index == -1) {
                    throw new it.csi.iride2.policy.exceptions.MalformedIdTokenException(token);
                } else {
                    this.cognome = token.substring(slash2Index + 1, slash3Index);
                    int slash4Index = token.indexOf(47, slash3Index + 1);
                    if (slash4Index == -1) {
                        throw new it.csi.iride2.policy.exceptions.MalformedIdTokenException(token);
                    } else {
                        this.idProvider = token.substring(slash3Index + 1, slash4Index);
                        int slash5Index = token.indexOf(47, slash4Index + 1);
                        if (slash5Index == -1) {
                            throw new it.csi.iride2.policy.exceptions.MalformedIdTokenException(token);
                        } else {
                            this.timestamp = token.substring(slash4Index + 1, slash5Index);
                            int slash6Index = token.indexOf(47, slash5Index + 1);
                            if (slash6Index == -1) {
                                throw new MalformedIdTokenException(token);
                            } else {
                                this.livelloAutenticazione = Integer.parseInt(token.substring(slash5Index + 1, slash6Index));
                                this.mac = token.substring(slash6Index + 1);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets the nome value for this Identita.
     *
     * @return nome
     */
    public String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this Identita.
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }


    /**
     * Gets the livelloAutenticazione value for this Identita.
     *
     * @return livelloAutenticazione
     */
    public int getLivelloAutenticazione() {
        return livelloAutenticazione;
    }


    /**
     * Sets the livelloAutenticazione value for this Identita.
     *
     * @param livelloAutenticazione
     */
    public void setLivelloAutenticazione(int livelloAutenticazione) {
        this.livelloAutenticazione = livelloAutenticazione;
    }


    /**
     * Gets the codFiscale value for this Identita.
     *
     * @return codFiscale
     */
    public String getCodFiscale() {
        return codFiscale;
    }


    /**
     * Sets the codFiscale value for this Identita.
     *
     * @param codFiscale
     */
    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }


    /**
     * Gets the timestamp value for this Identita.
     *
     * @return timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }


    /**
     * Sets the timestamp value for this Identita.
     *
     * @param timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    /**
     * Gets the mac value for this Identita.
     *
     * @return mac
     */
    public String getMac() {
        return mac;
    }


    /**
     * Sets the mac value for this Identita.
     *
     * @param mac
     */
    public void setMac(String mac) {
        this.mac = mac;
    }


    /**
     * Gets the idProvider value for this Identita.
     *
     * @return idProvider
     */
    public String getIdProvider() {
        return idProvider;
    }


    /**
     * Sets the idProvider value for this Identita.
     *
     * @param idProvider
     */
    public void setIdProvider(String idProvider) {
        this.idProvider = idProvider;
    }


    /**
     * Gets the rappresentazioneInterna value for this Identita.
     *
     * @return rappresentazioneInterna
     */
    public String getRappresentazioneInterna() {
        return rappresentazioneInterna;
    }


    /**
     * Sets the rappresentazioneInterna value for this Identita.
     *
     * @param rappresentazioneInterna
     */
    public void setRappresentazioneInterna(String rappresentazioneInterna) {
        this.rappresentazioneInterna = rappresentazioneInterna;
    }


    /**
     * Gets the cognome value for this Identita.
     *
     * @return cognome
     */
    public String getCognome() {
        return cognome;
    }


    /**
     * Sets the cognome value for this Identita.
     *
     * @param cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    private Object __equalsCalc = null;

    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Identita)) return false;
        Identita other = (Identita) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.nome == null && other.getNome() == null) ||
                        (this.nome != null &&
                                this.nome.equals(other.getNome()))) &&
                this.livelloAutenticazione == other.getLivelloAutenticazione() &&
                ((this.codFiscale == null && other.getCodFiscale() == null) ||
                        (this.codFiscale != null &&
                                this.codFiscale.equals(other.getCodFiscale()))) &&
                ((this.timestamp == null && other.getTimestamp() == null) ||
                        (this.timestamp != null &&
                                this.timestamp.equals(other.getTimestamp()))) &&
                ((this.mac == null && other.getMac() == null) ||
                        (this.mac != null &&
                                this.mac.equals(other.getMac()))) &&
                ((this.idProvider == null && other.getIdProvider() == null) ||
                        (this.idProvider != null &&
                                this.idProvider.equals(other.getIdProvider()))) &&
                ((this.rappresentazioneInterna == null && other.getRappresentazioneInterna() == null) ||
                        (this.rappresentazioneInterna != null &&
                                this.rappresentazioneInterna.equals(other.getRappresentazioneInterna()))) &&
                ((this.cognome == null && other.getCognome() == null) ||
                        (this.cognome != null &&
                                this.cognome.equals(other.getCognome())));
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
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        _hashCode += getLivelloAutenticazione();
        if (getCodFiscale() != null) {
            _hashCode += getCodFiscale().hashCode();
        }
        if (getTimestamp() != null) {
            _hashCode += getTimestamp().hashCode();
        }
        if (getMac() != null) {
            _hashCode += getMac().hashCode();
        }
        if (getIdProvider() != null) {
            _hashCode += getIdProvider().hashCode();
        }
        if (getRappresentazioneInterna() != null) {
            _hashCode += getRappresentazioneInterna().hashCode();
        }
        if (getCognome() != null) {
            _hashCode += getCognome().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(Identita.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:PolicyEnforcerBase", "Identita"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("livelloAutenticazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "livelloAutenticazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codFiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codFiscale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mac");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mac"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idProvider");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idProvider"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rappresentazioneInterna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rappresentazioneInterna"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cognome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cognome"));
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

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "Identita {\n" +
                "         nome:'" + nome + "'" +
                ",\n         livelloAutenticazione:" + livelloAutenticazione +
                ",\n         codFiscale:'" + codFiscale + "'" +
                ",\n         timestamp:'" + timestamp + "'" +
                ",\n         mac:'" + mac + "'" +
                ",\n         idProvider:'" + idProvider + "'" +
                ",\n         rappresentazioneInterna:'" + rappresentazioneInterna + "'" +
                ",\n         cognome:'" + cognome + "'" +
                ",\n         __equalsCalc:" + __equalsCalc +
                ",\n         __hashCodeCalc:" + __hashCodeCalc +
                "}\n";
    }
}