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

import it.csi.scriva.scrivabesrv.dto.TemplateQuadroExtendedDTO;

import java.util.List;

/**
 * The interface Template quadro dao.
 *
 * @author CSI PIEMONTE
 */
public interface TemplateQuadroDAO {

    /**
     * Load template quadri attivi list.
     *
     * @param codComponenteApp the cod componente app
     * @return List<TemplateQuadroExtendedDTO>   list
     */
    List<TemplateQuadroExtendedDTO> loadTemplateQuadriAttivi(String codComponenteApp);

    /**
     * Load template quadro by id list.
     *
     * @param idTemplateQuadro idTemplateQuadro
     * @param codComponenteApp the cod componente app
     * @return List<TemplateQuadroExtendedDTO>   list
     */
    List<TemplateQuadroExtendedDTO> loadTemplateQuadroById(Long idTemplateQuadro, String codComponenteApp);

    /**
     * Load template quadro by id template id quadro list.
     *
     * @param idTemplate       idTemplate
     * @param idQuadro         idQuadro
     * @param codComponenteApp the cod componente app
     * @return List<TemplateQuadroExtendedDTO>   list
     */
    List<TemplateQuadroExtendedDTO> loadTemplateQuadroByIdTemplateIdQuadro(Long idTemplate, Long idQuadro, String codComponenteApp);

    /**
     * Load template quadro by code adempimento list.
     *
     * @param codeAdempimento  codeAdempimento
     * @param codComponenteApp the cod componente app
     * @return List<TemplateQuadroExtendedDTO>   list
     */
    List<TemplateQuadroExtendedDTO> loadTemplateQuadroByCodeAdempimento(String codeAdempimento, String codComponenteApp);

    /**
     * Load template quadro by code template list.
     *
     * @param codeTemplate     codeTemplate
     * @param codComponenteApp the cod componente app
     * @return List<TemplateQuadroExtendedDTO>   list
     */
    List<TemplateQuadroExtendedDTO> loadTemplateQuadroByCodeTemplate(String codeTemplate, String codComponenteApp);

}