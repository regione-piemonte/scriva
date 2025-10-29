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

import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoUbicazioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoUbicazionePraticaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.PraticaCollegataDTO;
import it.csi.scriva.scrivabesrv.dto.SearchOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.util.service.integration.anagamb.SedeOperativa;

import java.util.List;

/**
 * The interface Oggetti service.
 *
 * @author CSI PIEMONTE
 */
public interface OggettiService {

    /**
     * Load oggetti list.
     *
     * @return the list
     */
    List<OggettoUbicazioneExtendedDTO> loadOggetti();

    /**
     * Load oggetto by id list.
     *
     * @param idOggetto the id oggetto
     * @return the list
     */
    List<OggettoUbicazioneExtendedDTO> loadOggettoById(Long idOggetto);

    /**
     * Load oggetto by id comune list.
     *
     * @param idComune the id comune
     * @return the list
     */
    List<OggettoUbicazioneExtendedDTO> loadOggettoByIdComune(Long idComune);

    /**
     * Load oggetto by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    List<OggettoUbicazioneExtendedDTO> loadOggettoByIdIstanza(Long idIstanza);

    /**
     * Load oggetto by uid list.
     *
     * @param uidOggetto the uid oggetto
     * @return the list
     */
    List<OggettoUbicazioneExtendedDTO> loadOggettoByUID(String uidOggetto);

    /**
     * Search oggetto list.
     *
     * @param searchOggetto the search oggetto
     * @param attoreScriva  the attore scriva
     * @return the list
     */
    List<OggettoUbicazioneExtendedDTO> searchOggetto(SearchOggettoDTO searchOggetto, AttoreScriva attoreScriva);

    /**
     * Sets pratiche collegate oggetto.
     *
     * @param oggettoUbicazioneList the oggetto ubicazione list
     * @param annoPresentazione     the anno presentazione
     * @return the list
     */
    List<OggettoUbicazionePraticaExtendedDTO> setPraticheCollegateOggetto(List<OggettoUbicazioneExtendedDTO> oggettoUbicazioneList, Integer annoPresentazione);

    /**
     * Gets pratiche collegate oggetto.
     *
     * @param oggettoUbicazioneList the oggetto ubicazione list
     * @param annoPresentazione     the anno presentazione
     * @return the pratiche collegate oggetto
     */
    List<PraticaCollegataDTO> getPraticheCollegateOggetto(List<OggettoUbicazioneExtendedDTO> oggettoUbicazioneList, Integer annoPresentazione);

    /**
     * Save oggetto long.
     *
     * @param oggetto      the oggetto
     * @param attoreScriva the attore scriva
     * @return the long
     */
    Long saveOggetto(OggettoUbicazioneExtendedDTO oggetto, AttoreScriva attoreScriva);

    /**
     * Update oggetto long.
     *
     * @param oggetto      the oggetto
     * @param attoreScriva the attore scriva
     * @return the integer
     */
    Integer updateOggetto(OggettoUbicazioneExtendedDTO oggetto, AttoreScriva attoreScriva);

    /**
     * Delete oggetto long.
     *
     * @param uidOggetto the uid oggetto
     * @return the long
     */
    Integer deleteOggetto(String uidOggetto);

    /**
     * Gets oggetto ubicazione list.
     *
     * @param oggettiList the oggetti list
     * @return the oggetto ubicazione list
     */
    List<OggettoUbicazioneExtendedDTO> getOggettoUbicazioneList(List<OggettoExtendedDTO> oggettiList);

    /**
     * Gets sedi operative from anagamb.
     *
     * @param idSoggetti the id soggetti
     * @param idComune   the id comune
     * @return the sedi operative from anagamb
     */
    List<SedeOperativa> getSediOperativeFromAnagamb(List<Long> idSoggetti, Long idComune);

    /**
     * Save ubicazioni by sedi operative.
     *
     * @param searchOggetto     the search oggetto
     * @param sedeOperativaList the sede operativa list
     * @param attoreScriva      the attore scriva
     */
    void saveUbicazioniBySediOperative(SearchOggettoDTO searchOggetto, List<SedeOperativa> sedeOperativaList, AttoreScriva attoreScriva);

    /**
     * Save ubicazioni oggetto.
     *
     * @param idOggetto             the id oggetto
     * @param ubicazioneOggettoList the ubicazione oggetto list
     * @param attoreScriva          the attore scriva
     * @return the long
     */
    Long saveUbicazioniOggetto(Long idOggetto, List<UbicazioneOggettoExtendedDTO> ubicazioneOggettoList, AttoreScriva attoreScriva);

    /**
     * Update ubicazioni oggetto integer.
     *
     * @param idOggetto             the id oggetto
     * @param ubicazioneOggettoList the ubicazione oggetto list
     * @param attoreScriva          the attore scriva
     * @return the integer
     */
    Integer updateUbicazioniOggetto(Long idOggetto, List<UbicazioneOggettoExtendedDTO> ubicazioneOggettoList, AttoreScriva attoreScriva);

    /**
     * Validate dto error dto.
     *
     * @param oggetto        the oggetto
     * @param codAdempimento the cod adempimento
     * @param isUpdate       the is update
     * @return the error dto
     */
    ErrorDTO validateDTO(OggettoUbicazioneExtendedDTO oggetto, String codAdempimento, Boolean isUpdate);
}