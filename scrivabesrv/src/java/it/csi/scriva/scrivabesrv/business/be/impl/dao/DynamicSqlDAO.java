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

import java.util.List;
import java.util.Map;

/**
 * The interface Dynamic sql dao.
 *
 * @author CSI PIEMONTE
 */
public interface DynamicSqlDAO {

    /**
     * Esegue la query passata in input e restituisce i relativi record in una lista di stringhe.
     * La prima stringa rappresenta le intestazioni di colonna.
     *
     * @param query     Query da eseguire
     * @param delimiter Separatore tra field
     * @param mapParam  the map param
     * @param offset    the offset
     * @param limit     the limit
     * @return Lista dei record in formato stringa
     */
    List<String> getListFromQuery(String query, String delimiter, Map<String, Object> mapParam, String offset, String limit);


    /**
     * Esegue la query passata in input e restituisce i relativi dati in un array di byte.
     *
     * @param query     Query da eseguire
     * @param delimiter Separatore tra field
     * @param mapParam  the map param
     * @param offset    the offset
     * @param limit     the limit
     * @return Array di byte dei record in formato CSV
     */
    byte[] getCSVFromQuery(String query, String delimiter, Map<String, Object> mapParam, String offset, String limit);
}