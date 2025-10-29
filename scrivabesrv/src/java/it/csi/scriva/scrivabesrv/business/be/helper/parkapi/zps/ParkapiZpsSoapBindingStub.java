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
 * ParkapiZpsSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.parkapi.zps;

import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UserException;

public class ParkapiZpsSoapBindingStub extends org.apache.axis.client.Stub implements ParkapiZps_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[3];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("determinaRicadenzaSuZpsPerGeometriaGML");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("zps", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("zps", "Ricadenza"));
        oper.setReturnClass(Ricadenza[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("zps", "determinaRicadenzaSuZpsPerGeometriaGMLReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("zps", "fault2"),
                      "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException",
                      new javax.xml.namespace.QName("zps", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("zps", "fault"),
                      "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException",
                      new javax.xml.namespace.QName("zps", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("zps", "fault1"),
                      "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException",
                      new javax.xml.namespace.QName("zps", "SystemException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("zps", "fault3"),
                      "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.zps.ZPSException",
                      new javax.xml.namespace.QName("zps", "ZPSException"),
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("cercaTutteLeZPS");
        oper.setReturnType(new javax.xml.namespace.QName("zps", "Zps"));
        oper.setReturnClass(Zps[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("zps", "cercaTutteLeZPSReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("zps", "fault2"),
                      "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException",
                      new javax.xml.namespace.QName("zps", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("zps", "fault"),
                      "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException",
                      new javax.xml.namespace.QName("zps", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("zps", "fault1"),
                      "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException",
                      new javax.xml.namespace.QName("zps", "SystemException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("zps", "fault3"),
                      "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.zps.ZPSException",
                      new javax.xml.namespace.QName("zps", "ZPSException"),
                      true
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("cercaZpsPerFiltri");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("zps", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("zps", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("zps", "Zps"));
        oper.setReturnClass(Zps[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("zps", "cercaZpsPerFiltriReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("zps", "fault2"),
                      "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException",
                      new javax.xml.namespace.QName("zps", "UnrecoverableException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("zps", "fault"),
                      "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException",
                      new javax.xml.namespace.QName("zps", "CSIException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("zps", "fault1"),
                      "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException",
                      new javax.xml.namespace.QName("zps", "SystemException"),
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("zps", "fault3"),
                      "it.csi.scriva.scrivabesrv.business.be.helper.parkapi.zps.ZPSException",
                      new javax.xml.namespace.QName("zps", "ZPSException"),
                      true
                     ));
        _operations[2] = oper;

    }

    public ParkapiZpsSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ParkapiZpsSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ParkapiZpsSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
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
            qName = new javax.xml.namespace.QName("zps", "ArrayOf_xsd_string");
            cachedSerQNames.add(qName);
            cls = String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("zps", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("zps", "CSIException");
            cachedSerQNames.add(qName);
            cls = CSIException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("zps", "Ricadenza");
            cachedSerQNames.add(qName);
            cls = Ricadenza.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("zps", "SystemException");
            cachedSerQNames.add(qName);
            cls = SystemException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("zps", "UnrecoverableException");
            cachedSerQNames.add(qName);
            cls = UnrecoverableException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("zps", "UserException");
            cachedSerQNames.add(qName);
            cls = UserException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("zps", "Zps");
            cachedSerQNames.add(qName);
            cls = Zps.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("zps", "ZPSException");
            cachedSerQNames.add(qName);
            cls = ZPSException.class;
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
        catch (Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public Ricadenza[] determinaRicadenzaSuZpsPerGeometriaGML(String in0) throws java.rmi.RemoteException, UnrecoverableException, CSIException, SystemException, ZPSException {
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
        _call.setOperationName(new javax.xml.namespace.QName("zps", "determinaRicadenzaSuZpsPerGeometriaGML"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        Object _resp = _call.invoke(new Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
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
        if (axisFaultException.detail instanceof UnrecoverableException) {
              throw (UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof CSIException) {
              throw (CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof SystemException) {
              throw (SystemException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof ZPSException) {
              throw (ZPSException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public Zps[] cercaTutteLeZPS() throws java.rmi.RemoteException, UnrecoverableException, CSIException, SystemException, ZPSException {
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
        _call.setOperationName(new javax.xml.namespace.QName("zps", "cercaTutteLeZPS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        Object _resp = _call.invoke(new Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (Zps[]) _resp;
            } catch (Exception _exception) {
                return (Zps[]) org.apache.axis.utils.JavaUtils.convert(_resp, Zps[].class);
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
        if (axisFaultException.detail instanceof ZPSException) {
              throw (ZPSException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public Zps[] cercaZpsPerFiltri(String in0, String in1) throws java.rmi.RemoteException, UnrecoverableException, CSIException, SystemException, ZPSException {
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
        _call.setOperationName(new javax.xml.namespace.QName("zps", "cercaZpsPerFiltri"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        Object _resp = _call.invoke(new Object[] {in0, in1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (Zps[]) _resp;
            } catch (Exception _exception) {
                return (Zps[]) org.apache.axis.utils.JavaUtils.convert(_resp, Zps[].class);
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
        if (axisFaultException.detail instanceof ZPSException) {
              throw (ZPSException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}