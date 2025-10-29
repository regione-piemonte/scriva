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
 * AnagambsrvAnagambsrvSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.util.service.integration.anagamb;

/**
 * The type Anagambsrv anagambsrv soap binding stub.
 */
public class AnagambsrvAnagambsrvSoapBindingStub extends org.apache.axis.client.Stub implements AnagambsrvAnagambsrv {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    /**
     * The Operations.
     */
    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[20];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("testResources");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "testResourcesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("selfCheck");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns2_CalledResource"), CalledResource[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "InvocationNode"));
        oper.setReturnClass(InvocationNode.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "selfCheckReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("hasSelfCheck");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "hasSelfCheckReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getSediLegali");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_soapenc_string"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_soapenc_string"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in3"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_soapenc_string"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in4"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns1_SedeLegale"));
        oper.setReturnClass(SedeLegale[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getSediLegaliReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getSedeLegale");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "DettaglioSedeLegale"));
        oper.setReturnClass(DettaglioSedeLegale.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getSedeLegaleReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getSediOperative");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in3"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in4"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in5"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in6"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in7"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in8"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns1_SedeOperativa"));
        oper.setReturnClass(SedeOperativa[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getSediOperativeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getSedeOperativa");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "DettaglioSedeOperativa"));
        oper.setReturnClass(DettaglioSedeOperativa.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getSedeOperativaReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("saveSedeLegale");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "DettaglioSedeLegale"), DettaglioSedeLegale.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "saveSedeLegaleReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("saveSedeOperativa");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "DettaglioSedeOperativa"), DettaglioSedeOperativa.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "saveSedeOperativaReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("listaFasceAddetti");
        oper.setReturnType(new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns1_FasceAddetti"));
        oper.setReturnClass(FasceAddetti[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "listaFasceAddettiReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("listaCertificazioniAmbientali");
        oper.setReturnType(new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns1_CodiceDescrizione"));
        oper.setReturnClass(CodiceDescrizione[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "listaCertificazioniAmbientaliReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("listaClassificazioniAcustiche");
        oper.setReturnType(new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns1_CodiceDescrizione"));
        oper.setReturnClass(CodiceDescrizione[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "listaClassificazioniAcusticheReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("listaAutorizzazioniAmbientali");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns1_CodiceDescrizione"));
        oper.setReturnClass(CodiceDescrizione[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "listaAutorizzazioniAmbientaliReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("listaAmbitiTematici");
        oper.setReturnType(new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns1_CodiceDescrizione"));
        oper.setReturnClass(CodiceDescrizione[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "listaAmbitiTematiciReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("listaAttivitaAteco91");
        oper.setReturnType(new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns1_CodiceDescrizione"));
        oper.setReturnClass(CodiceDescrizione[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "listaAttivitaAteco91Return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDettaglioSedeLegale");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "DettaglioSedeLegale"));
        oper.setReturnClass(DettaglioSedeLegale.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getDettaglioSedeLegaleReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("volturaSedeOperativa");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean"));
        oper.setReturnClass(java.lang.Boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "volturaSedeOperativaReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("updateCoordService");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "updateCoordServiceReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("saveDettaglioSedeOperativa");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "DettaglioSedeOperativa"), DettaglioSedeOperativa.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SedeOperativa"));
        oper.setReturnClass(SedeOperativa.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "saveDettaglioSedeOperativaReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getSediLegaliByCodiceFiscale");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_soapenc_string"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_soapenc_string"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in3"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_soapenc_string"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in4"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns1_SedeLegale"));
        oper.setReturnClass(SedeLegale[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getSediLegaliByCodiceFiscaleReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "UnrecoverableException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "CSIException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "AnagambsrvException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "fault"),
                      "SystemException",
                      new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException"),
                      true
                     ));
        _operations[19] = oper;

    }

    /**
     * Instantiates a new Anagambsrv anagambsrv soap binding stub.
     *
     * @throws AxisFault the axis fault
     */
    public AnagambsrvAnagambsrvSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    /**
     * Instantiates a new Anagambsrv anagambsrv soap binding stub.
     *
     * @param endpointURL the endpoint url
     * @param service     the service
     * @throws AxisFault the axis fault
     */
    public AnagambsrvAnagambsrvSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    /**
     * Instantiates a new Anagambsrv anagambsrv soap binding stub.
     *
     * @param service the service
     * @throws AxisFault the axis fault
     */
    public AnagambsrvAnagambsrvSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "CalledResource");
            cachedSerQNames.add(qName);
            cls = CalledResource.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "InvocationNode");
            cachedSerQNames.add(qName);
            cls = InvocationNode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "Outcome");
            cachedSerQNames.add(qName);
            cls = Outcome.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "ResourceType");
            cachedSerQNames.add(qName);
            cls = ResourceType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_soapenc_string");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns1_AttivitaEconomica");
            cachedSerQNames.add(qName);
            cls = AttivitaEconomica[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AttivitaEconomica");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns1_CodiceDescrizione");
            cachedSerQNames.add(qName);
            cls = CodiceDescrizione[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CodiceDescrizione");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns1_FasceAddetti");
            cachedSerQNames.add(qName);
            cls = FasceAddetti[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "FasceAddetti");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns1_SedeLegale");
            cachedSerQNames.add(qName);
            cls = SedeLegale[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SedeLegale");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns1_SedeOperativa");
            cachedSerQNames.add(qName);
            cls = SedeOperativa[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SedeOperativa");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns1_TemaAutorizzativo");
            cachedSerQNames.add(qName);
            cls = TemaAutorizzativo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "TemaAutorizzativo");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns2_CalledResource");
            cachedSerQNames.add(qName);
            cls = CalledResource[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "CalledResource");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "ArrayOf_tns2_InvocationNode");
            cachedSerQNames.add(qName);
            cls = InvocationNode[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "InvocationNode");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AnagambsrvException");
            cachedSerQNames.add(qName);
            cls = AnagambsrvException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "AttivitaEconomica");
            cachedSerQNames.add(qName);
            cls = AttivitaEconomica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CodiceDescrizione");
            cachedSerQNames.add(qName);
            cls = CodiceDescrizione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CommunicationException");
            cachedSerQNames.add(qName);
            cls = CommunicationException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "CSIException");
            cachedSerQNames.add(qName);
            cls = CSIException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "DettaglioRappresentanteSedeLegale");
            cachedSerQNames.add(qName);
            cls = DettaglioRappresentanteSedeLegale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "DettaglioSedeLegale");
            cachedSerQNames.add(qName);
            cls = DettaglioSedeLegale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "DettaglioSedeOperativa");
            cachedSerQNames.add(qName);
            cls = DettaglioSedeOperativa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "FasceAddetti");
            cachedSerQNames.add(qName);
            cls = FasceAddetti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SedeLegale");
            cachedSerQNames.add(qName);
            cls = SedeLegale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SedeOperativa");
            cachedSerQNames.add(qName);
            cls = SedeOperativa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "SystemException");
            cachedSerQNames.add(qName);
            cls = SystemException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "TemaAutorizzativo");
            cachedSerQNames.add(qName);
            cls = TemaAutorizzativo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UnrecoverableException");
            cachedSerQNames.add(qName);
            cls = UnrecoverableException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:anagambsrvAnagambsrv", "UserException");
            cachedSerQNames.add(qName);
            cls = UserException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    /**
     * Create call org . apache . axis . client . call.
     *
     * @return the org . apache . axis . client . call
     * @throws RemoteException the remote exception
     */
    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public boolean testResources() throws java.rmi.RemoteException, CSIException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "testResources"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Boolean) _resp).booleanValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public InvocationNode selfCheck(CalledResource[] in0) throws java.rmi.RemoteException, CSIException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "selfCheck"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (InvocationNode) _resp;
            } catch (java.lang.Exception _exception) {
                return (InvocationNode) org.apache.axis.utils.JavaUtils.convert(_resp, InvocationNode.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public boolean hasSelfCheck() throws java.rmi.RemoteException, CSIException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "hasSelfCheck"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Boolean) _resp).booleanValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public SedeLegale[] getSediLegali(java.lang.String[] in0, java.lang.String[] in1, java.lang.String in2, java.lang.String[] in3, java.lang.String in4) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "getSediLegali"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0, in1, in2, in3, in4});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (SedeLegale[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (SedeLegale[]) org.apache.axis.utils.JavaUtils.convert(_resp, SedeLegale[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public DettaglioSedeLegale getSedeLegale(java.lang.String in0) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "getSedeLegale"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (DettaglioSedeLegale) _resp;
            } catch (java.lang.Exception _exception) {
                return (DettaglioSedeLegale) org.apache.axis.utils.JavaUtils.convert(_resp, DettaglioSedeLegale.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public SedeOperativa[] getSediOperative(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4, java.lang.String in5, java.lang.String in6, java.lang.String in7, java.lang.String in8) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "getSediOperative"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0, in1, in2, in3, in4, in5, in6, in7, in8});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (SedeOperativa[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (SedeOperativa[]) org.apache.axis.utils.JavaUtils.convert(_resp, SedeOperativa[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public DettaglioSedeOperativa getSedeOperativa(java.lang.String in0) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "getSedeOperativa"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (DettaglioSedeOperativa) _resp;
            } catch (java.lang.Exception _exception) {
                return (DettaglioSedeOperativa) org.apache.axis.utils.JavaUtils.convert(_resp, DettaglioSedeOperativa.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public java.lang.String saveSedeLegale(DettaglioSedeLegale in0) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "saveSedeLegale"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public java.lang.String saveSedeOperativa(DettaglioSedeOperativa in0) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "saveSedeOperativa"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public FasceAddetti[] listaFasceAddetti() throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "listaFasceAddetti"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (FasceAddetti[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (FasceAddetti[]) org.apache.axis.utils.JavaUtils.convert(_resp, FasceAddetti[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public CodiceDescrizione[] listaCertificazioniAmbientali() throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "listaCertificazioniAmbientali"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CodiceDescrizione[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (CodiceDescrizione[]) org.apache.axis.utils.JavaUtils.convert(_resp, CodiceDescrizione[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public CodiceDescrizione[] listaClassificazioniAcustiche() throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "listaClassificazioniAcustiche"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CodiceDescrizione[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (CodiceDescrizione[]) org.apache.axis.utils.JavaUtils.convert(_resp, CodiceDescrizione[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public CodiceDescrizione[] listaAutorizzazioniAmbientali(java.lang.String in0) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "listaAutorizzazioniAmbientali"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CodiceDescrizione[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (CodiceDescrizione[]) org.apache.axis.utils.JavaUtils.convert(_resp, CodiceDescrizione[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public CodiceDescrizione[] listaAmbitiTematici() throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "listaAmbitiTematici"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CodiceDescrizione[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (CodiceDescrizione[]) org.apache.axis.utils.JavaUtils.convert(_resp, CodiceDescrizione[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public CodiceDescrizione[] listaAttivitaAteco91() throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "listaAttivitaAteco91"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CodiceDescrizione[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (CodiceDescrizione[]) org.apache.axis.utils.JavaUtils.convert(_resp, CodiceDescrizione[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public DettaglioSedeLegale getDettaglioSedeLegale(java.lang.String in0) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "getDettaglioSedeLegale"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (DettaglioSedeLegale) _resp;
            } catch (java.lang.Exception _exception) {
                return (DettaglioSedeLegale) org.apache.axis.utils.JavaUtils.convert(_resp, DettaglioSedeLegale.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public java.lang.Boolean volturaSedeOperativa(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "volturaSedeOperativa"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0, in1, in2});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.Boolean) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.Boolean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public boolean updateCoordService(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "updateCoordService"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0, in1, in2});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Boolean) _resp).booleanValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public SedeOperativa saveDettaglioSedeOperativa(DettaglioSedeOperativa in0) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "saveDettaglioSedeOperativa"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (SedeOperativa) _resp;
            } catch (java.lang.Exception _exception) {
                return (SedeOperativa) org.apache.axis.utils.JavaUtils.convert(_resp, SedeOperativa.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public SedeLegale[] getSediLegaliByCodiceFiscale(java.lang.String[] in0, java.lang.String[] in1, java.lang.String in2, java.lang.String[] in3, java.lang.String in4) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://anagambsrv.interfacecsi.anagambsrv.anagamb.csi.it", "getSediLegaliByCodiceFiscale"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0, in1, in2, in3, in4});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (SedeLegale[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (SedeLegale[]) org.apache.axis.utils.JavaUtils.convert(_resp, SedeLegale[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof AnagambsrvException) {
              throw (AnagambsrvException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}