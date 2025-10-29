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
 * ParkapiAreeprotetteSoapBindingStub.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.parkapi.areeprotette;

public class ParkapiAreeprotetteSoapBindingStub extends org.apache.axis.client.Stub implements ParkapiAreeprotette_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc[] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[4];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1() {
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("determinaRicadenzaSuAreeProtettePerGeometriaGML");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("areeProtette", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("areeProtette", "Ricadenza"));
        oper.setReturnClass(Ricadenza[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("areeProtette", "determinaRicadenzaSuAreeProtettePerGeometriaGMLReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("areeProtette", "fault2"),
                "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException",
                new javax.xml.namespace.QName("areeProtette", "UnrecoverableException"),
                true
        ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("areeProtette", "fault"),
                "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException",
                new javax.xml.namespace.QName("areeProtette", "CSIException"),
                true
        ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("areeProtette", "fault1"),
                "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException",
                new javax.xml.namespace.QName("areeProtette", "SystemException"),
                true
        ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("areeProtette", "fault3"),
                "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.areeprotette.AreeProtetteException",
                new javax.xml.namespace.QName("areeProtette", "AreeProtetteException"),
                true
        ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("cercaTutteLeAreeProtette");
        oper.setReturnType(new javax.xml.namespace.QName("areeProtette", "AreaProtetta"));
        oper.setReturnClass(AreaProtetta[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("areeProtette", "cercaTutteLeAreeProtetteReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("areeProtette", "fault2"),
                "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException",
                new javax.xml.namespace.QName("areeProtette", "UnrecoverableException"),
                true
        ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("areeProtette", "fault"),
                "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException",
                new javax.xml.namespace.QName("areeProtette", "CSIException"),
                true
        ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("areeProtette", "fault1"),
                "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException",
                new javax.xml.namespace.QName("areeProtette", "SystemException"),
                true
        ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("areeProtette", "fault3"),
                "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.areeprotette.AreeProtetteException",
                new javax.xml.namespace.QName("areeProtette", "AreeProtetteException"),
                true
        ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("cercaAreeProtettePerFiltri");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("areeProtette", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("areeProtette", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("areeProtette", "AreaProtetta"));
        oper.setReturnClass(AreaProtetta[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("areeProtette", "cercaAreeProtettePerFiltriReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("areeProtette", "fault2"),
                "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException",
                new javax.xml.namespace.QName("areeProtette", "UnrecoverableException"),
                true
        ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("areeProtette", "fault"),
                "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException",
                new javax.xml.namespace.QName("areeProtette", "CSIException"),
                true
        ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("areeProtette", "fault1"),
                "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException",
                new javax.xml.namespace.QName("areeProtette", "SystemException"),
                true
        ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("areeProtette", "fault3"),
                "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.areeprotette.AreeProtetteException",
                new javax.xml.namespace.QName("areeProtette", "AreeProtetteException"),
                true
        ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("cercaAreeProtetteConFiltriEstesi");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("areeProtette", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("areeProtette", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("areeProtette", "in2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("areeProtette", "in3"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("areeProtette", "in4"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("areeProtette", "in5"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("areeProtette", "in6"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("areeProtette", "in7"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("areeProtette", "in8"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("areeProtette", "AreaProtettaFiltriEstesi"));
        oper.setReturnClass(AreaProtettaFiltriEstesi[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("areeProtette", "cercaAreeProtetteConFiltriEstesiReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("areeProtette", "fault2"),
                "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException",
                new javax.xml.namespace.QName("areeProtette", "UnrecoverableException"),
                true
        ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("areeProtette", "fault"),
                "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException",
                new javax.xml.namespace.QName("areeProtette", "CSIException"),
                true
        ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("areeProtette", "fault1"),
                "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException",
                new javax.xml.namespace.QName("areeProtette", "SystemException"),
                true
        ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("areeProtette", "fault3"),
                "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.areeprotette.AreeProtetteException",
                new javax.xml.namespace.QName("areeProtette", "AreeProtetteException"),
                true
        ));
        _operations[3] = oper;

    }

    public ParkapiAreeprotetteSoapBindingStub() throws org.apache.axis.AxisFault {
        this(null);
    }

    public ParkapiAreeprotetteSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        this(service);
        super.cachedEndpoint = endpointURL;
    }

    public ParkapiAreeprotetteSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.2");
        Class cls;
        javax.xml.namespace.QName qName;
        javax.xml.namespace.QName qName2;
        Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
        Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
        Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
        Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
        Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
        Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
        Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
        Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
        Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
        Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        qName = new javax.xml.namespace.QName("areeProtette", "AreaProtetta");
        cachedSerQNames.add(qName);
        cls = AreaProtetta.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("areeProtette", "AreaProtettaFiltriEstesi");
        cachedSerQNames.add(qName);
        cls = AreaProtettaFiltriEstesi.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("areeProtette", "AreeProtetteException");
        cachedSerQNames.add(qName);
        cls = AreeProtetteException.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("areeProtette", "ArrayOf_xsd_string");
        cachedSerQNames.add(qName);
        cls = String[].class;
        cachedSerClasses.add(cls);
        qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
        qName2 = new javax.xml.namespace.QName("areeProtette", "item");
        cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
        cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

        qName = new javax.xml.namespace.QName("areeProtette", "CSIException");
        cachedSerQNames.add(qName);
        cls = it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("areeProtette", "Ricadenza");
        cachedSerQNames.add(qName);
        cls = Ricadenza.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("areeProtette", "SystemException");
        cachedSerQNames.add(qName);
        cls = it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("areeProtette", "UnrecoverableException");
        cachedSerQNames.add(qName);
        cls = it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("areeProtette", "UserException");
        cachedSerQNames.add(qName);
        cls = it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UserException.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

    }

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
                String key = (String) keys.nextElement();
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
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        Class cls = (Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            Class sf = (Class)
                                    cachedSerFactories.get(i);
                            Class df = (Class)
                                    cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        } else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
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
        } catch (Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public Ricadenza[] determinaRicadenzaSuAreeProtettePerGeometriaGML(String in0) throws java.rmi.RemoteException, it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException, it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException, it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException, AreeProtetteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("areeProtette", "determinaRicadenzaSuAreeProtettePerGeometriaGML"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            Object _resp = _call.invoke(new Object[]{in0});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (Ricadenza[]) _resp;
                } catch (Exception _exception) {
                    return (Ricadenza[]) org.apache.axis.utils.JavaUtils.convert(_resp, Ricadenza[].class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException) {
                    throw (it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException) {
                    throw (it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException) {
                    throw (it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof AreeProtetteException) {
                    throw (AreeProtetteException) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

    public AreaProtetta[] cercaTutteLeAreeProtette() throws java.rmi.RemoteException, it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException, it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException, it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException, AreeProtetteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("areeProtette", "cercaTutteLeAreeProtette"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            Object _resp = _call.invoke(new Object[]{});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (AreaProtetta[]) _resp;
                } catch (Exception _exception) {
                    return (AreaProtetta[]) org.apache.axis.utils.JavaUtils.convert(_resp, AreaProtetta[].class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException) {
                    throw (it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException) {
                    throw (it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException) {
                    throw (it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof AreeProtetteException) {
                    throw (AreeProtetteException) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

    public AreaProtetta[] cercaAreeProtettePerFiltri(String in0, String in1) throws java.rmi.RemoteException, it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException, it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException, it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException, AreeProtetteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("areeProtette", "cercaAreeProtettePerFiltri"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            Object _resp = _call.invoke(new Object[]{in0, in1});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (AreaProtetta[]) _resp;
                } catch (Exception _exception) {
                    return (AreaProtetta[]) org.apache.axis.utils.JavaUtils.convert(_resp, AreaProtetta[].class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException) {
                    throw (it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException) {
                    throw (it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException) {
                    throw (it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof AreeProtetteException) {
                    throw (AreeProtetteException) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

    public AreaProtettaFiltriEstesi[] cercaAreeProtetteConFiltriEstesi(String in0, String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8) throws java.rmi.RemoteException, it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException, it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException, it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException, AreeProtetteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("areeProtette", "cercaAreeProtetteConFiltriEstesi"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            Object _resp = _call.invoke(new Object[]{in0, in1, in2, in3, in4, in5, in6, in7, in8});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (AreaProtettaFiltriEstesi[]) _resp;
                } catch (Exception _exception) {
                    return (AreaProtettaFiltriEstesi[]) org.apache.axis.utils.JavaUtils.convert(_resp, AreaProtettaFiltriEstesi[].class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException) {
                    throw (it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException) {
                    throw (it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException) {
                    throw (it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof AreeProtetteException) {
                    throw (AreeProtetteException) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

}