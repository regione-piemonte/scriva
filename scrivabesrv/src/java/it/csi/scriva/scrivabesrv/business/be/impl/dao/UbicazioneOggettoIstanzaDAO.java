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

import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoIstanzaExtendedDTO;

import java.util.List;

/**
 * The interface Ubicazione oggetto istanza dao.
 *
 * @author CSI PIEMONTE
 */
public interface UbicazioneOggettoIstanzaDAO {

    /**
     * Load ubicazioni oggetti istanza list.
     *
     * @return List<UbicazioneOggettoIstanzaExtendedDTO>  list
     */
    List<UbicazioneOggettoIstanzaExtendedDTO> loadUbicazioniOggettiIstanza();

    /**
     * Load ubicazione oggetto istanza list.
     *
     * @param idUbicazioneOggettoIstanza idUbicazioneOggettoIstanza
     * @return List<UbicazioneOggettoIstanzaExtendedDTO>  list
     */
    List<UbicazioneOggettoIstanzaExtendedDTO> loadUbicazioneOggettoIstanza(Long idUbicazioneOggettoIstanza);

    /**
     * Load ubicazione oggetto istanza by id oggetto istanza list.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return List<UbicazioneOggettoIstanzaExtendedDTO>  list
     */
    List<UbicazioneOggettoIstanzaExtendedDTO> loadUbicazioneOggettoIstanzaByIdOggettoIstanza(Long idOggettoIstanza);

    /**
     * Load ubicazione oggetto istanza by fields list.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param idComune         the id comune
     * @param denIndirizzo     the den indirizzo
     * @param numCivico        the num civico
     * @param desLocalita      the des localita
     * @return the list
     */
    List<UbicazioneOggettoIstanzaExtendedDTO> loadUbicazioneOggettoIstanzaByFields(Long idOggettoIstanza, Long idComune, String denIndirizzo, String numCivico, String desLocalita);

    /**
     * Find by pk ubicazione oggetto istanza dto.
     *
     * @param idUbicazioneOggettoIstanza idUbicazioneOggettoIstanza
     * @return UbicazioneOggettoIstanzaDTO ubicazione oggetto istanza dto
     */
    UbicazioneOggettoIstanzaDTO findByPK(Long idUbicazioneOggettoIstanza);

    /**
     * Save ubicazione oggetto istanza long.
     *
     * @param dto UbicazioneOggettoIstanzaDTO
     * @return id record salvato
     */
    Long saveUbicazioneOggettoIstanza(UbicazioneOggettoIstanzaDTO dto);

    /**
     * Update ubicazione oggetto istanza integer.
     *
     * @param dto UbicazioneOggettoIstanzaDTO
     * @return numero record aggiornati
     */
    Integer updateUbicazioneOggettoIstanza(UbicazioneOggettoIstanzaDTO dto);

    /**
     * Delete ubicazione oggetto istanza integer.
     *
     * @param gestUID gestUID
     * @return numero record cancellati
     */
    Integer deleteUbicazioneOggettoIstanza(String gestUID);

    /**
     * Delete ubicazione oggetto istanza by id integer.
     *
     * @param idUbicazioneOggettoIstanza idUbicazioneOggettoIstanza
     * @return numero record cancellati
     */
    Integer deleteUbicazioneOggettoIstanzaById(Long idUbicazioneOggettoIstanza);

    /**
     * Delete ubicazione oggetto istanza by id oggetto istanza integer.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return numero record cancellati
     */
    Integer deleteUbicazioneOggettoIstanzaByIdOggettoIstanza(Long idOggettoIstanza);

    /**
     * Delete ubicazione oggetto istanza by uid oggetto istanza integer.
     *
     * @param uidOggettoIstanza uidOggettoIstanza
     * @return numero record cancellati
     */
    Integer deleteUbicazioneOggettoIstanzaByUidOggettoIstanza(String uidOggettoIstanza);

    /**
     * Delete ubicazioni oggetto istanza by not in id ubicazioni oggetto istanza integer.
     *
     * @param idOggettoIstanza           idOggettoIstanza
     * @param idUbicazioniOggettoIstanza idUbicazioniOggettoIstanza
     * @return numero record cancellati
     */
    Integer deleteUbicazioniOggettoIstanzaByNotInIdUbicazioniOggettoIstanza(Long idOggettoIstanza, List<Long> idUbicazioniOggettoIstanza);

    /**
     * Update ubicazione oggetto istanza flag.
     *
     * @param Long idIstanza
     * @param Long idOggetto
     * @param List<String> codIstatList
     * 
     */
	void updateUbicazioneOggettoIstanzaFlag(Long idIstanza, Long idOggetto, List<String> codIstatList);

}