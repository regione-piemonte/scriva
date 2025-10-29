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

import it.csi.scriva.scrivabesrv.dto.AdempimentoStatoIstanzaDTO;

import java.util.List;

/**
 * The interface Adempi stato istanza service.
 *
 * @author CSI PIEMONTE
 */
public interface AdempiStatoIstanzaService {

    /**
     * Load adempi stato istanza list.
     *
     * @param idAdempimento the id adempimento
     * @return the list
     */
    List<AdempimentoStatoIstanzaDTO> loadAdempiStatoIstanza(Long idAdempimento);

}