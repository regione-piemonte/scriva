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

import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaGeecoDTO;
import it.csi.scriva.scrivabesrv.dto.SearchOggettoDTO;

import java.util.List;

/**
 * The interface Oggetto istanza dao.
 *
 * @author CSI PIEMONTE
 */
public interface OggettoIstanzaDAO {

    /**
     * Load oggetti istanza list.
     *
     * @return List<OggettoIstanzaExtendedDTO>       list
     */
    List<OggettoIstanzaExtendedDTO> loadOggettiIstanza();

    /**
     * Load oggetto istanza by id list.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return List<OggettoIstanzaExtendedDTO>       list
     */
    List<OggettoIstanzaExtendedDTO> loadOggettoIstanzaById(Long idOggettoIstanza);

    /**
     * Load oggetto istanza by uid list.
     *
     * @param gestUID gestUID
     * @return List<OggettoIstanzaExtendedDTO>       list
     */
    List<OggettoIstanzaExtendedDTO> loadOggettoIstanzaByUID(String gestUID);

    /**
     * Load oggetto istanza by id oggetto list.
     *
     * @param idOggetto idOggetto
     * @return List<OggettoIstanzaExtendedDTO>       list
     */
    List<OggettoIstanzaExtendedDTO> loadOggettoIstanzaByIdOggetto(Long idOggetto);

    /**
     * Load oggetto istanza by id istanza list.
     *
     * @param idIstanza idIstanza
     * @return List<OggettoIstanzaExtendedDTO>       list
     */
    List<OggettoIstanzaExtendedDTO> loadOggettoIstanzaByIdIstanza(Long idIstanza);

    /**
     * Load oggetto istanza simple by id istanza list.
     *
     * @param idIstanza idIstanza
     * @return List<OggettoIstanzaDTO>       list
     */
    List<OggettoIstanzaDTO> loadOggettoIstanzaSimpleByIdIstanza(Long idIstanza);

    /**
     * Search oggetto istanza list.
     *
     * @param searchOggettoIstanza SearchOggettoDTO
     * @return List<OggettoIstanzaExtendedDTO>       list
     */
    List<OggettoIstanzaExtendedDTO> searchOggettoIstanza(SearchOggettoDTO searchOggettoIstanza);

    /**
     * Find by pk oggetto istanza dto.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return OggettoIstanzaDTO oggetto istanza dto
     */
    OggettoIstanzaDTO findByPK(Long idOggettoIstanza);

    /**
     * Save oggetto istanza long.
     *
     * @param dto OggettoIstanzaDTO
     * @return id record salvato
     */
    Long saveOggettoIstanza(OggettoIstanzaDTO dto);

    /**
     * Update oggetto istanza integer.
     *
     * @param dto OggettoIstanzaDTO
     * @return numero record aggiornati
     */
    Integer updateOggettoIstanza(OggettoIstanzaDTO dto);

    /**
     * Update ind geo stato integer.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @param indGeoStato      indGeoStato
     * @return numero record aggiornati
     */
    Integer updateIndGeoStato(Long idOggettoIstanza, String indGeoStato);

    /**
     * Delete oggetto istanza integer.
     *
     * @param gestUID gestUID
     * @return numero record cancellati
     */
    Integer deleteOggettoIstanza(String gestUID);

    /**
     * Delete oggetto by id integer.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return numero record cancellati
     */
    Integer deleteOggettoById(Long idOggettoIstanza);

    /**
     * Gets oggetto istanza geeco.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return OggettoIstanzaGeecoDTO oggetto istanza geeco
     */
    OggettoIstanzaGeecoDTO getOggettoIstanzaGeeco(Long idOggettoIstanza);

    /**
     * Gets oggetto istanza with last geo ref.
     *
     * @param idOggetto idOggetto
     * @return id record trovato
     */
    Long getOggettoIstanzaWithLastGeoRef(Long idOggetto);

    /**
     * Gets oggetto istanza update oggetto.
     *
     * @param idOggetto the id oggetto
     * @return the oggetto istanza update oggetto
     */
    Long getLastIdOggettoIstanzaRefOggetto(Long idOggetto);

    /**
     * Update flg geo modificato integer.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param value            the value
     * @return the integer
     */
    Integer updateFlgGeoModificatoByIdOggIst(Long idOggettoIstanza, Boolean value);

    /**
     * Update flg geo modificato by id ist integer.
     *
     * @param idIstanza the id istanza
     * @param value     the value
     * @return the integer
     */
    Integer updateFlgGeoModificatoByIdIst(Long idIstanza, Boolean value);

    /**
     * Gets oggetto istanza geeco.
     *
     * @param idIstanza            the id istanza
     * @param idOggettoIstanzaList the id oggetto istanza list
     * @param idLayerList          the id layer list
     * @param indLivelloEstrazione the ind livello estrazione
     * @return the oggetto istanza geeco
     */
    List<OggettoIstanzaGeecoDTO> getOggettoIstanzaGeeco(Long idIstanza, List<Long> idOggettoIstanzaList, List<Long> idLayerList, Long indLivelloEstrazione);


}