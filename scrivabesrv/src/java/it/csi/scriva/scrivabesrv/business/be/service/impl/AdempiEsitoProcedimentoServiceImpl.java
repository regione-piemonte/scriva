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
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempiEsitoProcedimentoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.AdempiEsitoProcedimentoService;
import it.csi.scriva.scrivabesrv.dto.AdempiEsitoProcedimentoExtendedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Adempi esito procedimento service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class AdempiEsitoProcedimentoServiceImpl extends BaseApiServiceImpl implements AdempiEsitoProcedimentoService {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Comune dao.
     */
    @Autowired
    AdempiEsitoProcedimentoDAO adempiEsitoProcedimentoDAO;

    /**
     * Load adempi esito procedimento list.
     *
     * @param idAdempimento the id adempimento
     * @param flgAttivi     the flg attivi
     * @return the list
     */
    @Override
    public List<AdempiEsitoProcedimentoExtendedDTO> loadAdempiEsitoProcedimento(Long idAdempimento, boolean flgAttivi) {
        logBeginInfo(className, "idAdempimento : [" + idAdempimento + "] - flgAttivi : [" + flgAttivi + "]");
        try {
            return adempiEsitoProcedimentoDAO.loadAdempiEsitoProcedimento(idAdempimento, flgAttivi);
        } finally {
            logEnd(className);
        }
    }
}