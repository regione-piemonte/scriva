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
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaEventoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoEventoExtendedDTO;

import java.sql.Date;
import java.util.List;

/**
 * The interface Istanza evento service.
 *
 * @author CSI PIEMONTE
 */
public interface IstanzaEventoService {

    /**
     * Load istanza evento list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    List<IstanzaEventoExtendedDTO> loadIstanzaEvento(Long idIstanza);

    /**
     * Trace istanza evento by code tipo evento list.
     *
     * @param idIstanza         the id istanza
     * @param codeTipoEvento    the code tipo evento
     * @param rifCanaleNotifica the rif canale notifica
     * @param codCanaleNotifica the cod canale notifica
     * @param uidRichiesta      the uid richiesta
     * @param dataIntegrazione  the data integrazione
     * @param tipoRichiesta     the tipo richiesta
     * @param attoreScriva      the attore scriva
     * @return the list
     * @throws GenericException the generic exception
     */
    List<IstanzaEventoExtendedDTO> traceIstanzaEventoByCodeTipoEvento(Long idIstanza,
                                                                      String codeTipoEvento,
                                                                      String rifCanaleNotifica,
                                                                      String codCanaleNotifica,
                                                                      String uidRichiesta,
                                                                      Date dataIntegrazione,
                                                                      String tipoRichiesta,
                                                                      AttoreScriva attoreScriva) throws GenericException;

    /**
     * Save istanza evento long.
     *
     * @param istanza      the istanza
     * @param tipoEvento   the tipo evento
     * @param attoreScriva the attore scriva
     * @return the long
     */
    Long saveIstanzaEvento(IstanzaDTO istanza, TipoEventoExtendedDTO tipoEvento, AttoreScriva attoreScriva);

    /**
     * Must update anagrafica oggetto boolean.
     *
     * @param idStatoIstanza the id stato istanza
     * @return the boolean
     */
    boolean mustUpdateAnagraficaOggetto(Long idStatoIstanza);

    /**
     * Must update anagrafica oggetto boolean.
     *
     * @param codStatoIstanza the cod stato istanza
     * @param idOggetto       the id oggetto
     * @param idIstanza       the id istanza
     * @return the boolean
     */
    boolean mustUpdateAnagraficaOggetto(String codStatoIstanza, Long idOggetto, Long idIstanza);
}