/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service;

import it.csi.scriva.scrivabesrv.dto.TipoEventoExtendedDTO;

import java.util.List;

/**
 * The interface Tipi evento service.
 *
 * @author CSI PIEMONTE
 */
public interface TipiEventoService {

    /**
     * Load configurazioni list.
     *
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    List<TipoEventoExtendedDTO> loadTipiEvento(String codComponenteApp);

    /**
     * Load tipo evento by code list.
     *
     * @param codTipoEvento    the cod tipo evento
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    List<TipoEventoExtendedDTO> loadTipoEventoByCode(String codTipoEvento, String codComponenteApp);

    /**
     * Gets tipo evento by stato istanza.
     *
     * @param idStatoIstanza the id stato istanza
     * @return the tipo evento by stato istanza
     */
    List<TipoEventoExtendedDTO> getTipoEventoByStatoIstanza(Long idStatoIstanza);

    /**
     * Gets tipo evento by code.
     *
     * @param codTipoEvento the cod tipo evento
     * @return the tipo evento by code
     */
    TipoEventoExtendedDTO getTipoEventoByCode(String codTipoEvento);

}