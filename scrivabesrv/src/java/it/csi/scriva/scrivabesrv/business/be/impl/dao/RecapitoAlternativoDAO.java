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

import it.csi.scriva.scrivabesrv.dto.RecapitoAlternativoDTO;
import it.csi.scriva.scrivabesrv.dto.RecapitoAlternativoExtendedDTO;

import java.util.List;

/**
 * The interface Recapito alternativo dao.
 *
 * @author CSI PIEMONTE
 */
public interface RecapitoAlternativoDAO {

    /**
     * Load recapiti alternativi list.
     *
     * @return the list
     */
    List<RecapitoAlternativoExtendedDTO> loadRecapitiAlternativi();

    /**
     * Load recapito alternativo by id list.
     *
     * @param idRecapitoAlternativo the id recapito alternativo
     * @return the list
     */
    List<RecapitoAlternativoExtendedDTO> loadRecapitoAlternativoById(Long idRecapitoAlternativo);

    /**
     * Load recapito alternativo by code list.
     *
     * @param codRecapitoAlternativo the cod recapito alternativo
     * @return the list
     */
    List<RecapitoAlternativoExtendedDTO> loadRecapitoAlternativoByCode(String codRecapitoAlternativo);

    /**
     * Load recapito alternativo by id soggetto istanza list.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @return the list
     */
    List<RecapitoAlternativoExtendedDTO> loadRecapitoAlternativoByIdSoggettoIstanza(Long idSoggettoIstanza);

    /**
     * Save recapito alternativo long.
     *
     * @param recapitoAlternativo the recapito alternativo
     * @return the long
     */
    Long saveRecapitoAlternativo(RecapitoAlternativoDTO recapitoAlternativo);

    /**
     * Update recapito alternativo integer.
     *
     * @param recapitoAlternativo the recapito alternativo
     * @return the integer
     */
    Integer updateRecapitoAlternativo(RecapitoAlternativoDTO recapitoAlternativo);

    /**
     * Delete recapito alternativo integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    Integer deleteRecapitoAlternativo(String gestUID);

    /**
     * Delete recapito alternativo by id soggetto istanza integer.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @return the integer
     */
    Integer deleteRecapitoAlternativoByIdSoggettoIstanza(Long idSoggettoIstanza);

}