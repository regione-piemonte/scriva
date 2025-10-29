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

import it.csi.scriva.scrivabesrv.dto.TipoIntegraAllegatoDTO;

import java.util.List;

/**
 * The interface Tipo integra allegato dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoIntegraAllegatoDAO {

    /**
     * Load tipi integra allegato list.
     *
     * @return the list
     */
    List<TipoIntegraAllegatoDTO> loadTipiIntegraAllegato();

    /**
     * Load tipo integra allegato by id list.
     *
     * @param idTipoIntegraAllegato the id tipo integra allegato
     * @return the list
     */
    List<TipoIntegraAllegatoDTO> loadTipoIntegraAllegatoById(Long idTipoIntegraAllegato);

    /**
     * Load tipo integra allegato by code list.
     *
     * @param codTipoIntegraAllegato the cod tipo integra allegato
     * @return the list
     */
    List<TipoIntegraAllegatoDTO> loadTipoIntegraAllegatoByCode(String codTipoIntegraAllegato);

}