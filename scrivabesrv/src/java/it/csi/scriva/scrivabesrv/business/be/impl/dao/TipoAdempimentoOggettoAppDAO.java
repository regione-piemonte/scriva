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

import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoOggettoAppExtendedDTO;

import java.util.List;

/**
 * The interface Tipo adempimento oggetto app dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoAdempimentoOggettoAppDAO {

    /**
     * Load tipo adempimento oggetti app list.
     *
     * @return List<TipoAdempimentoOggettoAppExtendedDTO>  list
     */
    List<TipoAdempimentoOggettoAppExtendedDTO> loadTipoAdempimentoOggettiApp();

    /**
     * Load tipo adempimento oggetto app by id list.
     *
     * @param idTipoAdempimentoOggettoApp idTipoAdempimentoOggettoApp
     * @return List<TipoAdempimentoOggettoAppExtendedDTO>  list
     */
    List<TipoAdempimentoOggettoAppExtendedDTO> loadTipoAdempimentoOggettoAppById(Long idTipoAdempimentoOggettoApp);


    /**
     * Load tipo adempimento oggetto app by id tipo adempimento profilo list.
     *
     * @param idTipoAdempimentoProfilo idTipoAdempimentoProfilo
     * @return List<TipoAdempimentoOggettoAppExtendedDTO>  list
     */
    List<TipoAdempimentoOggettoAppExtendedDTO> loadTipoAdempimentoOggettoAppByIdTipoAdempimentoProfilo(Long idTipoAdempimentoProfilo);

    /**
     * Load tipo adempimento oggetto app by code oggetto app list.
     *
     * @param codOggettoApp codOggettoApp
     * @return List<TipoAdempimentoOggettoAppExtendedDTO>  list
     */
    List<TipoAdempimentoOggettoAppExtendedDTO> loadTipoAdempimentoOggettoAppByCodeOggettoApp(String codOggettoApp);


    /**
     * Load tipo adempimento oggetto app for azioni list.
     *
     * @param idIstanza         idIstanza
     * @param cfAttore          codice fiscale attore
     * @param codComponenteApp  codice componente applicativo
     * @param codTipoOggettoApp codice tipo oggetto applicativo
     * @return List<TipoAdempimentoOggettoAppExtendedDTO>  list
     */
    List<TipoAdempimentoOggettoAppExtendedDTO> loadTipoAdempimentoOggettoAppForAzioni(Long idIstanza, String cfAttore, String codComponenteApp, String codTipoOggettoApp);

    /**
     * Load tipo adempimento oggetto app for azioni list.
     *
     * @param idIstanzaList     the id istanza list
     * @param cfAttore          the cf attore
     * @param codComponenteApp  the cod componente app
     * @param codTipoOggettoApp the cod tipo oggetto app
     * @return the list
     */
    List<TipoAdempimentoOggettoAppExtendedDTO> loadTipoAdempimentoOggettoAppForAzioni(List<Long> idIstanzaList, String cfAttore, String codComponenteApp, String codTipoOggettoApp);

}