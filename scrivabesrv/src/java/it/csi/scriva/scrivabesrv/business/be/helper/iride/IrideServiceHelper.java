/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.iride;


import it.csi.scriva.scrivabesrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.iride.dto.Application;
import it.csi.scriva.scrivabesrv.business.be.helper.iride.dto.Identita;
import it.csi.scriva.scrivabesrv.business.be.helper.iride.dto.PolicyEnforcerBaseService;
import it.csi.scriva.scrivabesrv.business.be.helper.iride.dto.PolicyEnforcerBaseServiceServiceLocator;
import it.csi.scriva.scrivabesrv.business.be.helper.iride.dto.Ruolo;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

/**
 * The type Iride service helper.
 *
 * @author CSI PIEMONTE
 */
public class IrideServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    private final String serviceEndPoint;

    private PolicyEnforcerBaseServiceServiceLocator policyEnforcerBaseServiceServiceLocator;

    private PolicyEnforcerBaseService policyEnforcerBaseService;

    /**
     * Instantiates a new Iride service helper.
     *
     * @param serviceEndPoint the service end point
     */
    public IrideServiceHelper(String serviceEndPoint) {
        super();
        this.serviceEndPoint = serviceEndPoint;
    }

    /**
     * Gets policy enforcer base service service locator.
     *
     * @return the policy enforcer base service service locator
     */
    private PolicyEnforcerBaseServiceServiceLocator getPolicyEnforcerBaseServiceServiceLocator() {
        if (this.policyEnforcerBaseServiceServiceLocator == null) {
            this.policyEnforcerBaseServiceServiceLocator = new PolicyEnforcerBaseServiceServiceLocator();
            //this.policyEnforcerBaseServiceServiceLocator.setPolicyEnforcerBaseEndpointAddress(this.serviceEndPoint);
        }
        return this.policyEnforcerBaseServiceServiceLocator;
    }

    /**
     * Gets policy enforcer base service.
     *
     * @return the policy enforcer base service
     * @throws ServiceException      the service exception
     * @throws MalformedURLException the malformed url exception
     */
    private PolicyEnforcerBaseService getPolicyEnforcerBaseService() throws ServiceException, MalformedURLException {
        if (this.policyEnforcerBaseService == null) {
            this.policyEnforcerBaseService = this.getPolicyEnforcerBaseServiceServiceLocator().getPolicyEnforcerBase(new URL(this.serviceEndPoint));
        }
        return policyEnforcerBaseService;
    }

    /**
     * Is identita autentica.
     *
     * @param identita the identita
     * @return the boolean
     */
    public boolean isIdentitaAutentica(Identita identita) {
        logBegin(className);
        try {
            logDebug(className, "IDENTITA : \n" + identita);
            boolean isIdentitaAutentica = this.getPolicyEnforcerBaseService().isIdentitaAutentica(identita);
            logDebug(className, "isIdentitaAutentica : " + isIdentitaAutentica);
            return isIdentitaAutentica;
        } catch (RemoteException | ServiceException | MalformedURLException e) {
            logError(className, e);
        }
        return Boolean.FALSE;
    }

    /**
     * Get ruoli ruolo [ ].
     *
     * @param identita the identita
     * @param cod      the cod
     * @return the ruolo [ ]
     */
    public Ruolo[] getRuoli(Identita identita, Application cod) {
        LOGGER.debug("[IrideServiceHelper::getRuoli] BEGIN");
        Ruolo[] ruoli = null;
        try {
            ruoli = this.getPolicyEnforcerBaseService().findRuoliForPersonaInApplication(identita, cod);
            if (ruoli != null && ruoli.length > 0) {
                logDebug(className, "Ruoli trovati: " + ruoli.length);
                logDebug(className, "Ruolo: " + ruoli[0].getCodiceRuolo());
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return ruoli;
    }


}