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

import it.csi.scriva.scrivabesrv.dto.ConfigurazioneNotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.util.notification.model.enumeration.IndChannelTypeEnum;

import java.util.List;

/**
 * The interface Configurazione notifica dao.
 *
 * @author CSI PIEMONTE
 */
public interface ConfigurazioneNotificaDAO {


    /**
     * Load configurazioni notifica list.
     *
     * @param flgAttivi the flg attivi
     * @return the list
     */
    List<ConfigurazioneNotificaExtendedDTO> loadConfigurazioniNotifica(Boolean flgAttivi);

    /**
     * Load configurazione notifica list.
     *
     * @param idNotificaConfigurazione the id notifica configurazione
     * @param flgAttivi                the flg attivi
     * @return the list
     */
    List<ConfigurazioneNotificaExtendedDTO> loadConfigurazioneNotifica(Long idNotificaConfigurazione, Boolean flgAttivi);

    /**
     * Load configurazione notifica by cod adempimento list.
     *
     * @param codAdempimento the cod adempimento
     * @param flgAttivi      the flg attivi
     * @return the list
     */
    List<ConfigurazioneNotificaExtendedDTO> loadConfigurazioneNotificaByCodAdempimento(String codAdempimento, Boolean flgAttivi);

    /**
     * Load configurazione notifica by code list list.
     *
     * @param codTipoEvento         the cod tipo evento
     * @param idIstanza             the id istanza
     * @param codCanaleNotificaList the cod canale notifica list
     * @param indTipoCanale         the ind tipo canale
     * @param componenteApp         the componente app
     * @param flgAttivi             the flg attivi
     * @return the list
     */
    List<ConfigurazioneNotificaExtendedDTO> loadConfigurazioneNotificaByCodeTipoEvento(String codTipoEvento, Long idIstanza, List<String> codCanaleNotificaList, IndChannelTypeEnum indTipoCanale, String componenteApp, Boolean flgAttivi);

}