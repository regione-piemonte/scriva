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
 * AnagambsrvAnagambsrvServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.util.service.integration.anagamb;

/**
 * The type Anagambsrv anagambsrv service locator.
 */
public class AnagambsrvAnagambsrvServiceLocator extends org.apache.axis.client.Service implements AnagambsrvAnagambsrvService {

    /**
     * Instantiates a new Anagambsrv anagambsrv service locator.
     */
    public AnagambsrvAnagambsrvServiceLocator() {
    }


    /**
     * Instantiates a new Anagambsrv anagambsrv service locator.
     *
     * @param config the config
     */
    public AnagambsrvAnagambsrvServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    /**
     * Instantiates a new Anagambsrv anagambsrv service locator.
     *
     * @param wsdlLoc the wsdl loc
     * @param sName   the s name
     * @throws ServiceException the service exception
     */
    public AnagambsrvAnagambsrvServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for anagambsrvAnagambsrv
    private java.lang.String anagambsrvAnagambsrv_address = "http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv";

    public java.lang.String getanagambsrvAnagambsrvAddress() {
        return anagambsrvAnagambsrv_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String anagambsrvAnagambsrvWSDDServiceName = "anagambsrvAnagambsrv";

    /**
     * Gets anagambsrv wsdd service name.
     *
     * @return the anagambsrv wsdd service name
     */
    public java.lang.String getanagambsrvAnagambsrvWSDDServiceName() {
        return anagambsrvAnagambsrvWSDDServiceName;
    }

    /**
     * Sets anagambsrv wsdd service name.
     *
     * @param name the name
     */
    public void setanagambsrvAnagambsrvWSDDServiceName(java.lang.String name) {
        anagambsrvAnagambsrvWSDDServiceName = name;
    }

    public AnagambsrvAnagambsrv getanagambsrvAnagambsrv() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(anagambsrvAnagambsrv_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getanagambsrvAnagambsrv(endpoint);
    }

    public AnagambsrvAnagambsrv getanagambsrvAnagambsrv(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            AnagambsrvAnagambsrvSoapBindingStub _stub = new AnagambsrvAnagambsrvSoapBindingStub(portAddress, this);
            _stub.setPortName(getanagambsrvAnagambsrvWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * Sets anagambsrv endpoint address.
     *
     * @param address the address
     */
    public void setanagambsrvAnagambsrvEndpointAddress(java.lang.String address) {
        anagambsrvAnagambsrv_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (AnagambsrvAnagambsrv.class.isAssignableFrom(serviceEndpointInterface)) {
                AnagambsrvAnagambsrvSoapBindingStub _stub = new AnagambsrvAnagambsrvSoapBindingStub(new java.net.URL(anagambsrvAnagambsrv_address), this);
                _stub.setPortName(getanagambsrvAnagambsrvWSDDServiceName());
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
        if ("anagambsrvAnagambsrv".equals(inputPortName)) {
            return getanagambsrvAnagambsrv();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "anagambsrvAnagambsrvService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tst-serviziweb.csi.it/anagambsrvApplAnagambsrvWsfad/services/anagambsrvAnagambsrv", "anagambsrvAnagambsrv"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     *
     * @param portName the port name
     * @param address  the address
     * @throws ServiceException the service exception
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

if ("anagambsrvAnagambsrv".equals(portName)) {
            setanagambsrvAnagambsrvEndpointAddress(address);
        }
        else
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     *
     * @param portName the port name
     * @param address  the address
     * @throws ServiceException the service exception
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}