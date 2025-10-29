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

import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioDTO;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioResponsabileDTO;

import java.util.List;

/**
 * The interface Competenza territorio dao.
 *
 * @author CSI PIEMONTE
 */
public interface CompetenzaTerritorioDAO {

    /**
     * Load competenze territorio list.
     *
     * @param codComponenteApp the cod componente app
     * @return list list
     */
    List<CompetenzaTerritorioExtendedDTO> loadCompetenzeTerritorio(String codComponenteApp);

    /**
     * Load competenze territorio list.
     *
     * @param idAdempimento    the id adempimento
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    List<CompetenzaTerritorioExtendedDTO> loadCompetenzeTerritorio(Long idAdempimento, String codComponenteApp);

    /**
     * Load competenza territorio by id list.
     *
     * @param idCompetenzaTerritorio idCompetenzaTerritorio
     * @return List<CompetenzaTerritorioExtendedDTO>    list
     */
    List<CompetenzaTerritorioExtendedDTO> loadCompetenzaTerritorioById(Long idCompetenzaTerritorio);

    /**
     * Load competenza territorio by codice gestore parchi list.
     *
     * @param codEnteGestore the cod ente gestore
     * @return the list
     */
    List<CompetenzaTerritorioDTO> loadCompetenzaTerritorioByCodiceGestoreParchi(String codEnteGestore);

    /**
     * Load competenza territorio responsabile.
     *
     * @param idCompetenzaTerritorioResponsabile idCompetenzaTerritorioResponsabile
     * @return List<CompetenzaTerritorioExtendedDTO>   list
     * @throws Exception
     */
    CompetenzaTerritorioResponsabileDTO loadCompetenzeTerritorioByIdCompResp(Long idCompetenzaTerritorioResponsabile) throws Exception;

    /**
     * Load competenza territorio.
     *
     * @param idCompetenzaTerritorioResponsabile idCompetenzaTerritorio
     * @return List<CompetenzaTerritorioResponsabileDTO>   list
     * @throws Exception
     */
    List<CompetenzaTerritorioResponsabileDTO> loadCompetenzeTerritorioByIdCompTerr(Long idCompetenzaTerritorio) throws Exception;

}