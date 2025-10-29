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

import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaNatura2000DTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaNatura2000ExtendedDTO;

import java.util.Date;
import java.util.List;

/**
 * The interface Oggetto istanza natura 2000 dao.
 *
 * @author CSI PIEMONTE
 */
public interface OggettoIstanzaNatura2000DAO {

    /**
     * Load oggetti istanza natura 2000 list.
     *
     * @param idOggettoNatura2000 the id oggetto natura 2000
     * @param idOggettoIstanza    the id oggetto istanza
     * @return the list
     */
    List<OggettoIstanzaNatura2000ExtendedDTO> loadOggettiIstanzaNatura2000(Long idOggettoNatura2000, Long idOggettoIstanza);

    /**
     * Save oggetto istanza natura 2000 long.
     *
     * @param dto the dto
     * @return the long
     */
    Long saveOggettoIstanzaNatura2000(OggettoIstanzaNatura2000DTO dto);

    /**
     * Update oggetto istanza natura 2000 integer.
     *
     * @param dto the dto
     * @return the integer
     */
    Integer updateOggettoIstanzaNatura2000(OggettoIstanzaNatura2000DTO dto);

    /**
     * Delete oggetto istanza natura 2000 integer.
     *
     * @param uid the uid
     * @return the integer
     */
    Integer deleteOggettoIstanzaNatura2000(String uid);

    /**
     * Delete oggetto istanza natura 2000 by id oggetto istanza integer.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the integer
     */
    Integer deleteOggettoIstanzaNatura2000ByIdOggettoIstanza(Long idOggettoIstanza);

    /**
     * Save copy geo area oggetto istanza long.
     *
     * @param idOggettoIstanzaNew    the id oggetto istanza new
     * @param idOggettoIstanzaToCopy the id oggetto istanza to copy
     * @param gestDataIns            the gest data ins
     * @param gestAttoreIns          the gest attore ins
     * @return the long
     */
    Long saveCopyOggettoIstanzaNatura2000(Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date gestDataIns, String gestAttoreIns);

}