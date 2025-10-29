/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.anagamb;

import it.csi.scriva.scrivabesrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivabesrv.util.service.integration.anagamb.AnagambsrvAnagambsrv;
import it.csi.scriva.scrivabesrv.util.service.integration.anagamb.AnagambsrvAnagambsrvServiceLocator;
import it.csi.scriva.scrivabesrv.util.service.integration.anagamb.SedeOperativa;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Anagamb service helper.
 *
 * @author CSI PIEMONTE
 */
public class AnagambServiceHelper extends AbstractServiceHelper {

    private AnagambsrvAnagambsrv service;
    /**
     * The Url service.
     */
    protected String urlService = "";

    /**
     * Instantiates a new Anagamb service helper.
     *
     * @param urlService urlService
     */
    public AnagambServiceHelper(String urlService) {
        this.urlService = urlService;
        this.service = this.getService(urlService);
    }

    private AnagambsrvAnagambsrv getService(String urlService) {
        LOGGER.debug("[AnagambServiceHelper::getService] BEGIN");
        AnagambsrvAnagambsrv server  = null;
        try {
            AnagambsrvAnagambsrvServiceLocator locator = new AnagambsrvAnagambsrvServiceLocator();
            server = locator.getanagambsrvAnagambsrv(new URL(urlService));

            LOGGER.debug("[AnagambServiceHelper::getService] Service 'JavaServiceDesc' INITIALIZED");
            LOGGER.debug("[AnagambServiceHelper::getService] END");
        } catch (MalformedURLException | ServiceException e) {
            LOGGER.error("[AnagambServiceHelper::getService] ERROR : invalid url [" + urlService + "]");
        }
        return server;
    }

    /**
     * Gets sedi operative.
     *
     * @param codiceIstaComuneSO codiceIstaComuneSO
     * @param codiceFiscale      codiceFiscale
     * @return List<SedeOperativa> sedi operative
     */
    public List<SedeOperativa> getSediOperative(String codiceIstaComuneSO, String codiceFiscale) {
        LOGGER.debug("[AnagambServiceHelper::getSediOperative] BEGIN");
        List<SedeOperativa> list = new ArrayList<>();
        try {
            SedeOperativa[] so = this.service.getSediOperative(null, null, null, codiceFiscale, null, codiceIstaComuneSO, null, null, null);
             list =  Arrays.asList(so);
            return list;
        } catch (Exception e) {
            LOGGER.error("[AnagambServiceHelper::getSediOperative] ERROR ", e);
        }
        LOGGER.debug("[AnagambServiceHelper::getSediOperative] E");
        return list;
    }
}