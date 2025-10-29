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
import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoProcedimentoStataleDAO;
import it.csi.scriva.scrivabesrv.business.be.service.StatiProcedimentoStataleService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.StatoProcedimentoStataleDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Stato procedimento statale service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class StatiProcedimentoStataleServiceImpl extends BaseApiServiceImpl implements StatiProcedimentoStataleService {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Comune dao.
     */
    @Autowired
    StatoProcedimentoStataleDAO statoProcedimentoStataleDAO;

    /**
     * Load stati procedimento statale by code list.
     *
     * @param codStatoProcStatale the cod stato proc statale
     * @param attoreScriva        the attore scriva
     * @return the list
     */
    @Override
    public List<StatoProcedimentoStataleDTO> loadStatiProcedimentoStataleByCode(String codStatoProcStatale, AttoreScriva attoreScriva) {
        logBeginInfo(className, "codStatoProcStatale : [" + codStatoProcStatale + "] - attoreScriva :\n " + attoreScriva + "\n");
        try {
            return StringUtils.isNotBlank(codStatoProcStatale) ? statoProcedimentoStataleDAO.loadStatiProcedimentoStataleByCode(codStatoProcStatale) : statoProcedimentoStataleDAO.loadStatiProcedimentoStatale(attoreScriva.getComponente());
        } finally {
            logEnd(className);
        }
    }
}