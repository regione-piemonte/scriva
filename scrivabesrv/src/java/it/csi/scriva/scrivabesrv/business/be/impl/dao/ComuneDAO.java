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

import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;

import java.util.List;

/**
 * The interface Comune dao.
 *
 * @author CSI PIEMONTE
 */
public interface ComuneDAO {

    /**
     * Load comuni list.
     *
     * @return the list
     */
    List<ComuneExtendedDTO> loadComuni();

    /**
     * Load comuni list.
     *
     * @param codIstatComune    the cod istat comune
     * @param codIstatProvincia the cod istat provincia
     * @param siglaProvincia    the sigla provincia
     * @return the list
     */
    List<ComuneExtendedDTO> loadComuni(String codIstatComune, String codIstatProvincia, String siglaProvincia);

    /**
     * Load comuni list.
     *
     * @param codIstatList the cod istat list
     * @return the list
     */
    List<ComuneExtendedDTO> loadComuni(List<String> codIstatList);

    /**
     * Load comuni attivi list.
     *
     * @return the list
     */
    List<ComuneExtendedDTO> loadComuniAttivi();

    /**
     * Load comune by id list.
     *
     * @param idComune the id comune
     * @return the list
     */
    List<ComuneExtendedDTO> loadComuneById(Long idComune);

    /**
     * Load comune by cod istat list.
     *
     * @param codIstatComune the cod istat comune
     * @return the list
     */
    List<ComuneExtendedDTO> loadComuneByCodIstat(String codIstatComune);

    /**
     * Load comune by denominazione list.
     *
     * @param denomComune the denom comune
     * @return the list
     */
    List<ComuneExtendedDTO> loadComuneByDenominazione(String denomComune);

    /**
     * Load comune by id regione list.
     *
     * @param idProvincia the id provincia
     * @return the list
     */
    List<ComuneExtendedDTO> loadComuneByIdProvincia(Long idProvincia);

    /**
     * Load comune by cod istat provincia list.
     *
     * @param codIstatProvincia the cod istat provincia
     * @return the list
     */
    List<ComuneExtendedDTO> loadComuneByCodIstatProvincia(String codIstatProvincia);

    /**
     * Load comune by denom regione list.
     *
     * @param denomProvincia the denom provincia
     * @return the list
     */
    List<ComuneExtendedDTO> loadComuneByDenomProvincia(Long denomProvincia);

}