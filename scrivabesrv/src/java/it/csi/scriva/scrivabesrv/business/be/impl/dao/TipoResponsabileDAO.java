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

import java.util.List;

import it.csi.scriva.scrivabesrv.dto.TipoResponsabileDTO;

/**
 * The interface tipo responsabili dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoResponsabileDAO {

    /**
     * Load tipo responsabili.
     *
     * @return List<TipoResponsabileDTO>   list
     */
    List<TipoResponsabileDTO> loadTipoResponsabile(Long idIstanza, List<String> codTipoResponsabile);
    
}