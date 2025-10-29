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

import it.csi.scriva.scrivabesrv.dto.CategoriaOggettoExtendedDTO;

import java.util.List;

/**
 * The interface Categoria oggetto dao.
 *
 * @author CSI PIEMONTE
 */
public interface CategoriaOggettoDAO {

    /**
     * Load categorie oggetto list.
     *
     * @return List<CategoriaOggettoExtendedDTO>  list
     */
    List<CategoriaOggettoExtendedDTO> loadCategorieOggetto(String componenteApp);

    /**
     * Load categoria oggetto by id list.
     *
     * @param idCategoriaOggetto idCategoriaOggetto
     * @return List<CategoriaOggettoExtendedDTO>  list
     */
    List<CategoriaOggettoExtendedDTO> loadCategoriaOggettoById(Long idCategoriaOggetto);


    /**
     * Load categoria oggetto by code list.
     *
     * @param codeCategoriaOggetto the code categoria oggetto
     * @return the list
     */
    List<CategoriaOggettoExtendedDTO> loadCategoriaOggettoByCode(String codeCategoriaOggetto, Long idAdempimento);

    /**
     * Load categoria oggetto by id adempimento list.
     *
     * @param idAdempimento idAdempimento
     * @return List<CategoriaOggettoExtendedDTO>  list
     */
    List<CategoriaOggettoExtendedDTO> loadCategoriaOggettoByIdAdempimento(Long idAdempimento);

    /**
     * Load categoria oggetto by id adempimento componente list.
     *
     * @param idAdempimento idAdempimento
     * @param componenteApp componenteApp
     * @return List<CategoriaOggettoExtendedDTO>  list
     */
    List<CategoriaOggettoExtendedDTO> loadCategoriaOggettoByIdAdempimentoComponente(Long idAdempimento, String componenteApp);

    /**
     * Load categoria oggetto by id adempimento componente search criteria list.
     *
     * @param idAdempimento  idAdempimento
     * @param componenteApp  componenteApp
     * @param searchCriteria searchCriteria
     * @return List<CategoriaOggettoExtendedDTO>  list
     */
    List<CategoriaOggettoExtendedDTO> loadCategoriaOggettoByIdAdempimentoComponenteSearchCriteria(Long idAdempimento, String componenteApp, String searchCriteria);

}