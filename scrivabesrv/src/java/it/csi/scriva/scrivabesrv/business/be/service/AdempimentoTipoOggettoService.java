/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service;

import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoOggettoExtendedDTO;

import java.util.List;

/**
 * The interface Adempimento tipo oggetto service.
 *
 * @author CSI PIEMONTE
 */
public interface AdempimentoTipoOggettoService {

    /**
     * Load adempimento tipi oggetto by id adempimento list.
     *
     * @param idAdempimento the id adempimento
     * @return the list
     */
    List<AdempimentoTipoOggettoExtendedDTO> loadAdempimentoTipiOggettoByIdAdempimento(Long idAdempimento);

    /**
     * Load adempimento tipi oggetto by code adempimento list.
     *
     * @param codAdempimento the cod adempimento
     * @return the list
     */
    List<AdempimentoTipoOggettoExtendedDTO> loadAdempimentoTipiOggettoByCodeAdempimento(String codAdempimento);

    /**
     * Load adempimento tipi oggetto to assign by code adempimento adempimento tipo oggetto extended dto.
     *
     * @param codAdempimento the cod adempimento
     * @return the list
     */
    AdempimentoTipoOggettoExtendedDTO loadAdempimentoTipiOggettoToAssignByCodeAdempimento(String codAdempimento);

}