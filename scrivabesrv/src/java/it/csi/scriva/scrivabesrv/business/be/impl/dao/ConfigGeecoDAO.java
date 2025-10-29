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

import it.csi.scriva.scrivabesrv.dto.ConfigGeecoDTO;

/**
 * The interface Config geeco dao.
 *
 * @author CSI PIEMONTE
 */
public interface ConfigGeecoDAO {

    /**
     * Gets config.
     *
     * @param idRuoloApplicativo idRuoloApplicativo
     * @param idAdempimento      idAdempimento
     * @return ConfigGeecoDTO config
     */
    ConfigGeecoDTO getConfig(Integer idRuoloApplicativo, Long idAdempimento);

    /**
     * Gets config.
     *
     * @param codGestioneAttore the cod gestione attore
     * @param idAdempimento     the id adempimento
     * @return the config
     */
    ConfigGeecoDTO getConfig(String codGestioneAttore, Long idAdempimento);

}