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

import it.csi.scriva.scrivabesrv.dto.MessaggioDTO;
import it.csi.scriva.scrivabesrv.dto.MessaggioExtendedDTO;

import java.util.List;

/**
 * The interface Messaggio dao.
 *
 * @author CSI PIEMONTE
 */
public interface MessaggioDAO {

    /**
     * Load messaggi list.
     *
     * @return List<MessaggioExtendedDTO> list
     */
    List<MessaggioExtendedDTO> loadMessaggi();

    /**
     * Load messaggio list.
     *
     * @param idMessaggio idMessaggio
     * @return List<MessaggioExtendedDTO> list
     */
    List<MessaggioExtendedDTO> loadMessaggio(Long idMessaggio);

    /**
     * Load messaggio by code list.
     *
     * @param codMessaggio codMessaggio
     * @return List<MessaggioExtendedDTO> list
     */
    List<MessaggioExtendedDTO> loadMessaggioByCode(String codMessaggio);

    /**
     * Load messaggi by code tipo messaggio list.
     *
     * @param codTipoMessaggio codTipoMessaggio
     * @return List<MessaggioExtendedDTO> list
     */
    List<MessaggioExtendedDTO> loadMessaggiByCodeTipoMessaggio(String codTipoMessaggio);

    /**
     * Save messaggio long.
     *
     * @param dto MessaggioDTO
     * @return id del record salvato
     */
    Long saveMessaggio(MessaggioDTO dto);

    /**
     * Update messaggio integer.
     *
     * @param dto MessaggioDTO
     * @return numero record aggiornati
     */
    Integer updateMessaggio(MessaggioDTO dto);

    /**
     * Delete messaggio integer.
     *
     * @param idMessaggio idMessaggio
     * @return numero record cancellati
     */
    Integer deleteMessaggio(Long idMessaggio);
}