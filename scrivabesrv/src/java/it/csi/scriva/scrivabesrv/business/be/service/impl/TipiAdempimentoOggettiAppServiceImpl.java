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
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAdempimentoOggettoAppDAO;
import it.csi.scriva.scrivabesrv.business.be.service.TipiAdempimentoOggettiAppService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoOggettoAppExtendedDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Tipi adempimento oggetti app service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class TipiAdempimentoOggettiAppServiceImpl extends BaseApiServiceImpl implements TipiAdempimentoOggettiAppService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private TipoAdempimentoOggettoAppDAO tipoAdempimentoOggettoAppDAO;

    /**
     * Gets tipo adempimento oggetti app.
     *
     * @return the tipo adempimento oggetti app
     */
    @Override
    public List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettiApp() {
        logBegin(className);
        try {
            return tipoAdempimentoOggettoAppDAO.loadTipoAdempimentoOggettiApp();
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets tipo adempimento oggetto app by id.
     *
     * @param idTipoAdempimentoOggettoApp the id tipo adempimento oggetto app
     * @return the tipo adempimento oggetto app by id
     */
    @Override
    public List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettoAppById(Long idTipoAdempimentoOggettoApp) {
        logBegin(className);
        try {
            return tipoAdempimentoOggettoAppDAO.loadTipoAdempimentoOggettoAppById(idTipoAdempimentoOggettoApp);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets tipo adempimento oggetto app by id tipo adempimento profilo.
     *
     * @param idTipoAdempimentoProfilo the id tipo adempimento profilo
     * @return the tipo adempimento oggetto app by id tipo adempimento profilo
     */
    @Override
    public List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettoAppByIdTipoAdempimentoProfilo(Long idTipoAdempimentoProfilo) {
        logBegin(className);
        try {
            return tipoAdempimentoOggettoAppDAO.loadTipoAdempimentoOggettoAppByIdTipoAdempimentoProfilo(idTipoAdempimentoProfilo);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets tipo adempimento oggetto app by code oggetto app.
     *
     * @param codOggettoApp the cod oggetto app
     * @return the tipo adempimento oggetto app by code oggetto app
     */
    @Override
    public List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettoAppByCodeOggettoApp(String codOggettoApp) {
        logBegin(className);
        try {
            return tipoAdempimentoOggettoAppDAO.loadTipoAdempimentoOggettoAppByCodeOggettoApp(codOggettoApp);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets tipo adempimento oggetto app for azioni.
     *
     * @param idIstanza         the id istanza
     * @param codTipoOggettoApp the cod tipo oggetto app
     * @param attoreScriva      the attore scriva
     * @return the tipo adempimento oggetto app for azioni
     */
    @Override
    public List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettoAppForAzioni(Long idIstanza, String codTipoOggettoApp, AttoreScriva attoreScriva) {
        logBegin(className);
        try {
            return tipoAdempimentoOggettoAppDAO.loadTipoAdempimentoOggettoAppForAzioni(idIstanza, attoreScriva.getCodiceFiscale(), attoreScriva.getComponente(), codTipoOggettoApp);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets tipo adempimento oggetto app for azioni.
     *
     * @param idIstanzaList     the id istanza list
     * @param codTipoOggettoApp the cod tipo oggetto app
     * @param attoreScriva      the attore scriva
     * @return the tipo adempimento oggetto app for azioni
     */
    @Override
    public List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettoAppForAzioni(List<Long> idIstanzaList, String codTipoOggettoApp, AttoreScriva attoreScriva) {
        logBegin(className);
        try {
            return tipoAdempimentoOggettoAppDAO.loadTipoAdempimentoOggettoAppForAzioni(idIstanzaList, attoreScriva.getCodiceFiscale(), attoreScriva.getComponente(), codTipoOggettoApp);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets tipo adempimento oggetto app for azioni with gestore.
     *
     * @param idIstanza         the id istanza
     * @param codTipoOggettoApp the cod tipo oggetto app
     * @param attoreScriva      the attore scriva
     * @return the tipo adempimento oggetto app for azioni with gestore
     */
    @Override
    public List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettoAppForAzioniWithGestore(Long idIstanza, String codTipoOggettoApp, AttoreScriva attoreScriva) {
        logBegin(className);
        try {
            return getOggettoAppNonPrevistoDaGestoreProcesso(getTipoAdempimentoOggettoAppForAzioni(idIstanza, codTipoOggettoApp, attoreScriva));
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets tipo adempimento oggetto app for azioni with gestore.
     *
     * @param idIstanzaList     the id istanza list
     * @param codTipoOggettoApp the cod tipo oggetto app
     * @param attoreScriva      the attore scriva
     * @return the tipo adempimento oggetto app for azioni with gestore
     */
    @Override
    public List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettoAppForAzioniWithGestore(List<Long> idIstanzaList, String codTipoOggettoApp, AttoreScriva attoreScriva) {
        logBegin(className);
        try {
            return getOggettoAppNonPrevistoDaGestoreProcesso(getTipoAdempimentoOggettoAppForAzioni(idIstanzaList, codTipoOggettoApp, attoreScriva));
        } finally {
            logEnd(className);
        }
    }


    /**
     * Gets tipo adempimento oggetto app non previsto da gestore processo.
     *
     * @param tipoAdempimentoOggettoAppList the tipo adempimento oggetto app list
     * @return the tipo adempimento oggetto app non previsto da gestore processo
     */
    @Override
    public List<TipoAdempimentoOggettoAppExtendedDTO> getOggettoAppNonPrevistoDaGestoreProcesso(List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList) {
        logBegin(className);
        List<TipoAdempimentoOggettoAppExtendedDTO> result = null;
        try {
            if (CollectionUtils.isNotEmpty(tipoAdempimentoOggettoAppList)) {
                result = tipoAdempimentoOggettoAppList.stream()
                        .filter(t -> t != null && t.getOggettoApp() != null && Boolean.FALSE.equals(t.getOggettoApp().getFlgPrevistoDaGestoreProcesso()))
                        .collect(Collectors.toList());
            }
        } finally {
            logEnd(className);
        }
        return result;
    }

}