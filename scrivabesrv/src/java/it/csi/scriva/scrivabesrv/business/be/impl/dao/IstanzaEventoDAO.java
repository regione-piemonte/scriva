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

import it.csi.scriva.scrivabesrv.dto.IstanzaEventoDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaEventoExtendedDTO;

import java.util.List;

/**
 * The interface Istanza evento dao.
 *
 * @author CSI PIEMONTE
 */
public interface IstanzaEventoDAO {

    /**
     * Load istanze eventi list.
     *
     * @return the list
     */
    List<IstanzaEventoExtendedDTO> loadIstanzeEventi();

    /**
     * Load istanza evento by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    List<IstanzaEventoExtendedDTO> loadIstanzaEventoByIdIstanza(Long idIstanza);

    /**
     * Find by pk istanza evento dto.
     *
     * @param idIstanzaEvento the id istanza evento
     * @return the istanza evento dto
     */
    IstanzaEventoDTO findByPK(Long idIstanzaEvento);

    /**
     * Save istanza evento long.
     *
     * @param dto the dto
     * @return the long
     */
    Long saveIstanzaEvento(IstanzaEventoDTO dto);

    /**
     * Update istanza evento integer.
     *
     * @param dto the dto
     * @return the integer
     */
    Integer updateIstanzaEvento(IstanzaEventoDTO dto);

    /**
     * Delete istanza evento integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    Integer deleteIstanzaEvento(String gestUID);

}