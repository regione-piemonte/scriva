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

import it.csi.scriva.scrivabesrv.dto.IstanzaVincoloAutDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaVincoloAutExtendedDTO;

import java.util.List;

/**
 * The interface Istanza vincolo aut dao.
 *
 * @author CSI PIEMONTE
 */
public interface IstanzaVincoloAutDAO {


    /**
     * Load istanza vincolo autorizzazione by istanza list.
     *
     * @param idIstanza  the id istanza
     * @param codVincolo the cod vincolo
     * @return the list
     */
    List<IstanzaVincoloAutExtendedDTO> loadIstanzaVincoloAutorizzazioneByIstanza(Long idIstanza, String codVincolo);

    /**
     * Load istanza vincolo autorizzazione by id list.
     *
     * @param idIstanzaVincoloAut the id istanza vincolo aut
     * @return the list
     */
    List<IstanzaVincoloAutExtendedDTO> loadIstanzaVincoloAutorizzazioneById(Long idIstanzaVincoloAut);

    /**
     * Load istanza vincolo autorizzazione by id list.
     *
     * @param gestUID the gest uid
     * @return the list
     */
    List<IstanzaVincoloAutExtendedDTO> loadIstanzaVincoloAutorizzazioneById(String gestUID);

    /**
     * Save istanza vincolo autorizzazione by istanza long.
     *
     * @param istanzaVincoloAut the istanza vincolo aut
     * @return the long
     */
    Long saveIstanzaVincoloAutorizzazione(IstanzaVincoloAutDTO istanzaVincoloAut);

    /**
     * Update istanza vincolo autorizzazione by istanza integer.
     *
     * @param istanzaVincoloAut the istanza vincolo aut
     * @return the integer
     */
    Integer updateIstanzaVincoloAutorizzazione(IstanzaVincoloAutDTO istanzaVincoloAut);

    /**
     * Delete istanza vincolo autorizzazione by istanza integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    Integer deleteIstanzaVincoloAutorizzazioneByIstanza(String gestUID);

}