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

import it.csi.scriva.scrivabesrv.dto.SoggettoDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoExtendedDTO;

import java.util.List;

/**
 * The interface Soggetto dao.
 *
 * @author CSI PIEMONTE
 */
public interface SoggettoDAO {

    /**
     * Load soggetti list.
     *
     * @return List<SoggettoExtendedDTO> list
     */
    List<SoggettoExtendedDTO> loadSoggetti();

    /**
     * Load soggetto by codice fiscale and tipo list.
     *
     * @param codiceFiscaleSoggetto codiceFiscaleSoggetto
     * @param tipoSoggetto          tipoSoggetto
     * @return List<SoggettoExtendedDTO> list
     */
    List<SoggettoExtendedDTO> loadSoggettoByCodiceFiscaleAndTipo(String codiceFiscaleSoggetto, String tipoSoggetto);

    /**
     * Load soggetti by tipo soggetto list.
     *
     * @param idTipoSoggetto idTipoSoggetto
     * @return List<SoggettoExtendedDTO> list
     */
    List<SoggettoExtendedDTO> loadSoggettiByTipoSoggetto(Long idTipoSoggetto);

    /**
     * Load soggetti by id list list.
     *
     * @param ids idSoggetto multipli
     * @return List<SoggettoExtendedDTO> list
     */
    List<SoggettoExtendedDTO> loadSoggettiByIdList(List<Long> ids);

    /**
     * Find by pk soggetto dto.
     *
     * @param idSoggetto idSoggetto
     * @return SoggettoDTO soggetto dto
     */
    SoggettoDTO findByPK(Long idSoggetto);

    /**
     * Save soggetto long.
     *
     * @param dto SoggettoDTO
     * @return id record salvato
     */
    Long saveSoggetto(SoggettoDTO dto);

    /**
     * Update soggetto integer.
     *
     * @param dto SoggettoDTO
     * @return numero record aggiornati
     */
    Integer updateSoggetto(SoggettoDTO dto);

    /**
     * Load soggetto list.
     *
     * @param id idSoggetto
     * @return List<SoggettoExtendedDTO> list
     */
    List<SoggettoExtendedDTO> loadSoggetto(Long id);

    /**
     * Load soggetto by codice fiscale list.
     *
     * @param codiceFiscaleSoggetto codiceFiscaleSoggetto
     * @return List<SoggettoExtendedDTO> list
     */
    List<SoggettoExtendedDTO> loadSoggettoByCodiceFiscale(String codiceFiscaleSoggetto);

    /**
     * Load soggetto by partita iva list.
     *
     * @param partitaIvaSoggetto partitaIvaSoggetto
     * @return List<SoggettoExtendedDTO> list
     */
    List<SoggettoExtendedDTO> loadSoggettoByPartitaIva(String partitaIvaSoggetto);

    /**
     * Delete soggetto integer.
     *
     * @param uid uid
     * @return numero record cancellati
     */
    Integer deleteSoggetto(String uid);

    /**
     * Delete soggetto by id integer.
     *
     * @param id idSoggetto
     * @return numero record cancellati
     */
    Integer deleteSoggettoById(Long id);

}