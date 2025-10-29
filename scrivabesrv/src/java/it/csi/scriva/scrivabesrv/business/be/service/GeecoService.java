/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service;

import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.GeecoUrlDTO;
import it.csi.scriva.scrivabesrv.dto.GeecoUrlReqDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaGeecoDTO;

/**
 * The interface Geeco service.
 *
 * @author CSI PIEMONTE
 */
public interface GeecoService {


    /**
     * Gets geeco url.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param oggetto          the oggetto
     * @param attoreScriva     the attore scriva
     * @return the geeco url
     */
    String getGeecoUrl(Long idOggettoIstanza, OggettoIstanzaGeecoDTO oggetto, AttoreScriva attoreScriva);

    /**
     * Gets geeco url.
     *
     * @param geecoUrlReq  the geeco url req
     * @param attoreScriva the attore scriva
     * @return the list
     */
    GeecoUrlDTO getUrl(GeecoUrlReqDTO geecoUrlReq, AttoreScriva attoreScriva);

    /**
     * Validate error dto.
     *
     * @param geecoUrlReq the geeco url req
     * @return the error dto
     */
    ErrorDTO validate(GeecoUrlReqDTO geecoUrlReq);

}