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

import it.csi.scriva.scrivabesrv.dto.IstanzaTemplateQuadroExtendedDTO;

import java.util.List;

/**
 * The interface Istanza template quadro dao.
 *
 * @author CSI PIEMONTE
 */
public interface IstanzaTemplateQuadroDAO {

    /**
     * Load istanze template quadro list.
     *
     * @return List<IstanzaTemplateQuadroExtendedDTO> list
     */
    List<IstanzaTemplateQuadroExtendedDTO> loadIstanzeTemplateQuadro();

    /**
     * Load istanza template quadro by id istanza list.
     *
     * @param idIstanza idIstanza
     * @return List<IstanzaTemplateQuadroExtendedDTO> list
     */
    List<IstanzaTemplateQuadroExtendedDTO> loadIstanzaTemplateQuadroByIdIstanza(Long idIstanza);

    /**
     * Load istanza template quadro by pk list.
     *
     * @param idIstanza        idIstanza
     * @param idTemplateQuadro idTemplateQuadro
     * @return List<IstanzaTemplateQuadroExtendedDTO> list
     */
    List<IstanzaTemplateQuadroExtendedDTO> loadIstanzaTemplateQuadroByPK(Long idIstanza, Long idTemplateQuadro);

    /**
     * Load path istanza template by id istanza string.
     *
     * @param idIstanza idIstanza
     * @return percorso del template dell'istanza
     */
    String loadPathIstanzaTemplateByIdIstanza(Long idIstanza);

    /**
     * Load path allegati template by id istanza string.
     *
     * @param idIstanza idIstanza
     * @return percorso del template degli allegati
     */
    String loadPathAllegatiTemplateByIdIstanza(Long idIstanza);

}