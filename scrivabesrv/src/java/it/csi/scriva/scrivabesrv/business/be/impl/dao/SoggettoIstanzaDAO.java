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

import it.csi.scriva.scrivabesrv.dto.SoggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoIstanzaExtendedDTO;

import java.util.List;
import java.util.Map;

/**
 * The interface Soggetto istanza dao.
 *
 * @author CSI PIEMONTE
 */
public interface SoggettoIstanzaDAO {

    /**
     * Load soggetti i stanze list.
     *
     * @return List<SoggettoIstanzaExtendedDTO>  list
     */
    List<SoggettoIstanzaExtendedDTO> loadSoggettiIstanze();

    /**
     * Load soggetto istanza list.
     *
     * @param id idSoggettoIstanza
     * @return List<SoggettoIstanzaExtendedDTO>  list
     */
    List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanza(Long id);

    /**
     * Load soggetto istanza by uid list.
     *
     * @param uid uidSoggettoIstanza
     * @return List<SoggettoIstanzaExtendedDTO>  list
     */
    List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaByUid(String uid);

    /**
     * Load soggetto istanza by id soggetto list.
     *
     * @param idSoggetto idSoggetto
     * @return List<SoggettoIstanzaExtendedDTO>  list
     */
    List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaByIdSoggetto(Long idSoggetto);

    /**
     * Load soggetto istanza by id soggetto padre list.
     *
     * @param idSoggettoPadre idSoggettoPadre
     * @return List<SoggettoIstanzaExtendedDTO>  list
     */
    List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaByIdSoggettoPadre(Long idSoggettoPadre);

    /**
     * Load soggetto istanza by id istanza list.
     *
     * @param idIstanza idIstanza
     * @return List<SoggettoIstanzaExtendedDTO>  list
     */
    List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaByIdIstanza(Long idIstanza);

    /**
     * Load soggetto istanza with delega by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaWithDelegaByIdIstanza(Long idIstanza);

    /**
     * Load soggetto istanza by id istanza and codice fiscale soggetto list.
     *
     * @param idIstanza             idIstanza
     * @param codiceFiscaleSoggetto codiceFiscaleSoggetto
     * @return List<SoggettoIstanzaExtendedDTO>  list
     */
    List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaByIdIstanzaAndCodiceFiscaleSoggetto(Long idIstanza, String codiceFiscaleSoggetto);

    /**
     * Load soggetto istanza by uid istanza and codice fiscale soggetto list.
     *
     * @param uidIstanza            uidIstanza
     * @param codiceFiscaleSoggetto codiceFiscaleSoggetto
     * @return List<SoggettoIstanzaExtendedDTO>  list
     */
    List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaByUidIstanzaAndCodiceFiscaleSoggetto(Long uidIstanza, String codiceFiscaleSoggetto);

    /**
     * Load soggetto istanza by codice fiscale soggetto list.
     *
     * @param codiceFiscaleSoggetto codiceFiscaleSoggetto
     * @return List<SoggettoIstanzaExtendedDTO>  list
     */
    List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaByCodiceFiscaleSoggetto(String codiceFiscaleSoggetto);

    /**
     * Load soggetto istanza by partita iva soggetto list.
     *
     * @param partitaIvaSoggetto partitaIvaSoggetto
     * @return List<SoggettoIstanzaExtendedDTO>  list
     */
    List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaByPartitaIvaSoggetto(String partitaIvaSoggetto);

    /**
     * Find by pk soggetto istanza dto.
     *
     * @param idSoggettoIstanza idSoggettoIstanza
     * @return SoggettoIstanzaDTO soggetto istanza dto
     */
    SoggettoIstanzaDTO findByPK(Long idSoggettoIstanza);

    /**
     * Save soggetto istanza long.
     *
     * @param dto SoggettoIstanzaDTO
     * @return id record salvato
     */
    Long saveSoggettoIstanza(SoggettoIstanzaDTO dto);

    /**
     * Update soggetto istanza integer.
     *
     * @param dto SoggettoIstanzaDTO
     * @return numero record inseriti
     */
    Integer updateSoggettoIstanza(SoggettoIstanzaDTO dto);

    /**
     * Delete soggetto istanza integer.
     *
     * @param uid uidSoggettoIstanza
     * @return numero record cancellati
     */
    Integer deleteSoggettoIstanza(String uid);

    /**
     * Save soggetti istanza map.
     *
     * @param dtos List<SoggettoIstanzaDTO>
     * @return mappa con id salvati
     */
    Map<String, Long> saveSoggettiIstanza(List<SoggettoIstanzaDTO> dtos);

    /**
     * Delete soggetto istanza by id integer.
     *
     * @param id idSoggettoIstanza
     * @return numero record cancellati
     */
    Integer deleteSoggettoIstanzaById(Long id);

    /**
     * Update istanza attore integer.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @param idIstanzaAttore   idIstanzaAttore
     * @param attoreUpd         attoreUpd
     * @return numero record aggiornati
     */
    Integer updateIstanzaAttore(Long idSoggettoIstanza, Long idIstanzaAttore, String attoreUpd);
}