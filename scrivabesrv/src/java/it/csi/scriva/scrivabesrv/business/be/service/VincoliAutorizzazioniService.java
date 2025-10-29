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

import it.csi.scriva.scrivabesrv.dto.VincoloAutorizzaExtendedDTO;

import java.util.List;

/**
 * The interface Vincoli  service.
 *
 * @author CSI PIEMONTE
 */
public interface VincoliAutorizzazioniService {

    /**
     * Load vincoli autorizzazioni list.
     *
     * @return the list
     */
    List<VincoloAutorizzaExtendedDTO> loadVincoliAutorizzazioni();

    /**
     * Load Vincoli autorizzazione by adempimento list.
     *
     * @param idAdempimento the id adempimento
     * @return the list
     */
    List<VincoloAutorizzaExtendedDTO> loadVincoloAutorizzazioneByAdempimento(Long idAdempimento);

    /**
     * Load Vincoli idrogeologici by id_oggetto_istanza list.
     *
     * @param id_oggetto_istanza the id_oggetto_istanza
     * @return the list
     */
    List<VincoloAutorizzaExtendedDTO> loadVincoloIdrogeologico(Long id_oggetto_istanza);


}