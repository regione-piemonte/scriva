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
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoTipoOggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.AdempimentoTipoOggettoService;
import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoOggettoExtendedDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Adempimento tipo oggetto service.
 */
@Component
public class AdempimentoTipoOggettoServiceImpl extends BaseApiServiceImpl implements AdempimentoTipoOggettoService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private AdempimentoTipoOggettoDAO adempimentoTipoOggettoDAO;

    /**
     * Load adempimento tipi oggetto by id adempimento list.
     *
     * @param idAdempimento the id adempimento
     * @return the list
     */
    @Override
    public List<AdempimentoTipoOggettoExtendedDTO> loadAdempimentoTipiOggettoByIdAdempimento(Long idAdempimento) {
        logBeginInfo(className, idAdempimento);
        return adempimentoTipoOggettoDAO.loadAdempimentoTipiOggettoByIdAdempimento(idAdempimento);
    }

    /**
     * Load adempimento tipi oggetto by code adempimento list.
     *
     * @param codAdempimento the cod adempimento
     * @return the list
     */
    @Override
    public List<AdempimentoTipoOggettoExtendedDTO> loadAdempimentoTipiOggettoByCodeAdempimento(String codAdempimento) {
        logBeginInfo(className, codAdempimento);
        return adempimentoTipoOggettoDAO.loadAdempimentoTipiOggettoByCodeAdempimento(codAdempimento);
    }

    /**
     * Load adempimento tipi oggetto to assign by code adempimento list.
     *
     * @param codAdempimento the cod adempimento
     * @return the list
     */
    @Override
    public AdempimentoTipoOggettoExtendedDTO loadAdempimentoTipiOggettoToAssignByCodeAdempimento(String codAdempimento) {
        logBeginInfo(className, codAdempimento);
        List<AdempimentoTipoOggettoExtendedDTO> adempimentoTipoOggettoList = adempimentoTipoOggettoDAO.loadAdempimentoTipiOggettoByCodeAdempimento(codAdempimento);
        adempimentoTipoOggettoList = adempimentoTipoOggettoList.stream()
                .filter(adempimentoTipoOggetto -> Boolean.TRUE.equals(adempimentoTipoOggetto.getFlgAssegna()))
                .collect(Collectors.toList());
        return CollectionUtils.isNotEmpty(adempimentoTipoOggettoList) ? adempimentoTipoOggettoList.get(0) : null;
    }
}