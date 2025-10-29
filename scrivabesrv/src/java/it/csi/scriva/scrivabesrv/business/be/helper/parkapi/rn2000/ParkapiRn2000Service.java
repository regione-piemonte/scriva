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
 * ParkapiRn2000Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.parkapi.rn2000;

import java.net.URL;

public interface ParkapiRn2000Service extends javax.xml.rpc.Service {
    public String getparkapiRn2000Address();

    public ParkapiRn2000_PortType getparkapiRn2000() throws javax.xml.rpc.ServiceException;

    public ParkapiRn2000_PortType getparkapiRn2000(URL portAddress) throws javax.xml.rpc.ServiceException;
}