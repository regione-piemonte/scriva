/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.manager;

import it.csi.scriva.scrivabesrv.business.be.helper.vincoloidrogeologico.VincoloIdrogeologicoServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.service.GeoService;
import it.csi.scriva.scrivabesrv.business.be.service.VincoliAutorizzazioniService;
import it.csi.scriva.scrivabesrv.business.be.service.impl.BaseServiceImpl;
import it.csi.scriva.scrivabesrv.dto.VincoloAutorizzaExtendedDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Vincoli manager.
 *
 * @author CSI PIEMONTE
 */
@Component
public class VincoliManager extends BaseServiceImpl {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private VincoloIdrogeologicoServiceHelper vincoloIdrogeologicoServiceHelper;

    /**
     * The Vincolo autorizzazioni service.
     */
    @Autowired
    VincoliAutorizzazioniService vincoliService;

    @Autowired
    GeoService geoService;


    public List<VincoloAutorizzaExtendedDTO> ricadenzaVincoloIdrogeologicoByIstanza(Long idOggettoIstanza) {
        logBeginInfo(className, idOggettoIstanza);
        // get geometry
        String gmlGeo = geoService.loadGMLGeometryByIdOggettoIstanza(idOggettoIstanza);
        try {
            Boolean isRicadenza = StringUtils.isNotBlank(gmlGeo) ? vincoloIdrogeologicoServiceHelper.isRicadenzaSignificativaByGMLGeometry(gmlGeo) : Boolean.FALSE;
            if (Boolean.TRUE.equals(isRicadenza)) {
               return vincoliService.loadVincoloIdrogeologico(idOggettoIstanza);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return new ArrayList<>();
    }

}