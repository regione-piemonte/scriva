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
 * ParkapiAreeprotette_PortType.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.parkapi.areeprotette;

import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.CSIException;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.SystemException;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.common.UnrecoverableException;

import java.rmi.RemoteException;

public interface ParkapiAreeprotette_PortType extends java.rmi.Remote {
    public Ricadenza[] determinaRicadenzaSuAreeProtettePerGeometriaGML(String in0) throws RemoteException, UnrecoverableException, CSIException, SystemException, AreeProtetteException;

    public AreaProtetta[] cercaTutteLeAreeProtette() throws RemoteException, UnrecoverableException, CSIException, SystemException, AreeProtetteException;

    public AreaProtetta[] cercaAreeProtettePerFiltri(String in0, String in1) throws RemoteException, UnrecoverableException, CSIException, SystemException, AreeProtetteException;

    public AreaProtettaFiltriEstesi[] cercaAreeProtetteConFiltriEstesi(String in0, String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8) throws RemoteException, UnrecoverableException, CSIException, SystemException, AreeProtetteException;
}