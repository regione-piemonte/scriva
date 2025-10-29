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
import it.csi.scriva.scrivabesrv.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ClasseAllegatoDTO;
import it.csi.scriva.scrivabesrv.dto.GenericInputParDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.UrlDTO;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * The interface Allegati service.
 *
 * @author CSI PIEMONTE
 */
public interface AllegatiService {

    /**
     * Load allegati list.
     *
     * @return the list
     */
    List<AllegatoIstanzaExtendedDTO> loadAllegati();

    /**
     * Load allegati by id allegato istanza extended dto.
     *
     * @param idAllegatoIstanza the id allegato istanza
     * @return the allegato istanza extended dto
     */
    AllegatoIstanzaExtendedDTO loadAllegatiById(Long idAllegatoIstanza);

    /**
     * Load allegati by uuid index allegato istanza extended dto.
     *
     * @param uuidIndex the uuid index
     * @return the allegato istanza extended dto
     */
    AllegatoIstanzaExtendedDTO loadAllegatiByUuidIndex(String uuidIndex);

    /**
     * Load allegati by id istanza list.
     *
     * @param idIstanza             the id istanza
     * @param codAllegato           the cod allegato
     * @param codClasseAllegato     the cod classe allegato
     * @param codCategoriaAllegato  the cod categoria allegato
     * @param codTipologiaAllegato  the cod tipologia allegato
     * @param idIstanzaOsservazione the id istanza osservazione
     * @param flgCancLogica         the flg canc logica
     * @param flgLinkDocumento      the flg link documento
     * @param offset                the offset
     * @param limit                 the limit
     * @param sort                  the sort
     * @param system                the system
     * @param attoreScriva          the attore scriva
     * @return the list
     */
    List<AllegatoIstanzaExtendedDTO> loadAllegatiByIdIstanza(Long idIstanza, String codAllegato, String codClasseAllegato, String codCategoriaAllegato, String codTipologiaAllegato, Long idIstanzaOsservazione, String flgCancLogica, String flgLinkDocumento, Integer offset, Integer limit, String sort, Boolean system, AttoreScriva attoreScriva);

    /**
     * Load allegato istanza non inviato by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    List<AllegatoIstanzaExtendedDTO> loadAllegatoIstanzaNonInviatoByIdIstanza(Long idIstanza);

    /**
     * Update allegato istanza integer.
     *
     * @param allegatoIstanza         the allegato istanza
     * @param adempimentoTipoAllegato the adempimento tipo allegato
     * @param attoreScriva            the attore scriva
     * @return the integer
     */
    Integer updateAllegatoIstanza(AllegatoIstanzaExtendedDTO allegatoIstanza, AdempimentoTipoAllegatoExtendedDTO adempimentoTipoAllegato, AttoreScriva attoreScriva);

    /**
     * Gets link index file.
     *
     * @param uuidIndex the uuid index
     * @param fromDate  the from date
     * @param toDate    the to date
     * @return the link index file
     */
    UrlDTO getLinkIndexFile(String uuidIndex, String fromDate, String toDate);

    /**
     * Validate allegato map.
     *
     * @param allegatoIstanza the allegato istanza
     * @param attoreScriva    the attore scriva
     * @return the map
     */
    Map<String, Object> validateAllegato(AllegatoIstanzaExtendedDTO allegatoIstanza, AttoreScriva attoreScriva);

    /**
     * Gets allegato file.
     *
     * @param codAllegato  the cod allegato
     * @param attoreScriva the attore scriva
     * @return the allegato file
     */
    File getAllegatoFile(String codAllegato, AttoreScriva attoreScriva);

    /**
     * Gets allegati zip file.
     *
     * @param idIstanza    the id istanza
     * @param codAllegato  the cod allegato
     * @param attoreScriva the attore scriva
     * @return the allegati zip file
     */
    File getAllegatiZipFile(Long idIstanza, String codAllegato, AttoreScriva attoreScriva);

    /**
     * Gets allegati zip file.
     *
     * @param idOsservazione the id osservazione
     * @param codAllegato    the cod allegato
     * @param attoreScriva   the attore scriva
     * @return the allegati zip file
     */
    File getAllegatiZipFileByIdOsservazione(Long idOsservazione, String codAllegato, AttoreScriva attoreScriva);

    /**
     * Gets allegati list zip file.
     *
     * @param idIstanza      the id istanza
     * @param codiciAllegato the codici allegato
     * @param attoreScriva   the attore scriva
     * @return the allegati list zip file
     */
    File getAllegatiListZipFile(Long idIstanza, GenericInputParDTO codiciAllegato, AttoreScriva attoreScriva);

    /**
     * Load allegati csv by id istanza file.
     *
     * @param idIstanza    the id istanza
     * @param codAllegato  the cod allegato
     * @param attoreScriva the attore scriva
     * @return the file
     */
    File getAllegatiCSVFile(Long idIstanza, String codAllegato, AttoreScriva attoreScriva);

    /**
     * Get allegati pdf file file.
     *
     * @param idIstanza    the id istanza
     * @param codAllegato  the cod allegato
     * @param attoreScriva the attore scriva
     * @return the file
     * @throws Exception the exception
     */
    File getAllegatiPDFFile(Long idIstanza, String codAllegato, AttoreScriva attoreScriva) throws Exception;

    /**
     * Gets file allegati.
     *
     * @param idIstanza      the id istanza
     * @param codAllegato    the cod allegato
     * @param idOsservazione the id osservazione
     * @param outputFormat   the output format
     * @param attoreScriva   the attore scriva
     * @return the file allegati
     * @throws Exception the exception
     */
    File getFileAllegati(Long idIstanza, String codAllegato, Long idOsservazione, String outputFormat, AttoreScriva attoreScriva) throws Exception;

    /**
     * Gets file allegati list.
     *
     * @param idIstanza      the id istanza
     * @param codiciAllegato the codici allegato
     * @param idOsservazione the id osservazione
     * @param outputFormat   the output format
     * @param attoreScriva   the attore scriva
     * @return the file allegati list
     * @throws Exception the exception
     */
    File getFileAllegatiList(Long idIstanza, GenericInputParDTO codiciAllegato, Long idOsservazione, String outputFormat, AttoreScriva attoreScriva) throws Exception;

    /**
     * Gets classe allegato.
     *
     * @param istanza              the istanza
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param attoreScriva         the attore scriva
     * @return the classe allegato
     */
    ClasseAllegatoDTO getClasseAllegato(IstanzaDTO istanza, String codTipologiaAllegato, AttoreScriva attoreScriva);

    /**
     * Gets classe allegato by code.
     *
     * @param codClasseAllegato the cod classe allegato
     * @return the classe allegato by code
     */
    ClasseAllegatoDTO getClasseAllegatoByCode(String codClasseAllegato);

    /**
     * Gets file from index.
     *
     * @param uuidIndex the uuid index
     * @return the file from index
     */
    File getFileFromIndex(String uuidIndex);

    /**
     * Update data invio esterno integer.
     *
     * @param idAllegatoIstanzaList the id allegato istanza list
     * @return the integer
     */
    Integer updateDataInvioEsterno(List<Long> idAllegatoIstanzaList);

    /**
     * Update id padre figlio.
     *
     * @param idIstanza            the id istanza
     * @param idAllegatoIstanza    the id allegato istanza
     * @param codTipologiaAllegato the cod tipologia allegato
     */
    void updateIdPadreFiglio(Long idIstanza, Long idAllegatoIstanza, String codTipologiaAllegato);

    /**
     * Sets dati pubblicazione.
     *
     * @param allegatoIstanza the allegato istanza
     * @param istanza         the istanza
     * @param attoreScriva    the attore scriva
     * @throws GenericException the generic exception
     */
    void setDatiPubblicazione(AllegatoIstanzaExtendedDTO allegatoIstanza, IstanzaDTO istanza, AttoreScriva attoreScriva) throws GenericException;

    /**
     * Update data padre from figlio.
     *
     * @param idAllegatoIstanzaPadre the id allegato istanza padre
     * @return the integer
     */
    Integer updateDataPadreFromFiglio(Long idAllegatoIstanzaPadre);

    /**
     * Update id padre osservazioni.
     *
     * @param allegatoIstanza the allegato istanza
     * @param attoreScriva    the attore scriva
     * @return integer integer
     */
    Integer updateIdPadreOsservazioni(AllegatoIstanzaExtendedDTO allegatoIstanza, AttoreScriva attoreScriva);

    /**
     * Update data protocollo mod istanza elenco allegati integer.
     *
     * @param idIstanza      the id istanza
     * @param numProtocollo  the num protocollo
     * @param dataProtocollo the data protocollo
     * @param attoreScriva   the attore scriva
     * @return the integer
     */
    Integer updateDataProtocolloModIstanzaElencoAllegati(Long idIstanza, String numProtocollo, Timestamp dataProtocollo, AttoreScriva attoreScriva);

}