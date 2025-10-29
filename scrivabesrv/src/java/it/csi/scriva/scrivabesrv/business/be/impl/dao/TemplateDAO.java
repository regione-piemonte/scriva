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

import it.csi.scriva.scrivabesrv.dto.TemplateDTO;
import it.csi.scriva.scrivabesrv.dto.TemplateExtendedDTO;

import java.util.List;

/**
 * The interface Template dao.
 *
 * @author CSI PIEMONTE
 */
public interface TemplateDAO {

    /**
     * Load template attivi list.
     *
     * @return List<TemplateExtendedDTO> list
     */
    List<TemplateExtendedDTO> loadTemplateAttivi();

    /**
     * Load template by id list.
     *
     * @param idTemplate idTemplate
     * @return List<TemplateExtendedDTO> list
     */
    List<TemplateExtendedDTO> loadTemplateById(Long idTemplate);

    /**
     * Load template by code list.
     *
     * @param codTemplate codTemplate
     * @return List<TemplateExtendedDTO> list
     */
    List<TemplateExtendedDTO> loadTemplateByCode(String codTemplate);

    /**
     * Load template by cod adempimento list.
     *
     * @param codAdempimento codAdempimento
     * @return List<TemplateExtendedDTO> list
     */
    List<TemplateExtendedDTO> loadTemplateByCodAdempimento(String codAdempimento);

    /**
     * Save template long.
     *
     * @param templateDTO TemplateDTO
     * @return id record salvato
     */
    Long saveTemplate(TemplateDTO templateDTO);

    /**
     * Update template int.
     *
     * @param templateDTO TemplateDTO
     * @return numero record aggiornati
     */
    int updateTemplate(TemplateDTO templateDTO);

    /**
     * Delete template.
     *
     * @param idTemplate idTemplate
     */
    void deleteTemplate(Long idTemplate);


}