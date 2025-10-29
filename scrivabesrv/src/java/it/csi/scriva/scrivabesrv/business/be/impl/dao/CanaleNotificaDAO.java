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

import it.csi.scriva.scrivabesrv.dto.CanaleNotificaDTO;
import it.csi.scriva.scrivabesrv.util.notification.model.enumeration.IndChannelTypeEnum;

import java.util.List;

/**
 * The interface Canale notifica dao.
 *
 * @author CSI PIEMONTE
 */
public interface CanaleNotificaDAO {

    /**
     * Load canali notifica list.
     *
     * @param componenteApp the componente app
     * @param flgAttivi     the flg attivi
     * @return the list
     */
    List<CanaleNotificaDTO> loadCanaliNotifica(String componenteApp, Boolean flgAttivi);

    /**
     * Load canale notifica list.
     *
     * @param idCanaleNotifica the id canale notifica
     * @return the list
     */
    List<CanaleNotificaDTO> loadCanaleNotifica(Long idCanaleNotifica);

    /**
     * Load canale notifica by code list.
     *
     * @param codCanaleNotifica the cod canale notifica
     * @return the list
     */
    List<CanaleNotificaDTO> loadCanaleNotificaByCodeList(String codCanaleNotifica);

    /**
     * Load canale notifica by code list.
     *
     * @param codCanaleNotificaList the cod canale notifica list
     * @param indTipoCanale         the ind tipo canale
     * @param componenteApp         the componente app
     * @return the list
     */
    List<CanaleNotificaDTO> loadCanaleNotificaByCodeList(List<String> codCanaleNotificaList, IndChannelTypeEnum indTipoCanale, String componenteApp);

}