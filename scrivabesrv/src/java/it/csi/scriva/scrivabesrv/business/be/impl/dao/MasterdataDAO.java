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

import it.csi.scriva.scrivabesrv.dto.MasterdataDTO;

/**
 * The interface Masterdata dao.
 *
 * @author CSI PIEMONTE
 */
public interface MasterdataDAO {

    /**
     * Load by code masterdata dto.
     *
     * @param codMasterdata codMasterdata
     * @return MasterdataDTO masterdata dto
     */
    MasterdataDTO loadByCode(String codMasterdata);
}