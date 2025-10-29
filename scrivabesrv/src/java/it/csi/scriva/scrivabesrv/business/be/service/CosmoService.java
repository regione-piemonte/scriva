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

import it.csi.scriva.scrivabesrv.business.be.exception.CosmoException;

import java.io.File;
import java.io.IOException;

/**
 * The type Cosmo service.
 *
 * @author CSI PIEMONTE
 */
public interface CosmoService {

    /**
     * Creazione pratica cosmo.
     *
     * @param idIstanza the id istanza
     * @throws CosmoException the cosmo exception
     * @throws IOException    the io exception
     */
    void creazionePraticaCosmo(Long idIstanza) throws CosmoException, IOException;

    /**
     * Aggiorna pratica documenti cosmo.
     *
     * @param idIstanza the id istanza
     * @throws CosmoException the cosmo exception
     * @throws IOException    the io exception
     */
    void aggiornaPraticaDocumentiCosmo(Long idIstanza, String codTipoEvento) throws CosmoException, IOException;

    /**
     * Aggiorna pratica cosmo.
     *
     * @param idIstanza the id istanza
     * @throws CosmoException the cosmo exception
     */
    void aggiornaPraticaCosmo(Long idIstanza) throws CosmoException;

    /**
     * Gets file from cosmo.
     *
     * @param urlCosmo the url cosmo
     * @return the file from cosmo
     * @throws CosmoException the cosmo exception
     */
    File getFileFromCosmo(String urlCosmo) throws CosmoException;
}