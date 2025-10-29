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

import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoProfiloDTO;

import java.util.List;

/**
 * The interface Tipo adempimento profilo dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoAdempimentoProfiloDAO {

    /**
     * Load tipo adempimento profili list.
     *
     * @return List<TipoAdempimentoProfiloExtendedDTO>  list
     */
    List<TipoAdempimentoProfiloDTO> loadTipoAdempimentoProfili();

    /**
     * Load tipo adempimento profilo by id list.
     *
     * @param idTipoAdempimentoProfilo idTipoAdempimentoProfilo
     * @return List<TipoAdempimentoProfiloExtendedDTO>  list
     */
    List<TipoAdempimentoProfiloDTO> loadTipoAdempimentoProfiloById(Long idTipoAdempimentoProfilo);

    /**
     * Load tipo adempimento profilo by id list list.
     *
     * @param idTipoAdempimentoProfilo the id tipo adempimento profilo
     * @return the list
     */
    List<TipoAdempimentoProfiloDTO> loadTipoAdempimentoProfiloByIdProfiloAppList(List<Long> idTipoAdempimentoProfilo);

    /**
     * Load tipo adempimento profilo by code profilo app list.
     *
     * @param codProfiloApp codProfiloApp
     * @return List<TipoAdempimentoOggettoAppExtendedDTO>  list
     */
    List<TipoAdempimentoProfiloDTO> loadTipoAdempimentoProfiloByCodeProfiloApp(Long codProfiloApp);

    /**
     * Load tipo adempimento profilo by code adempimento list.
     *
     * @param codAdempimento codAdempimento
     * @return List<TipoAdempimentoOggettoAppExtendedDTO>  list
     */
    List<TipoAdempimentoProfiloDTO> loadTipoAdempimentoProfiloByCodeAdempimento(Long codAdempimento);

}