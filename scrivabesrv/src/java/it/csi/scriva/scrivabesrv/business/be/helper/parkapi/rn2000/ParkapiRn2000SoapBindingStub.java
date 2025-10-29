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
 * ParkapiRn2000SoapBindingStub.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.parkapi.rn2000;


import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UserException;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.FaultDesc;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;

import java.util.Vector;

public class ParkapiRn2000SoapBindingStub extends Stub implements ParkapiRn2000_PortType {
    private Vector cachedSerClasses = new Vector();
    private Vector cachedSerQNames = new Vector();
    private Vector cachedSerFactories = new Vector();
    private Vector cachedDeserFactories = new Vector();

    static OperationDesc[] _operations;

    static {
        _operations = new OperationDesc[1];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1() {
        OperationDesc oper;
        ParameterDesc param;
        oper = new OperationDesc();
        oper.setName("CercaRN2000conFiltriEstesi");
        param = new ParameterDesc(new javax.xml.namespace.QName("rn2000", "in0"), ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new javax.xml.namespace.QName("rn2000", "in1"), ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new javax.xml.namespace.QName("rn2000", "in2"), ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new javax.xml.namespace.QName("rn2000", "in3"), ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new javax.xml.namespace.QName("rn2000", "in4"), ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new javax.xml.namespace.QName("rn2000", "in5"), ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new javax.xml.namespace.QName("rn2000", "in6"), ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new javax.xml.namespace.QName("rn2000", "in7"), ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("rn2000", "Rn2000"));
        oper.setReturnClass(Rn2000[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("rn2000", "CercaRN2000conFiltriEstesiReturn"));
        oper.setStyle(Style.WRAPPED);
        oper.setUse(Use.LITERAL);
        oper.addFault(new FaultDesc(
                new javax.xml.namespace.QName("rn2000", "fault2"),
                "UnrecoverableException",
                new javax.xml.namespace.QName("rn2000", "UnrecoverableException"),
                true
        ));
        oper.addFault(new FaultDesc(
                new javax.xml.namespace.QName("rn2000", "fault"),
                "CSIException",
                new javax.xml.namespace.QName("rn2000", "CSIException"),
                true
        ));
        oper.addFault(new FaultDesc(
                new javax.xml.namespace.QName("rn2000", "fault1"),
                "SystemException",
                new javax.xml.namespace.QName("rn2000", "SystemException"),
                true
        ));
        oper.addFault(new FaultDesc(
                new javax.xml.namespace.QName("rn2000", "fault3"),
                "RN2000Exception",
                new javax.xml.namespace.QName("rn2000", "RN2000Exception"),
                true
        ));
        _operations[0] = oper;

    }

    public ParkapiRn2000SoapBindingStub() throws org.apache.axis.AxisFault {
        this(null);
    }

    public ParkapiRn2000SoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        this(service);
        super.cachedEndpoint = endpointURL;
    }

    public ParkapiRn2000SoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new Service();
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
        qName = new javax.xml.namespace.QName("rn2000", "ArrayOf_xsd_string");
        cachedSerQNames.add(qName);
        cls = String[].class;
        cachedSerClasses.add(cls);
        qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
        qName2 = new javax.xml.namespace.QName("rn2000", "item");
        cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
        cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

        qName = new javax.xml.namespace.QName("rn2000", "CSIException");
        cachedSerQNames.add(qName);
        cls = CSIException.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("rn2000", "Rn2000");
        cachedSerQNames.add(qName);
        cls = Rn2000.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("rn2000", "RN2000Exception");
        cachedSerQNames.add(qName);
        cls = RN2000Exception.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("rn2000", "SystemException");
        cachedSerQNames.add(qName);
        cls = SystemException.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("rn2000", "UnrecoverableException");
        cachedSerQNames.add(qName);
        cls = UnrecoverableException.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("rn2000", "UserException");
        cachedSerQNames.add(qName);
        cls = UserException.class;
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

    public Rn2000[] cercaRN2000ConFiltriEstesi(String in0, String in1, String in2, String in3, String in4, String in5, String in6, String in7) throws java.rmi.RemoteException, UnrecoverableException, CSIException, SystemException, RN2000Exception {
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
        _call.setOperationName(new javax.xml.namespace.QName("rn2000", "CercaRN2000conFiltriEstesi"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            Object _resp = _call.invoke(new Object[]{in0, in1, in2, in3, in4, in5, in6, in7});
           
           // System.out.println("SOAP Response: " + _call.getResponseMessage().getSOAPPartAsString());

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (Rn2000[]) _resp;
                } catch (Exception _exception) {
                    return (Rn2000[]) org.apache.axis.utils.JavaUtils.convert(_resp, Rn2000[].class);
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
                if (axisFaultException.detail instanceof SystemException) {
                    throw (SystemException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof RN2000Exception) {
                    throw (RN2000Exception) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

}