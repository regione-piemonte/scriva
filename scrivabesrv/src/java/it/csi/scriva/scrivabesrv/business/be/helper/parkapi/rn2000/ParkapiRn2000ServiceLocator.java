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
 * ParkapiRn2000ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.parkapi.rn2000;

import org.apache.axis.client.Service;

public class ParkapiRn2000ServiceLocator extends Service implements ParkapiRn2000Service {

    public ParkapiRn2000ServiceLocator() {
    }


    public ParkapiRn2000ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ParkapiRn2000ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for parkapiRn2000
    private java.lang.String parkapiRn2000_address = "http://tst-applogic.reteunitaria.piemonte.it/parkapiApplRn2000Wsfad/services/parkapiRn2000";

    public java.lang.String getparkapiRn2000Address() {
        return parkapiRn2000_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String parkapiRn2000WSDDServiceName = "parkapiRn2000";

    public java.lang.String getparkapiRn2000WSDDServiceName() {
        return parkapiRn2000WSDDServiceName;
    }

    public void setparkapiRn2000WSDDServiceName(java.lang.String name) {
        parkapiRn2000WSDDServiceName = name;
    }

    public ParkapiRn2000_PortType getparkapiRn2000() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(parkapiRn2000_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getparkapiRn2000(endpoint);
    }

    public ParkapiRn2000_PortType getparkapiRn2000(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ParkapiRn2000SoapBindingStub _stub = new ParkapiRn2000SoapBindingStub(portAddress, this);
            _stub.setPortName(getparkapiRn2000WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setparkapiRn2000EndpointAddress(java.lang.String address) {
        parkapiRn2000_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ParkapiRn2000_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                ParkapiRn2000SoapBindingStub _stub = new ParkapiRn2000SoapBindingStub(new java.net.URL(parkapiRn2000_address), this);
                _stub.setPortName(getparkapiRn2000WSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("parkapiRn2000".equals(inputPortName)) {
            return getparkapiRn2000();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("rn2000", "parkapiRn2000Service");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("rn2000", "parkapiRn2000"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

if ("parkapiRn2000".equals(portName)) {
            setparkapiRn2000EndpointAddress(address);
        }
        else
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}