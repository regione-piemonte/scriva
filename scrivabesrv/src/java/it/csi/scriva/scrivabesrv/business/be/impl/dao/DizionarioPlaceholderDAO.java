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

import it.csi.scriva.scrivabesrv.dto.DizionarioPlaceholderDTO;

import java.util.List;

/**
 * The interface Dizionario placeholder dao.
 *
 * @author CSI PIEMONTE
 */
public interface DizionarioPlaceholderDAO {

    /**
     * Load dizionari placeholder list.
     *
     * @return the list
     */
    List<DizionarioPlaceholderDTO> loadDizionariPlaceholder();

    /**
     * Load dizionario placeholder by id list.
     *
     * @param idDizionarioPlaceholder the id dizionario placeholder
     * @return the list
     */
    List<DizionarioPlaceholderDTO> loadDizionarioPlaceholderById(Long idDizionarioPlaceholder);

    /**
     * Load dizionario placeholder by code list.
     *
     * @param codDizionarioPlaceholder the cod dizionario placeholder
     * @return the list
     */
    List<DizionarioPlaceholderDTO> loadDizionarioPlaceholderByCode(String codDizionarioPlaceholder);

    /**
     * Load dizionario placeholder by code list.
     *
     * @param codDizionarioPlaceholderList the cod dizionario placeholder list
     * @return the list
     */
    List<DizionarioPlaceholderDTO> loadDizionarioPlaceholderByCode(List<String> codDizionarioPlaceholderList);

}