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

import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoExtendedDTO;

import java.util.List;

/**
 * The interface Tipologie Oggetto service.
 *
 * @author CSI PIEMONTE
 */
public interface TipologieOggettoService {

    /**
     * Load Tipologie Oggetto list.
     *
     * @return the list
     */
    List<TipologiaOggettoExtendedDTO> loadAll();

    /**
     * Load Tipologie Oggetto by idTipologiaOggetto list.
     *
     * @param idTipologiaOggetto the id
     * @return the tipologia oggetto by id
     */
    List<TipologiaOggettoExtendedDTO> getTipologiaOggetto(Long idTipologiaOggetto);

    /**
     * Gets tipo evento by code.
     *
     * @param codTipologiaOggetto the cod tipo evento
     * @return the tipologia oggetto by code
     */
    List<TipologiaOggettoExtendedDTO> getTipologiaOggettoByCode(String codTipologiaOggetto);

    /**
     * Gets tipo evento by stato istanza.
     *
     * @param codAdempimento the adempimento code
     * @return the list by code
     */
    List<TipologiaOggettoExtendedDTO> loadTipologiaOggettoByCodeAdempimento(String codAdempimento);

    /**
     * Load tipologia oggetto by id layers list.
     *
     * @param idLayerList the id layer list
     * @return the list
     */
    List<TipologiaOggettoDTO> loadTipologiaOggettoByIdLayers(List<Long> idLayerList);

}