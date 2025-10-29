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

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GruppoSoggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.SoggettoGruppoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.GruppoSoggettoService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.GruppoSoggettoDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoGruppoDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoGruppoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The type Gruppo soggetto service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class GruppoSoggettoServiceImpl extends BaseServiceImpl implements GruppoSoggettoService {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Gruppo soggetto dao.
     */
    @Autowired
    GruppoSoggettoDAO gruppoSoggettoDAO;

    /**
     * The Soggetto gruppo dao.
     */
    @Autowired
    SoggettoGruppoDAO soggettoGruppoDAO;

    /**
     * Create gruppo soggetto gruppo soggetto dto.
     *
     * @param gruppoSoggetto the gruppo soggetto
     * @return the gruppo soggetto dto
     */
    @Override
    public GruppoSoggettoDTO createGruppoSoggetto(GruppoSoggettoDTO gruppoSoggetto) throws GenericException {
        logBeginInfo(className, gruppoSoggetto);
        List<GruppoSoggettoDTO> gruppoSoggettoList = null;
        Long id = gruppoSoggettoDAO.saveGruppoSoggetto(gruppoSoggetto);
        if (id != null && id > 0) {
            gruppoSoggettoList = gruppoSoggettoDAO.loadGruppoSoggettoById(id);
        }
        logEnd(className);
        return gruppoSoggettoList != null && !gruppoSoggettoList.isEmpty() ? gruppoSoggettoList.get(0) : null;
    }

    /**
     * Add soggetto istanza to gruppo.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @param gruppoSoggetto    the gruppo soggetto
     * @param flgCapoGruppo     the flg capo gruppo
     * @param attoreScriva      the attore scriva
     */
    @Override
    public void addSoggettoIstanzaToGruppo(@NotNull Long idSoggettoIstanza, @NotNull GruppoSoggettoDTO gruppoSoggetto, @NotNull Boolean flgCapoGruppo, @NotNull AttoreScriva attoreScriva) {
        logBeginInfo(className, idSoggettoIstanza);
        Long idGruppo = null;
        if (gruppoSoggetto != null) {
            //create group if not exist
            gruppoSoggetto.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            gruppoSoggetto.setIdFunzionario(ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null);
            //gruppoSoggetto.setIdIstanzaAttore(!ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null);
            idGruppo = gruppoSoggetto.getIdGruppoSoggetto() == null ?
                    gruppoSoggettoDAO.saveGruppoSoggetto(gruppoSoggetto) :
                    gruppoSoggetto.getIdGruppoSoggetto();
        }
        SoggettoGruppoDTO soggettoGruppo = soggettoGruppoDAO.loadSoggettiGruppoByPK(idGruppo, idSoggettoIstanza);
        if (soggettoGruppo == null) {
            //create association between soggettoIstanza and gruppo
            soggettoGruppoDAO.saveSoggettoGruppo(getSoggettoGruppo(idGruppo, idSoggettoIstanza, flgCapoGruppo, attoreScriva.getCodiceFiscale()));
        }
        logEnd(className);
    }

    /**
     * Update soggetto istanza gruppo.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @param gruppoSoggetto    the gruppo soggetto
     * @param flgCapoGruppo     the flg capo gruppo
     * @param attoreScriva      the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateSoggettoIstanzaGruppo(@NotNull Long idSoggettoIstanza, @NotNull GruppoSoggettoDTO gruppoSoggetto, @NotNull Boolean flgCapoGruppo, @NotNull AttoreScriva attoreScriva) {
        logBeginInfo(className, idSoggettoIstanza);
        Integer result = null;
        if (gruppoSoggetto != null) {
            gruppoSoggetto.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
            gruppoSoggetto.setIdFunzionario(ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null);
            gruppoSoggetto.setIdIstanzaAttore(!ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null);
            result = gruppoSoggettoDAO.updateGruppoSoggetto(gruppoSoggetto);
            SoggettoGruppoDTO soggettoGruppo = soggettoGruppoDAO.loadSoggettiGruppoByPK(gruppoSoggetto.getIdGruppoSoggetto(), idSoggettoIstanza);
            if (soggettoGruppo != null) {
                soggettoGruppoDAO.updateSoggettoGruppo(getSoggettoGruppo(gruppoSoggetto.getIdGruppoSoggetto(), idSoggettoIstanza, flgCapoGruppo, attoreScriva.getCodiceFiscale()));
            }
        }
        logEnd(className);
        return result;
    }

    /**
     * Remove soggetto istanza from gruppo.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     */
    @Override
    @Transactional
    public Integer removeSoggettoIstanzaFromGruppo(Long idSoggettoIstanza) {
        logBeginInfo(className, idSoggettoIstanza);
        Long idGruppo = null;
        Integer result = null;
        List<SoggettoGruppoExtendedDTO> soggettoGruppoList = soggettoGruppoDAO.loadSoggettoGruppoByIdSoggettoIstanza(idSoggettoIstanza);
        logInfo(className, "Extracted soggettoGruppoList with idSoggettoIstanza [" + idSoggettoIstanza + "]:\n" + soggettoGruppoList + "\n");

        //eliminazione soggetto dal gruppo
        for (SoggettoGruppoExtendedDTO soggettoGruppo : soggettoGruppoList) {
            result = deleteSoggettoGruppo(soggettoGruppo.getGestUID());
            logInfo(className, "Num gruppoSoggetto deleted with gestUID [" + soggettoGruppo.getGestUID() + "] : [" + result + "]");
            idGruppo = soggettoGruppo.getGruppoSoggetto().getIdGruppoSoggetto();
        }

        //se il soggetto è l'ultimo associato al gruppo, eliminare il gruppo
        if (idGruppo != null) {
            removeGruppo(idGruppo);
        }
        logEnd(className);
        return result;
    }

    /**
     * Remove gruppo integer.
     *
     * @param idGruppo the id gruppo
     * @return the integer
     */
    @Override
    @Transactional
    public Integer removeGruppo(Long idGruppo) {
        logBeginInfo(className, idGruppo);
        Integer result = null;
        //se il soggetto è l'ultimo associato al gruppo, eliminare il gruppo
        List<SoggettoGruppoExtendedDTO> soggettoGruppoList = soggettoGruppoDAO.loadSoggettoGruppoByIdGruppoSoggetto(idGruppo);
        logInfo(className, "Extracted soggettoGruppoList with idGruppo [" + idGruppo + "]:\n" + soggettoGruppoList + "\n");
        if (soggettoGruppoList != null && soggettoGruppoList.isEmpty()) {
            List<GruppoSoggettoDTO> gruppoSoggettoList = gruppoSoggettoDAO.loadGruppoSoggettoById(idGruppo);
            logInfo(className, "Extracted gruppoSoggettoList with idGruppo [" + idGruppo + "]:\n" + gruppoSoggettoList + "\n");
            if (gruppoSoggettoList != null && !gruppoSoggettoList.isEmpty()) {
                result = deleteGruppoSoggetto(gruppoSoggettoList.get(0).getGestUID());
                logInfo(className, "Num gruppoSoggetto deleted with gestUID [" + gruppoSoggettoList.get(0).getGestUID() + "] : [" + result + "]");
            }
        }
        logEnd(className);
        return result;
    }

    /**
     * Delete gruppo soggetto integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    @Override
    @Transactional
    public Integer deleteGruppoSoggetto(String gestUID) {
        logBeginInfo(className, gestUID);
        logEnd(className);
        return StringUtils.isNotBlank(gestUID) ? gruppoSoggettoDAO.deleteGruppoSoggettoByUid(gestUID) : null;
    }

    /**
     * Delete soggetto gruppo integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    @Override
    @Transactional
    public Integer deleteSoggettoGruppo(String gestUID) {
        logBeginInfo(className, gestUID);
        logEnd(className);
        return StringUtils.isNotBlank(gestUID) ? soggettoGruppoDAO.deleteSoggettoGruppoByUid(gestUID) : null;
    }

    private SoggettoGruppoDTO getSoggettoGruppo(@NotNull Long idGruppo, @NotNull Long idSoggettoIstanza, @NotNull Boolean flgCapoGruppo, @NotNull String cfAttore) {
        logBeginInfo(className, idSoggettoIstanza);
        SoggettoGruppoDTO soggettoGruppo = new SoggettoGruppoDTO();
        soggettoGruppo.setIdGruppoSoggetto(idGruppo);
        soggettoGruppo.setIdSoggettoIstanza(idSoggettoIstanza);
        soggettoGruppo.setFlgCapogruppo(flgCapoGruppo);
        soggettoGruppo.setGestAttoreIns(cfAttore);
        soggettoGruppo.setGestAttoreUpd(cfAttore);
        logEnd(className);
        return soggettoGruppo;
    }

}