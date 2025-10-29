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
 * ParkapiZpsServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.parkapi.zps;

public class ParkapiZpsServiceLocator extends org.apache.axis.client.Service implements ParkapiZpsService {

    public ParkapiZpsServiceLocator() {
    }


    public ParkapiZpsServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ParkapiZpsServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for parkapiZps
    private String parkapiZps_address = "http://tst-applogic.reteunitaria.piemonte.it/parkapiApplZpsWsfad/services/parkapiZps";

    public String getparkapiZpsAddress() {
        return parkapiZps_address;
    }

    // The WSDD service name defaults to the port name.
    private String parkapiZpsWSDDServiceName = "parkapiZps";

    public String getparkapiZpsWSDDServiceName() {
        return parkapiZpsWSDDServiceName;
    }

    public void setparkapiZpsWSDDServiceName(String name) {
        parkapiZpsWSDDServiceName = name;
    }

    public ParkapiZps_PortType getparkapiZps() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(parkapiZps_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getparkapiZps(endpoint);
    }

    public ParkapiZps_PortType getparkapiZps(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ParkapiZpsSoapBindingStub _stub = new ParkapiZpsSoapBindingStub(portAddress, this);
            _stub.setPortName(getparkapiZpsWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setparkapiZpsEndpointAddress(String address) {
        parkapiZps_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ParkapiZps_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                ParkapiZpsSoapBindingStub _stub = new ParkapiZpsSoapBindingStub(new java.net.URL(parkapiZps_address), this);
                _stub.setPortName(getparkapiZpsWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
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
        String inputPortName = portName.getLocalPart();
        if ("parkapiZps".equals(inputPortName)) {
            return getparkapiZps();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("zps", "parkapiZpsService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("zps", "parkapiZps"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {

if ("parkapiZps".equals(portName)) {
            setparkapiZpsEndpointAddress(address);
        }
        else
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}