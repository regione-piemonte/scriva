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

import it.csi.scriva.scrivabesrv.dto.CatastoUbicazioneOggettoIstanzaDTO;

import java.util.Date;
import java.util.List;


/**
 * The interface Catasto ubicazione oggetto istanza dao.
 *
 * @author CSI PIEMONTE
 */
public interface CatastoUbicazioneOggettoIstanzaDAO {

    /**
     * Load catasto ubicazione oggetto istanza list.
     *
     * @param idCatastoUbicazioneOggettoIstanza the id catasto ubicazione oggetto istanza
     * @param idUbicazioneOggettoIstanza        the id ubicazione oggetto istanza
     * @param idOggettoIstanza                  the id oggetto istanza
     * @param idIstanza                         the id istanza
     * @return the list
     */
    List<CatastoUbicazioneOggettoIstanzaDTO> loadCatastoUbicazioneOggettoIstanza(Long idCatastoUbicazioneOggettoIstanza, Long idUbicazioneOggettoIstanza, Long idOggettoIstanza, Long idIstanza);

    /**
     * Save catasto ubicazione oggetto istanza long.
     *
     * @param dto the dto
     * @return the long
     */
    Long saveCatastoUbicazioneOggettoIstanza(CatastoUbicazioneOggettoIstanzaDTO dto);

    /**
     * Update catasto ubicazione oggetto istanza integer.
     *
     * @param dto the dto
     * @return the integer
     */
    Integer updateCatastoUbicazioneOggettoIstanza(CatastoUbicazioneOggettoIstanzaDTO dto);

    /**
     * Delete catasto ubicazione oggetto istanza integer.
     *
     * @param uid the uid
     * @return the integer
     */
    Integer deleteCatastoUbicazioneOggettoIstanza(String uid);

    /**
     * Delete catasto ubicazione oggetto istanza by id oggetto istanza integer.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the integer
     */
    Integer deleteCatastoUbicazioneOggettoIstanzaByIdOggettoIstanza(Long idOggettoIstanza);

    /**
     * Delete catasto ubicazione oggetto istanza by id ubicazione oggetto istanza integer.
     *
     * @param idUbicazioneOggettoIstanza the id ubicazione oggetto istanza
     * @return the integer
     */
    Integer deleteCatastoUbicazioneOggettoIstanzaByIdUbicazioneOggettoIstanza(Long idUbicazioneOggettoIstanza);

    /**
     * Save copy catasto ubicazione oggetto istanza long.
     *
     * @param idUbicazioneOggettoIstanzaNew    the id ubicazione oggetto istanza new
     * @param idUbicazioneOggettoIstanzaToCopy the id ubicazione oggetto istanza to copy
     * @param gestDataIns                      the gest data ins
     * @param gestAttoreIns                    the gest attore ins
     * @return the long
     */
    Long saveCopyCatastoUbicazioneOggettoIstanza(Long idUbicazioneOggettoIstanzaNew, Long idUbicazioneOggettoIstanzaToCopy, Date gestDataIns, String gestAttoreIns);

}