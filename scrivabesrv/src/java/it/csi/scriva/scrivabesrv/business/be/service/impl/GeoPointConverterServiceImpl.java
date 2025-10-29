/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service.impl;

import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoPointConverterDAO;
import it.csi.scriva.scrivabesrv.business.be.service.GeoPointConverterService;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.GeoPointConverterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * The Tipologie oggetto service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class GeoPointConverterServiceImpl extends BaseApiServiceImpl implements GeoPointConverterService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private GeoPointConverterDAO geoPointConverterDAO;

    /**
     * Convert epsg point
     *
     * @return the point list
     */
    @Override
    public List<GeoPointConverterDTO> convert(Integer sourceEPSG, Integer targetEPSG, BigDecimal lat, BigDecimal lon) {
        logBegin(className);
        GeoPointConverterDTO source = new GeoPointConverterDTO();
        source.setSourceLAT(lat);
        source.setSourceLON(lon);
        if (null != sourceEPSG) {
            source.setSourceEPSG(sourceEPSG);
        }
        if (null != targetEPSG) {
            source.setTargetEPSG(targetEPSG);
        }
        List<GeoPointConverterDTO> pointConvertedList = null;
        try {
            pointConvertedList = geoPointConverterDAO.convert(source);
            //set source values
            pointConvertedList.get(0).setSourceLAT(source.getSourceLAT());
            pointConvertedList.get(0).setSourceLON(source.getSourceLON());
            pointConvertedList.get(0).setSourceEPSG(source.getSourceEPSG());
            pointConvertedList.get(0).setTargetEPSG(source.getTargetEPSG());
        } catch (Exception e) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, error);
        } finally {
            logEnd(className);
        }
        return pointConvertedList;
    }


}