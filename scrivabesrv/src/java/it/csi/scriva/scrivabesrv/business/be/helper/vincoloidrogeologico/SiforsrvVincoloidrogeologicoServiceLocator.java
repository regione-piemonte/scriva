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
 * SiforsrvVincoloidrogeologicoServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.vincoloidrogeologico;

public class SiforsrvVincoloidrogeologicoServiceLocator extends org.apache.axis.client.Service implements SiforsrvVincoloidrogeologicoService {

    public SiforsrvVincoloidrogeologicoServiceLocator() {
    }


    public SiforsrvVincoloidrogeologicoServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SiforsrvVincoloidrogeologicoServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for siforsrvVincoloidrogeologico
    private String siforsrvVincoloidrogeologico_address = "http://tst-applogic.reteunitaria.piemonte.it/siforsrvApplVincoloidrogeologicoWsfad/services/siforsrvVincoloidrogeologico";

    public String getsiforsrvVincoloidrogeologicoAddress() {
        return siforsrvVincoloidrogeologico_address;
    }

    // The WSDD service name defaults to the port name.
    private String siforsrvVincoloidrogeologicoWSDDServiceName = "siforsrvVincoloidrogeologico";

    public String getsiforsrvVincoloidrogeologicoWSDDServiceName() {
        return siforsrvVincoloidrogeologicoWSDDServiceName;
    }

    public void setsiforsrvVincoloidrogeologicoWSDDServiceName(String name) {
        siforsrvVincoloidrogeologicoWSDDServiceName = name;
    }

    public SiforsrvVincoloidrogeologico_PortType getsiforsrvVincoloidrogeologico() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(siforsrvVincoloidrogeologico_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getsiforsrvVincoloidrogeologico(endpoint);
    }

    public SiforsrvVincoloidrogeologico_PortType getsiforsrvVincoloidrogeologico(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            SiforsrvVincoloidrogeologicoSoapBindingStub _stub = new SiforsrvVincoloidrogeologicoSoapBindingStub(portAddress, this);
            _stub.setPortName(getsiforsrvVincoloidrogeologicoWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setsiforsrvVincoloidrogeologicoEndpointAddress(String address) {
        siforsrvVincoloidrogeologico_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (SiforsrvVincoloidrogeologico_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                SiforsrvVincoloidrogeologicoSoapBindingStub _stub = new SiforsrvVincoloidrogeologicoSoapBindingStub(new java.net.URL(siforsrvVincoloidrogeologico_address), this);
                _stub.setPortName(getsiforsrvVincoloidrogeologicoWSDDServiceName());
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
        if ("siforsrvVincoloidrogeologico".equals(inputPortName)) {
            return getsiforsrvVincoloidrogeologico();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("vincoloIdrogeologico", "siforsrvVincoloidrogeologicoService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("vincoloIdrogeologico", "siforsrvVincoloidrogeologico"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {

if ("siforsrvVincoloidrogeologico".equals(portName)) {
            setsiforsrvVincoloidrogeologicoEndpointAddress(address);
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