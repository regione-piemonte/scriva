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
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.AdempimentiService;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Adempimenti service.
 */
@Component
public class AdempimentiServiceImpl extends BaseApiServiceImpl implements AdempimentiService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private AdempimentoDAO adempimentoDAO;

    /**
     * Load adempimenti list.
     *
     * @param idAmbito           the id ambito
     * @param codAmbito          the cod ambito
     * @param idTipoAdempimento  the id tipo adempimento
     * @param codTipoAdempimento the cod tipoadempimento
     * @param codAdempimento     the cod adempimento
     * @param idCompilante       the id compilante
     * @param attoreScriva       the attore scriva
     * @return the list
     */
    @Override
    public List<AdempimentoExtendedDTO> loadAdempimenti(Long idAmbito, String codAmbito, Long idTipoAdempimento, String codTipoAdempimento, String codAdempimento, Long idCompilante, AttoreScriva attoreScriva) {
        logBeginInfo(className, "idAmbito : [" + idAmbito + "] - codAmbito : [" + codAmbito + "] - idTipoAdempimento : [" + idTipoAdempimento + "] - codTipoAdempimento : [" + codTipoAdempimento + "] - codAdempimento : [" + codAdempimento + "] - idCompilante : [" + idCompilante + "] - attoreScriva : [" + attoreScriva + "]");
        List<AdempimentoExtendedDTO> adempimentoList = null;
        try {
/*
            adempimentoList = attoreScriva != null && ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())
                    && idAmbito == null && StringUtils.isBlank(codAmbito) && idTipoAdempimento == null && StringUtils.isBlank(codTipoAdempimento)
                    && StringUtils.isBlank(codAdempimento) && idCompilante == null ?
                    adempimentoDAO.loadAdempimentoByCfFunzionario(attoreScriva.getCodiceFiscale()) :
                    adempimentoDAO.loadAdempimenti(idAmbito, codAmbito, idTipoAdempimento, codTipoAdempimento, codAdempimento, idCompilante, attoreScriva.getComponente());
*/
            if (attoreScriva != null) {
                adempimentoList = ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ?
                        adempimentoDAO.loadAdempimentoByCfFunzionario(attoreScriva, idAmbito, codAmbito, idTipoAdempimento, codTipoAdempimento, codAdempimento, idCompilante) :
                        adempimentoDAO.loadAdempimenti(idAmbito, codAmbito, idTipoAdempimento, codTipoAdempimento, codAdempimento, idCompilante, attoreScriva.getComponente());
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return adempimentoList;
    }

    /**
     * Load adempimento list.
     *
     * @param idAdempimento the id adempimento
     * @param attoreScriva  the attore scriva
     * @return the list
     */
    @Override
    public List<AdempimentoExtendedDTO> loadAdempimento(Long idAdempimento, AttoreScriva attoreScriva) {
        logBegin(className);
        logBeginInfo(className, "idAdempimento : [" + idAdempimento + "] - attoreScriva : [" + attoreScriva + "]");
        List<AdempimentoExtendedDTO> adempimentoList = null;
        try {
            adempimentoList = adempimentoDAO.loadAdempimentoById(idAdempimento);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return adempimentoList;
    }
}