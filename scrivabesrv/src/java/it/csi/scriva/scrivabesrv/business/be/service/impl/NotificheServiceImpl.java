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
import it.csi.scriva.scrivabesrv.business.be.impl.dao.NotificaApplicativaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.NotificheService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.LoadNotificheCountDTO;
import it.csi.scriva.scrivabesrv.dto.NotificaApplicativaDTO;
import it.csi.scriva.scrivabesrv.dto.SearchNotificheDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Notifiche  api service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class NotificheServiceImpl extends BaseApiServiceImpl implements NotificheService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private NotificaApplicativaDAO notificaApplicativaDAO;


    @Override
    public List<NotificaApplicativaDTO> loadNotifiche(String codiceFiscale, String codComponenteApp, String offset, String limit, String sort) {
        logBeginInfo(className, "\n codiceFiscale : [" + codiceFiscale + "]"
                + "\n codComponenteApp : " + codComponenteApp
                + "\n offset : " + offset + "\n limit : " + limit + "\n sort : " + sort);
        List<NotificaApplicativaDTO> notificheList = null;
        try {

            notificheList = notificaApplicativaDAO.loadNotificaApplicativa(codComponenteApp, codiceFiscale, false, null,
                    null, null, null, null, null, offset, limit, sort);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return notificheList;
    }


    @Override
    public List<NotificaApplicativaDTO> loadNotifiche(
            String codiceFiscale,
            String codComponenteApp,
            SearchNotificheDTO searchCriteria,
            String offset, String limit, String sort
    ) {

        logBeginInfo(className, "\n codiceFiscale : [" + codiceFiscale + "]"
                + "\n codComponenteApp : " + codComponenteApp
                + "\n searchCriteria :\n" + searchCriteria
                + "\n offset : " + offset + "\n limit : " + limit + "\n sort : " + sort);

        List<NotificaApplicativaDTO> notificheList = null;

        try {
            notificheList = notificaApplicativaDAO.loadNotificaApplicativa(
                    codComponenteApp,
                    codiceFiscale,
                    "NC".equalsIgnoreCase(searchCriteria.getCodStatoNotifica()) || "NLNC".equalsIgnoreCase(searchCriteria.getCodStatoNotifica())? Boolean.FALSE : Boolean.TRUE,
                    searchCriteria.getIdNotificaApplicativa(),
                    searchCriteria.getCodStatoNotifica(),
                    searchCriteria.getNumIstanza(),
                    searchCriteria.getIdAdempimentoList(),
                    searchCriteria.getDataInizio(),
                    searchCriteria.getDataFine(),
                    offset, limit, sort
            );
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return notificheList;
    }

    /**
     * Update notifiche tutte lette integer.
     *
     * @param notificaApplicativa the notifica applicativa
     * @param attoreScriva        the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateNotifica(NotificaApplicativaDTO notificaApplicativa, AttoreScriva attoreScriva) {
        logBeginInfo(className, notificaApplicativa);
        notificaApplicativa.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
        return notificaApplicativaDAO.updateNotificaApplicativa(notificaApplicativa);
    }

    /**
     * Update notifica integer.
     *
     * @param notificaApplicativaList the notifica applicativa list
     * @param attoreScriva            the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateNotifica(List<NotificaApplicativaDTO> notificaApplicativaList, AttoreScriva attoreScriva) {
        logBeginInfo(className, "attoreScriva : \n" + attoreScriva + "\n"
                + "notificaApplicativaList : " + notificaApplicativaList);
        Integer result = 0;
        for (NotificaApplicativaDTO notificaApplicativa : notificaApplicativaList) {
            result += updateNotifica(notificaApplicativa, attoreScriva);
        }
        return result;
    }

    /**
     * Update notifiche tutte lette integer.
     *
     * @param attoreScriva the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateNotificheTutteLette(AttoreScriva attoreScriva) {
        logBeginInfo(className, "attoreScriva : \n" + attoreScriva + "\n");
        return notificaApplicativaDAO.updateNotificheTutteLette(attoreScriva.getCodiceFiscale(), attoreScriva.getComponente());
    }

    /**
     * Update notifiche tutte lette integer.
     *
     * @param attoreScriva   the attore scriva
     * @param searchCriteria the search criteria
     * @return the integer
     */
    @Override
    public Integer updateNotificheTutteLette(AttoreScriva attoreScriva, SearchNotificheDTO searchCriteria) {
        logBeginInfo(className, "attoreScriva : \n" + attoreScriva + "\n"
                + "searchCriteria : \n" + searchCriteria + "\n");
        Integer result = 0;
        //String codComponenteApp, String cfDestinatario, String codStatoNotifica, String codIstanza, List<Long> idAdempimentoList, Date dataInizio, Date dataFine
        List<NotificaApplicativaDTO> notificaApplicativaList = notificaApplicativaDAO.loadNotificaApplicativa(
                attoreScriva.getComponente(),
                attoreScriva.getCodiceFiscale(),
                Boolean.FALSE,
                searchCriteria.getIdNotificaApplicativa(),
                searchCriteria.getCodStatoNotifica(),
                searchCriteria.getNumIstanza(),
                searchCriteria.getIdAdempimentoList(),
                searchCriteria.getDataInizio(),
                searchCriteria.getDataFine(),
                null, null, null
        );
        // filtro per quelli non letti e non cancellati
        notificaApplicativaList = notificaApplicativaList.stream()
                .filter(not -> not.getDataLettura() == null && not.getDataCancellazione() == null)
                .collect(Collectors.toList());
        for (NotificaApplicativaDTO notificaApplicativa : notificaApplicativaList) {
            notificaApplicativa.setDataLettura(new java.sql.Timestamp(System.currentTimeMillis()));
            notificaApplicativa.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
            result += updateNotifica(notificaApplicativa, attoreScriva);
        }
        return result;
    }

    /**
     * Gets pagination header.
     *
     * @param notificaApplicativaList the notifica applicativa list
     * @return the pagination header
     */
    @Override
    public LoadNotificheCountDTO getNotificheCountHeader(List<NotificaApplicativaDTO> notificaApplicativaList) {
        logBeginInfo(className, notificaApplicativaList);
        LoadNotificheCountDTO searchCount = new LoadNotificheCountDTO();
        searchCount.setCancellate((int) notificaApplicativaList.stream().filter(obj -> obj.getDataCancellazione() != null).count());
        searchCount.setLette((int) notificaApplicativaList.stream().filter(obj -> obj.getDataLettura() != null).count());
        searchCount.setTutte(notificaApplicativaList.size());
        searchCount.setNonLette(notificaApplicativaList.size() - searchCount.getLette());
        logEnd(className);
        return searchCount;
    }

}