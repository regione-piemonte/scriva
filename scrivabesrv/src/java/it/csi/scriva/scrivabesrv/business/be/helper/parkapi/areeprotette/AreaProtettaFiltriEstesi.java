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
 * AreaProtettaFiltriEstesi.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.parkapi.areeprotette;

public class AreaProtettaFiltriEstesi  implements java.io.Serializable {
    private String[] arrayIstatComune;

    private String codiceAmministrativo;

    private String codiceEnteGestore;

    private String codiceEuap;

    private String codicePatrimonialita;

    private String codiceTipoAreaProtetta;

    private String codiceTipoEnte;

    private java.util.Calendar dataAggiornIstituzionale;

    private java.util.Calendar dataIstituzione;

    private String denominazione;

    private String enteGestore;

    private int idAreaProtetta;

    private String idInspire;

    private String leggeIstitutiva;

    private String patrimonialita;

    private double percentualeApOccupataDaGeometria;

    private double percentualeRicadenzaGeometriaInAp;

    private double perimetroAmministrativo;

    private double superficieAmministrativa;

    private double superficieAreaProtetta;

    private double superficieIntersezione;

    private String tipoAreaProtetta;

    private String tipoEnte;

    public AreaProtettaFiltriEstesi() {
    }

    public AreaProtettaFiltriEstesi(
           String[] arrayIstatComune,
           String codiceAmministrativo,
           String codiceEnteGestore,
           String codiceEuap,
           String codicePatrimonialita,
           String codiceTipoAreaProtetta,
           String codiceTipoEnte,
           java.util.Calendar dataAggiornIstituzionale,
           java.util.Calendar dataIstituzione,
           String denominazione,
           String enteGestore,
           int idAreaProtetta,
           String idInspire,
           String leggeIstitutiva,
           String patrimonialita,
           double percentualeApOccupataDaGeometria,
           double percentualeRicadenzaGeometriaInAp,
           double perimetroAmministrativo,
           double superficieAmministrativa,
           double superficieAreaProtetta,
           double superficieIntersezione,
           String tipoAreaProtetta,
           String tipoEnte) {
           this.arrayIstatComune = arrayIstatComune;
           this.codiceAmministrativo = codiceAmministrativo;
           this.codiceEnteGestore = codiceEnteGestore;
           this.codiceEuap = codiceEuap;
           this.codicePatrimonialita = codicePatrimonialita;
           this.codiceTipoAreaProtetta = codiceTipoAreaProtetta;
           this.codiceTipoEnte = codiceTipoEnte;
           this.dataAggiornIstituzionale = dataAggiornIstituzionale;
           this.dataIstituzione = dataIstituzione;
           this.denominazione = denominazione;
           this.enteGestore = enteGestore;
           this.idAreaProtetta = idAreaProtetta;
           this.idInspire = idInspire;
           this.leggeIstitutiva = leggeIstitutiva;
           this.patrimonialita = patrimonialita;
           this.percentualeApOccupataDaGeometria = percentualeApOccupataDaGeometria;
           this.percentualeRicadenzaGeometriaInAp = percentualeRicadenzaGeometriaInAp;
           this.perimetroAmministrativo = perimetroAmministrativo;
           this.superficieAmministrativa = superficieAmministrativa;
           this.superficieAreaProtetta = superficieAreaProtetta;
           this.superficieIntersezione = superficieIntersezione;
           this.tipoAreaProtetta = tipoAreaProtetta;
           this.tipoEnte = tipoEnte;
    }


    /**
     * Gets the arrayIstatComune value for this AreaProtettaFiltriEstesi.
     *
     * @return arrayIstatComune
     */
    public String[] getArrayIstatComune() {
        return arrayIstatComune;
    }


    /**
     * Sets the arrayIstatComune value for this AreaProtettaFiltriEstesi.
     *
     * @param arrayIstatComune
     */
    public void setArrayIstatComune(String[] arrayIstatComune) {
        this.arrayIstatComune = arrayIstatComune;
    }


    /**
     * Gets the codiceAmministrativo value for this AreaProtettaFiltriEstesi.
     *
     * @return codiceAmministrativo
     */
    public String getCodiceAmministrativo() {
        return codiceAmministrativo;
    }


    /**
     * Sets the codiceAmministrativo value for this AreaProtettaFiltriEstesi.
     *
     * @param codiceAmministrativo
     */
    public void setCodiceAmministrativo(String codiceAmministrativo) {
        this.codiceAmministrativo = codiceAmministrativo;
    }


    /**
     * Gets the codiceEnteGestore value for this AreaProtettaFiltriEstesi.
     *
     * @return codiceEnteGestore
     */
    public String getCodiceEnteGestore() {
        return codiceEnteGestore;
    }


    /**
     * Sets the codiceEnteGestore value for this AreaProtettaFiltriEstesi.
     *
     * @param codiceEnteGestore
     */
    public void setCodiceEnteGestore(String codiceEnteGestore) {
        this.codiceEnteGestore = codiceEnteGestore;
    }


    /**
     * Gets the codiceEuap value for this AreaProtettaFiltriEstesi.
     *
     * @return codiceEuap
     */
    public String getCodiceEuap() {
        return codiceEuap;
    }


    /**
     * Sets the codiceEuap value for this AreaProtettaFiltriEstesi.
     *
     * @param codiceEuap
     */
    public void setCodiceEuap(String codiceEuap) {
        this.codiceEuap = codiceEuap;
    }


    /**
     * Gets the codicePatrimonialita value for this AreaProtettaFiltriEstesi.
     *
     * @return codicePatrimonialita
     */
    public String getCodicePatrimonialita() {
        return codicePatrimonialita;
    }


    /**
     * Sets the codicePatrimonialita value for this AreaProtettaFiltriEstesi.
     *
     * @param codicePatrimonialita
     */
    public void setCodicePatrimonialita(String codicePatrimonialita) {
        this.codicePatrimonialita = codicePatrimonialita;
    }


    /**
     * Gets the codiceTipoAreaProtetta value for this AreaProtettaFiltriEstesi.
     *
     * @return codiceTipoAreaProtetta
     */
    public String getCodiceTipoAreaProtetta() {
        return codiceTipoAreaProtetta;
    }


    /**
     * Sets the codiceTipoAreaProtetta value for this AreaProtettaFiltriEstesi.
     *
     * @param codiceTipoAreaProtetta
     */
    public void setCodiceTipoAreaProtetta(String codiceTipoAreaProtetta) {
        this.codiceTipoAreaProtetta = codiceTipoAreaProtetta;
    }


    /**
     * Gets the codiceTipoEnte value for this AreaProtettaFiltriEstesi.
     *
     * @return codiceTipoEnte
     */
    public String getCodiceTipoEnte() {
        return codiceTipoEnte;
    }


    /**
     * Sets the codiceTipoEnte value for this AreaProtettaFiltriEstesi.
     *
     * @param codiceTipoEnte
     */
    public void setCodiceTipoEnte(String codiceTipoEnte) {
        this.codiceTipoEnte = codiceTipoEnte;
    }


    /**
     * Gets the dataAggiornIstituzionale value for this AreaProtettaFiltriEstesi.
     *
     * @return dataAggiornIstituzionale
     */
    public java.util.Calendar getDataAggiornIstituzionale() {
        return dataAggiornIstituzionale;
    }


    /**
     * Sets the dataAggiornIstituzionale value for this AreaProtettaFiltriEstesi.
     *
     * @param dataAggiornIstituzionale
     */
    public void setDataAggiornIstituzionale(java.util.Calendar dataAggiornIstituzionale) {
        this.dataAggiornIstituzionale = dataAggiornIstituzionale;
    }


    /**
     * Gets the dataIstituzione value for this AreaProtettaFiltriEstesi.
     *
     * @return dataIstituzione
     */
    public java.util.Calendar getDataIstituzione() {
        return dataIstituzione;
    }


    /**
     * Sets the dataIstituzione value for this AreaProtettaFiltriEstesi.
     *
     * @param dataIstituzione
     */
    public void setDataIstituzione(java.util.Calendar dataIstituzione) {
        this.dataIstituzione = dataIstituzione;
    }


    /**
     * Gets the denominazione value for this AreaProtettaFiltriEstesi.
     *
     * @return denominazione
     */
    public String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this AreaProtettaFiltriEstesi.
     *
     * @param denominazione
     */
    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the enteGestore value for this AreaProtettaFiltriEstesi.
     *
     * @return enteGestore
     */
    public String getEnteGestore() {
        return enteGestore;
    }


    /**
     * Sets the enteGestore value for this AreaProtettaFiltriEstesi.
     *
     * @param enteGestore
     */
    public void setEnteGestore(String enteGestore) {
        this.enteGestore = enteGestore;
    }


    /**
     * Gets the idAreaProtetta value for this AreaProtettaFiltriEstesi.
     *
     * @return idAreaProtetta
     */
    public int getIdAreaProtetta() {
        return idAreaProtetta;
    }


    /**
     * Sets the idAreaProtetta value for this AreaProtettaFiltriEstesi.
     *
     * @param idAreaProtetta
     */
    public void setIdAreaProtetta(int idAreaProtetta) {
        this.idAreaProtetta = idAreaProtetta;
    }


    /**
     * Gets the idInspire value for this AreaProtettaFiltriEstesi.
     *
     * @return idInspire
     */
    public String getIdInspire() {
        return idInspire;
    }


    /**
     * Sets the idInspire value for this AreaProtettaFiltriEstesi.
     *
     * @param idInspire
     */
    public void setIdInspire(String idInspire) {
        this.idInspire = idInspire;
    }


    /**
     * Gets the leggeIstitutiva value for this AreaProtettaFiltriEstesi.
     *
     * @return leggeIstitutiva
     */
    public String getLeggeIstitutiva() {
        return leggeIstitutiva;
    }


    /**
     * Sets the leggeIstitutiva value for this AreaProtettaFiltriEstesi.
     *
     * @param leggeIstitutiva
     */
    public void setLeggeIstitutiva(String leggeIstitutiva) {
        this.leggeIstitutiva = leggeIstitutiva;
    }


    /**
     * Gets the patrimonialita value for this AreaProtettaFiltriEstesi.
     *
     * @return patrimonialita
     */
    public String getPatrimonialita() {
        return patrimonialita;
    }


    /**
     * Sets the patrimonialita value for this AreaProtettaFiltriEstesi.
     *
     * @param patrimonialita
     */
    public void setPatrimonialita(String patrimonialita) {
        this.patrimonialita = patrimonialita;
    }


    /**
     * Gets the percentualeApOccupataDaGeometria value for this AreaProtettaFiltriEstesi.
     *
     * @return percentualeApOccupataDaGeometria
     */
    public double getPercentualeApOccupataDaGeometria() {
        return percentualeApOccupataDaGeometria;
    }


    /**
     * Sets the percentualeApOccupataDaGeometria value for this AreaProtettaFiltriEstesi.
     *
     * @param percentualeApOccupataDaGeometria
     */
    public void setPercentualeApOccupataDaGeometria(double percentualeApOccupataDaGeometria) {
        this.percentualeApOccupataDaGeometria = percentualeApOccupataDaGeometria;
    }


    /**
     * Gets the percentualeRicadenzaGeometriaInAp value for this AreaProtettaFiltriEstesi.
     *
     * @return percentualeRicadenzaGeometriaInAp
     */
    public double getPercentualeRicadenzaGeometriaInAp() {
        return percentualeRicadenzaGeometriaInAp;
    }


    /**
     * Sets the percentualeRicadenzaGeometriaInAp value for this AreaProtettaFiltriEstesi.
     *
     * @param percentualeRicadenzaGeometriaInAp
     */
    public void setPercentualeRicadenzaGeometriaInAp(double percentualeRicadenzaGeometriaInAp) {
        this.percentualeRicadenzaGeometriaInAp = percentualeRicadenzaGeometriaInAp;
    }


    /**
     * Gets the perimetroAmministrativo value for this AreaProtettaFiltriEstesi.
     *
     * @return perimetroAmministrativo
     */
    public double getPerimetroAmministrativo() {
        return perimetroAmministrativo;
    }


    /**
     * Sets the perimetroAmministrativo value for this AreaProtettaFiltriEstesi.
     *
     * @param perimetroAmministrativo
     */
    public void setPerimetroAmministrativo(double perimetroAmministrativo) {
        this.perimetroAmministrativo = perimetroAmministrativo;
    }


    /**
     * Gets the superficieAmministrativa value for this AreaProtettaFiltriEstesi.
     *
     * @return superficieAmministrativa
     */
    public double getSuperficieAmministrativa() {
        return superficieAmministrativa;
    }


    /**
     * Sets the superficieAmministrativa value for this AreaProtettaFiltriEstesi.
     *
     * @param superficieAmministrativa
     */
    public void setSuperficieAmministrativa(double superficieAmministrativa) {
        this.superficieAmministrativa = superficieAmministrativa;
    }


    /**
     * Gets the superficieAreaProtetta value for this AreaProtettaFiltriEstesi.
     *
     * @return superficieAreaProtetta
     */
    public double getSuperficieAreaProtetta() {
        return superficieAreaProtetta;
    }


    /**
     * Sets the superficieAreaProtetta value for this AreaProtettaFiltriEstesi.
     *
     * @param superficieAreaProtetta
     */
    public void setSuperficieAreaProtetta(double superficieAreaProtetta) {
        this.superficieAreaProtetta = superficieAreaProtetta;
    }


    /**
     * Gets the superficieIntersezione value for this AreaProtettaFiltriEstesi.
     *
     * @return superficieIntersezione
     */
    public double getSuperficieIntersezione() {
        return superficieIntersezione;
    }


    /**
     * Sets the superficieIntersezione value for this AreaProtettaFiltriEstesi.
     *
     * @param superficieIntersezione
     */
    public void setSuperficieIntersezione(double superficieIntersezione) {
        this.superficieIntersezione = superficieIntersezione;
    }


    /**
     * Gets the tipoAreaProtetta value for this AreaProtettaFiltriEstesi.
     *
     * @return tipoAreaProtetta
     */
    public String getTipoAreaProtetta() {
        return tipoAreaProtetta;
    }


    /**
     * Sets the tipoAreaProtetta value for this AreaProtettaFiltriEstesi.
     *
     * @param tipoAreaProtetta
     */
    public void setTipoAreaProtetta(String tipoAreaProtetta) {
        this.tipoAreaProtetta = tipoAreaProtetta;
    }


    /**
     * Gets the tipoEnte value for this AreaProtettaFiltriEstesi.
     *
     * @return tipoEnte
     */
    public String getTipoEnte() {
        return tipoEnte;
    }


    /**
     * Sets the tipoEnte value for this AreaProtettaFiltriEstesi.
     *
     * @param tipoEnte
     */
    public void setTipoEnte(String tipoEnte) {
        this.tipoEnte = tipoEnte;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof AreaProtettaFiltriEstesi)) return false;
        AreaProtettaFiltriEstesi other = (AreaProtettaFiltriEstesi) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.arrayIstatComune==null && other.getArrayIstatComune()==null) ||
             (this.arrayIstatComune!=null &&
              java.util.Arrays.equals(this.arrayIstatComune, other.getArrayIstatComune()))) &&
            ((this.codiceAmministrativo==null && other.getCodiceAmministrativo()==null) ||
             (this.codiceAmministrativo!=null &&
              this.codiceAmministrativo.equals(other.getCodiceAmministrativo()))) &&
            ((this.codiceEnteGestore==null && other.getCodiceEnteGestore()==null) ||
             (this.codiceEnteGestore!=null &&
              this.codiceEnteGestore.equals(other.getCodiceEnteGestore()))) &&
            ((this.codiceEuap==null && other.getCodiceEuap()==null) ||
             (this.codiceEuap!=null &&
              this.codiceEuap.equals(other.getCodiceEuap()))) &&
            ((this.codicePatrimonialita==null && other.getCodicePatrimonialita()==null) ||
             (this.codicePatrimonialita!=null &&
              this.codicePatrimonialita.equals(other.getCodicePatrimonialita()))) &&
            ((this.codiceTipoAreaProtetta==null && other.getCodiceTipoAreaProtetta()==null) ||
             (this.codiceTipoAreaProtetta!=null &&
              this.codiceTipoAreaProtetta.equals(other.getCodiceTipoAreaProtetta()))) &&
            ((this.codiceTipoEnte==null && other.getCodiceTipoEnte()==null) ||
             (this.codiceTipoEnte!=null &&
              this.codiceTipoEnte.equals(other.getCodiceTipoEnte()))) &&
            ((this.dataAggiornIstituzionale==null && other.getDataAggiornIstituzionale()==null) ||
             (this.dataAggiornIstituzionale!=null &&
              this.dataAggiornIstituzionale.equals(other.getDataAggiornIstituzionale()))) &&
            ((this.dataIstituzione==null && other.getDataIstituzione()==null) ||
             (this.dataIstituzione!=null &&
              this.dataIstituzione.equals(other.getDataIstituzione()))) &&
            ((this.denominazione==null && other.getDenominazione()==null) ||
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
            ((this.enteGestore==null && other.getEnteGestore()==null) ||
             (this.enteGestore!=null &&
              this.enteGestore.equals(other.getEnteGestore()))) &&
            this.idAreaProtetta == other.getIdAreaProtetta() &&
            ((this.idInspire==null && other.getIdInspire()==null) ||
             (this.idInspire!=null &&
              this.idInspire.equals(other.getIdInspire()))) &&
            ((this.leggeIstitutiva==null && other.getLeggeIstitutiva()==null) ||
             (this.leggeIstitutiva!=null &&
              this.leggeIstitutiva.equals(other.getLeggeIstitutiva()))) &&
            ((this.patrimonialita==null && other.getPatrimonialita()==null) ||
             (this.patrimonialita!=null &&
              this.patrimonialita.equals(other.getPatrimonialita()))) &&
            this.percentualeApOccupataDaGeometria == other.getPercentualeApOccupataDaGeometria() &&
            this.percentualeRicadenzaGeometriaInAp == other.getPercentualeRicadenzaGeometriaInAp() &&
            this.perimetroAmministrativo == other.getPerimetroAmministrativo() &&
            this.superficieAmministrativa == other.getSuperficieAmministrativa() &&
            this.superficieAreaProtetta == other.getSuperficieAreaProtetta() &&
            this.superficieIntersezione == other.getSuperficieIntersezione() &&
            ((this.tipoAreaProtetta==null && other.getTipoAreaProtetta()==null) ||
             (this.tipoAreaProtetta!=null &&
              this.tipoAreaProtetta.equals(other.getTipoAreaProtetta()))) &&
            ((this.tipoEnte==null && other.getTipoEnte()==null) ||
             (this.tipoEnte!=null &&
              this.tipoEnte.equals(other.getTipoEnte())));
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
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getArrayIstatComune());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getArrayIstatComune(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
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
        if (getCodiceEuap() != null) {
            _hashCode += getCodiceEuap().hashCode();
        }
        if (getCodicePatrimonialita() != null) {
            _hashCode += getCodicePatrimonialita().hashCode();
        }
        if (getCodiceTipoAreaProtetta() != null) {
            _hashCode += getCodiceTipoAreaProtetta().hashCode();
        }
        if (getCodiceTipoEnte() != null) {
            _hashCode += getCodiceTipoEnte().hashCode();
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
        _hashCode += getIdAreaProtetta();
        if (getIdInspire() != null) {
            _hashCode += getIdInspire().hashCode();
        }
        if (getLeggeIstitutiva() != null) {
            _hashCode += getLeggeIstitutiva().hashCode();
        }
        if (getPatrimonialita() != null) {
            _hashCode += getPatrimonialita().hashCode();
        }
        _hashCode += Double.valueOf(getPercentualeApOccupataDaGeometria()).hashCode();
        _hashCode += Double.valueOf(getPercentualeRicadenzaGeometriaInAp()).hashCode();
        _hashCode += Double.valueOf(getPerimetroAmministrativo()).hashCode();
        _hashCode += Double.valueOf(getSuperficieAmministrativa()).hashCode();
        _hashCode += Double.valueOf(getSuperficieAreaProtetta()).hashCode();
        _hashCode += Double.valueOf(getSuperficieIntersezione()).hashCode();
        if (getTipoAreaProtetta() != null) {
            _hashCode += getTipoAreaProtetta().hashCode();
        }
        if (getTipoEnte() != null) {
            _hashCode += getTipoEnte().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AreaProtettaFiltriEstesi.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("areeProtette", "AreaProtettaFiltriEstesi"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("arrayIstatComune");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "arrayIstatComune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("areeProtette", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceAmministrativo");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "codiceAmministrativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceEnteGestore");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "codiceEnteGestore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceEuap");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "codiceEuap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codicePatrimonialita");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "codicePatrimonialita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceTipoAreaProtetta");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "codiceTipoAreaProtetta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceTipoEnte");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "codiceTipoEnte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataAggiornIstituzionale");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "dataAggiornIstituzionale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataIstituzione");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "dataIstituzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enteGestore");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "enteGestore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAreaProtetta");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "idAreaProtetta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idInspire");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "idInspire"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("leggeIstitutiva");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "leggeIstitutiva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("patrimonialita");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "patrimonialita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentualeApOccupataDaGeometria");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "percentualeApOccupataDaGeometria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentualeRicadenzaGeometriaInAp");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "percentualeRicadenzaGeometriaInAp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("perimetroAmministrativo");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "perimetroAmministrativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieAmministrativa");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "superficieAmministrativa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieAreaProtetta");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "superficieAreaProtetta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieIntersezione");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "superficieIntersezione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoAreaProtetta");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "tipoAreaProtetta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoEnte");
        elemField.setXmlName(new javax.xml.namespace.QName("areeProtette", "tipoEnte"));
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