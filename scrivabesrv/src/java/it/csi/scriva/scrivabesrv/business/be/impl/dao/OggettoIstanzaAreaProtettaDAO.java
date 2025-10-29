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

import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaAreaProtettaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaAreaProtettaExtendedDTO;

import java.util.Date;
import java.util.List;

/**
 * The interface Oggetto istanza area protetta dao.
 *
 * @author CSI PIEMONTE
 */
public interface OggettoIstanzaAreaProtettaDAO {

    /**
     * Load oggetti istanza area protetta list.
     *
     * @param idOggettoAreaProtetta the id oggetto area protetta
     * @param idOggettoIstanza      the id oggetto istanza
     * @return the list
     */
    List<OggettoIstanzaAreaProtettaExtendedDTO> loadOggettiIstanzaAreaProtetta(Long idOggettoAreaProtetta, Long idOggettoIstanza);

    /**
     * Save oggetto istanza categoria long.
     *
     * @param dto the dto
     * @return the long
     */
    Long saveOggettoIstanzaAreaProtetta(OggettoIstanzaAreaProtettaDTO dto);

    /**
     * Update oggetto istanza categoria integer.
     *
     * @param dto the dto
     * @return the integer
     */
    Integer updateOggettoIstanzaAreaProtetta(OggettoIstanzaAreaProtettaDTO dto);

    /**
     * Delete oggetto istanza categoria integer.
     *
     * @param uid the uid
     * @return the integer
     */
    Integer deleteOggettoIstanzaAreaProtetta(String uid);

    /**
     * Delete oggetto istanza categoria by id oggetto istanza integer.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the integer
     */
    Integer deleteOggettoIstanzaAreaProtettaByIdOggettoIstanza(Long idOggettoIstanza);

    /**
     * Save copy oggetto istanza area protetta long.
     *
     * @param idOggettoIstanzaNew    the id oggetto istanza new
     * @param idOggettoIstanzaToCopy the id oggetto istanza to copy
     * @param gestDataIns            the gest data ins
     * @param gestAttoreIns          the gest attore ins
     * @return the long
     */
    Long saveCopyOggettoIstanzaAreaProtetta(Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date gestDataIns, String gestAttoreIns);

}