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

import it.csi.scriva.scrivabesrv.dto.ComponenteAppDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaGestore;

import java.util.List;

/**
 * The interface Componente app dao.
 *
 * @author CSI PIEMONTE
 */
public interface ComponenteAppDAO {

    /**
     * Load componenti app list.
     *
     * @return List<ComponenteAppDTO>         list
     */
    List<ComponenteAppDTO> loadComponentiApp();

    /**
     * Load componente app by id list.
     *
     * @param idComponenteApp the id componente app
     * @return List<ComponenteAppDTO>         list
     */
    List<ComponenteAppDTO> loadComponenteAppById(Long idComponenteApp);

    /**
     * Load componente app by code list.
     *
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    List<ComponenteAppDTO> loadComponenteAppByCode(String codComponenteApp);

    /**
     * Load componente app by id adempimento comp territorio list.
     *
     * @param idAdempimento          the id adempimento
     * @param idCompetenzaTerritorio the id competenza territorio
     * @return the componente app dto
     */
    ComponenteAppDTO loadComponenteAppByIdAdempimentoCompTerritorio(Long idAdempimento, Long idCompetenzaTerritorio);

    /**
     * Load istanza competenza gestore list.
     *
     * @param idIstanzaList the id istanza list
     * @return the list
     */
    List<IstanzaCompetenzaGestore> loadIstanzaCompetenzaGestore(List<Long> idIstanzaList);

}