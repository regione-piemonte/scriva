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

import it.csi.scriva.scrivabesrv.dto.TipoCompetenzaDTO;

import java.util.List;

/**
 * The interface Tipo competenza dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoCompetenzaDAO {

    /**
     * Load tipi competenza list.
     *
     * @return List<TipoCompetenzaDTO>  list
     */
    List<TipoCompetenzaDTO> loadTipiCompetenza();

    /**
     * Load tipo competenza by id list.
     *
     * @param idTipoCompetenza idTipoCompetenza
     * @return List<TipoCompetenzaDTO>  list
     */
    List<TipoCompetenzaDTO> loadTipoCompetenzaById(Long idTipoCompetenza);

    /**
     * Load tipo competenza by code list.
     *
     * @param codTipoCompetenza codTipoCompetenza
     * @return List<TipoCompetenzaDTO>  list
     */
    List<TipoCompetenzaDTO> loadTipoCompetenzaByCode(String codTipoCompetenza);

    /**
     * Load tipo competenza by id categoria oggetto list.
     *
     * @param idCategoriaOggetto idCategoriaOggetto
     * @return List<TipoCompetenzaDTO>  list
     */
    List<TipoCompetenzaDTO> loadTipoCompetenzaByIdCategoriaOggetto(Long idCategoriaOggetto);

    /**
     * Load tipo competenza by id adempimento list.
     *
     * @param idAdempimento the id adempimento
     * @return the list
     */
    List<TipoCompetenzaDTO> loadTipoCompetenzaByIdAdempimento(Long idAdempimento);

}