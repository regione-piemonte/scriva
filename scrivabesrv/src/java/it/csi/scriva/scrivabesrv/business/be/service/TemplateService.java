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

import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;

import javax.validation.constraints.NotNull;

/**
 * The interface Template service.
 *
 * @author CSI PIEMONTE
 */
public interface TemplateService {

    /**
     * Gets template path.
     *
     * @return the template path
     */
    String getTemplatePath(@NotNull String tipoAdempimento);

    /**
     * Gets template filename.
     *
     * @param idIstanza            the id istanza
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param searchPath           the search path
     * @return the template filename
     */
    String getTemplateFilename(Long idIstanza, String codTipologiaAllegato, String searchPath);

    /**
     * Gets template filename.
     *
     * @param istanza              the istanza
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param searchPath           the search path
     * @return the template filename
     */
    String getTemplateFilename(IstanzaExtendedDTO istanza, String codTipologiaAllegato, String searchPath);

    /**
     * Gets template filename with input stream.
     *
     * @param istanza              the istanza
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param searchPath           the search path
     * @return the template filename with input stream
     */
    String getTemplateFilenameWithInputStream(IstanzaExtendedDTO istanza, String codTipologiaAllegato, String searchPath);

    /**
     * Gets template filename with input stream.
     *
     * @param istanza              the istanza
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param codTipoEvento        the cod tipo evento
     * @param searchPath           the search path
     * @return the template filename with input stream
     */
    String getTemplateFilenameWithInputStream(IstanzaExtendedDTO istanza, String codTipologiaAllegato, String codTipoEvento, String searchPath);

}