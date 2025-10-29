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
 * ParkapiSicService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.parkapi.sic;

public interface ParkapiSicService extends javax.xml.rpc.Service {
    public String getparkapiSicAddress();

    public ParkapiSic_PortType getparkapiSic() throws javax.xml.rpc.ServiceException;

    public ParkapiSic_PortType getparkapiSic(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}