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
 * ParkapiZps_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.parkapi.zps;

import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException;

public interface ParkapiZps_PortType extends java.rmi.Remote {
    public Ricadenza[] determinaRicadenzaSuZpsPerGeometriaGML(String in0) throws java.rmi.RemoteException, UnrecoverableException, CSIException, SystemException, ZPSException;
    public Zps[] cercaTutteLeZPS() throws java.rmi.RemoteException, UnrecoverableException, CSIException, SystemException, ZPSException;
    public Zps[] cercaZpsPerFiltri(String in0, String in1) throws java.rmi.RemoteException, UnrecoverableException, CSIException, SystemException, ZPSException;
}