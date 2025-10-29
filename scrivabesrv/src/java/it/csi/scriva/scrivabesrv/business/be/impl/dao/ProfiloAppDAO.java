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

import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ProfiloAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloOggettoAppDTO;

import java.util.List;

/**
 * The interface Profilo app dao.
 *
 * @author CSI PIEMONTE
 */
public interface ProfiloAppDAO {

    /**
     * Load profili app list.
     *
     * @return List<ProfiloAppExtendedDTO>       list
     */
    List<ProfiloAppExtendedDTO> loadProfiliApp();

    /**
     * Load profilo app by id list.
     *
     * @param idProfiloApp idProfiloApp
     * @return List<ProfiloAppExtendedDTO>       list
     */
    List<ProfiloAppExtendedDTO> loadProfiloAppById(Long idProfiloApp);

    /**
     * Load profilo app by code list.
     *
     * @param codProfiloApp codProfiloApp
     * @return List<ProfiloAppExtendedDTO>       list
     */
    List<ProfiloAppExtendedDTO> loadProfiloAppByCode(String codProfiloApp);

    /**
     * Load profilo app by code profilo and componente app list.
     *
     * @param codProfiloApp    codProfiloApp
     * @param codComponenteApp codComponenteApp
     * @return List<ProfiloAppExtendedDTO>       list
     */
    List<ProfiloAppExtendedDTO> loadProfiloAppByCodeProfiloAndComponenteApp(String codProfiloApp, String codComponenteApp);


    /**
     * Load profilo app by code profilo and componente app list.
     *
     * @param codProfiloApp    the cod profilo app
     * @param codComponenteApp the cod componente app
     * @param password         the password
     * @return the list
     */
    List<ProfiloAppExtendedDTO> loadProfiloAppByCodeProfiloAndComponenteApp(String codProfiloApp, String codComponenteApp, String password);

    /**
     * Load profilo app by code profilo and componente app list.
     *
     * @param codProfiloApp     the cod profilo app
     * @param codComponenteApp  the cod componente app
     * @param password          the password
     * @param codOggettoApp     the cod oggetto app
     * @param codTipoOggettoApp the cod tipo oggetto app
     * @return the list
     */
    List<ProfiloAppExtendedDTO> loadProfiloAppByCodeProfiloAndComponenteApp(String codProfiloApp, String codComponenteApp, String password, String codOggettoApp, String codTipoOggettoApp);

    /**
     * Load profilo app by id istanza and attore list.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the list
     */
    List<ProfiloOggettoAppDTO> loadProfiloAppByIdIstanzaAndAttore(Long idIstanza, AttoreScriva attoreScriva);

    /**
     * Load profilo app by id istanza and attore list.
     *
     * @param idIstanzaList the id istanza list
     * @param attoreScriva  the attore scriva
     * @return the list
     */
    List<ProfiloOggettoAppDTO> loadProfiloAppByIdIstanzaListAndAttore(List<Long> idIstanzaList, AttoreScriva attoreScriva);

    /**
     * Load profilo app by attore bo list.
     *
     * @param attoreScriva the attore scriva
     * @return the list
     */
    List<ProfiloOggettoAppDTO> loadProfiloAppByAttoreBO(AttoreScriva attoreScriva);

}