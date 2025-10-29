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

import it.csi.scriva.scrivabesrv.dto.DestinatarioCanaleExtendedDTO;

import java.util.List;

/**
 * The interface Destinatario canale dao.
 *
 * @author CSI PIEMONTE
 */
public interface DestinatarioCanaleDAO {

    /**
     * Load destinari canale list.
     *
     * @param componenteApp the componente app
     * @param flgAttivi     the flg attivi
     * @return the list
     */
    List<DestinatarioCanaleExtendedDTO> loadDestinatariCanale(String componenteApp, Boolean flgAttivi);

    /**
     * Load destinatario canale list.
     *
     * @param idDestinatarioCanale the id destinatario canale
     * @return the list
     */
    List<DestinatarioCanaleExtendedDTO> loadDestinatarioCanale(Long idDestinatarioCanale);

    /**
     * Load destinario canale by id destinatario list.
     *
     * @param idDestinatario the id destinatario
     * @return the list
     */
    List<DestinatarioCanaleExtendedDTO> loadDestinarioCanaleByIdDestinatario(Long idDestinatario);

    /**
     * Load destinatario canale by code destinatario list.
     *
     * @param codDestinatario the cod destinatario
     * @return the list
     */
    List<DestinatarioCanaleExtendedDTO> loadDestinatarioCanaleByCodeDestinatario(String codDestinatario);

    /**
     * Load destinatario canale by code tipo destinatario list.
     *
     * @param codTipoDestinatario the cod tipo destinatario
     * @return the list
     */
    List<DestinatarioCanaleExtendedDTO> loadDestinatarioCanaleByCodeTipoDestinatario(String codTipoDestinatario);

}