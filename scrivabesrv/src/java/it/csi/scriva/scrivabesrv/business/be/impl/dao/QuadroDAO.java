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

import it.csi.scriva.scrivabesrv.dto.QuadroDTO;
import it.csi.scriva.scrivabesrv.dto.QuadroExtendedDTO;

import java.util.List;

/**
 * The interface Quadro dao.
 *
 * @author CSI PIEMONTE
 */
public interface QuadroDAO {

    /**
     * Load quadri attivi list.
     *
     * @return List<QuadroExtendedDTO>   list
     */
    List<QuadroExtendedDTO> loadQuadriAttivi();

    /**
     * Load quadro by id list.
     *
     * @param idQuadro idQuadro
     * @return List<QuadroExtendedDTO>   list
     */
    List<QuadroExtendedDTO> loadQuadroById(Long idQuadro);

    /**
     * Load quadro by id tipo quadro list.
     *
     * @param codTipoQuadro codTipoQuadro
     * @return List<QuadroExtendedDTO>   list
     */
    List<QuadroExtendedDTO> loadQuadroByCodeTipoQuadro(String codTipoQuadro);
    
    /**
     * Load quadro by id code tipo quadro and id istanza.
     *
     * @param codTipoQuadro codTipoQuadro
     * @param idIstanza idIstanza
     * @return List<QuadroExtendedDTO>   list
     */
    List<QuadroExtendedDTO> loadQuadroByCodeTipoQuadroAndIstanza(String codTipoQuadro, Long idIstanza);
      

    /**
     * Load quadro by id template list.
     *
     * @param idTemplate       the id template
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    List<QuadroExtendedDTO> loadQuadroByIdTemplate(Long idTemplate, String codComponenteApp);

    /**
     * Save quadro long.
     *
     * @param formIOSezioneDTO QuadroDTO
     * @return id record salvato
     */
    Long saveQuadro(QuadroDTO formIOSezioneDTO);

    /**
     * Update quadro int.
     *
     * @param formIOSezioneDTO QuadroDTO
     * @return numero record aggiornati
     */
    int updateQuadro(QuadroDTO formIOSezioneDTO);

    /**
     * Delete quadro.
     *
     * @param idFormioSezione idFormioSezione
     */
    void deleteQuadro(Long idFormioSezione);

}