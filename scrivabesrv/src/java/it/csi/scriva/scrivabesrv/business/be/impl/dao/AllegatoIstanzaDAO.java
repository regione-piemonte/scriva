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

import it.csi.scriva.scrivabesrv.dto.AllegatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.NotificaAllegatoSearchDTO;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * The interface Allegato istanza dao.
 *
 * @author CSI PIEMONTE
 */
public interface AllegatoIstanzaDAO {

    /**
     * Load allegati istanza list.
     *
     * @param idIstanzaOsservazione the id istanza osservazione
     * @param attoreScriva          the attore scriva
     * @param flgOnlyPubblicati     the flg only pubblicati
     * @param codComponenteApp      the cod componente app
     * @return List<AllegatoIstanzaExtendedDTO>                          list
     */
    List<AllegatoIstanzaExtendedDTO> loadAllegatiIstanza(Long idIstanzaOsservazione, AttoreScriva attoreScriva, boolean flgOnlyPubblicati, String codComponenteApp);

    /**
     * Load allegato istanza by id list.
     *
     * @param id id
     * @return List<AllegatoIstanzaExtendedDTO>                          list
     */
    List<AllegatoIstanzaExtendedDTO> loadAllegatoIstanzaById(Long id);

    /**
     * Load allegato istanza by id istanza list.
     *
     * @param idIstanza             idIstanza
     * @param codAllegato           the cod allegato
     * @param codClasseAllegato     the cod classe allegato
     * @param codCategoriaAllegato  the cod categoria allegato
     * @param codTipologiaAllegato  the cod tipologia allegato
     * @param idIstanzaOsservazione the id istanza osservazione
     * @param flgCancLogica         the flg canc logica
     * @param flgOnlyPubblicati     the flg only pubblicati
     * @param offset                the offset
     * @param limit                 the limit
     * @param sort                  the sort
     * @param system                the system
     * @param codComponenteApp      the cod componente app
     * @return List<AllegatoIstanzaExtendedDTO>                          list
     */
    List<AllegatoIstanzaExtendedDTO> loadAllegatoIstanzaByIdIstanza(Long idIstanza, String codAllegato, String codClasseAllegato,
                                                                    String codCategoriaAllegato, String codTipologiaAllegato,
                                                                    Long idIstanzaOsservazione, String flgCancLogica,
                                                                    boolean flgOnlyPubblicati,
                                                                    String offset, String limit, String sort, Boolean system, String codComponenteApp);

    /**
     * Load allegato istanza by uuid index list.
     *
     * @param uuidIndex uuidIndex
     * @return List<AllegatoIstanzaExtendedDTO>                          list
     */
    List<AllegatoIstanzaExtendedDTO> loadAllegatoIstanzaByUuidIndex(String uuidIndex);

    /**
     * Load allegato istanza by id istanza and cod tipologia list.
     *
     * @param idIstanza            idIstanza
     * @param codTipologiaAllegato codice tipologia allegato
     * @return List<AllegatoIstanzaExtendedDTO>                          list
     */
    List<AllegatoIstanzaExtendedDTO> loadAllegatoIstanzaByIdIstanzaAndCodTipologia(Long idIstanza, String codTipologiaAllegato);

    /**
     * Load allegato istanza by id istanza and nome list.
     *
     * @param idIstanza    idIstanza
     * @param nomeAllegato nome allegato
     * @return List<AllegatoIstanzaExtendedDTO>                          list
     */
    List<AllegatoIstanzaExtendedDTO> loadAllegatoIstanzaByIdIstanzaAndNome(Long idIstanza, String nomeAllegato);

    /**
     * Load allegato istanza non inviato by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    List<AllegatoIstanzaExtendedDTO> loadAllegatoIstanzaNonInviatoByIdIstanza(Long idIstanza);
    
    /**
     * Load allegato istanza figli by id allegato istanza padre.
     *
     * @param idAllegatoIstanzaPadre the id istanza
     * @return the list
     */
    List<AllegatoIstanzaExtendedDTO> loadAllegatoIstanzaFigliByIdAllegatoPadre(Long idAllegatoIstanzaPadre);

    /**
     * Save allegato istanza long.
     *
     * @param allegatoIstanza      AllegatoIstanzaDTO
     * @param codCategoriaAllegato codice categoira allegato
     * @return id record inserito
     */
    Long saveAllegatoIstanza(AllegatoIstanzaDTO allegatoIstanza, String codCategoriaAllegato);

    /**
     * Update allegato istanza integer.
     *
     * @param allegatoIstanza AllegatoIstanzaDTO
     * @return numero record aggiornati
     */
    Integer updateAllegatoIstanza(AllegatoIstanzaDTO allegatoIstanza);

    /**
     * Delete allegato istanza by uuid index integer.
     *
     * @param uuidIndex uuidIndex
     * @return the integer
     */
    Integer deleteAllegatoIstanzaByUuidIndex(String uuidIndex);

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
     * @param idIstanza              the id istanza
     * @param idAllegatoIstanzaPadre the id allegato istanza padre
     */
    void updateIdPadreFiglio(Long idIstanza, Long idAllegatoIstanzaPadre);

    /**
     * Update id padre figlio elenco integrazioni.
     *
     * @param idIstanza              the id istanza
     * @param idAllegatoIstanzaPadre the id allegato istanza padre
     */
    void updateIdPadreFiglioElencoIntegrazioni(Long idIstanza, Long idAllegatoIstanzaPadre);

    /**
     * Update id padre figlio elenco allegati.
     *
     * @param idIstanza              the id istanza
     * @param idAllegatoIstanzaPadre the id allegato istanza padre
     */
    void updateIdPadreFiglioElencoAllegati(Long idIstanza, Long idAllegatoIstanzaPadre);

    /**
     * Delete id padre integer.
     *
     * @param idAllegatoIstanzaPadre the id padre
     * @return the integer
     */
    Integer deleteIdPadre(Long idAllegatoIstanzaPadre);

    /**
     * Update data pubblicazione integer.
     *
     * @param idIstanza         the id istanza
     * @param dataPubblicazione the data pubblicazione
     * @param attoreUpd         the attore upd
     * @return the integer
     */
    Integer updateDataPubblicazione(Long idIstanza, Date dataPubblicazione, String attoreUpd);

    /**
     * Update data padre from figlio integer.
     *
     * @param idAllegatoIstanzaPadre the id allegato istanza padre
     * @return the integer
     */
    Integer updateDataPadreFromFiglio(Long idAllegatoIstanzaPadre);

    /**
     * Update id padre osservazioni integer.
     *
     * @param idAllegatoIstanzaOsservazione the id allegato istanza osservazione
     * @param attoreScriva                  the attore scriva
     * @return the integer
     */
    Integer updateIdPadreOsservazioni(Long idAllegatoIstanzaOsservazione, AttoreScriva attoreScriva);

    /**
     * Update dati integrazione integer.
     *
     * @param idAllegatoIstanzaPadre the id allegato istanza padre
     * @param numProtocollo          the num protocollo
     * @param dataProtocollo         the data protocollo
     * @param attoreScriva           the attore scriva
     * @return the integer
     */
    Integer updateDatiIntegrazione(Long idAllegatoIstanzaPadre, String numProtocollo, Timestamp dataProtocollo, AttoreScriva attoreScriva);

    /**
     * Update protocollo mod istanza elenco allegati integer.
     *
     * @param idIstanza      the id istanza
     * @param numProtocollo  the num protocollo
     * @param dataProtocollo the data protocollo
     * @param attoreScriva   the attore scriva
     * @return the integer
     */
    Integer updateProtocolloModIstanzaElencoAllegati(Long idIstanza, String numProtocollo, Timestamp dataProtocollo, AttoreScriva attoreScriva);

    /**
     * Update protocollo figli elenco allegati integer.
     *
     * @param idIstanza      the id istanza
     * @param numProtocollo  the num protocollo
     * @param dataProtocollo the data protocollo
     * @param attoreScriva   the attore scriva
     * @return the integer
     */
    
     Integer updateProtocolloFigliElencoAllegati(Long idIstanza, String numProtocollo, Timestamp dataProtocollo, AttoreScriva attoreScriva);
    

     /**
     * Update protocollo mod istanza elenco allegati integer.
     *
     * @param idIstanza      the id istanza
     * @param numProtocollo  the num protocollo
     * @param dataProtocollo the data protocollo
     * @param attoreScriva   the attore scriva
     * @return the integer
     */
    Integer updateProtocolloModIstanzaElencoAllegatiAll(Long idIstanza, String numProtocollo, Timestamp dataProtocollo, AttoreScriva attoreScriva);


    /**
     * Update id allegato istanza padre.
     *
     * @param uuidIndex              the uuidIndex
     * 
     */
	void updateIdAllegatoIstanzaPadre(String uuidIndex);


    List<AllegatoIstanzaDTO> loadAllegatoIstanzaForNotifiche(NotificaAllegatoSearchDTO notificaAllegatoSearch);


}