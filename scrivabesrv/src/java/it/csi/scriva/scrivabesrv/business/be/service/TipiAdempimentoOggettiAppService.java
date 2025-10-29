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
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoOggettoAppExtendedDTO;

import java.util.List;

/**
 * The interface Tipi adempimento oggetti app service.
 *
 * @author CSI PIEMONTE
 */
public interface TipiAdempimentoOggettiAppService {

    /**
     * Gets tipo adempimento oggetti app.
     *
     * @return the tipo adempimento oggetti app
     */
    List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettiApp();

    /**
     * Gets tipo adempimento oggetto app by id.
     *
     * @param idTipoAdempimentoOggettoApp the id tipo adempimento oggetto app
     * @return the tipo adempimento oggetto app by id
     */
    List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettoAppById(Long idTipoAdempimentoOggettoApp);

    /**
     * Gets tipo adempimento oggetto app by id tipo adempimento profilo.
     *
     * @param idTipoAdempimentoProfilo the id tipo adempimento profilo
     * @return the tipo adempimento oggetto app by id tipo adempimento profilo
     */
    List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettoAppByIdTipoAdempimentoProfilo(Long idTipoAdempimentoProfilo);

    /**
     * Gets tipo adempimento oggetto app by code oggetto app.
     *
     * @param codOggettoApp the cod oggetto app
     * @return the tipo adempimento oggetto app by code oggetto app
     */
    List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettoAppByCodeOggettoApp(String codOggettoApp);

    /**
     * Gets tipo adempimento oggetto app for azioni.
     *
     * @param idIstanza         the id istanza
     * @param codTipoOggettoApp the cod tipo oggetto app
     * @param attoreScriva      the attore scriva
     * @return the tipo adempimento oggetto app for azioni
     */
    List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettoAppForAzioni(Long idIstanza, String codTipoOggettoApp, AttoreScriva attoreScriva);

    /**
     * Gets tipo adempimento oggetto app for azioni.
     *
     * @param idIstanzaList     the id istanza list
     * @param codTipoOggettoApp the cod tipo oggetto app
     * @param attoreScriva      the attore scriva
     * @return the tipo adempimento oggetto app for azioni
     */
    List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettoAppForAzioni(List<Long> idIstanzaList, String codTipoOggettoApp, AttoreScriva attoreScriva);

    /**
     * Gets tipo adempimento oggetto app for azioni with gestore.
     *
     * @param idIstanza         the id istanza
     * @param codTipoOggettoApp the cod tipo oggetto app
     * @param attoreScriva      the attore scriva
     * @return the tipo adempimento oggetto app for azioni with gestore
     */
    List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettoAppForAzioniWithGestore(Long idIstanza, String codTipoOggettoApp, AttoreScriva attoreScriva);

    /**
     * Gets tipo adempimento oggetto app for azioni with gestore.
     *
     * @param idIstanzaList     the id istanza list
     * @param codTipoOggettoApp the cod tipo oggetto app
     * @param attoreScriva      the attore scriva
     * @return the tipo adempimento oggetto app for azioni with gestore
     */
    List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggettoAppForAzioniWithGestore(List<Long> idIstanzaList, String codTipoOggettoApp, AttoreScriva attoreScriva);

    /**
     * Gets oggetto app non previsto da gestore processo.
     *
     * @param tipoAdempimentoOggettoAppList the tipo adempimento oggetto app list
     * @return the oggetto app non previsto da gestore processo
     */
    List<TipoAdempimentoOggettoAppExtendedDTO> getOggettoAppNonPrevistoDaGestoreProcesso(List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList);

}