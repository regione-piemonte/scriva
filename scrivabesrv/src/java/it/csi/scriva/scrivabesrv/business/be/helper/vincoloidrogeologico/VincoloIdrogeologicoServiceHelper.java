/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.vincoloidrogeologico;

import it.csi.scriva.scrivabesrv.business.be.helper.AbstractServiceHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Vincolo idrogeologico service helper.
 */
public class VincoloIdrogeologicoServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    private final SiforsrvVincoloidrogeologico_PortType service;

    /**
     * The Url service.
     */
    protected String urlService = "";

    /**
     * Instantiates a new Vincolo idrogeologico service helper.
     *
     * @param urlService the url service
     */
    public VincoloIdrogeologicoServiceHelper(String urlService) {
        this.urlService = urlService;
        this.service = this.getService(urlService);
    }

    /**
     * Gets service.
     *
     * @param urlService the url service
     * @return the service
     */
    private SiforsrvVincoloidrogeologico_PortType getService(String urlService) {
        logBeginInfo(className, urlService);
        SiforsrvVincoloidrogeologico_PortType siforsrvVincoloidrogeologico = null;
        try {
            SiforsrvVincoloidrogeologicoServiceLocator serviceLocator = new SiforsrvVincoloidrogeologicoServiceLocator();
            serviceLocator.setsiforsrvVincoloidrogeologicoEndpointAddress(urlService);
            siforsrvVincoloidrogeologico = serviceLocator.getsiforsrvVincoloidrogeologico();
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return siforsrvVincoloidrogeologico;
    }

    /**
     * Determina ricadenza su vincolo idrogeologico per geometria gml ricadenza [ ].
     *
     * @param gmlGeometry Geometria in formato GML
     * @return the ricadenza [ ]
     */
    public Ricadenza[] determinaRicadenzaSuVincoloIdrogeologicoPerGeometriaGML(String gmlGeometry) {
        logBeginInfo(className, gmlGeometry);
        Ricadenza[] ricadenze = null;
        try {
            ricadenze = this.service.determinaRicadenzaSuVincoloIdrogeologicoPerGeometriaGML(gmlGeometry);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return ricadenze;
    }

    /**
     * Is ricadenza significativa boolean.
     *
     * @param gmlGeometry the gml geometry escaped
     * @return the boolean
     */
    public Boolean isRicadenzaSignificativaByGMLGeometry(String gmlGeometry) {
        logBeginInfo(className, gmlGeometry);
        List<Ricadenza> ricadenzeSignificative = new ArrayList<>();
        try {
            Ricadenza[] ricadenze = this.determinaRicadenzaSuVincoloIdrogeologicoPerGeometriaGML(gmlGeometry);
            if (ricadenze != null && ricadenze.length > 0) {
                ricadenzeSignificative = Arrays.stream(ricadenze)
                        .filter(Ricadenza::isFlagRicadenzaSignificativa)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return !ricadenzeSignificative.isEmpty();
    }

}