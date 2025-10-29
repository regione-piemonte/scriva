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

import it.csi.scriva.scrivabesrv.dto.HelpExtendedDTO;

import java.util.List;

/**
 * The interface Help dao.
 *
 * @author CSI PIEMONTE
 */
public interface HelpDAO {

    /**
     * Load help list.
     *
     * @return List<HelpExtendedDTO> list
     */
    List<HelpExtendedDTO> loadHelp();

    /**
     * Load help by id list.
     *
     * @param idHelp idHelp
     * @return List<HelpExtendedDTO> list
     */
    List<HelpExtendedDTO> loadHelpById(Long idHelp);

    /**
     * Load help by chiave list.
     *
     * @param chiaveHelp chiaveHelp
     * @return List<HelpExtendedDTO> list
     */
    List<HelpExtendedDTO> loadHelpByChiave(String chiaveHelp);

}