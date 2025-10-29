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
 * Rn2000.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.parkapi.rn2000;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Calendar;

/**
 * The type Rn 2000.
 */
public class Rn2000 implements java.io.Serializable {
    private String[] arrayIstatComune;

    private String codiceAmministrativo;

    private String codiceEnteGestore;

    private String codiceTipoEnte;

    private String codiceTipoRn2000;

    private Calendar dataAggiornIstituzionale;

    private Calendar dataIstituzione;

    private String denominazione;

    private String enteGestore;

    private String idInspire;

    private int idReteNatura;

    private double percentualeRicadenzaGeometriaInRn2000;

    private double percentualeRn2000OccupataDaGeometria;

    private double perimetroAmministrativo;

    private double superficieAmministrativa;

    private double superficieIntersezione;

    private double superficieRn2000;

    private String tipoEnte;

    private String tipoRn2000;

    /**
     * Instantiates a new Rn 2000.
     */
    public Rn2000() {
    }

    /**
     * Instantiates a new Rn 2000.
     *
     * @param arrayIstatComune                      the array istat comune
     * @param codiceAmministrativo                  the codice amministrativo
     * @param codiceEnteGestore                     the codice ente gestore
     * @param codiceTipoEnte                        the codice tipo ente
     * @param codiceTipoRn2000                      the codice tipo rn 2000
     * @param dataAggiornIstituzionale              the data aggiorn istituzionale
     * @param dataIstituzione                       the data istituzione
     * @param denominazione                         the denominazione
     * @param enteGestore                           the ente gestore
     * @param idInspire                             the id inspire
     * @param idReteNatura                          the id rete natura
     * @param percentualeRicadenzaGeometriaInRn2000 the percentuale ricadenza geometria in rn 2000
     * @param percentualeRn2000OccupataDaGeometria  the percentuale rn 2000 occupata da geometria
     * @param perimetroAmministrativo               the perimetro amministrativo
     * @param superficieAmministrativa              the superficie amministrativa
     * @param superficieIntersezione                the superficie intersezione
     * @param superficieRn2000                      the superficie rn 2000
     * @param tipoEnte                              the tipo ente
     * @param tipoRn2000                            the tipo rn 2000
     */
    public Rn2000(
            String[] arrayIstatComune,
            String codiceAmministrativo,
            String codiceEnteGestore,
            String codiceTipoEnte,
            String codiceTipoRn2000,
            Calendar dataAggiornIstituzionale,
            Calendar dataIstituzione,
            String denominazione,
            String enteGestore,
            String idInspire,
            int idReteNatura,
            double percentualeRicadenzaGeometriaInRn2000,
            double percentualeRn2000OccupataDaGeometria,
            double perimetroAmministrativo,
            double superficieAmministrativa,
            double superficieIntersezione,
            double superficieRn2000,
            String tipoEnte,
            String tipoRn2000) {
        this.arrayIstatComune = arrayIstatComune;
        this.codiceAmministrativo = codiceAmministrativo;
        this.codiceEnteGestore = codiceEnteGestore;
        this.codiceTipoEnte = codiceTipoEnte;
        this.codiceTipoRn2000 = codiceTipoRn2000;
        this.dataAggiornIstituzionale = dataAggiornIstituzionale;
        this.dataIstituzione = dataIstituzione;
        this.denominazione = denominazione;
        this.enteGestore = enteGestore;
        this.idInspire = idInspire;
        this.idReteNatura = idReteNatura;
        this.percentualeRicadenzaGeometriaInRn2000 = percentualeRicadenzaGeometriaInRn2000;
        this.percentualeRn2000OccupataDaGeometria = percentualeRn2000OccupataDaGeometria;
        this.perimetroAmministrativo = perimetroAmministrativo;
        this.superficieAmministrativa = superficieAmministrativa;
        this.superficieIntersezione = superficieIntersezione;
        this.superficieRn2000 = superficieRn2000;
        this.tipoEnte = tipoEnte;
        this.tipoRn2000 = tipoRn2000;
    }


    /**
     * Gets the arrayIstatComune value for this Rn2000.
     *
     * @return arrayIstatComune string [ ]
     */
    public String[] getArrayIstatComune() {
        return arrayIstatComune;
    }


    /**
     * Sets the arrayIstatComune value for this Rn2000.
     *
     * @param arrayIstatComune the array istat comune
     */
    public void setArrayIstatComune(String[] arrayIstatComune) {
        this.arrayIstatComune = arrayIstatComune;
    }


    /**
     * Gets the codiceAmministrativo value for this Rn2000.
     *
     * @return codiceAmministrativo codice amministrativo
     */
    public String getCodiceAmministrativo() {
        return codiceAmministrativo;
    }


    /**
     * Sets the codiceAmministrativo value for this Rn2000.
     *
     * @param codiceAmministrativo the codice amministrativo
     */
    public void setCodiceAmministrativo(String codiceAmministrativo) {
        this.codiceAmministrativo = codiceAmministrativo;
    }


    /**
     * Gets the codiceEnteGestore value for this Rn2000.
     *
     * @return codiceEnteGestore codice ente gestore
     */
    public String getCodiceEnteGestore() {
        return codiceEnteGestore;
    }


    /**
     * Sets the codiceEnteGestore value for this Rn2000.
     *
     * @param codiceEnteGestore the codice ente gestore
     */
    public void setCodiceEnteGestore(String codiceEnteGestore) {
        this.codiceEnteGestore = codiceEnteGestore;
    }


    /**
     * Gets the codiceTipoEnte value for this Rn2000.
     *
     * @return codiceTipoEnte codice tipo ente
     */
    public String getCodiceTipoEnte() {
        return codiceTipoEnte;
    }


    /**
     * Sets the codiceTipoEnte value for this Rn2000.
     *
     * @param codiceTipoEnte the codice tipo ente
     */
    public void setCodiceTipoEnte(String codiceTipoEnte) {
        this.codiceTipoEnte = codiceTipoEnte;
    }


    /**
     * Gets the codiceTipoRn2000 value for this Rn2000.
     *
     * @return codiceTipoRn2000 codice tipo rn 2000
     */
    public String getCodiceTipoRn2000() {
        return codiceTipoRn2000;
    }


    /**
     * Sets the codiceTipoRn2000 value for this Rn2000.
     *
     * @param codiceTipoRn2000 the codice tipo rn 2000
     */
    public void setCodiceTipoRn2000(String codiceTipoRn2000) {
        this.codiceTipoRn2000 = codiceTipoRn2000;
    }


    /**
     * Gets the dataAggiornIstituzionale value for this Rn2000.
     *
     * @return dataAggiornIstituzionale data aggiorn istituzionale
     */
    public Calendar getDataAggiornIstituzionale() {
        return dataAggiornIstituzionale;
    }


    /**
     * Sets the dataAggiornIstituzionale value for this Rn2000.
     *
     * @param dataAggiornIstituzionale the data aggiorn istituzionale
     */
    public void setDataAggiornIstituzionale(Calendar dataAggiornIstituzionale) {
        this.dataAggiornIstituzionale = dataAggiornIstituzionale;
    }


    /**
     * Gets the dataIstituzione value for this Rn2000.
     *
     * @return dataIstituzione data istituzione
     */
    public Calendar getDataIstituzione() {
        return dataIstituzione;
    }


    /**
     * Sets the dataIstituzione value for this Rn2000.
     *
     * @param dataIstituzione the data istituzione
     */
    public void setDataIstituzione(Calendar dataIstituzione) {
        this.dataIstituzione = dataIstituzione;
    }


    /**
     * Gets the denominazione value for this Rn2000.
     *
     * @return denominazione denominazione
     */
    public String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this Rn2000.
     *
     * @param denominazione the denominazione
     */
    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the enteGestore value for this Rn2000.
     *
     * @return enteGestore ente gestore
     */
    public String getEnteGestore() {
        return enteGestore;
    }


    /**
     * Sets the enteGestore value for this Rn2000.
     *
     * @param enteGestore the ente gestore
     */
    public void setEnteGestore(String enteGestore) {
        this.enteGestore = enteGestore;
    }


    /**
     * Gets the idInspire value for this Rn2000.
     *
     * @return idInspire id inspire
     */
    public String getIdInspire() {
        return idInspire;
    }


    /**
     * Sets the idInspire value for this Rn2000.
     *
     * @param idInspire the id inspire
     */
    public void setIdInspire(String idInspire) {
        this.idInspire = idInspire;
    }


    /**
     * Gets the idReteNatura value for this Rn2000.
     *
     * @return idReteNatura id rete natura
     */
    public int getIdReteNatura() {
        return idReteNatura;
    }


    /**
     * Sets the idReteNatura value for this Rn2000.
     *
     * @param idReteNatura the id rete natura
     */
    public void setIdReteNatura(int idReteNatura) {
        this.idReteNatura = idReteNatura;
    }


    /**
     * Gets the percentualeRicadenzaGeometriaInRn2000 value for this Rn2000.
     *
     * @return percentualeRicadenzaGeometriaInRn2000 percentuale ricadenza geometria in rn 2000
     */
    public double getPercentualeRicadenzaGeometriaInRn2000() {
        return percentualeRicadenzaGeometriaInRn2000;
    }


    /**
     * Sets the percentualeRicadenzaGeometriaInRn2000 value for this Rn2000.
     *
     * @param percentualeRicadenzaGeometriaInRn2000 the percentuale ricadenza geometria in rn 2000
     */
    public void setPercentualeRicadenzaGeometriaInRn2000(double percentualeRicadenzaGeometriaInRn2000) {
        this.percentualeRicadenzaGeometriaInRn2000 = percentualeRicadenzaGeometriaInRn2000;
    }


    /**
     * Gets the percentualeRn2000OccupataDaGeometria value for this Rn2000.
     *
     * @return percentualeRn2000OccupataDaGeometria percentuale rn 2000 occupata da geometria
     */
    public double getPercentualeRn2000OccupataDaGeometria() {
        return percentualeRn2000OccupataDaGeometria;
    }


    /**
     * Sets the percentualeRn2000OccupataDaGeometria value for this Rn2000.
     *
     * @param percentualeRn2000OccupataDaGeometria the percentuale rn 2000 occupata da geometria
     */
    public void setPercentualeRn2000OccupataDaGeometria(double percentualeRn2000OccupataDaGeometria) {
        this.percentualeRn2000OccupataDaGeometria = percentualeRn2000OccupataDaGeometria;
    }


    /**
     * Gets the perimetroAmministrativo value for this Rn2000.
     *
     * @return perimetroAmministrativo perimetro amministrativo
     */
    public double getPerimetroAmministrativo() {
        return perimetroAmministrativo;
    }


    /**
     * Sets the perimetroAmministrativo value for this Rn2000.
     *
     * @param perimetroAmministrativo the perimetro amministrativo
     */
    public void setPerimetroAmministrativo(double perimetroAmministrativo) {
        this.perimetroAmministrativo = perimetroAmministrativo;
    }


    /**
     * Gets the superficieAmministrativa value for this Rn2000.
     *
     * @return superficieAmministrativa superficie amministrativa
     */
    public double getSuperficieAmministrativa() {
        return superficieAmministrativa;
    }


    /**
     * Sets the superficieAmministrativa value for this Rn2000.
     *
     * @param superficieAmministrativa the superficie amministrativa
     */
    public void setSuperficieAmministrativa(double superficieAmministrativa) {
        this.superficieAmministrativa = superficieAmministrativa;
    }


    /**
     * Gets the superficieIntersezione value for this Rn2000.
     *
     * @return superficieIntersezione superficie intersezione
     */
    public double getSuperficieIntersezione() {
        return superficieIntersezione;
    }


    /**
     * Sets the superficieIntersezione value for this Rn2000.
     *
     * @param superficieIntersezione the superficie intersezione
     */
    public void setSuperficieIntersezione(double superficieIntersezione) {
        this.superficieIntersezione = superficieIntersezione;
    }


    /**
     * Gets the superficieRn2000 value for this Rn2000.
     *
     * @return superficieRn2000 superficie rn 2000
     */
    public double getSuperficieRn2000() {
        return superficieRn2000;
    }


    /**
     * Sets the superficieRn2000 value for this Rn2000.
     *
     * @param superficieRn2000 the superficie rn 2000
     */
    public void setSuperficieRn2000(double superficieRn2000) {
        this.superficieRn2000 = superficieRn2000;
    }


    /**
     * Gets the tipoEnte value for this Rn2000.
     *
     * @return tipoEnte tipo ente
     */
    public String getTipoEnte() {
        return tipoEnte;
    }


    /**
     * Sets the tipoEnte value for this Rn2000.
     *
     * @param tipoEnte the tipo ente
     */
    public void setTipoEnte(String tipoEnte) {
        this.tipoEnte = tipoEnte;
    }


    /**
     * Gets the tipoRn2000 value for this Rn2000.
     *
     * @return tipoRn2000 tipo rn 2000
     */
    public String getTipoRn2000() {
        return tipoRn2000;
    }


    /**
     * Sets the tipoRn2000 value for this Rn2000.
     *
     * @param tipoRn2000 the tipo rn 2000
     */
    public void setTipoRn2000(String tipoRn2000) {
        this.tipoRn2000 = tipoRn2000;
    }

    private Object __equalsCalc = null;

    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Rn2000)) return false;
        Rn2000 other = (Rn2000) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.arrayIstatComune == null && other.getArrayIstatComune() == null) ||
                        (this.arrayIstatComune != null &&
                                Arrays.equals(this.arrayIstatComune, other.getArrayIstatComune()))) &&
                ((this.codiceAmministrativo == null && other.getCodiceAmministrativo() == null) ||
                        (this.codiceAmministrativo != null &&
                                this.codiceAmministrativo.equals(other.getCodiceAmministrativo()))) &&
                ((this.codiceEnteGestore == null && other.getCodiceEnteGestore() == null) ||
                        (this.codiceEnteGestore != null &&
                                this.codiceEnteGestore.equals(other.getCodiceEnteGestore()))) &&
                ((this.codiceTipoEnte == null && other.getCodiceTipoEnte() == null) ||
                        (this.codiceTipoEnte != null &&
                                this.codiceTipoEnte.equals(other.getCodiceTipoEnte()))) &&
                ((this.codiceTipoRn2000 == null && other.getCodiceTipoRn2000() == null) ||
                        (this.codiceTipoRn2000 != null &&
                                this.codiceTipoRn2000.equals(other.getCodiceTipoRn2000()))) &&
                ((this.dataAggiornIstituzionale == null && other.getDataAggiornIstituzionale() == null) ||
                        (this.dataAggiornIstituzionale != null &&
                                this.dataAggiornIstituzionale.equals(other.getDataAggiornIstituzionale()))) &&
                ((this.dataIstituzione == null && other.getDataIstituzione() == null) ||
                        (this.dataIstituzione != null &&
                                this.dataIstituzione.equals(other.getDataIstituzione()))) &&
                ((this.denominazione == null && other.getDenominazione() == null) ||
                        (this.denominazione != null &&
                                this.denominazione.equals(other.getDenominazione()))) &&
                ((this.enteGestore == null && other.getEnteGestore() == null) ||
                        (this.enteGestore != null &&
                                this.enteGestore.equals(other.getEnteGestore()))) &&
                ((this.idInspire == null && other.getIdInspire() == null) ||
                        (this.idInspire != null &&
                                this.idInspire.equals(other.getIdInspire()))) &&
                this.idReteNatura == other.getIdReteNatura() &&
                this.percentualeRicadenzaGeometriaInRn2000 == other.getPercentualeRicadenzaGeometriaInRn2000() &&
                this.percentualeRn2000OccupataDaGeometria == other.getPercentualeRn2000OccupataDaGeometria() &&
                this.perimetroAmministrativo == other.getPerimetroAmministrativo() &&
                this.superficieAmministrativa == other.getSuperficieAmministrativa() &&
                this.superficieIntersezione == other.getSuperficieIntersezione() &&
                this.superficieRn2000 == other.getSuperficieRn2000() &&
                ((this.tipoEnte == null && other.getTipoEnte() == null) ||
                        (this.tipoEnte != null &&
                                this.tipoEnte.equals(other.getTipoEnte()))) &&
                ((this.tipoRn2000 == null && other.getTipoRn2000() == null) ||
                        (this.tipoRn2000 != null &&
                                this.tipoRn2000.equals(other.getTipoRn2000())));
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
        if (getArrayIstatComune() != null) {
            for (int i = 0; i < Array.getLength(getArrayIstatComune()); i++) {
                Object obj = Array.get(getArrayIstatComune(), i);
                if (obj != null && !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCodiceAmministrativo() != null) {
            _hashCode += getCodiceAmministrativo().hashCode();
        }
        if (getCodiceEnteGestore() != null) {
            _hashCode += getCodiceEnteGestore().hashCode();
        }
        if (getCodiceTipoEnte() != null) {
            _hashCode += getCodiceTipoEnte().hashCode();
        }
        if (getCodiceTipoRn2000() != null) {
            _hashCode += getCodiceTipoRn2000().hashCode();
        }
        if (getDataAggiornIstituzionale() != null) {
            _hashCode += getDataAggiornIstituzionale().hashCode();
        }
        if (getDataIstituzione() != null) {
            _hashCode += getDataIstituzione().hashCode();
        }
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getEnteGestore() != null) {
            _hashCode += getEnteGestore().hashCode();
        }
        if (getIdInspire() != null) {
            _hashCode += getIdInspire().hashCode();
        }
        _hashCode += getIdReteNatura();
        _hashCode += Double.valueOf(getPercentualeRicadenzaGeometriaInRn2000()).hashCode();
        _hashCode += Double.valueOf(getPercentualeRn2000OccupataDaGeometria()).hashCode();
        _hashCode += Double.valueOf(getPerimetroAmministrativo()).hashCode();
        _hashCode += Double.valueOf(getSuperficieAmministrativa()).hashCode();
        _hashCode += Double.valueOf(getSuperficieIntersezione()).hashCode();
        _hashCode += Double.valueOf(getSuperficieRn2000()).hashCode();
        if (getTipoEnte() != null) {
            _hashCode += getTipoEnte().hashCode();
        }
        if (getTipoRn2000() != null) {
            _hashCode += getTipoRn2000().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static TypeDesc typeDesc = new TypeDesc(Rn2000.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("rn2000", "Rn2000"));
        ElementDesc elemField = new ElementDesc();
        elemField.setFieldName("arrayIstatComune");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "arrayIstatComune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("rn2000", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("codiceAmministrativo");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "codiceAmministrativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("codiceEnteGestore");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "codiceEnteGestore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("codiceTipoEnte");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "codiceTipoEnte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("codiceTipoRn2000");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "codiceTipoRn2000"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("dataAggiornIstituzionale");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "dataAggiornIstituzionale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("dataIstituzione");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "dataIstituzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("enteGestore");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "enteGestore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("idInspire");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "idInspire"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("idReteNatura");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "idReteNatura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("percentualeRicadenzaGeometriaInRn2000");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "percentualeRicadenzaGeometriaInRn2000"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("percentualeRn2000OccupataDaGeometria");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "percentualeRn2000OccupataDaGeometria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("perimetroAmministrativo");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "perimetroAmministrativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("superficieAmministrativa");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "superficieAmministrativa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("superficieIntersezione");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "superficieIntersezione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("superficieRn2000");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "superficieRn2000"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("tipoEnte");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "tipoEnte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("tipoRn2000");
        elemField.setXmlName(new javax.xml.namespace.QName("rn2000", "tipoRn2000"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     *
     * @return the type desc
     */
    public static TypeDesc getTypeDesc() {
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
            String mechType,
            Class _javaType,
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
            String mechType,
            Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new org.apache.axis.encoding.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }

}