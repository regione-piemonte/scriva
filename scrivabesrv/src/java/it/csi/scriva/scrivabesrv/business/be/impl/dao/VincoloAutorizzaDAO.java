/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl.dao;

import it.csi.scriva.scrivabesrv.dto.VincoloAutorizzaExtendedDTO;

import java.util.List;

/**
 * The interface Vincolo autorizza dao.
 */
/*
 * @author CSI PIEMONTE
 */
public interface VincoloAutorizzaDAO {

    /**
     * Load vincoli autorizzazioni list.
     *
     * @return the list
     */
    List<VincoloAutorizzaExtendedDTO> loadVincoliAutorizzazioni();

    /**
     * Load vincolo autorizzazione by adempimento list.
     *
     * @param idAdempimento the id adempimento
     * @return the list
     */
    List<VincoloAutorizzaExtendedDTO> loadVincoloAutorizzazioneByAdempimento(Long idAdempimento);

    /**
     * Load vincolo idrogeologico by id_oggetto_istanza list.
     *
     * @param id_oggetto_istanza the id_oggetto_istanza
     * @return the list
     */
    List<VincoloAutorizzaExtendedDTO> loadVincoloIdrogeologico(Long id_oggetto_istanza);

}