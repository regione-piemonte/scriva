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

import it.csi.scriva.scrivabesrv.dto.TemplateComunicazioneDTO;

import java.util.List;

/**
 * The interface Template comunicazione dao.
 *
 * @author CSI PIEMONTE
 */
public interface TemplateComunicazioneDAO {

    /**
     * Load template by code list.
     *
     * @param code codice template
     * @return List<TemplateComunicazioneDTO> list
     */
    List<TemplateComunicazioneDTO> loadTemplateByCode(String code);
}