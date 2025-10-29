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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.ComuneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.NazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ProvinciaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.RegioneDAO;
import it.csi.scriva.scrivabesrv.business.be.service.LimitiAmministrativiService;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ProvinciaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RegioneExtendedDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Limiti amministrativi service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class LimitiAmministrativiServiceImpl extends BaseServiceImpl implements LimitiAmministrativiService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private NazioneDAO nazioneDAO;

    @Autowired
    private RegioneDAO regioneDAO;

    @Autowired
    private ProvinciaDAO provinciaDAO;

    @Autowired
    private ComuneDAO comuneDAO;

    /**
     * Load nazioni list.
     *
     * @param codIstat the cod istat
     * @return the list
     */
    @Override
    public List<NazioneDTO> loadNazioni(String codIstat) {
        logBegin(className);
        List<NazioneDTO> nazioneList = null;
        try {
            nazioneList = StringUtils.isBlank(codIstat) ? nazioneDAO.loadNazioni() : nazioneDAO.loadNazioneByCodIstat(codIstat);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return nazioneList;
    }

    /**
     * Load nazioni attive list.
     *
     * @return the list
     */
    @Override
    public List<NazioneDTO> loadNazioniAttive() {
        logBegin(className);
        List<NazioneDTO> nazioneList = null;
        try {
            nazioneList = nazioneDAO.loadNazioniAttive();
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return nazioneList;
    }

    /**
     * Load regioni list.
     *
     * @param codIstat        the cod istat
     * @param codIstatNazione the cod istat nazione
     * @return the list
     */
    @Override
    public List<RegioneExtendedDTO> loadRegioni(String codIstat, String codIstatNazione) {
        logBegin(className);
        List<RegioneExtendedDTO> regioneList = null;
        try {
            regioneList = regioneDAO.loadRegioni(codIstat, codIstatNazione);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return regioneList;
    }

    /**
     * Load regioni attive list.
     *
     * @return the list
     */
    @Override
    public List<RegioneExtendedDTO> loadRegioniAttive() {
        logBegin(className);
        List<RegioneExtendedDTO> regioneList = null;
        try {
            regioneList = regioneDAO.loadRegioniAttive();
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return regioneList;
    }

    /**
     * Load province list.
     *
     * @param codIstat        the cod istat
     * @param codIstatRegione the cod istat regione
     * @param idAdempimento   the id adempimento
     * @return the list
     */
    @Override
    public List<ProvinciaExtendedDTO> loadProvince(String codIstat, String codIstatRegione, Long idAdempimento) {
        logBegin(className);
        List<ProvinciaExtendedDTO> provinciaList = null;
        try {
            provinciaList = provinciaDAO.loadProvince(codIstat, codIstatRegione, idAdempimento);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return provinciaList;
    }

    /**
     * Load province attive list.
     *
     * @return the list
     */
    @Override
    public List<ProvinciaExtendedDTO> loadProvinceAttive() {
        logBegin(className);
        List<ProvinciaExtendedDTO> provinciaList = null;
        try {
            provinciaList = provinciaDAO.loadProvinceAttive();
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return provinciaList;
    }

    /**
     * Load comuni list.
     *
     * @param codIstat          the cod istat
     * @param codIstatProvincia the cod istat provincia
     * @return the list
     */
    @Override
    public List<ComuneExtendedDTO> loadComuni(String codIstat, String codIstatProvincia, String siglaProvincia) {
        logBegin(className);
        List<ComuneExtendedDTO> comuneList = null;
        try {
            comuneList = comuneDAO.loadComuni(codIstat, codIstatProvincia, siglaProvincia);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return comuneList;
    }

    /**
     * Load comuni list.
     *
     * @param codIstatList the cod istat list
     * @return the list
     */
    @Override
    public List<ComuneExtendedDTO> loadComuni(List<String> codIstatList) {
        logBegin(className);
        List<ComuneExtendedDTO> comuneList = null;
        try {
            comuneList = comuneDAO.loadComuni(codIstatList);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return comuneList;
    }

    /**
     * Load comuni attivi list.
     *
     * @return the list
     */
    @Override
    public List<ComuneExtendedDTO> loadComuniAttivi() {
        logBegin(className);
        List<ComuneExtendedDTO> comuneList = null;
        try {
            comuneList = comuneDAO.loadComuniAttivi();
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return comuneList;
    }
}