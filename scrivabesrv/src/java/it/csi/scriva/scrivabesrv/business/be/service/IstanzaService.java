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

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoAllegatoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaResponsabileExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaStatoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ReferenteIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoOggettoAppExtendedDTO;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;

/**
 * The interface Istanza service.
 *
 * @author CSI PIEMONTE
 */
public interface IstanzaService {

    /**
     * Gets istanza.
     *
     * @param idIstanza the id istanza
     * @return the istanza
     */
    IstanzaExtendedDTO getIstanza(Long idIstanza);

    /**
     * Update istanza istanza extended dto.
     *
     * @param flgCreaPratica the flg crea pratica
     * @param istanza        the istanza
     * @param attoreScriva 
     * @return the istanza extended dto
     * @throws GenericException the generic exception
     */
    IstanzaExtendedDTO updateIstanza(Boolean flgCreaPratica, IstanzaExtendedDTO istanza, AttoreScriva attoreScriva) throws GenericException;

    /**
     * Update json data istanza istanza extended dto.
     *
     * @param istanza the istanza
     * @return the istanza extended dto
     */
    IstanzaExtendedDTO updateJsonDataIstanza(IstanzaExtendedDTO istanza);

    /**
     * Save istanza attore long.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the long
     */
    Long saveIstanzaAttore(Long idIstanza, AttoreScriva attoreScriva);

    /**
     * Gets cod tipologia allegato.
     *
     * @param idIstanza the id istanza
     * @return the cod tipologia allegato
     */
    String getCodTipologiaAllegato(Long idIstanza);

    /**
     * Gets adempimento tipo allegato.
     *
     * @param codAllegato   the cod allegato
     * @param idAdempimento the id adempimento
     * @return the adempimento tipo allegato
     */
    AdempimentoTipoAllegatoExtendedDTO getAdempimentoTipoAllegato(String codAllegato, Long idAdempimento);

    /**
     * Upload allegati istanza long.
     *
     * @param idIstanza            the id istanza
     * @param file                 the file
     * @param fileName             the file name
     * @param codCategoriaAllegato the cod categoria allegato
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param attoreScriva         the attore scriva
     * @return the long
     * @throws GenericException the generic exception
     */
    Long uploadAllegatiIstanza(Long idIstanza, File file, String fileName, String codCategoriaAllegato, String codTipologiaAllegato, AttoreScriva attoreScriva) throws GenericException;

    /**
     * Archivia allegati istanza.
     *
     * @param istanza              the istanza
     * @param file                 the file
     * @param fileName             the file name
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param cancella             the cancella
     * @param attoreScriva         the attore scriva
     * @throws GenericException the generic exception
     */
    void archiviaAllegatiIstanza(IstanzaExtendedDTO istanza, File file, String fileName, String codTipologiaAllegato, Boolean cancella, AttoreScriva attoreScriva) throws GenericException;

    /**
     * Create codice pratica string.
     *
     * @param idIstanza the id istanza
     * @return the string
     * @throws GenericException the generic exception
     */
    IstanzaExtendedDTO createCodicePratica(Long idIstanza, HttpHeaders httpHeaders, AttoreScriva attoreScriva) throws GenericException;

    /**
     * Validate dto error dto.
     *
     * @param istanza  the istanza
     * @param isUpdate the is update
     * @return the error dto
     */
    ErrorDTO validateDTO(IstanzaExtendedDTO istanza, Boolean isUpdate);

    /**
     * Validate referente dto error dto.
     *
     * @param referente the referente
     * @return the error dto
     */
    ErrorDTO validateReferenteDTO(ReferenteIstanzaDTO referente);

    /**
     * Save storico boolean.
     *
     * @param idStatoIstanza the id stato istanza
     * @return the boolean
     */
    boolean saveStorico(Long idStatoIstanza);

    /**
     * Load istanza stato list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    List<IstanzaStatoExtendedDTO> loadIstanzaStato(Long idIstanza);

    /**
     * Patch data pubblicazione istanza list.
     *
     * @param idIstanza         the id istanza
     * @param dataPubblicazione the data pubblicazione
     * @param attoreScriva      the attore scriva
     * @return the list
     */
    List<IstanzaExtendedDTO> patchDataPubblicazioneIstanza(Long idIstanza, Date dataPubblicazione, AttoreScriva attoreScriva);

    /**
     * Load istanza responsabili list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    List<IstanzaResponsabileExtendedDTO> loadIstanzaResponsabili(Long idIstanza);

    /**
     * Update data atto from provvedimento finale integer.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the integer
     */
    Integer updateDataConclProcedimentoFromProvvedimentoFinale(Long idIstanza, AttoreScriva attoreScriva);

    /**
     * Update protocollo allegati istanza integer.
     *
     * @param istanza      the istanza
     * @param attoreScriva the attore scriva
     * @return the integer
     */
    Integer updateProtocolloAllegatiIstanza(IstanzaExtendedDTO istanza, AttoreScriva attoreScriva);

    /**
     * Gets tipo adempimento ogg app istanza.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the tipo adempimento ogg app istanza
     */
    List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggAppIstanza(Long idIstanza, AttoreScriva attoreScriva);

    /**
     * Gets tipo adempimento ogg app istanza json.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the tipo adempimento ogg app istanza json
     */
    String getTipoAdempimentoOggAppIstanzaJson(Long idIstanza, AttoreScriva attoreScriva);

}