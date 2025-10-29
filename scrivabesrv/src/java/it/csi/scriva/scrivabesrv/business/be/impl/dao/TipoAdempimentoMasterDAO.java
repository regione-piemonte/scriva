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

import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoMasterSlimDTO;

import java.util.List;

/**
 * The interface Tipo adempimento master dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoAdempimentoMasterDAO {

    /**
     * Gets masterdata order call.
     *
     * @param idTipoAdempimento idTipoAdempimento
     * @param tipoInfo          tipoInfo
     * @return List<TipoAdempimentoMasterSlimDTO> masterdata order call
     */
    List<TipoAdempimentoMasterSlimDTO> getMasterdataOrderCall(Long idTipoAdempimento, String tipoInfo);

    /**
     * Gets masterdata order call by code tipo adempimento.
     *
     * @param codTipoAdempimento codTipoAdempimento
     * @param tipoInfo           tipoInfo
     * @return List<TipoAdempimentoMasterSlimDTO> masterdata order call by code tipo adempimento
     */
    List<TipoAdempimentoMasterSlimDTO> getMasterdataOrderCallByCodeTipoAdempimento(String codTipoAdempimento, String tipoInfo);
}