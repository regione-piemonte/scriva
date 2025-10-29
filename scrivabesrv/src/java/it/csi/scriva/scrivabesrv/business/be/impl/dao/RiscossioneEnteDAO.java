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

import it.csi.scriva.scrivabesrv.dto.RiscossioneEnteExtendedDTO;

import java.util.List;

/**
 * The interface Riscossione ente dao.
 */
public interface RiscossioneEnteDAO {

    /**
     * Load riscossioni ente attivi list.
     *
     * @return the list
     */
    List<RiscossioneEnteExtendedDTO> loadRiscossioniEnteAttivi();

    /**
     * Load riscossione ente by id list.
     *
     * @param idRiscossioneEnte the id riscossione ente
     * @return the list
     */
    List<RiscossioneEnteExtendedDTO> loadRiscossioneEnteById(Long idRiscossioneEnte);

    /**
     * Load riscossione ente by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    List<RiscossioneEnteExtendedDTO> loadRiscossioneEnteByIdIstanza(Long idIstanza, String componenteApp);

}