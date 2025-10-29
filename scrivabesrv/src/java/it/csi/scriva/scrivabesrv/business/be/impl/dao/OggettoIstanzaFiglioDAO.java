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

import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaFiglioDTO;

import java.util.List;

/**
 * The interface Oggetto istanza figlio dao.
 *
 * @author CSI PIEMONTE
 */
public interface OggettoIstanzaFiglioDAO {

    /**
     * Load oggetto istanza figlio by id oggetto istanza list.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */
    List<OggettoIstanzaFiglioDTO> loadOggettoIstanzaFiglioByIdOggettoIstanza(Long idOggettoIstanza);

    /**
     * Load oggetto istanza figlio by id ogg ist padre list.
     *
     * @param idOggettoIstanzaPadre the id oggetto istanza padre
     * @return the list
     */
    List<OggettoIstanzaFiglioDTO> loadOggettoIstanzaFiglioByIdOggIstPadre(Long idOggettoIstanzaPadre);

    /**
     * Load oggetto istanza figlio by id ogg ist figlio list.
     *
     * @param idOggettoIstanzaFiglio the id oggetto istanza figlio
     * @return the list
     */
    List<OggettoIstanzaFiglioDTO> loadOggettoIstanzaFiglioByIdOggIstFiglio(Long idOggettoIstanzaFiglio);
    
    /**
     * Load oggetto istanza figli by uid list.
     *
     * @param gestUID gestUID
     * @return List<OggettoIstanzaExtendedDTO>       list
     */
    List<OggettoIstanzaExtendedDTO> loadOggettiFigli(String gestUID);

    /**
     * Save oggetto istanza figlio long.
     *
     * @param dto the dto
     * @return the long
     */
    Long saveOggettoIstanzaFiglio(OggettoIstanzaFiglioDTO dto);

    /**
     * Delete oggetto istanza figlio integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    Integer deleteOggettoIstanzaFiglio(String gestUID);

    /**
     * Delete oggetto istanza figlio by id ogg ist padre integer.
     *
     * @param idOggettoIstanzaPadre the id oggetto istanza padre
     * @return the integer
     */
    Integer deleteOggettoIstanzaFiglioByIdOggIstPadre(Long idOggettoIstanzaPadre);

    /**
     * Delete oggetto istanza figlio by id ogg ist figlio integer.
     *
     * @param idOggettoIstanzaFiglio the id oggetto istanza figlio
     * @return the integer
     */
    Integer deleteOggettoIstanzaFiglioByIdOggIstFiglio(Long idOggettoIstanzaFiglio);


}