/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service.impl;

import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoStatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.AdempiStatoIstanzaService;
import it.csi.scriva.scrivabesrv.dto.AdempimentoStatoIstanzaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Adempi stato istanza service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class AdempiStatoIstanzaServiceImpl extends BaseApiServiceImpl implements AdempiStatoIstanzaService {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Adempimento stato istanza dao.
     */
    @Autowired
    AdempimentoStatoIstanzaDAO adempimentoStatoIstanzaDAO;

    /**
     * Load adempi stato istanza list.
     *
     * @param idAdempimento the id adempimento
     * @return the list
     */
    @Override
    public List<AdempimentoStatoIstanzaDTO> loadAdempiStatoIstanza(Long idAdempimento) {
        logBeginInfo(className, "idAdempimento : [" + idAdempimento + "]");
        try {
            return adempimentoStatoIstanzaDAO.loadAdempiStatoIstanza(idAdempimento);
        } finally {
            logEnd(className);
        }
    }
}