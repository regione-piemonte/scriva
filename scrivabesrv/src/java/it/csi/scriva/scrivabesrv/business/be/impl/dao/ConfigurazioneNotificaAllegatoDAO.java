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

import it.csi.scriva.scrivabesrv.dto.ConfigurazioneNotificaAllegatoDTO;

import java.util.List;

/**
 * The interface Configurazione notifica allegato dao.
 *
 * @author CSI PIEMONTE
 */
public interface ConfigurazioneNotificaAllegatoDAO {

    /**
     * Load configurazioni notifica allegato list.
     *
     * @return the list
     */
    List<ConfigurazioneNotificaAllegatoDTO> loadConfigurazioniNotificaAllegato();

    /**
     * Load configurazione notifica allegato list.
     *
     * @param idNotificaConfigurazione the id notifica configurazione
     * @return the list
     */
    List<ConfigurazioneNotificaAllegatoDTO> loadConfigurazioneNotificaAllegato(Long idNotificaConfigurazione);

    /**
     * Load configurazione notifica allegato list.
     *
     * @param idNotificaConfigurazioneList the id notifica configurazione list
     * @return the list
     */
    List<ConfigurazioneNotificaAllegatoDTO> loadConfigurazioneNotificaAllegato(List<Long> idNotificaConfigurazioneList);

}