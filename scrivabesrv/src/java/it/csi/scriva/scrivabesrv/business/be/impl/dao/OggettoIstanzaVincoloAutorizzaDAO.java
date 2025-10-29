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

import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaVincoloAutorizzaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaVincoloAutorizzaExtendedDTO;

import java.util.Date;
import java.util.List;

/**
 * The interface Oggetto istanza vincolo autorizza dao.
 *
 * @author CSI PIEMONTE
 */
public interface OggettoIstanzaVincoloAutorizzaDAO {

    /**
     * Load oggetti istanza vincolo autorizza list.
     *
     * @param idOggettoVincoloAutorizza the id oggetto vincolo autorizza
     * @param idOggettoIstanza          the id oggetto istanza
     * @return the list
     */
    List<OggettoIstanzaVincoloAutorizzaExtendedDTO> loadOggettiIstanzaVincoloAutorizza(Long idOggettoVincoloAutorizza, Long idOggettoIstanza);

    /**
     * Save oggetto istanza vincolo autorizza long.
     *
     * @param dto the dto
     * @return the long
     */
    Long saveOggettoIstanzaVincoloAutorizza(OggettoIstanzaVincoloAutorizzaDTO dto);

    /**
     * Update oggetto istanza vincolo autorizza integer.
     *
     * @param dto the dto
     * @return the integer
     */
    Integer updateOggettoIstanzaVincoloAutorizza(OggettoIstanzaVincoloAutorizzaDTO dto);

    /**
     * Delete oggetto istanza vincolo autorizza integer.
     *
     * @param uid the uid
     * @return the integer
     */
    Integer deleteOggettoIstanzaVincoloAutorizza(String uid);

    /**
     * Delete oggetto istanza vincolo autorizza by id oggetto istanza integer.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the integer
     */
    Integer deleteOggettoIstanzaVincoloAutorizzaByIdOggettoIstanza(Long idOggettoIstanza);

    /**
     * Save copy oggetto istanza vincolo autorizza long.
     *
     * @param idAdempimento          the id adempimento
     * @param idOggettoIstanzaNew    the id oggetto istanza new
     * @param idOggettoIstanzaToCopy the id oggetto istanza to copy
     * @param gestDataIns            the gest data ins
     * @param gestAttoreIns          the gest attore ins
     * @return the long
     */
    Long saveCopyOggettoIstanzaVincoloAutorizza(Long idAdempimento, Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date gestDataIns, String gestAttoreIns);

}