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

import it.csi.scriva.scrivabesrv.dto.IstanzaResponsabileDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaStatoDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaStatoExtendedDTO;

import java.util.List;

/**
 * The interface Istanza stato dao.
 *
 * @author CSI PIEMONTE
 */
public interface IstanzaStatoDAO {

    /**
     * Load istanza stati list.
     *
     * @return List<IstanzaStatoExtendedDTO>   list
     */
    List<IstanzaStatoExtendedDTO> loadIstanzaStati();

    /**
     * Load istanza stati by istanza list.
     *
     * @param idIstanza idIstanza
     * @return List<IstanzaStatoExtendedDTO>   list
     */
    List<IstanzaStatoExtendedDTO> loadIstanzaStatiByIstanza(Long idIstanza);

    /**
     * Save istanza stato long.
     *
     * @param dto IstanzaStatoDTO
     * @return id record salvato
     */
    Long saveIstanzaStato(IstanzaStatoDTO dto);

}