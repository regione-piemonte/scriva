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
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CompilantePreferenzaCanaleDAO;
import it.csi.scriva.scrivabesrv.business.be.service.CompilantePreferenzaCanaleService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.CompilantePreferenzaCanaleExtendedDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * The type Compilante preferenza canale service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class CompilantePreferenzaCanaleServiceImpl extends BaseApiServiceImpl implements CompilantePreferenzaCanaleService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private CompilantePreferenzaCanaleDAO compilantePreferenzaCanaleDAO;

    /**
     * Load compilante preferenze by codice fiscale list.
     *
     * @param cfCompilante the cf compilante
     * @return the list
     */
    @Override
    public List<CompilantePreferenzaCanaleExtendedDTO> loadCompilantePreferenzeByCodiceFiscale(String cfCompilante) {
        logBeginInfo(className, cfCompilante);
        List<CompilantePreferenzaCanaleExtendedDTO> result = new ArrayList<>();
        try {
            List<CompilantePreferenzaCanaleExtendedDTO> compilantePreferenzaCanaleList = compilantePreferenzaCanaleDAO.loadCompilantePreferenzeCanaleByCodiceFiscale(cfCompilante, Boolean.FALSE);
            List<CompilantePreferenzaCanaleExtendedDTO> preferenzaCanaleList = compilantePreferenzaCanaleDAO.loadPreferenzeCanale(Boolean.FALSE);
            List<CompilantePreferenzaCanaleExtendedDTO> preferenzaCanaleListFiltered = preferenzaCanaleList;
            if (CollectionUtils.isNotEmpty(compilantePreferenzaCanaleList)) {
                result.addAll(compilantePreferenzaCanaleList);
                preferenzaCanaleListFiltered = CollectionUtils.isNotEmpty(preferenzaCanaleList) ?
                        preferenzaCanaleList.stream()
                                .filter(p -> compilantePreferenzaCanaleList.stream()
                                        .noneMatch(cp -> cp.getCanaleNotifica().getIdCanaleNotifica().equals(p.getCanaleNotifica().getIdCanaleNotifica()))
                                )
                                .collect(Collectors.toList()) :
                        new ArrayList<>();
            }
            if (CollectionUtils.isNotEmpty(preferenzaCanaleListFiltered)) {
                result.addAll(preferenzaCanaleListFiltered);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Load all compilante preferenze by codice fiscale list.
     *
     * @param cfCompilante the cf compilante
     * @return the list
     */
    @Override
    public List<CompilantePreferenzaCanaleExtendedDTO> loadAllCompilantePreferenzeByCodiceFiscale(String cfCompilante) {
        logBeginInfo(className, cfCompilante);
        return compilantePreferenzaCanaleDAO.loadCompilantePreferenzeCanaleByCodiceFiscale(cfCompilante, Boolean.TRUE);
    }

    /**
     * Load all compilante preferenze list by codice fiscale list.
     *
     * @param cfCompilante      the cf compilante
     * @param codCanaleNotifica the cod canale notifica
     * @return the list
     */
    @Override
    public Boolean checkCompilantePreferenzeCanale(String cfCompilante, String codCanaleNotifica) {
        logBeginInfo(className, cfCompilante + codCanaleNotifica);
        boolean result = Boolean.FALSE;
        List<CompilantePreferenzaCanaleExtendedDTO> compilantePreferenzaCanaleList = compilantePreferenzaCanaleDAO.loadCompilantePreferenzeCanaleByCodiceFiscale(cfCompilante, Boolean.TRUE);
        if (CollectionUtils.isNotEmpty(compilantePreferenzaCanaleList)) {
            compilantePreferenzaCanaleList = compilantePreferenzaCanaleList.stream()
                    .filter(pref -> codCanaleNotifica.equalsIgnoreCase(pref.getCanaleNotifica().getCodCanaleNotifica()) && Boolean.TRUE.equals(pref.getFlgAbilitato()))
                    .collect(Collectors.toList());
            result = CollectionUtils.isNotEmpty(compilantePreferenzaCanaleList);
        }
        return result;
    }

    /**
     * Upsert compilante preferenza canale integer.
     *
     * @param compilantePreferenzaCanaleList the compilante preferenza canale list
     * @param attoreScriva                   the attore scriva
     * @return the integer
     */
    @Override
    public Integer upsertCompilantePreferenzaCanale(List<CompilantePreferenzaCanaleExtendedDTO> compilantePreferenzaCanaleList, AttoreScriva attoreScriva) {
        logBeginInfo(className, compilantePreferenzaCanaleList);
        AtomicInteger res = new AtomicInteger();
        if (!CollectionUtils.isEmpty(compilantePreferenzaCanaleList)) {
            compilantePreferenzaCanaleList.forEach(compilantePreferenzaCanale -> {
                compilantePreferenzaCanale.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                compilantePreferenzaCanale.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                res.addAndGet(compilantePreferenzaCanaleDAO.upsertCompilantePreferenzaCanale(compilantePreferenzaCanale.getDTO()));
            });
        }
        return res.get();
    }

}