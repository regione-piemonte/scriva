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

import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioResponsabileDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaResponsabileDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaResponsabileExtendedDTO;

import java.util.List;

/**
 * The interface Istanza responsabili dao.
 *
 * @author CSI PIEMONTE
 */
public interface IstanzaResponsabiliDAO {

    /**
     * Load istanza responsabili by id istanza.
     *
     * @param idIstanza idIstanza
     * @return List<IstanzaResponsabileExtendedDTO>     list
     */
    List<IstanzaResponsabileExtendedDTO> loadIstanzaResponsabiliByIstanza(Long idIstanza);

    /**
     * Save istanza long.
     *
     * @param dto IstanzaResponsabileDTO
     * @return id record salvato
     */
    Long saveIstanzaResponsabile(IstanzaResponsabileDTO dto);

    /**
     * Update istanza long.
     *
     * @param dto IstanzaDTO
     * @return numero record aggiornati
     */
    Integer updateIstanzaResponsabile(IstanzaResponsabileDTO dto);

    /**
     * Load istanza responsabili by id competenza territorio.
     *
     * @param idCompetenzaTerritorio idCompetenzaTerritorio
     * @return List<IstanzaResponsabileExtendedDTO>     list
     */
    List<CompetenzaTerritorioResponsabileDTO> loadIstanzaResponsabiliByIdCompetenza(Long idCompetenzaTerritorio);

    /**
     * Delete istanza responsabile integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    Integer deleteIstanzaResponsabile(String gestUID);

    /**
     * Delete istanza responsabile integer.
     *
     * @param idIstanzaResponsabileList the id istanza responsabile list
     * @return the integer
     */
    Integer deleteIstanzaResponsabile(List<Long> idIstanzaResponsabileList);
}