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
 * AnagambsrvAnagambsrvService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.util.service.integration.anagamb;

/**
 * The interface Anagambsrv anagambsrv service.
 */
public interface AnagambsrvAnagambsrvService extends javax.xml.rpc.Service {

    /**
     * Gets anagambsrv address.
     *
     * @return the anagambsrv address
     */
    public String getanagambsrvAnagambsrvAddress();

    /**
     * Gets anagambsrv.
     *
     * @return the anagambsrv
     * @throws ServiceException the service exception
     */
    public AnagambsrvAnagambsrv getanagambsrvAnagambsrv() throws javax.xml.rpc.ServiceException;

    /**
     * Gets anagambsrv.
     *
     * @param portAddress the port address
     * @return the anagambsrv
     * @throws ServiceException the service exception
     */
    public AnagambsrvAnagambsrv getanagambsrvAnagambsrv(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}