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

import it.csi.scriva.scrivabesrv.dto.DestinatarioExtendedDTO;

import java.util.List;

/**
 * The interface Destinatario dao.
 *
 * @author CSI PIEMONTE
 */
public interface DestinatarioDAO {

    /**
     * Load destinatari list.
     *
     * @param componenteApp the componente app
     * @param flgAttivi     the flg attivi
     * @return the list
     */
    List<DestinatarioExtendedDTO> loadDestinatari(String componenteApp, Boolean flgAttivi);

    /**
     * Load destinatario list.
     *
     * @param idDestinatario the id destinatario
     * @return the list
     */
    List<DestinatarioExtendedDTO> loadDestinatario(Long idDestinatario);

    /**
     * Load destinatario by code list.
     *
     * @param codDestinatario the cod destinatario
     * @return the list
     */
    List<DestinatarioExtendedDTO> loadDestinatarioByCode(String codDestinatario);

}