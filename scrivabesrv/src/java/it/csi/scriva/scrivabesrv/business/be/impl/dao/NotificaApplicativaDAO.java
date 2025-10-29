/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl.dao;

import it.csi.scriva.scrivabesrv.dto.NotificaApplicativaDTO;

import java.util.Date;
import java.util.List;

/**
 * The interface Notifica applicativa dao.
 *
 * @author CSI PIEMONTE
 */
public interface NotificaApplicativaDAO {

    /**
     * Load notifica applicativa list.
     *
     * @return the list
     */
    List<NotificaApplicativaDTO> loadNotificaApplicativa();

    /**
     * Load notifica applicativa list.
     *
     * @param idNotificaApplicativa the id notifica applicativa
     * @param codStatoNotifica      the cod stato notifica
     * @param idIstanza             the id istanza
     * @param codComponenteApp      the cod componente app
     * @param cfDestinatario        the cf destinatario
     * @param rifCanale             the rif canale
     * @return the list
     */
    // List<NotificaApplicativaDTO> loadNotificaApplicativa(Long idNotificaApplicativa, String codStatoNotifica, Long idIstanza, String codComponenteApp, String cfDestinatario, String rifCanale);


    /**
     * Load notifica applicativa list per la ricerca.
     *
     * @param codComponenteApp      the cod componente app
     * @param cfDestinatario        the cf destinatario
     * @param ancheCancellate       the anche cancellate
     * @param idNotificaApplicativa the id notifica applicativa
     * @param codStatoNotifica      the cod stato notifica
     * @param codIstanza            the cod istanza
     * @param idAdempimentoList     the id adempimento list
     * @param dataInizio            the data inizio
     * @param dataFine              the data fine
     * @param offset                the offset
     * @param limit                 the limit
     * @param sort                  the sort
     * @return list list
     */
    List<NotificaApplicativaDTO> loadNotificaApplicativa(String codComponenteApp, String cfDestinatario,
                                                         Boolean ancheCancellate, Long idNotificaApplicativa, String codStatoNotifica, String codIstanza,
                                                         List<Long> idAdempimentoList, Date dataInizio, Date dataFine, String offset, String limit, String sort);

    /**
     * Save notifica applicativa long.
     *
     * @param notifica the notifica
     * @return the long
     */
    Long saveNotificaApplicativa(NotificaApplicativaDTO notifica);

    /**
     * Update notifica applicativa integer.
     *
     * @param notificaApplicativa the notifica applicativa
     * @return the integer
     */
    Integer updateNotificaApplicativa(NotificaApplicativaDTO notificaApplicativa);

    /**
     * Update notifiche tutte lette integer.
     *
     * @param codiceFiscale    the codice fiscale
     * @param codComponenteApp the cod componente app
     * @return the integer
     */
    Integer updateNotificheTutteLette(String codiceFiscale, String codComponenteApp);

    /**
     * Update notifiche tutte lette integer.
     *
     * @param codComponenteApp  the cod componente app
     * @param cfDestinatario    the cf destinatario
     * @param codStatoNotifica  the cod stato notifica
     * @param codIstanza        the cod istanza
     * @param idAdempimentoList the id adempimento list
     * @param dataInizio        the data inizio
     * @param dataFine          the data fine
     * @return the integer
     */
    Integer updateNotificheTutteLette(String codComponenteApp, String cfDestinatario,
                                      String codStatoNotifica, String codIstanza,
                                      List<Long> idAdempimentoList, Date dataInizio, Date dataFine);

    /**
     * Delete notifica applicativa.
     *
     * @param gestUid the gest uid
     * @return the integer
     */
    Integer deleteNotificaApplicativa(String gestUid);

}