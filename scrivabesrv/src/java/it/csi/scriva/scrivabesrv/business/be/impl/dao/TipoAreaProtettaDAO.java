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

import it.csi.scriva.scrivabesrv.dto.TipoAreaProtettaDTO;

import java.util.List;

/**
 * The interface Tipo area protetta dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoAreaProtettaDAO {

    /**
     * Load tipi area protetta list.
     *
     * @return the list
     */
    List<TipoAreaProtettaDTO> loadTipiAreaProtetta();

    /**
     * Load tipo area protetta by id list.
     *
     * @param idTipoAreaProtetta the id tipo area protetta
     * @return the list
     */
    List<TipoAreaProtettaDTO> loadTipoAreaProtettaById(Long idTipoAreaProtetta);

    /**
     * Load tipo area protetta by code list.
     *
     * @param codTipoAreaProtetta the cod tipo area protetta
     * @return the list
     */
    List<TipoAreaProtettaDTO> loadTipoAreaProtettaByCode(String codTipoAreaProtetta);

}