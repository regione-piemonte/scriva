/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.notification.service;

import it.csi.scriva.scrivabesrv.dto.ConfigurazioneNotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoNotificaEventoExtendedDTO;

import java.util.List;
import java.util.Map;

/**
 * The interface Template service.
 *
 * @author CSI PIEMONTE
 */
public interface TemplateNotificationService {

    /**
     * Gets tipo notifica evento.
     *
     * @param codTipoEvento the cod tipo evento
     * @return the tipo notifica evento
     */
    List<TipoNotificaEventoExtendedDTO> getTipoNotificaEvento(String codTipoEvento);


    /**
     * Creates the notifiche from conf.
     *
     * @param listConfigNotifica the list config notifica
     * @param isIstanza          the is istanza
     * @param codCanaleNotifica  the cod canale notifica
     * @param rifCanaleNotifica  the rif canale notifica
     * @param cfAttoreInLinea    the cf attore in linea
     * @return the list
     */
    List<NotificaExtendedDTO> createNotificheFromConf(List<ConfigurazioneNotificaExtendedDTO> listConfigNotifica,
			Long isIstanza,
			String codCanaleNotifica,
			String rifCanaleNotifica,
			String cfAttoreInLinea);

    /**
     * Gets configurazione notifica.
     *
     * @param codTipoEvento    the cod tipo evento
     * @param idIstanza        the id istanza
     * @param codComponenteApp the cod componente app
     * @return the configurazione notifica
     */
    List<ConfigurazioneNotificaExtendedDTO> getConfigurazioneNotifica(String codTipoEvento, Long idIstanza, String codComponenteApp);

    /**
     * Gets configurazione allegato notifica.
     *
     * @param configurazioneNotificaList the configurazione notifica list
     * @return the configurazione notifica
     */
    List<ConfigurazioneNotificaExtendedDTO> getConfigurazioneAllegatoNotifica(List<ConfigurazioneNotificaExtendedDTO> configurazioneNotificaList);

    /**
     * Verify place holder.
     *
     * @param notificaList the notifica list
     * @param uidRichiesta the uid richiesta
     */
    void verifyPlaceHolder(List<NotificaExtendedDTO> notificaList, String uidRichiesta);

    /**
     * Gets configurazioni.
     *
     * @return the configurazioni
     */
    Map<String, String> getConfigurazioni();


    /**
     * Gets conf notify all.
     *
     * @return the conf notify all
     */
    String getConfNotifyAll();

    /**
     * Gets conf notify canale.
     *
     * @return the conf notify canale
     */
    Map<String, String> getConfNotifyCanale();

    /**
     * Gets conf notify canale scriva.
     *
     * @return the conf notify canale scriva
     */
    Map<String, String> getConfNotifyCanaleScriva();

    /**
     * Gets conf server posta.
     *
     * @return the conf server posta
     */
    Map<String, String> getConfServerPosta();

    /**
     * Gets conf server pec.
     *
     * @return the conf server pec
     */
    Map<String, String> getConfServerPEC();

    /**
     * @param configurazioneNotificaList
     * @param notificaList
     * @param gestUidRichiesta
     * @param desTipoRichiesta
     * @return
     */
    List<NotificaExtendedDTO> setAllegatiFromConf(List<ConfigurazioneNotificaExtendedDTO> configurazioneNotificaList, List<NotificaExtendedDTO> notificaList, String gestUidRichiesta, String desTipoRichiesta);
}