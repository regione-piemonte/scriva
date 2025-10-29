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

import it.csi.scriva.scrivabesrv.dto.TipoNaturaGiuridicaDTO;

import java.util.List;

/**
 * The interface Tipo natura giuridica dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoNaturaGiuridicaDAO {

    /**
     * Load tipi natura giuridica list.
     *
     * @return List<TipoNaturaGiuridicaDTO>  list
     */
    List<TipoNaturaGiuridicaDTO> loadTipiNaturaGiuridica();

    /**
     * Load tipo natura giuridica by id list.
     *
     * @param idTipoNaturaGiuridica idTipoNaturaGiuridica
     * @return List<TipoNaturaGiuridicaDTO>  list
     */
    List<TipoNaturaGiuridicaDTO> loadTipoNaturaGiuridicaById(Long idTipoNaturaGiuridica);

    /**
     * Load tipo natura giuridica by code list.
     *
     * @param codTipoNaturaGiuridica codTipoNaturaGiuridica
     * @return List<TipoNaturaGiuridicaDTO>  list
     */
    List<TipoNaturaGiuridicaDTO> loadTipoNaturaGiuridicaByCode(String codTipoNaturaGiuridica);

    /**
     * Load tipo natura giuridica by code masterdata fonte list.
     *
     * @param codMasterdata the cod masterdata
     * @param infoFonte     the info fonte
     * @param codFonte      the cod fonte
     * @return the list
     */
    List<TipoNaturaGiuridicaDTO> loadTipoNaturaGiuridicaByCodeMasterdataFonte(String codMasterdata, String infoFonte, String codFonte);

    /**
     * Save tipo natura giuridica.
     *
     * @param dto TipoNaturaGiuridicaDTO
     */
    void saveTipoNaturaGiuridica(TipoNaturaGiuridicaDTO dto);

    /**
     * Update tipo natura giuridica.
     *
     * @param dto TipoNaturaGiuridicaDTO
     */
    void updateTipoNaturaGiuridica(TipoNaturaGiuridicaDTO dto);

    /**
     * Delete tipo natura giuridica.
     *
     * @param id TipoNaturaGiuridicaDTO
     */
    void deleteTipoNaturaGiuridica(Long id);
}