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

import it.csi.scriva.scrivabesrv.dto.TipoEventoExtendedDTO;

import java.util.List;

/**
 * The interface Tipo evento dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoEventoDAO {

    /**
     * Load tipi evento list.
     *
     * @return the list
     */
    List<TipoEventoExtendedDTO> loadTipiEvento();

    /**
     * Load tipi evento list.
     *
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    List<TipoEventoExtendedDTO> loadTipiEvento(String codComponenteApp);

    /**
     * Load tipo evento by id list.
     *
     * @param idTipoEvento     the id tipo evento
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    List<TipoEventoExtendedDTO> loadTipoEventoById(Long idTipoEvento, String codComponenteApp);

    /**
     * Load tipo evento by cod tipo evento list.
     *
     * @param codTipoEvento    the cod tipo evento
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    List<TipoEventoExtendedDTO> loadTipoEventoByCode(String codTipoEvento, String codComponenteApp);

    /**
     * Load tipo evento by id stato istanza list.
     *
     * @param idStatoIstanza   the id stato istanza
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    List<TipoEventoExtendedDTO> loadTipoEventoByIdStatoIstanza(Long idStatoIstanza, String codComponenteApp);

}