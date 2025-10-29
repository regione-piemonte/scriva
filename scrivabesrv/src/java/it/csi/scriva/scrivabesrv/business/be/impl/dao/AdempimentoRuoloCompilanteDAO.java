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

import it.csi.scriva.scrivabesrv.dto.AdempimentoRuoloCompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoRuoloCompilanteExtendedDTO;

import java.util.List;

/**
 * The interface Adempimento ruolo compilante dao.
 *
 * @author CSI PIEMONTE
 */
public interface AdempimentoRuoloCompilanteDAO {

    /**
     * Gets adempimenti ruoli compilante.
     *
     * @return List<AdempimentoRuoloCompilanteExtendedDTO> adempimenti ruoli compilante
     */
    List<AdempimentoRuoloCompilanteExtendedDTO> getAdempimentiRuoliCompilante();

    /**
     * Gets adempimento ruolo compilante by ruolo compilante.
     *
     * @param idRuoloCompilante idRuoloCompilante
     * @return List<AdempimentoRuoloCompilanteExtendedDTO> adempimento ruolo compilante by ruolo compilante
     */
    List<AdempimentoRuoloCompilanteExtendedDTO> getAdempimentoRuoloCompilanteByRuoloCompilante(Long idRuoloCompilante);

    /**
     * Gets adempimento ruolo compilante by adempimento.
     *
     * @param idAdempimento idAdempimento
     * @return List<AdempimentoRuoloCompilanteExtendedDTO> adempimento ruolo compilante by adempimento
     */
    List<AdempimentoRuoloCompilanteExtendedDTO> getAdempimentoRuoloCompilanteByAdempimento(Long idAdempimento, String codComponenteApp);

    /**
     * Gets adempimento ruolo compilante by ruolo compilante adempimento.
     *
     * @param idRuoloCompilante idRuoloCompilante
     * @param idAdempimento     idAdempimento
     * @return List<AdempimentoRuoloCompilanteExtendedDTO> adempimento ruolo compilante by ruolo compilante adempimento
     */
    List<AdempimentoRuoloCompilanteExtendedDTO> getAdempimentoRuoloCompilanteByRuoloCompilanteAdempimento(Long idRuoloCompilante, Long idAdempimento);

    /**
     * Save adempimento ruolo compilante integer.
     *
     * @param adempimentoRuoloCompilanteDTO AdempimentoRuoloCompilanteDTO
     * @return id record salvato
     */
    Integer saveAdempimentoRuoloCompilante(AdempimentoRuoloCompilanteDTO adempimentoRuoloCompilanteDTO);

    /**
     * Update adempimento ruolo compilante integer.
     *
     * @param adempimentoRuoloCompilanteDTO AdempimentoRuoloCompilanteDTO
     * @return numero record aggiornati
     */
    Integer updateAdempimentoRuoloCompilante(AdempimentoRuoloCompilanteDTO adempimentoRuoloCompilanteDTO);
}